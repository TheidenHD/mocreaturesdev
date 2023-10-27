/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.aquatic;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityAngler extends MoCEntitySmallFish {

    public MoCEntityAngler(EntityType<? extends MoCEntityAngler> type, World world) {
        super(type, world);
        this.setTypeMoC(3);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("smallfish_anglerfish.png");
    }

    @Nullable
    protected ResourceLocation getLootTable() {        return MoCLootTables.ANGLERFISH;
    }
    
    protected SoundEvent getDeathSound() {
        return MoCSoundEvents.ENTITY_FISH_DEATH_VICIOUS;
    }
}
