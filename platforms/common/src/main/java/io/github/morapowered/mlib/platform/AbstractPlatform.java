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

package io.github.morapowered.mlib.platform;

import io.github.morapowered.mlib.platform.internal.InternalPlatform;
import io.github.morapowered.mlib.util.BuildParameters;
import org.jetbrains.annotations.ApiStatus;
import org.slf4j.Logger;

import java.lang.reflect.Field;

@ApiStatus.Internal
public abstract class AbstractPlatform implements Platform {

    public AbstractPlatform() {
        try {
            Field platformField = InternalPlatform.class.getDeclaredField("platform");
            platformField.setAccessible(true);
            platformField.set(null, this);
            platformField.setAccessible(false);
        } catch (NoSuchFieldException | IllegalAccessException e) {
            throw new IllegalStateException(e);
        }
    }

    protected void init() {
        getLogger().info("mlib {} (version: {}, branch: {}, build: {})", getName(), isMod() ? BuildParameters.MOD_VERSION : BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
    }

    protected void shutdown() {
        getLogger().info("mlib shutdown");
    }

    protected abstract Logger getLogger();

    protected abstract boolean isMod();

}
