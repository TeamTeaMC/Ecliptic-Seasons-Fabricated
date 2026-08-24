package com.teamtea.eclipticseasons.client.gui.screen.entry.spec;

import com.teamtea.eclipticseasons.client.gui.screen.ESModConfigScreen;
import com.teamtea.eclipticseasons.client.gui.screen.entry.base.SpecEntry;
import net.minecraft.client.gui.components.CycleButton;
import net.minecraft.client.gui.layouts.LayoutElement;
import net.neoforged.neoforge.common.ModConfigSpec;

public class BooleanEntry extends SpecEntry<Boolean> {
    public BooleanEntry(ModConfigSpec.BooleanValue spec) {
        super(spec);
    }

    @Override
    public LayoutElement buildLayout(ESModConfigScreen screen, int x, int y, int width) {
        return buildLabelAndControl(screen, getLabel(screen), buildModConfigSpec(screen, x, y, width), width);
    }

    @Override
    public LayoutElement buildModConfigSpec(ESModConfigScreen screen, int x, int y, int width) {
        return buildResettableCycle(width, spec.get(), spec.getDefault(), spec::set,
                onValueChange -> CycleButton.onOffBuilder(spec.get()).displayOnlyValue()
                        .create(0, 0, width - 30, 20, label, onValueChange));
    }

    @Override
    public int getPosition() {
        return 0;
    }
}
