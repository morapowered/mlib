package io.github.morapowered.mlib.platform;

import io.github.morapowered.mlib.platform.internal.InternalPlatform;
import org.jetbrains.annotations.NotNull;
import org.slf4j.Logger;

public interface Platform {

    static @NotNull Platform get() {
        return InternalPlatform.getPlatform();
    }

    @NotNull String getName();

    @NotNull Logger getLogger();

}
