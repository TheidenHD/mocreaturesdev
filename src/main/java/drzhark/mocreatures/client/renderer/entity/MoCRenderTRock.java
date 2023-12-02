/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BlockRendererDispatcher;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.client.renderer.texture.TextureMap;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderTRock extends Render<Entity> {

    public MoCRenderTRock() {
        super(MoCProxyClient.mc.getRenderManager());
        this.shadowSize = 0.5F;
    }

    public void renderMyRock(MoCEntityThrowableRock entitytrock, double par2, double par4, double par6, float par8, float partialTicks) {
        BlockRendererDispatcher blockrendererdispatcher = Minecraft.getMinecraft().getBlockRendererDispatcher();
        matrixStackIn.push();
        //matrixStackIn.translate(-0.5F, -0.55F, 0.5F);
        matrixStackIn.translate(-0.5F, 0F, 0.5F);
        matrixStackIn.translate((float) par2, (float) par4, (float) par6);
        matrixStackIn.rotate(((100 - entitytrock.acceleration) / 10F) * 36F, 0F, -1F, 0.0F);
        int i = entitytrock.getBrightnessForRender();
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        matrixStackIn.color(1.0F, 1.0F, 1.0F, 1.0F);
        this.bindTexture(TextureMap.LOCATION_BLOCKS_TEXTURE);
        float lightLevel = entitytrock.getBrightness();
        blockrendererdispatcher.renderBlockBrightness(entitytrock.getState(), lightLevel);
        matrixStackIn.pop();
    }

    @Override
    public void doRender(Entity par1Entity, double par2, double par4, double par6, float par8, float par9) {
        this.renderMyRock((MoCEntityThrowableRock) par1Entity, par2, par4, par6, par8, par9);
    }

    protected ResourceLocation getMyTexture(MoCEntityThrowableRock trock) {
        return TextureMap.LOCATION_BLOCKS_TEXTURE;
    }

    @Override
    protected ResourceLocation getEntityTexture(Entity par1Entity) {
        return null;//this.getMyTexture((MoCEntityThrowableRock) par1Entity);
    }
}
