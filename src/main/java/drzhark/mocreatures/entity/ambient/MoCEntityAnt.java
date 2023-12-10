/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityAnt extends MoCEntityAmbient {

    private static final DataParameter<Boolean> FOUND_FOOD = EntityDataManager.createKey(MoCEntityAnt.class, DataSerializers.BOOLEAN);

    public MoCEntityAnt(World world) {
        super(world);
        setSize(0.3F, 0.2F);
        this.texture = "ant.png";
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 1.2D));
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(FOUND_FOOD, Boolean.FALSE);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(3.0D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(1.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.28D);
    }

    public boolean getHasFood() {
        return this.dataManager.get(FOUND_FOOD);
    }

    public void setHasFood(boolean flag) {
        this.dataManager.set(FOUND_FOOD, flag);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.world.isRemote) {
            if (!getHasFood()) {
                ItemEntity entityitem = MoCTools.getClosestFood(this, 8D);
                if (entityitem == null || entityitem.isDead) {
                    return;
                }
                if (entityitem.getRidingEntity() == null) {
                    float f = entityitem.getDistance(this);
                    if (f > 1.0F) {
                        int i = MathHelper.floor(entityitem.getPosX());
                        int j = MathHelper.floor(entityitem.getPosY());
                        int k = MathHelper.floor(entityitem.getPosZ());
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
        ItemEntity cargo = new ItemEntity(this.world, this.getPosX(), this.getPosY() + 0.2D, this.getPosZ(), entityitem.getItem());
        entityitem.setDead();
        if (!this.world.isRemote) {
            this.world.addEntity(cargo);
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
    protected ResourceLocation getLootTable() {
        return MoCLootTables.ANT;
    }

    @Override
    public float getEyeHeight() {
        return 0.1F;
    }
}
