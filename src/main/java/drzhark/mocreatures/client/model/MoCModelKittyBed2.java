/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import net.minecraft.client.renderer.entity.model.EntityModel;
import net.minecraft.client.renderer.model.ModelRenderer;
import net.minecraft.entity.Entity;



public class MoCModelKittyBed2<T extends Entity> extends EntityModel<T> {

    ModelRenderer Sheet;

    public MoCModelKittyBed2() {
        float f = 0.0F;
        this.Sheet = new ModelRenderer(this, 0, 15);
        this.Sheet.addBox(0.0F, 0.0F, 0.0F, 16, 3, 14, f);
        this.Sheet.setRotationPoint(-8F, 21F, -7F);
    }

    @Override
    public void render(Entity entity, float f, float f1, float f2, float f3, float f4, float f5) {
        this.Sheet.render(f5);
    }
}
