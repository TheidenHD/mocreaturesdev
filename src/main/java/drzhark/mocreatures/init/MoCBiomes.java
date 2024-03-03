/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.ObjectHolder;

@ObjectHolder(MoCConstants.MOD_ID)
public class MoCBiomes {

//    public static Biome wyvernIsles = new MoCBiomeWyvernIsles(new BiomeProperties("Wyvern Isles") //TODO TheidenHD
//            .setBaseHeight(1.0F)
//            .setHeightVariation(0.5F)
//            .setTemperature(0.25F)
//            .setWaterColor(0x775757));
//
//    public static Biome wyvernDesertIsles = new MoCBiomeWyvernIslesDesert(new BiomeProperties("Shifting Sands")
//            .setBaseHeight(0.3F)
//            .setHeightVariation(0.2F)
//            .setTemperature(0.4F)
//            .setWaterColor(0x775757));

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class RegistrationHandler {

//        @SubscribeEvent //TODO TheidenHD
//        public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
//            final IForgeRegistry<Biome> registry = event.getRegistry();
//            registerBiome(registry, wyvernIsles, "wyvern_isles", MoCEntities.WYVERN_LAIR, BiomeDictionary.Type.VOID, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.COLD);
//            registerBiome(registry, wyvernDesertIsles, "wyvern_desert_isles", MoCEntities.WYVERN_LAIR, BiomeDictionary.Type.VOID, BiomeDictionary.Type.MAGICAL, BiomeDictionary.Type.COLD, BiomeDictionary.Type.SANDY);
//        }
//
//        private static <T extends Biome> void registerBiome(final IForgeRegistry<Biome> registry, final T biome, final String biomeName, final BiomeDictionary.Type... types) {
//            registry.register(biome.setRegistryName(MoCConstants.MOD_ID, biomeName));
//            BiomeDictionary.addTypes(biome, types);
//        }
    }
}
