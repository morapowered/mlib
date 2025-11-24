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

package io.github.morapowered.database.redis.gson.serializers;

import com.google.gson.*;
import io.github.morapowered.database.redis.configuration.StandardRedisConfiguration;
import io.github.morapowered.database.redis.gson.util.KeyConstants;

import java.lang.reflect.Type;

public class StandardRedisConfigurationSerializer implements JsonSerializer<StandardRedisConfiguration>, JsonDeserializer<StandardRedisConfiguration> {

    @Override
    public StandardRedisConfiguration deserialize(JsonElement json, Type typeOfT, JsonDeserializationContext context) throws JsonParseException {
        if (!json.isJsonObject()) {
            throw new JsonParseException("Invalid JSON of StandardRedisConfiguration");
        }
        JsonObject object = json.getAsJsonObject();
        return StandardRedisConfiguration.builder()
                .host(object.get(KeyConstants.HOST).getAsString())
                .password(object.get(KeyConstants.PASSWORD).getAsString())
                .database(object.get(KeyConstants.DATABSE).getAsInt())
                .ssl(object.get(KeyConstants.USE_SSL).getAsBoolean())
                .build();
    }

    @Override
    public JsonElement serialize(StandardRedisConfiguration value, Type typeOfSrc, JsonSerializationContext context) {
        if (value == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        object.addProperty(KeyConstants.HOST, value.getHost());
        object.addProperty(KeyConstants.PASSWORD, value.getPassword());
        object.addProperty(KeyConstants.DATABSE, value.getDatabase());
        object.addProperty(KeyConstants.USE_SSL, value.isSsl());
        return object;
    }
}
