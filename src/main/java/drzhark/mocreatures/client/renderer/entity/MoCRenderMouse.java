/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityMouse;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMouse extends MoCRenderMoC<MoCEntityMouse> {

    public MoCRenderMouse(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public void doRender(MoCEntityMouse entitymouse, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitymouse, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityMouse entitymouse, float f) {
        stretch(entitymouse);
        return entitymouse.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityMouse entitymouse, float f) {
        // When mice are picked up
        if (entitymouse.upsideDown()) {
            upsideDown(entitymouse);
        }

        if (entitymouse.isOnLadder()) {
            rotateAnimal(entitymouse);
        }
    }

    protected void rotateAnimal(MoCEntityMouse entitymouse) {
        matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(MoCEntityMouse entitymouse) {
        float f = 0.6F;
        matrixStackIn.scale(f, f, f);
    }

    protected void upsideDown(MoCEntityMouse entitymouse) {
        matrixStackIn.rotate(-90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(-0.55F, 0.0F, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityMouse entitymouse) {
        return entitymouse.getTexture();
    }
}
