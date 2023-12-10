/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFishy extends RenderLiving<MoCEntityFishy> {

    public MoCRenderFishy(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void doRender(MoCEntityFishy entityfishy, double d, double d1, double d2, float f, float f1) {
        if (entityfishy.getTypeMoC() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityfishy.selectType();
        }
        super.doRender(entityfishy, d, d1, d2, f, f1);
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
    protected ResourceLocation getEntityTexture(MoCEntityFishy entityfishy) {
        return entityfishy.getTexture();
    }
}
