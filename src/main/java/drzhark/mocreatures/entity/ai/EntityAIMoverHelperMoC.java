/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.pathfinding.NodeProcessor;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.math.MathHelper;

public class EntityAIMoverHelperMoC extends EntityMoveHelper {

    protected EntityMoveHelper.Action action = EntityMoveHelper.Action.WAIT;
    CreatureEntity theCreature;

    public EntityAIMoverHelperMoC(LivingEntity entityliving) {
        super(entityliving);
        this.theCreature = (CreatureEntity) entityliving;
    }

    public boolean isUpdating() {
        return this.action == EntityMoveHelper.Action.MOVE_TO;
    }

    /**
     * Sets the speed and location to move to
     */
    public void setMoveTo(double x, double y, double z, double speedIn) {
        this.getPosX() = x;
        this.getPosY() = y;
        this.getPosZ() = z;
        this.speed = speedIn;
        this.action = EntityMoveHelper.Action.MOVE_TO;
    }

    /*public void read(EntityMoveHelper that)
    {
        this.action = that.action;
        this.getPosX() = that.getPosX();
        this.getPosY() = that.getPosY();
        this.getPosZ() = that.getPosZ();
        this.speed = Math.max(that.speed, 1.0D);
        this.moveForward = that.moveForward;
        this.moveStrafe = that.moveStrafe;
    }*/

    public void strafe(float forward, float strafe) {
        this.action = EntityMoveHelper.Action.STRAFE;
        this.moveForward = forward;
        this.moveStrafe = strafe;
        this.speed = 0.25D;
    }

    public void onUpdateMoveOnGround() {
        if (this.action == EntityMoveHelper.Action.STRAFE) {
            float f = (float) this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue();
            float f1 = (float) this.speed * f;
            float f2 = this.moveForward;
            float f3 = this.moveStrafe;
            float f4 = MathHelper.sqrt(f2 * f2 + f3 * f3);

            if (f4 < 1.0F) {
                f4 = 1.0F;
            }

            f4 = f1 / f4;
            f2 = f2 * f4;
            f3 = f3 * f4;
            float f5 = MathHelper.sin(this.entity.rotationYaw * 0.017453292F);
            float f6 = MathHelper.cos(this.entity.rotationYaw * 0.017453292F);
            float f7 = f2 * f6 - f3 * f5;
            float f8 = f3 * f6 + f2 * f5;
            PathNavigate pathnavigate = this.entity.getNavigator();

            if (pathnavigate != null) {
                NodeProcessor nodeprocessor = pathnavigate.getNodeProcessor();

                if (nodeprocessor != null && nodeprocessor.getPathNodeType(this.entity.world, MathHelper.floor(this.entity.getPosX() + (double) f7), MathHelper.floor(this.entity.getPosY()), MathHelper.floor(this.entity.getPosZ() + (double) f8)) != PathNodeType.WALKABLE) {
                    this.moveForward = 1.0F;
                    this.moveStrafe = 0.0F;
                    f1 = f;
                }
            }

            this.entity.setAIMoveSpeed(f1);
            this.entity.setMoveForward(this.moveForward);
            this.entity.setMoveStrafing(this.moveStrafe);
            this.action = EntityMoveHelper.Action.WAIT;
        } else if (this.action == EntityMoveHelper.Action.MOVE_TO) {
            this.action = EntityMoveHelper.Action.WAIT;
            double d0 = this.getPosX() - this.entity.getPosX();
            double d1 = this.getPosZ() - this.entity.getPosZ();
            double d2 = this.getPosY() - this.entity.getPosY();
            double d3 = d0 * d0 + d2 * d2 + d1 * d1;

            if (d3 < 2.500000277905201E-7D) {
                this.entity.setMoveForward(0.0F);
                return;
            }

            float f9 = (float) (MathHelper.atan2(d1, d0) * (180D / Math.PI)) - 90.0F;
            this.entity.rotationYaw = this.limitAngle(this.entity.rotationYaw, f9, 20.0F);
            this.entity.setAIMoveSpeed((float) (this.speed * this.entity.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue()));

            if (d2 > (double) this.entity.stepHeight && d0 * d0 + d1 * d1 < (double) Math.max(1.0F, this.entity.width)) {
                this.entity.getJumpHelper().setJumping();
            }
        } else {
            this.entity.setMoveForward(0.0F);
        }
    }

    /**
     * Limits the given angle to a upper and lower limit.
     */
    protected float limitAngle(float p_75639_1_, float p_75639_2_, float p_75639_3_) {
        float f = MathHelper.wrapDegrees(p_75639_2_ - p_75639_1_);

        if (f > p_75639_3_) {
            f = p_75639_3_;
        }

        if (f < -p_75639_3_) {
            f = -p_75639_3_;
        }

        float f1 = p_75639_1_ + f;

        if (f1 < 0.0F) {
            f1 += 360.0F;
        } else if (f1 > 360.0F) {
            f1 -= 360.0F;
        }

        return f1;
    }

    @Override
    public void onUpdateMoveHelper() {
        boolean isFlyer = ((IMoCEntity) theCreature).isFlyer();
        boolean isSwimmer = this.theCreature.isInWater();
        float fLimitAngle = 90F;
        if (!isFlyer && !isSwimmer) {
            onUpdateMoveOnGround();
            return;
        }

        /*
         * Flying specific movement code
         */
        if (isFlyer && !theCreature.isBeingRidden()) {
            this.flyingMovementUpdate();
        }

        /*
         * Water movement code
         */
        if (isSwimmer) {
            this.swimmerMovementUpdate();
            fLimitAngle = 30F;
        }
        if (this.action == EntityMoveHelper.Action.MOVE_TO && !this.theCreature.getNavigator().noPath()) {
            double d0 = this.getPosX() - this.theCreature.getPosX();
            double d1 = this.getPosY() - this.theCreature.getPosY();
            double d2 = this.getPosZ() - this.theCreature.getPosZ();
            double d3 = d0 * d0 + d1 * d1 + d2 * d2;
            d3 = MathHelper.sqrt(d3);
            if (d3 < 0.5) {
                this.entity.setMoveForward(0.0F);
                this.theCreature.getNavigator().clearPath();
                return;
            }
            //System.out.println("distance to objective = " + d3 + "objective: X = " + this.getPosX() + ", Y = " + this.getPosY() + ", Z = " + this.getPosZ());
            d1 /= d3;
            float f = (float) (Math.atan2(d2, d0) * 180.0D / Math.PI) - 90.0F;
            this.theCreature.rotationYaw = this.limitAngle(this.theCreature.rotationYaw, f, fLimitAngle);
            this.theCreature.renderYawOffset = this.theCreature.rotationYaw;
            float f1 = (float) (this.speed * this.theCreature.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getAttributeValue());
            this.theCreature.setAIMoveSpeed(this.theCreature.getAIMoveSpeed() + (f1 - this.theCreature.getAIMoveSpeed()) * 0.125F);
            double d4 = Math.sin((double) (this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.01D;
            double d5 = Math.cos(this.theCreature.rotationYaw * (float) Math.PI / 180.0F);
            double d6 = Math.sin(this.theCreature.rotationYaw * (float) Math.PI / 180.0F);
            this.theCreature.getMotion().getX() += d4 * d5;
            this.theCreature.getMotion().getZ() += d4 * d6;
            //d4 = Math.sin((double)(this.theCreature.ticksExisted + this.theCreature.getEntityId()) * 0.75D) * 0.05D;
            this.theCreature.getMotion().getY() += d4 * (d6 + d5) * 0.25D;
            this.theCreature.getMotion().getY() += (double) this.theCreature.getAIMoveSpeed() * d1 * 1.5D;
        }
    }

    /**
     * Makes flying creatures reach the proper flying height
     */
    private void flyingMovementUpdate() {

        //Flying alone
        if (((IMoCEntity) theCreature).getIsFlying()) {
            int distY = MoCTools.distanceToFloor(this.theCreature);
            if (distY <= ((IMoCEntity) theCreature).minFlyingHeight()
                    && (this.theCreature.collidedHorizontally || this.theCreature.world.rand.nextInt(100) == 0)) {
                this.theCreature.getMotion().getY() += 0.02D;
            }
            if (distY > ((IMoCEntity) theCreature).maxFlyingHeight() || this.theCreature.world.rand.nextInt(150) == 0) {
                this.theCreature.getMotion().getY() -= 0.02D;
            }

        } else {
            if (this.theCreature.getMotion().getY() < 0) {
                this.theCreature.getMotion().getY() *= 0.6D;
            }
        }

    }

    /**
     * Makes creatures in the water float to the right depth
     */
    private void swimmerMovementUpdate() {
        if (theCreature.isBeingRidden()) {
            return;
        }

        double distToSurface = (MoCTools.waterSurfaceAtGivenEntity(theCreature) - theCreature.getPosY());
        if (distToSurface > ((IMoCEntity) theCreature).getDivingDepth()) {
            if (theCreature.getMotion().getY() < 0) {
                theCreature.getMotion().getY() = 0;
            }
            theCreature.getMotion().getY() += 0.001D;// 0.001
            theCreature.getMotion().getY() += (distToSurface * 0.01);
        }

        if (!theCreature.getNavigator().noPath() && theCreature.collidedHorizontally) {
            if (theCreature instanceof MoCEntityAquatic) {
                theCreature.getMotion().getY() = 0.05D;
            } else {
                ((IMoCEntity) theCreature).forceEntityJump();
            }
        }

        if ((this.theCreature.getAttackTarget() != null && ((this.theCreature.getAttackTarget().getPosY() < (this.getPosY() - 0.5D)) && this.theCreature
                .getDistance(this.theCreature.getAttackTarget()) < 10F))) {
            if (this.theCreature.getMotion().getY() < -0.1) {
                this.theCreature.getMotion().getY() = -0.1;
            }
        }
    }

    public enum Action {
        WAIT,
        MOVE_TO,
        STRAFE
    }
}
