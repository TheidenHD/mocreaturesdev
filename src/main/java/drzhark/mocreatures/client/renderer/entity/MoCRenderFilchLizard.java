/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.passive.MoCEntityFilchLizard;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.block.model.ItemCameraTransforms;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
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
                GlStateManager.pushMatrix();
                if (this.livingEntityRenderer.getMainModel().isChild) {
                    GlStateManager.translate(0.0F, 0.625F, 0.0F);
                    GlStateManager.rotate(-20.0F, -1.0F, 0.0F, 0.0F);
                    GlStateManager.scale(0.5F, 0.5F, 0.5F);
                }
                if (!entity.getHeldItemMainhand().isEmpty()) {
                    this.renderHeldItemLizard(entity, itemStack, ItemCameraTransforms.TransformType.THIRD_PERSON_LEFT_HAND);
                }
                GlStateManager.popMatrix();
            }
        }

        public void renderHeldItemLizard(LivingEntity entity, ItemStack itemStack, ItemCameraTransforms.TransformType transformType) {
            if (!itemStack.isEmpty()) {
                GlStateManager.pushMatrix();
                if (entity.isSneaking()) {
                    GlStateManager.translate(0.0F, 0.2F, 0.0F);
                }
                GlStateManager.rotate(90.0F, 0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(180.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.rotate(20.0F, 0.0F, 0.0F, 1.0F);
                GlStateManager.translate(-0.55F, -1.0F, -0.05F);
                Minecraft.getMinecraft().getItemRenderer().renderItemSide(entity, itemStack, transformType, true);
                GlStateManager.popMatrix();
            }
        }

        public boolean shouldCombineTextures() {
            return false;
        }
    }
}
