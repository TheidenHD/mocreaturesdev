/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.client.model.MoCModelKitty;
import drzhark.mocreatures.entity.neutral.MoCEntityKitty;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;

@OnlyIn(Dist.CLIENT)
public class MoCRenderKitty extends MoCRenderMoC<MoCEntityKitty> {

    public MoCModelKitty kitty;

    public MoCRenderKitty(ModelBase modelkitty, float f) {
        super(modelkitty, f);
        this.kitty = (MoCModelKitty) modelkitty;
    }

    @Override
    protected ResourceLocation getEntityTexture(MoCEntityKitty entitykitty) {
        return entitykitty.getTexture();
    }

    @Override
    public void doRender(MoCEntityKitty entitykitty, double d, double d1, double d2, float f, float f1) {
        super.doRender(entitykitty, d, d1, d2, f, f1);
        boolean displayPetIcons = MoCreatures.proxy.getDisplayPetIcons();
        if (entitykitty.getIsTamed()) {
            float f2 = 1.6F;
            float f3 = 0.01666667F * f2;
            float f4 = entitykitty.getDistance(this.renderManager.renderViewEntity);
            if (f4 < 12F) {
                float f5 = 0.2F;
                if (this.kitty.isSitting) {
                    f5 = 0.4F;
                }

                GlStateManager.pushMatrix();
                GlStateManager.translate((float) d + 0.0F, (float) d1 - f5, (float) d2);
                GlStateManager.glNormal3f(0.0F, 1.0F, 0.0F);
                GlStateManager.rotate(-this.renderManager.playerViewY, 0.0F, 1.0F, 0.0F);
                GlStateManager.scale(-f3, -f3, f3);
                GlStateManager.disableLighting();
                Tessellator tessellator = Tessellator.getInstance();

                if (displayPetIcons && entitykitty.getShowEmoteIcon()) {
                    this.bindTexture(entitykitty.getEmoteIcon());
                    int i = -90;
                    int k = 32;
                    int l = (k / 2) * -1;
                    float f9 = 0.0F;
                    float f11 = 1.0F / k;
                    float f12 = 1.0F / k;
                    tessellator.getBuffer().begin(7, DefaultVertexFormats.POSITION_TEX);
                    tessellator.getBuffer().pos(l, i + k, f9).tex(0.0D, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i + k, f9).tex(k * f11, k * f12).endVertex();
                    tessellator.getBuffer().pos(l + k, i, f9).tex(k * f11, 0.0D).endVertex();
                    tessellator.getBuffer().pos(l, i, f9).tex(0.0D, 0.0D).endVertex();
                    tessellator.draw();
                }

                GlStateManager.enableLighting();
                GlStateManager.popMatrix();
            }
        }
    }

    @Override
    protected float handleRotationFloat(MoCEntityKitty entitykitty, float f) {
        if (!entitykitty.getIsAdult()) {
            stretch(entitykitty);
        }
        return entitykitty.ticksExisted + f;
    }

    protected void onMaBack(MoCEntityKitty entitykitty) {
        GlStateManager.rotate(90F, 0.0F, 0.0F, -1F);
        if (!entitykitty.world.isRemote && (entitykitty.getRidingEntity() != null)) {
            GlStateManager.translate(-1.5F, 0.2F, -0.2F);
        } else {
            GlStateManager.translate(0.1F, 0.2F, -0.2F);
        }

    }

    protected void onTheSide(MoCEntityKitty entityliving) {
        GlStateManager.rotate(90F, 0.0F, 0.0F, -1F);
        GlStateManager.translate(0.2F, 0.0F, -0.2F);
    }

    @Override
    protected void preRenderCallback(MoCEntityKitty entitykitty, float f) {
        this.kitty.isSitting = entitykitty.getIsSitting();
        this.kitty.isSwinging = entitykitty.getIsSwinging();
        this.kitty.swingProgress = entitykitty.swingProgress;
        this.kitty.kittystate = entitykitty.getKittyState();
        if (entitykitty.getKittyState() == 20) {
            onTheSide(entitykitty);
        }
        if (entitykitty.climbingTree()) {
            rotateAnimal(entitykitty);
        }
        if (entitykitty.upsideDown()) {
            upsideDown(entitykitty);
        }
        if (entitykitty.onMaBack()) {
            onMaBack(entitykitty);
        }
    }

    protected void rotateAnimal(MoCEntityKitty entitykitty) {
        GlStateManager.rotate(90F, -1.0F, 0.0F, 0.0F);
        GlStateManager.translate(0.0F, 0.5F, 0.0F);
    }

    protected void stretch(MoCEntityKitty entitykitty) {
        GlStateManager.scale(entitykitty.getAge() * 0.01F, entitykitty.getAge() * 0.01F, entitykitty.getAge() * 0.01F);
    }

    protected void upsideDown(MoCEntityKitty entitykitty) {
        GlStateManager.rotate(180F, 0.0F, 0.0F, -1F);
        GlStateManager.translate(-0.35F, 0F, -0.55F);
    }
}
