/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIMoverHelperMoC;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.ai.PathNavigateFlyer;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageHealth;
import net.minecraft.client.resources.I18n;
import net.minecraft.entity.*;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.monster.MonsterEntity;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.ITextComponent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.Difficulty;
import net.minecraft.world.IServerWorld;
import net.minecraft.world.World;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import net.minecraftforge.fml.network.PacketDistributor;

import javax.annotation.Nullable;
import java.util.UUID;

public abstract class MoCEntityMob extends MonsterEntity implements IMoCEntity {

    protected static final DataParameter<Boolean> ADULT = EntityDataManager.createKey(MoCEntityMob.class, DataSerializers.BOOLEAN);
    protected static final DataParameter<Integer> TYPE = EntityDataManager.createKey(MoCEntityMob.class, DataSerializers.VARINT);
    protected static final DataParameter<Integer> AGE = EntityDataManager.createKey(MoCEntityMob.class, DataSerializers.VARINT);
    protected static final DataParameter<String> NAME_STR = EntityDataManager.createKey(MoCEntityMob.class, DataSerializers.STRING);
    protected boolean divePending;
    protected String texture;
    protected PathNavigator navigatorWater;
    protected PathNavigator navigatorFlyer;
    protected EntityAIWanderMoC2 wander;

    protected MoCEntityMob(World world) {
        super(world);
        this.texture = "blank.jpg";
        this.moveHelper = new EntityAIMoverHelperMoC(this);
        this.navigatorWater = new SwimmerPathNavigator(this, world);
        this.navigatorFlyer = new PathNavigateFlyer(this, world);
        this.wander = new EntityAIWanderMoC2(this, 1.0D, 80);
        this.goalSelector.addGoal(4, this.wander);
    }

    @OnlyIn(Dist.CLIENT)
    @Override
    public ITextComponent getName() {
        ITextComponent entityString = this.getProfessionName();
        if (!MoCreatures.proxy.verboseEntityNames || entityString == null) return super.getName();
        String translationKey = "entity." + entityString + ".verbose.name";
        String translatedString = I18n.format(translationKey);
        return !translatedString.equals(translationKey) ? translatedString : super.getName();
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(getMoveSpeed());
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(getAttackStrenght());
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(20.0D);
    }

    @Override
    public ILivingEntityData onInitialSpawn(IServerWorld worldIn, DifficultyInstance difficultyIn, SpawnReason reason, @Nullable ILivingEntityData spawnDataIn, @Nullable CompoundNBT dataTag) {
        selectType();
        return super.onInitialSpawn(worldIn, difficultyIn, reason, spawnDataIn, dataTag);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture(this.texture);
    }

    protected double getAttackStrenght() {
        return 2D;
    }

    /**
     * Put your code to choose a texture / the mob type in here. Will be called
     * by default MocEntity constructors.
     */
    @Override
    public void selectType() {
        setTypeMoC(1);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(ADULT, false);
        this.dataManager.register(TYPE, 0);
        this.dataManager.register(AGE, 45);
        this.dataManager.register(NAME_STR, "");
    }

    @Override
    public int getTypeMoC() {
        return this.dataManager.get(TYPE);
    }

    @Override
    public void setTypeMoC(int i) {
        this.dataManager.set(TYPE, i);
    }

    @Override
    public boolean getIsAdult() {
        return this.dataManager.get(ADULT);
    }

    @Override
    public void setAdult(boolean flag) {
        this.dataManager.set(ADULT, flag);
    }

    @Override
    public boolean getIsTamed() {
        return false;
    }

    @Override
    public String getPetName() {
        return this.dataManager.get(NAME_STR);
    }

    @Override
    public void setPetName(String name) {
        this.dataManager.set(NAME_STR, String.valueOf(name));
    }

    @Override
    public int getAge() {
        return this.dataManager.get(AGE);
    }

    @Override
    public void setAge(int i) {
        this.dataManager.set(AGE, i);
        if (getAge() >= getMaxAge()) {
            setAdult(true);
        }
    }

    @Nullable
    public UUID getOwnerId() {
        return null;
    }

    @Override
    public int getOwnerPetId() {
        return 0;
    }

    @Override
    public void setOwnerPetId(int petId) {
    }

    @Override
    public boolean getCanSpawnHere() {
        boolean willSpawn = super.getCanSpawnHere();
        boolean debug = MoCreatures.proxy.debug;
        if (willSpawn && debug)
            MoCreatures.LOGGER.info("Mob: " + this.getName() + " at: " + this.getPosition() + " State: " + this.world.getBlockState(this.getPosition()) + " biome: " + MoCTools.biomeName(world, getPosition()));
        return willSpawn;
    }

    public boolean entitiesToIgnore(Entity entity) {
        if ((!(entity instanceof MobEntity)) || (entity instanceof MonsterEntity) || (entity instanceof MoCEntityEgg))
            return true;
        return entity instanceof MoCEntityKittyBed || entity instanceof MoCEntityLitterBox || this.getIsTamed() && entity instanceof MoCEntityAnimal && ((MoCEntityAnimal) entity).getIsTamed() || entity instanceof EntityWolf && !MoCreatures.proxy.attackWolves || entity instanceof MoCEntityHorse && !MoCreatures.proxy.attackHorses;
    }

    @Override
    public boolean checkSpawningBiome() {
        return true;
    }

    @Override
    public void livingTick() {
        if (!this.world.isRemote) {
            if (getIsTamed() && this.rand.nextInt(200) == 0) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
            }

            if (this.isHarmedByDaylight() && this.world.isDaytime()) {
                float var1 = this.getBrightness();
                if (var1 > 0.5F && this.world.canBlockSeeSky(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY()), MathHelper.floor(this.getPosZ()))) && this.rand.nextFloat() * 30.0F < (var1 - 0.4F) * 2.0F) {
                    this.setFire(8);
                }
            }
            if (getAge() == 0) setAge(getMaxAge() - 10); //fixes tiny creatures spawned by error
            if (!getIsAdult() && (this.rand.nextInt(300) == 0)) {
                setAge(getAge() + 1);
                if (getAge() >= getMaxAge()) {
                    setAdult(true);
                }
            }

            if (getIsFlying() && this.getNavigator().noPath() && !isMovementCeased() && this.getAttackTarget() == null && this.rand.nextInt(20) == 0) {
                this.wander.makeUpdate();
            }
        }

        this.getNavigator().onUpdateNavigation();
        super.livingTick();
    }

    protected int getMaxAge() {
        return 100;
    }

    protected boolean isHarmedByDaylight() {
        return false;
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (!this.world.isRemote && getIsTamed()) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageHealth(this.getEntityId(), this.getHealth()), new PacketDistributor.TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        return super.attackEntityFrom(damagesource, i);
    }

    /**
     * Boolean used to select pathfinding behavior
     */
    public boolean isFlyer() {
        return false;
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Adult", getIsAdult());
        nbttagcompound.putInt("Edad", getAge());
        nbttagcompound.putString("Name", getPetName());
        nbttagcompound.putInt("TypeInt", getTypeMoC());

    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setAdult(nbttagcompound.getBoolean("Adult"));
        setAge(nbttagcompound.getInt("Edad"));
        setPetName(nbttagcompound.getString("Name"));
        setTypeMoC(nbttagcompound.getInt("TypeInt"));

    }

    @Override
    public void fall(float f, float f1) {
        if (!isFlyer()) {
            super.fall(f, f1);
        }
    }

    @Override
    public boolean isOnLadder() {
        if (isFlyer()) {
            return false;
        } else {
            return super.isOnLadder();
        }
    }

    @Override
    public void travel(Vector3d vector) {
        if (!isFlyer()) {
            super.travel(vector);
            return;
        }
        this.moveEntityWithHeadingFlyer(vector);
    }

    public void moveEntityWithHeadingFlyer(Vector3d vector) {
        if (this.isServerWorld()) {

            this.moveRelative(0.1F, vector);
            this.move(MoverType.SELF, this.getMotion());
            this.getMotion().getX() *= 0.8999999761581421D;
            this.getMotion().getY() *= 0.8999999761581421D;
            this.getMotion().getZ() *= 0.8999999761581421D;
        } else {
            super.travel(vector);
        }
    }


    /**
     * Used to synchronize the attack animation between server and client
     */
    @Override
    public void performAnimation(int attackType) {
    }

    public float getMoveSpeed() {
        return 0.7F;
    }

    @Override
    public int nameYOffset() {
        return 0;
    }

    @Override
    public boolean renderName() {
        return MoCreatures.proxy.getDisplayPetName() && (getPetName() != null && !getPetName().isEmpty() && (!this.isBeingRidden()) && (this.getRidingEntity() == null));
    }

    @Override
    public void makeEntityJump() {
        //TODO
    }

    @Override
    public void makeEntityDive() {
        this.divePending = true;
    }

    @Override
    protected boolean canDespawn() {
        return !getIsTamed();
    }

    @Override
    public void setDead() {
        // Server check required to prevent tamed entities from being duplicated on client-side
        if (!this.world.isRemote && (getIsTamed()) && (getHealth() > 0)) {
            return;
        }
        super.setDead();
    }

    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    @Override
    public float getAdjustedYOffset() {
        return 0F;
    }

    @Override
    public void setArmorType(int i) {
    }

    @Override
    public float pitchRotationOffset() {
        return 0F;
    }

    @Override
    public float rollRotationOffset() {
        return 0F;
    }

    @Override
    public float yawRotationOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedZOffset() {
        return 0F;
    }

    @Override
    public float getAdjustedXOffset() {
        return 0F;
    }

    @Override
    public boolean canAttackTarget(LivingEntity entity) {
        return false;
    }

    @Override
    public boolean getIsSitting() {
        return false;
    }

    @Override
    public boolean isNotScared() {
        return true;
    }

    @Override
    public boolean isMovementCeased() {
        return false;
    }

    @Override
    public boolean shouldAttackPlayers() {
        return this.world.getDifficulty() != Difficulty.PEACEFUL;
    }

    @Override
    public double getDivingDepth() {
        return 0;
    }

    @Override
    public boolean isDiving() {
        return false;
    }

    @Override
    public void forceEntityJump() {
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        boolean flag = entityIn.attackEntityFrom(DamageSource.causeMobDamage(this), ((int) this.getEntityAttribute(Attributes.ATTACK_DAMAGE).getAttributeValue()));
        if (flag) {
            this.applyEnchantments(this, entityIn);
        }

        return flag;
    }

    @Override
    public int maxFlyingHeight() {
        return 5;
    }

    @Override
    public int minFlyingHeight() {
        return 1;
    }

    @Override
    public PathNavigator getNavigator() {
        if (this.isInWater() && this.isAmphibian()) {
            return this.navigatorWater;
        }
        if (this.isFlyer()) {
            return this.navigatorFlyer;
        }
        return this.navigator;
    }

    public boolean isAmphibian() {
        return false;
    }

    @Override
    public boolean getIsFlying() {
        return isFlyer();
    }

    /**
     * Returns true if the entity is of the @link{EntityClassification} provided
     *
     * @param type          The EntityClassification type this entity is evaluating
     * @param forSpawnCount If this is being invoked to check spawn count caps.
     * @return If the creature is of the type provided
     */
    @Override
    public boolean isCreatureType(EntityClassification type, boolean forSpawnCount) {
        return type == EntityClassification.MONSTER;
    }

    @Override
    public String getClazzString() {
        return EntityList.getEntityString(this);
    }

    @Override
    public boolean getIsGhost() {
        return false;
    }
}
