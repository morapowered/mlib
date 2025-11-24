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

package io.github.morapowered.mlib;

import com.mojang.logging.LogUtils;
import io.github.morapowered.mlib.platform.AbstractPlatform;
import lombok.Getter;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStoppingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

@Mod(value = "mlib")
public class MLibNeoForgeMod extends AbstractPlatform {

    private final @Getter Logger logger = LogUtils.getLogger();

    public MLibNeoForgeMod(IEventBus eventBus) {
        super();

        eventBus.addListener(this::initialize);
        NeoForge.EVENT_BUS.register(this);
    }

    public void initialize(FMLCommonSetupEvent event) {
        init();
    }

    @Override
    protected boolean isMod() {
        return true;
    }


    @Override
    public @NotNull String getName() {
        return "neoforge";
    }

    @SubscribeEvent
    public void onServerStopping(ServerStoppingEvent event) {
        shutdown();
    }
}
