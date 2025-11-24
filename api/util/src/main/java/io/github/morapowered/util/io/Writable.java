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
import java.nio.file.Files;
import java.nio.file.OpenOption;
import java.nio.file.Path;
import java.util.Objects;

@FunctionalInterface
public interface Writable {

    @Nullable OutputStream createOutputStream() throws IOException;

    default boolean isWritable() throws IOException {
        try (OutputStream stream = createOutputStream()) {
            return stream != null;
        }
    }

    default @Nullable BufferedWriter createWriter() throws IOException {
        OutputStream outputStream = createOutputStream();
        if (outputStream == null) {
            return null;
        }
        return new BufferedWriter(new OutputStreamWriter(outputStream));
    }

    default void write(byte[] data) throws IOException {
        try (OutputStream outputStream = createOutputStream()) {
            if (outputStream == null) {
                return;
            }
            outputStream.write(data);
        }
    }

    static Writable outputStream(final @NotNull CheckedSupplier<OutputStream, IOException> outputStreamSupplier) {
        Objects.requireNonNull(outputStreamSupplier, "outputStreamSupplier");
        return outputStreamSupplier::get;
    }

    static Writable path(final @NotNull Path path, final @NotNull OpenOption... options) {
        Objects.requireNonNull(path, "path");
        Objects.requireNonNull(options, "options");
        return () -> Files.newOutputStream(path, options);
    }

    static Writable file(final @NotNull File file, final @NotNull OpenOption... options) {
        Objects.requireNonNull(file, "file");
        return path(file.toPath(), options);
    }

    static Writable url(@NotNull URL url) throws IOException {
        Objects.requireNonNull(url, "url");
        return () -> {
            final URLConnection connection = url.openConnection();
            try {
                return connection.getOutputStream();
            } catch (UnknownServiceException e) {
                return null;
            }
        };
    }

}
