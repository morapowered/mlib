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

package io.github.morapowered.database.mongo.gson.serializers;

import com.google.gson.*;
import io.github.morapowered.database.mongo.configuration.MongoConfiguration;
import io.github.morapowered.database.mongo.util.KeyConstants;

import java.lang.reflect.Type;

public class GsonMongoConfigurationSerializer implements JsonSerializer<MongoConfiguration>, JsonDeserializer<MongoConfiguration> {
    @Override
    public MongoConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!(json.isJsonObject())) {
            throw new JsonParseException("Invalid MongoConfiguration JSON");
        }
        JsonObject object = json.getAsJsonObject();
        return MongoConfiguration.builder()
                .connectionString(object.get(KeyConstants.CONNECTION_STRING).getAsString())
                .build();
    }

    @Override
    public JsonElement serialize(MongoConfiguration value, Type typeOfSrc, JsonSerializationContext context) {
        if (value == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        object.addProperty(KeyConstants.CONNECTION_STRING, value.getConnectionString());
        return object;
    }
}
