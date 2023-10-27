package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import com.mojang.blaze3d.vertex.IVertexBuilder;
import drzhark.mocreatures.client.model.MoCModelMediumFish;
import drzhark.mocreatures.entity.aquatic.MoCEntityMediumFish;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.RenderType;
import net.minecraft.client.renderer.entity.EntityRendererManager;
import net.minecraft.client.renderer.entity.IEntityRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.texture.OverlayTexture;
import net.minecraft.util.ResourceLocation;

public class MoCRenderMediumFish extends MoCRenderMoC<MoCEntityMediumFish, MoCModelMediumFish> {

    public MoCRenderMediumFish(EntityRendererManager manager, MoCModelMediumFish entityModelIn) {
        super(manager, entityModelIn, 0.2F);
        this.addLayer(new MoCRenderMediumFish.MoCLayerMediumFish(this));
    }

    @Override
    public void render(MoCEntityMediumFish entityMediumFish, float entityYaw, float partialTicks, MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn) {
        if (entityMediumFish.getTypeMoC() == 0) { // && !MoCreatures.mc.isMultiplayerWorld())
            entityMediumFish.selectType();
        }
        super.render(entityMediumFish, entityYaw, partialTicks, matrixStackIn, bufferIn, packedLightIn);
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityMediumFish entity) {
        return entity.getTexture();
    }

    private class MoCLayerMediumFish extends LayerRenderer<MoCEntityMediumFish, MoCModelMediumFish> {
        public MoCLayerMediumFish(IEntityRenderer<MoCEntityMediumFish, MoCModelMediumFish> entityRendererIn) {
            super(entityRendererIn);
        }

        @Override
        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityMediumFish entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            IVertexBuilder ivertexbuilder = bufferIn.getBuffer(RenderType.getEntityTranslucent(entitylivingbaseIn.getTexture()));
            this.getEntityModel().renderMain(matrixStackIn, ivertexbuilder, packedLightIn, OverlayTexture.NO_OVERLAY, entitylivingbaseIn);
        }
    }
}
