package org.moddingtutorials.snacks.events;

import org.moddingtutorials.snacks.ModMain;

import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.event.entity.player.PlayerEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid=ModMain.MOD_ID)
public class GiveBookOnJoin {
    @SubscribeEvent
    public static void onjoin(PlayerEvent.PlayerLoggedInEvent event){
        CompoundNBT playerdata = event.getPlayer().getPersistentData();
        if (playerdata.contains("Has gotten book")){
            return;
        }
        playerdata.putBoolean("Has gotten book", true);
        Item givebook = ForgeRegistries.ITEMS.getValue(new ResourceLocation("patchouli", "guide_book"));
        ItemStack bookstack = new ItemStack(givebook);
        CompoundNBT data = bookstack.getOrCreateTag();
        data.putString("patchouli:book", "snacks:snacks_book");
        bookstack.setTag(data);
        event.getPlayer().addItem(bookstack);
    }
}
