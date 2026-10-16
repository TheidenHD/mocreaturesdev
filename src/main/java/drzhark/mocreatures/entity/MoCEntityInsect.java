/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.*;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.control.FlyingMoveControl;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.FlyingPathNavigation;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;

    protected MoCEntityInsect(EntityType<? extends MoCEntityInsect> type, Level world) {
        super(type, world);
        //setSize(0.4F, 0.3F);
        this.moveControl = new FlyingMoveControl(this, 10, false);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return Mob.createMobAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FLYING_SPEED, 0.6D);
    }

    @Override
    protected PathNavigation createNavigation(Level worldIn) {
        FlyingPathNavigation flyingpathnavigation = new FlyingPathNavigation(this, worldIn);
        flyingpathnavigation.setCanOpenDoors(false);
        flyingpathnavigation.setCanFloat(true);
        flyingpathnavigation.setCanPassDoors(true);
        return flyingpathnavigation;
    }

    @Override
    protected void defineSynchedData() {
        super.defineSynchedData();
    }

    @Override
    protected void registerGoals() {
        super.registerGoals();
        this.goalSelector.addGoal(0, new WaterAvoidingRandomFlyingGoal(this, 0.8D));
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntityDimensions sizeIn) {
        return 0.2F;
    }

    @Override
    public boolean getIsFlying() {
        return this.isOnAir() && !this.onClimbable();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isInWater()) {
            setDeltaMovement(getDeltaMovement().multiply(1.0D, 0.6D, 1.0D));
        }

        if (!this.level().isClientSide()) {
            if (this.random.nextInt(50) == 0) {
                int[] ai = MoCTools.returnNearestBlockCoord(this, this.isAttractedToLight() ? state -> state.is(Blocks.TORCH) || state.is(Blocks.WALL_TORCH)
                        : state -> state.is(Blocks.GRASS), 8D);
                if (ai[0] > -1000) {
                    this.getNavigation().moveTo(ai[0], ai[1], ai[2], 1.0D);
                }
            }
        } else {
            if (this.climbCounter > 0 && ++this.climbCounter > 8) {
                this.climbCounter = 0;
            }
        }
    }

    public boolean isAttractedToLight() {
        return false;
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType == 1) //climbing animation
        {
            this.climbCounter = 1;
        }
    }

    @Override
    public boolean onClimbable() {
        return this.horizontalCollision;
    }

    public boolean climbing() {
        return this.climbCounter != 0;
    }

    @Override
    protected void checkFallDamage(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean isIgnoringBlockTriggers() {
        return true;
    }

    @Override
    public MobType getMobType() {
        return MobType.ARTHROPOD;
    }
}
