/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.LivingRenderer;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderMoC<T extends LivingEntity, M extends EntityModel<T>> extends LivingRenderer<T, M> {

    private float prevPitch;
    private float prevRoll;
    private float prevYaw;

    public MoCRenderMoC(M modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
    }

    @Override
    public void render(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        renderMoC(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    public void renderMoC(T entityIn, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        IMoCEntity entityMoC = (IMoCEntity) entityIn;
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entityMoC.getPetName().isEmpty());
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        if (entityMoC.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = (float) this.renderManager.squareDistanceTo((Entity) entityMoC);
            if (f5 < 16F) {
                String s = "";
                s = s + entityMoC.getPetName();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f7, (float) d2);
                matrixStackIn.glNormal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                matrixStackIn.scale(-f3, -f3, f3);
                Tessellator tessellator1 = Tessellator.getInstance();
                int yOff = entityMoC.nameYOffset();
                if (flag1) {

                    if (!flag) {
                        yOff += 8;
                    }
                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // might break SSP
                    float f8 = ((LivingEntity) entityMoC).getHealth();
                    float f9 = ((LivingEntity) entityMoC).getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getBuffer().pos(-20F + f11, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20F + f11, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -6 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -10 + yOff, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -6 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -10 + yOff, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();

                }
                if (flag) {
                    matrixStackIn.depthMask(false);
                    matrixStackIn.disableDepth();
                    matrixStackIn.enableBlend();
                    matrixStackIn.blendFunc(770, 771);

                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getBuffer().pos(-i - 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(-i - 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, 8 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, -1 + yOff, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();
                    fontrenderer.drawString(matrixStackIn, s, -fontrenderer.getStringWidth(s) / 2, yOff, 0x20ffffff);
                    matrixStackIn.enableDepth();
                    matrixStackIn.depthMask(true);
                    fontrenderer.drawString(matrixStackIn, s, -fontrenderer.getStringWidth(s) / 2, yOff, -1);
                    matrixStackIn.disableBlend();
                    matrixStackIn.color(1.0F, 1.0F, 1.0F, 1.0F);
                }
                matrixStackIn.pop();
            }
        }
        super.render(entityIn, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    protected void stretch(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.getSizeFactor();
        if (f != 0) {
            matrixStackIn.scale(f, f, f);
        }
    }

    @Override
    protected void preRenderCallback(T entityliving, MatrixStack matrixStackIn, float f) {
        IMoCEntity mocreature = (IMoCEntity) entityliving;
        super.preRenderCallback(entityliving, matrixStackIn, f);
        // Interpolation factor for smoother animations
        float interpolationFactor = 0.05F;
        // Interpolate pitch, roll, and yaw
        float interpolatedPitch = prevPitch + (mocreature.pitchRotationOffset() - prevPitch) * interpolationFactor;
        float interpolatedRoll = prevRoll + (mocreature.rollRotationOffset() - prevRoll) * interpolationFactor;
        float interpolatedYaw = prevYaw + (mocreature.yawRotationOffset() - prevYaw) * interpolationFactor;
        // Apply the interpolated transformations
        if (interpolatedPitch != 0) {
            matrixStackIn.rotate(interpolatedPitch, -1.0F, 0.0F, 0.0F);
        }
        if (interpolatedRoll != 0) {
            matrixStackIn.rotate(interpolatedRoll, 0F, 0F, -1.0F);
        }
        if (interpolatedYaw != 0) {
            matrixStackIn.rotate(interpolatedYaw, 0.0F, -1.0F, 0.0F);
        }
        // Save the current values for the next frame's interpolation
        prevPitch = interpolatedPitch;
        prevRoll = interpolatedRoll;
        prevYaw = interpolatedYaw;
        adjustPitch(mocreature, matrixStackIn);
        adjustRoll(mocreature, matrixStackIn);
        adjustYaw(mocreature, matrixStackIn);
        stretch(mocreature, matrixStackIn);
    }

    /**
     * Tilts the creature to the front / back
     */
    protected void adjustPitch(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.pitchRotationOffset();

        if (f != 0) {
            matrixStackIn.rotate(f, -1F, 0.0F, 0.0F);
        }
    }

    /**
     * Rolls creature
     */
    protected void adjustRoll(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.rollRotationOffset();

        if (f != 0) {
            matrixStackIn.rotate(f, 0F, 0F, -1F);
        }
    }

    protected void adjustYaw(IMoCEntity mocreature, MatrixStack matrixStackIn) {
        float f = mocreature.yawRotationOffset();
        if (f != 0) {
            matrixStackIn.rotate(f, 0.0F, -1.0F, 0.0F);
        }
    }

    /**
     * translates the model
     */
    protected void adjustOffsets(float xOffset, float yOffset, float zOffset, MatrixStack matrixStackIn) {
        matrixStackIn.translate(xOffset, yOffset, zOffset);
    }

    @Override
    public ResourceLocation getEntityTexture(LivingEntity entity) {
        return ((IMoCEntity) entity).getTexture();
    }
}
