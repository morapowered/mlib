package io.github.morapowered.mlib.platform;

import io.github.morapowered.mlib.platform.internal.InternalPlatform;
import io.github.morapowered.mlib.util.BuildParameters;
import org.jetbrains.annotations.ApiStatus;

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
        getLogger().info("mlib (platform: {}, version: {}, branch: {}, build: {})", getName(), BuildParameters.VERSION, BuildParameters.BRANCH, BuildParameters.BUILD);
    }

    protected void shutdown() {
        getLogger().info("mlib shutdown");
    }

}
