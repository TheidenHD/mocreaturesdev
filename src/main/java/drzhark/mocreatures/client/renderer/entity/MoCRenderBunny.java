/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC<MoCEntityBunny> {

    public MoCRenderBunny(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityBunny entitybunny) {
        return entitybunny.getTexture();
    }

    @Override
    public void doRender(MoCEntityBunny entitybunny, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitybunny, d, d1, d2, f, f1);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBunny entitybunny, float f) {
        if (!entitybunny.getIsAdult()) {
            stretch(entitybunny);
        }
        return entitybunny.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBunny entitybunny, float f) {
        rotBunny(entitybunny);
        adjustOffsets(entitybunny.getAdjustedXOffset(), entitybunny.getAdjustedYOffset(), entitybunny.getAdjustedZOffset());
    }

    protected void rotBunny(MoCEntityBunny entitybunny) {
        if (!entitybunny.onGround && (entitybunny.getRidingEntity() == null)) {
            if (entitybunny.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entitybunny.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                matrixStackIn.rotate((float) (entitybunny.getMotion().getY() * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    protected void stretch(MoCEntityBunny entitybunny) {
        float f = entitybunny.getAge() * 0.01F;
        matrixStackIn.scale(f, f, f);
    }
}
