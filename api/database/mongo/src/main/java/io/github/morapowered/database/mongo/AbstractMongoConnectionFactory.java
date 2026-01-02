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

package io.github.morapowered.database.mongo;

import com.mongodb.ConnectionString;
import com.mongodb.MongoClientSettings;
import com.mongodb.MongoException;
import io.github.morapowered.database.mongo.configuration.MongoConfiguration;
import org.jetbrains.annotations.Contract;
import org.jetbrains.annotations.NotNull;

import java.io.Closeable;
import java.io.IOException;
import java.util.Objects;

public abstract class AbstractMongoConnectionFactory<T extends Closeable> {

    private final String applicationName;
    private T client;

    protected AbstractMongoConnectionFactory(String applicationName) {
        this.applicationName = applicationName;
    }

    public void setup(MongoConfiguration configuration) throws MongoException {
        MongoClientSettings.Builder builder = MongoClientSettings.builder()
                .applyConnectionString(new ConnectionString(configuration.getConnectionString()))
                .applicationName(applicationName);
        this.client = createClient(builder.build());
    }

    public void shutdown() throws MongoException {
        if (client != null) {
            try {
                client.close();
            } catch (IOException e) {
                throw MongoException.fromThrowableNonNull(e);
            }
        }
    }

    protected abstract T createClient(MongoClientSettings settings);

    public @NotNull T getClient() throws MongoException {
        if (client == null) {
            throw new MongoException("Unable connection with Mongo (client returned null).");
        }
        return client;
    }


    public static abstract class Builder<T extends AbstractMongoConnectionFactory<E>, E extends Closeable, B extends Builder<T, E, B>> {

        protected String applicationName;

        Builder() {
        }

        protected abstract B asBuilder();

        @Contract("_ -> this")
        public @NotNull B applicationName(final @NotNull String applicationName) {
            this.applicationName = Objects.requireNonNull(applicationName, "applicationName");
            return asBuilder();
        }

        public abstract @NotNull T build();

    }
}
