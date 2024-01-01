/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.hunter.MoCEntityPetScorpion;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderPetScorpion extends MoCRenderMoC<MoCEntityPetScorpion> {

    public MoCRenderPetScorpion(MoCModelScorpion modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(MoCEntityPetScorpion entityscorpion, double d, double d1, double d2, float f, float f1) {
        super.doRender(entityscorpion, d, d1, d2, f, f1);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityPetScorpion entityscorpion) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityPetScorpion entityscorpion, float f) {
        /* TODO: Fix rider rotation
        if (entityscorpion.isOnLadder()) {
            rotateAnimal(entityscorpion);
        }
        */

        if (entityscorpion.getIsSitting()) {
            float factorY = 0.4F * (entityscorpion.getAge() / 100.0F);
            matrixStackIn.translate(0.0F, factorY, 0.0F);
        }

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion);
            if (entityscorpion.getRidingEntity() != null) {
                upsideDown(entityscorpion);
            }
        } else {
            adjustHeight(entityscorpion);
        }
    }

    protected void upsideDown(MoCEntityPetScorpion entityscorpion) {
        matrixStackIn.rotate(-90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(-1.5F, -0.5F, -2.5F);
    }

    protected void adjustHeight(MoCEntityPetScorpion entityscorpion) {
        matrixStackIn.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityPetScorpion entityscorpion) {
        matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityPetScorpion entityscorpion) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getAge() * 0.01F;
        }
        matrixStackIn.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityPetScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
