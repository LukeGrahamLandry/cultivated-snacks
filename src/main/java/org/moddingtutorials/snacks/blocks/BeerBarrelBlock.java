package org.moddingtutorials.snacks.blocks;

import org.moddingtutorials.snacks.init.ItemInit;
import org.moddingtutorials.snacks.init.TileEntityInit;
import org.moddingtutorials.tiles.BeerBarrelTile;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.tags.ITag;
import net.minecraft.tags.ItemTags;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ActionResultType;
import net.minecraft.util.Hand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockRayTraceResult;
import net.minecraft.world.IBlockReader;
import net.minecraft.world.World;

public class BeerBarrelBlock extends Block {
    public static final ITag.INamedTag<Item> FERMENTABLE = ItemTags.bind("snacks:fermentable");
    public BeerBarrelBlock(Properties props) {
        super(props);
        //TODO Auto-generated constructor stub
    }
    @Override
    public boolean hasTileEntity(BlockState state) {
        return true;
    }

    @Override
    public TileEntity createTileEntity(BlockState state, IBlockReader world) {
        return TileEntityInit.HOPSBARREL.get().create();
    }
    @Override
    public ActionResultType use(BlockState blockstate, World world, BlockPos blockpos, PlayerEntity player, Hand hand, BlockRayTraceResult ray) {
        ItemStack itemstack = player.getItemInHand(hand);
        Item item = itemstack.getItem();
        if (FERMENTABLE.contains(item)) {
            addHops(world, blockpos);
            itemstack.shrink(1);
        }
        if (ItemInit.MUG.get() == item) {
            if (hasBeer(world, blockpos)) {
                player.setItemInHand(hand, new ItemStack(ItemInit.BEER.get()));
                takeOutBeer(world, blockpos);
            }
        }

        
        return super.use(blockstate, world, blockpos, player, hand, ray);
    }

    private void addHops(World world, BlockPos blockPos){
        BeerBarrelTile tile = (BeerBarrelTile) world.getBlockEntity(blockPos);
        tile.addHops();

    }

    private void takeOutBeer(World world, BlockPos blockPos){
        BeerBarrelTile tile = (BeerBarrelTile) world.getBlockEntity(blockPos);
        tile.takeOutBeer();
    }

    private boolean hasBeer(World world, BlockPos blockPos){
        BeerBarrelTile tile = (BeerBarrelTile) world.getBlockEntity(blockPos);
        return tile.hasBeer();
    }
    
}
