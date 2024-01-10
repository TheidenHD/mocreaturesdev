/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.ambient.MoCEntityCricket;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderCricket extends MoCRenderMoC<MoCEntityCricket> {

    public MoCRenderCricket(ModelBase modelbase) {
        super(modelbase, 0.0F);
    }

    @Override
    protected void preRenderCallback(MoCEntityCricket entitycricket, float par2) {
        rotateCricket(entitycricket);
    }

    protected void rotateCricket(MoCEntityCricket entitycricket) {
        if (!entitycricket.onGround) {
            if (entitycricket.getMotion().getY() > 0.5D) {
                matrixStackIn.rotate(35F, -1F, 0.0F, 0.0F);
            } else if (entitycricket.getMotion().getY() < -0.5D) {
                matrixStackIn.rotate(-35F, -1F, 0.0F, 0.0F);
            } else {
                matrixStackIn.rotate((float) (entitycricket.getMotion().getY() * 70D), -1F, 0.0F, 0.0F);
            }
        }
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityCricket par1Entity) {
        return par1Entity.getTexture();
    }
}
