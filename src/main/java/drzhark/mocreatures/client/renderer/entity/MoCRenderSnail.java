package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelSnail;
import drzhark.mocreatures.entity.ambient.MoCEntitySnail;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoCRenderSnail extends MoCRenderMoC<MoCEntitySnail, MoCModelSnail> {

    public MoCRenderSnail(EntityRendererManager manager, MoCModelSnail modelbase) {
        super(manager, modelbase, 0.0F);
        this.addLayer(new MoCLayerSnail(this));
    }

    private class MoCLayerSnail extends LayerRenderer<MoCEntitySnail, MoCModelSnail> {
        public MoCLayerSnail(IEntityRenderer<MoCEntitySnail, MoCModelSnail> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntitySnail entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntitySolid(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderMain(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
    }
}
