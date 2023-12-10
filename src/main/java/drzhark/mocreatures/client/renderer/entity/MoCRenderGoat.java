/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelGoat;
import drzhark.mocreatures.entity.neutral.MoCEntityGoat;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderGoat extends RenderLiving<MoCEntityGoat> {

    private final MoCModelGoat tempGoat;
    float depth = 0F;

    public MoCRenderGoat(ModelBase modelbase, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelbase, f);
        this.tempGoat = (MoCModelGoat) modelbase;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityGoat entitygoat) {
        return entitygoat.getTexture();
    }

    @Override
    protected void preRenderCallback(MoCEntityGoat entitygoat, float f) {
        matrixStackIn.translate(0.0F, this.depth, 0.0F);
        stretch(entitygoat);
    }

    @Override
    public void doRender(MoCEntityGoat entitygoat, double d, double d1, double d2, float f, float f1) {
        this.tempGoat.typeInt = entitygoat.getTypeMoC();
        this.tempGoat.age = entitygoat.getAge() * 0.01F;
        this.tempGoat.bleat = entitygoat.getBleating();
        this.tempGoat.attacking = entitygoat.getAttacking();
        this.tempGoat.legMov = entitygoat.legMovement();
        this.tempGoat.earMov = entitygoat.earMovement();
        this.tempGoat.tailMov = entitygoat.tailMovement();
        this.tempGoat.eatMov = entitygoat.mouthMovement();
        super.doRender(entitygoat, d, d1, d2, f, f1);
        boolean flag = MoCreatures.proxy.getDisplayPetName() && !(entitygoat.getPetName()).isEmpty();
        boolean flag1 = MoCreatures.proxy.getDisplayPetHealth();
        if (entitygoat.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitygoat.getDistance(this.renderManager.renderViewEntity);
            if (f4 < 16F) {
                String s = "";
                s = s + entitygoat.getPetName();
                float f5 = 0.1F;
                FontRenderer fontrenderer = getFontRendererFromRenderManager();
                matrixStackIn.push();
                matrixStackIn.translate((float) d + 0.0F, (float) d1 + f5, (float) d2);
                matrixStackIn.glNormal3f(0.0F, 1.0F, 0.0F);
                matrixStackIn.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                matrixStackIn.scale(-f3, -f3, f3);

                Tessellator tessellator = Tessellator.getInstance();
                byte byte0 = (byte) (-15 + (-40 * entitygoat.getAge() * 0.01F));
                if (flag1) {

                    if (!flag) {
                        byte0 += 8;
                    }
                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    // might break SSP
                    float f6 = entitygoat.getHealth();
                    // max health is always 30 for dolphins, so we do not need to use a data watcher
                    float f7 = entitygoat.getMaxHealth();
                    float f8 = f6 / f7;
                    float f9 = 40F * f8;
                    tessellator.getBuffer().pos(-20F + f9, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(-20F + f9, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(20D, -6 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(20D, -10 + byte0, 0.0D).color(0.7F, 0.0F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(-20D, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(-20D, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(f9 - 20F, -6 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.getBuffer().pos(f9 - 20F, -10 + byte0, 0.0D).color(0.0F, 0.7F, 0.0F, 1.0F).endVertex();
                    tessellator.draw();

                }
                if (flag) {
                    matrixStackIn.depthMask(false);
                    matrixStackIn.disableDepth();
                    matrixStackIn.enableBlend();
                    matrixStackIn.blendFunc(770, 771);

                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_COLOR);
                    int i = fontrenderer.getStringWidth(s) / 2;
                    tessellator.getBuffer().pos(-i - 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuffer().pos(-i - 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuffer().pos(i + 1, 8 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.getBuffer().pos(i + 1, -1 + byte0, 0.0D).color(0.0F, 0.0F, 0.0F, 0.25F).endVertex();
                    tessellator.draw();

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

    protected void stretch(MoCEntityGoat entitygoat) {
        matrixStackIn.scale(entitygoat.getAge() * 0.01F, entitygoat.getAge() * 0.01F, entitygoat.getAge() * 0.01F);
    }
}
