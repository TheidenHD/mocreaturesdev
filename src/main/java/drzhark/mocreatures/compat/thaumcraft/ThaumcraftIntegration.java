/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat.thaumcraft;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.init.MoCBlocks;
import drzhark.mocreatures.init.MoCItems;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import thaumcraft.api.ThaumcraftApi;
import thaumcraft.api.aspects.Aspect;
import thaumcraft.api.aspects.AspectEventProxy;
import thaumcraft.api.aspects.AspectList;
import thaumcraft.api.aspects.AspectRegistryEvent;

// Courtesy of Rozmir for most of the chosen aspects
@SuppressWarnings("deprecation")
public class ThaumcraftIntegration {

    @SubscribeEvent
    public static void registerAspects(AspectRegistryEvent event) {
        AspectEventProxy proxy = event.register;

        // Blocks
        proxy.registerObjectTag(new ItemStack(MoCBlocks.ancientOre), new AspectList().add(Aspect.DEATH, 15).add(Aspect.EARTH, 5));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.carvedSilverSandstone), new AspectList().add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 15).add(Aspect.ORDER, 1).add(Aspect.CRYSTAL, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.cobbledDeepWyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 1).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.cobbledWyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 1).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.deepWyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.firestone), new AspectList().add(Aspect.FIRE, 30).add(Aspect.LIGHT, 15));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.gleamingGlass), new AspectList().add(Aspect.CRYSTAL, 5).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.mossyCobbledDeepWyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 1).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.mossyCobbledWyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 3).add(Aspect.ENTROPY, 1).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.silverSand), new AspectList().add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 5).add(Aspect.CRYSTAL, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.silverSandstone), new AspectList().add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 15).add(Aspect.CRYSTAL, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.smoothSilverSandstone), new AspectList().add(Aspect.EARTH, 15).add(Aspect.ENTROPY, 15).add(Aspect.ORDER, 1).add(Aspect.CRYSTAL, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvdirt), new AspectList().add(Aspect.EARTH, 5).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvgrass), new AspectList().add(Aspect.EARTH, 5).add(Aspect.PLANT, 2).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvernNestBlock), new AspectList().add(Aspect.PLANT, 10).add(Aspect.BEAST, 10).add(Aspect.LIFE, 5));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvwoodLog), new AspectList().add(Aspect.PLANT, 20).add(Aspect.AURA, 5));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvwoodPlanks), new AspectList().add(Aspect.PLANT, 3).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvwoodSapling), new AspectList().add(Aspect.PLANT, 15).add(Aspect.AURA, 5));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.wyvstone), new AspectList().add(Aspect.EARTH, 5).add(Aspect.AURA, 1));
        proxy.registerObjectTag(new ItemStack(MoCBlocks.tallWyvgrass), new AspectList().add(Aspect.PLANT, 5).add(Aspect.AIR, 1).add(Aspect.AURA, 1));

        // Items - Armor
        proxy.registerObjectTag(new ItemStack(MoCItems.helmetCroc), new AspectList().add(Aspect.BEAST, 30).add(Aspect.PROTECT, 8).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(MoCItems.plateCroc), new AspectList().add(Aspect.BEAST, 46).add(Aspect.PROTECT, 20).add(Aspect.CRAFT, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.legsCroc), new AspectList().add(Aspect.BEAST, 40).add(Aspect.PROTECT, 16).add(Aspect.CRAFT, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.bootsCroc), new AspectList().add(Aspect.BEAST, 25).add(Aspect.PROTECT, 8).add(Aspect.CRAFT, 3));

        proxy.registerObjectTag(new ItemStack(MoCItems.helmetFur), new AspectList().add(Aspect.BEAST, 30).add(Aspect.PROTECT, 3).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(MoCItems.chestFur), new AspectList().add(Aspect.BEAST, 46).add(Aspect.PROTECT, 10).add(Aspect.CRAFT, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.legsFur), new AspectList().add(Aspect.BEAST, 40).add(Aspect.PROTECT, 6).add(Aspect.CRAFT, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.bootsFur), new AspectList().add(Aspect.BEAST, 25).add(Aspect.PROTECT, 3).add(Aspect.CRAFT, 3));

        proxy.registerObjectTag(new ItemStack(MoCItems.helmetHide), new AspectList().add(Aspect.BEAST, 30).add(Aspect.PROTECT, 6).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(MoCItems.chestHide), new AspectList().add(Aspect.BEAST, 46).add(Aspect.PROTECT, 16).add(Aspect.CRAFT, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.legsHide), new AspectList().add(Aspect.BEAST, 40).add(Aspect.PROTECT, 12).add(Aspect.CRAFT, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.bootsHide), new AspectList().add(Aspect.BEAST, 25).add(Aspect.PROTECT, 6).add(Aspect.CRAFT, 3));
        // Items - Food
        proxy.registerObjectTag(new ItemStack(MoCItems.crabcooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.crabraw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.WATER, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.duckCooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.duckRaw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.WATER, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.omelet), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.ostrichcooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.ostrichraw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AIR, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.ratCooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.ratRaw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.EARTH, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.cookedTurkey), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.rawTurkey), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AIR, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.turtlecooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.turtleraw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.WATER, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.venisonCooked), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.CRAFT, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.venisonRaw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.WATER, 5));
        // Items - Misc
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletbone), new AspectList().add(Aspect.MOTION, 10).add(Aspect.UNDEAD, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletbonefull), new AspectList().add(Aspect.MOTION, 10).add(Aspect.UNDEAD, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletfairy), new AspectList().add(Aspect.MOTION, 10).add(Aspect.AURA, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletfairyfull), new AspectList().add(Aspect.MOTION, 10).add(Aspect.AURA, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletghost), new AspectList().add(Aspect.MOTION, 10).add(Aspect.SOUL, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletghostfull), new AspectList().add(Aspect.MOTION, 10).add(Aspect.SOUL, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletpegasus), new AspectList().add(Aspect.MOTION, 10).add(Aspect.AIR, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.amuletpegasusfull), new AspectList().add(Aspect.MOTION, 10).add(Aspect.AIR, 10).add(Aspect.DESIRE, 3).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.ancientSilverScrap), new AspectList().add(Aspect.DESIRE, 5).add(Aspect.EARTH, 5).add(Aspect.METAL, 10));
        proxy.registerObjectTag(new ItemStack(MoCItems.animalHide), new AspectList().add(Aspect.BEAST, 6).add(Aspect.PROTECT, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.bigcatclaw), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 2));
        proxy.registerObjectTag(new ItemStack(MoCItems.chitin), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.BEAST, 5).add(Aspect.EARTH, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.chitinCave), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.BEAST, 5).add(Aspect.SENSES, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.chitinFrost), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.BEAST, 5).add(Aspect.COLD, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.chitinNether), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.BEAST, 5).add(Aspect.FIRE, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.chitinUndead), new AspectList().add(Aspect.PROTECT, 5).add(Aspect.BEAST, 5).add(Aspect.ENTROPY, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.essencedarkness), new AspectList().add(Aspect.DARKNESS, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.essenceEternal), new AspectList().add(Aspect.SOUL, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.essencefire), new AspectList().add(Aspect.FIRE, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.essenceIce), new AspectList().add(Aspect.COLD, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.essencelight), new AspectList().add(Aspect.AURA, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.essenceundead), new AspectList().add(Aspect.UNDEAD, 15).add(Aspect.ALCHEMY, 3).add(Aspect.EXCHANGE, 3).add(Aspect.BEAST, 6));
        proxy.registerObjectTag(new ItemStack(MoCItems.firestoneChunk), new AspectList().add(Aspect.FIRE, 10).add(Aspect.LIGHT, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.fishnet), new AspectList().add(Aspect.BEAST, 26).add(Aspect.WATER, 3).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(MoCItems.fishnetfull), new AspectList().add(Aspect.BEAST, 26).add(Aspect.WATER, 3).add(Aspect.CRAFT, 3));
        proxy.registerObjectTag(new ItemStack(MoCItems.fur), new AspectList().add(Aspect.BEAST, 6).add(Aspect.PROTECT, 4));
        proxy.registerObjectTag(new ItemStack(MoCItems.heartdarkness), new AspectList().add(Aspect.DARKNESS, 10).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 1).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.heartEternal), new AspectList().add(Aspect.SOUL, 10).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 1).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.heartfire), new AspectList().add(Aspect.FIRE, 10).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 1).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.heartIce), new AspectList().add(Aspect.COLD, 10).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 1).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.heartundead), new AspectList().add(Aspect.UNDEAD, 10).add(Aspect.LIFE, 5).add(Aspect.EXCHANGE, 1).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.hideCroc), new AspectList().add(Aspect.BEAST, 6).add(Aspect.PROTECT, 7));
        proxy.registerObjectTag(new ItemStack(MoCItems.petamulet), new AspectList().add(Aspect.MOTION, 10).add(Aspect.DESIRE, 14).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.petamuletfull), new AspectList().add(Aspect.MOTION, 10).add(Aspect.DESIRE, 14).add(Aspect.BEAST, 15).add(Aspect.VOID, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.recordshuffle), new AspectList().add(Aspect.SENSES, 15).add(Aspect.DESIRE, 10).add(Aspect.AIR, 5).add(Aspect.BEAST, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.sharkteeth), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 2).add(Aspect.WATER, 1));
        proxy.registerObjectTag(new ItemStack(MoCItems.sugarlump), new AspectList().add(Aspect.PLANT, 10).add(Aspect.WATER, 6).add(Aspect.DESIRE, 2).add(Aspect.ENERGY, 2).add(Aspect.AIR, 2));
        proxy.registerObjectTag(new ItemStack(MoCItems.tusksDiamond), new AspectList().add(Aspect.CRYSTAL, 56).add(Aspect.DESIRE, 56).add(Aspect.BEAST, 5).add(Aspect.AVERSION, 20));
        proxy.registerObjectTag(new ItemStack(MoCItems.tusksIron), new AspectList().add(Aspect.METAL, 56).add(Aspect.BEAST, 5).add(Aspect.AVERSION, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.tusksWood), new AspectList().add(Aspect.PLANT, 11).add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8));
        proxy.registerObjectTag(new ItemStack(MoCItems.unicornhorn), new AspectList().add(Aspect.AURA, 3).add(Aspect.BEAST, 5).add(Aspect.LIFE, 5).add(Aspect.AVERSION, 3));
        // Items - Tools
        proxy.registerObjectTag(new ItemStack(MoCItems.bo), new AspectList().add(Aspect.AVERSION, 25).add(Aspect.METAL, 24));
        proxy.registerObjectTag(new ItemStack(MoCItems.katana), new AspectList().add(Aspect.AVERSION, 25).add(Aspect.METAL, 24));
        proxy.registerObjectTag(new ItemStack(MoCItems.nunchaku), new AspectList().add(Aspect.AVERSION, 25).add(Aspect.METAL, 24));
        proxy.registerObjectTag(new ItemStack(MoCItems.sai), new AspectList().add(Aspect.AVERSION, 25).add(Aspect.METAL, 24));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpStingCave), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8).add(Aspect.MOTION, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpStingDirt), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8).add(Aspect.DEATH, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpStingFrost), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8).add(Aspect.TRAP, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpStingNether), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8).add(Aspect.FIRE, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpStingUndead), new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 8).add(Aspect.DARKNESS, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpSwordCave), new AspectList().add(Aspect.BEAST, 16).add(Aspect.DESIRE, 16).add(Aspect.MOTION, 10).add(Aspect.AVERSION, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpSwordDirt), new AspectList().add(Aspect.BEAST, 16).add(Aspect.DESIRE, 16).add(Aspect.DEATH, 10).add(Aspect.AVERSION, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpSwordFrost), new AspectList().add(Aspect.BEAST, 16).add(Aspect.DESIRE, 16).add(Aspect.TRAP, 10).add(Aspect.AVERSION, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpSwordNether), new AspectList().add(Aspect.BEAST, 16).add(Aspect.DESIRE, 16).add(Aspect.FIRE, 10).add(Aspect.AVERSION, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpSwordUndead), new AspectList().add(Aspect.BEAST, 16).add(Aspect.DESIRE, 16).add(Aspect.DARKNESS, 10).add(Aspect.AVERSION, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpAxeCave), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.MOTION, 12).add(Aspect.AVERSION, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpAxeDirt), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.DEATH, 12).add(Aspect.AVERSION, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpAxeFrost), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.TRAP, 12).add(Aspect.AVERSION, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpAxeNether), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.FIRE, 12).add(Aspect.AVERSION, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpAxeUndead), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.DARKNESS, 12).add(Aspect.AVERSION, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpMattockCave), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.MOTION, 12).add(Aspect.TOOL, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpMattockDirt), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.DEATH, 12).add(Aspect.TOOL, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpMattockFrost), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.TRAP, 12).add(Aspect.TOOL, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpMattockNether), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.FIRE, 12).add(Aspect.TOOL, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.scorpMattockUndead), new AspectList().add(Aspect.BEAST, 20).add(Aspect.DESIRE, 20).add(Aspect.DARKNESS, 12).add(Aspect.TOOL, 16));
        proxy.registerObjectTag(new ItemStack(MoCItems.sharksword), new AspectList().add(Aspect.AVERSION, 12).add(Aspect.PLANT, 8).add(Aspect.BEAST, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.sharkaxe), new AspectList().add(Aspect.AVERSION, 15).add(Aspect.PLANT, 10).add(Aspect.BEAST, 14));
        proxy.registerObjectTag(new ItemStack(MoCItems.sharkMattock), new AspectList().add(Aspect.TOOL, 15).add(Aspect.PLANT, 10).add(Aspect.BEAST, 14));
        proxy.registerObjectTag(new ItemStack(MoCItems.silversword), new AspectList().add(Aspect.AVERSION, 20).add(Aspect.METAL, 22).add(Aspect.DEATH, 12));
        proxy.registerObjectTag(new ItemStack(MoCItems.silveraxe), new AspectList().add(Aspect.AVERSION, 24).add(Aspect.METAL, 33).add(Aspect.DEATH, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.silverMattock), new AspectList().add(Aspect.TOOL, 24).add(Aspect.METAL, 33).add(Aspect.DEATH, 15));
        proxy.registerObjectTag(new ItemStack(MoCItems.whip), new AspectList().add(Aspect.AVERSION, 5).add(Aspect.BEAST, 15).add(Aspect.ORDER, 5));
        proxy.registerObjectTag(new ItemStack(MoCItems.reptileWhip), new AspectList().add(Aspect.AVERSION, 7).add(Aspect.BEAST, 20).add(Aspect.ORDER, 5));

        // Entities - Animals
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "blackbear", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "bird", new AspectList().add(Aspect.BEAST, 5).add(Aspect.AIR, 5).add(Aspect.FLIGHT, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "boar", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.DESIRE, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "bunny", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5).add(Aspect.MOTION, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "crocodile", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 10).add(Aspect.EARTH, 5).add(Aspect.AVERSION, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "deer", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 10).add(Aspect.AIR, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "duck", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5).add(Aspect.FLIGHT, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "elephant", new AspectList().add(Aspect.BEAST, 20).add(Aspect.ENTROPY, 20).add(Aspect.EARTH, 20));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "fox", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.AVERSION, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "grizzlybear", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "goat", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.DESIRE, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "komododragon", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 5).add(Aspect.EARTH, 10).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "kitty", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.ENTROPY, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "leoger", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "leopard", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "liard", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "liger", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "lion", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "lither", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "mole", new AspectList().add(Aspect.BEAST, 5).add(Aspect.ENTROPY, 5).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "mouse", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "ostrich", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 5).add(Aspect.FLIGHT, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "pandabear", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.SENSES, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "panthard", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "panther", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "panthger", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "rat", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "raccoon", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5).add(Aspect.DESIRE, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "snake", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "tiger", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 15).add(Aspect.AVERSION, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "turkey", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5).add(Aspect.FLIGHT, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "turtle", new AspectList().add(Aspect.BEAST, 5).add(Aspect.PROTECT, 10).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "wildhorse", new AspectList().add(Aspect.BEAST, 15).add(Aspect.AIR, 5).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "wildpolarbear", new AspectList().add(Aspect.BEAST, 15).add(Aspect.COLD, 10).add(Aspect.AVERSION, 20));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "wwolf", new AspectList().add(Aspect.BEAST, 15).add(Aspect.EARTH, 10).add(Aspect.AVERSION, 5));
        // Entities - Insects
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "ant", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "bee", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "butterfly", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "cricket", new AspectList().add(Aspect.BEAST, 2).add(Aspect.EARTH, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "dragonfly", new AspectList().add(Aspect.BEAST, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "firefly", new AspectList().add(Aspect.BEAST, 2).add(Aspect.LIGHT, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "fly", new AspectList().add(Aspect.BEAST, 2).add(Aspect.ENTROPY, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "maggot", new AspectList().add(Aspect.BEAST, 2).add(Aspect.ENTROPY, 2).add(Aspect.LIFE, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "roach", new AspectList().add(Aspect.BEAST, 2).add(Aspect.ENTROPY, 2).add(Aspect.FLIGHT, 2));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "snail", new AspectList().add(Aspect.BEAST, 2).add(Aspect.ALCHEMY, 2).add(Aspect.WATER, 2));
        // Entities - Monsters
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "cavescorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.SENSES, 5).add(Aspect.ENTROPY, 10).add(Aspect.MOTION, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "dirtscorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 5).add(Aspect.ENTROPY, 10).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "firescorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ENTROPY, 10).add(Aspect.FIRE, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "flamewraith", new AspectList().add(Aspect.UNDEAD, 10).add(Aspect.MAN, 5).add(Aspect.SOUL, 10).add(Aspect.FIRE, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "frostscorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.COLD, 5).add(Aspect.ENTROPY, 10).add(Aspect.TRAP, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "hellrat", new AspectList().add(Aspect.BEAST, 10).add(Aspect.EARTH, 10).add(Aspect.FIRE, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "horsemob", new AspectList().add(Aspect.ENTROPY, 10).add(Aspect.BEAST, 15).add(Aspect.AIR, 5).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "petscorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ENTROPY, 10).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "silverskeleton", new AspectList().add(Aspect.UNDEAD, 20).add(Aspect.MAN, 5).add(Aspect.METAL, 10).add(Aspect.DEATH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "undeadscorpion", new AspectList().add(Aspect.BEAST, 10).add(Aspect.ENTROPY, 15).add(Aspect.DARKNESS, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "wraith", new AspectList().add(Aspect.UNDEAD, 10).add(Aspect.MAN, 5).add(Aspect.SOUL, 10));
        // Entities - Mythical Creatures
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "biggolem", new AspectList().add(Aspect.METAL, 15).add(Aspect.MECHANISM, 10).add(Aspect.MOTION, 10).add(Aspect.MAGIC, 20).add(Aspect.EARTH, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "caveogre", new AspectList().add(Aspect.BEAST, 20).add(Aspect.ENTROPY, 10).add(Aspect.DARKNESS, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "darkmanticore", new AspectList().add(Aspect.BEAST, 15).add(Aspect.DARKNESS, 5).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.MOTION, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "ent", new AspectList().add(Aspect.PLANT, 20).add(Aspect.MAN, 5).add(Aspect.MAGIC, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "firemanticore", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.FIRE, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "fireogre", new AspectList().add(Aspect.BEAST, 20).add(Aspect.ENTROPY, 10).add(Aspect.FIRE, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "frostmanticore", new AspectList().add(Aspect.BEAST, 15).add(Aspect.COLD, 5).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.TRAP, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "greenogre", new AspectList().add(Aspect.BEAST, 20).add(Aspect.ENTROPY, 10).add(Aspect.EARTH, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "manticorepet", new AspectList().add(Aspect.BEAST, 20).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "minigolem", new AspectList().add(Aspect.METAL, 10).add(Aspect.MECHANISM, 5).add(Aspect.MOTION, 5).add(Aspect.MAGIC, 10).add(Aspect.EARTH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "plainmanticore", new AspectList().add(Aspect.BEAST, 20).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.DEATH, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "toxicmanticore", new AspectList().add(Aspect.BEAST, 15).add(Aspect.DEATH, 5).add(Aspect.FLIGHT, 10).add(Aspect.ENTROPY, 15).add(Aspect.DARKNESS, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "werewolf", new AspectList().add(Aspect.BEAST, 15).add(Aspect.DARKNESS, 10).add(Aspect.EXCHANGE, 5).add(Aspect.MAN, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "wyvern", new AspectList().add(Aspect.BEAST, 15).add(Aspect.FLIGHT, 20).add(Aspect.AVERSION, 5).add(Aspect.DEATH, 10));
        // Entities - Sea Creatures
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "anchovy", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "angelfish", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "angler", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "bass", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "cod", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "clownfish", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "crab", new AspectList().add(Aspect.BEAST, 5).add(Aspect.EARTH, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "dolphin", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "fishy", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "goldfish", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "hippotang", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "jellyfish", new AspectList().add(Aspect.BEAST, 5).add(Aspect.DEATH, 10).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "manderin", new AspectList().add(Aspect.BEAST, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "mantaray", new AspectList().add(Aspect.BEAST, 15).add(Aspect.WATER, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "piranha", new AspectList().add(Aspect.BEAST, 5).add(Aspect.AVERSION, 5).add(Aspect.WATER, 5));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "salmon", new AspectList().add(Aspect.BEAST, 10).add(Aspect.WATER, 10));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "shark", new AspectList().add(Aspect.BEAST, 15).add(Aspect.AVERSION, 15).add(Aspect.WATER, 15));
        ThaumcraftApi.registerEntityTag(MoCConstants.MOD_PREFIX + "stingray", new AspectList().add(Aspect.BEAST, 10).add(Aspect.DEATH, 10).add(Aspect.WATER, 10));
    }
}
