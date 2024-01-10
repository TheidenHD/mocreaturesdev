/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityRat;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderRat<T extends MoCEntityRat> extends MobRenderer<T> {

    public MoCRenderRat(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void render(T entityrat, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entityrat, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected float handleRotationFloat(T entityrat, float f) {
        stretch(entityrat);
        return entityrat.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(T entityrat, float f) {
        if (entityrat.isOnLadder()) {
            rotateAnimal(entityrat);
        }
    }

    protected void rotateAnimal(T entityrat) {
        matrixStackIn.rotate(90.0F, -1.0F, 0.0F, 0.0F);
        matrixStackIn.translate(0.0F, 0.4F, 0.0F);
    }

    protected void stretch(T entityrat) {
        float f = 0.8F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(T entityrat) {
        return entityrat.getTexture();
    }
}
