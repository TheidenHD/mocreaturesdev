/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityHellRat;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHellRat extends MoCRenderRat<MoCEntityHellRat> {

    public MoCRenderHellRat(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void stretch(MoCEntityHellRat entityhellrat) {
        float f = 1.3F;
        matrixStackIn.scale(f, f, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityHellRat entityhellrat) {
        return entityhellrat.getTexture();
    }
}
