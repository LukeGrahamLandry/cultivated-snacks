package org.moddingtutorials.snacks.items;

import org.moddingtutorials.snacks.init.ItemInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class FullMugItem extends Item {
    public FullMugItem(Item.Properties props){
        super(props);
    }
    public ItemStack finishUsingItem(ItemStack item, World world, LivingEntity entity) {
        entity.eat(world, item);
        return new ItemStack(ItemInit.MUG.get());
     }
  
}
