package org.moddingtutorials.snacks.init;

import net.minecraftforge.eventbus.api.IEventBus;
import org.moddingtutorials.snacks.ModMain;
import net.minecraft.item.*;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Supplier;

public class ItemInit {
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, ModMain.MOD_ID);

    public static final List<Supplier<Item>> basicItems = new ArrayList<>();

    public static void register(IEventBus modEventBus) {
        ITEMS.register(modEventBus);
    }

    public static void createFood(String name, Food.Builder food){
        RegistryObject<Item> result = ITEMS.register(name, () -> new Item(new Item.Properties().tab(ModCreativeTab.instance).food(food.build())));
        basicItems.add(result);
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
