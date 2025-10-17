package io.github.morapowered.mlib.platform.internal;

import io.github.morapowered.mlib.platform.Platform;
import org.jetbrains.annotations.ApiStatus;

@ApiStatus.Internal
public class InternalPlatform {

    private static Platform platform;

    public static Platform getPlatform() {
        if (platform == null) {
            throw new IllegalStateException("platform not initialized yet");
        }
        return platform;
    }

}
