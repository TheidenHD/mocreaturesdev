/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;
import net.minecraft.util.math.MathHelper;


public class MoCModelMouse<T extends Entity> extends EntityModel<T> {

    public ModelRenderer Head;
    public ModelRenderer EarR;
    public ModelRenderer EarL;
    public ModelRenderer WhiskerR;
    public ModelRenderer WhiskerL;
    public ModelRenderer Tail;
    public ModelRenderer FrontL;
    public ModelRenderer FrontR;
    public ModelRenderer RearL;
    public ModelRenderer RearR;
    public ModelRenderer BodyF;

    public MoCModelMouse() {
        this.Head = new ModelRenderer(this, 0, 0);
        this.Head.addBox(-1.5F, -1.0F, -6.0F, 3, 4, 6, 0.0F);
        this.Head.setRotationPoint(0.0F, 19F, -6.0F);
        this.EarR = new ModelRenderer(this, 16, 26);
        this.EarR.addBox(-3.5F, -3.0F, -1.0F, 3, 3, 1, 0.0F);
        this.EarR.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.EarL = new ModelRenderer(this, 24, 26);
        this.EarL.addBox(0.5F, -3.0F, -1.0F, 3, 3, 1, 0.0F);
        this.EarL.setRotationPoint(0.0F, 19F, -6.0F);
        this.WhiskerR = new ModelRenderer(this, 20, 20);
        this.WhiskerR.addBox(-4.5F, -1.0F, -7.0F, 3, 3, 1, 0.0F);
        this.WhiskerR.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.WhiskerL = new ModelRenderer(this, 24, 20);
        this.WhiskerL.addBox(1.5F, -1.0F, -6.0F, 3, 3, 1, 0.0F);
        this.WhiskerL.setRotationPoint(0.0F, 19.0F, -6.0F);
        this.Tail = new ModelRenderer(this, 56, 0);
        this.Tail.addBox(-0.5F, 0.0F, -1.0F, 1, 14, 1, 0.0F);
        this.Tail.setRotationPoint(0.0F, 20.0F, 6.0F);
        this.Tail.rotateAngleX = 1.570796F;
        this.FrontL = new ModelRenderer(this, 0, 18);
        this.FrontL.addBox(-2.0F, 0.0F, -3.0F, 2, 1, 4, 0.0F);
        this.FrontL.setRotationPoint(3.0F, 23.0F, -4.0F);
        this.FrontR = new ModelRenderer(this, 0, 18);
        this.FrontR.addBox(0.0F, 0.0F, -3.0F, 2, 1, 4, 0.0F);
        this.FrontR.setRotationPoint(-3.0F, 23.0F, -4.0F);
        this.RearL = new ModelRenderer(this, 0, 18);
        this.RearL.addBox(-2.0F, 0.0F, -4.0F, 2, 1, 4, 0.0F);
        this.RearL.setRotationPoint(3.0F, 23.0F, 5.0F);
        this.RearR = new ModelRenderer(this, 0, 18);
        this.RearR.addBox(0.0F, 0.0F, -4.0F, 2, 1, 4, 0.0F);
        this.RearR.setRotationPoint(-3.0F, 23.0F, 5.0F);
        this.BodyF = new ModelRenderer(this, 20, 0);
        this.BodyF.addBox(-3.0F, -3.0F, -7.0F, 6, 6, 12, 0.0F);
        this.BodyF.setRotationPoint(0.0F, 20.0F, 1.0F);
    }

    @Override
    public void render(MatrixStack matrixStackIn, IVertexBuilder bufferIn, int packedLightIn, int packedOverlayIn, float red, float green, float blue, float alpha) {
        super.render(entity, f, f1, f2, f3, f4, f5);
        setRotationAngles(f, f1, f2, f3, f4, f5);
        this.Head.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.EarR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.EarL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WhiskerR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.WhiskerL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.Tail.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.FrontR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearL.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.RearR.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
        this.BodyF.render(matrixStackIn, bufferIn, packedLightIn, packedOverlayIn, red, green, blue, alpha);
    }

    public void setRotationAngles(float f, float f1, float f2, float f3, float f4, float f5) {
        this.Head.rotateAngleX = -(f4 / 57.29578F);
        this.Head.rotateAngleY = f3 / 57.29578F;
        this.EarR.rotateAngleX = this.Head.rotateAngleX;
        this.EarR.rotateAngleY = this.Head.rotateAngleY;
        this.EarL.rotateAngleX = this.Head.rotateAngleX;
        this.EarL.rotateAngleY = this.Head.rotateAngleY;
        this.WhiskerR.rotateAngleX = this.Head.rotateAngleX;
        this.WhiskerR.rotateAngleY = this.Head.rotateAngleY;
        this.WhiskerL.rotateAngleX = this.Head.rotateAngleX;
        this.WhiskerL.rotateAngleY = this.Head.rotateAngleY;
        this.FrontL.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        this.RearL.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        this.RearR.rotateAngleX = MathHelper.cos(f * 0.6662F) * 0.6F * f1;
        this.FrontR.rotateAngleX = MathHelper.cos((f * 0.6662F) + 3.141593F) * 0.8F * f1;
        this.Tail.rotateAngleY = this.FrontL.rotateAngleX * 0.625F;
    }
}
