/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ambient;

import drzhark.mocreatures.entity.MoCEntityAmbient;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityMaggot extends MoCEntityAmbient {

    public MoCEntityMaggot(World world) {
        super(world);
        setSize(0.2F, 0.2F);
        this.texture = "maggot.png";
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(1, new EntityAIWanderMoC2(this, 0.8D));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.1D);
    }

    @Override
    protected SoundEvent getDeathSound() {
        return SoundEvents.ENTITY_SILVERFISH_STEP;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return SoundEvents.ENTITY_SILVERFISH_STEP;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.MAGGOT;
    }

    @Override
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return !this.onGround && isOnLadder();
    }

    @Override
    public void jump() {
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.45F;
    }
}
