/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.world.entity.SharedMonsterAttributes;
import net.minecraft.world.entity.ai.EntityAIPanic;
import net.minecraft.world.entity.ai.EntityAISwimming;
import net.minecraft.world.entity.ai.EntityAIWatchClosest;
import net.minecraft.world.entity.player.Player;
import net.minecraft.util.DamageSource;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;

public class MoCEntityDuck extends MoCEntityAnimal {

    public boolean field_70885_d = false;
    public float field_70886_e = 0.0F;
    public float destPos = 0.0F;
    public float field_70884_g;
    public float field_70888_h;
    public float field_70889_i = 1.0F;

    public MoCEntityDuck(EntityType<? extends MoCEntityDuck> type, Level world) {
        super(type, world);
        this.texture = "duck.png";
        //setSize(0.4F, 0.7F);
    }

    @Override
    protected void registerGoals() {
        this.goalSelector.addGoal(0, new FloatGoal(this));
        this.goalSelector.addGoal(1, new PanicGoal(this, 1.4D));
        this.goalSelector.addGoal(5, new EntityAIWanderMoC2(this, 1.0D));
        this.goalSelector.addGoal(6, new LookAtPlayerGoal(this, Player.class, 6.0F));
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityAnimal.createAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D);
    }

    // TODO: Add proper death sound event
    @Override
    protected SoundEvent getDeathSound() {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_HURT_LEGACY : MoCSoundEvents.ENTITY_DUCK_HURT;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_HURT_LEGACY : MoCSoundEvents.ENTITY_DUCK_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        return MoCreatures.proxy.legacyDuckSounds ? MoCSoundEvents.ENTITY_DUCK_AMBIENT_LEGACY : MoCSoundEvents.ENTITY_DUCK_AMBIENT;
    }

    // TODO: Add unique step sound
    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        this.playSound(MoCSoundEvents.ENTITY_DUCK_STEP, 0.15F, 1.0F);
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.DUCK;
    }

    @Override
    public void aiStep() {
        super.aiStep();
        this.field_70888_h = this.field_70886_e;
        this.field_70884_g = this.destPos;
        this.destPos = (float) (this.destPos + (this.onGround() ? -1 : 4) * 0.3D);

        if (this.destPos < 0.0F) {
            this.destPos = 0.0F;
        }

        if (this.destPos > 1.0F) {
            this.destPos = 1.0F;
        }

        if (!this.onGround() && this.field_70889_i < 1.0F) {
            this.field_70889_i = 1.0F;
        }

        this.field_70889_i = (float) (this.field_70889_i * 0.9D);

        if (!this.onGround() && this.getDeltaMovement().y < 0.0D) {
            this.setDeltaMovement(this.getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        this.field_70886_e += this.field_70889_i * 2.0F;
    }

    @Override
    public boolean causeFallDamage(float distance, float damageMultiplier, DamageSource source) {
        return false;
    }

    @Override
    protected float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return this.getBbHeight() * 0.945F;
    }
}
