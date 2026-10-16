/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.vertex.PoseStack;
import com.mojang.blaze3d.vertex.VertexConsumer;
import com.mojang.math.Axis;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelShark;
import drzhark.mocreatures.entity.aquatic.MoCEntityShark;
import net.minecraft.client.gui.Font;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererProvider;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.joml.Matrix4f;

@OnlyIn(Dist.CLIENT)
public class MoCRenderShark extends MobRenderer<MoCEntityShark, MoCModelShark<MoCEntityShark>> {

    public MoCRenderShark(EntityRendererProvider.Context context, MoCModelShark<MoCEntityShark> model, float shadowSize) {
        super(context, model, shadowSize);
    }

    @Override
    public void render(MoCEntityShark entityshark, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLight) {
        super.render(entityshark, entityYaw, partialTicks, poseStack, buffer, packedLight);

        boolean flag = MoCreatures.proxy.getDisplayPetName() && !entityshark.getPetName().isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();

        if (!entityshark.shouldRenderNameAndHealth()) {
            return;
        }
        // 16 blocks, squared
        if (this.entityRenderDispatcher.distanceToSqr(entityshark) >= 256.0D) {
            return;
        }

        float f3 = 0.01666667F * 1.6F;
        String s = entityshark.getPetName();
        Font font = this.getFont();

        poseStack.pushPose();
        poseStack.translate(0.0F, 0.1F, 0.0F);
        poseStack.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
        poseStack.scale(-f3, -f3, f3);
        Matrix4f matrix4f = poseStack.last().pose();

        int byte0 = -50;

        if (flag1) {
            if (!flag) {
                byte0 += 8;
            }
            float f9 = 40F * (entityshark.getHealth() / entityshark.getMaxHealth());
            VertexConsumer vc = buffer.getBuffer(RenderType.textBackground());

            // red: missing health
            quad(vc, matrix4f, -20F + f9, -10 + byte0, 20F, -6 + byte0, 0.7F, 0.0F, 0.0F, 1.0F, packedLight);
            // green: current health
            quad(vc, matrix4f, -20F, -10 + byte0, f9 - 20F, -6 + byte0, 0.0F, 0.7F, 0.0F, 1.0F, packedLight);
        }

        if (flag) {
            float textX = -font.width(s) / 2.0F;
            // see-through pass with the 25% black background
            font.drawInBatch(s, textX, byte0, 0x20FFFFFF, false, matrix4f, buffer, Font.DisplayMode.SEE_THROUGH, 0x40000000, packedLight);
            // normal pass on top
            font.drawInBatch(s, textX, byte0, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLight);
        }

        poseStack.popPose();
    }

    private static void quad(VertexConsumer vc, Matrix4f m, float x0, float y0, float x1, float y1,
                             float r, float g, float b, float a, int light) {
        vc.vertex(m, x0, y0, 0.0F).color(r, g, b, a).uv2(light).endVertex();
        vc.vertex(m, x0, y1, 0.0F).color(r, g, b, a).uv2(light).endVertex();
        vc.vertex(m, x1, y1, 0.0F).color(r, g, b, a).uv2(light).endVertex();
        vc.vertex(m, x1, y0, 0.0F).color(r, g, b, a).uv2(light).endVertex();
    }

    @Override
    protected void scale(MoCEntityShark entityshark, PoseStack poseStack, float partialTickTime) {
        stretch(entityshark, poseStack);
    }

    protected void stretch(MoCEntityShark entityshark, PoseStack poseStack) {
        float f = (entityshark.getMoCAge() * 0.01F) / 2;
        poseStack.scale(f, f, f);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityShark entityshark) {
        return entityshark.getTexture();
    }
}