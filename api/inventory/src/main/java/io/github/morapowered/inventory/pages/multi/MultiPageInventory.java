/*
 * This file is licensed under the MIT License.
 *
 * Copyright (c) 2025 Pedro Souza
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

package io.github.morapowered.inventory.pages.multi;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.inventory.item.SimpleItem;
import io.github.morapowered.inventory.item.types.CommandItemImpl;
import io.github.morapowered.inventory.pages.multi.config.MultiPageInventoryConfiguration;
import io.github.morapowered.inventory.util.AdventureUtil;
import lombok.Getter;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

@Getter
public abstract class MultiPageInventory {

    private final MultiPageInventoryConfiguration configuration;

    private final ChestTemplate.Builder builder;
    private final ChestTemplate template;
    private LinkedPage firstPage;

    private final List<Button> buttons = new ArrayList<>();

    protected MultiPageInventory(MultiPageInventoryConfiguration configuration) {
        this.configuration = configuration;
        this.builder = ChestTemplate.builder(configuration.getRows());

        for (Integer slot : configuration.getSlots()) {
            PlaceholderButton button = new PlaceholderButton();
            builder.set(slot, button);
        }

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(configuration.getPreviousItem().createStack())
                .linkType(LinkType.Previous)
                .build();
        configuration.getPreviousItem().getSlots().forEach(slot -> builder.set(slot, previous));

        LinkedPageButton next = LinkedPageButton.builder()
                .display(configuration.getNextItem().createStack())
                .linkType(LinkType.Next)
                .build();
        configuration.getNextItem().getSlots().forEach(slot -> builder.set(slot, next));

        configuration.getStaticItems().forEach((id, inventoryItem) -> {
            inventoryItem.createSimpleButton(builder, buttonAction -> {
                onClick(id, inventoryItem, buttonAction);
            }, itemStack -> {
            }, string -> string, string -> string);
        });
        this.template = builder.build();
    }

    public void addButton(Button... buttons) {
        this.buttons.addAll(Arrays.asList(buttons));
    }

    private void createPagination() {
        this.firstPage = PaginationHelper.createPagesFromPlaceholders(template, buttons,
                LinkedPage.builder().title(AdventureUtil.text(configuration.getTitle())));
    }

    public void open(ServerPlayer player) {
        if (firstPage == null) {
            throw new IllegalStateException("Pagination not created");
        }
        UIManager.openUIForcefully(player, firstPage);
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

    protected abstract void onStaticClick(String id, SimpleItem item, ButtonAction action);
}
