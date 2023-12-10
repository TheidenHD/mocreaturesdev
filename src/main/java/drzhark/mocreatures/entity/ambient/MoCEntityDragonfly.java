/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.ILivingEntityData;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityDragonfly extends MoCEntityInsect {

    private int soundCount;

    public MoCEntityDragonfly(World world) {
        super(world);
        this.texture = "dragonflya.png";
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(1.0D);
    }

    @Override
    public ILivingEntityData onInitialSpawn(DifficultyInstance difficulty, ILivingEntityData par1EntityLivingData) {
        if (this.world.provider.getDimension() == MoCreatures.proxy.wyvernDimension) this.enablePersistence();
        return super.onInitialSpawn(difficulty, par1EntityLivingData);
    }

    @Override
    protected boolean canDespawn() {
        return this.world.provider.getDimension() != MoCreatures.proxy.wyvernDimension;
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            setTypeMoC(this.rand.nextInt(4) + 1);
        }
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("dragonfly_green.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("dragonfly_cyan.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("dragonfly_red.png");
            default:
                return MoCreatures.proxy.getModelTexture("dragonfly_blue.png");
        }
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            PlayerEntity ep = this.world.getClosestPlayer(this, 5D);
            if (ep != null && getIsFlying() && --this.soundCount == -1) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_DRAGONFLY_AMBIENT);
                this.soundCount = 20;
            }
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_DRAGONFLY_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_DRAGONFLY_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.DRAGONFLY;
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.25F;
        }
        return 0.12F;
    }
}
