/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.world.item.Item;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.datafix.IFixableData;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.ForgeRegistries;

import java.util.HashMap;
import java.util.Map;

public class ItemIDFixer implements IFixableData {
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = new HashMap<>();

    static {
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "haystack"), new ResourceLocation("minecraft", "hay_block"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "horsesaddle"), new ResourceLocation("minecraft", "saddle"));
        ITEM_NAME_MAPPINGS.put(new ResourceLocation(MoCConstants.MOD_ID, "scrollofowner"), new ResourceLocation(MoCConstants.MOD_ID, "scrolloffreedom"));
    }

    public ItemIDFixer() {
        MinecraftForge.EVENT_BUS.register(this);
    }

    @Override
    public int getFixVersion() {
        return 1;
    }

    @Override
    public CompoundTag fixTagCompound(CompoundTag compound) {
        return compound;
    }

    @SubscribeEvent
    public void missingItemMapping(RegistryEvent.MissingMappings<Item> event) {
        for (RegistryEvent.MissingMappings.Mapping<Item> entry : event.getAllMappings()) {
            ResourceLocation oldName = entry.key;
            ResourceLocation newName = ITEM_NAME_MAPPINGS.get(oldName);
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null) {
                    entry.remap(newItem);
                }
            }
        }
    }
}
