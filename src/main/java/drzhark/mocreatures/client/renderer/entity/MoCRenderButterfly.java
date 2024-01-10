/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityButterfly;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

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
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityButterfly entitybutterfly) {
        return entitybutterfly.getTexture();
    }
}
