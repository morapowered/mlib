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

package io.github.morapowered.inventory.util;

import io.github.morapowered.platform.ModPlatform;
import net.kyori.adventure.text.format.TextDecoration;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.minecraft.network.chat.Component;

public class AdventureUtil {

    public static Component text(String text) {
        return text(component(text));
    }

    public static net.kyori.adventure.text.Component component(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

    public static Component text(net.kyori.adventure.text.Component component) {
        return nonWrappingSerializer()
                .serialize(component.applyFallbackStyle(style -> style.decoration(TextDecoration.ITALIC, false)));
    }

    private static ComponentSerializer<net.kyori.adventure.text.Component, net.kyori.adventure.text.Component, Component> nonWrappingSerializer() {
        return ModPlatform.get().nonWrappingSerializer();
    }

}
