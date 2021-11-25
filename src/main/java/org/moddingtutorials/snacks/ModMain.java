package org.moddingtutorials.snacks;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.crafting.Ingredient;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.brewing.BrewingRecipeRegistry;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLCommonSetupEvent;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;
import net.minecraftforge.registries.ForgeRegistries;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.moddingtutorials.snacks.init.BlockInit;
import org.moddingtutorials.snacks.init.EffectInit;
import org.moddingtutorials.snacks.init.ItemInit;
import org.moddingtutorials.snacks.init.TileEntityInit;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(ModMain.MOD_ID)
public class ModMain {
    public static final String MOD_ID = "snacks";
    public static final Logger LOGGER = LogManager.getLogger();

    public ModMain() {
        final IEventBus modEventBus = FMLJavaModLoadingContext.get().getModEventBus();
        modEventBus.addListener(this::setup);

        ItemInit.register(modEventBus);
        EffectInit.POTIONS.register(modEventBus);

        MinecraftForge.EVENT_BUS.register(this);

        BlockInit.BLOCKS.register(modEventBus);
        TileEntityInit.TILE_ENTITY_TYPES.register(modEventBus);
    }

    private void setup(FMLCommonSetupEvent event){
        LOGGER.debug("the setup");
        Item teaLeaf = ForgeRegistries.ITEMS.getValue(new ResourceLocation("thermal", "tea"));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(ItemInit.WATER_MUG.get()), Ingredient.of(teaLeaf), new ItemStack(ItemInit.TEA_MUG.get()));
        BrewingRecipeRegistry.addRecipe(Ingredient.of(ItemInit.WATER_MUG.get()), Ingredient.of(ItemInit.COFFEE_GRINDS.get()), new ItemStack(ItemInit.COFFEE_MUG.get()));
    }

}
