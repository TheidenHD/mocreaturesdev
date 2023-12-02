/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.client.model.MoCModelKittyBed;
import drzhark.mocreatures.client.model.MoCModelKittyBed2;
import drzhark.mocreatures.entity.item.MoCEntityKittyBed;
import drzhark.mocreatures.proxy.MoCProxyClient;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.client.renderer.matrixStackIn;
import net.minecraft.util.ResourceLocation;

@SuppressWarnings("unused")
@OnlyIn(Dist.CLIENT)
public class MoCRenderKittyBed extends RenderLiving<MoCEntityKittyBed> {

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
    protected ResourceLocation getEntityTexture(MoCEntityKittyBed entitykittybed) {
        return entitykittybed.getTexture();
    }

    private static class LayerMoCKittyBed implements LayerRenderer<MoCEntityKittyBed> {

        private final MoCRenderKittyBed mocRenderer;
        private final MoCModelKittyBed2 mocModel = new MoCModelKittyBed2();

        public LayerMoCKittyBed(MoCRenderKittyBed render) {
            this.mocRenderer = render;
        }

        public void doRenderLayer(MoCEntityKittyBed entitykittybed, float f, float f1, float f2, float f3, float f4, float f5, float f6) {
            float f8 = 0.35F;
            int j = this.mocRenderer.mycolor;
            matrixStackIn.color(f8 * fleeceColorTable[j][0], f8 * fleeceColorTable[j][1], f8 * fleeceColorTable[j][2]);
            this.mocModel.setModelAttributes(this.mocRenderer.getMainModel());
            this.mocModel.setLivingAnimations(entitykittybed, f, f1, f2);
            this.mocModel.render(entitykittybed, f, f1, f3, f4, f5, f6);
        }

        @Override
        public boolean shouldCombineTextures() {
            return true;
        }
    }
}
