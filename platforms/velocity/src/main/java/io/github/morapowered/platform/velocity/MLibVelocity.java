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

package io.github.morapowered.platform.velocity;

import com.google.inject.Inject;
import com.velocitypowered.api.event.Subscribe;
import com.velocitypowered.api.event.proxy.ProxyInitializeEvent;
import com.velocitypowered.api.plugin.Plugin;
import com.velocitypowered.api.plugin.annotation.DataDirectory;
import com.velocitypowered.api.proxy.ProxyServer;
import io.github.morapowered.depencymanager.DependencyManager;
import io.github.morapowered.platform.Platform;
import io.github.morapowered.platform.provider.PlatformProvider;
import io.github.morapowered.platform.util.BuildParameters;
import io.github.morapowered.platform.velocity.classpath.VelocityClassPathAppender;
import io.github.morapowered.platform.velocity.dependencies.VelocityDependencies;
import lombok.Getter;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.nio.file.Path;

@Plugin(id = "mlib", name = "mlib", version = BuildParameters.VERSION, authors = {"Pedro Souza"})
public class MLibVelocity implements ProxyPlatform {

    private final @Getter Logger logger;
    private final @Getter ProxyServer server;
    private final Path workDir;

    @Inject
    public MLibVelocity(Logger logger, ProxyServer server, @DataDirectory Path workDir) {
        this.logger = logger;
        this.server = server;
        this.workDir = workDir;

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

    @Subscribe
    public void onProxyInitialize(ProxyInitializeEvent event) {
        VelocityClassPathAppender classPathAppender = new VelocityClassPathAppender();
        logger.info("mlib (version: {}, branch: {}, build: {})", BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
        DependencyManager dependencyManager = DependencyManager.builder()
                .withMavenCentral()
                .dir(workDir.resolve("libraries/"))
                .build();
        try {
            dependencyManager.loadDependencies(VelocityDependencies.DEPENDENCIES);
            dependencyManager.apply(classPathAppender);
            logger.info("Loaded {} dependencies.", VelocityDependencies.DEPENDENCIES.size());
        } catch (Exception ex) {
            throw new IllegalStateException("Fail resolving dependencies: " + ex.getMessage(), ex);
        }
    }

    @Override
    public @NotNull String getImplementationName() {
        return "proxy";
    }
}
