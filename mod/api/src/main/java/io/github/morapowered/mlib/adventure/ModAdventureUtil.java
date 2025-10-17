package io.github.morapowered.mlib.adventure;

import net.kyori.adventure.platform.fabric.FabricAudiences;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.network.chat.Component;

public class ModAdventureUtil extends AdventureUtil {

    public static Component text(String text) {
        return text(miniMessage(text));
    }

    public static Component text(net.kyori.adventure.text.Component component) {
        return FabricAudiences.nonWrappingSerializer().serialize(component.applyFallbackStyle(style ->
                style.decoration(TextDecoration.ITALIC, false)));
    }

}
