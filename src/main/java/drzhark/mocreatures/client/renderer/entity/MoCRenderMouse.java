/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelMouse;
import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse, MoCModelMouse<MoCEntityMouse>> {

    public MoCRenderMouse(MoCModelMouse modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void render(MoCEntityMouse entitymouse, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitymouse, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected float handleRotationFloat(MoCEntityMouse entitymouse, float f) {
        stretch(entitymouse);
        return entitymouse.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entitymouse, MatrixStack matrixStackIn, float partialTickTime) {
        // When mice are picked up
        if (entitymouse.upsideDown()) {
            upsideDown(matrixStackIn);
        }

        if (entitymouse.isOnLadder()) {
            rotateAnimal(matrixStackIn);
        }
    }

    protected void rotateAnimal(MatrixStack matrixStackIn) {
        matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(MatrixStack matrixStackIn) {
        float f = 0.6F;
        matrixStackIn.scale(f, f, f);
    }

    protected void upsideDown(MatrixStack matrixStackIn) {
        matrixStackIn.rotate(-90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(-0.55F, 0.0F, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityMouse entitymouse) {
        return entitymouse.getTexture();
    }
}
