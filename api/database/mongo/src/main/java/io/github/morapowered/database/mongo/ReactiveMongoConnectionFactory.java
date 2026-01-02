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

import com.mongodb.MongoClientSettings;
import com.mongodb.reactivestreams.client.MongoClient;
import com.mongodb.reactivestreams.client.MongoClients;
import org.jetbrains.annotations.NotNull;

public class ReactiveMongoConnectionFactory extends AbstractMongoConnectionFactory<MongoClient> {

    public static @NotNull Builder builder() {
        return new Builder();
    }

    public ReactiveMongoConnectionFactory(String applicationName) {
        super(applicationName);
    }

    @Override
    protected MongoClient createClient(MongoClientSettings settings) {
        return MongoClients.create(settings);
    }

    public static final class Builder extends AbstractMongoConnectionFactory.Builder<ReactiveMongoConnectionFactory, MongoClient, Builder> {

        @Override
        protected Builder asBuilder() {
            return this;
        }

        @Override
        public @NotNull ReactiveMongoConnectionFactory build() {
            return new ReactiveMongoConnectionFactory(applicationName);
        }

    }
}
