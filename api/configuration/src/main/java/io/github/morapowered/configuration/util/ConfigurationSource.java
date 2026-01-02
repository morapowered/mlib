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

package io.github.morapowered.configuration.util;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;
import org.spongepowered.configurate.loader.AtomicFiles;

import java.io.*;
import java.net.URL;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Objects;
import java.util.concurrent.Callable;

import static java.util.Objects.requireNonNull;

public interface ConfigurationSource {

    @Nullable Callable<BufferedReader> getSource();

    @Nullable Callable<BufferedWriter> getSink();

    static ConfigurationSource path(final Path path) {
        Path absPath = Objects.requireNonNull(path, "path").toAbsolutePath();
        return create(() -> Files.newBufferedReader(absPath, StandardCharsets.UTF_8), AtomicFiles.atomicWriterFactory(path, StandardCharsets.UTF_8));
    }

    static ConfigurationSource file(final File file) {
        return path(Objects.requireNonNull(file, "file").toPath());
    }

    static ConfigurationSource url(final URL url) {
        requireNonNull(url, "url");
        return create(() -> new BufferedReader(new InputStreamReader(url.openConnection().getInputStream(), StandardCharsets.UTF_8)), null);
    }

    static ConfigurationSource resource(final @NotNull ClassLoader loader, final @NotNull String resource) {
        Objects.requireNonNull(loader, "loader");
        Objects.requireNonNull(resource, "resource");
        return create(() -> {
            InputStream is = loader.getResourceAsStream(resource);
            if (is == null) {
                return null;
            }
            return new BufferedReader(new InputStreamReader(is, StandardCharsets.UTF_8));
        }, () -> null);
    }

    static ConfigurationSource resource(final @NotNull String resource) {
        Objects.requireNonNull(resource, "resource");
        final Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        final ClassLoader classLoader = callerClass.getClassLoader();
        return resource(classLoader, resource);
    }

    static ConfigurationSource string(final @NotNull String string, final @NotNull Charset charset) {
        Objects.requireNonNull(string, "string");
        Objects.requireNonNull(charset, "charset");
        return create(() -> new BufferedReader(new InputStreamReader(new ByteArrayInputStream(string.getBytes(charset)), charset)), () -> null);
    }

    static ConfigurationSource stringUtf8(final @NotNull String string) {
        Objects.requireNonNull(string, "string");
        return string(string, StandardCharsets.UTF_8);
    }

    static ConfigurationSource create(@Nullable Callable<BufferedReader> source, Callable<BufferedWriter> sink) {
        return new ConfigurationSource() {
            @Override
            public @Nullable Callable<BufferedReader> getSource() {
                return source;
            }

            @Override
            public @Nullable Callable<BufferedWriter> getSink() {
                return sink;
            }
        };
    }

}
