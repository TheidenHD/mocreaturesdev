package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.entity.tameable.IMoCTameable;
import net.minecraft.world.entity.EntityCreature;
import net.minecraft.world.entity.EntityLivingBase;
import net.minecraft.world.entity.ai.EntityAINearestAttackableTarget;

public class EntityAITargetNonTamedMoC<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<T> {
    private final EntityCreature tameable;

    public EntityAITargetNonTamedMoC(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
        super(creature, classTarget, checkSight);
        this.tameable = creature;
    }

    public boolean shouldExecute() {
        return this.tameable instanceof IMoCTameable && !((IMoCTameable) this.tameable).getIsTamed() && super.shouldExecute();
    }
}
