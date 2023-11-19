/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.aquatic.MoCEntityFishy;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelFishy<T extends Entity> extends EntityModel<T> {

    public ModelRenderer Body;
    public ModelRenderer UpperFin;
    public ModelRenderer LowerFin;
    public ModelRenderer RightFin;
    public ModelRenderer LeftFin;
    public ModelRenderer Tail;

    public MoCModelFishy() {
        this.Body = new ModelRenderer(this, 0, 0);
        this.Body.addBox(0.0F, 0.0F, -3.5F, 1, 5, 5, 0.0F);
        this.Body.setRotationPoint(0.0F, 18F, -1F);
        this.Body.rotateAngleX = 0.7853981F;
        this.Tail = new ModelRenderer(this, 12, 0);
        this.Tail.addBox(0.0F, 0.0F, 0.0F, 1, 3, 3, 0.0F);
        this.Tail.setRotationPoint(0.0F, 20.5F, 3F);
        this.Tail.rotateAngleX = 0.7853981F;
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        float scale = 0.0865F;

        setRotationAngles(f, f1, f2, f3, f4, f5);
        MoCEntityFishy smallFish = (MoCEntityFishy) entity;
        float yOffset = smallFish.getAdjustedYOffset();
        float xOffset = smallFish.getAdjustedXOffset();
        float zOffset = smallFish.getAdjustedZOffset();
        GlStateManager.pushMatrix();
        GlStateManager.translate(xOffset, yOffset, zOffset);
        this.Body.render(scale);
        this.Tail.render(scale);
        GlStateManager.popMatrix();
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.Tail.rotateAngleY = MathHelper.cos(f * 0.6662F) * 1.4F * f1;
    }
}
