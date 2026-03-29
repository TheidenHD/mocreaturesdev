/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.block.*;
import net.minecraft.client.renderer.ItemBlockRenderTypes;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.resources.model.Material;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.flag.FeatureFlag;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.*;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.lifecycle.FMLClientSetupEvent;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCBlocks {

    public static final DeferredRegister<Block> BLOCKS = DeferredRegister.create(ForgeRegistries.BLOCKS, MoCConstants.MOD_ID);
    public static final DeferredRegister<Item> ITEMS = DeferredRegister.create(ForgeRegistries.ITEMS, MoCConstants.MOD_ID);

    public static final BlockSetType BLOCK_SET_WYVWOOD = BlockSetType.register(new BlockSetType("wyvwood"));
    public static final WoodType WOOD_WYVWOOD = WoodType.register(new WoodType("wyvwood", BLOCK_SET_WYVWOOD));

    public static final RegistryObject<ButtonBlock> deepWyvwstoneButton = register("deep_wyvstone_button", MoCBlocks::stoneButton);
    public static final RegistryObject<ButtonBlock> wyvwstoneButton = register("wyvwstoneButton", MoCBlocks::stoneButton);
    public static final RegistryObject<ButtonBlock> wyvwoodButton = register("wyvwood_button", () -> woodenButton(BLOCK_SET_WYVWOOD));
    public static final RegistryObject<Block> wyvdirt = register("wyvdirt", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIRT).strength(0.5F, 0.6F).sound(SoundType.GRAVEL)));
    public static final RegistryObject<DoorBlock> wyvwoodDoor = register("wyvwood_door", () -> new DoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().ignitedByLava().pushReaction(PushReaction.DESTROY), BLOCK_SET_WYVWOOD));
    public static final RegistryObject<FenceGateBlock> wyvwoodFenceGate = register("wyvwood_fence_gate", () -> new FenceGateBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 5.0F).ignitedByLava(), WOOD_WYVWOOD));
    public static final RegistryObject<FenceBlock> wyvwoodFence = register("wyvwood_button", () -> new FenceBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).forceSolidOn().instrument(NoteBlockInstrument.BASS).strength(2.0F, 5.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<Block> firestone = register("firestone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_ORANGE).strength(0.3F).lightLevel(state -> 7).sound(SoundType.GLASS)));
    public static final RegistryObject<GlassBlock> gleamingGlass = register("gleaming_glass", () -> new GlassBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.4F).sound(SoundType.GLASS).noOcclusion().isValidSpawn(MoCBlocks::never).isRedstoneConductor(MoCBlocks::never).isSuffocating(MoCBlocks::never).isViewBlocking(MoCBlocks::never)));
    public static final RegistryObject<MoCBlockGrass> wyvgrass = register("wyvwood_button", () -> new MoCBlockGrass(BlockBehaviour.Properties.of().mapColor(MapColor.GRASS).randomTicks().strength(0.6F).sound(SoundType.GRASS)));
    public static final RegistryObject<LeavesBlock> wyvwoodLeaves = register("wyvwood_leaves", () -> leaves(MapColor.DIAMOND, SoundType.GRASS));

    public static MoCBlockOre ancientOre;
    public static Block ancientSilverBlock;
    public static Block carvedSilverSandstone;
    public static Block cobbledWyvstone;
    public static MoCBlockSlab.Half cobbledWyvstoneSlab;
    public static MoCBlockSlab.Double cobbledWyvstoneSlabDouble;
    public static MoCBlockStairs cobbledWyvstoneStairs;
    public static MoCBlockWall cobbledWyvstoneWall;
    public static Block cobbledDeepWyvstone;
    public static MoCBlockSlab.Half cobbledDeepWyvstoneSlab;
    public static MoCBlockSlab.Double cobbledDeepWyvstoneSlabDouble;
    public static MoCBlockStairs cobbledDeepWyvstoneStairs;
    public static MoCBlockWall cobbledDeepWyvstoneWall;
    public static Block deepWyvstone;
    //public static MoCBlockButtonStone deepWyvwstoneButton;
    public static MoCBlockPressurePlateStone deepWyvstonePressurePlate;
    public static MoCBlockSlab.Half deepWyvstoneSlab;
    public static MoCBlockSlab.Double deepWyvstoneSlabDouble;
    public static MoCBlockStairs deepWyvstoneStairs;
    public static MoCBlockWall deepWyvstoneWall;
    public static Block fineSilverBlock;
    //public static Block firestone;
    //public static Block gleamingGlass;
    public static Block gleamingGlassPane;
    public static Block mossyCobbledWyvstone;
    public static MoCBlockSlab.Half mossyCobbledWyvstoneSlab;
    public static MoCBlockSlab.Double mossyCobbledWyvstoneSlabDouble;
    public static MoCBlockStairs mossyCobbledWyvstoneStairs;
    public static MoCBlockWall mossyCobbledWyvstoneWall;
    public static Block mossyCobbledDeepWyvstone;
    public static MoCBlockSlab.Half mossyCobbledDeepWyvstoneSlab;
    public static MoCBlockSlab.Double mossyCobbledDeepWyvstoneSlabDouble;
    public static MoCBlockStairs mossyCobbledDeepWyvstoneStairs;
    public static MoCBlockWall mossyCobbledDeepWyvstoneWall;
    public static Block silverSand;
    public static Block silverSandstone;
    public static MoCBlockSlab.Half silverSandstoneSlab;
    public static MoCBlockSlab.Double silverSandstoneSlabDouble;
    public static MoCBlockStairs silverSandstoneStairs;
    public static MoCBlockWall silverSandstoneWall;
    public static Block smoothSilverSandstone;
    public static Block tallWyvgrass;
    public static MoCBlockOre wyvernDiamondOre;
    public static MoCBlockOre wyvernEmeraldOre;
    public static MoCBlockOre wyvernGoldOre;
    public static MoCBlockOre wyvernIronOre;
    public static MoCBlockOre wyvernLapisOre;
    public static MoCBlockNest wyvernNestBlock;
    public static Block wyvstone;
    //public static MoCBlockButtonStone wyvwstoneButton;
    public static MoCBlockPressurePlateStone wyvstonePressurePlate;
    public static MoCBlockSlab.Half wyvstoneSlab;
    public static MoCBlockSlab.Double wyvstoneSlabDouble;
    public static MoCBlockStairs wyvstoneStairs;
    public static MoCBlockWall wyvstoneWall;
    //public static Block wyvgrass;
    //public static Block wyvdirt;
    //public static MoCBlockButtonWood wyvwoodButton;
    //public static MoCBlockDoorWood wyvwoodDoor;
    //public static MoCBlockFenceWood wyvwoodFence;
    //public static MoCBlockFenceGateWood wyvwoodFenceGate;
    //public static Block wyvwoodLeaves;
    public static Block wyvwoodLog;
    public static Block wyvwoodPlanks;
    public static Block wyvwoodSapling;
    public static MoCBlockSlab.Half wyvwoodSlab;
    public static MoCBlockSlab.Double wyvwoodSlabDouble;
    public static MoCBlockStairs wyvwoodPlanksStairs;
    public static MoCBlockTrapdoorWood wyvwoodTrapdoor;

    @SubscribeEvent
    public static void registerBlocks(RegistryEvent.Register<Block> event) {
        event.getRegistry().registerAll(
                //setup(new MoCBlockButtonStone(), "deep_wyvstone_button"),
                //setup(new MoCBlockButtonStone(), "wyvstone_button"),
                //setup(new MoCBlockButtonWood(), "wyvwood_button"),
                //setup(new MoCBlockDirt(MapColor.DIRT), "wyvdirt").setHardness(0.6F),
                //setup(new MoCBlockDoorWood(MapColor.DIAMOND), "wyvwood_door"),
                //setup(new MoCBlockFenceGateWood(MapColor.DIAMOND, true), "wyvwood_fence_gate"),
                //setup(new MoCBlockFenceWood(MapColor.DIAMOND, true), "wyvwood_fence"),
                //setup(new MoCBlockFirestone(MapColor.ADOBE), "firestone").setHardness(3.0F).setLightLevel(0.5F),
                //setup(new MoCBlockGlass(true), "gleaming_glass").setHardness(0.4F),
                //setup(new MoCBlockGrass(MapColor.BLUE_STAINED_HARDENED_CLAY), "wyvgrass").setHardness(0.7F),
                //setup(new MoCBlockLeaf(MapColor.DIAMOND, true, 100), "wyvwood_leaves").setHardness(0.2F).setLightOpacity(1),
                setup(new MoCBlockLog(MapColor.CYAN_STAINED_HARDENED_CLAY, true), "wyvwood_log").setHardness(2.0F),
                setup(new MoCBlockMetal(MapColor.IRON), "ancient_silver_block").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockMetal(MapColor.CLAY), "fine_silver_block").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockNest(), "wyvern_nest_block").setHardness(0.5F),
                setup(new MoCBlockOre(MapColor.STONE), "ancient_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_diamond_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_emerald_ore").setHardness(4.5F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_gold_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_iron_ore").setHardness(3.0F).setResistance(5.0F),
                setup(new MoCBlockOre(MapColor.STONE), "wyvern_lapis_ore").setHardness(1.5F).setResistance(5.0F),
                setup(new MoCBlockPane(Material.GLASS, SoundType.GLASS, false, true), "gleaming_glass_pane").setHardness(0.4F),
                setup(new MoCBlockPlanks(MapColor.DIAMOND, true), "wyvwood_planks").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockPressurePlateStone(MapColor.STONE), "deep_wyvstone_pressure_plate"),
                setup(new MoCBlockPressurePlateStone(MapColor.STONE), "wyvstone_pressure_plate"),
                setup(new MoCBlockPressurePlateWood(MapColor.DIAMOND), "wyvwood_pressure_plate"),
                setup(new MoCBlockRock(MapColor.CLAY), "carved_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.CLAY), "smooth_silver_sandstone").setHardness(1.2F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_deep_wyvstone").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "cobbled_wyvstone").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "deep_wyvstone").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_deep_wyvstone").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "mossy_cobbled_wyvstone").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockRock(MapColor.STONE), "wyvstone").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSand(MapColor.CLAY), "silver_sand").setHardness(0.6F),
                setup(new MoCBlockSapling(EnumWoodType.WYVWOOD, MapColor.FOLIAGE, true), "wyvwood_sapling").setHardness(0.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.CLAY, false), "silver_sandstone_slab_double").setHardness(1.2F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "cobbled_deep_wyvstone_slab_double").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "cobbled_wyvstone_slab_double").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "deep_wyvstone_slab_double").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_deep_wyvstone_slab_double").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_wyvstone_slab_double").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.ROCK, MapColor.STONE, false), "wyvstone_slab_double").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Double(Material.WOOD, MapColor.DIAMOND, true), "wyvwood_slab_double").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.CLAY, false), "silver_sandstone_slab").setHardness(1.2F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "cobbled_deep_wyvstone_slab").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "cobbled_wyvstone_slab").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "deep_wyvstone_slab").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_deep_wyvstone_slab").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "mossy_cobbled_wyvstone_slab").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.ROCK, MapColor.STONE, false), "wyvstone_slab").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockSlab.Half(Material.WOOD, MapColor.DIAMOND, true), "wyvwood_slab").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockStairs(new MoCBlockPlanks(MapColor.DIAMOND, true).getDefaultState(), true), "wyvwood_stairs").setHardness(2.0F).setResistance(5.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.CLAY).getDefaultState(), false), "silver_sandstone_stairs").setHardness(1.2F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "cobbled_deep_wyvstone_stairs").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "cobbled_wyvstone_stairs").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "deep_wyvstone_stairs").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "mossy_cobbled_deep_wyvstone_stairs").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "mossy_cobbled_wyvstone_stairs").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockStairs(new MoCBlockRock(MapColor.STONE).getDefaultState(), false), "wyvstone_stairs").setHardness(1.5F).setResistance(10.0F),
                setup(new MoCBlockTallGrass(MapColor.LIGHT_BLUE_STAINED_HARDENED_CLAY, false), "tall_wyvgrass").setHardness(0.0F),
                setup(new MoCBlockTrapdoorWood(MapColor.DIAMOND), "wyvwood_trapdoor"),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.CLAY), false), "silver_sandstone_wall").setHardness(1.2F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "cobbled_deep_wyvstone_wall").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "cobbled_wyvstone_wall").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "deep_wyvstone_wall").setHardness(3.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "mossy_cobbled_deep_wyvstone_wall").setHardness(3.5F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "mossy_cobbled_wyvstone_wall").setHardness(2.0F).setResistance(10.0F),
                setup(new MoCBlockWall(new MoCBlockRock(MapColor.STONE), false), "wyvstone_wall").setHardness(1.5F).setResistance(10.0F)
        );
    }

    private static RotatedPillarBlock log(MapColor p_285370_, MapColor p_285126_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_285370_ : p_285126_;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
    }

    private static RotatedPillarBlock log(MapColor p_285425_, MapColor p_285292_, SoundType p_285418_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_258972_) -> {
            return p_258972_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_285425_ : p_285292_;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(p_285418_).ignitedByLava());
    }

    private static LeavesBlock leaves(MapColor color, SoundType type) {
        return new LeavesBlock(BlockBehaviour.Properties.of().mapColor(color).strength(0.2F).randomTicks().sound(type).noOcclusion().isValidSpawn(MoCBlocks::ocelotOrParrot).isSuffocating(MoCBlocks::never).isViewBlocking(MoCBlocks::never).ignitedByLava().pushReaction(PushReaction.DESTROY).isRedstoneConductor(MoCBlocks::never));
    }

    private static ButtonBlock woodenButton(BlockSetType type, FeatureFlag... flag) {
        BlockBehaviour.Properties blockbehaviour$properties = BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY);
        if (flag.length > 0) {
            blockbehaviour$properties = blockbehaviour$properties.requiredFeatures(flag);
        }

        return new ButtonBlock(blockbehaviour$properties, type, 30, true);
    }

    private static ButtonBlock stoneButton() {
        return new ButtonBlock(BlockBehaviour.Properties.of().noCollission().strength(0.5F).pushReaction(PushReaction.DESTROY), BlockSetType.STONE, 20, false);
    }

    private static boolean always(BlockState state, BlockGetter getter, BlockPos pos) {
        return true;
    }

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
    }

    private static Boolean always(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return true;
    }

    private static Boolean ocelotOrParrot(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return (boolean)(type == EntityType.OCELOT || type == EntityType.PARROT);
    }

    private static <T extends Block> RegistryObject<T> register(String name, Supplier<T> blockSupplier) {
        RegistryObject<T> block = BLOCKS.register(name, blockSupplier);
        ITEMS.register(name, () -> {
            return new BlockItem(block.get(), new Item.Properties());
        });
        return block;
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
        ITEMS.register(eventBus);
    }

    @OnlyIn(Dist.CLIENT)
    @Mod.EventBusSubscriber(value = Dist.CLIENT, modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
    public static class ClientEvents {
        @SubscribeEvent
        public static void registerRenderLayers(FMLClientSetupEvent event) {
            event.enqueueWork(() -> {
                ItemBlockRenderTypes.setRenderLayer(wyvwoodLeaves.get(), RenderType.cutoutMipped());
                ItemBlockRenderTypes.setRenderLayer(wyvwoodSapling.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(tallWyvgrass.get(), RenderType.cutout());
                ItemBlockRenderTypes.setRenderLayer(gleamingGlass.get(), RenderType.translucent());
            });
        }
    }
}
