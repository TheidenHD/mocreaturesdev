/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Preconditions;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.block.*;
import drzhark.mocreatures.block.MoCBlockSapling.EnumWoodType;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.material.MaterialColor;
import net.minecraft.client.renderer.model.ModelResourceLocation;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.client.model.ModelLoader;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.IForgeRegistry;
import net.minecraftforge.registries.IForgeRegistryEntry;

import javax.annotation.Nonnull;

@SuppressWarnings("deprecation")
@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
public class MoCBlocks {

    public static MoCBlockOre ancientOre;
    public static Block ancientSilverBlock;
    public static Block carvedSilverSandstone;
    public static Block cobbledWyvstone;
    public static Block cobbledDeepWyvstone;
    public static Block deepWyvstone;
    public static Block firestone;
    public static Block gleamingGlass;
    public static Block mossyCobbledWyvstone;
    public static Block mossyCobbledDeepWyvstone;
    public static Block silverSand;
    public static Block silverSandstone;
    public static Block smoothSilverSandstone;
    public static Block tallWyvgrass;
    public static MoCBlockOre wyvernDiamondOre;
    public static MoCBlockOre wyvernEmeraldOre;
    public static MoCBlockOre wyvernGoldOre;
    public static MoCBlockOre wyvernIronOre;
    public static MoCBlockOre wyvernLapisOre;
    public static MoCBlockNest wyvernNestBlock;
    public static Block wyvstone;
    public static Block wyvgrass;
    public static Block wyvdirt;
    public static Block wyvwoodLeaves;
    public static Block wyvwoodSapling;
    public static Block wyvwoodLog;
    public static Block strippedwyvwoodLog;
    public static Block wyvwoodPlanks;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                setup(new MoCBlockMetal(AbstractBlock.Properties.create(Material.IRON, MaterialColor.IRON).hardnessAndResistance(3.0F, 10.0F)), "ancient_silver_block"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(2.0F, 10.0F)), "cobbled_wyvstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.5F, 10.0F)), "cobbled_deep_wyvstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "wyvstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 10.0F)), "deep_wyvstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "mossy_cobbled_wyvstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 10.0F)), "mossy_cobbled_deep_wyvstone"),
                setup(new MoCBlockGlass(AbstractBlock.Properties.create(Material.GLASS).hardnessAndResistance(0.4F)), "gleaming_glass"),
                setup(new MoCBlockSand(AbstractBlock.Properties.create(Material.EARTH, MaterialColor.CLAY).hardnessAndResistance(0.6F)), "silver_sand"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "silver_sandstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "carved_silver_sandstone"),
                setup(new MoCBlockRock(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.CLAY).hardnessAndResistance(1.2F)), "smooth_silver_sandstone"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "ancient_ore"),
                setup(new MoCBlockFirestone(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.ADOBE).hardnessAndResistance(3.0F).setLightLevel(state -> 7)), "firestone"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(4.5F, 5.0F)), "wyvern_diamond_ore"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(4.5F, 5.0F)), "wyvern_emerald_ore"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "wyvern_gold_ore"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(3.0F, 5.0F)), "wyvern_iron_ore"),
                setup(new MoCBlockOre(AbstractBlock.Properties.create(Material.ROCK, MaterialColor.STONE).hardnessAndResistance(1.5F, 5.0F)), "wyvern_lapis_ore"),
                setup(new MoCBlockGrass(MaterialColor.BLUE_STAINED_HARDENED_CLAY), "wyvgrass").setHardness(0.7F),
                setup(new MoCBlockDirt(MaterialColor.DIRT), "wyvdirt").setHardness(0.6F),
                setup(new MoCBlockLeaf(MaterialColor.DIAMOND, true, 100), "wyvwood_leaves").setHardness(0.2F).setLightOpacity(1),
                setup(new MoCBlockSapling(EnumWoodType.WYVWOOD, MaterialColor.FOLIAGE, true), "wyvwood_sapling").setHardness(0.0F),
                setup(new MoCBlockLog(MaterialColor.CYAN_STAINED_HARDENED_CLAY, true), "wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockLog(MaterialColor.CYAN_STAINED_HARDENED_CLAY, true), "stripped_wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockTallGrass(MaterialColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, false), "tall_wyvgrass").setHardness(0.0F),
                setup(new MoCBlockPlanks(MaterialColor.DIAMOND, true), "wyvwood_planks").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockNest(AbstractBlock.Properties.create(Material.ORGANIC, MaterialColor.YELLOW).hardnessAndResistance(0.5F)), "wyvern_nest_block")
        );
    }

    @SubscribeEvent
    public static void registerItemBlocks(RegistryEvent.Register<Item> event) {
        final IForgeRegistry<Item> registry = event.getRegistry();
        ForgeRegistries.BLOCKS.getValues().stream()
                .filter(block -> block.getRegistryName().getNamespace().equals(MoCConstants.MOD_ID))
                .forEach(block -> registry.register(setup(new BlockItem(block, new Item.Properties().group(MoCreatures.tabMoC)), block.getRegistryName())));
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
            ((Block) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
            ((Block) entry).setRegistryName(registryName);
        }
        if (entry instanceof Item) {
            ((Item) entry).setTranslationKey(registryName.getNamespace() + "." + registryName.getPath());
        }
        return entry;
    }
}
