/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.init;

import drzhark.mocreatures.MoCConstants;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;

import net.minecraftforge.fml.RegistryObject;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;

public class MoCSoundEvents {
    /* Entity */
    // Ambient
    public static final SoundEvent ENTITY_BEE_AMBIENT;
    public static final SoundEvent ENTITY_BEE_HURT;
    public static final SoundEvent ENTITY_BEE_ANGRY;
    //
    public static final SoundEvent ENTITY_CRICKET_AMBIENT;
    public static final SoundEvent ENTITY_CRICKET_CHIRP;
    public static final SoundEvent ENTITY_CRICKET_HURT;
    //
    public static final SoundEvent ENTITY_DRAGONFLY_AMBIENT;
    public static final SoundEvent ENTITY_DRAGONFLY_HURT;
    //
    public static final SoundEvent ENTITY_FLY_AMBIENT;
    public static final SoundEvent ENTITY_FLY_HURT;
    //
    public static final SoundEvent ENTITY_GRASSHOPPER_AMBIENT;
    public static final SoundEvent ENTITY_GRASSHOPPER_CHIRP;
    public static final SoundEvent ENTITY_GRASSHOPPER_FLY;
    public static final SoundEvent ENTITY_GRASSHOPPER_HURT;

    // Aquatic
    public static final SoundEvent ENTITY_DOLPHIN_DEATH;
    public static final SoundEvent ENTITY_DOLPHIN_HURT;
    public static final SoundEvent ENTITY_DOLPHIN_AMBIENT;
    public static final SoundEvent ENTITY_DOLPHIN_ANGRY;

    // Hostile
    public static final SoundEvent ENTITY_BIG_GOLEM_AMBIENT;
    public static final SoundEvent ENTITY_BIG_GOLEM_ATTACH;
    public static final SoundEvent ENTITY_BIG_GOLEM_CLANG;
    public static final SoundEvent ENTITY_BIG_GOLEM_DEATH;
    public static final SoundEvent ENTITY_BIG_GOLEM_HURT;
    public static final SoundEvent ENTITY_BIG_GOLEM_STEP;
    //
    public static final SoundEvent ENTITY_HELL_RAT_AMBIENT;
    public static final SoundEvent ENTITY_HELL_RAT_DEATH;
    public static final SoundEvent ENTITY_HELL_RAT_HURT;
    //
    public static final SoundEvent ENTITY_MINI_GOLEM_DEATH;
    public static final SoundEvent ENTITY_MINI_GOLEM_HURT;
    //
    public static final SoundEvent ENTITY_OGRE_AMBIENT;
    public static final SoundEvent ENTITY_OGRE_DEATH;
    public static final SoundEvent ENTITY_OGRE_HURT;
    //
    public static final SoundEvent ENTITY_RAT_AMBIENT;
    public static final SoundEvent ENTITY_RAT_DEATH;
    public static final SoundEvent ENTITY_RAT_HURT;
    //
    public static final SoundEvent ENTITY_SCORPION_AMBIENT;
    public static final SoundEvent ENTITY_SCORPION_ATTACK;
    public static final SoundEvent ENTITY_SCORPION_DEATH;
    public static final SoundEvent ENTITY_SCORPION_HURT;
    public static final SoundEvent ENTITY_SCORPION_STING;
    //
    public static final SoundEvent ENTITY_SILVER_SKELETON_AMBIENT;
    public static final SoundEvent ENTITY_SILVER_SKELETON_ATTACK;
    public static final SoundEvent ENTITY_SILVER_SKELETON_DEATH;
    public static final SoundEvent ENTITY_SILVER_SKELETON_HURT;
    public static final SoundEvent ENTITY_SILVER_SKELETON_STEP;
    //
    public static final SoundEvent ENTITY_WEREWOLF_AMBIENT;
    public static final SoundEvent ENTITY_WEREWOLF_DEATH;
    public static final SoundEvent ENTITY_WEREWOLF_HURT;
    public static final SoundEvent ENTITY_WEREWOLF_TRANSFORM;
    //
    public static final SoundEvent ENTITY_WRAITH_AMBIENT;
    public static final SoundEvent ENTITY_WRAITH_DEATH;
    public static final SoundEvent ENTITY_WRAITH_HURT;
    //
    public static final SoundEvent ENTITY_WOLF_AMBIENT;
    public static final SoundEvent ENTITY_WOLF_DEATH;
    public static final SoundEvent ENTITY_WOLF_HURT;

    // Hunter
    public static final SoundEvent ENTITY_BEAR_AMBIENT;
    public static final SoundEvent ENTITY_BEAR_DEATH;
    public static final SoundEvent ENTITY_BEAR_HURT;
    //
    public static final SoundEvent ENTITY_CROCODILE_AMBIENT;
    public static final SoundEvent ENTITY_CROCODILE_DEATH;
    public static final SoundEvent ENTITY_CROCODILE_HURT;
    public static final SoundEvent ENTITY_CROCODILE_ATTACK;
    public static final SoundEvent ENTITY_CROCODILE_REST;
    public static final SoundEvent ENTITY_CROCODILE_ROLL;
    //
    public static final SoundEvent ENTITY_FOX_AMBIENT;
    public static final SoundEvent ENTITY_FOX_DEATH;
    public static final SoundEvent ENTITY_FOX_HURT;
    //
    public static final SoundEvent ENTITY_LION_AMBIENT;
    public static final SoundEvent ENTITY_LION_AMBIENT_BABY;
    public static final SoundEvent ENTITY_LION_DEATH;
    public static final SoundEvent ENTITY_LION_DEATH_BABY;
    public static final SoundEvent ENTITY_LION_HURT;
    public static final SoundEvent ENTITY_LION_HURT_BABY;
    //
    public static final SoundEvent ENTITY_RACCOON_AMBIENT;
    public static final SoundEvent ENTITY_RACCOON_DEATH;
    public static final SoundEvent ENTITY_RACCOON_HURT;
    //
    public static final SoundEvent ENTITY_SNAKE_AMBIENT;
    public static final SoundEvent ENTITY_SNAKE_ANGRY;
    public static final SoundEvent ENTITY_SNAKE_DEATH;
    public static final SoundEvent ENTITY_SNAKE_HURT;
    public static final SoundEvent ENTITY_SNAKE_RATTLE;
    public static final SoundEvent ENTITY_SNAKE_ATTACK;
    public static final SoundEvent ENTITY_SNAKE_SWIM;

    // Neutral
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_ELEPHANT_AMBIENT;
    public static final SoundEvent ENTITY_ELEPHANT_DEATH;
    public static final SoundEvent ENTITY_ELEPHANT_HURT;
    //
    public static final SoundEvent ENTITY_ENT_AMBIENT;
    public static final SoundEvent ENTITY_ENT_DEATH;
    public static final SoundEvent ENTITY_ENT_HURT;
    //
    public static final SoundEvent ENTITY_GOAT_AMBIENT;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_BABY;
    public static final SoundEvent ENTITY_GOAT_AMBIENT_FEMALE;
    public static final SoundEvent ENTITY_GOAT_DEATH;
    public static final SoundEvent ENTITY_GOAT_DIG;
    public static final SoundEvent ENTITY_GOAT_EAT;
    public static final SoundEvent ENTITY_GOAT_HURT;
    //
    public static final SoundEvent ENTITY_KITTY_AMBIENT;
    public static final SoundEvent ENTITY_KITTY_AMBIENT_BABY;
    public static final SoundEvent ENTITY_KITTY_ANGRY;
    public static final SoundEvent ENTITY_KITTY_DEATH;
    public static final SoundEvent ENTITY_KITTY_DEATH_BABY;
    public static final SoundEvent ENTITY_KITTY_DRINK;
    public static final SoundEvent ENTITY_KITTY_EAT;
    public static final SoundEvent ENTITY_KITTY_HUNGRY;
    public static final SoundEvent ENTITY_KITTY_HURT;
    public static final SoundEvent ENTITY_KITTY_HURT_BABY;
    public static final SoundEvent ENTITY_KITTY_PURR;
    public static final SoundEvent ENTITY_KITTY_TRAPPED;
    public static final SoundEvent ENTITY_KITTY_BED_POUR_FOOD;
    public static final SoundEvent ENTITY_KITTY_BED_POUR_MILK;
    //
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT;
    public static final SoundEvent ENTITY_OSTRICH_AMBIENT_BABY;
    public static final SoundEvent ENTITY_OSTRICH_DEATH;
    public static final SoundEvent ENTITY_OSTRICH_HURT;
    //
    public static final SoundEvent ENTITY_WYVERN_AMBIENT;
    public static final SoundEvent ENTITY_WYVERN_DEATH;
    public static final SoundEvent ENTITY_WYVERN_FLAP;
    public static final SoundEvent ENTITY_WYVERN_HURT;
    public static final SoundEvent ENTITY_WYVERN_STEP;

    // Passive
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLACK;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_BLUE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_GREEN;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_RED;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_WHITE;
    public static final SoundEvent ENTITY_BIRD_AMBIENT_YELLOW;
    //
    public static final SoundEvent ENTITY_BUNNY_DEATH;
    public static final SoundEvent ENTITY_BUNNY_HURT;
    public static final SoundEvent ENTITY_BUNNY_LAND;
    public static final SoundEvent ENTITY_BUNNY_LIFT;
    //
    public static final SoundEvent ENTITY_DEER_AMBIENT_BABY;
    public static final SoundEvent ENTITY_DEER_AMBIENT;
    public static final SoundEvent ENTITY_DEER_DEATH;
    public static final SoundEvent ENTITY_DEER_HURT;
    //
    public static final SoundEvent ENTITY_DUCK_AMBIENT;
    public static final SoundEvent ENTITY_DUCK_HURT;
    public static final SoundEvent ENTITY_DUCK_STEP;
    //
    public static final SoundEvent ENTITY_FILCH_LIZARD_HISS;
    public static final SoundEvent ENTITY_FILCH_LIZARD_DEATH;
    //
    public static final SoundEvent ENTITY_FISH_DEATH_VICIOUS;
    public static final SoundEvent ENTITY_FISH_FLOP;
    public static final SoundEvent ENTITY_FISH_HURT;
    public static final SoundEvent ENTITY_FISH_SWIM;
    //
    public static final SoundEvent ENTITY_HORSE_ANGRY;
    public static final SoundEvent ENTITY_HORSE_AMBIENT;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_GHOST;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_AMBIENT_ZEBRA;
    public static final SoundEvent ENTITY_HORSE_ANGRY_GHOST;
    public static final SoundEvent ENTITY_HORSE_ANGRY_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_DEATH;
    public static final SoundEvent ENTITY_HORSE_DEATH_GHOST;
    public static final SoundEvent ENTITY_HORSE_DEATH_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT;
    public static final SoundEvent ENTITY_HORSE_HURT_GHOST;
    public static final SoundEvent ENTITY_HORSE_HURT_UNDEAD;
    public static final SoundEvent ENTITY_HORSE_HURT_ZEBRA;
    //
    public static final SoundEvent ENTITY_MOLE_AMBIENT;
    public static final SoundEvent ENTITY_MOLE_DEATH;
    public static final SoundEvent ENTITY_MOLE_HURT;
    //
    public static final SoundEvent ENTITY_MOUSE_AMBIENT;
    public static final SoundEvent ENTITY_MOUSE_DEATH;
    public static final SoundEvent ENTITY_MOUSE_HURT;
    //
    public static final SoundEvent ENTITY_TURKEY_AMBIENT;
    public static final SoundEvent ENTITY_TURKEY_HURT;
    //
    public static final SoundEvent ENTITY_TURTLE_HISS;
    public static final SoundEvent ENTITY_TURTLE_DEATH;

    /* Entity (Legacy) */
    // Hostile
    public static final SoundEvent ENTITY_BIG_GOLEM_DEATH_LEGACY;
    public static final SoundEvent ENTITY_BIG_GOLEM_HURT_LEGACY;
    //
    public static final SoundEvent ENTITY_RAT_DEATH_LEGACY;
    //
    public static final SoundEvent ENTITY_WEREHUMAN_DEATH_LEGACY;
    public static final SoundEvent ENTITY_WEREHUMAN_HURT_LEGACY;
    public static final SoundEvent ENTITY_WEREWOLF_TRANSFORM_LEGACY;
    //
    public static final SoundEvent ENTITY_WRAITH_AMBIENT_LEGACY;
    public static final SoundEvent ENTITY_WRAITH_DEATH_LEGACY;
    public static final SoundEvent ENTITY_WRAITH_HURT_LEGACY;
    
    // Passive
    public static final SoundEvent ENTITY_DUCK_AMBIENT_LEGACY;
    public static final SoundEvent ENTITY_DUCK_HURT_LEGACY;

    /* Generic */
    public static final SoundEvent ENTITY_GENERIC_ARMOR_ON;
    public static final SoundEvent ENTITY_GENERIC_ARMOR_OFF;
    public static final SoundEvent ENTITY_GENERIC_ATTACH;
    public static final SoundEvent ENTITY_GENERIC_CLANG;
    public static final SoundEvent ENTITY_GENERIC_DESTROY;
    public static final SoundEvent ENTITY_GENERIC_DRINK;
    public static final SoundEvent ENTITY_GENERIC_EAT;
    public static final SoundEvent ENTITY_GENERIC_ENVENOM;
    public static final SoundEvent ENTITY_GENERIC_EXPLODE;
    public static final SoundEvent ENTITY_GENERIC_FLAP;
    public static final SoundEvent ENTITY_GENERIC_FLAP_SOFT;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_APPEAR;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_CONVERSION;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_CREEPY;
    public static final SoundEvent ENTITY_GENERIC_MAGIC_ENCHANTED;
    public static final SoundEvent ENTITY_GENERIC_LAUNCH;
    public static final SoundEvent ENTITY_GENERIC_ROPING;
    public static final SoundEvent ENTITY_GENERIC_SMACK;
    public static final SoundEvent ENTITY_GENERIC_STOMP;
    public static final SoundEvent ENTITY_GENERIC_WHIP;
    public static final SoundEvent ENTITY_GENERIC_WHOOSH;

    /* Music */
    // Record
    public static final SoundEvent MUSIC_DISC_SHUFFLING;

    static {
        if (!Bootstrap.isRegistered()) {
            throw new RuntimeException("Accessed Sounds before Bootstrap!");
        } else {
            /* Entity */
            // Ambient
            ENTITY_BEE_AMBIENT = createSoundEvent("entity.bee.ambient");
            ENTITY_BEE_HURT = createSoundEvent("entity.bee.hurt");
            ENTITY_BEE_ANGRY = createSoundEvent("entity.bee.angry");
            //
            ENTITY_CRICKET_AMBIENT = createSoundEvent("entity.cricket.ambient");
            ENTITY_CRICKET_CHIRP = createSoundEvent("entity.cricket.chirp");
            ENTITY_CRICKET_HURT = createSoundEvent("entity.cricket.hurt");
            //
            ENTITY_DRAGONFLY_AMBIENT = createSoundEvent("entity.dragonfly.ambient");
            ENTITY_DRAGONFLY_HURT = createSoundEvent("entity.dragonfly.hurt");
            //
            ENTITY_FLY_AMBIENT = createSoundEvent("entity.fly.ambient");
            ENTITY_FLY_HURT = createSoundEvent("entity.fly.hurt");
            //
            ENTITY_GRASSHOPPER_AMBIENT = createSoundEvent("entity.grasshopper.ambient"); // TODO
            ENTITY_GRASSHOPPER_CHIRP = createSoundEvent("entity.grasshopper.chirp");
            ENTITY_GRASSHOPPER_FLY = createSoundEvent("entity.grasshopper.fly");
            ENTITY_GRASSHOPPER_HURT = createSoundEvent("entity.grasshopper.hurt");

            // Aquatic
            ENTITY_DOLPHIN_AMBIENT = createSoundEvent("entity.dolphin.ambient");
            ENTITY_DOLPHIN_DEATH = createSoundEvent("entity.dolphin.death");
            ENTITY_DOLPHIN_HURT = createSoundEvent("entity.dolphin.hurt");
            ENTITY_DOLPHIN_ANGRY = createSoundEvent("entity.dolphin.angry");

            // Hostile
            ENTITY_BIG_GOLEM_AMBIENT = createSoundEvent("entity.big_golem.ambient");
            ENTITY_BIG_GOLEM_ATTACH = createSoundEvent("entity.big_golem.attach");
            ENTITY_BIG_GOLEM_CLANG = createSoundEvent("entity.big_golem.clang");
            ENTITY_BIG_GOLEM_DEATH = createSoundEvent("entity.big_golem.death");
            ENTITY_BIG_GOLEM_HURT = createSoundEvent("entity.big_golem.hurt");
            ENTITY_BIG_GOLEM_STEP = createSoundEvent("entity.big_golem.step");
            //
            ENTITY_MINI_GOLEM_DEATH = createSoundEvent("entity.mini_golem.death");
            ENTITY_MINI_GOLEM_HURT = createSoundEvent("entity.mini_golem.hurt");
            //
            ENTITY_HELL_RAT_AMBIENT = createSoundEvent("entity.hell_rat.ambient");
            ENTITY_HELL_RAT_DEATH = createSoundEvent("entity.hell_rat.death");
            ENTITY_HELL_RAT_HURT = createSoundEvent("entity.hell_rat.hurt");
            //
            ENTITY_OGRE_AMBIENT = createSoundEvent("entity.ogre.ambient");
            ENTITY_OGRE_DEATH = createSoundEvent("entity.ogre.death");
            ENTITY_OGRE_HURT = createSoundEvent("entity.ogre.hurt");
            //
            ENTITY_RAT_AMBIENT = createSoundEvent("entity.rat.ambient");
            ENTITY_RAT_DEATH = createSoundEvent("entity.rat.death");
            ENTITY_RAT_HURT = createSoundEvent("entity.rat.hurt");
            //
            ENTITY_SCORPION_AMBIENT = createSoundEvent("entity.scorpion.ambient");
            ENTITY_SCORPION_ATTACK = createSoundEvent("entity.scorpion.attack");
            ENTITY_SCORPION_DEATH = createSoundEvent("entity.scorpion.death");
            ENTITY_SCORPION_HURT = createSoundEvent("entity.scorpion.hurt");
            ENTITY_SCORPION_STING = createSoundEvent("entity.scorpion.sting");
            //
            ENTITY_SILVER_SKELETON_AMBIENT = createSoundEvent("entity.silver_skeleton.ambient");
            ENTITY_SILVER_SKELETON_ATTACK = createSoundEvent("entity.silver_skeleton.attack");
            ENTITY_SILVER_SKELETON_DEATH = createSoundEvent("entity.silver_skeleton.death");
            ENTITY_SILVER_SKELETON_HURT = createSoundEvent("entity.silver_skeleton.hurt");
            ENTITY_SILVER_SKELETON_STEP = createSoundEvent("entity.silver_skeleton.step");
            //
            ENTITY_WEREWOLF_AMBIENT = createSoundEvent("entity.werewolf.ambient");
            ENTITY_WEREWOLF_DEATH = createSoundEvent("entity.werewolf.death");
            ENTITY_WEREWOLF_HURT = createSoundEvent("entity.werewolf.hurt");
            ENTITY_WEREWOLF_TRANSFORM = createSoundEvent("entity.werewolf.transform");
            //
            ENTITY_WOLF_AMBIENT = createSoundEvent("entity.wolf.ambient");
            ENTITY_WOLF_DEATH = createSoundEvent("entity.wolf.death");
            ENTITY_WOLF_HURT = createSoundEvent("entity.wolf.hurt");
            //
            ENTITY_WRAITH_AMBIENT = createSoundEvent("entity.wraith.ambient");
            ENTITY_WRAITH_DEATH = createSoundEvent("entity.wraith.death");
            ENTITY_WRAITH_HURT = createSoundEvent("entity.wraith.hurt");

            // Hunter
            ENTITY_BEAR_AMBIENT = createSoundEvent("entity.bear.ambient");
            ENTITY_BEAR_DEATH = createSoundEvent("entity.bear.death");
            ENTITY_BEAR_HURT = createSoundEvent("entity.bear.hurt");
            //
            ENTITY_CROCODILE_AMBIENT = createSoundEvent("entity.crocodile.ambient");
            ENTITY_CROCODILE_DEATH = createSoundEvent("entity.crocodile.death");
            ENTITY_CROCODILE_HURT = createSoundEvent("entity.crocodile.hurt");
            ENTITY_CROCODILE_ATTACK = createSoundEvent("entity.crocodile.attack");
            ENTITY_CROCODILE_REST = createSoundEvent("entity.crocodile.rest");
            ENTITY_CROCODILE_ROLL = createSoundEvent("entity.crocodile.roll");
            //
            ENTITY_FOX_AMBIENT = createSoundEvent("entity.fox.ambient");
            ENTITY_FOX_DEATH = createSoundEvent("entity.fox.death");
            ENTITY_FOX_HURT = createSoundEvent("entity.fox.hurt");
            //
            ENTITY_LION_AMBIENT = createSoundEvent("entity.lion.ambient");
            ENTITY_LION_AMBIENT_BABY = createSoundEvent("entity.lion.ambient_baby");
            ENTITY_LION_DEATH = createSoundEvent("entity.lion.death");
            ENTITY_LION_DEATH_BABY = createSoundEvent("entity.lion.death_baby");
            ENTITY_LION_HURT = createSoundEvent("entity.lion.hurt");
            ENTITY_LION_HURT_BABY = createSoundEvent("entity.lion.hurt_baby");
            //
            ENTITY_RACCOON_AMBIENT = createSoundEvent("entity.raccoon.ambient");
            ENTITY_RACCOON_DEATH = createSoundEvent("entity.raccoon.death");
            ENTITY_RACCOON_HURT = createSoundEvent("entity.raccoon.hurt");
            //
            ENTITY_SNAKE_AMBIENT = createSoundEvent("entity.snake.ambient");
            ENTITY_SNAKE_ANGRY = createSoundEvent("entity.snake.angry");
            ENTITY_SNAKE_DEATH = createSoundEvent("entity.snake.death");
            ENTITY_SNAKE_HURT = createSoundEvent("entity.snake.hurt");
            ENTITY_SNAKE_RATTLE = createSoundEvent("entity.snake.rattle");
            ENTITY_SNAKE_ATTACK = createSoundEvent("entity.snake.attack");
            ENTITY_SNAKE_SWIM = createSoundEvent("entity.snake.swim");

            // Neutral
            ENTITY_ELEPHANT_AMBIENT_BABY = createSoundEvent("entity.elephant.ambient_baby");
            ENTITY_ELEPHANT_AMBIENT = createSoundEvent("entity.elephant.ambient");
            ENTITY_ELEPHANT_DEATH = createSoundEvent("entity.elephant.death");
            ENTITY_ELEPHANT_HURT = createSoundEvent("entity.elephant.hurt");
            //
            ENTITY_ENT_AMBIENT = createSoundEvent("entity.ent.ambient"); // TODO
            ENTITY_ENT_DEATH = createSoundEvent("entity.ent.death"); // TODO
            ENTITY_ENT_HURT = createSoundEvent("entity.ent.hurt"); // TODO
            //
            ENTITY_GOAT_AMBIENT = createSoundEvent("entity.goat.ambient");
            ENTITY_GOAT_AMBIENT_BABY = createSoundEvent("entity.goat.ambient_baby");
            ENTITY_GOAT_AMBIENT_FEMALE = createSoundEvent("entity.goat.ambient_female");
            ENTITY_GOAT_DEATH = createSoundEvent("entity.goat.death");
            ENTITY_GOAT_HURT = createSoundEvent("entity.goat.hurt");
            ENTITY_GOAT_DIG = createSoundEvent("entity.goat.dig");
            ENTITY_GOAT_EAT = createSoundEvent("entity.goat.eat");
            //
            ENTITY_KITTY_AMBIENT = createSoundEvent("entity.kitty.ambient");
            ENTITY_KITTY_AMBIENT_BABY = createSoundEvent("entity.kitty.ambient_baby");
            ENTITY_KITTY_ANGRY = createSoundEvent("entity.kitty.angry");
            ENTITY_KITTY_DEATH = createSoundEvent("entity.kitty.death");
            ENTITY_KITTY_DEATH_BABY = createSoundEvent("entity.kitty.death_baby");
            ENTITY_KITTY_DRINK = createSoundEvent("entity.kitty.drink");
            ENTITY_KITTY_EAT = createSoundEvent("entity.kitty.eat");
            ENTITY_KITTY_HUNGRY = createSoundEvent("entity.kitty.hungry");
            ENTITY_KITTY_HURT = createSoundEvent("entity.kitty.hurt");
            ENTITY_KITTY_HURT_BABY = createSoundEvent("entity.kitty.hurt_baby");
            ENTITY_KITTY_PURR = createSoundEvent("entity.kitty.purr");
            ENTITY_KITTY_TRAPPED = createSoundEvent("entity.kitty.trapped");
            ENTITY_KITTY_BED_POUR_FOOD = createSoundEvent("entity.kitty_bed.pour_food");
            ENTITY_KITTY_BED_POUR_MILK = createSoundEvent("entity.kitty_bed.pour_milk");
            //
            ENTITY_OSTRICH_AMBIENT = createSoundEvent("entity.ostrich.ambient");
            ENTITY_OSTRICH_AMBIENT_BABY = createSoundEvent("entity.ostrich.ambient_baby");
            ENTITY_OSTRICH_DEATH = createSoundEvent("entity.ostrich.death");
            ENTITY_OSTRICH_HURT = createSoundEvent("entity.ostrich.hurt");
            //
            ENTITY_WYVERN_AMBIENT = createSoundEvent("entity.wyvern.ambient");
            ENTITY_WYVERN_DEATH = createSoundEvent("entity.wyvern.death");
            ENTITY_WYVERN_FLAP = createSoundEvent("entity.wyvern.flap");
            ENTITY_WYVERN_HURT = createSoundEvent("entity.wyvern.hurt");
            ENTITY_WYVERN_STEP = createSoundEvent("entity.wyvern.step");

            // Passive
            ENTITY_BIRD_AMBIENT_BLACK = createSoundEvent("entity.bird.ambient_black");
            ENTITY_BIRD_AMBIENT_BLUE = createSoundEvent("entity.bird.ambient_blue");
            ENTITY_BIRD_AMBIENT_GREEN = createSoundEvent("entity.bird.ambient_green");
            ENTITY_BIRD_AMBIENT_RED = createSoundEvent("entity.bird.ambient_red");
            ENTITY_BIRD_AMBIENT_YELLOW = createSoundEvent("entity.bird.ambient_yellow");
            ENTITY_BIRD_AMBIENT_WHITE = createSoundEvent("entity.bird.ambient_white");
            //
            ENTITY_BUNNY_DEATH = createSoundEvent("entity.bunny.death");
            ENTITY_BUNNY_HURT = createSoundEvent("entity.bunny.hurt");
            ENTITY_BUNNY_LAND = createSoundEvent("entity.bunny.land");
            ENTITY_BUNNY_LIFT = createSoundEvent("entity.bunny.lift");
            //
            ENTITY_DEER_AMBIENT_BABY = createSoundEvent("entity.deer.ambient_baby");
            ENTITY_DEER_AMBIENT = createSoundEvent("entity.deer.ambient");
            ENTITY_DEER_DEATH = createSoundEvent("entity.deer.death");
            ENTITY_DEER_HURT = createSoundEvent("entity.deer.hurt");
            //
            ENTITY_DUCK_AMBIENT = createSoundEvent("entity.duck.ambient");
            ENTITY_DUCK_HURT = createSoundEvent("entity.duck.hurt");
            ENTITY_DUCK_STEP = createSoundEvent("entity.duck.step");
            //
            ENTITY_FILCH_LIZARD_HISS = createSoundEvent("entity.filch_lizard.hiss");
            ENTITY_FILCH_LIZARD_DEATH = createSoundEvent("entity.filch_lizard.death");
            //
            ENTITY_FISH_DEATH_VICIOUS = createSoundEvent("entity.fish.death_vicious");
            ENTITY_FISH_FLOP = createSoundEvent("entity.fish.flop");
            ENTITY_FISH_HURT = createSoundEvent("entity.fish.hurt");
            ENTITY_FISH_SWIM = createSoundEvent("entity.fish.swim");
            //
            ENTITY_HORSE_ANGRY = createSoundEvent("entity.horse.angry");
            ENTITY_HORSE_AMBIENT = createSoundEvent("entity.horse.ambient");
            ENTITY_HORSE_AMBIENT_GHOST = createSoundEvent("entity.horse.ambient_ghost");
            ENTITY_HORSE_AMBIENT_UNDEAD = createSoundEvent("entity.horse.ambient_undead");
            ENTITY_HORSE_AMBIENT_ZEBRA = createSoundEvent("entity.zebra.ambient");
            ENTITY_HORSE_ANGRY_GHOST = createSoundEvent("entity.horse.angry_ghost");
            ENTITY_HORSE_ANGRY_UNDEAD = createSoundEvent("entity.horse.angry_undead");
            ENTITY_HORSE_DEATH = createSoundEvent("entity.horse.death");
            ENTITY_HORSE_DEATH_GHOST = createSoundEvent("entity.horse.death_ghost");
            ENTITY_HORSE_DEATH_UNDEAD = createSoundEvent("entity.horse.death_undead");
            ENTITY_HORSE_HURT = createSoundEvent("entity.horse.hurt");
            ENTITY_HORSE_HURT_GHOST = createSoundEvent("entity.horse.hurt_ghost");
            ENTITY_HORSE_HURT_UNDEAD = createSoundEvent("entity.horse.hurt_undead");
            ENTITY_HORSE_HURT_ZEBRA = createSoundEvent("entity.zebra.hurt");
            //
            ENTITY_MOLE_AMBIENT = createSoundEvent("entity.mole.ambient");
            ENTITY_MOLE_DEATH = createSoundEvent("entity.mole.death");
            ENTITY_MOLE_HURT = createSoundEvent("entity.mole.hurt");
            //
            ENTITY_MOUSE_AMBIENT = createSoundEvent("entity.mouse.ambient");
            ENTITY_MOUSE_DEATH = createSoundEvent("entity.mouse.death");
            ENTITY_MOUSE_HURT = createSoundEvent("entity.mouse.hurt");
            //
            ENTITY_TURKEY_AMBIENT = createSoundEvent("entity.turkey.ambient");
            ENTITY_TURKEY_HURT = createSoundEvent("entity.turkey.hurt");
            //
            ENTITY_TURTLE_HISS = createSoundEvent("entity.turtle.hiss");
            ENTITY_TURTLE_DEATH = createSoundEvent("entity.turtle.death");

            /* Entity (Legacy) */
            // Hostile
            ENTITY_BIG_GOLEM_DEATH_LEGACY = createSoundEvent("entity.big_golem.death_legacy");
            ENTITY_BIG_GOLEM_HURT_LEGACY = createSoundEvent("entity.big_golem.hurt_legacy");
            //
            ENTITY_RAT_DEATH_LEGACY = createSoundEvent("entity.rat.death_legacy");
            //
            ENTITY_WEREHUMAN_DEATH_LEGACY = createSoundEvent("entity.werehuman.death_legacy");
            ENTITY_WEREHUMAN_HURT_LEGACY = createSoundEvent("entity.werehuman.hurt_legacy");
            ENTITY_WEREWOLF_TRANSFORM_LEGACY = createSoundEvent("entity.werewolf.transform_legacy");
            //
            ENTITY_WRAITH_AMBIENT_LEGACY = createSoundEvent("entity.wraith.ambient_legacy");
            ENTITY_WRAITH_DEATH_LEGACY = createSoundEvent("entity.wraith.death_legacy");
            ENTITY_WRAITH_HURT_LEGACY = createSoundEvent("entity.wraith.hurt_legacy");
            
            // Passive
            ENTITY_DUCK_AMBIENT_LEGACY = createSoundEvent("entity.duck.ambient_legacy");
            ENTITY_DUCK_HURT_LEGACY = createSoundEvent("entity.duck.hurt_legacy");

            /* Generic */
            ENTITY_GENERIC_ARMOR_ON = createSoundEvent("entity.generic.armor_on");
            ENTITY_GENERIC_ARMOR_OFF = createSoundEvent("entity.generic.armor_off");
            ENTITY_GENERIC_ATTACH = createSoundEvent("entity.generic.attach");
            ENTITY_GENERIC_CLANG = createSoundEvent("entity.generic.clang");
            ENTITY_GENERIC_DESTROY = createSoundEvent("entity.generic.destroy");
            ENTITY_GENERIC_DRINK = createSoundEvent("entity.generic.drink");
            ENTITY_GENERIC_EAT = createSoundEvent("entity.generic.eat");
            ENTITY_GENERIC_ENVENOM = createSoundEvent("entity.generic.envenom");
            ENTITY_GENERIC_EXPLODE = createSoundEvent("entity.generic.explode");
            ENTITY_GENERIC_FLAP = createSoundEvent("entity.generic.flap");
            ENTITY_GENERIC_FLAP_SOFT = createSoundEvent("entity.generic.flap_soft");
            ENTITY_GENERIC_LAUNCH = createSoundEvent("entity.generic.launch");
            ENTITY_GENERIC_MAGIC_APPEAR = createSoundEvent("entity.generic.magic_appear");
            ENTITY_GENERIC_MAGIC_CONVERSION = createSoundEvent("entity.generic.magic_conversion");
            ENTITY_GENERIC_MAGIC_CREEPY = createSoundEvent("entity.generic.magic_creepy");
            ENTITY_GENERIC_MAGIC_ENCHANTED = createSoundEvent("entity.generic.magic_enchanted");
            ENTITY_GENERIC_ROPING = createSoundEvent("entity.generic.roping");
            ENTITY_GENERIC_SMACK = createSoundEvent("entity.generic.smack");
            ENTITY_GENERIC_STOMP = createSoundEvent("entity.generic.stomp");
            ENTITY_GENERIC_WHIP = createSoundEvent("entity.generic.whip");
            ENTITY_GENERIC_WHOOSH = createSoundEvent("entity.generic.whoosh");

            /* Music */
            // Record
            MUSIC_DISC_SHUFFLING = createSoundEvent("music_disc.shuffling");
        }
    }

    /**
     * Create a {@link SoundEvent}.
     *
     * @param soundName The SoundEvent's name without the testmod3 prefix
     * @return The SoundEvent
     */
    private static RegistryObject<SoundEvent> createSoundEvent(final String soundName) {
        return SOUND_DEFERRED.register(soundName, () -> new SoundEvent(new ResourceLocation(MoCConstants.MOD_ID, soundName)));
    }

    @Mod.EventBusSubscriber(modid = MoCConstants.MOD_ID)
    public static class RegistrationHandler {
        @SubscribeEvent
        public static void registerSoundEvents(final RegistryEvent.Register<SoundEvent> event) {
            event.getRegistry().registerAll(
                    /* Entity */
                    // Ambient
                    ENTITY_BEE_AMBIENT,
                    ENTITY_BEE_HURT,
                    ENTITY_BEE_ANGRY,
                    //
                    ENTITY_CRICKET_AMBIENT,
                    ENTITY_CRICKET_CHIRP,
                    ENTITY_CRICKET_HURT,
                    //
                    ENTITY_DRAGONFLY_AMBIENT,
                    ENTITY_DRAGONFLY_HURT,
                    //
                    ENTITY_FLY_AMBIENT,
                    ENTITY_FLY_HURT,
                    //
                    ENTITY_GRASSHOPPER_AMBIENT,
                    ENTITY_GRASSHOPPER_CHIRP,
                    ENTITY_GRASSHOPPER_FLY,
                    ENTITY_GRASSHOPPER_HURT,

                    // Aquatic
                    ENTITY_DOLPHIN_AMBIENT,
                    ENTITY_DOLPHIN_DEATH,
                    ENTITY_DOLPHIN_HURT,
                    ENTITY_DOLPHIN_ANGRY,

                    // Hostile
                    ENTITY_BIG_GOLEM_AMBIENT,
                    ENTITY_BIG_GOLEM_ATTACH,
                    ENTITY_BIG_GOLEM_CLANG,
                    ENTITY_BIG_GOLEM_DEATH,
                    ENTITY_BIG_GOLEM_HURT,
                    ENTITY_BIG_GOLEM_STEP,
                    //
                    ENTITY_MINI_GOLEM_DEATH,
                    ENTITY_MINI_GOLEM_HURT,
                    //
                    ENTITY_HELL_RAT_AMBIENT,
                    ENTITY_HELL_RAT_DEATH,
                    ENTITY_HELL_RAT_HURT,
                    //
                    ENTITY_OGRE_AMBIENT,
                    ENTITY_OGRE_DEATH,
                    ENTITY_OGRE_HURT,
                    //
                    ENTITY_RAT_AMBIENT,
                    ENTITY_RAT_DEATH,
                    ENTITY_RAT_HURT,
                    //
                    ENTITY_SCORPION_AMBIENT,
                    ENTITY_SCORPION_ATTACK,
                    ENTITY_SCORPION_DEATH,
                    ENTITY_SCORPION_HURT,
                    ENTITY_SCORPION_STING,
                    //
                    ENTITY_SILVER_SKELETON_AMBIENT,
                    ENTITY_SILVER_SKELETON_ATTACK,
                    ENTITY_SILVER_SKELETON_DEATH,
                    ENTITY_SILVER_SKELETON_HURT,
                    ENTITY_SILVER_SKELETON_STEP,
                    //
                    ENTITY_WEREWOLF_AMBIENT,
                    ENTITY_WEREWOLF_DEATH,
                    ENTITY_WEREWOLF_HURT,
                    ENTITY_WEREWOLF_TRANSFORM,
                    //
                    ENTITY_WOLF_AMBIENT,
                    ENTITY_WOLF_DEATH,
                    ENTITY_WOLF_HURT,
                    //
                    ENTITY_WRAITH_AMBIENT,
                    ENTITY_WRAITH_DEATH,
                    ENTITY_WRAITH_HURT,

                    // Hunter
                    ENTITY_BEAR_AMBIENT,
                    ENTITY_BEAR_DEATH,
                    ENTITY_BEAR_HURT,
                    //
                    ENTITY_CROCODILE_AMBIENT,
                    ENTITY_CROCODILE_DEATH,
                    ENTITY_CROCODILE_HURT,
                    ENTITY_CROCODILE_ATTACK,
                    ENTITY_CROCODILE_REST,
                    ENTITY_CROCODILE_ROLL,
                    //
                    ENTITY_FOX_AMBIENT,
                    ENTITY_FOX_DEATH,
                    ENTITY_FOX_HURT,
                    //
                    ENTITY_LION_AMBIENT,
                    ENTITY_LION_AMBIENT_BABY,
                    ENTITY_LION_DEATH,
                    ENTITY_LION_DEATH_BABY,
                    ENTITY_LION_HURT,
                    ENTITY_LION_HURT_BABY,
                    //
                    ENTITY_RACCOON_AMBIENT,
                    ENTITY_RACCOON_DEATH,
                    ENTITY_RACCOON_HURT,
                    //
                    ENTITY_SNAKE_AMBIENT,
                    ENTITY_SNAKE_ANGRY,
                    ENTITY_SNAKE_DEATH,
                    ENTITY_SNAKE_HURT,
                    ENTITY_SNAKE_RATTLE,
                    ENTITY_SNAKE_ATTACK,
                    ENTITY_SNAKE_SWIM,

                    // Neutral
                    ENTITY_ELEPHANT_AMBIENT_BABY,
                    ENTITY_ELEPHANT_AMBIENT,
                    ENTITY_ELEPHANT_DEATH,
                    ENTITY_ELEPHANT_HURT,
                    //
                    ENTITY_ENT_AMBIENT,
                    ENTITY_ENT_DEATH,
                    ENTITY_ENT_HURT,
                    //
                    ENTITY_GOAT_AMBIENT,
                    ENTITY_GOAT_AMBIENT_BABY,
                    ENTITY_GOAT_AMBIENT_FEMALE,
                    ENTITY_GOAT_DEATH,
                    ENTITY_GOAT_HURT,
                    ENTITY_GOAT_DIG,
                    ENTITY_GOAT_EAT,
                    //
                    ENTITY_KITTY_AMBIENT,
                    ENTITY_KITTY_AMBIENT_BABY,
                    ENTITY_KITTY_ANGRY,
                    ENTITY_KITTY_DEATH,
                    ENTITY_KITTY_DEATH_BABY,
                    ENTITY_KITTY_DRINK,
                    ENTITY_KITTY_EAT,
                    ENTITY_KITTY_HUNGRY,
                    ENTITY_KITTY_HURT,
                    ENTITY_KITTY_HURT_BABY,
                    ENTITY_KITTY_PURR,
                    ENTITY_KITTY_TRAPPED,
                    ENTITY_KITTY_BED_POUR_FOOD,
                    ENTITY_KITTY_BED_POUR_MILK,
                    //
                    ENTITY_OSTRICH_AMBIENT,
                    ENTITY_OSTRICH_AMBIENT_BABY,
                    ENTITY_OSTRICH_DEATH,
                    ENTITY_OSTRICH_HURT,
                    //
                    ENTITY_WYVERN_AMBIENT,
                    ENTITY_WYVERN_DEATH,
                    ENTITY_WYVERN_FLAP,
                    ENTITY_WYVERN_HURT,
                    ENTITY_WYVERN_STEP,

                    // Passive
                    ENTITY_BIRD_AMBIENT_BLACK,
                    ENTITY_BIRD_AMBIENT_BLUE,
                    ENTITY_BIRD_AMBIENT_GREEN,
                    ENTITY_BIRD_AMBIENT_RED,
                    ENTITY_BIRD_AMBIENT_YELLOW,
                    ENTITY_BIRD_AMBIENT_WHITE,
                    //
                    ENTITY_BUNNY_DEATH,
                    ENTITY_BUNNY_HURT,
                    ENTITY_BUNNY_LAND,
                    ENTITY_BUNNY_LIFT,
                    //
                    ENTITY_DEER_AMBIENT_BABY,
                    ENTITY_DEER_AMBIENT,
                    ENTITY_DEER_DEATH,
                    ENTITY_DEER_HURT,
                    //
                    ENTITY_DUCK_AMBIENT,
                    ENTITY_DUCK_HURT,
                    ENTITY_DUCK_STEP,
                    //
                    ENTITY_FILCH_LIZARD_HISS,
                    ENTITY_FILCH_LIZARD_DEATH,
                    //
                    ENTITY_FISH_DEATH_VICIOUS,
                    ENTITY_FISH_FLOP,
                    ENTITY_FISH_HURT,
                    ENTITY_FISH_SWIM,
                    //
                    ENTITY_HORSE_ANGRY,
                    ENTITY_HORSE_AMBIENT,
                    ENTITY_HORSE_AMBIENT_GHOST,
                    ENTITY_HORSE_AMBIENT_UNDEAD,
                    ENTITY_HORSE_AMBIENT_ZEBRA,
                    ENTITY_HORSE_ANGRY_GHOST,
                    ENTITY_HORSE_ANGRY_UNDEAD,
                    ENTITY_HORSE_DEATH,
                    ENTITY_HORSE_DEATH_GHOST,
                    ENTITY_HORSE_DEATH_UNDEAD,
                    ENTITY_HORSE_HURT,
                    ENTITY_HORSE_HURT_GHOST,
                    ENTITY_HORSE_HURT_UNDEAD,
                    ENTITY_HORSE_HURT_ZEBRA,
                    //
                    ENTITY_MOLE_AMBIENT,
                    ENTITY_MOLE_DEATH,
                    ENTITY_MOLE_HURT,
                    //
                    ENTITY_MOUSE_AMBIENT,
                    ENTITY_MOUSE_DEATH,
                    ENTITY_MOUSE_HURT,
                    //
                    ENTITY_TURKEY_AMBIENT,
                    ENTITY_TURKEY_HURT,
                    //
                    ENTITY_TURTLE_HISS,
                    ENTITY_TURTLE_DEATH,

                    /* Entity (Legacy) */
                    // Hostile
                    ENTITY_BIG_GOLEM_DEATH_LEGACY,
                    ENTITY_BIG_GOLEM_HURT_LEGACY,
                    //
                    ENTITY_RAT_DEATH_LEGACY,
                    //
                    ENTITY_WEREHUMAN_DEATH_LEGACY,
                    ENTITY_WEREHUMAN_HURT_LEGACY,
                    ENTITY_WEREWOLF_TRANSFORM_LEGACY,
                    //
                    ENTITY_WRAITH_AMBIENT_LEGACY,
                    ENTITY_WRAITH_DEATH_LEGACY,
                    ENTITY_WRAITH_HURT_LEGACY,
                    
                    // Passive
                    ENTITY_DUCK_AMBIENT_LEGACY,
                    ENTITY_DUCK_HURT_LEGACY,

                    /* Generic */
                    ENTITY_GENERIC_ARMOR_ON,
                    ENTITY_GENERIC_ARMOR_OFF,
                    ENTITY_GENERIC_ATTACH,
                    ENTITY_GENERIC_CLANG,
                    ENTITY_GENERIC_DESTROY,
                    ENTITY_GENERIC_DRINK,
                    ENTITY_GENERIC_EAT,
                    ENTITY_GENERIC_ENVENOM,
                    ENTITY_GENERIC_EXPLODE,
                    ENTITY_GENERIC_FLAP,
                    ENTITY_GENERIC_FLAP_SOFT,
                    ENTITY_GENERIC_LAUNCH,
                    ENTITY_GENERIC_MAGIC_APPEAR,
                    ENTITY_GENERIC_MAGIC_CONVERSION,
                    ENTITY_GENERIC_MAGIC_CREEPY,
                    ENTITY_GENERIC_MAGIC_ENCHANTED,
                    ENTITY_GENERIC_ROPING,
                    ENTITY_GENERIC_SMACK,
                    ENTITY_GENERIC_STOMP,
                    ENTITY_GENERIC_WHIP,
                    ENTITY_GENERIC_WHOOSH,

                    /* Music */
                    // Record
                    MUSIC_DISC_SHUFFLING
            );
        }
    }
}