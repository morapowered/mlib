/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025 Pedro Souza
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
import io.github.morapowered.loaderutils.classpath.ClassPathAppender;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.nio.file.Path;
import java.util.*;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
public interface DependencyManager {

    static Builder builder() {
        return new DependencyManagerImpl.BuilderImpl();
    }

    @NotNull Path getDir();

    @NotNull Collection<Repository> getRepositories();

    @NotNull ClassLoader obtainClassLoaderWith(Set<Dependency> dependencies);

    void loadDependencies(Set<Dependency> dependencies) throws DependencyDownloadException;

    void loadDependency(Dependency dependency) throws DependencyDownloadException;

    @NotNull Map<Dependency, Path> getLoadedDependencies();

    void apply(ClassPathAppender appender, List<Dependency> excludes);

    default void apply(ClassPathAppender appender) {
        apply(appender, Collections.emptyList());
    }


    interface Builder {

        @Contract("_ -> this")
        @NotNull Builder dir(Path dir);

        @Contract("_ -> this")
        @NotNull Builder repository(Repository repository);

        @Contract("_ -> this")
        default @NotNull Builder repository(final @NotNull String url) {
            return repository(Repository.builder()
                    .url(url)
                    .build());
        }

        default @NotNull Builder withMavenCentral() {
            return repository(Repository.PAPERMC) // Maven Central Mirror
                    .repository(Repository.MAVEN_CENTRAL);
        }

        @NotNull DependencyManager build();


    }

}
