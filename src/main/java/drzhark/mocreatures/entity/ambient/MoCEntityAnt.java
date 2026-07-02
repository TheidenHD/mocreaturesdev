/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.util.Mth;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MoCEntityAnt extends MoCEntityAmbient {

    private static final EntityDataAccessor<Boolean> FOUND_FOOD = SynchedEntityData.defineId(MoCEntityAnt.class, EntityDataSerializers.BOOLEAN);

    public MoCEntityAnt(EntityType<? extends MoCEntityAnt> type, Level world) {
        super(type, world);
        //setSize(0.3F, 0.2F);
        this.texture = "ant.png";
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 1.2D));
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
        this.entityData.define(FOUND_FOOD, Boolean.FALSE);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityAmbient.registerAttributes().add(Attributes.MAX_HEALTH, 3.0D).add(Attributes.ARMOR, 1.0D).add(Attributes.MOVEMENT_SPEED, 0.28D);
    }

    public boolean getHasFood() {
        return this.entityData.get(FOUND_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.entityData.set(FOUND_FOOD, flag);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isInWater()) {
            this.motionY *= 0.6D;
        }

        if (!this.level().isRemote) {
            if (!getHasFood()) {
                ItemEntity entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem == null || entityitem.removed) {
                    return;
                }
                if (entityitem.getRidingEntity() == null) {
                    float f = entityitem.getDistance(this);
                    if (f > 1.0F) {
                        int i = Mth.floor(entityitem.getPosX());
                        int j = Mth.floor(entityitem.getPosY());
                        int k = Mth.floor(entityitem.getPosZ());
                        faceLocation(i, j, k, 30F);

                        getMyOwnPath(entityitem, f);
                        return;
                    }
                    if (f < 1.0F) {
                        exchangeItem(entityitem);
                        setHasFood(true);
                        return;
                    }
                }
            }

        }

        if (getHasFood()) {
            if (!this.isBeingRidden()) {
                ItemEntity entityitem = MoCTools.getClosestFood(this, 2D);
                if (entityitem != null && entityitem.getRidingEntity() == null) {
                    entityitem.startRiding(this);
                    return;

                }

                if (!this.isBeingRidden()) {
                    setHasFood(false);
                }
            }
        }
    }

    private void exchangeItem(ItemEntity entityitem) {
        ItemEntity cargo = new ItemEntity(this.level(), this.getPosX(), this.getPosY() + 0.2D, this.getPosZ(), entityitem.getItem());
        entityitem.remove();
        if (!this.level().isRemote) {
            this.level().addFreshEntity(cargo);
        }
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && MoCTools.isItemEdible(stack.getItem());
    }

    @Override
    public float getAIMoveSpeed() {
        if (getHasFood()) {
            return 0.1F;
        }
        return 0.15F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return null;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return null;
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.ANT;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.1F;
    }
    
    @Override
    public int getMaxSpawnedInChunk() {
        return 4;
    }
}
