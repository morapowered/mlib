package io.github.morapowered.mlib.inventory.pages.simple;

import ca.landonjw.gooeylibs2.api.UIManager;
import ca.landonjw.gooeylibs2.api.button.Button;
import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import ca.landonjw.gooeylibs2.api.page.GooeyPage;
import ca.landonjw.gooeylibs2.api.template.types.ChestTemplate;
import io.github.morapowered.mlib.adventure.ModAdventureUtil;
import io.github.morapowered.mlib.inventory.item.InventoryItem;
import io.github.morapowered.mlib.inventory.item.types.CommandItem;
import io.github.morapowered.mlib.inventory.pages.simple.config.SimplePageInventoryConfiguration;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerPlayer;

import java.util.Collection;

public abstract class SimplePageInventory {

    private final SimplePageInventoryConfiguration configuration;

    private final ChestTemplate template;
    private GooeyPage page;

    protected SimplePageInventory(SimplePageInventoryConfiguration configuration) {
        this.configuration = configuration;

        ChestTemplate.Builder builder = ChestTemplate.builder(configuration.rows);

        configuration.staticItems.forEach((id, inventoryItem) -> {
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
                .title(ModAdventureUtil.text(configuration.title))
                .build();
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

    public void open(ServerPlayer player) {
        if (page == null) {
            throw new IllegalStateException("Page not created");
        }
        UIManager.openUIForcefully(player, page);
    }

    protected abstract void onStaticClick(String id, InventoryItem item, ButtonAction action);

    public ChestTemplate getTemplate() {
        return template;
    }

    public GooeyPage getPage() {
        return page;
    }
}
