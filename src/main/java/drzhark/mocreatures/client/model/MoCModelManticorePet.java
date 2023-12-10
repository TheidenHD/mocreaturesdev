/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.model;

import drzhark.mocreatures.entity.hunter.MoCEntityManticorePet;
import net.minecraft.entity.Entity;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;

@OnlyIn(Dist.CLIENT)
public class MoCModelManticorePet extends MoCModelManticore {

    @Override
    public void updateAnimationModifiers(Entity entity) {
        MoCEntityManticorePet manticorePet = (MoCEntityManticorePet) entity;
        this.isFlyer = manticorePet.isFlyer();
        this.isSaddled = manticorePet.getIsRideable();
        this.flapwings = true;
        this.floating = this.isFlyer && manticorePet.isOnAir() && !manticorePet.onGround;
        this.isRidden = manticorePet.isBeingRidden();
        this.hasMane = true;
        this.hasSaberTeeth = true;
        this.onAir = manticorePet.isOnAir();
        this.hasStinger = true;
        this.isMovingVertically = manticorePet.getMotion().getY() != 0 && !manticorePet.onGround;
        this.hasChest = false;
        this.isTamed = false;
    }
}
