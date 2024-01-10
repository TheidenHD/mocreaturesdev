/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelGolem;
import drzhark.mocreatures.entity.hostile.MoCEntityGolem;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGolem extends MoCRenderMoC<MoCEntityGolem> {

    public MoCRenderGolem(ModelBase modelbase, float f) {
        super(modelbase, f);
        this.addLayer(new LayerMoCGolem(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityGolem par1Entity) {
        return par1Entity.getTexture();
    }

    private class LayerMoCGolem implements LayerRenderer<MoCEntityGolem> {

        private final MoCRenderGolem mocRenderer;
        private final MoCModelGolem mocModel = new MoCModelGolem();

        public LayerMoCGolem(MoCRenderGolem render) {
            this.mocRenderer = render;
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityGolem entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {

            ResourceLocation effectTexture = entity.getEffectTexture();
            if (effectTexture != null) {
                matrixStackIn.depthMask(false);
                float var4 = entity.ticksExisted + limbSwingAmount;
                bindTexture(effectTexture);
                matrixStackIn.matrixMode(5890);
                matrixStackIn.loadIdentity();
                float var5 = var4 * 0.01F;
                float var6 = var4 * 0.01F;
                matrixStackIn.translate(var5, var6, 0.0F);
                matrixStackIn.matrixMode(5888);
                matrixStackIn.enableBlend();
                float var7 = 0.5F;
                matrixStackIn.color(var7, var7, var7, 1.0F);

                matrixStackIn.blendFunc(1, 1);
                this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
                this.mocModel.setLivingAnimations(entity, limbSwing, limbSwingAmount, partialTicks);
                this.mocModel.render(entity, f, f1, f3, f4, f5, f6);
                matrixStackIn.matrixMode(5890);
                matrixStackIn.loadIdentity();
                matrixStackIn.matrixMode(5888);

                matrixStackIn.disableBlend();
                matrixStackIn.depthMask(true);
            }
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
