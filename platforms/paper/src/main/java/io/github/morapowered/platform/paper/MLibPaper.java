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

package io.github.morapowered.platform.paper;

import io.github.morapowered.platform.Platform;
import io.github.morapowered.platform.provider.PlatformProvider;
import io.github.morapowered.platform.util.BuildParameters;
import org.bukkit.plugin.java.JavaPlugin;
import org.jetbrains.annotations.NotNull;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

public class MLibPaper extends JavaPlugin implements Platform {

    public MLibPaper() {
        try {
            Class<PlatformProvider> clazz = PlatformProvider.class;
            Method method = clazz.getDeclaredMethod("set", Platform.class);
            method.setAccessible(true);
            method.invoke(null, this);
            method.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Fail initializing mlib", ex);
        }
    }

    @Override
    public void onLoad() {
        getSLF4JLogger().info("mlib (version: {}, branch: {}, build: {})", BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
        // Start Dependency Maznager here?
    }

    @Override
    public @NotNull String getImplementationName() {
        return "paper";
    }
}
