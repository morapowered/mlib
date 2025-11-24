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

package io.github.morapowered.database.sql.configurate.util;

import org.spongepowered.configurate.ConfigurationNode;
import org.spongepowered.configurate.serialize.SerializationException;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class Util {


    public static Map<String, String> mapOrThrow(ConfigurationNode node) throws SerializationException {
        if (!node.isMap()) {
            throw new SerializationException("Invalid map in " + pathToString(node));
        }
        HashMap<String, String> map = new HashMap<>();
        for (Map.Entry<Object, ? extends ConfigurationNode> entry : node.childrenMap().entrySet()) {
            String key = entry.getKey().toString();
            String value = requireStringOrThrow(entry.getValue());
            map.put(key, value);
        }
        return map;
    }

    public static Map<String, String> mapOrEmpty(ConfigurationNode rootNode, String... nodeKey) throws SerializationException {
        Object[] keys = Arrays.stream(nodeKey).toArray();
        ConfigurationNode node = rootNode.node();
        if (!rootNode.hasChild(keys) || node.empty() || !node.isMap()) {
            return new HashMap<>();
        }
        return mapOrThrow(node);
    }

    public static String requireStringOrThrow(ConfigurationNode node) throws SerializationException {
        return requireOrThrow(node, String.class);
    }

    public static int requireIntOrThrow(ConfigurationNode node) throws SerializationException {
        return requireOrThrow(node, Integer.class);
    }

    public static <T> T requireOrThrow(ConfigurationNode node, Class<T> clazz) throws SerializationException {
        T obj = node.get(clazz);
        if (obj == null) {
            throw new SerializationException("Required " + clazz.getName() + " in " + pathToString(node));
        }
        return obj;
    }

    public static String pathToString(ConfigurationNode node) {
        return String.join(".", Arrays.stream(node.path().array()).map(Object::toString).toList());
    }

}
