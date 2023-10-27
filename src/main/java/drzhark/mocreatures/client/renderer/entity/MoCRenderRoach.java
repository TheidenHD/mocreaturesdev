package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelRoach;
import drzhark.mocreatures.entity.ambient.MoCEntityRoach;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;

public class MoCRenderRoach extends MoCRenderInsect<MoCEntityRoach, MoCModelRoach>{
    public MoCRenderRoach(EntityRendererManager manager, MoCModelRoach modelbase) {
        super(manager, modelbase);
        this.addLayer(new MoCLayerRoach(this));
    }

    private class MoCLayerRoach extends LayerRenderer<MoCEntityRoach, MoCModelRoach> {
        public MoCLayerRoach(IEntityRenderer<MoCEntityRoach, MoCModelRoach> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityRoach entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderWings(entitylivingbaseIn, matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY);
        }
    }
}
