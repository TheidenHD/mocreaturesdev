/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity.legacy;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat1;
import drzhark.mocreatures.client.model.legacy.MoCLegacyModelBigCat2;
import drzhark.mocreatures.entity.hunter.MoCEntityBigCat;
import drzhark.mocreatures.entity.hunter.MoCEntityLion;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCLegacyRenderBigCat extends MobRenderer<MoCEntityBigCat> {

    public MoCLegacyModelBigCat2 bigcat1;

    public MoCLegacyRenderBigCat(MoCLegacyModelBigCat2 modelbigcat2, MoCLegacyModelBigCat1 modelbigcat1, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbigcat2, f);
        this.addLayer(new LayerMoCBigCat(this));
        this.bigcat1 = modelbigcat2;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityBigCat entitybigcat) {
        return entitybigcat.getTexture();
    }

    @Override
    public void render(MoCEntityBigCat entitybigcat, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        super.render(entitybigcat, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitybigcat.getPetName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();

        if (entitybigcat.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f5 = entitybigcat.getDistance(this.renderManager.renderViewEntity);
            if (f5 < 16F) {
                String s = "";
                s = s + entitybigcat.getPetName();
                float f7 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f7, (float) d2);
                matrixStackIn.glNormal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                matrixStackIn.scale(-f3, -f3, f3);

                Tessellator tessellator1 = Tessellator.getInstance();
                byte byte0 = -60;
                if (flag1) {

                    if (!flag) {
                        byte0 += 8;
                    }

                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // may break SSP, need to test
                    float f8;
                    f8 = entitybigcat.getHealth();

                    /*
                     * if(MoCreatures.mc.isMultiplayerWorld()) { f8 =
                     * entityliving.getHealth(); } else { f8 =
                     * entityliving.getHealth(); }
                     */
                    float f9 = entitybigcat.getMaxHealth();
                    float f10 = f8 / f9;
                    float f11 = 40F * f10;
                    tessellator1.getBuffer().pos(-20F + f11, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20F + f11, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.getBuffer().pos(f11 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator1.draw();

                }
                if (flag) {
                    matrixStackIn.depthMask(false);
                    matrixStackIn.disableDepth();
                    matrixStackIn.enableBlend();
                    matrixStackIn.blendFunc(770, 771);

                    tessellator1.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator1.getBuffer().pos(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.getBuffer().pos(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator1.draw();

                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, 0x20ffffff);
                    matrixStackIn.enableDepth();
                    matrixStackIn.depthMask(true);
                    fontrenderer.drawString(s, -fontrenderer.getStringWidth(s) / 2, byte0, -1);
                    matrixStackIn.disableBlend();
                    matrixStackIn.color(1.0F, 1.0F, 1.0F, 1.0F);
                }

                matrixStackIn.pop();
            }
        }

    }

    @Override
    protected float handleRotationFloat(MoCEntityBigCat entitybigcat, float f) {
        stretch(entitybigcat);
        return entitybigcat.ticksExisted + f;
    }

    @Override
    protected void preRenderCallback(MoCEntityBigCat entitybigcat, float f) {
        this.bigcat1.sitting = entitybigcat.getIsSitting();
        this.bigcat1.tamed = entitybigcat.getIsTamed();
    }

    protected void stretch(MoCEntityBigCat entitybigcat) {
        float f = entitybigcat.getAge() * 0.01F;
        if (entitybigcat.getIsAdult()) {
            f = 1.0F;
        }
        matrixStackIn.scale(f, f, f);
    }

    // Render mane
    private class LayerMoCBigCat implements LayerRenderer<MoCEntityBigCat> {

        private final MoCLegacyRenderBigCat mocRenderer;
        private final MoCLegacyModelBigCat1 mocModel = new MoCLegacyModelBigCat1();

        public LayerMoCBigCat(MoCLegacyRenderBigCat render) {
            this.mocRenderer = render;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityBigCat entitybigcat, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            if (entitybigcat instanceof MoCEntityLion && entitybigcat.hasMane()) {
                if (entitybigcat.getTypeMoC() == 7) {
                    bindTexture(MoCreatures.proxy.getModelTexture("big_cat_white_lion_legacy_layer.png"));
                } else {
                    bindTexture(MoCreatures.proxy.getModelTexture("big_cat_lion_legacy_layer_male.png"));
                }
            } else {
                bindTexture(MoCreatures.proxy.getModelTexture("big_cat_lion_legacy_layer_female.png"));
            }
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entitybigcat, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(entitybigcat, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
