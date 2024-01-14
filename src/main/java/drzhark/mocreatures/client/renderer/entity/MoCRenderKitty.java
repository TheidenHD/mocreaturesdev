/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.systems.RenderSystem;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.vector.Vector3f;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC<MoCEntityKitty, MoCModelKitty<MoCEntityKitty>> {

    public MoCModelKitty kitty;

    public MoCRenderKitty(EntityRendererManager renderManagerIn, MoCModelKitty modelkitty, float f) {
        super(renderManagerIn, modelkitty, f);
        this.kitty = modelkitty;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityKitty entitykitty) {
        return entitykitty.getTexture();
    }

    @Override
    public void render(MoCEntityKitty entitykitty, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitykitty, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        boolean displayPetIcons = MoCreatures.proxy.getDisplayPetIcons();
        if (entitykitty.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitykitty.getDistance(this.renderManager.info.getRenderViewEntity());
            if (f4 < 12F) {
                float f5 = 0.2F;
                if (this.kitty.isSitting) {
                    f5 = 0.4F;
                }

                matrixStackIn.push();
                matrixStackIn.translate(0.0F, f5, 0.0F);
                RenderSystem.normal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(Vector3f.YP.rotationDegrees(-this.renderManager.info.getYaw()));
                matrixStackIn.scale(-f3, -f3, f3);

                Tessellator tessellator = Tessellator.getInstance();

                if (displayPetIcons && entitykitty.getShowEmoteIcon()) {
                    Minecraft.getInstance().getTextureManager().bindTexture(entitykitty.getEmoteIcon());
                    int i = -90;
                    int k = 32;
                    int l = (k / 2) * -1;
                    float f9 = 0.0F;
                    float f11 = 1.0F / k;
                    float f12 = 1.0F / k;
                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
                    tessellator.getBuffer().pos(l, i + k, f9).tex(0.0F, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i + k, f9).tex(k * f11, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i, f9).tex(k * f11, 0.0F).endVertex();
                    tessellator.getBuffer().pos(l, i, f9).tex(0.0F, 0.0F).endVertex();
                    tessellator.draw();
                }


                matrixStackIn.pop();
            }
        }
    }

    protected void onMaBack(MoCEntityKitty entitykitty, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(90F));
        if (!entitykitty.world.isRemote && (entitykitty.getRidingEntity() != null)) {
            matrixStackIn.translate(-1.5F, 0.2F, -0.2F);
        } else {
            matrixStackIn.translate(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(MoCEntityKitty entityliving, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(90F));
        matrixStackIn.translate(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void preRenderCallback(MoCEntityKitty entitykitty, MatrixStack matrixStackIn, float f) {
        this.kitty.isSitting = entitykitty.getIsSitting();
        this.kitty.isSwinging = entitykitty.getIsSwinging();
        this.kitty.swingProgress = entitykitty.swingProgress;
        this.kitty.kittystate = entitykitty.getKittyState();
        if (!entitykitty.getIsAdult()) {
            stretch(entitykitty, matrixStackIn);
        }
        if (entitykitty.getKittyState() == 20) {
            onTheSide(entitykitty, matrixStackIn);
        }
        if (entitykitty.climbingTree()) {
            rotateAnimal(entitykitty, matrixStackIn);
        }
        if (entitykitty.upsideDown()) {
            upsideDown(entitykitty, matrixStackIn);
        }
        if (entitykitty.onMaBack()) {
            onMaBack(entitykitty, matrixStackIn);
        }
    }

    protected void rotateAnimal(MoCEntityKitty entitykitty, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.XN.rotationDegrees(90F));
        matrixStackIn.translate(0.0F, 0.5F, 0.0F);
    }

    protected void stretch(MoCEntityKitty entitykitty, MatrixStack matrixStackIn) {
        matrixStackIn.scale(entitykitty.getAge() * 0.01F, entitykitty.getAge() * 0.01F, entitykitty.getAge() * 0.01F);
    }

    protected void upsideDown(MoCEntityKitty entitykitty, MatrixStack matrixStackIn) {
        matrixStackIn.rotate(Vector3f.ZN.rotationDegrees(180F));
        matrixStackIn.translate(-0.35F, 0F, -0.55F);
    }
}
