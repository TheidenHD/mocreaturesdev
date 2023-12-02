/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Block;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EnumCreatureAttribute;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateClimber;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityScorpion extends MoCEntityMob {

    private static final DataParameter<Boolean> CLIMBING = EntityDataManager.createKey(MoCEntityScorpion.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> HAS_BABIES = EntityDataManager.createKey(MoCEntityScorpion.class, DataSerializers.BOOLEAN);
    public int mouthCounter;
    public int armCounter;
    public int getTypeMoC; // Baby Type
    private boolean isPoisoning;
    private int poisontimer;

    public MoCEntityScorpion(World world, int type) {
        super(world);
        setSize(1.4F, 0.9F);
        setAdult(true);
        setAge(20);
        this.poisontimer = 0;
        this.getTypeMoC = type;

        // Fire and Undead Scorpions won't spawn with babies
        if (!this.world.isRemote && getTypeMoC != 3 && getTypeMoC != 5) {
            setHasBabies(this.getIsAdult() && this.rand.nextInt(4) == 0);
        }
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(3, new EntityAILeapAtTarget(this, 0.4F));
        this.tasks.addTask(4, new MoCEntityScorpion.AIScorpionAttack(this));
        this.tasks.addTask(5, new EntityAIWanderAvoidWater(this, 0.8D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new MoCEntityScorpion.AIScorpionTarget<>(this, PlayerEntity.class));
        this.targetTasks.addTask(3, new MoCEntityScorpion.AIScorpionTarget<>(this, EntityIronGolem.class));
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("scorpion_dirt.png");
    }

    @Override
    protected PathNavigate createNavigator(World worldIn) {
        return new PathNavigateClimber(this, worldIn);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(CLIMBING, Boolean.FALSE);
        this.dataManager.register(HAS_BABIES, Boolean.FALSE);
    }

    public boolean getHasBabies() {
        return this.dataManager.get(HAS_BABIES);
    }

    public void setHasBabies(boolean flag) {
        this.dataManager.set(HAS_BABIES, flag);
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setPoisoning(boolean flag) {
        if (flag && !this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        this.isPoisoning = flag;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) // Sting Attack
        {
            setPoisoning(true);
        } else if (animationType == 1) // Attack Animation (Claws)
        {
            this.armCounter = 1;
            swingArm();
        } else if (animationType == 3) // Mouth Movement Animation
        {
            this.mouthCounter = 1;
        }
    }

    @Override
    public boolean isOnLadder() {
        return this.isBesideClimbableBlock();
    }

    public boolean isBesideClimbableBlock() {
        return this.dataManager.get(CLIMBING);
    }

    public void setBesideClimbableBlock(boolean climbing) {
        this.dataManager.set(CLIMBING, climbing);
    }

    @Override
    public boolean attackEntityAsMob(Entity entity) {
        // Claw Attack Sound
        if (this.poisontimer != 1) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_CLAW);
        }
        return super.attackEntityAsMob(entity);
    }

    @Override
    public void onLivingUpdate() {

        if (!this.onGround && (this.getRidingEntity() != null)) {
            this.rotationYaw = this.getRidingEntity().rotationYaw;
        }

        if (this.mouthCounter != 0 && this.mouthCounter++ > 50) {
            this.mouthCounter = 0;
        }

        if (this.armCounter != 0 && this.armCounter++ > 24) {
            this.armCounter = 0;
        }

        if (!this.world.isRemote && !this.isBeingRidden() && this.getIsAdult() && !this.getHasBabies() && this.rand.nextInt(100) == 0) {
            MoCTools.findMobRider(this);
            /*List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getEntityBoundingBox().grow(4D, 2D, 4D));
            for (int i = 0; i < list.size(); i++) {
                Entity entity = (Entity) list.get(i);
                if (!(entity instanceof MobEntity)) {
                    continue;
                }
                MobEntity entitymob = (MobEntity) entity;
                if (entitymob.getRidingEntity() == null
                        && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                    entitymob.mountEntity(this);
                    break;
                }
            }*/
        }

        if (getIsPoisoning()) {
            this.poisontimer++;
            if (this.poisontimer == 1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_SCORPION_STING);
            }
            if (this.poisontimer > 50) {
                this.poisontimer = 0;
                setPoisoning(false);
            }
        }

        super.onLivingUpdate();
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (entity != this && entity instanceof LivingEntity && this.shouldAttackPlayers()) {
                setAttackTarget((LivingEntity) entity);
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean entitiesToIgnore(Entity entity) {
        return ((super.entitiesToIgnore(entity)) || (this.getIsTamed() && entity instanceof MoCEntityPetScorpion && ((MoCEntityPetScorpion) entity).getIsTamed()));
    }

    public void swingArm() {
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 1), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
    }

    @Override
    public void tick() {
        super.tick();

        if (!this.world.isRemote) {
            this.setBesideClimbableBlock(this.collidedHorizontally);
        }
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);

        if (!this.world.isRemote && getIsAdult() && getHasBabies()) {
            int k = this.rand.nextInt(5);
            for (int i = 0; i < k; i++) {
                MoCEntityPetScorpion entityscorpy = new MoCEntityPetScorpion(this.world);
                entityscorpy.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                entityscorpy.setAdult(false);
                entityscorpy.setAge(20);
                entityscorpy.setTypeMoC(getTypeMoC);
                this.world.addEntity(entityscorpy);
                MoCTools.playCustomSound(entityscorpy, SoundEvents.ENTITY_SLIME_SQUISH);
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_SCORPION_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_SCORPION_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        // Mouth Movement Animation
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        return MoCSoundEvents.ENTITY_SCORPION_AMBIENT;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(SoundEvents.ENTITY_SPIDER_STEP, 0.15F, 1.5F);
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setHasBabies(nbttagcompound.getBoolean("Babies"));
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Babies", getHasBabies());
    }

    @Override
    public int getTalkInterval() {
        return 300;
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        return EnumCreatureAttribute.ARTHROPOD;
    }

    @Override
    public float getAdjustedYOffset() {
        return 30F;
    }

    @Override
    protected int getMaxAge() {
        return 120;
    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.15D;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.2D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
        passenger.rotationYaw = this.rotationYaw;
    }

    static class AIScorpionAttack extends EntityAIAttackMelee {
        public AIScorpionAttack(MoCEntityScorpion scorpion) {
            super(scorpion, 1.0D, true);
        }

        @Override
        public boolean shouldContinueExecuting() {
            float f = this.attacker.getBrightness();

            if (f >= 0.5F && this.attacker.getRNG().nextInt(100) == 0) {
                this.attacker.setAttackTarget(null);
                return false;
            } else {
                return super.shouldContinueExecuting();
            }
        }

        @Override
        protected double getAttackReachSqr(LivingEntity attackTarget) {
            return 4.0F + attackTarget.width;
        }
    }

    static class AIScorpionTarget<T extends LivingEntity> extends EntityAINearestAttackableTarget<T> {
        public AIScorpionTarget(MoCEntityScorpion scorpion, Class<T> classTarget) {
            super(scorpion, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
