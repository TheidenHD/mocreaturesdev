/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityManticore;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelManticore<T extends  MoCEntityManticore> extends MoCModelAbstractBigCat<T> {

    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.isFlyer = entityIn.isFlyer();
        this.isSaddled = entityIn.getIsRideable();
        this.flapwings = true;
        this.floating = this.isFlyer && entityIn.isOnAir() && !entityIn.isOnGround();
        this.poisoning = entityIn.swingingTail();
        this.isRidden = entityIn.isBeingRidden();
        this.hasMane = true;
        this.hasSaberTeeth = true;
        this.onAir = entityIn.isOnAir();
        this.hasStinger = true;
        this.isMovingVertically = entityIn.getMotion().getY() != 0 && !entityIn.isOnGround();
        this.hasChest = false;
        this.isTamed = false;
    }
}
