/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends RenderLiving<MoCEntityWraith> {

    public MoCRenderWraith(ModelBiped modelbiped, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbiped, f);
    }

    @Override
    public void doRender(MoCEntityWraith wraith, double d, double d1, double d2, float f, float f1) {
        boolean flag = wraith.isGlowing();
        GlStateManager.pushMatrix();
        GlStateManager.enableBlend();
        if (!flag) {
            float transparency = 0.6F;
            GlStateManager.blendFunc(770, 771);
            GlStateManager.color(0.8F, 0.8F, 0.8F, transparency);
        } else {
            GlStateManager.blendFunc(770, 1);
        }
        super.doRender(wraith, d, d1, d2, f, f1);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityWraith wraith) {
        return wraith.getTexture();
    }
}
