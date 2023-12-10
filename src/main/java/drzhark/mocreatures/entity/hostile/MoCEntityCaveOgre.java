/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityCaveOgre extends MoCEntityOgre {

    public MoCEntityCaveOgre(World world) {
        super(world);
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 60.0D).createMutableAttribute(Attributes.ARMOR, 10.0D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 8.0D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("ogre_cave.png");
    }

    /**
     * Returns the strength of the blasting power
     */
    @Override
    public float getDestroyForce() {
        return MoCreatures.proxy.ogreCaveStrength;
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    @Override
    public boolean getCanSpawnHere() {
        return super.getCanSpawnHere() && !this.world.canSeeSky(new BlockPos(this)) && (this.getPosY() < 50.0D);
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.CAVE_OGRE;
    }
}