package drzhark.mocreatures.entity.ai;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.entity.passive.MoCEntityBunny;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.ai.goal.Goal;
import net.minecraft.world.level.Level;

import java.util.List;

public class EntityAIBunnyReproduce extends Goal {
    private final MoCEntityBunny bunny;

    public EntityAIBunnyReproduce(MoCEntityBunny bunny) {
        this.bunny = bunny;
    }

    @Override
    public boolean canUse() {
        return bunny.getIsTamed() && bunny.getIsAdult() && bunny.getHasEaten() && bunny.getVehicle() == null;
    }

    @Override
    public void tick() {
        if (bunny.bunnyReproduceTickerA < 1023) {
            bunny.bunnyReproduceTickerA++;
        } else if (bunny.bunnyReproduceTickerB < 127) {
            bunny.bunnyReproduceTickerB++;
        } else {
            Level world = bunny.level();
            List<Entity> nearbyEntities = world.getEntities(bunny, bunny.getBoundingBox().inflate(4.0D));

            for (Entity entity : nearbyEntities) {
                if (!(entity instanceof MoCEntityBunny) || entity == bunny) {
                    continue;
                }

                MoCEntityBunny otherBunny = (MoCEntityBunny) entity;
                if (otherBunny.isPassenger() || otherBunny.bunnyReproduceTickerA < 1023 || !otherBunny.getIsAdult() || !otherBunny.getHasEaten()) {
                    continue;
                }

                bunny.getNavigation().moveTo(otherBunny, 1.0D);

                EntityType<?> babyType = bunny.getType();
                if (bunny.getRandom().nextInt(2) == 0) {
                    babyType = otherBunny.getType();
                }

                MoCEntityBunny babyBunny = (MoCEntityBunny) babyType.create(world);
                babyBunny.setPos(bunny.getX(), bunny.getY(), bunny.getZ());
                babyBunny.setAdult(false);



                world.addFreshEntity(babyBunny);
                MoCTools.playCustomSound(bunny, SoundEvents.CHICKEN_EGG);

                resetReproduction(bunny);
                resetReproduction(otherBunny);
                break;
            }
        }
    }

    private void resetReproduction(MoCEntityBunny bunny) {
        bunny.setHasEaten(false);
        bunny.bunnyReproduceTickerA = bunny.getRandom().nextInt(64);
        bunny.bunnyReproduceTickerB = 0;
    }
}
