/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures;

import com.mojang.authlib.GameProfile;
import drzhark.mocreatures.client.MoCKeyHandler;
import drzhark.mocreatures.compat.CompatHandler;
import drzhark.mocreatures.compat.datafixes.BlockIDFixer;
import drzhark.mocreatures.compat.datafixes.EntityIDFixer;
import drzhark.mocreatures.dimension.MoCWorldProviderWyvernSkylands;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.tameable.MoCPetMapData;
import drzhark.mocreatures.event.MoCEventHooks;
import drzhark.mocreatures.event.MoCEventHooksClient;
import drzhark.mocreatures.event.MoCEventHooksTerrain;
import drzhark.mocreatures.init.MoCCreativeTabs;
import drzhark.mocreatures.init.MoCEntities;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.command.CommandMoCPets;
import drzhark.mocreatures.network.command.CommandMoCSpawn;
import drzhark.mocreatures.network.command.CommandMoCTP;
import drzhark.mocreatures.network.command.CommandMoCreatures;
import drzhark.mocreatures.proxy.MoCProxy;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectLinkedOpenHashMap;
import it.unimi.dsi.fastutil.objects.Object2ObjectOpenHashMap;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MobEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.world.DimensionType;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.spongepowered.asm.mixin.MixinEnvironment;

import java.util.UUID;

@Mod(modid = MoCConstants.MOD_ID, name = MoCConstants.MOD_NAME, version = MoCConstants.MOD_VERSION, acceptableRemoteVersions = MoCConstants.MOD_ACCEPTED_VERSIONS, dependencies = MoCConstants.MOD_DEPENDENCIES)
public class MoCreatures {

    public static final Logger LOGGER = LogManager.getLogger(MoCConstants.MOD_ID);
    public static final ItemGroup tabMoC = new MoCCreativeTabs(ItemGroup.GROUPS.length, "MoCreaturesTab");
    public static final String MOC_LOGO = TextFormatting.WHITE + "[" + TextFormatting.AQUA + MoCConstants.MOD_NAME + TextFormatting.WHITE + "]";
    @Instance(MoCConstants.MOD_ID)
    public static MoCreatures instance;
    @SidedProxy(clientSide = "drzhark.mocreatures.proxy.MoCProxyClient", serverSide = "drzhark.mocreatures.proxy.MoCProxy")
    public static MoCProxy proxy;
    public static GameProfile MOCFAKEPLAYER = new GameProfile(UUID.fromString("6E379B45-1111-2222-3333-2FE1A88BCD66"), "[MoCreatures]");
    public static DimensionType WYVERN_SKYLANDS;
    public static int wyvernSkylandsDimensionID;
    public static Object2ObjectLinkedOpenHashMap<String, MoCEntityData> mocEntityMap = new Object2ObjectLinkedOpenHashMap<>();
    public static Object2ObjectOpenHashMap<EntityType<?>, MoCEntityData> entityMap = new Object2ObjectOpenHashMap<>();
    public static Int2ObjectOpenHashMap<Class<? extends MobEntity>> instaSpawnerMap = new Int2ObjectOpenHashMap<>();
    public MoCPetMapData mapData;

    public static boolean isServer() {
        return (FMLCommonHandler.instance().getEffectiveSide() == MixinEnvironment.Side.SERVER);
    }

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        MoCMessageHandler.init();
        MinecraftForge.EVENT_BUS.register(new MoCEventHooks());
        MinecraftForge.TERRAIN_GEN_BUS.register(new MoCEventHooksTerrain());
        proxy.configInit(event);
        if (!isServer()) {
            MinecraftForge.EVENT_BUS.register(new MoCEventHooksClient());
            MinecraftForge.EVENT_BUS.register(new MoCKeyHandler());
        }
        MoCEntities.registerEntities();
        CompatHandler.preInit();
    }

    @EventHandler
    public void load(FMLInitializationEvent event) {
        wyvernSkylandsDimensionID = proxy.wyvernDimension;
        proxy.mocSettingsConfig.save();
        proxy.registerRenderers();
        proxy.registerRenderInformation();
        WYVERN_SKYLANDS = DimensionType.register("Wyvern Skylands", "_wyvern_skylands", wyvernSkylandsDimensionID, MoCWorldProviderWyvernSkylands.class, false);
        DimensionManager.registerDimension(wyvernSkylandsDimensionID, WYVERN_SKYLANDS);
        MoCEventHooksTerrain.addBiomeTypes();
        MoCEntities.registerSpawns();
        MoCEventHooksTerrain.buildWorldGenSpawnLists();
        CompatHandler.init();
        ModFixs modFixer = FMLCommonHandler.instance().getDataFixer().init(MoCConstants.MOD_ID, MoCConstants.DATAFIXER_VERSION);
        modFixer.registerFix(FixTypes.BLOCK_ENTITY, new BlockIDFixer());
        modFixer.registerFix(FixTypes.ENTITY, new EntityIDFixer());
    }

    @EventHandler
    public void postInit(FMLPostInitializationEvent event) {
        CompatHandler.postInit();
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
}
