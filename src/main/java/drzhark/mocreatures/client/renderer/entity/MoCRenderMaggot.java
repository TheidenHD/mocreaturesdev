package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelMaggot;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoCRenderMaggot extends MoCRenderMoC<MoCEntityMaggot, MoCModelMaggot> {

    public MoCRenderMaggot(EntityRendererManager manager, MoCModelMaggot modelbase) {
        super(manager, modelbase, 0.0F);
        this.addLayer(new MoCLayerMaggot(this));
    }

    private class MoCLayerMaggot extends LayerRenderer<MoCEntityMaggot, MoCModelMaggot> {
        public MoCLayerMaggot(IEntityRenderer<MoCEntityMaggot, MoCModelMaggot> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityMaggot entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderMain(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, limbSwing, limbSwingAmount);
        }
    }
}
