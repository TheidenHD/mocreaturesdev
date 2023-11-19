/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelLitterBox;
import drzhark.mocreatures.entity.item.MoCEntityLitterBox;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderLitterBox extends RenderLiving<MoCEntityLitterBox> {

    public MoCModelLitterBox litterbox;

    public MoCRenderLitterBox(MoCModelLitterBox modellitterbox, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modellitterbox, f);
        this.litterbox = modellitterbox;
    }

    @Override
    protected void preRenderCallback(MoCEntityLitterBox entitylitterbox, float f) {
        this.litterbox.usedlitter = entitylitterbox.getUsedLitter();
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityLitterBox entitylitterbox) {
        return entitylitterbox.getTexture();
    }
}
