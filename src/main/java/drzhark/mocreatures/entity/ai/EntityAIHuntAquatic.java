/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAquatic;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAquatic;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.world.entity.player.Player;

public class EntityAIHuntAquatic<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {

    private final PathfinderMob hunter;
    private final Class<T> targetClass;

    public EntityAIHuntAquatic(PathfinderMob entity, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate<EntityLivingBase> predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
        this.targetClass = classTarget;
    }

    public EntityAIHuntAquatic(PathfinderMob entityCreature, Class<T> classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHuntAquatic(PathfinderMob entity, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, null);

    }

    @Override
    public boolean canUse() {
        // Conditions: Don't hunt when tamed and target entity is of class Player
        boolean hunterHasOwner = ((MoCEntityTameableAquatic)this.hunter).getIsTamed();
        boolean hunterTargetsPlayers = Player.class.isAssignableFrom(this.targetClass);
        return (!hunterTargetsPlayers || !hunterHasOwner) && ((MoCEntityAquatic) this.hunter).getIsHunting() && super.canUse();
    }
}
