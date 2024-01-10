/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelFirefly;
import drzhark.mocreatures.entity.ambient.MoCEntityFirefly;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderFirefly extends MoCRenderInsect<MoCEntityFirefly, MoCModelFirefly<MoCEntityFirefly>> {

    public MoCRenderFirefly(MoCModelFirefly modelbase) {
        super(modelbase);
        this.addLayer(new LayerMoCFirefly(this));
    }

    @Override
    protected void preRenderCallback(MoCEntityFirefly entityfirefly, float par2) {
        if (entityfirefly.getIsFlying()) {
            rotateFirefly(entityfirefly);
        } else if (entityfirefly.climbing()) {
            rotateAnimal(entityfirefly);
        }

    }

    protected void rotateFirefly(MoCEntityFirefly entityfirefly) {
        matrixStackIn.rotate(40F, -1F, 0.0F, 0.0F);

    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityFirefly entityfirefly) {
        return entityfirefly.getTexture();
    }

    private class LayerMoCFirefly extends LayerRenderer<MoCEntityFirefly, MoCModelFirefly<MoCEntityFirefly>> {
        private final MoCRenderFirefly mocRenderer;
        private final MoCModelFirefly mocModel = new MoCModelFirefly();

        public LayerMoCFirefly(MoCRenderFirefly p_i46112_1_) {
            this.mocRenderer = p_i46112_1_;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityFirefly entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            this.setTailBrightness(matrixStackIn, entitylivingbaseIn, partialTicks);
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entitylivingbaseIn, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(p_177162_1_, p_177162_2_, p_177162_3_, p_177162_5_, p_177162_6_, p_177162_7_, p_177162_8_);
        }

        protected void setTailBrightness(MatrixStack matrixStackIn, MoCEntityFirefly entityliving, float par3) {
            this.mocRenderer.bindTexture(MoCreatures.proxy.getModelTexture("firefly_glow.png"));
            float var4 = 1.0F;
            matrixStackIn.enableBlend();
            matrixStackIn.disableAlpha();
            matrixStackIn.blendFunc(1, 1);
            char var5 = 61680;
            int var6 = var5 % 65536;
            int var7 = var5 / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, var6, var7);
            matrixStackIn.color(1.0F, 1.0F, 1.0F, 1.0F);
            matrixStackIn.color(1.0F, 1.0F, 1.0F, var4);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
