/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.datafixes;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.Items;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.MissingMappingsEvent;

import java.util.Map;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class ItemIDFixer {
    private static final Map<ResourceLocation, ResourceLocation> ITEM_NAME_MAPPINGS = Map.of(
            new ResourceLocation(MoCConstants.MOD_ID, "haystack"), new ResourceLocation("minecraft", "hay_block"),
            new ResourceLocation(MoCConstants.MOD_ID, "horsesaddle"), new ResourceLocation("minecraft", "saddle"),
            new ResourceLocation(MoCConstants.MOD_ID, "scrollofowner"), new ResourceLocation(MoCConstants.MOD_ID, "scrolloffreedom")
    );

    @SubscribeEvent
    public static void onMissingMappings(MissingMappingsEvent event) {
        for (MissingMappingsEvent.Mapping<Item> mapping : event.getMappings(ForgeRegistries.Keys.ITEMS, MoCConstants.MOD_ID)) {
            ResourceLocation newName = ITEM_NAME_MAPPINGS.get(mapping.getKey());
            if (newName != null) {
                Item newItem = ForgeRegistries.ITEMS.getValue(newName);
                if (newItem != null && newItem != Items.AIR) {
                    mapping.remap(newItem);
                }
            }
        }
    }
}
