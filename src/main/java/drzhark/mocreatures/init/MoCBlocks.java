/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.*;
import drzhark.mocreatures.block.MoCBlockSapling.EnumWoodType;
import net.minecraft.block.Block;
import net.minecraft.block.material.MapColor;
import net.minecraft.client.renderer.block.model.ModelResourceLocation;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
@GameRegistry.ObjectHolder(MoCConstants.MOD_ID)
public class MoCBlocks {

    @GameRegistry.ObjectHolder("ancient_ore")
    public static MoCBlockOre ancientOre;
    @GameRegistry.ObjectHolder("ancient_silver_block")
    public static Block ancientSilverBlock;
    @GameRegistry.ObjectHolder("carved_silver_sandstone")
    public static Block carvedSilverSandstone;
    @GameRegistry.ObjectHolder("cobbled_wyvstone")
    public static Block cobbledWyvstone;
    @GameRegistry.ObjectHolder("cobbled_deep_wyvstone")
    public static Block cobbledDeepWyvstone;
    @GameRegistry.ObjectHolder("deep_wyvstone")
    public static Block deepWyvstone;
    @GameRegistry.ObjectHolder("firestone")
    public static Block firestone;
    @GameRegistry.ObjectHolder("gleaming_glass")
    public static Block gleamingGlass;
    @GameRegistry.ObjectHolder("mossy_cobbled_wyvstone")
    public static Block mossyCobbledWyvstone;
    @GameRegistry.ObjectHolder("mossy_cobbled_deep_wyvstone")
    public static Block mossyCobbledDeepWyvstone;
    @GameRegistry.ObjectHolder("silver_sand")
    public static Block silverSand;
    @GameRegistry.ObjectHolder("silver_sandstone")
    public static Block silverSandstone;
    @GameRegistry.ObjectHolder("smooth_silver_sandstone")
    public static Block smoothSilverSandstone;
    @GameRegistry.ObjectHolder("tall_wyvgrass")
    public static Block tallWyvgrass;
    @GameRegistry.ObjectHolder("wyvern_diamond_ore")
    public static MoCBlockOre wyvernDiamondOre;
    @GameRegistry.ObjectHolder("wyvern_emerald_ore")
    public static MoCBlockOre wyvernEmeraldOre;
    @GameRegistry.ObjectHolder("wyvern_gold_ore")
    public static MoCBlockOre wyvernGoldOre;
    @GameRegistry.ObjectHolder("wyvern_iron_ore")
    public static MoCBlockOre wyvernIronOre;
    @GameRegistry.ObjectHolder("wyvern_lapis_ore")
    public static MoCBlockOre wyvernLapisOre;
    @GameRegistry.ObjectHolder("wyvern_nest_block")
    public static MoCBlockNest wyvernNestBlock;
    @GameRegistry.ObjectHolder("wyvstone")
    public static Block wyvstone;
    @GameRegistry.ObjectHolder("wyvgrass")
    public static Block wyvgrass;
    @GameRegistry.ObjectHolder("wyvdirt")
    public static Block wyvdirt;
    @GameRegistry.ObjectHolder("wyvwood_leaves")
    public static Block wyvwoodLeaves;
    @GameRegistry.ObjectHolder("wyvwood_sapling")
    public static Block wyvwoodSapling;
    @GameRegistry.ObjectHolder("wyvwood_log")
    public static Block wyvwoodLog;
    @GameRegistry.ObjectHolder("stripped_wyvwood_log")
    public static Block strippedwyvwoodLog;
    @GameRegistry.ObjectHolder("wyvwood_planks")
    public static Block wyvwoodPlanks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new MoCBlockMetal(MapColor.IRON), "ancient_silver_block").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_wyvstone").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_deep_wyvstone").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "wyvstone").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "deep_wyvstone").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_wyvstone").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_deep_wyvstone").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockGlass(), "gleaming_glass").setHardness(0.4F),
                setup(new MoCBlockSand(MapColor.CLAY), "silver_sand").setHardness(0.6F),
                setup(new MoCBlockRock(MapColor.CLAY), "silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "carved_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "smooth_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockOre(MapColor.STONE), "ancient_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockFirestone(MapColor.ADOBE), "firestone").setHardness(3.0F).setLightLevel(0.5F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_diamond_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_emerald_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_gold_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_iron_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_lapis_ore").setHardness(1.5F).setResistance(5.0F),
                setup(new MoCBlockGrass(MapColor.BLUE_STAINED_HARDENED_CLAY), "wyvgrass").setHardness(0.7F),
                setup(new MoCBlockDirt(MapColor.DIRT), "wyvdirt").setHardness(0.6F),
                setup(new MoCBlockLeaf(MapColor.DIAMOND, true, 100), "wyvwood_leaves").setHardness(0.2F).setLightOpacity(1),
                setup(new MoCBlockSapling(EnumWoodType.WYVWOOD, MapColor.FOLIAGE, true), "wyvwood_sapling").setHardness(0.0F),
                setup(new MoCBlockLog(MapColor.CYAN_STAINED_HARDENED_CLAY, true), "wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockLog(MapColor.CYAN_STAINED_HARDENED_CLAY, true), "stripped_wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockTallGrass(MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, false), "tall_wyvgrass").setHardness(0.0F),
                setup(new MoCBlockPlanks(MapColor.DIAMOND, true), "wyvwood_planks").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockNest(), "wyvern_nest_block").setHardness(0.5F)
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(MoCConstants.MOD_ID))
                .forEach(block -> registry.register(setup(new ItemBlock(block), block.getRegistryName())));
    }

    @OnlyIn(Dist.CLIENT)
    @SubscribeEvent
    public static void registerModels(ModelRegistryEvent event) {
        for (Item item : ForgeRegistries.ITEMS.getValues()) {
            if (item.getRegistryName().getNamespace().equals(MoCConstants.MOD_ID)) {
                ModelLoader.setCustomModelResourceLocation(item, 0, new ModelResourceLocation(item.getRegistryName(), "normal"));
            }
        }
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, String name) {
        return setup(entry, new ResourceLocation(MoCConstants.MOD_ID, name));
    }

    @Nonnull
    public static <T extends IForgeRegistryEntry<T>> T setup(T entry, ResourceLocation registryName) {
        Preconditions.checkNotNull(entry, "Entry to setup must not be null!");
        Preconditions.checkNotNull(registryName, "Registry name to assign must not be null!");
        entry.setRegistryName(registryName);
        if (entry instanceof Block) {
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath()).setCreativeTab(MoCreatures.tabMoC);
        }
        return entry;
    }
}
