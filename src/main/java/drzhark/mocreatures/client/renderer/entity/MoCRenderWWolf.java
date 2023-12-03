/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityWWolf;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWWolf extends RenderLiving<MobEntity> {

    public MoCRenderWWolf(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    protected ResourceLocation getEntityTexture(MobEntity par1Entity) {
        return ((MoCEntityWWolf) par1Entity).getTexture();
    }
}
