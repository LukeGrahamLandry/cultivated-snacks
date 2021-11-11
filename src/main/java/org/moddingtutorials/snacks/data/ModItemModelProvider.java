package org.moddingtutorials.snacks.data;

import net.minecraft.data.DataGenerator;
import net.minecraft.data.IDataProvider;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.client.model.generators.ItemModelProvider;
import net.minecraftforge.client.model.generators.ModelFile;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.moddingtutorials.snacks.ModMain;
import org.moddingtutorials.snacks.init.ItemInit;

import java.util.function.Supplier;

public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(DataGenerator gen, ExistingFileHelper existingFileHelper) {
        super(gen, ModMain.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
        for (Supplier<Item> item : ItemInit.basicItems){
            getBuilder(item.get().getRegistryName().toString())
                    .parent(new ModelFile.UncheckedModelFile("item/generated"))
                    .texture("layer0", new ResourceLocation(ModMain.MOD_ID, "item/" + item.get().getRegistryName().getPath()));
        }
    }
}