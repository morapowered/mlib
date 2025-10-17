package io.github.morapowered.mlib.inventory.pages.confirm;

import ca.landonjw.gooeylibs2.api.button.ButtonAction;
import io.github.morapowered.mlib.inventory.pages.confirm.config.ConfirmInventoryConfiguration;
import io.github.morapowered.mlib.inventory.pages.simple.SimplePageInventory;

public abstract class ConfirmInventory extends SimplePageInventory {

    private final ConfirmInventoryConfiguration configuration;

    protected ConfirmInventory(ConfirmInventoryConfiguration configuration) {
        super(configuration);
        this.configuration = configuration;

        configuration.items.no.createSimpleButton(getTemplate(), this::onClickNo, itemStack -> {
        }, string -> string, string -> string);
        configuration.items.yes.createSimpleButton(getTemplate(), this::onClickYes, itemStack -> {
        }, string -> string, string -> string);

    }

    public abstract void onClickNo(ButtonAction action);

    public abstract void onClickYes(ButtonAction action);

}
