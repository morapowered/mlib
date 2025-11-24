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
import io.github.morapowered.database.sql.configuration.DriverBasedDatabaseConfiguration;
import io.github.morapowered.database.sql.configuration.PoolConfiguration;
import io.github.morapowered.database.sql.gson.util.KeyConstants;
import io.github.morapowered.database.sql.gson.util.Util;

import java.lang.reflect.Type;

public class DriverBasedDatabaseConfigurationSerializer implements JsonSerializer<DriverBasedDatabaseConfiguration>, JsonDeserializer<DriverBasedDatabaseConfiguration> {

    @Override
    public DriverBasedDatabaseConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!element.isJsonObject()) {
            throw new JsonParseException("Invalid DriverBasedDatabaseConfiguration JSON format");
        }
        JsonObject object = element.getAsJsonObject();
        return DriverBasedDatabaseConfiguration.builder()
                .host(object.get(KeyConstants.HOST).getAsString())
                .username(object.get(KeyConstants.USERNAME).getAsString())
                .password(object.get(KeyConstants.PASSWORD).getAsString())
                .database(object.get(KeyConstants.DATABASE).getAsString())
                .poolConfiguration(context.deserialize(object.getAsJsonObject(KeyConstants.POOL_CONFIGURATION), PoolConfiguration.class))
                .properties(Util.mapOrThrow(object.getAsJsonObject(KeyConstants.PROPERTIES)))
                .tables(Util.mapOrEmpty(object, KeyConstants.TABLES))
                .build();
    }

    @Override
    public JsonElement serialize(DriverBasedDatabaseConfiguration value, Type type, JsonSerializationContext context) {
        if (value == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        object.addProperty(KeyConstants.HOST, value.getHost());
        object.addProperty(KeyConstants.USERNAME, value.getUsername());
        object.addProperty(KeyConstants.PASSWORD, value.getPassword());
        object.add(KeyConstants.POOL_CONFIGURATION, context.serialize(value.getPoolConfiguration(), PoolConfiguration.class));
        object.add(KeyConstants.PROPERTIES, Util.mapAsJson(value.getProperties()));
        if (!value.getTables().isEmpty()) {
            object.add(KeyConstants.TABLES, Util.mapAsJson(value.getTables()));
        }
        return null;
    }
}
