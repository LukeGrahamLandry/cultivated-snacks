package org.moddingtutorials.snacks.client;

import org.moddingtutorials.snacks.ModMain;
import org.moddingtutorials.snacks.init.ItemInit;
import org.moddingtutorials.snacks.items.SkittleItem;

import net.minecraft.potion.EffectInstance;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.client.event.ColorHandlerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;

@Mod.EventBusSubscriber(modid = ModMain.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)

public class ClientSetup {
    @SubscribeEvent
    public static void registerItemColors(ColorHandlerEvent.Item event){
        event.getItemColors().register((stack, layer) -> {
            EffectInstance effect = SkittleItem.readEffect(stack);
            if (effect == null){
                return 0xffffff;
            }
            return effect.getEffect().getColor();
        }, ItemInit.SKITTLE.get());
    }
}
