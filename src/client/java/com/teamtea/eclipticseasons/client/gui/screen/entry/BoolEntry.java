package com.teamtea.eclipticseasons.client.gui.screen.entry;

import com.teamtea.eclipticseasons.client.gui.screen.ESModConfigScreen;
import com.teamtea.eclipticseasons.client.gui.screen.entry.base.ConfigEntry;
import net.minecraft.client.gui.components.AbstractWidget;
import net.minecraft.client.gui.components.CycleButton;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BoolEntry extends ConfigEntry.SpecEntry<Boolean> {
    public BoolEntry(ModConfigSpec.BooleanValue spec) {
        super(spec);
    }

    @Override
    public AbstractWidget buildModConfigSpec(ESModConfigScreen screen, int x, int y, int width) {
        CycleButton<Boolean> booleanCycleButton = CycleButton.onOffBuilder(spec.get())
                .create(x, y, width, 20, this.label, (button, value) -> spec.set(value));
        return booleanCycleButton;
    }

    @Override
    public int getPosition() {
        return 0;
    }
}
