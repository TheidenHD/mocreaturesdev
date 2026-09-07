/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import com.google.common.base.Supplier;
import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.ambient.*;
import drzhark.mocreatures.entity.aquatic.*;
import drzhark.mocreatures.entity.hostile.*;
import drzhark.mocreatures.entity.hunter.*;
import drzhark.mocreatures.entity.hunter.MoCEntitySnake;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.neutral.*;
import drzhark.mocreatures.entity.neutral.MoCEntityBoar;
import drzhark.mocreatures.entity.passive.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.HashMap;
import java.util.Map;

@Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class MoCEntities {
//    public static BiomeDictionary.Type STEEP = BiomeDictionary.Type.getType("STEEP");
//    public static BiomeDictionary.Type WYVERN_LAIR = BiomeDictionary.Type.getType("WYVERN_LAIR");
//    public static Map<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>> ENTITIES = new HashMap<>();
//    private static final List<Item> SPAWN_EGGS = new ArrayList<>();


    public static final DeferredRegister<EntityType<?>> ENTITY_TYPES = DeferredRegister.create(ForgeRegistries.ENTITY_TYPES, MoCConstants.MOD_ID);
    private static final Map<RegistryObject<? extends EntityType<? extends LivingEntity>>, Supplier<AttributeSupplier.Builder>> ENTITY_ATTRIBUTES = new HashMap<>();


    private static <T extends LivingEntity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, Supplier<AttributeSupplier.Builder> attributes, int primaryColor, int secondaryColor) {
        return registerEntity(name, factory, category, width, height, attributes);
    }

    private static <T extends LivingEntity> RegistryObject<EntityType<T>> registerEntity(String name, EntityType.EntityFactory<T> factory, MobCategory category, float width, float height, Supplier<AttributeSupplier.Builder> attributes) {

        RegistryObject<EntityType<T>> entityType = ENTITY_TYPES.register(name.toLowerCase(),
                () -> EntityType.Builder.of(factory, category)
                        .sized(width, height)
                        .clientTrackingRange(80)
                        .updateInterval(3)
                        .build(ResourceLocation.fromNamespaceAndPath(MoCConstants.MOD_ID, name.toLowerCase()).toString()));

        // Store attributes
        ENTITY_ATTRIBUTES.put(entityType, attributes);

        return entityType;
    }
    /**
     * Animal
     */
    public static final RegistryObject<EntityType<MoCEntityBird>> BIRD = registerEntity("Bird", MoCEntityBird::new, MobCategory.CREATURE, 0.5F, 0.9F, MoCEntityBird::registerAttributes, 37109, 4609629);
    public static final RegistryObject<EntityType<MoCEntityBlackBear>> BEAR = registerEntity("BlackBear", MoCEntityBlackBear::new, MobCategory.CREATURE, 0.85F, 1.175F, MoCEntityBlackBear::registerAttributes,  986897, 8609347);
    public static final RegistryObject<EntityType<MoCEntityBoar>> BOAR = registerEntity("Boar", MoCEntityBoar::new, MobCategory.CREATURE, 0.9F, 0.9F, MoCEntityBoar::registerAttributes,  2037783, 4995892);
    public static final RegistryObject<EntityType<MoCEntityBunny>> BUNNY = registerEntity("Bunny", MoCEntityBunny::new, MobCategory.CREATURE, 0.5F, 0.5F, MoCEntityBunny::registerAttributes,  8741934, 14527570);
    public static final RegistryObject<EntityType<MoCEntityCrocodile>> CROCODILE = registerEntity("Crocodile", MoCEntityCrocodile::new, MobCategory.CREATURE, 0.9F, 0.5F, MoCEntityCrocodile::registerAttributes,  2698525, 10720356);
    public static final RegistryObject<EntityType<MoCEntityDuck>> DUCK = registerEntity("Duck", MoCEntityDuck::new, MobCategory.CREATURE, 0.4F, 0.7F, MoCEntityDuck::registerAttributes,  3161353, 14011565);
    public static final RegistryObject<EntityType<MoCEntityDeer>> DEER = registerEntity("Deer", MoCEntityDeer::new, MobCategory.CREATURE, 0.9F, 1.425F, MoCEntityDeer::registerAttributes,  11572843, 13752020);
    public static final RegistryObject<EntityType<MoCEntityElephant>> ELEPHANT = registerEntity("Elephant", MoCEntityElephant::new, MobCategory.CREATURE, 1.1F, 3F, MoCEntityElephant::registerAttributes,  4274216, 9337176);
    public static final RegistryObject<EntityType<MoCEntityEnt>> ENT = registerEntity("Ent", MoCEntityEnt::new, MobCategory.CREATURE, 1.4F, 7F, MoCEntityEnt::registerAttributes,  9794886, 5800509);
    public static final RegistryObject<EntityType<MoCEntityFilchLizard>> FILCH_LIZARD = registerEntity("FilchLizard", MoCEntityFilchLizard::new, MobCategory.CREATURE, 0.6f, 0.5f, MoCEntityFilchLizard::registerAttributes,  9930060, 5580310);
    public static final RegistryObject<EntityType<MoCEntityFox>> FOX = registerEntity("Fox", MoCEntityFox::new, MobCategory.CREATURE, 0.7F, 0.85F, MoCEntityFox::registerAttributes,  15966491, 4009236);
    public static final RegistryObject<EntityType<MoCEntityGoat>> GOAT = registerEntity("Goat", MoCEntityGoat::new, MobCategory.CREATURE, 0.8F, 0.9F, MoCEntityGoat::registerAttributes,  15262682, 4404517);
    public static final RegistryObject<EntityType<MoCEntityGrizzlyBear>> GRIZZLY_BEAR = registerEntity("GrizzlyBear", MoCEntityGrizzlyBear::new, MobCategory.CREATURE, 1.125F, 1.57F, MoCEntityGrizzlyBear::registerAttributes,  3547151, 11371099);
    public static final RegistryObject<EntityType<MoCEntityKitty>> KITTY = registerEntity("Kitty", MoCEntityKitty::new, MobCategory.CREATURE, 0.8F, 0.8F, MoCEntityKitty::registerAttributes,  16707009, 14861419);
    public static final RegistryObject<EntityType<MoCEntityKomodo>> KOMODO_DRAGON = registerEntity("KomodoDragon", MoCEntityKomodo::new, MobCategory.CREATURE, 1.25F, 0.9F, MoCEntityKomodo::registerAttributes,  8615512, 3025185);
    public static final RegistryObject<EntityType<MoCEntityLeoger>> LEOGER = registerEntity("Leoger", MoCEntityLeoger::new, MobCategory.CREATURE, 1.3F, 1.3815F, MoCEntityLeoger::registerAttributes,  13274957, 6638124);
    public static final RegistryObject<EntityType<MoCEntityLeopard>> LEOPARD = registerEntity("Leopard", MoCEntityLeopard::new, MobCategory.CREATURE, 1.165F, 1.01F, MoCEntityLeopard::registerAttributes,  13478009, 3682085);
    public static final RegistryObject<EntityType<MoCEntityLiard>> LIARD = registerEntity("Liard", MoCEntityLiard::new, MobCategory.CREATURE, 1.175F, 1.065F, MoCEntityLiard::registerAttributes,  11965543, 8215850);
    public static final RegistryObject<EntityType<MoCEntityLion>> LION = registerEntity("Lion", MoCEntityLion::new, MobCategory.CREATURE, 1.25F, 1.275F, MoCEntityLion::registerAttributes,  11503958, 2234383);
    public static final RegistryObject<EntityType<MoCEntityLiger>> LIGER = registerEntity("Liger", MoCEntityLiger::new, MobCategory.CREATURE, 1.35F, 1.43525F, MoCEntityLiger::registerAttributes,  13347170, 9068088);
    public static final RegistryObject<EntityType<MoCEntityLither>> LITHER = registerEntity("Lither", MoCEntityLither::new, MobCategory.CREATURE, 1.175F, 1.17F, MoCEntityLither::registerAttributes,  2234897, 7821878);
    public static final RegistryObject<EntityType<MoCEntityManticorePet>> MANTICORE_PET = registerEntity("ManticorePet", MoCEntityManticorePet::new, MobCategory.CREATURE, 1.4F, 1.3F, MoCEntityManticorePet::registerAttributes);
    public static final RegistryObject<EntityType<MoCEntityMole>> MOLE = registerEntity("Mole", MoCEntityMole::new, MobCategory.CREATURE, 1F, 0.5F, MoCEntityMole::registerAttributes,  263173, 10646113);
    public static final RegistryObject<EntityType<MoCEntityMouse>> MOUSE = registerEntity("Mouse", MoCEntityMouse::new, MobCategory.CREATURE, 0.45F, 0.3F, MoCEntityMouse::registerAttributes,  7428164, 15510186);
    public static final RegistryObject<EntityType<MoCEntityOstrich>> OSTRICH = registerEntity("Ostrich", MoCEntityOstrich::new, MobCategory.CREATURE, 0.8F, 2.225F, MoCEntityOstrich::registerAttributes,  12884106, 10646377);
    public static final RegistryObject<EntityType<MoCEntityPandaBear>> PANDA_BEAR = registerEntity("PandaBear", MoCEntityPandaBear::new, MobCategory.CREATURE, 0.8F, 1.05F, MoCEntityPandaBear::registerAttributes,  13354393, 789516);
    public static final RegistryObject<EntityType<MoCEntityPanthard>> PANTHARD = registerEntity("Panthard", MoCEntityPanthard::new, MobCategory.CREATURE, 1.14F, 1.063175F, MoCEntityPanthard::registerAttributes,  591108, 9005068);
    public static final RegistryObject<EntityType<MoCEntityPanther>> PANTHER = registerEntity("Panther", MoCEntityPanther::new, MobCategory.CREATURE, 1.175F, 1.065F, MoCEntityPanther::registerAttributes,  1709584, 16768078);
    public static final RegistryObject<EntityType<MoCEntityPanthger>> PANTHGER = registerEntity("Panthger", MoCEntityPanthger::new, MobCategory.CREATURE, 1.225F, 1.2225F, MoCEntityPanthger::registerAttributes,  2826517, 14348086);
    public static final RegistryObject<EntityType<MoCEntityPetScorpion>> PET_SCORPION = registerEntity("PetScorpion", MoCEntityPetScorpion::new, MobCategory.CREATURE, 1.4F, 0.9F, MoCEntityPetScorpion::registerAttributes);
    public static final RegistryObject<EntityType<MoCEntityPolarBear>> POLAR_BEAR = registerEntity("WildPolarBear", MoCEntityPolarBear::new, MobCategory.CREATURE, 1.5F, 1.834F, MoCEntityPolarBear::registerAttributes,  15131867, 11380879);
    public static final RegistryObject<EntityType<MoCEntityRaccoon>> RACCOON = registerEntity("Raccoon", MoCEntityRaccoon::new, MobCategory.CREATURE, 0.6F, 0.525F, MoCEntityRaccoon::registerAttributes,  6115913, 1578001);
    public static final RegistryObject<EntityType<MoCEntitySnake>> SNAKE = registerEntity("Snake", MoCEntitySnake::new, MobCategory.CREATURE, 1.4F, 0.5F, MoCEntitySnake::registerAttributes,  670976, 11309312);
    public static final RegistryObject<EntityType<MoCEntityTiger>> TIGER = registerEntity("Tiger", MoCEntityTiger::new, MobCategory.CREATURE, 1.25F, 1.275F, MoCEntityTiger::registerAttributes,  12476160, 2956299);
    public static final RegistryObject<EntityType<MoCEntityTurtle>> TURTLE = registerEntity("Turtle", MoCEntityTurtle::new, MobCategory.CREATURE, 0.6F, 0.425F, MoCEntityTurtle::registerAttributes,  6505237, 10524955);
    public static final RegistryObject<EntityType<MoCEntityTurkey>> TURKEY = registerEntity("Turkey", MoCEntityTurkey::new, MobCategory.CREATURE, 0.6F, 0.9F, MoCEntityTurkey::registerAttributes,  12268098, 6991322);
    public static final RegistryObject<EntityType<MoCEntityHorse>> WILDHORSE = registerEntity("WildHorse", MoCEntityHorse::new, MobCategory.CREATURE, 1.3964844F, 1.6F, MoCEntityHorse::registerAttributes,  9204829, 11379712);
    public static final RegistryObject<EntityType<MoCEntityWyvern>> WYVERN = registerEntity("Wyvern", MoCEntityWyvern::new, MobCategory.CREATURE, 1.45F, 1.55F, MoCEntityWyvern::registerAttributes,  11440923, 15526339);
    /**
     * Monster
     */
    public static final RegistryObject<EntityType<MoCEntityCaveOgre>> CAVE_OGRE = registerEntity("CaveOgre", MoCEntityCaveOgre::new, MobCategory.MONSTER, 1.8F, 3.05F, MoCEntityCaveOgre::registerAttributes,  5079480, 12581631);
    public static final RegistryObject<EntityType<MoCEntityFlameWraith>> FLAME_WRAITH = registerEntity("FlameWraith", MoCEntityFlameWraith::new, MobCategory.MONSTER, 0.6F, 2.0F, MoCEntityFlameWraith::registerAttributes,  8988239, 16748288);
    public static final RegistryObject<EntityType<MoCEntityFireOgre>> FIRE_OGRE = registerEntity("FireOgre", MoCEntityFireOgre::new, MobCategory.MONSTER, 1.8F, 3.05F, MoCEntityFireOgre::registerAttributes,  6882304, 16430080);
    public static final RegistryObject<EntityType<MoCEntityGreenOgre>> GREEN_OGRE = registerEntity("GreenOgre", MoCEntityGreenOgre::new, MobCategory.MONSTER, 1.8F, 3.05F, MoCEntityGreenOgre::registerAttributes,  1607501, 2032997);
    public static final RegistryObject<EntityType<MoCEntityGolem>> BIG_GOLEM = registerEntity("BigGolem", MoCEntityGolem::new, MobCategory.MONSTER, 1.8F, 4.3F, MoCEntityGolem::registerAttributes,  4868682, 52411);
    public static final RegistryObject<EntityType<MoCEntityHorseMob>> HORSEMOB = registerEntity("HorseMob", MoCEntityHorseMob::new, MobCategory.MONSTER, 1.3964844F, 1.6F, MoCEntityHorseMob::registerAttributes,  6326628, 12369062);
    public static final RegistryObject<EntityType<MoCEntityHellRat>> HELLRAT = registerEntity("HellRat", MoCEntityHellRat::new, MobCategory.MONSTER, 0.88F, 0.755F, MoCEntityHellRat::registerAttributes,  1049090, 15956249);
    public static final RegistryObject<EntityType<MoCEntityDarkManticore>> DARK_MANTICORE = registerEntity("DarkManticore", MoCEntityDarkManticore::new, MobCategory.MONSTER, 1.35F, 1.45F, MoCEntityDarkManticore::registerAttributes,  3289650, 657930);
    public static final RegistryObject<EntityType<MoCEntityFireManticore>> FIRE_MANTICORE = registerEntity("FireManticore", MoCEntityFireManticore::new, MobCategory.MONSTER, 1.35F, 1.45F, MoCEntityFireManticore::registerAttributes,  7148552, 2819585);
    public static final RegistryObject<EntityType<MoCEntityFrostManticore>> FROST_MANTICORE = registerEntity("FrostManticore", MoCEntityFrostManticore::new, MobCategory.MONSTER, 1.35F, 1.45F, MoCEntityFrostManticore::registerAttributes,  3559006, 2041389);
    public static final RegistryObject<EntityType<MoCEntityPlainManticore>> PLAIN_MANTICORE = registerEntity("PlainManticore", MoCEntityPlainManticore::new, MobCategory.MONSTER, 1.35F, 1.45F, MoCEntityPlainManticore::registerAttributes,  7623465, 5510656);
    public static final RegistryObject<EntityType<MoCEntityToxicManticore>> TOXIC_MANTICORE = registerEntity("ToxicManticore", MoCEntityToxicManticore::new, MobCategory.MONSTER, 1.35F, 1.45F, MoCEntityToxicManticore::registerAttributes,  6252034, 3365689);
    public static final RegistryObject<EntityType<MoCEntityMiniGolem>> MINI_GOLEM = registerEntity("MiniGolem", MoCEntityMiniGolem::new, MobCategory.MONSTER, 0.9F, 1.2F, MoCEntityMiniGolem::registerAttributes,  7895160, 8512741);
    public static final RegistryObject<EntityType<MoCEntityRat>> RAT = registerEntity("Rat", MoCEntityRat::new, MobCategory.MONSTER, 0.58F, 0.455F, MoCEntityRat::registerAttributes,  3685435, 15838633);
    public static final RegistryObject<EntityType<MoCEntitySilverSkeleton>> SILVER_SKELETON = registerEntity("SilverSkeleton", MoCEntitySilverSkeleton::new, MobCategory.MONSTER, 0.6F, 2.125F, MoCEntitySilverSkeleton::registerAttributes,  13421750, 8158847);
    public static final RegistryObject<EntityType<MoCEntityCaveScorpion>> CAVE_SCORPION = registerEntity("CaveScorpion", MoCEntityCaveScorpion::new, MobCategory.MONSTER, 1.4F, 0.9F, MoCEntityCaveScorpion::registerAttributes,  789516, 3223866);
    public static final RegistryObject<EntityType<MoCEntityDirtScorpion>> DIRT_SCORPION = registerEntity("DirtScorpion", MoCEntityDirtScorpion::new, MobCategory.MONSTER, 1.4F, 0.9F, MoCEntityDirtScorpion::registerAttributes,  4134919, 13139755);
    public static final RegistryObject<EntityType<MoCEntityFrostScorpion>> FROST_SCORPION = registerEntity("FrostScorpion", MoCEntityFrostScorpion::new, MobCategory.MONSTER, 1.4F, 0.9F, MoCEntityFrostScorpion::registerAttributes,  333608, 5218691);
    public static final RegistryObject<EntityType<MoCEntityFireScorpion>> FIRE_SCORPION = registerEntity("FireScorpion", MoCEntityFireScorpion::new, MobCategory.MONSTER, 1.4F, 0.9F, MoCEntityFireScorpion::registerAttributes,  2163457, 9515286);
    public static final RegistryObject<EntityType<MoCEntityUndeadScorpion>> UNDEAD_SCORPION = registerEntity("UndeadScorpion", MoCEntityUndeadScorpion::new, MobCategory.MONSTER, 1.4F, 0.9F, MoCEntityUndeadScorpion::registerAttributes,  1118208, 7899732);
    public static final RegistryObject<EntityType<MoCEntityWerewolf>> WEREWOLF = registerEntity("Werewolf", MoCEntityWerewolf::new, MobCategory.MONSTER, 0.7F, 2.0F, MoCEntityWerewolf::registerAttributes,  1970698, 7032379);
    public static final RegistryObject<EntityType<MoCEntityWraith>> WRAITH = registerEntity("Wraith", MoCEntityWraith::new, MobCategory.MONSTER, 0.6F, 2.0F, MoCEntityWraith::registerAttributes,  5987163, 16711680);
    public static final RegistryObject<EntityType<MoCEntityWWolf>> WWOLF = registerEntity("WWolf", MoCEntityWWolf::new, MobCategory.MONSTER, 0.8F, 1.1F, MoCEntityWWolf::registerAttributes,  5657166, 13223102);
    /**
     * Aquatic
     */
    public static final RegistryObject<EntityType<MoCEntityAnchovy>> ANCHOVY = registerEntity("Anchovy", MoCEntityAnchovy::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  7039838, 12763545);
    public static final RegistryObject<EntityType<MoCEntityAngelFish>> ANGELFISH = registerEntity("AngelFish", MoCEntityAngelFish::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  12040119, 15970609);
    public static final RegistryObject<EntityType<MoCEntityAngler>> ANGLER = registerEntity("Angler", MoCEntityAngler::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  5257257, 6225864);
    public static final RegistryObject<EntityType<MoCEntityBass>> BASS = registerEntity("Bass", MoCEntityBass::new, MobCategory.WATER_CREATURE, 0.7f, 0.45f, MoCEntityAquatic::registerAttributes,  4341299, 10051649);
    public static final RegistryObject<EntityType<MoCEntityClownFish>> CLOWNFISH = registerEntity("ClownFish", MoCEntityClownFish::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  16439491, 15425029);
    public static final RegistryObject<EntityType<MoCEntityCod>> COD = registerEntity("Cod", MoCEntityCod::new, MobCategory.WATER_CREATURE, 0.7f, 0.45f, MoCEntityAquatic::registerAttributes,  5459520, 14600592);
    public static final RegistryObject<EntityType<MoCEntityDolphin>> DOLPHIN = registerEntity("Dolphin", MoCEntityDolphin::new, MobCategory.WATER_CREATURE, 1.3F, 0.605F, MoCEntityAquatic::registerAttributes,  4086148, 11251396);
    public static final RegistryObject<EntityType<MoCEntityFishy>> FISHY = registerEntity("Fishy", MoCEntityFishy::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  5665535, 2037680);
    public static final RegistryObject<EntityType<MoCEntityGoldFish>> GOLDFISH = registerEntity("GoldFish", MoCEntityGoldFish::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  15577089, 16735257);
    public static final RegistryObject<EntityType<MoCEntityHippoTang>> HIPPOTANG = registerEntity("HippoTang", MoCEntityHippoTang::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  4280267, 12893441);
    public static final RegistryObject<EntityType<MoCEntityJellyFish>> JELLYFISH = registerEntity("JellyFish", MoCEntityJellyFish::new, MobCategory.WATER_CREATURE, 0.45F, 0.575F, MoCEntityAquatic::registerAttributes,  12758461, 9465021);
    public static final RegistryObject<EntityType<MoCEntityManderin>> MANDERIN = registerEntity("Manderin", MoCEntityManderin::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  14764801, 5935359);
    public static final RegistryObject<EntityType<MoCEntityPiranha>> PIRANHA = registerEntity("Piranha", MoCEntityPiranha::new, MobCategory.WATER_CREATURE, 0.5f, 0.3f, MoCEntityAquatic::registerAttributes,  10756121, 3160114);
    public static final RegistryObject<EntityType<MoCEntitySalmon>> SALMON = registerEntity("Salmon", MoCEntitySalmon::new, MobCategory.WATER_CREATURE, 0.7f, 0.45f, MoCEntityAquatic::registerAttributes,  5262951, 10716540);
    public static final RegistryObject<EntityType<MoCEntityMantaRay>> MANTARAY = registerEntity("MantaRay", MoCEntityMantaRay::new, MobCategory.WATER_CREATURE, 1.4F, 0.4F, MoCEntityAquatic::registerAttributes,  5791360, 11580358);
    public static final RegistryObject<EntityType<MoCEntityShark>> SHARK = registerEntity("Shark", MoCEntityShark::new, MobCategory.WATER_CREATURE, 1.65F, 0.9F, MoCEntityAquatic::registerAttributes,  3817558, 11580358);
    public static final RegistryObject<EntityType<MoCEntityStingRay>> STINGRAY = registerEntity("StingRay", MoCEntityStingRay::new, MobCategory.WATER_CREATURE, 0.7F, 0.3F, MoCEntityAquatic::registerAttributes,  3679519, 8418674);
    /**
     * Ambient
     */
    public static final RegistryObject<EntityType<MoCEntityAnt>> ANT = registerEntity("Ant", MoCEntityAnt::new, MobCategory.AMBIENT, 0.3F, 0.2F, MoCEntityAnt::registerAttributes,  5915945, 2693905);
    public static final RegistryObject<EntityType<MoCEntityBee>> BEE = registerEntity("Bee", MoCEntityBee::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityBee::registerAttributes,  15912747, 526604);
    public static final RegistryObject<EntityType<MoCEntityButterfly>> BUTTERFLY = registerEntity("ButterFly", MoCEntityButterfly::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityButterfly::registerAttributes,  12615169, 2956801);
    public static final RegistryObject<EntityType<MoCEntityCrab>> CRAB = registerEntity("Crab", MoCEntityCrab::new, MobCategory.AMBIENT, 0.45F, 0.3F, MoCEntityCrab::registerAttributes,  11880978, 15514213);
    public static final RegistryObject<EntityType<MoCEntityCricket>> CRICKET = registerEntity("Cricket", MoCEntityCricket::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityCricket::registerAttributes,  4071430, 8612672);
    public static final RegistryObject<EntityType<MoCEntityDragonfly>> DRAGONFLY = registerEntity("DragonFly", MoCEntityDragonfly::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityDragonfly::registerAttributes,  665770, 2207231);
    public static final RegistryObject<EntityType<MoCEntityFirefly>> FIREFLY = registerEntity("Firefly", MoCEntityFirefly::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityFirefly::registerAttributes,  2102294, 8501028);
    public static final RegistryObject<EntityType<MoCEntityFly>> FLY = registerEntity("Fly", MoCEntityFly::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityFly::registerAttributes,  1184284, 11077640);
    public static final RegistryObject<EntityType<MoCEntityGrasshopper>> GRASSHOPPER = registerEntity("Grasshopper", MoCEntityGrasshopper::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityGrasshopper::registerAttributes,  7830593, 3747075);
    public static final RegistryObject<EntityType<MoCEntityMaggot>> MAGGOT = registerEntity("Maggot", MoCEntityMaggot::new, MobCategory.AMBIENT, 0.2F, 0.2F, MoCEntityMaggot::registerAttributes,  14076037, 6839592);
    public static final RegistryObject<EntityType<MoCEntitySnail>> SNAIL = registerEntity("Snail", MoCEntitySnail::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntitySnail::registerAttributes,  10850932, 7225384);
    public static final RegistryObject<EntityType<MoCEntityRoach>> ROACH = registerEntity("Roach", MoCEntityRoach::new, MobCategory.AMBIENT, 0.4F, 0.3F, MoCEntityRoach::registerAttributes,  5185289, 10245148);
    /**
     * Other
     */
    public static final RegistryObject<EntityType<MoCEntityEgg>> EGG = registerEntity("Egg", MoCEntityEgg::new, MobCategory.MISC, 0.25F, 0.25F, MoCEntityEgg::registerAttributes, );
    public static final RegistryObject<EntityType<MoCEntityKittyBed>> KITTY_BED = registerEntity("KittyBed", MoCEntityKittyBed::new, MobCategory.MISC, 1.0F, 0.15F, MoCEntityKittyBed::registerAttributes);
    public static final RegistryObject<EntityType<MoCEntityLitterBox>> LITTERBOX = registerEntity("LitterBox", MoCEntityLitterBox::new, MobCategory.MISC, 1.0F, 0.15F, MoCEntityLitterBox::registerAttributes);
    public static final RegistryObject<EntityType<MoCEntityThrowableRock>> TROCK = createRock(MoCEntityThrowableRock::new, MobCategory.MISC, 1.0F, 1.0F, "TRock");
    static int MoCEntityID = 0;

//    private static <T extends Entity> EntityType createRock(EntityType.Builder<T> builder, String name) {
//        EntityType entity = builder.build(name);
//        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
//        return entity;
//    }
//
//    private static <T extends Entity> EntityType createEntityEntry(EntityType.Builder<T> builder, Supplier<AttributeSupplier.Builder> attributes, String name) {
//        EntityType entity = builder.build(name);
//        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
//        ENTITIES.put(entity, attributes);
//        return entity;
//    }
//
//    private static <T extends LivingEntity> EntityType createEntityEntry(EntityType.Builder<T> builder, Supplier<AttributeSupplier.Builder> attributes, String name, int primaryColorIn, int secondaryColorIn, EntitySpawnPlacementRegistry.PlacementType type, EntitySpawnPlacementRegistry.IPlacementPredicate placementPredicate) {
//        EntityType entity = builder.build(name);
//        entity.setRegistryName(new ResourceLocation(MoCConstants.MOD_PREFIX + name.toLowerCase()));
//        EntitySpawnPlacementRegistry.register(entity, type, Heightmap.Types.MOTION_BLOCKING_NO_LEAVES, placementPredicate);
//        Item spawnEgg = new SpawnEggItem(entity, primaryColorIn, secondaryColorIn, (new Item.Properties()).group(ItemGroup.MISC));
//        spawnEgg.setRegistryName(new ResourceLocation(MoCConstants.MOD_ID, name.toLowerCase() + "_spawn_egg"));
//        SPAWN_EGGS.add(spawnEgg);
//        ENTITIES.put(entity, attributes);
//        return entity;
//    }
//
//    public static void registerEntities() {
//        MoCreatures.LOGGER.info("Registering entities...");
//
//        ResourceKey<Level>[] overworld = new ResourceKey[]{Level.OVERWORLD};
//        ResourceKey<Level>[] nether = new ResourceKey[]{Level.NETHER};
//        ResourceKey<Level>[] overworldNether = new ResourceKey[]{Level.OVERWORLD, Level.NETHER};
//        ResourceKey<Level>[] wyvernLair = new ResourceKey[]{MoCreatures.proxy.wyvernDimension};
//        ResourceKey<Level>[] overworldWyvernLair = new ResourceKey[]{Level.OVERWORLD, MoCreatures.proxy.wyvernDimension};
//
//        /*
//         * Animal
//         */
//        MoCreatures.mocEntityMap.put("BlackBear", new MoCEntityData("BlackBear", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBlackBear.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS))));
//        MoCreatures.mocEntityMap.put("GrizzlyBear", new MoCEntityData("GrizzlyBear", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityGrizzlyBear.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
//        MoCreatures.mocEntityMap.put("WildPolarBear", new MoCEntityData("WildPolarBear", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPolarBear.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.SNOWY))));
//        MoCreatures.mocEntityMap.put("PandaBear", new MoCEntityData("PandaBear", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPandaBear.class, 7, 1, 3), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
//        MoCreatures.mocEntityMap.put("Bird", new MoCEntityData("Bird", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBird.class, 16, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.MESA, Type.LUSH, STEEP))));
//        MoCreatures.mocEntityMap.put("Boar", new MoCEntityData("Boar", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBoar.class, 12, 2, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Bunny", new MoCEntityData("Bunny", 4, overworldWyvernLair, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityBunny.class, 12, 2, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.SNOWY, Type.CONIFEROUS, WYVERN_LAIR, STEEP))));
//        MoCreatures.mocEntityMap.put("Crocodile", new MoCEntityData("Crocodile", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityCrocodile.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP))));
//        MoCreatures.mocEntityMap.put("Deer", new MoCEntityData("Deer", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityDeer.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.CONIFEROUS))));
//        MoCreatures.mocEntityMap.put("Duck", new MoCEntityData("Duck", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityDuck.class, 12, 2, 4), new ArrayList<>(Arrays.asList(Type.RIVER, Type.LUSH))));
//        MoCreatures.mocEntityMap.put("Elephant", new MoCEntityData("Elephant", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityElephant.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.JUNGLE, Type.SAVANNA, Type.SNOWY)), new ArrayList<>(Arrays.asList(Type.MESA))));
//        MoCreatures.mocEntityMap.put("Ent", new MoCEntityData("Ent", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityEnt.class, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
//        MoCreatures.mocEntityMap.put("FilchLizard", new MoCEntityData("FilchLizard", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityFilchLizard.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA, WYVERN_LAIR))));
//        MoCreatures.mocEntityMap.put("Fox", new MoCEntityData("Fox", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityFox.class, 10, 1, 1), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.CONIFEROUS))));
//        MoCreatures.mocEntityMap.put("Goat", new MoCEntityData("Goat", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityGoat.class, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.PLAINS, STEEP))));
//        MoCreatures.mocEntityMap.put("Kitty", new MoCEntityData("Kitty", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityKitty.class, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.FOREST)))); // spawns in villages
//        MoCreatures.mocEntityMap.put("KomodoDragon", new MoCEntityData("KomodoDragon", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityKomodo.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.SAVANNA))));
//        MoCreatures.mocEntityMap.put("Leopard", new MoCEntityData("Leopard", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityLeopard.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.SNOWY, Type.SAVANNA))));
//        MoCreatures.mocEntityMap.put("Lion", new MoCEntityData("Lion", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityLion.class, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY, Type.MESA))));
//        MoCreatures.mocEntityMap.put("Mole", new MoCEntityData("Mole", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityMole.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.LUSH, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Mouse", new MoCEntityData("Mouse", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityMouse.class, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
//        MoCreatures.mocEntityMap.put("Ostrich", new MoCEntityData("Ostrich", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityOstrich.class, 7, 1, 1), new ArrayList<>(Arrays.asList(Type.SAVANNA, Type.SANDY)), new ArrayList<>(Arrays.asList(Type.MESA))));
//        MoCreatures.mocEntityMap.put("Panther", new MoCEntityData("Panther", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityPanther.class, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
//        MoCreatures.mocEntityMap.put("Raccoon", new MoCEntityData("Raccoon", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityRaccoon.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST))));
//        MoCreatures.mocEntityMap.put("Snake", new MoCEntityData("Snake", 3, overworldWyvernLair, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntitySnake.class, 14, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.PLAINS, Type.FOREST, Type.SWAMP, Type.LUSH, Type.JUNGLE, WYVERN_LAIR, STEEP))));
//        MoCreatures.mocEntityMap.put("Tiger", new MoCEntityData("Tiger", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTiger.class, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE))));
//        MoCreatures.mocEntityMap.put("Turkey", new MoCEntityData("Turkey", 2, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTurkey.class, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Turtle", new MoCEntityData("Turtle", 3, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityTurtle.class, 12, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
//        MoCreatures.mocEntityMap.put("WildHorse", new MoCEntityData("WildHorse", 4, overworld, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityHorse.class, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SAVANNA))));
//        MoCreatures.mocEntityMap.put("Wyvern", new MoCEntityData("Wyvern", 3, wyvernLair, EnumCreatureType.CREATURE, new SpawnListEntry(MoCEntityWyvern.class, 12, 1, 3), new ArrayList<>(Arrays.asList(WYVERN_LAIR))));
//        /*
//         * Monster
//         */
//        MoCreatures.mocEntityMap.put("BigGolem", new MoCEntityData("BigGolem", 1, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(BIG_GOLEM, 3, 1, 1), new ArrayList<>(Arrays.asList(Type.SANDY, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
//        MoCreatures.mocEntityMap.put("MiniGolem", new MoCEntityData("MiniGolem", 2, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(MINI_GOLEM, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.WASTELAND))));
//        MoCreatures.mocEntityMap.put("HorseMob", new MoCEntityData("HorseMob", 3, overworldNether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HORSEMOB, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER, Type.PLAINS, Type.SAVANNA, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("CaveScorpion", new MoCEntityData("CaveScorpion", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CAVE_SCORPION, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.DRY, Type.HOT, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("DirtScorpion", new MoCEntityData("DirtScorpion", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(DIRT_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MESA, Type.DRY, Type.HOT))));
//        MoCreatures.mocEntityMap.put("FireScorpion", new MoCEntityData("FireScorpion", 3, nether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FIRE_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
//        MoCreatures.mocEntityMap.put("FrostScorpion", new MoCEntityData("FrostScorpion", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FROST_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
//        MoCreatures.mocEntityMap.put("UndeadScorpion", new MoCEntityData("UndeadScorpion", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(UNDEAD_SCORPION, 6, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("SilverSkeleton", new MoCEntityData("SilverSkeleton", 4, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(SILVER_SKELETON, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.SANDY, Type.SNOWY, Type.MESA, Type.PLAINS, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("Werewolf", new MoCEntityData("Werewolf", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(WEREWOLF, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.CONIFEROUS, Type.FOREST))));
//        MoCreatures.mocEntityMap.put("WWolf", new MoCEntityData("WWolf", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(WWOLF, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.FOREST, Type.SNOWY, Type.WASTELAND))));
//        MoCreatures.mocEntityMap.put("DarkManticore", new MoCEntityData("DarkManticore", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(DARK_MANTICORE, 5, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS, Type.SNOWY, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("FireManticore", new MoCEntityData("FireManticore", 3, nether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FIRE_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.NETHER))));
//        MoCreatures.mocEntityMap.put("FrostManticore", new MoCEntityData("FrostManticore", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FROST_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SNOWY))));
//        MoCreatures.mocEntityMap.put("PlainManticore", new MoCEntityData("PlainManticore", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(PLAIN_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.SANDY, Type.MOUNTAIN, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("ToxicManticore", new MoCEntityData("ToxicManticore", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(TOXIC_MANTICORE, 8, 1, 3), new ArrayList<>(Arrays.asList(Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("GreenOgre", new MoCEntityData("GreenOgre", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(GREEN_OGRE, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.PLAINS, Type.SWAMP, Type.LUSH, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("CaveOgre", new MoCEntityData("CaveOgre", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(CAVE_OGRE, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.SANDY, Type.FOREST, Type.SNOWY, Type.JUNGLE, Type.HILLS, Type.MESA, Type.MOUNTAIN, Type.PLAINS, Type.SWAMP, Type.WASTELAND, Type.DEAD, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("FireOgre", new MoCEntityData("FireOgre", 3, nether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FIRE_OGRE, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
//        MoCreatures.mocEntityMap.put("Wraith", new MoCEntityData("Wraith", 3, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(WRAITH, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.CONIFEROUS, Type.DEAD, Type.DENSE, Type.SPOOKY))));
//        MoCreatures.mocEntityMap.put("FlameWraith", new MoCEntityData("FlameWraith", 3, nether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(FLAME_WRAITH, 5, 1, 2), new ArrayList<>(Arrays.asList(Type.NETHER))));
//        MoCreatures.mocEntityMap.put("Rat", new MoCEntityData("Rat", 2, overworld, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(RAT, 7, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS, Type.MESA, STEEP))));
//        MoCreatures.mocEntityMap.put("HellRat", new MoCEntityData("HellRat", 4, nether, MobCategory.MONSTER, new MobSpawnSettings.SpawnerData(HELLRAT, 6, 1, 4), new ArrayList<>(Arrays.asList(Type.NETHER))));
//        /*
//         * Aquatic
//         */
//        MoCreatures.mocEntityMap.put("Bass", new MoCEntityData("Bass", 4, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(BASS, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Cod", new MoCEntityData("Cod", 4, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(COD, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("Dolphin", new MoCEntityData("Dolphin", 3, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(DOLPHIN, 6, 2, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("Fishy", new MoCEntityData("Fishy", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(FISHY, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("JellyFish", new MoCEntityData("JellyFish", 4, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(JELLYFISH, 8, 1, 4), new ArrayList<>(Arrays.asList(Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("Salmon", new MoCEntityData("Salmon", 4, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(SALMON, 10, 1, 4), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER, Type.OCEAN, Type.RIVER, Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Piranha", new MoCEntityData("Piranha", 4, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(PIRANHA, 4, 1, 3), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.JUNGLE, Type.LUSH))));
//        MoCreatures.mocEntityMap.put("MantaRay", new MoCEntityData("MantaRay", 3, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(MANTARAY, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("StingRay", new MoCEntityData("StingRay", 3, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(STINGRAY, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER))));
//        MoCreatures.mocEntityMap.put("Shark", new MoCEntityData("Shark", 3, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(SHARK, 6, 1, 2), new ArrayList<>(Arrays.asList(Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("Anchovy", new MoCEntityData("Anchovy", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ANCHOVY, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN, Type.RIVER))));
//        MoCreatures.mocEntityMap.put("AngelFish", new MoCEntityData("AngelFish", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ANGELFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.SWAMP, Type.RIVER, Type.JUNGLE))));
//        MoCreatures.mocEntityMap.put("Angler", new MoCEntityData("Angler", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(ANGLER, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("ClownFish", new MoCEntityData("ClownFish", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(CLOWNFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("GoldFish", new MoCEntityData("GoldFish", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(GOLDFISH, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.RIVER))));
//        MoCreatures.mocEntityMap.put("HippoTang", new MoCEntityData("HippoTang", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(HIPPOTANG, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
//        MoCreatures.mocEntityMap.put("Manderin", new MoCEntityData("Manderin", 6, overworld, MobCategory.WATER_CREATURE, new MobSpawnSettings.SpawnerData(MANDERIN, 12, 1, 6), new ArrayList<>(Arrays.asList(Type.BEACH, Type.OCEAN))));
//        /*
//         * Ambient
//         */
//        MoCreatures.mocEntityMap.put("Ant", new MoCEntityData("Ant", 4, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ANT, 12, 1, 4), new ArrayList<>(Arrays.asList(BiomeTags.IS_FOREST, Type.JUNGLE, Type.MESA, Type.PLAINS, Type.SWAMP, Type.HOT, Type.DRY, Type.LUSH, Type.SPARSE, STEEP))));
//        MoCreatures.mocEntityMap.put("Bee", new MoCEntityData("Bee", 3, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(BEE, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("ButterFly", new MoCEntityData("ButterFly", 3, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(BUTTERFLY, 12, 1, 4), new ArrayList<>(Arrays.asList(Type.FOREST, Type.PLAINS))));
//        MoCreatures.mocEntityMap.put("Crab", new MoCEntityData("Crab", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(CRAB, 11, 1, 2), new ArrayList<>(Arrays.asList(Type.BEACH, Type.WATER))));
//        MoCreatures.mocEntityMap.put("Cricket", new MoCEntityData("Cricket", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(CRICKET, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP))));
//        MoCreatures.mocEntityMap.put("DragonFly", new MoCEntityData("DragonFly", 2, overworldWyvernLair, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(DRAGONFLY, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SWAMP, Type.BEACH, Type.WET))));
//        MoCreatures.mocEntityMap.put("Firefly", new MoCEntityData("Firefly", 3, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(FIREFLY, 9, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.SWAMP, Type.LUSH, Type.DENSE))));
//        MoCreatures.mocEntityMap.put("Fly", new MoCEntityData("Fly", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(FLY, 12, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
//        MoCreatures.mocEntityMap.put("Grasshopper", new MoCEntityData("Grasshopper", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(GRASSHOPPER, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.PLAINS, Type.SAVANNA))));
//        MoCreatures.mocEntityMap.put("Maggot", new MoCEntityData("Maggot", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(MAGGOT, 8, 1, 2), new ArrayList<>(Arrays.asList(Type.JUNGLE, Type.MESA, Type.WET, Type.SWAMP, Type.HOT))));
//        MoCreatures.mocEntityMap.put("Snail", new MoCEntityData("Snail", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(SNAIL, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.FOREST, Type.JUNGLE, Type.DENSE, Type.LUSH))));
//        MoCreatures.mocEntityMap.put("Roach", new MoCEntityData("Roach", 2, overworld, MobCategory.AMBIENT, new MobSpawnSettings.SpawnerData(ROACH, 10, 1, 2), new ArrayList<>(Arrays.asList(Type.HOT))));
//    }
//
//    @SubscribeEvent
//    public static void registerEntities(final RegistryEvent.Register<EntityType<?>> event) {
//        for (EntityType<?> entity : ENTITIES.keySet()) {
//            event.getRegistry().register(entity);
//        }
//        event.getRegistry().register(TROCK);
//    }
//
//    @SubscribeEvent
//    public static void addFreshEntityAttributes(EntityAttributeCreationEvent event) {
//        for (Map.Entry<EntityType<? extends LivingEntity>, Supplier<AttributeSupplier.Builder>> entry : ENTITIES.entrySet()) {
//            AttributeModifierMap map = entry.getValue().get().create();
//            event.put(entry.getKey(), map);
//
//            // ✅ Attribute validation log
//            if (!map.hasAttribute(Attributes.FOLLOW_RANGE)) {
//                //MoCreatures.LOGGER.warn("[Attribute Missing] " + entry.getKey().getRegistryName() + " lacks FOLLOW_RANGE");
//            } else {
//                //MoCreatures.LOGGER.debug("[Attribute OK] " + entry.getKey().getRegistryName() + " includes FOLLOW_RANGE");
//            }
//        }
//    }
//
//
//    @SubscribeEvent
//    public static void registerSpawnEggs(RegistryEvent.Register<Item> event) {
//        for (Item spawnEgg : SPAWN_EGGS) {
//            Preconditions.checkNotNull(spawnEgg.getRegistryName(), "registryName");
//            event.getRegistry().register(spawnEgg);
//        }
//    }
//
//    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
//    public static class RegistrationHandler {
//
//        @SubscribeEvent(priority = EventPriority.HIGH)
//        public static void registerSpawns(BiomeLoadingEvent event) {
//            if (event.getName() != null) {
//                Biome biome = ForgeRegistries.BIOMES.getValue(event.getName());
//                if (biome == null) return;
//
//                RegistryKey<Biome> biomeKey = RegistryKey.getOrCreateKey(ForgeRegistries.Keys.BIOMES, event.getName());
//                Set<BiomeDictionary.Type> biomeTypes = BiomeDictionary.getTypes(biomeKey);
//
//                for (MoCEntityData entityData : MoCreatures.mocEntityMap.values()) {
//                    if (!entityData.getCanSpawn() || entityData.getFrequency() <= 0) {
//                        //MoCreatures.LOGGER.debug("[Spawn Skip] " + entityData.getEntityName() + " is disabled or has 0 frequency");
//                        continue;
//                    }
//
//                    List<BiomeDictionary.Type> includeList = entityData.getBiomeTypes();
//                    List<BiomeDictionary.Type> excludeList = entityData.getBlockedBiomeTypes();
//
//                    boolean biomeAllowed = biomeTypes.stream().noneMatch(excludeList::contains)
//                            && biomeTypes.stream().anyMatch(includeList::contains);
//
//                    if (!biomeAllowed) {
//                        /*MoCreatures.LOGGER.debug("[Biome Skip] " + entityData.getEntityName()
//                                + " does not match biome " + event.getName()
//                                + " (types=" + biomeTypes + ", includes=" + includeList + ", excludes=" + excludeList + ")");*/
//                        continue;
//                    }
//
//                    event.getSpawns().getSpawner(entityData.getType()).add(entityData.getSpawnListEntry());
//
//                    /*MoCreatures.LOGGER.info("[Spawn Registered] " + entityData.getEntityName()
//                            + " in biome " + event.getName()
//                            + " with weight=" + entityData.getSpawnListEntry().itemWeight
//                            + ", min=" + entityData.getSpawnListEntry().minCount
//                            + ", max=" + entityData.getSpawnListEntry().maxCount);*/
//                }
//            }
//        }
//    }
}
