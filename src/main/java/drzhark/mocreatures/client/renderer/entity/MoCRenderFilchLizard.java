/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.passive.MoCEntityFilchLizard;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ResourceLocation;

// Courtesy of Daveyx0, permission given
public class MoCRenderFilchLizard extends RenderLiving<MoCEntityFilchLizard> {

    public MoCRenderFilchLizard(ModelBase modelBase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelBase, f);
        this.addLayer(new LayerHeldItemCustom(this));
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityFilchLizard entity) {
        return entity.getTexture();
    }

    private class LayerHeldItemCustom implements LayerRenderer<MoCEntityFilchLizard> {
        protected final RenderLivingBase<?> livingEntityRenderer;

        public LayerHeldItemCustom(RenderLivingBase<?> livingEntityRendererIn) {
            this.livingEntityRenderer = livingEntityRendererIn;
        }

        public void doRenderLayer(MoCEntityFilchLizard entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale) {
            ItemStack itemStack = entity.getHeldItemMainhand();
            if (!itemStack.isEmpty()) {
                matrixStackIn.push();
                if (this.livingEntityRenderer.getMainModel().isChild) {
                    matrixStackIn.translate(0.0F, 0.625F, 0.0F);
                    matrixStackIn.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                    matrixStackIn.scale(0.5F, 0.5F, 0.5F);
                }
                if (!entity.getHeldItemMainhand().isEmpty()) {
                    this.renderHeldItemLizard(entity, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
                }
                matrixStackIn.pop();
            }
        }

        public void renderHeldItemLizard(LivingEntity entity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
            if (!itemStack.isEmpty()) {
                matrixStackIn.push();
                if (entity.isSneaking()) {
                    matrixStackIn.translate(0.0F, 0.2F, 0.0F);
                }
                matrixStackIn.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                matrixStackIn.rotate(20.0F, 0.0F, 0.0F, 1.0F);
                matrixStackIn.translate(-0.55F, -1.0F, -0.05F);
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, transformType, true);
                matrixStackIn.pop();
            }
        }

        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
