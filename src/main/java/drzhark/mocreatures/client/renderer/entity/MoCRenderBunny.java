/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBunny extends MoCRenderMoC<MoCEntityBunny> {

    public MoCRenderBunny(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBunny entitybunny) {
        return entitybunny.getTexture();
    }

    @Override
    public void render(MoCEntityBunny entitybunny, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitybunny, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
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
