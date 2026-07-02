package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.ai.EntityAIBase;
import net.minecraft.init.SoundEvents;
import net.minecraft.world.level.Level;

import java.util.List;

public class EntityAIBunnyReproduce extends EntityAIBase {
    private final MoCEntityBunny bunny;

    public EntityAIBunnyReproduce(MoCEntityBunny bunny) {
        this.bunny = bunny;
    }

    @Override
    public boolean canUse() {
        return bunny.getIsTamed() && bunny.getIsAdult() && bunny.getHasEaten() && bunny.getRidingEntity() == null;
    }

    @Override
    public void updateTask() {
        if (bunny.bunnyReproduceTickerA < 1023) {
            bunny.bunnyReproduceTickerA++;
        } else if (bunny.bunnyReproduceTickerB < 127) {
            bunny.bunnyReproduceTickerB++;
        } else {
            Level world = bunny.world;
            List<Entity> nearbyEntities = world.getEntitiesWithinAABBExcludingEntity(bunny, bunny.getEntityBoundingBox().grow(4.0D));

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof MoCEntityBunny) || entity == bunny) {
                    continue;
                }

                MoCEntityBunny otherBunny = (MoCEntityBunny) entity;
                if (otherBunny.getRidingEntity() != null || otherBunny.bunnyReproduceTickerA < 1023 || !otherBunny.getIsAdult() || !otherBunny.getHasEaten()) {
                    continue;
                }

                bunny.getNavigator().tryMoveToEntityLiving(otherBunny, 1.0D);

                MoCEntityBunny babyBunny = new MoCEntityBunny(world);
                babyBunny.setPos(bunny.posX, bunny.posY, bunny.posZ);
                babyBunny.setAdult(false);

                int babyType = bunny.getType();
                if (bunny.getRNG().nextInt(2) == 0) {
                    babyType = otherBunny.getType();
                }

                babyBunny.setType(babyType);
                world.spawnEntity(babyBunny);
                MoCTools.playCustomSound(bunny, SoundEvents.ENTITY_CHICKEN_EGG);

                resetReproduction(bunny);
                resetReproduction(otherBunny);
                break;
            }
        }
    }

    private void resetReproduction(MoCEntityBunny bunny) {
        bunny.setHasEaten(false);
        bunny.bunnyReproduceTickerA = bunny.getRNG().nextInt(64);
        bunny.bunnyReproduceTickerB = 0;
    }
}
