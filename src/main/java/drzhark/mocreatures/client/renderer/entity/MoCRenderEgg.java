/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderEgg extends RenderLiving<MoCEntityEgg> {

    public MoCRenderEgg(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityEgg entityegg, float f) {
        stretch(entityegg);
        super.preRenderCallback(entityegg, f);

    }

    protected void stretch(MoCEntityEgg entityegg) {
        float f = entityegg.getSize() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityEgg entityegg) {
        return entityegg.getTexture();
    }
}
