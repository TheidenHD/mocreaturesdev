/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity;

import drzhark.mocreatures.MoCTools;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.EntitySize;
import net.minecraft.entity.Pose;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.pathfinding.FlyingPathNavigator;
import net.minecraft.pathfinding.PathNavigator;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public abstract class MoCEntityInsect extends MoCEntityAmbient {

    private int climbCounter;

    protected MoCEntityInsect(World world) {
        super(world);
        setSize(0.4F, 0.3F);
        this.moveHelper = new EntityFlyHelper(this);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getAttributeMap().registerAttribute(Attributes.FLYING_SPEED);
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(4.0D);
        this.getEntityAttribute(Attributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getEntityAttribute(Attributes.FLYING_SPEED).setBaseValue(0.6D);
    }

    @Override
    protected PathNavigator createNavigator(World worldIn) {
        FlyingPathNavigator FlyingPathNavigator = new FlyingPathNavigator(this, worldIn);
        FlyingPathNavigator.setCanEnterDoors(true);
        FlyingPathNavigator.setCanFloat(true);
        return FlyingPathNavigator;
    }

    @Override
    protected void entityInit() {
        super.entityInit();
    }

    @Override
    protected void initEntityAI() {
        super.initEntityAI();
        this.goalSelector.addGoal(0, new EntityAIWanderAvoidWaterFlying(this, 0.8D));
    }

    @Override
    public float getStandingEyeHeight(Pose poseIn, EntitySize sizeIn) {
        return 0.2F;
    }

    @Override
    public boolean getIsFlying() {
        return (isOnAir() || !onGround) && (getMotion().getX() != 0 || getMotion().getY() != 0 || getMotion().getZ() != 0);
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (!this.onGround && this.getMotion().getY() < 0.0D) {
            this.getMotion().getY() *= 0.6D;
        }

        if (!this.world.isRemote) {
            if (isAttractedToLight() && this.rand.nextInt(50) == 0) {
                int[] ai = MoCTools.returnNearestBlockCoord(this, Blocks.TORCH, 8D);
                if (ai[0] > -1000) {
                    this.getNavigator().tryMoveToXYZ(ai[0], ai[1], ai[2], 1.0D);
                }
            }
        } else {
            if (this.climbCounter > 0 && ++this.climbCounter > 8) {
                this.climbCounter = 0;
            }
        }
    }

    /**
     * Is this insect attracted to light?
     */
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
        return (this.climbCounter != 0);
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
