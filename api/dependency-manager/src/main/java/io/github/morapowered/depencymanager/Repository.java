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

import io.github.morapowered.util.function.CheckedBiFunction;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.IOException;
import java.net.URL;
import java.net.URLConnection;
import java.util.concurrent.TimeUnit;

/*
 * Based on concepts and structure from the "LuckPerms" (https://github.com/LuckPerms/LuckPerms/)
 * (c) lucko (Luck) <luck@lucko.me> and contributors — licensed under the MIT License
 * Rewritten and adapted by Pedro Souza in 2025
 */
public interface Repository {

    CheckedBiFunction<Dependency, Repository, URLConnection, IOException> DEFAULT_CONNECTION = (dependency, repository) -> {
        URL url = new URL(repository.getUrl() + dependency.getMavenRepositoryPath());
        return url.openConnection();
    };

    Repository MAVEN_CENTRAL = Repository.builder()
            .url("https://repo.maven.apache.org/maven2/")
            .build();

    // A mirror of Maven Central, similar to what LuckPerms does, with the same justifications.
    Repository PAPERMC = Repository.builder()
            .url("https://repo.papermc.io/repository/maven-public/")
            .connection((dependency, repository) -> {
                URLConnection connection = DEFAULT_CONNECTION.apply(dependency, repository);

                // Set a connect/read timeout, so if the mirror goes offline we can fallback
                // to Maven Central within a reasonable time.
                connection.setConnectTimeout((int) TimeUnit.SECONDS.toMillis(5));
                connection.setReadTimeout((int) TimeUnit.SECONDS.toMillis(10));

                return connection;
            })
            .build();

    static Builder builder() {
        return new RepositoryImpl.BuilderImpl();
    }

    @NotNull String getUrl();

    @NotNull CheckedBiFunction<Dependency, Repository, URLConnection, IOException> getConnection();

    interface Builder {


        @Contract("_ -> this")
        @NotNull Builder url(final @NotNull String url);

        @Contract
        @NotNull Builder connection(final @NotNull CheckedBiFunction<Dependency, Repository, URLConnection, IOException> connection);

        @NotNull Repository build();

    }

}
