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

package io.github.morapowered.inventory.pages.config;

import io.github.morapowered.inventory.item.SimpleItem;
import org.jetbrains.annotations.NotNull;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public abstract class PageConfigurationAbstractBuilder<B> {

    protected String title = "{green}Title here";
    protected int rows = 3;
    protected final HashMap<String, SimpleItem> staticItems = new HashMap<>();

    public @NotNull B title(@NotNull String title) {
        this.title = Objects.requireNonNull(title, "title");
        return asBuilder();
    }

    public @NotNull B rows(int rows) {
        if (rows < 1 || rows > 6) {
            throw new IllegalArgumentException("Invalid rows range [1, 6]: " + rows);
        }
        this.rows = rows;
        return asBuilder();
    }

    public @NotNull B staticItems(@NotNull Map<String, SimpleItem> staticItems) {
        this.staticItems.putAll(Objects.requireNonNull(staticItems, "staticItems"));
        return asBuilder();
    }

    public @NotNull B staticItem(@NotNull String id, @NotNull SimpleItem item) {
        this.staticItems.put(Objects.requireNonNull(id, "id"), Objects.requireNonNull(item, "item"));
        return asBuilder();
    }

    public abstract B asBuilder();

}
