/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelBear;
import drzhark.mocreatures.entity.hunter.MoCEntityBear;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBear extends MoCRenderMoC<MoCEntityBear> {

    public MoCRenderBear(MoCModelBear modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected void preRenderCallback(MoCEntityBear entitybear, float f) {
        stretch(entitybear);
        super.preRenderCallback(entitybear, f);

    }

    protected void stretch(MoCEntityBear entitybear) {
        float sizeFactor = entitybear.getAge() * 0.01F;
        if (entitybear.getIsAdult()) {
            sizeFactor = 1.0F;
        }
        sizeFactor *= entitybear.getBearSize();
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBear entitybear) {
        return entitybear.getTexture();
    }
}
