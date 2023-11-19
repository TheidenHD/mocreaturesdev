/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityDeer extends MoCEntityTameableAnimal {

    private int readyToJumpTimer;

    public MoCEntityDeer(World world) {
        super(world);
        setAge(75);
        setSize(0.9F, 1.425F);
        setAdult(true);
        setTamed(false);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIFleeFromEntityMoC(this, entity -> !(entity instanceof MoCEntityDeer) && (entity.height > 0.8F || entity.width > 0.8F), 6.0F, this.getMyAISpeed(), this.getMyAISpeed() * 1.2D));
        this.tasks.addTask(2, new EntityAIPanic(this, this.getMyAISpeed() * 1.2D));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, getMyAISpeed()));
        this.tasks.addTask(5, new EntityAIWanderMoC2(this, getMyAISpeed()));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.35D);
    }

    @Override
    public void selectType() {
        if (getType() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 20) {
                setType(1);
            } else if (i <= 70) {
                setType(2);
            } else {
                setAdult(false);
                setType(3);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {

        switch (getType()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("deer_doe.png");
            case 3:
                setAdult(false);
                return MoCreatures.proxy.getModelTexture("deer_fawn.png");
            default:
                return MoCreatures.proxy.getModelTexture("deer_stag.png");
        }
    }

    @Override
    public void fall(float f, float f1) {
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityDeer) && super.entitiesToInclude(entity);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DEER_DEATH;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DEER_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!getIsAdult()) {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT_BABY;
        } else {
            return MoCSoundEvents.ENTITY_DEER_AMBIENT;
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.DEER;
    }

    public double getMyAISpeed() {
        /*if (getType() == 1) {
            return 1.1D;
        } else if (getType() == 2) {
            return 1.3D;
        }*/
        return 1.1D;
    }

    @Override
    public int getTalkInterval() {
        return 400;
    }

    @Override
    public int getMaxAge() {
        return 130;
    }

    @Override
    public void setAdult(boolean flag) {
        if (!this.world.isRemote) {
            setType(this.rand.nextInt(1));
        }
        super.setAdult(flag);
    }

    @Override
    public boolean getIsAdult() {
        return this.getType() != 3 && super.getIsAdult();
    }

    @Override
    public void onUpdate() {
        super.onUpdate();

        if (!this.world.isRemote) {

            if (this.onGround && --this.readyToJumpTimer <= 0) {
                if (MoCTools.getMyMovementSpeed(this) > 0.17F) {
                    float velX = (float) (0.5F * Math.cos((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    float velZ = (float) (0.5F * Math.sin((MoCTools.realAngle(this.rotationYaw - 90F)) / 57.29578F));
                    this.motionX -= velX;
                    this.motionZ -= velZ;
                    this.motionY = 0.5D;
                    this.readyToJumpTimer = this.rand.nextInt(10) + 20;
                }
            }
        }
    }

    @Override
    public float pitchRotationOffset() {
        if (!this.onGround && MoCTools.getMyMovementSpeed(this) > 0.08F) {
            if (this.motionY > 0.5D) {
                return 25F;
            }
            if (this.motionY < -0.5D) {
                return -25F;
            }
            return (float) (this.motionY * 70D);
        }
        return 0F;
    }

    @Override
    public float getSizeFactor() {
        if (getType() == 1) {
            return 1.6F;
        }
        if (getType() == 2) {
            return 1.3F;
        }
        return getAge() * 0.01F;
    }

    public float getEyeHeight() {
        return this.height * 0.945F;
    }
}
