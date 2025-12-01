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

package io.github.morapowered.inventory.gson.util;

import com.google.gson.JsonElement;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import io.github.morapowered.util.function.CheckedSupplier;
import net.minecraft.ResourceLocationException;
import net.minecraft.resources.ResourceLocation;

import java.util.UUID;
import java.util.function.Supplier;

public class GsonUtil {

    public static ResourceLocation resourceLocationOrThrow(JsonElement element) throws JsonSyntaxException {
        String value = element.getAsString();
        if (value == null) {
            throw new JsonParseException("Element cannot be read string");
        }
        try {
            return ResourceLocation.parse(value);
        } catch (ResourceLocationException ex) {
            throw new JsonParseException(ex);
        }
    }

    public static UUID uuidOrNull(JsonElement element) throws JsonSyntaxException {
        String value = element.getAsString();
        if (value == null) {
            return null;
        }
        try {
            return UUID.fromString(value);
        } catch (Exception ex) {
            throw new JsonParseException(ex);
        }
    }

    public static <T, E extends Throwable> T checkGetOrDefault(Supplier<Boolean> checker, CheckedSupplier<T, E> supplier, T defaultValue) throws E {
        if (!checker.get()) {
            return defaultValue;
        }
        return supplier.get();
    }

}
