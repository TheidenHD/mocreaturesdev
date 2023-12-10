/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hostile.MoCEntityManticore;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelManticore extends MoCModelBigCat {

    @Override
    public void updateAnimationModifiers(Entity entity) {
        MoCEntityManticore manticore = (MoCEntityManticore) entity;
        this.isFlyer = manticore.isFlyer();
        this.isSaddled = manticore.getIsRideable();
        this.flapwings = true;
        this.floating = this.isFlyer && manticore.isOnAir() && !manticore.onGround;
        this.poisoning = manticore.swingingTail();
        this.isRidden = manticore.isBeingRidden();
        this.hasMane = true;
        this.hasSaberTeeth = true;
        this.onAir = manticore.isOnAir();
        this.hasStinger = true;
        this.isMovingVertically = manticore.getMotion().getY() != 0 && !manticore.onGround;
        this.hasChest = false;
        this.isTamed = false;
    }
}
