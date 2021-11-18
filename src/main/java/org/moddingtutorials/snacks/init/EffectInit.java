package org.moddingtutorials.snacks.init;

import net.minecraftforge.eventbus.api.IEventBus;
import org.moddingtutorials.snacks.ModMain;
import org.moddingtutorials.snacks.effects.CoffeeGrindsEffect;
import org.moddingtutorials.snacks.effects.TeaEffect;
import org.moddingtutorials.snacks.items.FullMugItem;
import org.moddingtutorials.snacks.items.MugItem;

import net.minecraft.item.*;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class EffectInit {
    public static final DeferredRegister<Effect> POTIONS = DeferredRegister.create(ForgeRegistries.POTIONS, ModMain.MOD_ID);

    public static final Supplier<Effect> COFFEEGRINDSEFFECT = POTIONS.register("coffee_grinds", () -> new CoffeeGrindsEffect());
    public static final Supplier<Effect> TEAEFFECT = POTIONS.register("tea", () -> new TeaEffect());
}
