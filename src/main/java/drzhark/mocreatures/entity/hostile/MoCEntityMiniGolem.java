/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.SharedMonsterAttributes;
import net.minecraft.world.entity.ai.*;
import net.minecraft.world.entity.monster.EntityIronGolem;
import net.minecraft.world.entity.player.Player;
import net.minecraft.init.SoundEvents;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MoCEntityMiniGolem extends MoCEntityMob {

    private static final EntityDataAccessor<Boolean> ANGRY = SynchedEntityData.defineId(MoCEntityMiniGolem.class, EntityDataSerializers.BOOLEAN);
    private static final EntityDataAccessor<Boolean> HAS_ROCK = SynchedEntityData.defineId(MoCEntityMiniGolem.class, EntityDataSerializers.BOOLEAN);
    public int tCounter;
    public MoCEntityThrowableRock tempRock;

    public MoCEntityMiniGolem(EntityType<? extends MoCEntityMiniGolem> type, Level world) {
        super(type, world);
        this.texture = "mini_golem.png";
        //setSize(0.9F, 1.2F);
        this.xpReward = 5;
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(2, new MoCEntityMiniGolem.AIGolemAttack(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtPlayerGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoCEntityMiniGolem.AIGolemTarget<>(this, Player.class, true));
        this.targetSelector.addGoal(3, new MoCEntityMiniGolem.AIGolemTarget<>(this, IronGolem.class, true));
    }

    public static AttributeSupplier.Builder createAttributes() {
        return MoCEntityMob.createAttributes()
                .add(Attributes.MAX_HEALTH, 20.0D)
                .add(Attributes.ARMOR, 6.0D)
                .add(Attributes.MOVEMENT_SPEED, 0.25D)
                .add(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(ANGRY, Boolean.FALSE);
        this.entityData.define(HAS_ROCK, Boolean.FALSE);
    }

    public boolean getIsAngry() {
        return this.entityData.get(ANGRY);
    }

    public void setIsAngry(boolean flag) {
        this.entityData.set(ANGRY, flag);
    }

    public boolean getHasRock() {
        return this.entityData.get(HAS_ROCK);
    }

    public void setHasRock(boolean flag) {
        this.entityData.set(HAS_ROCK, flag);
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (!this.level().isClientSide()) {
            setIsAngry(getTarget() != null);

            if (getIsAngry() && getTarget() != null && !getHasRock() && this.random.nextInt(30) == 0) {
                acquireTRock();
            }

            if (getHasRock()) {
                getNavigation().stop();
                attackWithTRock();
            }
        }
    }

    @Override
    public void die(DamageSource cause) {
        if (getHasRock() && this.tempRock != null) this.tempRock.transformToItem();
        super.die(cause);
    }

    protected void acquireTRock() {
        BlockState tRockState = MoCTools.destroyRandomBlockWithIBlockState(this, 3D);
        if (tRockState == null) {
            this.tCounter = 1;
            setHasRock(false);
            return;
        }

        //creates a dummy TRock on top of it
        MoCEntityThrowableRock tRock = MoCEntityThrowableRock.build(this.level(), this, this.getX(), this.getY() + 1.5D, this.getZ());
        this.level().addFreshEntity(tRock);
        tRock.setState(tRockState);
        tRock.setBehavior(1);
        this.tempRock = tRock;
        setHasRock(true);
    }

    /**
     *
     */
    protected void attackWithTRock() {
        this.tCounter++;

        if (this.tCounter < 50) {
            //maintains position of TRock above head
            this.tempRock.setPos(this.getX(), this.getY() + 1.0D, this.getZ());
        }

        if (this.tCounter >= 50) {
            //throws a newly spawned TRock and destroys the held TRock
            if (this.getTarget() != null && this.distanceTo(this.getTarget()) < 48F) {
                MoCTools.throwStone(this, this.getTarget(), this.tempRock.getState(), 10D, 0.25D);
            } else {
                this.tempRock.transformToItem();
            }

            this.tempRock.remove(RemovalReason.DISCARDED);
            setHasRock(false);
            this.tCounter = 0;
        }
    }

    /**
     * Stretches the model to that size
     */
    @Override
    public float getSizeFactor() {
        return 1.0F;
    }

    /**
     * Plays step sound at given x, y, z for the entity
     */  
    @Override
    protected void playStepSound(BlockPos p_180429_1_, Block p_180429_2_) {
        if (MoCreatures.proxy.legacyMiniGolemSounds) {
            this.playSound(MoCSoundEvents.ENTITY_GENERIC_STOMP, 1.0F, 1.0F);
        } else {
            this.playSound(SoundEvents.ENTITY_IRONGOLEM_STEP, 1.0F, 1.0F);
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY : MoCSoundEvents.ENTITY_MINI_GOLEM_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_HURT_LEGACY : MoCSoundEvents.ENTITY_MINI_GOLEM_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyMiniGolemSounds ? MoCSoundEvents.ENTITY_BIG_GOLEM_AMBIENT : null;
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.MINI_GOLEM;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.92F;
    }

    static class AIGolemAttack extends MeleeAttackGoal {
        public AIGolemAttack(MoCEntityMiniGolem golem, double speed, boolean useLongMemory) {
            super(golem, speed, useLongMemory);
        }

        @Override
        public boolean canContinueToUse() {
            float f = this.mob.getLightLevelDependentMagicValue();

            if (f >= 0.5F && this.mob.getRandom().nextInt(100) == 0) {
                this.mob.setTarget(null);
                return false;
            } else {
                return super.canContinueToUse();
            }
        }
    }

    static class AIGolemTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public AIGolemTarget(MoCEntityMiniGolem golem, Class<T> classTarget, boolean checkSight) {
            super(golem, classTarget, checkSight);
        }

        @Override
        public boolean canUse() {
            float f = this.mob.getLightLevelDependentMagicValue();
            return f < 0.5F && super.canUse();
        }
    }
}
