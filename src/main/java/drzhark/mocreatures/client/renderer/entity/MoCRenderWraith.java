/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.hostile.MoCEntityWraith;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderWraith extends MobRenderer<MoCEntityWraith> {

    public MoCRenderWraith(ModelBiped modelbiped, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbiped, f);
    }

    @Override
    public void render(MoCEntityWraith wraith, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        boolean flag = wraith.isGlowing();
        matrixStackIn.push();
        matrixStackIn.enableBlend();
        if (!flag) {
            float transparency = 0.6F;
            matrixStackIn.blendFunc(770, 771);
            matrixStackIn.color(0.8F, 0.8F, 0.8F, transparency);
        } else {
            matrixStackIn.blendFunc(770, 1);
        }
        super.render(wraith, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        matrixStackIn.disableBlend();
        matrixStackIn.pop();
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityWraith wraith) {
        return wraith.getTexture();
    }
}
