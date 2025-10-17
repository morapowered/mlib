package io.github.morapowered.mlib.inventory.pages.multi;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.button.PlaceholderButton;
import ca.landonjw.gooeylibs2.api.button.linked.LinkType;
import ca.landonjw.gooeylibs2.api.button.linked.LinkedPageButton;
import ca.landonjw.gooeylibs2.api.helpers.PaginationHelper;
import ca.landonjw.gooeylibs2.api.page.LinkedPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.mlib.adventure.ModAdventureUtil;
import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.types.CommandItem;
import io.github.morapowered.mlib.inventory.item.types.PlayerHeadItem;
import io.github.morapowered.mlib.inventory.pages.multi.config.MultiPageInventoryConfiguration;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public abstract class MultiPageInventory {

    private final MultiPageInventoryConfiguration configuration;

    private final ChestTemplate.Builder builder;
    private final ChestTemplate template;
    private LinkedPage firstPage;

    private final List<Button> buttons = new ArrayList<>();

    protected MultiPageInventory(MultiPageInventoryConfiguration configuration) {
        this.configuration = configuration;
        this.builder = ChestTemplate.builder(configuration.rows);

        for (int slot = configuration.start; slot <= configuration.end; slot++) {
            if (configuration.noItens.contains(slot)) {
                continue;
            }
            PlaceholderButton button = new PlaceholderButton();
            builder.set(slot, button);
        }

        LinkedPageButton previous = LinkedPageButton.builder()
                .display(configuration.items.previousPage.createStack())
                .linkType(LinkType.Previous)
                .build();
        configuration.items.previousPage.getSlots().forEach(slot -> builder.set(slot, previous));

        LinkedPageButton next = LinkedPageButton.builder()
                .display(configuration.items.nextPage.createStack())
                .linkType(LinkType.Next)
                .build();
        configuration.items.nextPage.getSlots().forEach(slot -> builder.set(slot, next));

        configuration.staticItems.forEach((id, inventoryItem) -> {
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
                LinkedPage.builder().title(ModAdventureUtil.text(configuration.title)));
    }

    public void open(ServerPlayer player) {
        if (firstPage == null) {
            throw new IllegalStateException("Pagination not created");
        }
        UIManager.openUIForcefully(player, firstPage);
    }

    private void onClick(String id, InventoryItem item, ButtonAction action) {
        if (item instanceof CommandItem commandItem) {
            MinecraftServer server = action.getPlayer().getServer();
            if (server != null) {
                server.getCommands().performPrefixedCommand(server.createCommandSourceStack(),
                        commandItem.getCommand().replace("{clicker}", action.getPlayer().getName().getString()));
            }
            return;
        }
        onStaticClick(id, item, action);
    }

    protected abstract void onStaticClick(String id, InventoryItem item, ButtonAction action);

    public ChestTemplate.Builder getBuilder() {
        return builder;
    }

    public ChestTemplate getTemplate() {
        return template;
    }

    public LinkedPage getFirstPage() {
        return firstPage;
    }
}
