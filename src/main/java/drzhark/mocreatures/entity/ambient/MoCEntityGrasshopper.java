/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityGrasshopper extends MoCEntityInsect {

    private int jumpCounter;
    private int soundCounter;

    public MoCEntityGrasshopper(EntityType<? extends MoCEntityGrasshopper> type, World world) {
        super(type, world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        return MoCEntityGrasshopper.registerAttributes().createMutableAttribute(Attributes.ARMOR, 1.0D);
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
            return MoCreatures.proxy.getModelTexture("grasshopper_bright_green.png");
        } else {
            return MoCreatures.proxy.getModelTexture("grasshopper_olive_green.png");
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();
        if (!this.world.isRemote) {
            if (getIsFlying() || !this.onGround) {
                PlayerEntity ep = this.world.getClosestPlayer(this, 5D);
                if (ep != null && --this.soundCounter == -1) {
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GRASSHOPPER_FLY.get());
                    this.soundCounter = 10;
                }
            }

            if (this.jumpCounter > 0 && ++this.jumpCounter > 30) {
                this.jumpCounter = 0;
            }
        }
    }

    @Override
    protected SoundEvent getAmbientSound() {
        if (world.isDaytime()) {
            // TODO: Add grasshopper daytime ambient sound
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_GRASSHOPPER_CHIRP.get() : null;
        } else {
            return world.rand.nextDouble() <= 0.1D ? MoCSoundEvents.ENTITY_GRASSHOPPER_CHIRP.get() : null;
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT.get();
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT.get();
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.GRASSHOPPER;
    }

    @Override
    public void tick() {
        super.tick();
        if (!this.world.isRemote) {
            if (onGround && ((getMotion().getX() > 0.05D) || (getMotion().getZ() > 0.05D) || (getMotion().getX() < -0.05D) || (getMotion().getZ() < -0.05D)))
                if (this.jumpCounter == 0) {
                    this.setMotion(this.getMotion().getX() * 5D, 0.45D, this.getMotion().getZ() * 5D);
                    this.jumpCounter = 1;
                }
        }
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.12F;
        }
        return 0.15F;
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.15F;
    }
}
