package io.github.morapowered.mlib.adventure

import io.github.eupedroosouza.mlib.adventure.ModAdventureUtil
import net.kyori.adventure.audience.Audience
import net.kyori.adventure.text.Component
import net.minecraft.world.entity.player.Player

fun text(text: String) = ModAdventureUtil.text(text)
fun text(component: Component) = ModAdventureUtil.text(component)

fun Player.sendRichMessage(message: String) {
    (this as Audience).sendMessage(miniMessage(message))
}