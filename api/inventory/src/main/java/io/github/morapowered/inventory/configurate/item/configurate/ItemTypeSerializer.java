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

package io.github.morapowered.inventory.configurate.item.configurate;

import io.github.morapowered.inventory.configurate.item.SimpleItemSerializer;
import io.github.morapowered.inventory.configurate.serializer.ConfigurateItemSerializer;
import io.github.morapowered.inventory.util.ItemKeyConstants;
import io.github.morapowered.inventory.configurate.util.ConfigurateUtil;
import io.github.morapowered.inventory.item.ItemType;
import io.github.morapowered.inventory.item.SimpleItem;
import lombok.RequiredArgsConstructor;
import net.minecraft.resources.ResourceLocation;
import org.checkerframework.checker.nullness.qual.Nullable;
import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;
import org.spongepowered.configurate.serialize.TypeSerializer;

import java.lang.reflect.Type;
import java.util.HashMap;

@RequiredArgsConstructor
public class ItemTypeSerializer implements TypeSerializer<SimpleItem> {

    private final HashMap<ResourceLocation, ConfigurateItemSerializer<?>> serializers;

    @Override
    public SimpleItem deserialize(Type type, ConfigurationNode node) throws SerializationException {
        ResourceLocation typeKey = ConfigurateUtil.resourceLocationOrThrow(node.node(ItemKeyConstants.TYPE));
        ConfigurateItemSerializer<?> serializer = serializers.getOrDefault(typeKey, SimpleItemSerializer.INSTANCE);
        return serializer.deserialize(node);
    }

    @Override
    public void serialize(Type type, @Nullable SimpleItem value, ConfigurationNode node) throws SerializationException {
        if (value == null) {
            node.set(null);
            return;
        }
        writeValue(value, node);
        if (value.getItemType() != ItemType.SIMPLE) {
            node.node(ItemKeyConstants.TYPE).set(value.getItemType().getKey().toString());
        }
    }

    <V extends SimpleItem> void writeValue(V value, ConfigurationNode node) throws SerializationException {
        ConfigurateItemSerializer<V> serializer = (ConfigurateItemSerializer<V>) serializers.getOrDefault(value.getItemType().getKey(), SimpleItemSerializer.INSTANCE);
        serializer.serialize(value, node);
    }

}
