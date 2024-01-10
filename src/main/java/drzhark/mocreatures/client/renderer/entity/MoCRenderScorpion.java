/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelScorpion;
import drzhark.mocreatures.entity.hostile.MoCEntityScorpion;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderScorpion extends MoCRenderMoC<MoCEntityScorpion> {

    public MoCRenderScorpion(MoCModelScorpion modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void render(MoCEntityScorpion entityscorpion, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityscorpion, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected float getDeathMaxRotation(MoCEntityScorpion entityscorpion) {
        return 180.0F;
    }

    @Override
    protected void preRenderCallback(MoCEntityScorpion entityscorpion, float f) {
        /* TODO: Fix rider rotation
        if (entityscorpion.isOnLadder()) {
            rotateAnimal(entityscorpion);
        }
        */

        if (!entityscorpion.getIsAdult()) {
            stretch(entityscorpion);
        } else {
            adjustHeight(entityscorpion);
        }
    }

    protected void adjustHeight(MoCEntityScorpion entityscorpion) {
        matrixStackIn.translate(0.0F, -0.1F, 0.0F);
    }

    protected void rotateAnimal(MoCEntityScorpion entityscorpion) {
        matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(0.0F, 1.0F, 0.0F);
    }

    protected void stretch(MoCEntityScorpion entityscorpion) {

        float f = 1.1F;
        if (!entityscorpion.getIsAdult()) {
            f = entityscorpion.getAge() * 0.01F;
        }
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityScorpion entityscorpion) {
        return entityscorpion.getTexture();
    }
}
