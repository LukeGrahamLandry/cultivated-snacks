package org.moddingtutorials.snacks.tiles;

import org.moddingtutorials.snacks.init.TileEntityInit;

import net.minecraft.tileentity.ITickableTileEntity;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.tileentity.TileEntityType;

public class BeerBarrelTile extends TileEntity implements ITickableTileEntity{
    int hops = 0;
    int beer = 0;
    int progress = 0;
    @Override
    public void tick() {
       if (hops > 0){
           progress += 1;
       }
        if (progress >= 20*10) {
            beer += 1;
            progress = 0;
            hops -= 1;
        }
    }
    public BeerBarrelTile() {
        super(TileEntityInit.HOPSBARREL.get());
        //TODO Auto-generated constructor stub
    }
    public void addHops() {
        hops += 1;
    }
    public void takeOutBeer() {
        beer -= 1;
    }
    public boolean hasBeer() {
        return beer > 0;
    }
}
