/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGrasshopper extends MoCRenderMoC<MoCEntityGrasshopper> {

    public MoCRenderGrasshopper(ModelBase modelbase) {
        super(modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityGrasshopper entity, float par2) {
        rotateGrasshopper(entity);
    }

    protected void rotateGrasshopper(MoCEntityGrasshopper entity) {
        if (!entity.onGround) {
            if (entity.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entity.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                matrixStackIn.rotate((float) (entity.getMotion().getY() * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGrasshopper par1Entity) {
        return par1Entity.getTexture();
    }
}
