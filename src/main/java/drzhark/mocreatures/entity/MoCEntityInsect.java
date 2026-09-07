/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.Pose;
import net.minecraft.world.entity.ai.attributes.AttributeSupplier;
import net.minecraft.world.entity.ai.attributes.Attributes;
import net.minecraft.world.entity.ai.goal.WaterAvoidingRandomFlyingGoal;
import net.minecraft.world.entity.ai.navigation.PathNavigation;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public abstract class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;

    protected MoCEntityInsect(EntityType<? extends MoCEntityInsect> type, Level world) {
        super(type, world);
        //setSize(0.4F, 0.3F);
        this.moveController = new FlyingMovementController(this, 10, false);
    }

    public static AttributeSupplier.Builder registerAttributes() {
        return MoCEntityAmbient.registerAttributes().add(Attributes.MAX_HEALTH, 4.0D).add(Attributes.MOVEMENT_SPEED, 0.25D).add(Attributes.FLYING_SPEED, 0.6D);
    }

    @Override
    protected PathNavigation createNavigator(Level worldIn) {
        FlyingPathNavigator FlyingPathNavigator = new FlyingPathNavigator(this, worldIn);
        FlyingPathNavigator.setCanEnterDoors(true);
        FlyingPathNavigator.setCanSwim(true);
        return FlyingPathNavigator;
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
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.2F;
    }

    @Override
    public boolean getIsFlying() {
        return this.isOnAir() && !this.isOnLadder();
    }

    @Override
    public void aiStep() {
        super.aiStep();

        if (this.isInWater()) {
            this.motionY *= 0.6D;
        }

        if (!this.level().isClientSide()) {
            if (this.random.nextInt(50) == 0) {
                int[] ai = MoCTools.returnNearestBlockCoord(this, this.isAttractedToLight() ? Blocks.TORCH : Blocks.TALLGRASS, 8D);
                if (ai[0] > -1000) {
                    this.getNavigation().tryMoveToXYZ(ai[0], ai[1], ai[2], 1.0D);
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
    public boolean isOnLadder() {
        return this.collidedHorizontally;
    }

    public boolean climbing() {
        return this.climbCounter != 0;
    }

    @Override
    protected void updateFallState(double y, boolean onGroundIn, BlockState state, BlockPos pos) {
    }

    @Override
    public boolean doesEntityNotTriggerPressurePlate() {
        return true;
    }

    @Override
    public CreatureAttribute getCreatureAttribute() {
        return CreatureAttribute.ARTHROPOD;
    }
}
