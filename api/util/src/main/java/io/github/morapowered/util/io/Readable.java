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

package io.github.morapowered.util.io;

import io.github.morapowered.util.function.CheckedSupplier;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.net.UnknownServiceException;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

@FunctionalInterface
public interface Readable {

    @Nullable InputStream createInputStream() throws IOException;

    default boolean isReadable() throws IOException {
        try (InputStream stream = createInputStream()) {
            return stream != null;
        }
    }

    default @Nullable BufferedReader createReader() throws IOException {
        InputStream inputStream = createInputStream();
        if (inputStream == null) {
            return null;
        }
        return new BufferedReader(new InputStreamReader(inputStream));
    }

    default byte[] toByteArray() throws IOException {
        try (InputStream inputStream = createInputStream()) {
            if (inputStream == null) {
                return null;
            }
            return inputStream.readAllBytes();
        }
    }

    default String toUTF8String() throws IOException {
        return new String(toByteArray(), StandardCharsets.UTF_8);
    }

    default String toString(Charset charset) throws IOException {
        return new String(toByteArray(), charset);
    }

    static Readable inputStream(final @NotNull CheckedSupplier<InputStream, IOException> inputStreamSupplier) {
        Objects.requireNonNull(inputStreamSupplier, "inputStreamSupplier");
        return inputStreamSupplier::get;
    }

    static Readable path(final @NotNull Path path, final @NotNull OpenOption... options) {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(options, "options");
        return () -> {
            if (Files.exists(path)) {
                return Files.newInputStream(path, options);
            }
            return null;
        };
    }

    static Readable file(final @NotNull File file, final @NotNull OpenOption... options) {
        Objects.requireNonNull(file, "file");
        return path(file.toPath(), options);
    }

    static Readable url(@NotNull URL url) {
        Objects.requireNonNull(url, "url");
        return () -> {
            final URLConnection connection = url.openConnection();
            try {
                return connection.getInputStream();
            } catch (UnknownServiceException e) {
                return null;
            }
        };
    }

    static Readable resource(final @NotNull ClassLoader loader, final @NotNull String resource) {
        Objects.requireNonNull(loader, "loader");
        Objects.requireNonNull(resource, "resource");
        return () -> loader.getResourceAsStream(resource);
    }

    static Readable resource(final @NotNull String resource) {
        Objects.requireNonNull(resource, "resource");
        final Class<?> callerClass = StackWalker.getInstance(StackWalker.Option.RETAIN_CLASS_REFERENCE).getCallerClass();
        final ClassLoader classLoader = callerClass.getClassLoader();
        return resource(classLoader, resource);
    }

    static Readable string(final @NotNull String string, final @NotNull Charset charset) {
        Objects.requireNonNull(string, "string");
        Objects.requireNonNull(charset, "charset");
        return () -> new ByteArrayInputStream(string.getBytes(charset));
    }

    static Readable stringUtf8(final @NotNull String string) {
        Objects.requireNonNull(string, "string");
        return string(string, StandardCharsets.UTF_8);
    }



}
