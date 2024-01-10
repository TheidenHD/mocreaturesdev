/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityBird;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderBird extends MoCRenderMoC<MoCEntityBird> {

    public MoCRenderBird(ModelBase modelbase, float f) {
        super(modelbase, f);
    }

    @Override
    public ResourceLocation getEntityTexture(MobEntity par1Entity) {
        return ((MoCEntityBird) par1Entity).getTexture();
    }

    @Override
    public void render(MoCEntityBird entitybird, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitybird, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    protected float handleRotationFloat(MoCEntityBird entitybird, float f) {
        float f1 = entitybird.winge + ((entitybird.wingb - entitybird.winge) * f);
        float f2 = entitybird.wingd + ((entitybird.wingc - entitybird.wingd) * f);
        return (MathHelper.sin(f1) + 1.0F) * f2;
    }

    @Override
    protected void preRenderCallback(MoCEntityBird entitybird, float f) {
        if (!entitybird.world.isRemote && (entitybird.getRidingEntity() != null)) {
            matrixStackIn.translate(0.0F, 1.3F, 0.0F);
        }
    }
}
