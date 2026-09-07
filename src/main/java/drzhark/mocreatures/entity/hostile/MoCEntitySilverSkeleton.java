/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.goal.MeleeAttackGoal;
import net.minecraft.world.entity.ai.goal.target.HurtByTargetGoal;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.network.PacketDistributor;

import javax.annotation.Nullable;

public class MoCEntitySilverSkeleton extends MoCEntityMob {

    public int attackCounterLeft;
    public int attackCounterRight;

    public MoCEntitySilverSkeleton(EntityType<? extends MoCEntitySilverSkeleton> type, Level world) {
        super(type, world);
        this.texture = "silver_skeleton.png";
        //setSize(0.6F, 2.125F);
        experienceValue = 5 + this.level().random.nextInt(4);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new SwimGoal(this));
        this.goalSelector.addGoal(2, new MoCEntitySilverSkeleton.AISkeletonAttack(this, 1.0D, true));
        this.goalSelector.addGoal(8, new LookAtGoal(this, Player.class, 8.0F));
        this.targetSelector.addGoal(1, new HurtByTargetGoal(this));
        this.targetSelector.addGoal(2, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, Player.class, false));
        this.targetSelector.addGoal(3, new MoCEntitySilverSkeleton.AISkeletonTarget<>(this, IronGolemEntity.class, false));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(35.0D);
        getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(14.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(7.0D);
    }

    @Override
    public void aiStep() {
        if (!this.level().isClientSide()) {
            setSprinting(this.getTarget() != null);
        }

        if (this.attackCounterLeft > 0 && ++this.attackCounterLeft > 10) {
            this.attackCounterLeft = 0;
        }

        if (this.attackCounterRight > 0 && ++this.attackCounterRight > 10) {
            this.attackCounterRight = 0;
        }

        super.aiStep();
    }

    @Override
    public void performAnimation(int animationType) {

        if (animationType == 1) //left arm
        {
            this.attackCounterLeft = 1;
        }
        if (animationType == 2) //right arm
        {
            this.attackCounterRight = 1;
        }
    }

    /**
     * Starts attack counters and synchronizes animations with clients
     */
    private void startAttackAnimation() {
        if (!this.level().isClientSide()) {
            boolean leftArmW = this.random.nextInt(2) == 0;

            if (leftArmW) {
                this.attackCounterLeft = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 1));
            } else {
                this.attackCounterRight = 1;
                MoCMessageHandler.INSTANCE.send(PacketDistributor.NEAR.with( () -> new PacketDistributor.TargetPoint(this.getX(), this.getY(), this.getZ(), 64, this.level().getDimensionKey())), new MoCMessageAnimation(this.getId(), 2));
            }
        }
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
    	MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SILVER_SKELETON_ATTACK.get());
        startAttackAnimation();
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public float getAIMoveSpeed() {
        if (isSprinting()) {
            return 0.425F;
        }
        
        return 0.25F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_DEATH.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_HURT.get();
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCSoundEvents.ENTITY_SILVER_SKELETON_AMBIENT.get();
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.UNDEAD;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block block) {
        this.playSound(MoCSoundEvents.ENTITY_SILVER_SKELETON_STEP.get(), 0.3F, 1.0F);
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.SILVER_SKELETON;
    }

    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return this.getBbHeight() * 0.905F;
    }

    static class AISkeletonAttack extends MeleeAttackGoal {
        public AISkeletonAttack(MoCEntitySilverSkeleton skeleton, double speed, boolean useLongMemory) {
            super(skeleton, speed, useLongMemory);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }
    }

    static class AISkeletonTarget<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {
        public AISkeletonTarget(MoCEntitySilverSkeleton skeleton, Class<T> classTarget, boolean checkSight) {
            super(skeleton, classTarget, checkSight);
        }

        @Override
        public boolean canUse() {
            float f = this.goalOwner.getBrightness();
            return f < 0.5F && super.canUse();
        }
    }
}
