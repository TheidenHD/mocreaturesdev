/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityGrasshopper;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;

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
            if (entity.motionY > 0.5D) {
                GlStateManager.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entity.motionY < -0.5D) {
                GlStateManager.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                GlStateManager.rotate((float) (entity.motionY * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityGrasshopper par1Entity) {
        return par1Entity.getTexture();
    }
}
