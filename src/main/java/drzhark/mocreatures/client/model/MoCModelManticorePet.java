/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;

import net.minecraft.client.model.geom.ModelPart;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelManticorePet<T extends MoCEntityManticorePet> extends MoCModelBigCat<T> {

    public MoCModelManticorePet(ModelPart root) {
        super(root);
    }

    /**
     * In 1.20.1, override prepareMobModel(...) instead of setLivingAnimations(...).
     */
    @Override
    public void setLivingAnimations(T entityIn, float limbSwing, float limbSwingAmount, float partialTick) {
        this.isFlyer = entityIn.isFlyer();
        this.isSaddled = entityIn.getIsRideable();
        this.flapwings = true;
        this.floating = this.isFlyer && entityIn.isOnAir() && !entityIn.isOnGround();
        this.isRidden = entityIn.isBeingRidden();
        this.hasMane = true;
        this.hasSaberTeeth = true;
        this.onAir = entityIn.isOnAir();
        this.hasStinger = true;
        this.isSitting = manticorePet.getIsSitting();
        this.isMovingVertically = manticorePet.motionY != 0 && !manticorePet.onGround();
        this.hasChest = false;
        this.isTamed = false;
    }
    
    @Override
    public void setupAnim(
            T entity,
            float limbSwing,
            float limbSwingAmount,
            float ageInTicks,
            float netHeadYaw,
            float headPitch
    ) {
        super.setupAnim(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch);
        // No extra per-limb adjustments in this subclass.
    }
}
