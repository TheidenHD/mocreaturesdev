/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import com.google.common.base.Predicate;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;

public class EntityAIHunt<T extends LivingEntity> extends EntityAINearestAttackableTarget<T> {

    private final EntityCreature hunter;

    public EntityAIHunt(EntityCreature entity, Class<T> classTarget, int chance, boolean checkSight, boolean onlyNearby, Predicate<LivingEntity> predicate) {
        super(entity, classTarget, chance, checkSight, onlyNearby, predicate);
        this.hunter = entity;
    }

    public EntityAIHunt(EntityCreature entityCreature, Class<T> classTarget, boolean checkSight) {
        this(entityCreature, classTarget, checkSight, false);
    }

    public EntityAIHunt(EntityCreature entity, Class<T> classTarget, boolean checkSight, boolean onlyNearby) {
        this(entity, classTarget, 10, checkSight, onlyNearby, null);

    }

    @Override
    public boolean shouldExecute() {
        return ((MoCEntityAnimal) this.hunter).getIsHunting() && super.shouldExecute();
    }
}
