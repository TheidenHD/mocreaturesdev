/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderButterfly extends MoCRenderInsect<MoCEntityButterfly> {

    public MoCRenderButterfly(ModelBase modelbase) {
        super(modelbase);

    }

    @Override
    protected void preRenderCallback(MoCEntityButterfly entitybutterfly, float par2) {
        if (entitybutterfly.isOnAir() || !entitybutterfly.onGround) {
            adjustHeight(entitybutterfly, entitybutterfly.tFloat());
        }
        if (entitybutterfly.climbing()) {
            rotateAnimal(entitybutterfly);
        }
        stretch(entitybutterfly);
    }

    protected void adjustHeight(MoCEntityButterfly entitybutterfly, float FHeight) {
        GlStateManager.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
