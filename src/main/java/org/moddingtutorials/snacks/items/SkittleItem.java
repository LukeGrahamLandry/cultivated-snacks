package org.moddingtutorials.snacks.items;

import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import javax.annotation.Nullable;

import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;

import org.moddingtutorials.snacks.init.ItemInit;

import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.attributes.Attribute;
import net.minecraft.entity.ai.attributes.AttributeModifier;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.potion.Effect;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.EffectUtils;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionUtils;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.IFormattableTextComponent;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.util.text.StringTextComponent;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.registries.ForgeRegistries;

public class SkittleItem extends Item {
    public SkittleItem(Item.Properties props){
        super(props.food(new Food.Builder().alwaysEat().build()));
    }

    @Override
    public boolean isFoil(ItemStack p_77636_1_) {
        // TODO Auto-generated method stub
        return true;
    }

    @OnlyIn(Dist.CLIENT)
    public void appendHoverText(ItemStack itemStack, @Nullable World p_77624_2_, List<ITextComponent> p_77624_3_, ITooltipFlag p_77624_4_) {
        super.appendHoverText(itemStack, p_77624_2_, p_77624_3_, p_77624_4_);

       EffectInstance effectinstance = readEffect(itemStack);
       if (effectinstance != null){
        IFormattableTextComponent iformattabletextcomponent = new TranslationTextComponent(effectinstance.getDescriptionId());
        Effect effect = effectinstance.getEffect();
        Map<Attribute, AttributeModifier> map = effect.getAttributeModifiers();
        List<Pair<Attribute, AttributeModifier>> list1 = Lists.newArrayList();
        if (!map.isEmpty()) {
           for(Entry<Attribute, AttributeModifier> entry : map.entrySet()) {
              AttributeModifier attributemodifier = entry.getValue();
              AttributeModifier attributemodifier1 = new AttributeModifier(attributemodifier.getName(), effect.getAttributeModifierValue(effectinstance.getAmplifier(), attributemodifier), attributemodifier.getOperation());
              list1.add(new Pair<>(entry.getKey(), attributemodifier1));
           }
        }

        if (effectinstance.getAmplifier() > 0) {
           iformattabletextcomponent = new TranslationTextComponent("potion.withAmplifier", iformattabletextcomponent, new TranslationTextComponent("potion.potency." + effectinstance.getAmplifier()));

        }

        if (effectinstance.getDuration() > 20) {
           iformattabletextcomponent = new TranslationTextComponent("potion.withDuration", iformattabletextcomponent, EffectUtils.formatDuration(effectinstance, 1));
        }

        p_77624_3_.add(iformattabletextcomponent.withStyle(effect.getCategory().getTooltipFormatting()));

        if (!list1.isEmpty()) {
            p_77624_3_.add(StringTextComponent.EMPTY);
            p_77624_3_.add((new TranslationTextComponent("potion.whenDrank")).withStyle(TextFormatting.DARK_PURPLE));
   
            for(Pair<Attribute, AttributeModifier> pair : list1) {
               AttributeModifier attributemodifier2 = pair.getSecond();
               double d0 = attributemodifier2.getAmount();
               double d1;
               if (attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_BASE && attributemodifier2.getOperation() != AttributeModifier.Operation.MULTIPLY_TOTAL) {
                  d1 = attributemodifier2.getAmount();
               } else {
                  d1 = attributemodifier2.getAmount() * 100.0D;
               }
   
               if (d0 > 0.0D) {
                p_77624_3_.add((new TranslationTextComponent("attribute.modifier.plus." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.BLUE));
               } else if (d0 < 0.0D) {
                  d1 = d1 * -1.0D;
                  p_77624_3_.add((new TranslationTextComponent("attribute.modifier.take." + attributemodifier2.getOperation().toValue(), ItemStack.ATTRIBUTE_MODIFIER_FORMAT.format(d1), new TranslationTextComponent(pair.getFirst().getDescriptionId()))).withStyle(TextFormatting.RED));
               }
            }
     }
       }

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
                    ItemStack skittle = new ItemStack(ItemInit.SKITTLE.get()); 
                    writeEffect(skittle, effect);
                    skittleStack.shrink(1);
                    player.addItem(skittle);
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

     public static void writeEffect(ItemStack stack, EffectInstance effect){
        CompoundNBT data = stack.getOrCreateTag();
        data.putInt("length", effect.getDuration());
        data.putInt("level", effect.getAmplifier());
        data.putString("name", effect.getEffect().getRegistryName().toString());
        stack.setTag(data);
     }

     public static EffectInstance readEffect(ItemStack stack){
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
