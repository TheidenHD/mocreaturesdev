/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures;

import com.google.common.base.Supplier;
import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCKeyHandler;
import drzhark.mocreatures.client.renderer.fx.MoCParticles;
import drzhark.mocreatures.compat.CompatHandler;
import drzhark.mocreatures.compat.datafixes.BlockIDFixer;
import drzhark.mocreatures.compat.datafixes.EntityIDFixer;
import drzhark.mocreatures.compat.datafixes.ItemIDFixer;
import drzhark.mocreatures.compat.tinkers.TinkersConstructIntegration;
import drzhark.mocreatures.dimension.MoCWorldProviderWyvernSkylands;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.event.MoCEventHooks;
import drzhark.mocreatures.event.MoCEventHooksClient;
import drzhark.mocreatures.event.MoCEventHooksTerrain;
import drzhark.mocreatures.init.MoCCreativeTabs;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.init.MoCRecipes;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.command.CommandMoCPets;
import drzhark.mocreatures.network.command.CommandMoCSpawn;
import drzhark.mocreatures.network.command.CommandMoCTP;
import drzhark.mocreatures.network.command.CommandMoCreatures;
import drzhark.mocreatures.proxy.MoCProxy;
import drzhark.mocreatures.proxy.MoCProxyClient;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.util.datafix.FixTypes;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraft.world.biome.Biome;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.common.util.ModFixs;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.Loader;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.*;
import net.minecraftforge.fml.common.registry.ForgeRegistries;
import net.minecraftforge.fml.relauncher.Side;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.Map;
import java.util.UUID;

@Mod(MoCConstants.MOD_ID)
public class MoCreatures {

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public static final ItemGroup tabMoC = new MoCCreativeTabs(ItemGroup.GROUPS.length, "MoCreaturesTab");
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + MoCConstants.MOD_NAME + TextFormatting.WHITE + "]";
    public static MoCreatures instance;
    public static MoCProxy proxy;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    public static DimensionType WYVERN_SKYLANDS;
    public static RegistryKey<World> wyvernSkylandsDimensionID;
    public static Object2ObjectLinkedOpenHashMap<String, MoCEntityData> mocEntityMap = new Object2ObjectLinkedOpenHashMap<>();
    public static Object2ObjectOpenHashMap<EntityType<?>, MoCEntityData> entityMap = new Object2ObjectOpenHashMap<>();
    public static Int2ObjectOpenHashMap<Class<? extends MobEntity>> instaSpawnerMap = new Int2ObjectOpenHashMap<>();
    public MoCPetMapData mapData;

    public MoCreatures(){
        instance = this;


        this.proxy = DistExecutor.unsafeRunForDist(() -> MoCProxyClient::new, () -> MoCProxy::new);
        //this.proxy.initialize();
        //this.proxy.attachLifecycle(FMLJavaModLoadingContext.get().getModEventBus());
        //this.proxy.attachEventHandlers(MinecraftForge.EVENT_BUS);
        MoCMessageHandler.init();
        final IEventBus eventBus = FMLJavaModLoadingContext.get().getModEventBus();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.EVENT_BUS.register(new MoCEventHooksTerrain());
        //proxy.configInit();
        if (true) {
            MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
            if (Loader.isModLoaded("tconstruct")) {
                MinecraftForge.EVENT_BUS.register(new TinkersConstructIntegration());
            }
        }
        //MoCEntities.registerEntities();
        CompatHandler.preInit();

        wyvernSkylandsDimensionID = proxy.wyvernDimension;
        //proxy.mocSettingsConfig.save();
        proxy.configInit();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        WYVERN_SKYLANDS = DimensionType.register("Wyvern Skylands", "_wyvern_skylands", wyvernSkylandsDimensionID, MoCWorldProviderWyvernSkylands.class, false);
        DimensionManager.registerDimension(wyvernSkylandsDimensionID, WYVERN_SKYLANDS);
        MoCEventHooksTerrain.addBiomeTypes();
        MoCEntities.registerSpawns();
        MoCEventHooksTerrain.buildWorldGenSpawnLists();
        MoCRecipes.registerOreDictionaries();
        CompatHandler.init();
        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(MoCConstants.MOD_ID, MoCConstants.DATAFIXER_VERSION);
        modFixer.registerFix(FixTypes.BLOCK_ENTITY, new BlockIDFixer());
        modFixer.registerFix(FixTypes.ENTITY, new EntityIDFixer());
        modFixer.registerFix(FixTypes.ITEM_INSTANCE, new ItemIDFixer());
    }

    public static boolean isServer(World world) {
        return !world.isRemote();
    }

    @EventHandler
    public void loadComplete(FMLLoadCompleteEvent event) {
        if (MoCreatures.proxy.debug) {
            for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.CREATURE)) {
                    LOGGER.info("Creature is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.WATER_CREATURE)) {
                    LOGGER.info("Water Creature is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.MONSTER)) {
                    LOGGER.info("Monster is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
                for (Biome.SpawnListEntry entry : biome.getSpawnableList(EnumCreatureType.AMBIENT)) {
                    LOGGER.info("Ambient is spawnable in biome " + biome.biomeName + ": " + entry.entityClass);
                }
            }
        }
    }

    @EventHandler
    public void serverStarting(FMLServerStartingEvent event) {
        event.registerServerCommand(new CommandMoCreatures());
        event.registerServerCommand(new CommandMoCTP());
        event.registerServerCommand(new CommandMoCPets());
        if (isServer()) {
            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
                event.registerServerCommand(new CommandMoCSpawn());
            }
        }
    }

//    @EventHandler
//    public void postInit(FMLPostInitializationEvent event) {
//        CompatHandler.postInit();
//    }
//
//    @EventHandler
//    public void serverStarting(FMLServerStartingEvent event) {
//        event.registerServerCommand(new CommandMoCreatures());
//        event.registerServerCommand(new CommandMoCTP());
//        event.registerServerCommand(new CommandMoCPets());
//        if (isServer()) {
//            if (FMLCommonHandler.instance().getMinecraftServerInstance().isDedicatedServer()) {
//                event.registerServerCommand(new CommandMoCSpawn());
//            }
//        }
//    }
}
