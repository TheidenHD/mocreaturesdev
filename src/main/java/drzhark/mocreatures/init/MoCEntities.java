/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityData;
import drzhark.mocreatures.entity.ambient.*;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.hostile.*;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.hunter.*;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.neutral.MoCEntityBoar;
import drzhark.mocreatures.entity.neutral.*;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.entity.*;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.MobSpawnInfo;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.registries.ForgeRegistries;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class MoCEntities {

    public static BiomeDictionary.Type STEEP = BiomeDictionary.Type.getType("STEEP");
    public static BiomeDictionary.Type WYVERN_LAIR = BiomeDictionary.Type.getType("WYVERN_LAIR");
    public static List<EntityType> ENTITIES = new ArrayList<>();
    public static List<EntityType> SPAWN_ENTITIES = new ArrayList<>();
    /**
     * Animal
     */
    public static EntityType BIRD = createEntityEntry(MoCEntityBird.class, "Bird", 37109, 4609629, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType BEAR = createEntityEntry(MoCEntityBlackBear.class, "BlackBear", 986897, 8609347, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType BOAR = createEntityEntry(MoCEntityBoar.class, "Boar", 2037783, 4995892, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType BUNNY = createEntityEntry(MoCEntityBunny.class, "Bunny", 8741934, 14527570, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType CROCODILE = createEntityEntry(MoCEntityCrocodile.class, "Crocodile", 2698525, 10720356, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType DUCK = createEntityEntry(MoCEntityDuck.class, "Duck", 3161353, 14011565, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType DEER = createEntityEntry(MoCEntityDeer.class, "Deer", 11572843, 13752020, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType ELEPHANT = createEntityEntry(MoCEntityElephant.class, "Elephant", 4274216, 9337176, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType ENT = createEntityEntry(MoCEntityEnt.class, "Ent", 9794886, 5800509, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FILCH_LIZARD = createEntityEntry(MoCEntityFilchLizard.class, "FilchLizard", 9930060, 5580310, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FOX = createEntityEntry(MoCEntityFox.class, "Fox", 15966491, 4009236, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType GOAT = createEntityEntry(MoCEntityGoat.class, "Goat", 15262682, 4404517, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType GRIZZLY_BEAR = createEntityEntry(MoCEntityGrizzlyBear.class, "GrizzlyBear", 3547151, 11371099, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType KITTY = createEntityEntry(MoCEntityKitty.class, "Kitty", 16707009, 14861419, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType KOMODO_DRAGON = createEntityEntry(MoCEntityKomodo.class, "KomodoDragon", 8615512, 3025185, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LEOGER = createEntityEntry(MoCEntityLeoger.class, "Leoger", 13274957, 6638124, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LEOPARD = createEntityEntry(MoCEntityLeopard.class, "Leopard", 13478009, 3682085, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LIARD = createEntityEntry(MoCEntityLiard.class, "Liard", 11965543, 8215850, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LION = createEntityEntry(MoCEntityLion.class, "Lion", 11503958, 2234383, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LIGER = createEntityEntry(MoCEntityLiger.class, "Liger", 13347170, 9068088, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType LITHER = createEntityEntry(MoCEntityLither.class, "Lither", 2234897, 7821878, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType MANTICORE_PET = createEntityEntry(MoCEntityManticorePet.class, "ManticorePet");
    public static EntityType MOLE = createEntityEntry(MoCEntityMole.class, "Mole", 263173, 10646113, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType MOUSE = createEntityEntry(MoCEntityMouse.class, "Mouse", 7428164, 15510186, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType OSTRICH = createEntityEntry(MoCEntityOstrich.class, "Ostrich", 12884106, 10646377, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PANDA_BEAR = createEntityEntry(MoCEntityPandaBear.class, "PandaBear", 13354393, 789516, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PANTHARD = createEntityEntry(MoCEntityPanthard.class, "Panthard", 591108, 9005068, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PANTHER = createEntityEntry(MoCEntityPanther.class, "Panther", 1709584, 16768078, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PANTHGER = createEntityEntry(MoCEntityPanthger.class, "Panthger", 2826517, 14348086, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PET_SCORPION = createEntityEntry(MoCEntityPetScorpion.class, "PetScorpion");
    public static EntityType POLAR_BEAR = createEntityEntry(MoCEntityPolarBear.class, "WildPolarBear", 15131867, 11380879, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType RACCOON = createEntityEntry(MoCEntityRaccoon.class, "Raccoon", 6115913, 1578001, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType SNAKE = createEntityEntry(MoCEntitySnake.class, "Snake", 670976, 11309312, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType TIGER = createEntityEntry(MoCEntityTiger.class, "Tiger", 12476160, 2956299, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType TURTLE = createEntityEntry(MoCEntityTurtle.class, "Turtle", 6505237, 10524955, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType TURKEY = createEntityEntry(MoCEntityTurkey.class, "Turkey", 12268098, 6991322, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType WILDHORSE = createEntityEntry(MoCEntityHorse.class, "WildHorse", 9204829, 11379712, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType WYVERN = createEntityEntry(MoCEntityWyvern.class, "Wyvern", 11440923, 15526339, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    /**
     * Monster
     */
    public static EntityType CAVE_OGRE = createEntityEntry(MoCEntityCaveOgre.class, "CaveOgre", 5079480, 12581631, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FLAME_WRAITH = createEntityEntry(MoCEntityFlameWraith.class, "FlameWraith", 8988239, 16748288, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FIRE_OGRE = createEntityEntry(MoCEntityFireOgre.class, "FireOgre", 6882304, 16430080, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType GREEN_OGRE = createEntityEntry(MoCEntityGreenOgre.class, "GreenOgre", 1607501, 2032997, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType BIG_GOLEM = createEntityEntry(MoCEntityGolem.class, "BigGolem", 4868682, 52411, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType HORSEMOB = createEntityEntry(MoCEntityHorseMob.class, "HorseMob", 6326628, 12369062, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType HELLRAT = createEntityEntry(MoCEntityHellRat.class, "HellRat", 1049090, 15956249, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType DARK_MANTICORE = createEntityEntry(MoCEntityDarkManticore.class, "DarkManticore", 3289650, 657930, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FIRE_MANTICORE = createEntityEntry(MoCEntityFireManticore.class, "FireManticore", 7148552, 2819585, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FROST_MANTICORE = createEntityEntry(MoCEntityFrostManticore.class, "FrostManticore", 3559006, 2041389, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType PLAIN_MANTICORE = createEntityEntry(MoCEntityPlainManticore.class, "PlainManticore", 7623465, 5510656, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType TOXIC_MANTICORE = createEntityEntry(MoCEntityToxicManticore.class, "ToxicManticore", 6252034, 3365689, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType MINI_GOLEM = createEntityEntry(MoCEntityMiniGolem.class, "MiniGolem", 7895160, 8512741, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType RAT = createEntityEntry(MoCEntityRat.class, "Rat", 3685435, 15838633, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType SILVER_SKELETON = createEntityEntry(MoCEntitySilverSkeleton.class, "SilverSkeleton", 13421750, 8158847, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType CAVE_SCORPION = createEntityEntry(MoCEntityCaveScorpion.class, "CaveScorpion", 789516, 3223866, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType DIRT_SCORPION = createEntityEntry(MoCEntityDirtScorpion.class, "DirtScorpion", 4134919, 13139755, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FROST_SCORPION = createEntityEntry(MoCEntityFrostScorpion.class, "FrostScorpion", 333608, 5218691, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType FIRE_SCORPION = createEntityEntry(MoCEntityFireScorpion.class, "FireScorpion", 2163457, 9515286, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType UNDEAD_SCORPION = createEntityEntry(MoCEntityUndeadScorpion.class, "UndeadScorpion", 1118208, 7899732, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType WEREWOLF = createEntityEntry(MoCEntityWerewolf.class, "Werewolf", 1970698, 7032379, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType WRAITH = createEntityEntry(MoCEntityWraith.class, "Wraith", 5987163, 16711680, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType WWOLF = createEntityEntry(MoCEntityWWolf.class, "WWolf", 5657166, 13223102, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    /**
     * Aquatic
     */
    public static EntityType ANCHOVY = createEntityEntry(MoCEntityAnchovy.class, "Anchovy", 7039838, 12763545, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType ANGELFISH = createEntityEntry(MoCEntityAngelFish.class, "AngelFish", 12040119, 15970609, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType ANGLER = createEntityEntry(MoCEntityAngler.class, "Angler", 5257257, 6225864, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType BASS = createEntityEntry(MoCEntityBass.class, "Bass", 4341299, 10051649, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType CLOWNFISH = createEntityEntry(MoCEntityClownFish.class, "ClownFish", 16439491, 15425029, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType COD = createEntityEntry(MoCEntityCod.class, "Cod", 5459520, 14600592, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType DOLPHIN = createEntityEntry(MoCEntityDolphin.class, "Dolphin", 4086148, 11251396, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType FISHY = createEntityEntry(MoCEntityFishy.class, "Fishy", 5665535, 2037680, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType GOLDFISH = createEntityEntry(MoCEntityGoldFish.class, "GoldFish", 15577089, 16735257, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType HIPPOTANG = createEntityEntry(MoCEntityHippoTang.class, "HippoTang", 4280267, 12893441, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType JELLYFISH = createEntityEntry(MoCEntityJellyFish.class, "JellyFish", 12758461, 9465021, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType MANDERIN = createEntityEntry(MoCEntityManderin.class, "Manderin", 14764801, 5935359, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType PIRANHA = createEntityEntry(MoCEntityPiranha.class, "Piranha", 10756121, 3160114, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType SALMON = createEntityEntry(MoCEntitySalmon.class, "Salmon", 5262951, 10716540, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType MANTARAY = createEntityEntry(MoCEntityMantaRay.class, "MantaRay", 5791360, 11580358, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType SHARK = createEntityEntry(MoCEntityShark.class, "Shark", 3817558, 11580358, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    public static EntityType STINGRAY = createEntityEntry(MoCEntityStingRay.class, "StingRay", 3679519, 8418674, EntitySpawnPlacementRegistry.PlacementType.IN_WATER);
    /**
     * Ambient
     */
    public static EntityType ANT = createEntityEntry(MoCEntityAnt.class, "Ant", 5915945, 2693905, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType BEE = createEntityEntry(MoCEntityBee.class, "Bee", 15912747, 526604, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType BUTTERFLY = createEntityEntry(MoCEntityButterfly.class, "ButterFly", 12615169, 2956801, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType CRAB = createEntityEntry(MoCEntityCrab.class, "Crab", 11880978, 15514213, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType CRICKET = createEntityEntry(MoCEntityCricket.class, "Cricket", 4071430, 8612672, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType DRAGONFLY = createEntityEntry(MoCEntityDragonfly.class, "DragonFly", 665770, 2207231, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType FIREFLY = createEntityEntry(MoCEntityFirefly.class, "Firefly", 2102294, 8501028, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType FLY = createEntityEntry(MoCEntityFly.class, "Fly", 1184284, 11077640, EntitySpawnPlacementRegistry.PlacementType.IN_AIR);
    public static EntityType GRASSHOPPER = createEntityEntry(MoCEntityGrasshopper.class, "Grasshopper", 7830593, 3747075, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType MAGGOT = createEntityEntry(MoCEntityMaggot.class, "Maggot", 14076037, 6839592, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType SNAIL = createEntityEntry(MoCEntitySnail.class, "Snail", 10850932, 7225384, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    public static EntityType ROACH = createEntityEntry(MoCEntityRoach.class, "Roach", 5185289, 10245148, EntitySpawnPlacementRegistry.PlacementType.ON_GROUND);
    /**
     * Other
     */
    public static EntityType EGG = createEntityEntry(MoCEntityEgg.class, "Egg");
    public static EntityType KITTY_BED = createEntityEntry(MoCEntityKittyBed.class, "KittyBed");
    public static EntityType LITTERBOX = createEntityEntry(MoCEntityLitterBox.class, "LitterBox");
    public static EntityType TROCK = createEntityEntry(MoCEntityThrowableRock.class, "TRock");
    static int MoCEntityID = 0;

    private static EntityType createEntityEntry(Class<? extends Entity> cls, String name) {
        EntityType entityEntry = new EntityType(cls, name);
        entityEntry.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
        ENTITIES.add(entityEntry);
        return entityEntry;
    }

    private static EntityType createEntityEntry(Class<? extends Entity> cls, String name, int primaryColorIn, int secondaryColorIn, EntitySpawnPlacementRegistry.PlacementType type) {
        EntityType entityEntry = new EntityType(cls, name);
        entityEntry.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
        entityEntry.setEgg(new EntityEggInfo(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()), primaryColorIn, secondaryColorIn));
        EntitySpawnPlacementRegistry.getPlacementType().setPlacementType(cls, type);
        SPAWN_ENTITIES.add(entityEntry);
        return entityEntry;
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String entityName) {
        final ResourceLocation resourceLocation = new ResourceLocation(MoCConstants.MOD_PREFIX + entityName.toLowerCase());
        EntityRegistry.registerModEntity(resourceLocation, entityClass, resourceLocation.toString(), MoCEntityID++, MoCreatures.instance, 64, 1, true);
    }

    private static void registerEntity(Class<? extends Entity> entityClass, String entityName, int eggColor, int eggDotsColor) {
        final ResourceLocation resourceLocation = new ResourceLocation(MoCConstants.MOD_PREFIX + entityName.toLowerCase());
        EntityRegistry.registerModEntity(resourceLocation, entityClass, resourceLocation.toString(), MoCEntityID++, MoCreatures.instance, 64, 1, true, eggColor, eggDotsColor);
    }

    public static void registerEntities() {
        MoCreatures.LOGGER.info("Registering entities...");

        for (EntityType entry : ENTITIES) {
            registerEntity(entry.getEntityClass(), entry.getName());
        }

        for (EntityType entry : SPAWN_ENTITIES) {
            registerEntity(entry.getEntityClass(), entry.getName(), entry.getEgg().primaryColor, entry.getEgg().secondaryColor);
        }

        int[] overworld = new int[]{0};
        int[] nether = new int[]{-1};
        int[] overworldNether = new int[]{0, -1};
        int[] wyvernLair = new int[]{MoCreatures.proxy.wyvernDimension};
        int[] overworldWyvernLair = new int[]{0, MoCreatures.proxy.wyvernDimension};

        /*
         * Animal
         */
        MoCreatures.mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityBlackBear.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityGrizzlyBear.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityPolarBear.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityPandaBear.class, 7, 1, 3), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityBird.class, 16, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.MESA, Type.LUSH, STEEP))));
        MoCreatures.mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityBoar.class, 12, 2, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, overworldWyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityBunny.class, 12, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.SNOWY, Type.CONIFEROUS, WYVERN_LAIR, STEEP))));
        MoCreatures.mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityCrocodile.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP))));
        MoCreatures.mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityDeer.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityDuck.class, 12, 2, 4), new ArrayList<>(Arrays.asList(Type.RIVER, Type.LUSH))));
        MoCreatures.mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityElephant.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.JUNGLE, Type.SAVANNA, Type.SNOWY)), new ArrayList<>(Arrays.asList(Type.MESA))));
        MoCreatures.mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityEnt.class, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("FilchLizard", new MoCEntityData("FilchLizard", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityFilchLizard.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA, WYVERN_LAIR))));
        MoCreatures.mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityFox.class, 10, 1, 1), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.CONIFEROUS))));
        MoCreatures.mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityGoat.class, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.PLAINS, STEEP))));
        MoCreatures.mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityKitty.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.FOREST)))); // spawns in villages
        MoCreatures.mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityKomodo.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityLeopard.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.SNOWY, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityLion.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA))));
        MoCreatures.mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityMole.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.LUSH, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityMouse.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
        MoCreatures.mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityOstrich.class, 7, 1, 1), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY)), new ArrayList<>(Arrays.asList(Type.MESA))));
        MoCreatures.mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityPanther.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityRaccoon.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
        MoCreatures.mocEntityMap.put("Snake", new MoCEntityData("Snake", 3, overworldWyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntitySnake.class, 12, 1, 1), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.PLAINS, Type.FOREST, Type.SWAMP, Type.LUSH, Type.JUNGLE, WYVERN_LAIR, STEEP))));
        MoCreatures.mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityTiger.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityTurkey.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityTurtle.class, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
        MoCreatures.mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, overworld, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityHorse.class, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SAVANNA))));
        MoCreatures.mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, wyvernLair, EntityClassification.CREATURE, new MobSpawnInfo.Spawners(MoCEntityWyvern.class, 12, 1, 3), new ArrayList<>(Arrays.asList(WYVERN_LAIR))));
        /*
         * Monster
         */
        MoCreatures.mocEntityMap.put("BigGolem", new MoCEntityData("BigGolem", 1, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityGolem.class, 3, 1, 1), new ArrayList<>(Arrays.asList(Type.SANDY, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("MiniGolem", new MoCEntityData("MiniGolem", 2, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityMiniGolem.class, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("HorseMob", new MoCEntityData("HorseMob", 3, overworldNether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityHorseMob.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER, Type.PLAINS, Type.SAVANNA, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("CaveScorpion", new MoCEntityData("CaveScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityCaveScorpion.class, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.DRY, Type.HOT, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("DirtScorpion", new MoCEntityData("DirtScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityDirtScorpion.class, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.DRY, Type.HOT))));
        MoCreatures.mocEntityMap.put("FireScorpion", new MoCEntityData("FireScorpion", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFireScorpion.class, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("FrostScorpion", new MoCEntityData("FrostScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFrostScorpion.class, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("UndeadScorpion", new MoCEntityData("UndeadScorpion", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityUndeadScorpion.class, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("SilverSkeleton", new MoCEntityData("SilverSkeleton", 4, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntitySilverSkeleton.class, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.PLAINS, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("Werewolf", new MoCEntityData("Werewolf", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityWerewolf.class, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.CONIFEROUS, Type.FOREST))));
        MoCreatures.mocEntityMap.put("WWolf", new MoCEntityData("WWolf", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityWWolf.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.WASTELAND))));
        MoCreatures.mocEntityMap.put("DarkManticore", new MoCEntityData("DarkManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityDarkManticore.class, 5, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS, Type.SNOWY, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FireManticore", new MoCEntityData("FireManticore", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFireManticore.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("FrostManticore", new MoCEntityData("FrostManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFrostManticore.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
        MoCreatures.mocEntityMap.put("PlainManticore", new MoCEntityData("PlainManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityPlainManticore.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("ToxicManticore", new MoCEntityData("ToxicManticore", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityToxicManticore.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("GreenOgre", new MoCEntityData("GreenOgre", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityGreenOgre.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SWAMP, Type.LUSH, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("CaveOgre", new MoCEntityData("CaveOgre", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityCaveOgre.class, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FireOgre", new MoCEntityData("FireOgre", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFireOgre.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("Wraith", new MoCEntityData("Wraith", 3, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityWraith.class, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS, Type.DEAD, Type.DENSE, Type.SPOOKY))));
        MoCreatures.mocEntityMap.put("FlameWraith", new MoCEntityData("FlameWraith", 3, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityFlameWraith.class, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
        MoCreatures.mocEntityMap.put("Rat", new MoCEntityData("Rat", 2, overworld, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityRat.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
        MoCreatures.mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, nether, EntityClassification.MONSTER, new MobSpawnInfo.Spawners(MoCEntityHellRat.class, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.NETHER))));
        /*
         * Aquatic
         */
        MoCreatures.mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityBass.class, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityCod.class, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityDolphin.class, 6, 2, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityFishy.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityJellyFish.class, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntitySalmon.class, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityPiranha.class, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.JUNGLE, Type.LUSH))));
        MoCreatures.mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityMantaRay.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityStingRay.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
        MoCreatures.mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityShark.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityAnchovy.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN, Type.RIVER))));
        MoCreatures.mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityAngelFish.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.JUNGLE))));
        MoCreatures.mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityAngler.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityClownFish.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityGoldFish.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.RIVER))));
        MoCreatures.mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityHippoTang.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        MoCreatures.mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, overworld, EntityClassification.WATER_CREATURE, new MobSpawnInfo.Spawners(MoCEntityManderin.class, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
        /*
         * Ambient
         */
        MoCreatures.mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityAnt.class, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.MESA, Type.PLAINS, Type.SWAMP, Type.HOT, Type.DRY, Type.LUSH, Type.SPARSE, STEEP))));
        MoCreatures.mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityBee.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityButterfly.class, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
        MoCreatures.mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityCrab.class, 11, 1, 2), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER))));
        MoCreatures.mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityCricket.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
        MoCreatures.mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, overworldWyvernLair, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityDragonfly.class, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP, Type.BEACH, Type.WET, WYVERN_LAIR))));
        MoCreatures.mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityFirefly.class, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP, Type.LUSH, Type.DENSE, WYVERN_LAIR))));
        MoCreatures.mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityFly.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
        MoCreatures.mocEntityMap.put("Grasshopper", new MoCEntityData("Grasshopper", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityGrasshopper.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SAVANNA, WYVERN_LAIR))));
        MoCreatures.mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityMaggot.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
        MoCreatures.mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntitySnail.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.DENSE, Type.LUSH))));
        MoCreatures.mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, overworld, EntityClassification.AMBIENT, new MobSpawnInfo.Spawners(MoCEntityRoach.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.HOT))));
    }

    public static void registerSpawns() {
        // Read spawn properties from config
        MoCreatures.proxy.readMocConfigValues();

        // Iterate over all entities
        for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
            // Skip entities early that are disabled
            if (!entityData.getCanSpawn() || entityData.getFrequency() <= 0) {
                continue;
            }

            // Initialize list for valid spawn biomes
            List<Biome> spawnBiomes = new ArrayList<>();

            // Iterate over all biomes
            for (Biome biome : ForgeRegistries.BIOMES.getValuesCollection()) {
                boolean isBlocked = false;

                // Iterate over all blocked biome types
                for (BiomeDictionary.Type typeBlocked : entityData.getBlockedBiomeTypes()) {
                    // Check if biome has blocked biome type
                    // Set flag and skip when first blocked type is found
                    if (BiomeDictionary.hasType(biome, typeBlocked)) {
                        isBlocked = true;
                        break;
                    }
                }

                // Check if biome is not blocked and matches any of the allowed biome types
                // Add biome to list when valid
                if (!isBlocked && entityData.getBiomeTypes().stream().anyMatch(type -> BiomeDictionary.hasType(biome, type))) {
                    spawnBiomes.add(biome);

                    if (MoCreatures.proxy.debug) {
                        MoCreatures.LOGGER.debug("Entity {} is valid for biome {}", entityData.getEntityName(), biome.biomeName);
                    }
                }
            }

            // Register entity spawn with spawn properties from config and previously computed spawn biomes
            EntityRegistry.addSpawn(entityData.getEntityClass(), entityData.getFrequency(), entityData.getMinSpawn(), entityData.getMaxSpawn(), entityData.getTypeMoC(), spawnBiomes.toArray(new Biome[0]));
        }

        MoCreatures.LOGGER.info("Entity spawn registration complete.");
    }
}
