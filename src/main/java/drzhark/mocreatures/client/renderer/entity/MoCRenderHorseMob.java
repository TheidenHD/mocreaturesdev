/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelHorseMob;
import drzhark.mocreatures.entity.hostile.MoCEntityHorseMob;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderHorseMob extends MobRenderer<MoCEntityHorseMob> {

    public MoCRenderHorseMob(MoCModelHorseMob modelbase) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, 0.5F);

    }

    protected void adjustHeight(MoCEntityHorseMob entityhorsemob, float FHeight) {
        matrixStackIn.translate(0.0F, FHeight, 0.0F);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityHorseMob entityhorsemob) {
        return entityhorsemob.getTexture();
    }
}
