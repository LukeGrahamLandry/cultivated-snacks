package org.moddingtutorials.snacks.init;

import org.moddingtutorials.snacks.ModMain;
import org.moddingtutorials.snacks.tiles.BeerBarrelTile;

import net.minecraft.tileentity.TileEntityType;
import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class TileEntityInit {
    public static final DeferredRegister<TileEntityType<?>> TILE_ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.TILE_ENTITIES, ModMain.MOD_ID);
    public static final RegistryObject<TileEntityType<BeerBarrelTile>> HOPSBARREL = TILE_ENTITY_TYPES.register("mob_slayer",
            () -> TileEntityType.Builder.of(BeerBarrelTile::new, BlockInit.HOPS_BARREL.get()).build(null));
    }

