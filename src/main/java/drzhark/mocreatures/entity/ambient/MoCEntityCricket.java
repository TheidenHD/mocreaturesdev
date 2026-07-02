/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.world.entity.EntitySize;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MoCEntityCricket extends MoCEntityAmbient {

    private int jumpCounter;
    
    public MoCEntityCricket(Level world) {
        super(world);
        setSize(0.4F, 0.3F);
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAIWanderMoC2(this, 1.2D));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            int i = this.rand.nextInt(100);
            if (i <= 50) {
                setTypeMoC(1);
            } else {
                setTypeMoC(2);
            }
        }
    }

    @Override
    public ResourceLocation getTexture() {
        if (getTypeMoC() == 1) {
            return MoCreatures.proxy.getModelTexture("cricket_light_brown.png");
        } else {
            return MoCreatures.proxy.getModelTexture("cricket_brown.png");
        }
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (this.isInWater()) {
            this.motionY *= 0.6D;
        }

        if (!this.level().isRemote) {
            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!world.isDaytime()) {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_AMBIENT.get() : null;
        } else {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_CHIRP.get() : null;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CRICKET_HURT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CRICKET_HURT.get();
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.CRICKET;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.level().isRemote) {
            if (onGround() && ((getMotion().getX() > 0.05D) || (getMotion().getZ() > 0.05D) || (getMotion().getX() < -0.05D) || (getMotion().getZ() < -0.05D)))
                if (this.jumpCounter == 0) {
                    this.setMotion(this.getMotion().getX() * 5D, 0.45D, this.getMotion().getZ() * 5D);
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.15F;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }
}
