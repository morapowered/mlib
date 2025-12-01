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
import io.github.morapowered.mlib.util.BuildParameters;
import io.github.morapowered.platform.ModPlatform;
import io.github.morapowered.platform.Platform;
import io.github.morapowered.platform.provider.PlatformProvider;
import net.minecraft.server.MinecraftServer;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.server.ServerStartingEvent;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

@Mod(value = "mlib")
public class MLibNeoForge implements ModPlatform {

    private final Logger logger = LogUtils.getLogger();
    private MinecraftServer server;

    public MLibNeoForge(IEventBus eventBus) {
        try {
            Class<PlatformProvider> clazz = PlatformProvider.class;
            Method method = clazz.getDeclaredMethod("set", Platform.class);
            method.setAccessible(true);
            method.invoke(null, this);
            method.setAccessible(false);
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException ex) {
            throw new IllegalStateException("Fail initializing mlib", ex);
        }
        eventBus.addListener(this::initialize);
        NeoForge.EVENT_BUS.register(this);
    }

    public void initialize(FMLCommonSetupEvent event) {
        logger.info("mlib (version: {}, branch: {}, build: {})", BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
        // Start Dependency Manager
    }

    @SubscribeEvent
    public void onServerStopping(ServerStartingEvent event) {
        this.server = event.getServer();
    }

    @Override
    public @NotNull MinecraftServer getMinecraftServer() {
        if (server == null) {
            throw new IllegalStateException("Server has not been started yet");
        }
        return server;
    }


    @Override
    public @NotNull String getImplementationName() {
        return "neoforge";
    }
}
