package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelDragonfly;
import drzhark.mocreatures.entity.ambient.MoCEntityDragonfly;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoCRenderDragonfly extends MoCRenderInsect<MoCEntityDragonfly, MoCModelDragonfly>{
    public MoCRenderDragonfly(EntityRendererManager manager, MoCModelDragonfly modelbase) {
        super(manager, modelbase);
        this.addLayer(new MoCLayerDragonfly(this));
    }

    private class MoCLayerDragonfly extends LayerRenderer<MoCEntityDragonfly, MoCModelDragonfly> {
        public MoCLayerDragonfly(IEntityRenderer<MoCEntityDragonfly, MoCModelDragonfly> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityDragonfly entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderWings(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
    }
}
