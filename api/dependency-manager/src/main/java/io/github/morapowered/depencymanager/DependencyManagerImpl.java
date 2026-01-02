/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025-2026 Pedro Souza
 *
 * Permission is hereby granted, free of charge, to any person obtaining a copy
 * of this software and associated documentation files (the "Software"), to deal
 * in the Software without restriction, including without limitation the rights
 * to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
 * copies of the Software, and to permit persons to whom the Software is
 * furnished to do so, subject to the following conditions:
 *
 * The above copyright notice and this permission notice shall be included in all
 * copies or substantial portions of the Software.
 *
 * THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
 * IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
 * FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
 * AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
 * LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
 * OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
 * SOFTWARE.
 */

package io.github.morapowered.depencymanager;

import io.github.morapowered.depencymanager.exception.DependencyDownloadException;
import io.github.morapowered.loaderutils.classloader.IsolatedClassLoader;
import io.github.morapowered.loaderutils.classpath.ClassPathAppender;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.io.InputStream;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.*;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CountDownLatch;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
@Getter
public class DependencyManagerImpl implements DependencyManager {


    private final Path dir;
    private final Set<Repository> repositories;
    private final Map<Dependency, Path> loadedDependencies = new HashMap<>();
    private final Map<Set<Dependency>, IsolatedClassLoader> loaders = new HashMap<>();

    public DependencyManagerImpl(Path dir, Set<Repository> repositories) {
        this.dir = Objects.requireNonNull(dir, "dir").toAbsolutePath().normalize();
        this.repositories = Objects.requireNonNull(repositories, "repositories");
    }

    @Override
    public @NotNull ClassLoader obtainClassLoaderWith(final Set<Dependency> dependencies) {
        Set<Dependency> deeps = Collections.unmodifiableSet(dependencies);

        for (Dependency dependency : dependencies) {
            if (!loadedDependencies.containsKey(dependency)) {
                throw new IllegalStateException("Dependency " + dependency + " is not loaded.");
            }
        }

        synchronized (this.loaders) {
            IsolatedClassLoader classLoader = this.loaders.get(deeps);
            if (classLoader != null) {
                return classLoader;
            }
            URL[] urls = deeps.stream()
                    .map(this.loadedDependencies::get)
                    .map(file -> {
                        try {
                            return file.toUri().toURL();
                        } catch (MalformedURLException e) {
                            throw new RuntimeException(e);
                        }
                    })
                    .toArray(URL[]::new);
            classLoader = new IsolatedClassLoader(urls);
            this.loaders.put(deeps, classLoader);
            return classLoader;
        }

    }

    @Override
    public void loadDependencies(Set<Dependency> dependencies) throws DependencyDownloadException {
        final CountDownLatch latch = new CountDownLatch(dependencies.size());
        for (Dependency dependency : dependencies) {
            if (this.loadedDependencies.containsKey(dependency)) {
                latch.countDown();
                return;
            }
            CompletableFuture.runAsync(() -> {
                try {
                    loadDependency(dependency);
                } catch (Throwable ex) {
                    new RuntimeException("Unable to load dependency " + dependency.getArtifactId(), ex).printStackTrace(System.err);
                } finally {
                    latch.countDown();
                }
            });
        }
        try {
            latch.await();
        } catch (InterruptedException ex) {
            Thread.currentThread().interrupt();
        }
    }

    @Override
    public void loadDependency(Dependency dependency) throws DependencyDownloadException {
        if (this.loadedDependencies.containsKey(dependency)) {
            return;
        }

        Path file = downloadDependency(dependency);
        this.loadedDependencies.put(dependency, file);
    }

    @Override
    public void apply(ClassPathAppender appender, List<Dependency> excludes) {
        for (Path file : loadedDependencies.entrySet()
                .stream()
                .filter(e -> !excludes.contains(e.getKey()))
                .map(Map.Entry::getValue)
                .toList()) {
            appender.addJarToClasspath(file);
        }
    }

    public @NotNull Collection<Repository> getRepositories() {
        return Collections.unmodifiableSet(repositories);
    }

    public @NotNull Map<Dependency, Path> getLoadedDependencies() {
        return Collections.unmodifiableMap(loadedDependencies);
    }

    private Path downloadDependency(Dependency dependency) {
        Path file = dir.resolve(dependency.getMavenRepositoryPath());
        if (Files.exists(file)) {
            return file;
        }

        DependencyDownloadException lastError = null;
        for (Repository repository : repositories) {
            try {
                download(repository, dependency, file);
                return file;
            } catch (DependencyDownloadException ex) {
                lastError = ex;
            }
        }

        throw Objects.requireNonNull(lastError);
    }

    private byte[] download(Repository repository, Dependency dependency) throws DependencyDownloadException {
        byte[] data;
        try {
            URLConnection connection = repository.getConnection().apply(dependency, repository);
            try (InputStream in = connection.getInputStream()) {
                data = in.readAllBytes();
            }
        } catch (Exception ex) {
            throw new DependencyDownloadException(ex, dependency);
        }
        if (data.length == 0) {
            throw new DependencyDownloadException("Empty stream", dependency);
        }
        if (dependency.getChecksum().length > 0) {
            byte[] hash = Dependency.createDigest().digest(data);
            if (!Arrays.equals(dependency.getChecksum(), hash)) { // Checksum
                throw new DependencyDownloadException("Downloaded file had an invalid hash. " +
                        "Expected: " + Base64.getEncoder().encodeToString(dependency.getChecksum()) + " " +
                        "Actual: " + Base64.getEncoder().encodeToString(hash), dependency);
            }
        }
        return data;
    }


    private void download(Repository repository, Dependency dependency, Path file) throws DependencyDownloadException {
        try {
            Path parent = file.getParent().normalize().toAbsolutePath();
            if (!Files.exists(parent)) {
                Files.createDirectories(parent);
            } else if (!Files.isDirectory(parent)) {
                throw new IllegalStateException("Unable access dependency " + dependency.getCoordinates() + " dir: ");
            }
            Files.write(file, download(repository, dependency));
        } catch (IOException ex) {
            throw new DependencyDownloadException(ex, dependency);
        }
    }

    public static final class BuilderImpl implements Builder {

        private Path dir;
        private final HashSet<Repository> repositories = new HashSet<>();

        BuilderImpl() {
        }

        @Override
        public @NotNull Builder dir(Path dir) {
            this.dir = Objects.requireNonNull(dir, "dir");
            return this;
        }

        @Override
        public @NotNull Builder repository(Repository repository) {
            this.repositories.add(Objects.requireNonNull(repository, "repository"));
            return this;
        }


        @Override
        public @NotNull DependencyManager build() {
            return new DependencyManagerImpl(dir, repositories);
        }
    }


}
