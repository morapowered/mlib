package io.github.morapowered.mlib.adventure;

import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

public class AdventureUtil {

    public static Component miniMessage(String text) {
        return MiniMessage.miniMessage().deserialize(text);
    }

}
