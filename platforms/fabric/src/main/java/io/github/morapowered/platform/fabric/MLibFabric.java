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

package io.github.morapowered.platform.fabric;

import com.mojang.logging.LogUtils;
import io.github.morapowered.depencymanager.DependencyManager;
import io.github.morapowered.platform.ModPlatform;
import io.github.morapowered.platform.Platform;
import io.github.morapowered.platform.fabric.classpath.FabricClassPathAppender;
import io.github.morapowered.platform.fabric.dependencies.FabricDependencies;
import io.github.morapowered.platform.provider.PlatformProvider;
import io.github.morapowered.platform.util.BuildParameters;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerLifecycleEvents;
import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.entrypoint.PreLaunchEntrypoint;
import net.kyori.adventure.platform.fabric.FabricAudiences;
import net.kyori.adventure.platform.fabric.FabricServerAudiences;
import net.kyori.adventure.platform.fabric.impl.NonWrappingComponentSerializer;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.serializer.ComponentSerializer;
import net.minecraft.server.MinecraftServer;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

public class MLibFabric implements ModPlatform, PreLaunchEntrypoint {

    private final Logger logger = LogUtils.getLogger();
    private final FabricClassPathAppender classPathAppender = new FabricClassPathAppender();
    private final Path workDir = FabricLoader.getInstance().getConfigDir().resolve("mlib/");
    private MinecraftServer server;

    public MLibFabric() {
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
    public void onPreLaunch() {
        logger.info("mlib (version: {}, branch: {}, build: {})", BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
        DependencyManager dependencyManager = DependencyManager.builder()
                .withMavenCentral()
                .dir(workDir.resolve("libraries/"))
                .build();
        try {
            dependencyManager.loadDependencies(FabricDependencies.DEPENDENCIES);
            dependencyManager.apply(classPathAppender);
            logger.info("mlib Loaded {} dependencies.", FabricDependencies.DEPENDENCIES.size());
        } catch (Exception ex) {
            throw new IllegalStateException("Fail resolving dependencies: " + ex.getMessage(), ex);
        }
        ServerLifecycleEvents.SERVER_STARTING.register(minecraftServer -> this.server = minecraftServer);
    }

    @Override
    public @NotNull MinecraftServer getMinecraftServer() {
        if (server == null) {
            throw new IllegalStateException("Server has not been started yet");
        }
        return server;
    }

    @Override
    public @NotNull ComponentSerializer<Component, Component, net.minecraft.network.chat.Component> nonWrappingSerializer() {
        return FabricAudiences.nonWrappingSerializer();
    }

    @Override
    public @NotNull String getImplementationName() {
        return "fabric";
    }

}
