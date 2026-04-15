package com.teamtea.eclipticseasons.api.event;


import com.teamtea.eclipticseasons.api.constant.solar.SolarTerm;
import lombok.Builder;
import lombok.Data;
import lombok.Getter;
import net.minecraft.world.level.Level;


/**
 * The event is fired on the {@link com.teamtea.eclipticseasons.common.hook.ESEventHook}
 **/
@Builder
@Getter
public class SolarTermChangeEvent  implements IESEvent {
    private final SolarTerm oldSolarTerm;
    private final SolarTerm newSolarTerm;
    private final Level level;
    private final int solarDays;
}
