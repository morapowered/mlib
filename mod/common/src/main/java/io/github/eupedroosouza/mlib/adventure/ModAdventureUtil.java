package io.github.eupedroosouza.mlib.adventure;

import io.github.morapowered.mlib.adventure.AdventureUtil;
import io.github.morapowered.mlib.platform.ModPlatform;
import net.kyori.adventure.text.format.TextDecoration;
import net.minecraft.network.chat.Component;

public class ModAdventureUtil extends AdventureUtil {

    public static Component text(String text) {
        return text(miniMessage(text));
    }

    public static Component text(net.kyori.adventure.text.Component component) {
        return ModPlatform.platform().getAudiences().nonWrappingSerializer().serialize(component.applyFallbackStyle(style ->
                style.decoration(TextDecoration.ITALIC, false)));
    }

}
