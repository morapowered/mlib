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

package io.github.morapowered.database.sql.gson.serializers;

import com.google.gson.*;
import io.github.morapowered.database.sql.configuration.PoolConfiguration;
import io.github.morapowered.database.sql.gson.util.KeyConstants;

import java.lang.reflect.Type;

public class PoolConfigurationSerializer implements JsonSerializer<PoolConfiguration>, JsonDeserializer<PoolConfiguration> {

    @Override
    public PoolConfiguration deserialize(JsonElement element, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!element.isJsonObject()) {
            throw new JsonParseException("Invalid DriverBasedDatabaseConfiguration JSON format");
        }
        JsonObject object = element.getAsJsonObject();
        return PoolConfiguration.builder()
                .maximumPoolSize(object.get(KeyConstants.MAXIMUM_POOL_SIZE).getAsInt())
                .minimumIdle(object.get(KeyConstants.MINIMUM_IDLE).getAsInt())
                .maximumLifetime(object.get(KeyConstants.MAXIMUM_LIFETIME).getAsInt())
                .connectionTimeout(object.get(KeyConstants.CONNECTION_TIMEOUT).getAsInt())
                .keepAliveTime(object.get(KeyConstants.KEEP_ALIVE_TIME).getAsInt())
                .build();

    }

    @Override
    public JsonElement serialize(PoolConfiguration src, Type typeOfSrc, JsonSerializationContext context) {
        return null;
    }
}
