/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityInsect;
import drzhark.mocreatures.entity.ai.EntityAIFleeFromEntityMoC;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityRoach extends MoCEntityInsect {

    public MoCEntityRoach(World world) {
        super(world);
        this.texture = "roach.png";
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.goalSelector.addGoal(0, new EntityAIFleeFromEntityMoC(this, entity -> !(entity instanceof MoCEntityCrab) && (entity.getHeight() > 0.3F || entity.getWidth() > 0.3F), 6.0F, 0.8D, 1.3D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.ARMOR, 1.0D);
    }

    @Override
    public void livingTick() {
        super.livingTick();
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return !(entity instanceof MoCEntityInsect) && super.entitiesToInclude(entity);
    }

    @Override
    public boolean isFlyer() {
        return true;
    }

    @Override
    public boolean isMyFavoriteFood(ItemStack stack) {
        return !stack.isEmpty() && stack.getItem() == Items.ROTTEN_FLESH;
    }

    @Override
    public float getAIMoveSpeed() {
        if (getIsFlying()) {
            return 0.1F;
        }
        return 0.25F;
    }

    @Override
    public boolean isNotScared() {
        return getIsFlying();
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.1F;
    }

    @Override
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCSoundEvents.ENTITY_GRASSHOPPER_HURT;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.ROACH;
    }
}
