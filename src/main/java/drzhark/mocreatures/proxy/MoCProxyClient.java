/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.proxy;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.gui.MoCGUIEntityNamer;
import drzhark.mocreatures.client.model.*;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import drzhark.mocreatures.client.renderer.entity.*;
import drzhark.mocreatures.client.renderer.entity.legacy.MoCLegacyRenderBigCat;
import drzhark.mocreatures.client.renderer.fx.MoCEntityFXStar;
import drzhark.mocreatures.client.renderer.fx.MoCEntityFXUndead;
import drzhark.mocreatures.client.renderer.fx.MoCEntityFXVacuum;
import drzhark.mocreatures.client.renderer.fx.MoCEntityFXVanish;
import drzhark.mocreatures.client.renderer.texture.MoCTextures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.hostile.*;
import drzhark.mocreatures.entity.passive.*;
import drzhark.mocreatures.init.MoCEntities;
import net.minecraft.client.Minecraft;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.particles.ParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraftforge.fml.client.registry.RenderingRegistry;

public class MoCProxyClient extends MoCProxy {

    public static Minecraft mc = Minecraft.getInstance();
    public static MoCProxyClient instance;
    public static MoCTextures mocTextures = new MoCTextures();

    public MoCProxyClient() {
        instance = this;
    }

    @Override
    public void registerRenderers() {
        super.registerRenderers();
    }

    @Override
    public ResourceLocation getArmorTexture(String texture) {
        return mocTextures.getArmorTexture(texture);
    }

    @Override
    public ResourceLocation getBlockTexture(String texture) {
        return mocTextures.getBlockTexture(texture);
    }

    @Override
    public ResourceLocation getItemTexture(String texture) {
        return mocTextures.getItemTexture(texture);
    }

    @Override
    public ResourceLocation getModelTexture(String texture) {
        return mocTextures.getModelTexture(texture);
    }

    @Override
    public ResourceLocation getGuiTexture(String texture) {
        return mocTextures.getGuiTexture(texture);
    }

    @Override
    public ResourceLocation getMiscTexture(String texture) {
        return mocTextures.getMiscTexture(texture);
    }

    @SuppressWarnings({"deprecation", "unchecked", "rawtypes"})
    @Override
    public void registerRenderInformation() {
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUNNY, manager -> new MoCRenderBunny(manager, new MoCModelBunny(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BIRD, manager -> new MoCRenderBird(manager, new MoCModelBird(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TURTLE, manager -> new MoCRenderTurtle(manager, new MoCModelTurtle(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MOUSE, manager -> new MoCRenderMouse(manager, new MoCModelMouse(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SNAKE, manager -> new MoCRenderSnake(manager, new MoCModelSnake(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TURKEY, manager -> new MoCRenderMoC(manager, new MoCModelTurkey(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BUTTERFLY, manager -> new MoCRenderButterfly(manager, new MoCModelButterfly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WILDHORSE, manager -> new MoCRenderHorse(manager, new MoCModelHorse()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HORSEMOB, manager -> new MoCRenderHorseMob(manager, new MoCModelHorseMob()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BOAR, manager -> new MoCRenderMoC(manager, new MoCModelBoar(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEAR, manager -> new MoCRenderMoC(manager, new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GRIZZLY_BEAR, manager -> new MoCRenderMoC(manager, new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANDA_BEAR, manager -> new MoCRenderMoC(manager, new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.POLAR_BEAR, manager -> new MoCRenderMoC(manager, new MoCModelBear(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DUCK, manager -> new MoCRenderMoC(manager, new MoCModelDuck(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DEER, manager -> new MoCRenderMoC(manager, new MoCModelDeer(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WWOLF, manager -> new MoCRenderWWolf(manager, new MoCModelWolf(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WRAITH, manager -> new MoCRenderWraith(manager, new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FLAME_WRAITH, manager -> new MoCRenderWraith(manager, new MoCModelWraith(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WEREWOLF, manager -> new MoCRenderWerewolf(manager, new MoCModelWerehuman(), new MoCModelWerewolf(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FILCH_LIZARD, manager -> new MoCRenderFilchLizard(manager, new MoCModelFilchLizard(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FOX, manager -> new MoCRenderMoC(manager, new MoCModelFox(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SHARK, manager -> new MoCRenderShark(manager, new MoCModelShark(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DOLPHIN, manager -> new MoCRenderDolphin(manager, new MoCModelDolphin(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FISHY, manager -> new MoCRenderMoC(manager, new MoCModelFishy(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.EGG, manager -> new MoCRenderEgg(manager, new MoCModelEgg(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KITTY, manager -> new MoCRenderKitty(manager, new MoCModelKitty(0.0F, 15F), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KITTY_BED, manager -> new MoCRenderKittyBed(manager, new MoCModelKittyBed(), new MoCModelKittyBed2(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LITTERBOX, manager -> new MoCRenderLitterBox(manager, new MoCModelLitterBox(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.RAT, manager -> new MoCRenderRat(manager, new MoCModelRat(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HELLRAT, manager -> new MoCRenderHellRat(manager, new MoCModelRat(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CAVE_SCORPION, manager -> new MoCRenderScorpion(manager, new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DIRT_SCORPION, manager -> new MoCRenderScorpion(manager, new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIRE_SCORPION, manager -> new MoCRenderScorpion(manager, new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FROST_SCORPION, manager -> new MoCRenderScorpion(manager, new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.UNDEAD_SCORPION, manager -> new MoCRenderScorpion(manager, new MoCModelScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CROCODILE, manager -> new MoCRenderCrocodile(manager, new MoCModelCrocodile(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANTARAY, manager -> new MoCRenderMoC(manager, new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.STINGRAY, manager -> new MoCRenderMoC(manager, new MoCModelRay(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.JELLYFISH, manager -> new MoCRenderMoC(manager, new MoCModelJellyFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GOAT, manager -> new MoCRenderGoat(manager, new MoCModelGoat(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.OSTRICH, manager -> new MoCRenderOstrich(manager, new MoCModelOstrich(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BEE, manager -> new MoCRenderInsect(manager, new MoCModelBee()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FLY, manager -> new MoCRenderInsect(manager, new MoCModelFly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DRAGONFLY, manager -> new MoCRenderInsect(manager, new MoCModelDragonfly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIREFLY, manager -> new MoCRenderFirefly(manager, new MoCModelFirefly()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRICKET, manager -> new MoCRenderCricket(manager, new MoCModelCricket()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GRASSHOPPER, manager -> new MoCRenderGrasshopper(manager, new MoCModelGrasshopper()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SNAIL, manager -> new MoCRenderMoC(manager, new MoCModelSnail(), 0.0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BIG_GOLEM, manager -> new MoCRenderGolem(manager, new MoCModelGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TROCK, manager -> new MoCRenderTRock(manager));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PET_SCORPION, manager -> new MoCRenderPetScorpion(manager, new MoCModelPetScorpion(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ELEPHANT, manager -> new MoCRenderMoC(manager, new MoCModelElephant(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.KOMODO_DRAGON, manager -> new MoCRenderMoC(manager, new MoCModelKomodo(), 0.3F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.WYVERN, manager -> new MoCRenderMoC(manager, new MoCModelWyvern(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GREEN_OGRE, manager -> new MoCRenderMoC(manager, new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CAVE_OGRE, manager -> new MoCRenderMoC(manager, new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIRE_OGRE, manager -> new MoCRenderMoC(manager, new MoCModelOgre(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ROACH, manager -> new MoCRenderInsect(manager, new MoCModelRoach()));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MAGGOT, manager -> new MoCRenderMoC(manager, new MoCModelMaggot(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CRAB, manager -> new MoCRenderMoC(manager, new MoCModelCrab(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.RACCOON, manager -> new MoCRenderMoC(manager, new MoCModelRaccoon(), 0.4F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MINI_GOLEM, manager -> new MoCRenderMoC(manager, new MoCModelMiniGolem(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SILVER_SKELETON, manager -> new MoCRenderMoC(manager, new MoCModelSilverSkeleton(), 0.6F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANT, manager -> new MoCRenderMoC(manager, new MoCModelAnt(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.COD, manager -> new MoCRenderMoC(manager, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.SALMON, manager -> new MoCRenderMoC(manager, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.BASS, manager -> new MoCRenderMoC(manager, new MoCModelMediumFish(), 0.2F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANCHOVY, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANGELFISH, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ANGLER, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.CLOWNFISH, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.GOLDFISH, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.HIPPOTANG, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANDERIN, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PIRANHA, manager -> new MoCRenderMoC(manager, new MoCModelSmallFish(), 0.1F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.ENT, manager -> new MoCRenderMoC(manager, new MoCModelEnt(), 0.5F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MOLE, manager -> new MoCRenderMoC(manager, new MoCModelMole(), 0F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.DARK_MANTICORE, manager -> new MoCRenderMoC(manager, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FIRE_MANTICORE, manager -> new MoCRenderMoC(manager, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.FROST_MANTICORE, manager -> new MoCRenderMoC(manager, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PLAIN_MANTICORE, manager -> new MoCRenderMoC(manager, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TOXIC_MANTICORE, manager -> new MoCRenderMoC(manager, new MoCModelManticore(), 0.7F));
        RenderingRegistry.registerEntityRenderingHandler(MoCEntities.MANTICORE_PET, manager -> new MoCRenderMoC(manager, new MoCModelManticorePet(), 0.7F));

        if (MoCreatures.proxy.legacyBigCatModels) {
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOGER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOPARD, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIARD, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIGER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LION, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LITHER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHARD, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHGER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TIGER, manager -> new MoCLegacyRenderBigCat(manager, new MoCLegacyModelBigCat2(), new MoCLegacyModelBigCat1(), 0.5F));
        } else {
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOGER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LEOPARD, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIARD, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LIGER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LION, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.LITHER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHARD, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.PANTHGER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
            RenderingRegistry.registerEntityRenderingHandler(MoCEntities.TIGER, manager -> new MoCRenderMoC(manager, new MoCModelBigCat(), 0.5F));
        }
    }

    @Override
    public PlayerEntity getPlayer() {
        return MoCProxyClient.mc.player;
    }

    /**
     * Sets the name client side. Name is synchronized with data watchers
     */
    @Override
    public void setName(PlayerEntity player, IMoCEntity mocanimal) {
        mc.displayGuiScreen(new MoCGUIEntityNamer(mocanimal, mocanimal.getPetName()));
    }

    @Override
    public void UndeadFX(Entity entity) {
        //if (!((Boolean) MoCreatures.particleFX.get()).booleanValue()) return;
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        int i = (densityInt / 2) * (entity.world.rand.nextInt(2) + 1);
        if (i == 0) {
            i = 1;
        }
        if (i > 10) {
            i = 10;
        }
        for (int x = 0; x < i; x++) {
            MoCEntityFXUndead FXUndead = new MoCEntityFXUndead((ClientWorld)entity.getEntityWorld(), entity.getPosX(), entity.getPosY() + entity.world.rand.nextFloat() * entity.getHeight(), entity.getPosZ());
            mc.particles.addEffect(FXUndead);

        }
    }

    @Override
    public void StarFX(MoCEntityHorse entity) {
        int densityInt = MoCreatures.proxy.getParticleFX();
        if (densityInt == 0) {
            return;
        }

        if ((entity.getTypeMoC() >= 50 && entity.getTypeMoC() < 60) || entity.getTypeMoC() == 36) {

            float fRed = entity.colorFX(1, entity.getTypeMoC());
            float fGreen = entity.colorFX(2, entity.getTypeMoC());
            float fBlue = entity.colorFX(3, entity.getTypeMoC());

            int i = densityInt * entity.world.rand.nextInt(2);// + 2;
            for (int x = 0; x < i; x++) {
                MoCEntityFXStar FXStar = new MoCEntityFXStar(mc.world, entity.getPosX(), entity.getPosY() + entity.world.rand.nextFloat() * entity.getHeight(), entity.getPosZ(), fRed, fGreen, fBlue);
                mc.particles.addEffect(FXStar);

            }

        }
    }

    @Override
    public void LavaFX(Entity entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }
        double var2 = entity.world.rand.nextGaussian() * 0.02D;
        double var4 = entity.world.rand.nextGaussian() * 0.02D;
        double var6 = entity.world.rand.nextGaussian() * 0.02D;
        mc.world.addParticle(ParticleTypes.LAVA, entity.getPosX() + entity.world.rand.nextFloat() * entity.getWidth() - entity.getWidth(), entity.getPosY() + 0.5D + entity.world.rand.nextFloat() * entity.getHeight(), entity.getPosZ() + entity.world.rand.nextFloat() * entity.getWidth() - entity.getWidth(), var2, var4, var6);

    }

    @Override
    public void VanishFX(MoCEntityHorse entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < densityInt * 8; ++var6) {
            double newPosX = ((float) entity.getPosX() + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.getPosY() + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.getPosZ() + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish FXVanish = new MoCEntityFXVanish((ClientWorld) entity.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getTypeMoC()), entity.colorFX(2, entity.getTypeMoC()), entity.colorFX(3, entity.getTypeMoC()), false);
            mc.particles.addEffect(FXVanish);
        }
    }

    @Override
    public void MaterializeFX(MoCEntityHorse entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6) {
            double newPosX = ((float) entity.getPosX() + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.getPosY() + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.getPosZ() + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish FXVanish = new MoCEntityFXVanish(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1, entity.getTypeMoC()), entity.colorFX(2, entity.getTypeMoC()), entity.colorFX(3, entity.getTypeMoC()), true);
            mc.particles.addEffect(FXVanish);
        }

    }

    @Override
    public void VacuumFX(MoCEntityGolem entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var1 = 0; var1 < 2; ++var1) {
            double newPosX = entity.getPosX() - (1.5 * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosZ = entity.getPosZ() - (1.5 * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
            double newPosY = entity.getPosY() + (entity.getHeight() - 0.8D - entity.getAdjustedYOffset() * 1.8);// + (entity.world.rand.nextDouble() * ((double) entity.getHeight() - (double) entity.getAdjustedYOffset() * 2));
            //adjustedYOffset from 0 (tallest) to 1.45 (on the ground)
            //height = 4F

            double speedX = (entity.world.rand.nextDouble() - 0.5D) * 4.0D;
            double speedY = -entity.world.rand.nextDouble();
            double speedZ = (entity.world.rand.nextDouble() - 0.5D) * 4.0D;
            MoCEntityFXVacuum FXVacuum = new MoCEntityFXVacuum(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, entity.colorFX(1), entity.colorFX(2), entity.colorFX(3), 146);
            mc.particles.addEffect(FXVacuum);
        }
    }

    @SuppressWarnings("unused")
    @Override
    public void hammerFX(PlayerEntity entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 10); ++var6) {
            double newPosX = ((float) entity.getPosX() + entity.world.rand.nextFloat());
            double newPosY = 0.3D + ((float) entity.getPosY() + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.getPosZ() + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            // TODO - fix particle fx
            /*EntitySpellParticleFX hammerFX = new EntitySpellParticleFX(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ);
            hammerFX.setBaseSpellTextureIndex(144);
            ((EntityFX) hammerFX).setRBGColorF(74F / 256F, 145F / 256F, 71F / 256F);
            mc.effectRenderer.addEffect(hammerFX);*/
        }

    }

    @Override
    public void teleportFX(PlayerEntity entity) {
        int densityInt = (MoCreatures.proxy.getParticleFX());
        if (densityInt == 0) {
            return;
        }

        for (int var6 = 0; var6 < (densityInt * 50); ++var6) {
            double newPosX = ((float) entity.getPosX() + entity.world.rand.nextFloat());
            double newPosY = 0.7D + ((float) entity.getPosY() + entity.world.rand.nextFloat());
            double newPosZ = ((float) entity.getPosZ() + entity.world.rand.nextFloat());
            int var19 = entity.world.rand.nextInt(2) * 2 - 1;
            double speedY = (entity.world.rand.nextFloat() - 0.5D) * 0.5D;
            double speedX = entity.world.rand.nextFloat() * 2.0F * var19;
            double speedZ = entity.world.rand.nextFloat() * 2.0F * var19;

            MoCEntityFXVanish hammerFX = new MoCEntityFXVanish(mc.world, newPosX, newPosY, newPosZ, speedX, speedY, speedZ, 189F / 256F, 110F / 256F, 229F / 256F, true);
            mc.particles.addEffect(hammerFX);
        }

    }

    @Override
    public int getProxyMode() {
        return 2;
    }

//    @Override //TODO TheidenHD
//    public void configInit(FMLPreInitializationEvent event) {
//        super.configInit(event);
//    }

    @Override
    public void resetAllData() {
        super.resetAllData();
    }

    @Override
    public int getParticleFX() {
        return this.particleFX;
    }

    @Override
    public boolean getDisplayPetName() {
        return this.displayPetName;
    }

    @Override
    public boolean getDisplayPetIcons() {
        return this.displayPetIcons;
    }

    @Override
    public boolean getDisplayPetHealth() {
        return this.displayPetHealth;
    }

    @Override
    public boolean getAnimateTextures() {
        return this.animateTextures;
    }

    @Override
    public void printMessageToPlayer(String msg) {
        try {
            Minecraft.getInstance().player.sendMessage(new TranslationTextComponent(msg), Minecraft.getInstance().player.getUniqueID());
        } catch (Exception e) {

        }

    }
}