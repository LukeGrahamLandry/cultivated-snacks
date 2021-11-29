package org.moddingtutorials.snacks.init;

import net.minecraftforge.eventbus.api.IEventBus;
import org.moddingtutorials.snacks.ModMain;
import org.moddingtutorials.snacks.items.FullMugItem;
import org.moddingtutorials.snacks.items.MugItem;
import org.moddingtutorials.snacks.items.SkittleItem;

import net.minecraft.item.*;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MOD_ID);

    public static final List<Supplier<Item>> basicItems = new ArrayList<>();

    public static final Supplier<Item> WATER_MUG = create("water_mug", () -> new Item(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance)));
    public static final Supplier<Item> MUG = create("mug", () -> new MugItem(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance)));
    public static final Supplier<Item> TEA_MUG = create("tea_mug", () -> new FullMugItem(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance).food(new Food.Builder().nutrition(1).alwaysEat().effect(()->new EffectInstance(EffectInit.TEAEFFECT.get(), 16*20), 1).build())));
    public static final Supplier<Item> CLAY_MUG = create("clay_mug", () -> new Item(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance)));
    public static final Supplier<Item> COFFEE_MUG = create("coffee_mug", () -> new FullMugItem(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance).food(new Food.Builder().nutrition(1).alwaysEat().effect(()->new EffectInstance(Effects.MOVEMENT_SPEED, 16*20), 1).build())));
    public static final Supplier<Item> COFFEE_GRINDS = createFood("coffee_grinds", new Food.Builder().nutrition(1).alwaysEat().effect(()->new EffectInstance(EffectInit.COFFEEGRINDSEFFECT.get(), 16*20), 1));  
    public static final Supplier<Item> BEER = create("beer", () -> new FullMugItem(new Item.Properties().stacksTo(1).tab(ModCreativeTab.instance).food(new Food.Builder().nutrition(1).alwaysEat().effect(()->new EffectInstance(Effects.DAMAGE_BOOST, 10*20), 1).effect(()->new EffectInstance(Effects.CONFUSION, 16*20), 1).build())));
    public static final Supplier<Item> SKITTLE = create("skittle", () -> new  SkittleItem(new Item.Properties().tab(ModCreativeTab.instance)));

    public static void register(IEventBus modEventBus) {
        createFood("chicken_and_spinach", new Food.Builder().nutrition(8).saturationMod(0.8F));
        createFood("frozen_melon_pop", new Food.Builder().nutrition(1).saturationMod(0.1F).effect(()->new EffectInstance(Effects.MOVEMENT_SLOWDOWN, 15*20), 1).effect(()->new EffectInstance(Effects.FIRE_RESISTANCE, 15*20), 1));
        createFood("chicken_radish_salad", new Food.Builder().nutrition(7).saturationMod(0.8F)); 
        createFood("onion_rings", new Food.Builder().nutrition(4).saturationMod(0.4F));     
        create("sweetberry_jam", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(64).tab(ModCreativeTab.instance))); 
        create("strawberry_jam", () -> new Item(new Item.Properties().craftRemainder(Items.GLASS_BOTTLE).stacksTo(64).tab(ModCreativeTab.instance))); 


        ITEMS.register(modEventBus);
    }

    public static Supplier<Item> createFood(String name, Food.Builder food){
        RegistryObject<Item> result = ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModCreativeTab.instance).food(food.build())));
        basicItems.add(result);
        return result;
    }

    public static Supplier<Item> create(String name, Supplier<Item> item){
        RegistryObject<Item> result = ITEMS.register(name, item);
        basicItems.add(result);
        return result;
    }

    public static class ModCreativeTab extends ItemGroup {
        public static final ModCreativeTab instance = new ModCreativeTab(ItemGroup.TABS.length, "firstmod");
        private ModCreativeTab(int index, String label) {
            super(index, label);
        }

        @Override
        public ItemStack makeIcon() {
            return new ItemStack(Items.BREAD);
        }
    }
}
