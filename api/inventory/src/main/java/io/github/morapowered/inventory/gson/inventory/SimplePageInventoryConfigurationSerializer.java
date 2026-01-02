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

package io.github.morapowered.inventory.gson.inventory;

import com.google.gson.*;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;

import java.lang.reflect.Type;

public final class SimplePageInventoryConfigurationSerializer implements JsonSerializer<SimplePageInventoryConfiguration>, JsonDeserializer<SimplePageInventoryConfiguration> {

    @Override
    public SimplePageInventoryConfiguration deserialize(JsonElement element, Type type, JsonDeserializationContext context) throws JsonParseException {
        if (!element.isJsonObject()) {
            throw new JsonParseException("Invalid configuration");
        }
        JsonObject object = element.getAsJsonObject();
        return AbstractPageConfigurationSerializer.deserialize(SimplePageInventoryConfiguration.builder(), object, context)
                .build();
    }

    @Override
    public JsonElement serialize(SimplePageInventoryConfiguration configuration, Type type, JsonSerializationContext context) {
        if (configuration == null) {
            return null;
        }
        JsonObject object = new JsonObject();
        AbstractPageConfigurationSerializer.serialize(configuration, object, context);
        return object;
    }
}
