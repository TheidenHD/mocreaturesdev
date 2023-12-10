/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCricket extends MoCEntityAmbient {

    private int jumpCounter;
    private int soundCounter;

    public MoCEntityCricket(World world) {
        super(world);
        setSize(0.4F, 0.3F);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(1.0D);
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
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (!world.isDaytime()) {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_AMBIENT : null;
        } else {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_CRICKET_CHIRP : null;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_CRICKET_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_CRICKET_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CRICKET;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (onGround && ((getMotion().getX() > 0.05D) || (getMotion().getZ() > 0.05D) || (getMotion().getX() < -0.05D) || (getMotion().getZ() < -0.05D)))
                if (this.jumpCounter == 0) {
                    this.getMotion().getY() = 0.45D;
                    this.getMotion().getX() *= 5D;
                    this.getMotion().getZ() *= 5D;
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
    public float getEyeHeight() {
        return 0.15F;
    }
}
