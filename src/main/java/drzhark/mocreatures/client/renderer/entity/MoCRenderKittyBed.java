/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import com.mojang.blaze3d.matrix.MatrixStack;
import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.IRenderTypeBuffer;
import net.minecraft.client.renderer.entity.MobRenderer;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class MoCRenderKittyBed extends MobRenderer<MoCEntityKittyBed, MoCModelKittyBed<MoCEntityKittyBed>> {

    public static float[][] fleeceColorTable = {{1.0F, 1.0F, 1.0F}, {0.95F, 0.7F, 0.2F}, {0.9F, 0.5F, 0.85F}, {0.6F, 0.7F, 0.95F},
            {0.9F, 0.9F, 0.2F}, {0.5F, 0.8F, 0.1F}, {0.95F, 0.7F, 0.8F}, {0.3F, 0.3F, 0.3F}, {0.6F, 0.6F, 0.6F}, {0.3F, 0.6F, 0.7F},
            {0.7F, 0.4F, 0.9F}, {0.2F, 0.4F, 0.8F}, {0.5F, 0.4F, 0.3F}, {0.4F, 0.5F, 0.2F}, {0.8F, 0.3F, 0.3F}, {0.1F, 0.1F, 0.1F}};
    public MoCModelKittyBed kittybed;
    private int mycolor;

    public MoCRenderKittyBed(MoCModelKittyBed modelkittybed, MoCModelKittyBed2 modelkittybed2, float f) {
        super(MoCProxyClient.mc.getRenderManager(), modelkittybed, f);
        this.kittybed = modelkittybed;
        this.addLayer(new LayerMoCKittyBed(this));
    }

    @Override
    protected void preRenderCallback(MoCEntityKittyBed entitykittybed, float f) {
        this.mycolor = entitykittybed.getSheetColor();
        this.kittybed.hasMilk = entitykittybed.getHasMilk();
        this.kittybed.hasFood = entitykittybed.getHasFood();
        this.kittybed.pickedUp = entitykittybed.getPickedUp();
        this.kittybed.milklevel = entitykittybed.milkLevel;
    }

    @Override
    public ResourceLocation getEntityTexture(MoCEntityKittyBed entitykittybed) {
        return entitykittybed.getTexture();
    }

    private static class LayerMoCKittyBed implements LayerRenderer<MoCEntityKittyBed> {

        private final MoCRenderKittyBed mocRenderer;
        private final MoCModelKittyBed2 mocModel = new MoCModelKittyBed2();

        public LayerMoCKittyBed(MoCRenderKittyBed render) {
            this.mocRenderer = render;
        }

        public void render(MatrixStack matrixStackIn, IRenderTypeBuffer bufferIn, int packedLightIn, MoCEntityKittyBed entitykittybed, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch) {
            float f8 = 0.35F;
            int j = this.mocRenderer.mycolor;
            matrixStackIn.color(f8 * fleeceColorTable[j][0], f8 * fleeceColorTable[j][1], f8 * fleeceColorTable[j][2]);
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entitykittybed, limbSwing, limbSwingAmount, partialTicks);
            this.mocModel.render(entitykittybed, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
