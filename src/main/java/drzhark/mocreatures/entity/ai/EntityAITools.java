/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.player.EntityPlayer;

public class EntityAITools {

    protected static boolean IsNearPlayer(MobEntity entityliving, double d) {
        EntityPlayer entityplayer1 = entityliving.world.getClosestPlayerToEntity(entityliving, d);
        return entityplayer1 != null;
    }

    protected static EntityPlayer getIMoCTameableOwner(IMoCTameable pet) {
        if (pet.getOwnerId() == null) {
            return null;
        }

        for (int i = 0; i < ((MobEntity) pet).world.playerEntities.size(); ++i) {
            EntityPlayer entityplayer = ((MobEntity) pet).world.playerEntities.get(i);

            if (pet.getOwnerId().equals(entityplayer.getUniqueID())) {
                return entityplayer;
            }
        }
        return null;
    }
}
