/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;

public class EntityAITools {

    protected static boolean IsNearPlayer(LivingEntity entityliving, double d) {
        PlayerEntity entityplayer1 = entityliving.world.getClosestPlayer(entityliving, d);
        return entityplayer1 != null;
    }

    protected static PlayerEntity getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        for (int i = 0; i < ((LivingEntity) pet).world.playerEntities.size(); ++i) {
            PlayerEntity entityplayer = ((LivingEntity) pet).world.playerEntities.get(i);

            if (pet.getOwnerId().equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
