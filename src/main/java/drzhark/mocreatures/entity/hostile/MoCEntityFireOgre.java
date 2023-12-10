/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

import javax.annotation.Nullable;

public class MoCEntityFireOgre extends MoCEntityOgre {

    public MoCEntityFireOgre(World world) {
        super(world);
        this.isImmuneToFire = true;
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(Attributes.MAX_HEALTH).setBaseValue(65.0D);
        this.getEntityAttribute(Attributes.ARMOR).setBaseValue(9.0D);
        this.getEntityAttribute(Attributes.ATTACK_DAMAGE).setBaseValue(7.5D);
    }

    @Override
    public ResourceLocation getTexture() {
        return MoCreatures.proxy.getModelTexture("ogre_fire.png");
    }

    @Override
    public boolean isFireStarter() {
        return true;
    }

    @Override
    public float getDestroyForce() {
        return MoCreatures.proxy.ogreFireStrength;
    }

    @Override
    public void livingTick() {
        super.livingTick();

        if (this.isWet()) {
            this.attackEntityFrom(DamageSource.DROWN, 1.0F);
        }
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FIRE_OGRE;
    }
}
