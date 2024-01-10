/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFishy extends MobRenderer<MoCEntityFishy> {

    public MoCRenderFishy(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void render(MoCEntityFishy entityfishy, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityfishy.getTypeMoC() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityfishy.selectType();
        }
        super.render(entityfishy, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected void preRenderCallback(MoCEntityFishy entityfishy, float f) {
        matrixStackIn.translate(0.0F, 0.3F, 0.0F);
    }

    @Override
    protected float handleRotationFloat(MoCEntityFishy entityfishy, float f) {
        if (!entityfishy.getIsAdult()) {
            stretch(entityfishy);
        }
        return entityfishy.ticksExisted + f;
    }

    protected void stretch(MoCEntityFishy entityfishy) {
        matrixStackIn.scale(entityfishy.getAge() * 0.01F, entityfishy.getAge() * 0.01F, entityfishy.getAge() * 0.01F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFishy entityfishy) {
        return entityfishy.getTexture();
    }
}
