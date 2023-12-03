/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.ai.goal.NearestAttackableTargetGoal;

public class EntityAIHunt<T extends MobEntity> extends NearestAttackableTargetGoal<T> {

    private final CreatureEntity hunter;

    public EntityAIHunt(CreatureEntity entity, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate<MobEntity> predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
    }

    public EntityAIHunt(CreatureEntity entityCreature, Class<T> classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHunt(CreatureEntity entity, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, null);

    }

    @Override
    public boolean shouldExecute() {
        return ((MoCEntityAnimal) this.hunter).getIsHunting() && super.shouldExecute();
    }
}
