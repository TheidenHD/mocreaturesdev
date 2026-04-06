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
import net.minecraft.world.level.block.grower.OakTreeGrower;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.block.state.properties.BlockSetType;
import net.minecraft.world.level.block.state.properties.NoteBlockInstrument;
import net.minecraft.world.level.block.state.properties.WoodType;
import net.minecraft.world.level.material.MapColor;
import net.minecraft.world.level.material.PushReaction;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
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
    public static final RegistryObject<RotatedPillarBlock> wyvwoodLog = register("wyvwood_log", () -> log(MapColor.COLOR_CYAN, MapColor.COLOR_CYAN));
    public static final RegistryObject<Block> ancientSilverBlock = register("ancient_silver_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.METAL).strength(3F, 10F).sound(SoundType.METAL)));
    public static final RegistryObject<Block> fineSilverBlock = register("fine_silver_block", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(3F, 10F).sound(SoundType.METAL)));
    public static final RegistryObject<MoCBlockNest> wyvernNestBlock = register("wyvern_nest_block", () -> new MoCBlockNest(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_YELLOW).strength(0.5F).sound(SoundType.GRASS)));
    public static final RegistryObject<Block> ancientOre = register("ancient_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvernDiamondOre = register("wyvern_diamond_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.5F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvernEmeraldOre = register("wyvern_emerald_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(4.5F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvernGoldOre = register("wyvern_gold_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvernIronOre = register("wyvern_iron_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvernLapisOre = register("wyvern_lapis_ore", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5F, 5F).sound(SoundType.STONE)));
    public static final RegistryObject<IronBarsBlock> gleamingGlassPane = register("gleaming_glass_pane", () -> new IronBarsBlock(BlockBehaviour.Properties.of().instrument(NoteBlockInstrument.HAT).strength(0.4F).sound(SoundType.GLASS).noOcclusion()));
    public static final RegistryObject<Block> wyvwoodPlanks = register("wyvwood_planks", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASS).strength(2.0F, 5.0F).sound(SoundType.WOOD).ignitedByLava()));
    public static final RegistryObject<PressurePlateBlock> deepWyvstonePressurePlate = register("deep_wyvstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.STONE));
    public static final RegistryObject<PressurePlateBlock> wyvstonePressurePlate = register("wyvstone_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.MOBS, BlockBehaviour.Properties.of().mapColor(MapColor.STONE).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BlockSetType.STONE));
    public static final RegistryObject<PressurePlateBlock> wyvwoodPressurePlate = register("wyvwood_pressure_plate", () -> new PressurePlateBlock(PressurePlateBlock.Sensitivity.EVERYTHING, BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).forceSolidOn().instrument(NoteBlockInstrument.BASS).noCollission().strength(0.5F).ignitedByLava().pushReaction(PushReaction.DESTROY), BLOCK_SET_WYVWOOD));
    public static final RegistryObject<Block> carvedSilverSandstone = register("carved_silver_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.2F, 6F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> silverSandstone = register("silver_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.2F, 6F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> smoothSilverSandstone = register("smooth_silver_sandstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).strength(1.2F, 6F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> cobbledDeepWyvstone = register("cobbled_deep_wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.5F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> cobbledWyvstone = register("cobbled_wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> deepWyvstone = register("deep_wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> mossyCobbledDeepWyvstone = register("mossy_cobbled_deep_wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(3.5F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> mossyCobbledWyvstone = register("mossy_cobbled_wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(2F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<Block> wyvstone = register("wyvstone", () -> new Block(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).strength(1.5F, 10.0F).sound(SoundType.STONE)));
    public static final RegistryObject<SandBlock> silverSand = register("silver_sand", () -> new SandBlock(12107978, BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.SNARE).strength(0.5F).sound(SoundType.SAND)));
    public static final RegistryObject<MoCBlockSapling> wyvwoodSapling = register("wyvwood_sapling", () -> new MoCBlockSapling(new WyvwoodTreeGrower(), BlockBehaviour.Properties.of().mapColor(MapColor.PLANT).noCollission().randomTicks().instabreak().sound(SoundType.GRASS).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<SlabBlock> silverSandstoneSlab = register("silver_sandstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.CLAY).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.2F, 6F)));
    public static final RegistryObject<SlabBlock> cobbledDeepWyvstoneSlab = register("cobbled_deep_wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F, 10F)));
    public static final RegistryObject<SlabBlock> cobbledWyvstoneSlab = register("cobbled_wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 10F)));
    public static final RegistryObject<SlabBlock> deepWyvstoneSlab = register("deep_wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.0F, 10F)));
    public static final RegistryObject<SlabBlock> mossyCobbledDeepWyvstoneSlab = register("mossy_cobbled_deep_wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(3.5F, 10F)));
    public static final RegistryObject<SlabBlock> mossyCobbledWyvstoneSlab = register("mossy_cobbled_wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(2.0F, 10F)));
    public static final RegistryObject<SlabBlock> wyvstoneSlab = register("wyvstone_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.STONE).instrument(NoteBlockInstrument.BASEDRUM).requiresCorrectToolForDrops().strength(1.5F, 10F)));
    public static final RegistryObject<SlabBlock> wyvwoodSlab = register("wyvwood_slab", () -> new SlabBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASS).strength(2.0F, 5.0F).ignitedByLava()));
    public static final RegistryObject<StairBlock> silverSandstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> silverSandstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(silverSandstone.get())));
    public static final RegistryObject<StairBlock> cobbledDeepWyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> cobbledDeepWyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(cobbledDeepWyvstone.get())));
    public static final RegistryObject<StairBlock> cobbledWyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> cobbledWyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(cobbledWyvstone.get())));
    public static final RegistryObject<StairBlock> deepWyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> deepWyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(deepWyvstone.get())));
    public static final RegistryObject<StairBlock> mossyCobbledDeepWyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> mossyCobbledDeepWyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(mossyCobbledDeepWyvstone.get())));
    public static final RegistryObject<StairBlock> mossyCobbledWyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> mossyCobbledWyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(mossyCobbledWyvstone.get())));
    public static final RegistryObject<StairBlock> wyvstoneStairs = register("wyvwood_stairs", () -> new StairBlock(() -> wyvstone.get().defaultBlockState(), BlockBehaviour.Properties.copy(wyvstone.get())));
    public static final RegistryObject<StairBlock> wyvwoodStairs = register("wyvwood_stairs", () -> new StairBlock(() -> wyvwoodPlanks.get().defaultBlockState(), BlockBehaviour.Properties.copy(wyvwoodPlanks.get())));
    public static final RegistryObject<MoCBlockTallGrass> tallWyvgrass = register("tall_wyvgrass", () -> new MoCBlockTallGrass(BlockBehaviour.Properties.of().mapColor(MapColor.COLOR_LIGHT_BLUE).noCollission().instabreak().sound(SoundType.GRASS).offsetType(BlockBehaviour.OffsetType.XYZ).pushReaction(PushReaction.DESTROY)));
    public static final RegistryObject<TrapDoorBlock> wyvwoodTrapdoor = register("silver_sandstone_slab", () -> new TrapDoorBlock(BlockBehaviour.Properties.of().mapColor(MapColor.DIAMOND).instrument(NoteBlockInstrument.BASS).strength(3.0F).noOcclusion().isValidSpawn(MoCBlocks::never).ignitedByLava(), MoCBlocks.BLOCK_SET_WYVWOOD));
    public static final RegistryObject<WallBlock> silverSandstoneWall = register("silver_sandstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> cobbledDeepWyvstoneWall = register("cobbled_deep_wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> cobbledWyvstoneWall = register("cobbled_wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> deepWyvstoneWall = register("deep_wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> mossyCobbledDeepWyvstoneWall = register("mossy_cobbled_deep_wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> mossyCobbledWyvstoneWall = register("mossy_cobbled_wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));
    public static final RegistryObject<WallBlock> wyvstoneWall = register("wyvstone_wall", () -> new WallBlock(BlockBehaviour.Properties.copy(silverSandstone.get()).forceSolidOn()));

    private static RotatedPillarBlock log(MapColor p_285370_, MapColor p_285126_) {
        return new RotatedPillarBlock(BlockBehaviour.Properties.of().mapColor((p_152624_) -> {
            return p_152624_.getValue(RotatedPillarBlock.AXIS) == Direction.Axis.Y ? p_285370_ : p_285126_;
        }).instrument(NoteBlockInstrument.BASS).strength(2.0F).sound(SoundType.WOOD).ignitedByLava());
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

    private static boolean never(BlockState state, BlockGetter getter, BlockPos pos) {
        return false;
    }

    private static Boolean never(BlockState state, BlockGetter getter, BlockPos pos, EntityType<?> type) {
        return false;
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
