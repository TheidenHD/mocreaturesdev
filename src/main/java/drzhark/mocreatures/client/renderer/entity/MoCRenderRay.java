package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelRay;
import drzhark.mocreatures.entity.aquatic.MoCEntityRay;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MoCRenderRay extends MoCRenderMoC<MoCEntityRay, MoCModelRay> {

    public MoCRenderRay(EntityRendererManager manager, MoCModelRay entityModelIn) {
        super(manager, entityModelIn, 0.4F);
        this.addLayer(new MoCRenderRay.MoCLayerRay(this));
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityRay entity) {
        return entity.getTexture();
    }

    private class MoCLayerRay extends LayerRenderer<MoCEntityRay, MoCModelRay> {
        public MoCLayerRay(IEntityRenderer<MoCEntityRay, MoCModelRay> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityRay entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderMain(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, entitylivingbaseIn);
        }
    }
}
