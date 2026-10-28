package drzhark.mocreatures.util;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.item.MoCItemBow;
import net.minecraft.client.renderer.item.ItemProperties;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD, value = Dist.CLIENT)
public class MoCItemProperties {

    @SubscribeEvent
    public static void onClientSetup(FMLClientSetupEvent event) {
        event.enqueueWork(() -> {
            registerBow((MoCItemBow) MoCItems.SILVER_BOW.get());
            // ... your other bows
        });
    }

    private static void registerBow(MoCItemBow bow) {
        ItemProperties.register(bow, new ResourceLocation("pull"), (stack, level, entity, seed) -> {
            if (entity == null || entity.getUseItem() != stack) {
                return 0.0F;
            }
            return (stack.getUseDuration() - entity.getUseItemRemainingTicks()) / (20.0F * bow.getDrawTimeMult());
        });
        ItemProperties.register(bow, new ResourceLocation("pulling"), (stack, level, entity, seed) ->
                entity != null && entity.isUsingItem() && entity.getUseItem() == stack ? 1.0F : 0.0F);
    }
}