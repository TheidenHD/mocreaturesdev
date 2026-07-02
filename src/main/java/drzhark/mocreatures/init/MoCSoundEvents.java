/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;

import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

public class MoCSoundEvents {

    public static final DeferredRegister<SoundEvent> SOUND_DEFERRED = DeferredRegister.create(ForgeRegistries.SOUND_EVENTS, MoCConstants.MOD_ID);

    /* Entity */
    // Ambient
    public static final RegistryObject<SoundEvent> ENTITY_BEE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BEE_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BEE_ANGRY = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_CHIRP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_DRAGONFLY_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DRAGONFLY_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_FLY_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FLY_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_CHIRP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_FLY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_HURT = createSoundEvent();

    // Aquatic
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_ANGRY = createSoundEvent();

    // Hostile
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_ATTACH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_CLANG = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_STEP = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_MINI_GOLEM_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_MINI_GOLEM_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_RAT_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_RAT_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_RAT_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_ATTACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_STING = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_ATTACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_STEP = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_TRANSFORM = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_HURT = createSoundEvent();

    // Hunter
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_ATTACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_REST = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_ROLL = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_FOX_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FOX_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FOX_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_LION_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_LION_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_LION_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_LION_DEATH_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_LION_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_LION_HURT_BABY = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_ANGRY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_RATTLE = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_ATTACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_SWIM = createSoundEvent();

    // Neutral
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_ENT_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_ENT_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_ENT_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT_FEMALE = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_DIG = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_EAT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_ANGRY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DEATH_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DRINK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_EAT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HUNGRY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HURT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_PURR = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_TRAPPED = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_BED_POUR_FOOD = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_BED_POUR_MILK = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_FLAP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_STEP = createSoundEvent();

    // Passive
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_BLACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_BLUE = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_GREEN = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_RED = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_WHITE = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_YELLOW = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_LAND = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_LIFT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_DEER_AMBIENT_BABY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DEER_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DEER_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DEER_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_STEP = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_FILCH_LIZARD_HISS = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FILCH_LIZARD_DEATH = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_FISH_DEATH_VICIOUS = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FISH_FLOP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FISH_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_FISH_SWIM = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_GHOST = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_UNDEAD = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_ZEBRA = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY_GHOST = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY_UNDEAD = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH_GHOST = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH_UNDEAD = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_GHOST = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_UNDEAD = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_ZEBRA = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_DEATH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AMBIENT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_TURKEY_HURT = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_TURTLE_HISS = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_TURTLE_DEATH = createSoundEvent();

    /* Entity (Legacy) */
    // Hostile
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_DEATH_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_HURT_LEGACY = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_RAT_DEATH_LEGACY = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WEREHUMAN_DEATH_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WEREHUMAN_HURT_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_TRANSFORM_LEGACY = createSoundEvent();
    //
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_AMBIENT_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_DEATH_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_HURT_LEGACY = createSoundEvent();
    
    // Passive
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT_LEGACY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT_LEGACY = createSoundEvent();

    /* Generic */
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ARMOR_ON = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ARMOR_OFF = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ATTACH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_CLANG = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_DESTROY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_DRINK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_EAT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ENVENOM = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_EXPLODE = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_FLAP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_FLAP_SOFT = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_APPEAR = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_CONVERSION = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_CREEPY = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_ENCHANTED = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_LAUNCH = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ROPING = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_SMACK = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_STOMP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_WHIP = createSoundEvent();
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_WHOOSH = createSoundEvent();

    /* Music */
    // Record
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHUFFLING = createSoundEvent();


    /**
     * Create a {@link SoundEvent}.
     *
     * @param soundName The SoundEvent's name without the testmod3 prefix
     * @return The SoundEvent
     */
    @SuppressWarnings("removal")
    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        return SOUND_DEFERRED.register(soundName, () -> SoundEvent.createVariableRangeEvent(new ResourceLocation(MoCConstants.MOD_ID, soundName))
        );
    }

}