/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.IMoCEntity;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.ai.EntityAIPanic;

public class EntityAIPanicMoC extends EntityAIPanic {

    private final CreatureEntity entityCreature;

    public EntityAIPanicMoC(CreatureEntity creature, double speedIn) {
        super(creature, speedIn);
        this.entityCreature = creature;
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    @Override
    public boolean shouldExecute() {
        if (this.entityCreature instanceof IMoCEntity && ((IMoCEntity) this.entityCreature).isNotScared()) {
            return false;
        }
        return super.shouldExecute();
    }

}
