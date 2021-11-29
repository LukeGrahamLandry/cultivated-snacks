package org.moddingtutorials.snacks.items;

import java.util.List;

import org.moddingtutorials.snacks.init.ItemInit;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.registries.ForgeRegistries;

public class SkittleItem extends Item {
    public SkittleItem(Item.Properties props){
        super(props.food(new Food.Builder().build()));
    }

    public ActionResult<ItemStack> use(World world, PlayerEntity player, Hand hand) {
        ItemStack skittleStack = player.getItemInHand(hand);
        Hand otherHand;
            if (hand == Hand.MAIN_HAND) {
                otherHand = Hand.OFF_HAND;
            }
            else {
                otherHand = Hand.MAIN_HAND;
            }
        ItemStack potionStack = player.getItemInHand(otherHand);
            if (potionStack.getItem() == Items.POTION){
                Potion potion = PotionUtils.getPotion(potionStack);
                List<EffectInstance> effects = potion.getEffects();

                if (effects.size() == 0) {
                    return super.use(world, player, hand);
                }
                else {
                    EffectInstance effect = effects.get(0);
                    writeEffect(skittleStack, effect);
                    player.setItemInHand(otherHand, new ItemStack(Items.GLASS_BOTTLE));
                }

                return ActionResult.sidedSuccess(skittleStack, world.isClientSide());
            } else {
                return super.use(world, player, hand);
            }
     }    
    public ItemStack finishUsingItem(ItemStack item, World world, LivingEntity entity) {
        if (readEffect(item) != null) {  
        entity.addEffect(readEffect(item));
        }
        return entity.eat(world, item);
     }

     private static void writeEffect(ItemStack stack, EffectInstance effect){
        CompoundNBT data = stack.getOrCreateTag();
        data.putInt("length", effect.getDuration());
        data.putInt("level", effect.getAmplifier());
        data.putString("name", effect.getEffect().getRegistryName().toString());
        stack.setTag(data);
     }

     private static EffectInstance readEffect(ItemStack stack){
        CompoundNBT data = stack.getOrCreateTag();
        if (data.contains("level")){
            String name = data.getString("name");
            int level = data.getInt("level");
            int length = data.getInt("length");
            Effect effect = ForgeRegistries.POTIONS.getValue(new ResourceLocation(name));
            return new EffectInstance(effect, length, level);
        } else {
            return null;
        }
    }
}
