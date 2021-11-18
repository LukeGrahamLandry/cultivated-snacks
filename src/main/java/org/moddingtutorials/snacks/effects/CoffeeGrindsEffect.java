package org.moddingtutorials.snacks.effects;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class CoffeeGrindsEffect extends Effect{

    public CoffeeGrindsEffect() {
        super(EffectType.HARMFUL, 0x5c3421);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public void applyEffectTick(LivingEntity player, int level) {
        player.setAirSupply(player.getAirSupply() - 30);
        if (player.getAirSupply() < -20){
            player.setAirSupply(0);
            player.hurt(DamageSource.DROWN, 2.0F);
        }
            
    }
    @Override
    public boolean isDurationEffectTick(int duration, int level) {
        return duration % 5 == 0;
    }
}
