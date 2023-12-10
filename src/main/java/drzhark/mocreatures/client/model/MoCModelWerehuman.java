/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import net.minecraft.client.model.ModelBiped;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelWerehuman extends ModelBiped {

    public MoCModelWerehuman() {
        //TODO 4.1 FIX
        super(0.0F, 0.0F, 64, 32);
    }
}
