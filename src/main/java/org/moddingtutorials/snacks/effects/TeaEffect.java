package org.moddingtutorials.snacks.effects;

import java.util.Collection;

import net.minecraft.entity.LivingEntity;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectType;
import net.minecraft.util.DamageSource;

public class TeaEffect extends Effect{

    public TeaEffect() {
        super(EffectType.BENEFICIAL, 0x82ffa8);
        //TODO Auto-generated constructor stub
    }
    
    @Override
    public void applyEffectTick(LivingEntity player, int level) {
        // player.getActiveEffects(); -> EffectInstance
        Collection<EffectInstance> effectlist = player.getActiveEffects();
        for (EffectInstance item : effectlist){

            if (item.getEffect().getCategory() == EffectType.HARMFUL){
                player.removeEffect(item.getEffect());
            }
            
        }

        // player.removeEffect(effect);

            
    }
    @Override
    public boolean isDurationEffectTick(int duration, int level) {
        return duration % 5 == 0;
    }
}
