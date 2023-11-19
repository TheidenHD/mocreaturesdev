/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.monster.EntityIronGolem;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;

public class MoCEntityManticore extends MoCEntityMob {

    public int mouthCounter;
    public int tailCounter;
    public int wingFlapCounter;
    private boolean isPoisoning;
    private int poisontimer;

    public MoCEntityManticore(World world) {
        super(world);
        setSize(1.35F, 1.45F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(2, new MoCEntityManticore.AIManticoreAttack(this));
        this.tasks.addTask(6, new EntityAILookIdle(this));
        this.tasks.addTask(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, false));
        this.targetTasks.addTask(2, new MoCEntityManticore.AIManticoreTarget<>(this, PlayerEntity.class));
        this.targetTasks.addTask(3, new MoCEntityManticore.AIManticoreTarget<>(this, EntityIronGolem.class));
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    // Flying Speed
    @Override
    public float getMoveSpeed() {
        return 0.9F;
    }

    @Override
    public void fall(float f, float f1) {
    }

    public boolean getIsRideable() {
        return false;
    }

    @Override
    public int maxFlyingHeight() {
        return 10;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (-0.1D);
        double newPosX = this.posX + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.posZ - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.posY + getMountedYOffset() + passenger.getYOffset(), newPosZ);
        passenger.rotationYaw = this.rotationYaw;
    }

    @Override
    public double getMountedYOffset() {
        return (this.height * 0.75D) - 0.1D;
    }

    protected void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    public boolean isOnAir() {
        return this.world.isAirBlock(new BlockPos(MathHelper.floor(this.posX), MathHelper.floor(this.posY - 0.2D), MathHelper
                .floor(this.posZ)));
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        // TODO: Fix broken mouth movement
        if (this.world.isRemote) {
            if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
                this.mouthCounter = 0;
            }

            if (this.rand.nextInt(250) == 0) {
                moveTail();
            }

            if (this.tailCounter > 0 && ++this.tailCounter > 10 && this.rand.nextInt(15) == 0) {
                this.tailCounter = 0;
            }
        }

        if (isFlyer()) {
            if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
                this.wingFlapCounter = 0;
            }

            // TODO: Particles while flying?
            /*if (this.wingFlapCounter != 0 && this.wingFlapCounter % 5 == 0 && this.world.isRemote) {
                MoCreatures.proxy.StarFX(this);
            }*/

            if (!this.world.isRemote && this.wingFlapCounter == 5) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            }
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

        if (!this.world.isRemote) {
            if (!this.world.isRemote && isFlyer() && isOnAir()) {
                float myFlyingSpeed = MoCTools.getMyMovementSpeed(this);
                int wingFlapFreq = (int) (25 - (myFlyingSpeed * 10));
                if (!this.isBeingRidden() || wingFlapFreq < 5) {
                    wingFlapFreq = 5;
                }

                if (this.rand.nextInt(wingFlapFreq) == 0) {
                    wingFlap();
                }
            }
        }

        if (!this.isBeingRidden() && this.rand.nextInt(200) == 0) {
            MoCTools.findMobRider(this);
        }
    }

    @Override
    public void makeEntityJump() {
        wingFlap();
        super.makeEntityJump();
    }

    public void wingFlap() {
        if (this.world.isRemote) {
            return;
        }

        if (this.isFlyer() && this.wingFlapCounter == 0) {
            this.wingFlapCounter = 1;
            if (!this.world.isRemote) {
                MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 3),
                        new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
            }
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 0) // Sting Attack
        {
            setPoisoning(true);
        } else if (animationType == 3) // Flapping Wings
        {
            this.wingFlapCounter = 1;
        }
    }

    public boolean getIsPoisoning() {
        return this.isPoisoning;
    }

    public void setPoisoning(boolean flag) {
        if (flag && !this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), 0),
                    new TargetPoint(this.world.provider.getDimensionType().getId(), this.posX, this.posY, this.posZ, 64));
        }

        this.isPoisoning = flag;
    }

    public boolean swingingTail() {
        return getIsPoisoning() && this.poisontimer < 15;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth(); // Mouth Animation
        return MoCSoundEvents.ENTITY_LION_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth(); // Mouth Animation
        return MoCSoundEvents.ENTITY_LION_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth(); // Mouth Animation
        return MoCSoundEvents.ENTITY_LION_AMBIENT;
    }

    @Override
    public float getSizeFactor() {
        return 1.4F;
    }

    public float getEyeHeight() {
        return this.height * 0.945F;
    }

    static class AIManticoreAttack extends EntityAIAttackMelee {
        public AIManticoreAttack(MoCEntityManticore manticore) {
            super(manticore, 1.0D, true);
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
        protected double getAttackReachSqr(EntityLivingBase attackTarget) {
            return 4.0F + attackTarget.width;
        }
    }

    static class AIManticoreTarget<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
        public AIManticoreTarget(MoCEntityManticore manticore, Class<T> classTarget) {
            super(manticore, classTarget, true);
        }

        @Override
        public boolean shouldExecute() {
            float f = this.taskOwner.getBrightness();
            return f < 0.5F && super.shouldExecute();
        }
    }
}
