/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity.legacy;

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
public class MoCLegacyRenderShark extends MobRenderer<MoCEntityShark, MoCModelShark<MoCEntityShark>> {

    public MoCLegacyRenderShark(EntityRendererProvider.Context renderManagerIn, MoCModelShark modelbase, float f) {
        super(renderManagerIn, modelbase, f);
    }

    @SuppressWarnings("removal")
    @Override
    public void render(MoCEntityShark entityshark, float entityYaw, float partialTicks, PoseStack poseStack, MultiBufferSource buffer, int packedLightIn) {
        super.render(entityshark, entityYaw, partialTicks, poseStack, buffer, packedLightIn);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entityshark.getPetName().isEmpty());
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        if (entityshark.shouldRenderNameAndHealth()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entityshark.distanceTo(this.entityRenderDispatcher.camera.getEntity());
            if (f4 < 16F) {
                String s = entityshark.getPetName();
                float f5 = 0.1F;
                Font font = this.getFont();

                poseStack.pushPose();
                poseStack.translate(0.0F, f5, 0.0F);
                poseStack.mulPose(Axis.YP.rotationDegrees(-this.entityRenderDispatcher.camera.getYRot()));
                poseStack.scale(-f3, -f3, f3);

                byte byte0 = -50;
                if (flag1) {
                    if (!flag) {
                        byte0 += 8;
                    }
                    Matrix4f matrix4f = poseStack.last().pose();
                    float f9 = 40F * (entityshark.getHealth() / entityshark.getMaxHealth());
                    VertexConsumer vc = buffer.getBuffer(RenderType.textBackground());

                    // red: missing health
                    vc.vertex(matrix4f, -20F + f9, -10 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f, -20F + f9,  -6 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f,  20F,       -6 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f,  20F,      -10 + byte0, 0.0F).color(0.7F, 0.0F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();

                    // green: current health
                    vc.vertex(matrix4f, -20F,      -10 + byte0, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f, -20F,       -6 + byte0, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f, f9 - 20F,   -6 + byte0, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                    vc.vertex(matrix4f, f9 - 20F,  -10 + byte0, 0.0F).color(0.0F, 0.7F, 0.0F, 1.0F).uv2(packedLightIn).endVertex();
                }

                if (flag) {
                    Matrix4f matrix4f = poseStack.last().pose();
                    float textX = -font.width(s) / 2.0F;
                    // see-through pass, with the 25% black background drawn by the font itself
                    font.drawInBatch(s, textX, byte0, 0x20FFFFFF, false, matrix4f, buffer, Font.DisplayMode.SEE_THROUGH, 0x40000000, packedLightIn);
                    // normal pass on top
                    font.drawInBatch(s, textX, byte0, -1, false, matrix4f, buffer, Font.DisplayMode.NORMAL, 0, packedLightIn);
                }

                poseStack.popPose();
            }
        }
    }

    @Override
    protected void scale(MoCEntityShark entityshark, PoseStack poseStack, float partialTickTime) {
        stretch(entityshark, poseStack);
    }

    protected void stretch(MoCEntityShark entityshark, PoseStack poseStack) {
        poseStack.scale((entityshark.getMoCAge() * 0.01F) / 2, (entityshark.getMoCAge() * 0.01F) / 2, (entityshark.getMoCAge() * 0.01F) / 2);
    }

    @Override
    public ResourceLocation getTextureLocation(MoCEntityShark entityshark) {
        return entityshark.getTexture();
    }
}
