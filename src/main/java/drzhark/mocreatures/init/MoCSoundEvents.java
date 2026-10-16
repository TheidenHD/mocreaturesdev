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
    public static final RegistryObject<SoundEvent> ENTITY_BEE_AMBIENT = createSoundEvent("entity.bee.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_BEE_HURT = createSoundEvent("entity.bee.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_BEE_ANGRY = createSoundEvent("entity.bee.angry");
    //
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_AMBIENT = createSoundEvent("entity.cricket.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_CHIRP = createSoundEvent("entity.cricket.chirp");
    public static final RegistryObject<SoundEvent> ENTITY_CRICKET_HURT = createSoundEvent("entity.cricket.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_DRAGONFLY_AMBIENT = createSoundEvent("entity.dragonfly.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_DRAGONFLY_HURT = createSoundEvent("entity.dragonfly.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_FLY_AMBIENT = createSoundEvent("entity.fly.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_FLY_HURT = createSoundEvent("entity.fly.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_AMBIENT = createSoundEvent("entity.grasshopper.ambient"); // TODO
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_CHIRP = createSoundEvent("entity.grasshopper.chirp");
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_FLY = createSoundEvent("entity.grasshopper.fly");
    public static final RegistryObject<SoundEvent> ENTITY_GRASSHOPPER_HURT = createSoundEvent("entity.grasshopper.hurt");

    // Aquatic
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_DEATH = createSoundEvent("entity.dolphin.death");
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_HURT = createSoundEvent("entity.dolphin.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_AMBIENT = createSoundEvent("entity.dolphin.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_DOLPHIN_ANGRY = createSoundEvent("entity.dolphin.angry");

    // Hostile
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_AMBIENT = createSoundEvent("entity.big_golem.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_ATTACH = createSoundEvent("entity.big_golem.attach");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_CLANG = createSoundEvent("entity.big_golem.clang");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_DEATH = createSoundEvent("entity.big_golem.death");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_HURT = createSoundEvent("entity.big_golem.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_STEP = createSoundEvent("entity.big_golem.step");
    //
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_AMBIENT = createSoundEvent("entity.hell_rat.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_DEATH = createSoundEvent("entity.hell_rat.death");
    public static final RegistryObject<SoundEvent> ENTITY_HELL_RAT_HURT = createSoundEvent("entity.hell_rat.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_MINI_GOLEM_DEATH = createSoundEvent("entity.mini_golem.death");
    public static final RegistryObject<SoundEvent> ENTITY_MINI_GOLEM_HURT = createSoundEvent("entity.mini_golem.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_AMBIENT = createSoundEvent("entity.ogre.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_DEATH = createSoundEvent("entity.ogre.death");
    public static final RegistryObject<SoundEvent> ENTITY_OGRE_HURT = createSoundEvent("entity.ogre.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_RAT_AMBIENT = createSoundEvent("entity.rat.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_RAT_DEATH = createSoundEvent("entity.rat.death");
    public static final RegistryObject<SoundEvent> ENTITY_RAT_HURT = createSoundEvent("entity.rat.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_AMBIENT = createSoundEvent("entity.scorpion.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_ATTACK = createSoundEvent("entity.scorpion.attack");
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_DEATH = createSoundEvent("entity.scorpion.death");
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_HURT = createSoundEvent("entity.scorpion.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SCORPION_STING = createSoundEvent("entity.scorpion.sting");
    //
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_AMBIENT = createSoundEvent("entity.silver_skeleton.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_ATTACK = createSoundEvent("entity.silver_skeleton.attack");
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_DEATH = createSoundEvent("entity.silver_skeleton.death");
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_HURT = createSoundEvent("entity.silver_skeleton.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SILVER_SKELETON_STEP = createSoundEvent("entity.silver_skeleton.step");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_AMBIENT = createSoundEvent("entity.werewolf.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_DEATH = createSoundEvent("entity.werewolf.death");
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_HURT = createSoundEvent("entity.werewolf.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_TRANSFORM = createSoundEvent("entity.werewolf.transform");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_AMBIENT = createSoundEvent("entity.wraith.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_DEATH = createSoundEvent("entity.wraith.death");
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_HURT = createSoundEvent("entity.wraith.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_AMBIENT = createSoundEvent("entity.wolf.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_DEATH = createSoundEvent("entity.wolf.death");
    public static final RegistryObject<SoundEvent> ENTITY_WOLF_HURT = createSoundEvent("entity.wolf.hurt");

    // Hunter
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_AMBIENT = createSoundEvent("entity.bear.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_DEATH = createSoundEvent("entity.bear.death");
    public static final RegistryObject<SoundEvent> ENTITY_BEAR_HURT = createSoundEvent("entity.bear.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_AMBIENT = createSoundEvent("entity.crocodile.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_DEATH = createSoundEvent("entity.crocodile.death");
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_HURT = createSoundEvent("entity.crocodile.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_ATTACK = createSoundEvent("entity.crocodile.attack");
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_REST = createSoundEvent("entity.crocodile.rest");
    public static final RegistryObject<SoundEvent> ENTITY_CROCODILE_ROLL = createSoundEvent("entity.crocodile.roll");
    //
    public static final RegistryObject<SoundEvent> ENTITY_FOX_AMBIENT = createSoundEvent("entity.fox.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_FOX_DEATH = createSoundEvent("entity.fox.death");
    public static final RegistryObject<SoundEvent> ENTITY_FOX_HURT = createSoundEvent("entity.fox.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_LION_AMBIENT = createSoundEvent("entity.lion.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_LION_AMBIENT_BABY = createSoundEvent("entity.lion.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_LION_DEATH = createSoundEvent("entity.lion.death");
    public static final RegistryObject<SoundEvent> ENTITY_LION_DEATH_BABY = createSoundEvent("entity.lion.death_baby");
    public static final RegistryObject<SoundEvent> ENTITY_LION_HURT = createSoundEvent("entity.lion.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_LION_HURT_BABY = createSoundEvent("entity.lion.hurt_baby");
    //
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_AMBIENT = createSoundEvent("entity.raccoon.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_DEATH = createSoundEvent("entity.raccoon.death");
    public static final RegistryObject<SoundEvent> ENTITY_RACCOON_HURT = createSoundEvent("entity.raccoon.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_AMBIENT = createSoundEvent("entity.snake.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_ANGRY = createSoundEvent("entity.snake.angry");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_DEATH = createSoundEvent("entity.snake.death");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_HURT = createSoundEvent("entity.snake.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_RATTLE = createSoundEvent("entity.snake.rattle");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_ATTACK = createSoundEvent("entity.snake.attack");
    public static final RegistryObject<SoundEvent> ENTITY_SNAKE_SWIM = createSoundEvent("entity.snake.swim");

    // Neutral
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_AMBIENT_BABY = createSoundEvent("entity.elephant.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_AMBIENT = createSoundEvent("entity.elephant.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_DEATH = createSoundEvent("entity.elephant.death");
    public static final RegistryObject<SoundEvent> ENTITY_ELEPHANT_HURT = createSoundEvent("entity.elephant.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_ENT_AMBIENT = createSoundEvent("entity.ent.ambient"); // TODO
    public static final RegistryObject<SoundEvent> ENTITY_ENT_DEATH = createSoundEvent("entity.ent.death"); // TODO
    public static final RegistryObject<SoundEvent> ENTITY_ENT_HURT = createSoundEvent("entity.ent.hurt"); // TODO
    //
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT = createSoundEvent("entity.goat.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT_BABY = createSoundEvent("entity.goat.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_AMBIENT_FEMALE = createSoundEvent("entity.goat.ambient_female");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_DEATH = createSoundEvent("entity.goat.death");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_DIG = createSoundEvent("entity.goat.dig");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_EAT = createSoundEvent("entity.goat.eat");
    public static final RegistryObject<SoundEvent> ENTITY_GOAT_HURT = createSoundEvent("entity.goat.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_AMBIENT = createSoundEvent("entity.kitty.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_AMBIENT_BABY = createSoundEvent("entity.kitty.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_ANGRY = createSoundEvent("entity.kitty.angry");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DEATH = createSoundEvent("entity.kitty.death");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DEATH_BABY = createSoundEvent("entity.kitty.death_baby");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_DRINK = createSoundEvent("entity.kitty.drink");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_EAT = createSoundEvent("entity.kitty.eat");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HUNGRY = createSoundEvent("entity.kitty.hungry");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HURT = createSoundEvent("entity.kitty.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_HURT_BABY = createSoundEvent("entity.kitty.hurt_baby");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_PURR = createSoundEvent("entity.kitty.purr");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_TRAPPED = createSoundEvent("entity.kitty.trapped");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_BED_POUR_FOOD = createSoundEvent("entity.kitty_bed.pour_food");
    public static final RegistryObject<SoundEvent> ENTITY_KITTY_BED_POUR_MILK = createSoundEvent("entity.kitty_bed.pour_milk");
    //
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_AMBIENT = createSoundEvent("entity.ostrich.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_AMBIENT_BABY = createSoundEvent("entity.ostrich.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_DEATH = createSoundEvent("entity.ostrich.death");
    public static final RegistryObject<SoundEvent> ENTITY_OSTRICH_HURT = createSoundEvent("entity.ostrich.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_AMBIENT = createSoundEvent("entity.wyvern.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_DEATH = createSoundEvent("entity.wyvern.death");
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_FLAP = createSoundEvent("entity.wyvern.flap");
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_HURT = createSoundEvent("entity.wyvern.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_WYVERN_STEP = createSoundEvent("entity.wyvern.step");

    // Passive
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_BLACK = createSoundEvent("entity.bird.ambient_black");
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_BLUE = createSoundEvent("entity.bird.ambient_blue");
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_GREEN = createSoundEvent("entity.bird.ambient_green");
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_RED = createSoundEvent("entity.bird.ambient_red");
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_WHITE = createSoundEvent("entity.bird.ambient_white");
    public static final RegistryObject<SoundEvent> ENTITY_BIRD_AMBIENT_YELLOW = createSoundEvent("entity.bird.ambient_yellow");
    //
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_DEATH = createSoundEvent("entity.bunny.death");
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_HURT = createSoundEvent("entity.bunny.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_LAND = createSoundEvent("entity.bunny.land");
    public static final RegistryObject<SoundEvent> ENTITY_BUNNY_LIFT = createSoundEvent("entity.bunny.lift");
    //
    public static final RegistryObject<SoundEvent> ENTITY_DEER_AMBIENT_BABY = createSoundEvent("entity.deer.ambient_baby");
    public static final RegistryObject<SoundEvent> ENTITY_DEER_AMBIENT = createSoundEvent("entity.deer.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_DEER_DEATH = createSoundEvent("entity.deer.death");
    public static final RegistryObject<SoundEvent> ENTITY_DEER_HURT = createSoundEvent("entity.deer.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT = createSoundEvent("entity.duck.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT = createSoundEvent("entity.duck.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_STEP = createSoundEvent("entity.duck.step");
    //
    public static final RegistryObject<SoundEvent> ENTITY_FILCH_LIZARD_HISS = createSoundEvent("entity.filch_lizard.hiss");
    public static final RegistryObject<SoundEvent> ENTITY_FILCH_LIZARD_DEATH = createSoundEvent("entity.filch_lizard.death");
    //
    public static final RegistryObject<SoundEvent> ENTITY_FISH_DEATH_VICIOUS = createSoundEvent("entity.fish.death_vicious");
    public static final RegistryObject<SoundEvent> ENTITY_FISH_FLOP = createSoundEvent("entity.fish.flop");
    public static final RegistryObject<SoundEvent> ENTITY_FISH_HURT = createSoundEvent("entity.fish.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_FISH_SWIM = createSoundEvent("entity.fish.swim");
    //
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY = createSoundEvent("entity.horse.angry");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT = createSoundEvent("entity.horse.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_GHOST = createSoundEvent("entity.horse.ambient_ghost");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_UNDEAD = createSoundEvent("entity.horse.ambient_undead");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_AMBIENT_ZEBRA = createSoundEvent("entity.zebra.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY_GHOST = createSoundEvent("entity.horse.angry_ghost");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_ANGRY_UNDEAD = createSoundEvent("entity.horse.angry_undead");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH = createSoundEvent("entity.horse.death");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH_GHOST = createSoundEvent("entity.horse.death_ghost");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_DEATH_UNDEAD = createSoundEvent("entity.horse.death_undead");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT = createSoundEvent("entity.horse.hurt");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_GHOST = createSoundEvent("entity.horse.hurt_ghost");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_UNDEAD = createSoundEvent("entity.horse.hurt_undead");
    public static final RegistryObject<SoundEvent> ENTITY_HORSE_HURT_ZEBRA = createSoundEvent("entity.zebra.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_AMBIENT = createSoundEvent("entity.mole.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_DEATH = createSoundEvent("entity.mole.death");
    public static final RegistryObject<SoundEvent> ENTITY_MOLE_HURT = createSoundEvent("entity.mole.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_AMBIENT = createSoundEvent("entity.mouse.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_DEATH = createSoundEvent("entity.mouse.death");
    public static final RegistryObject<SoundEvent> ENTITY_MOUSE_HURT = createSoundEvent("entity.mouse.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_TURKEY_AMBIENT = createSoundEvent("entity.turkey.ambient");
    public static final RegistryObject<SoundEvent> ENTITY_TURKEY_HURT = createSoundEvent("entity.turkey.hurt");
    //
    public static final RegistryObject<SoundEvent> ENTITY_TURTLE_HISS = createSoundEvent("entity.turtle.hiss");
    public static final RegistryObject<SoundEvent> ENTITY_TURTLE_DEATH = createSoundEvent("entity.turtle.death");

    /* Entity (Legacy) */
    // Hostile
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_DEATH_LEGACY = createSoundEvent("entity.big_golem.death_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_BIG_GOLEM_HURT_LEGACY = createSoundEvent("entity.big_golem.hurt_legacy");
    //
    public static final RegistryObject<SoundEvent> ENTITY_RAT_DEATH_LEGACY = createSoundEvent("entity.rat.death_legacy");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WEREHUMAN_DEATH_LEGACY = createSoundEvent("entity.werehuman.death_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_WEREHUMAN_HURT_LEGACY = createSoundEvent("entity.werehuman.hurt_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_WEREWOLF_TRANSFORM_LEGACY = createSoundEvent("entity.werewolf.transform_legacy");
    //
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_AMBIENT_LEGACY = createSoundEvent("entity.wraith.ambient_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_DEATH_LEGACY = createSoundEvent("entity.wraith.death_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_WRAITH_HURT_LEGACY = createSoundEvent("entity.wraith.hurt_legacy");

    // Passive
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_AMBIENT_LEGACY = createSoundEvent("entity.duck.ambient_legacy");
    public static final RegistryObject<SoundEvent> ENTITY_DUCK_HURT_LEGACY = createSoundEvent("entity.duck.hurt_legacy");

    /* Generic */
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ARMOR_ON = createSoundEvent("entity.generic.armor_on");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ARMOR_OFF = createSoundEvent("entity.generic.armor_off");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ATTACH = createSoundEvent("entity.generic.attach");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_CLANG = createSoundEvent("entity.generic.clang");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_DESTROY = createSoundEvent("entity.generic.destroy");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_DRINK = createSoundEvent("entity.generic.drink");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_EAT = createSoundEvent("entity.generic.eat");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ENVENOM = createSoundEvent("entity.generic.envenom");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_EXPLODE = createSoundEvent("entity.generic.explode");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_FLAP = createSoundEvent("entity.generic.flap");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_FLAP_SOFT = createSoundEvent("entity.generic.flap_soft");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_APPEAR = createSoundEvent("entity.generic.magic_appear");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_CONVERSION = createSoundEvent("entity.generic.magic_conversion");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_CREEPY = createSoundEvent("entity.generic.magic_creepy");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_MAGIC_ENCHANTED = createSoundEvent("entity.generic.magic_enchanted");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_LAUNCH = createSoundEvent("entity.generic.launch");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_ROPING = createSoundEvent("entity.generic.roping");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_SMACK = createSoundEvent("entity.generic.smack");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_STOMP = createSoundEvent("entity.generic.stomp");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_WHIP = createSoundEvent("entity.generic.whip");
    public static final RegistryObject<SoundEvent> ENTITY_GENERIC_WHOOSH = createSoundEvent("entity.generic.whoosh");

    /* Music */
    // Record
    public static final RegistryObject<SoundEvent> MUSIC_DISC_SHUFFLING = createSoundEvent("music_disc.shuffling");


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