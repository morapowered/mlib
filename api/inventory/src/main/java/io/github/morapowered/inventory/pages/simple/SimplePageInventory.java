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

package io.github.morapowered.inventory.pages.simple;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.item.types.CommandItemImpl;
import io.github.morapowered.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import io.github.morapowered.inventory.util.AdventureUtil;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;


@Getter
public abstract class SimplePageInventory {

    private final SimplePageInventoryConfiguration configuration;

    private final ChestTemplate template;
    private GooeyPage page;

    protected SimplePageInventory(SimplePageInventoryConfiguration configuration) {
        this.configuration = configuration;

        ChestTemplate.Builder builder = ChestTemplate.builder(configuration.getRows());

        configuration.getStaticItems().forEach((id, inventoryItem) -> {
            inventoryItem.createSimpleButton(builder, action -> onClick(id, inventoryItem, action), itemStack -> {
            }, string -> string, string -> string);
        });

        this.template = builder.build();
    }

    public void addButton(int slot, Button button) {
        template.set(slot, button);
    }

    public void addButton(Button button, int... slots) {
        for (int slot : slots) {
            template.set(slot, button);
        }
    }

    public void addButton(Button button, Collection<Integer> slots) {
        slots.forEach(slot -> template.set(slot, button));
    }

    public void createPage() {
        this.page = GooeyPage.builder()
                .template(template)
                .title(AdventureUtil.text(configuration.getTitle()))
                .build();
    }

    private void onClick(String id, SimpleItem item, ButtonAction action) {
        if (item instanceof CommandItemImpl commandItem) {
            MinecraftServer server = action.getPlayer().getServer();
            if (server != null) {
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),
                        commandItem.getCommand().replace("{clicker}", action.getPlayer().getName().getString()));
            }
            return;
        }
        onStaticClick(id, item, action);
    }

    public void open(ServerPlayer player) {
        if (page == null) {
            throw new IllegalStateException("Page not created");
        }
        UIManager.openUIForcefully(player, page);
    }

    protected abstract void onStaticClick(String id, SimpleItem item, ButtonAction action);
}
