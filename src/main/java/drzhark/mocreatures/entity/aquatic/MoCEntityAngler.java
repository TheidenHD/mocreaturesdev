/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.level.Level;

public class MoCEntityAngler extends MoCEntitySmallFish {

    public MoCEntityAngler(EntityType<? extends MoCEntityAngler> type, Level world) {
        super(type, world);
        this.setTypeMoC(3);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_anglerfish.png");
    }

    @Override
    protected ResourceLocation getDefaultLootTable() {
        return MoCLootTables.ANGLERFISH;
    }
    
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_DEATH_VICIOUS;
    }
}
