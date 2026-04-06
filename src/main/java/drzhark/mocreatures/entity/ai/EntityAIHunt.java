/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.PathfinderMob;
import net.minecraft.world.entity.ai.goal.target.NearestAttackableTargetGoal;
import net.minecraft.world.entity.player.Player;

public class EntityAIHunt<T extends LivingEntity> extends NearestAttackableTargetGoal<T> {

    private final PathfinderMob hunter;
    private final Class<T> targetClass;

    public EntityAIHunt(PathfinderMob entity, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate<LivingEntity> predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
        this.targetClass = classTarget;
    }

    public EntityAIHunt(PathfinderMob entityCreature, Class<T> classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHunt(PathfinderMob entity, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, null);

    }

    @Override
    public boolean canUse() {
        // Conditions: Don't hunt when tamed and target entity is of class Player
        boolean hunterHasOwner = ((MoCEntityTameableAnimal)this.hunter).getIsTamed();
        boolean hunterTargetsPlayers = Player.class.isAssignableFrom(this.targetClass);
        return (!hunterTargetsPlayers || !hunterHasOwner) && ((MoCEntityAnimal) this.hunter).getIsHunting() && super.canUse();
    }
}
