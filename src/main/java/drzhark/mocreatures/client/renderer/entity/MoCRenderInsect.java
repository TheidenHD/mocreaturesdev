/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.renderer.entity;

import drzhark.mocreatures.entity.MoCEntityInsect;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.matrixStackIn;

@OnlyIn(Dist.CLIENT)
public class MoCRenderInsect<T extends MoCEntityInsect> extends MoCRenderMoC<T> {

    public MoCRenderInsect(ModelBase modelbase) {
        super(modelbase, 0.0F);

    }

    @Override
    protected void preRenderCallback(T entityinsect, float par2) {
        if (entityinsect.climbing()) {
            rotateAnimal(entityinsect);
        }

        stretch(entityinsect);
    }

    protected void rotateAnimal(T entityinsect) {
        matrixStackIn.rotate(90F, -1F, 0.0F, 0.0F);
    }

    protected void stretch(T entityinsect) {
        float sizeFactor = entityinsect.getSizeFactor();
        matrixStackIn.scale(sizeFactor, sizeFactor, sizeFactor);
    }
}
