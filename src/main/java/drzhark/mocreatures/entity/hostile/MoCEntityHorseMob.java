/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.hostile;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityMob;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import net.minecraft.block.Block;
import net.minecraft.block.SoundType;
import net.minecraft.entity.CreatureAttribute;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.attributes.AttributeModifierMap;
import net.minecraft.entity.ai.attributes.Attributes;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;

@SuppressWarnings("deprecation")
public class MoCEntityHorseMob extends MoCEntityMob {

    public int mouthCounter;
    public int textCounter;
    public int standCounter;
    public int tailCounter;
    public int eatingCounter;
    public int wingFlapCounter;

    public MoCEntityHorseMob(World world) {
        super(world);
        setSize(1.3964844F, 1.6F);
        experienceValue = 5;
    }

    @Override
    protected void initEntityAI() {
        this.goalSelector.addGoal(0, new EntityAISwimming(this));
        this.goalSelector.addGoal(2, new EntityAIAttackMelee(this, 1.0D, true));
        this.goalSelector.addGoal(8, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
        this.targetgoalSelector.addGoal(1, new EntityAINearestAttackableTarget<>(this, PlayerEntity.class, true));
    }

    public static AttributeModifierMap.MutableAttribute registerAttributes() {
        MoCEntityMob.registerAttributes().createMutableAttribute(Attributes.MAX_HEALTH, 30.0D).createMutableAttribute(Attributes.MOVEMENT_SPEED, 0.3D).createMutableAttribute(Attributes.ATTACK_DAMAGE, 4.0D);
    }

    @Override
    public void selectType() {
        if (this.world.provider.doesWaterVaporize()) {
            setTypeMoC(38);
            this.isImmuneToFire = true;
        } else {
            if (getTypeMoC() == 0) {
                int j = this.rand.nextInt(100);
                if (j <= (40)) {
                    setTypeMoC(23); //undead
                } else if (j <= (80)) {
                    setTypeMoC(26); //skeleton horse
                } else {
                    setTypeMoC(32); //bat
                }
            }
        }
    }

    /**
     * Overridden for the dynamic nightmare texture. *
     * 23 Undead
     * 24 Undead Unicorn
     * 25 Undead Pegasus
     * 26 skeleton
     * 27 skeleton unicorn
     * 28 skeleton pegasus
     * 30 bug horse
     * 32 Bat Horse
     * 38 nightmare
     */
    @Override
    public ResourceLocation getTexture() {

        switch (getTypeMoC()) {
            case 23://undead horse

                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getModelTexture("horseundead.png");
                }
                String baseTex = "horseundead";
                int max = 79;

                if (this.rand.nextInt(3) == 0) {
                    this.textCounter++;
                }
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > max) {
                    this.textCounter = 10;
                }

                String iteratorTex = String.valueOf(this.textCounter);
                iteratorTex = iteratorTex.substring(0, 1);
                String decayTex = String.valueOf(getAge() / 100);
                decayTex = decayTex.substring(0, 1);
                return MoCreatures.proxy.getModelTexture(baseTex + decayTex + iteratorTex + ".png");

            case 26:
                return MoCreatures.proxy.getModelTexture("horseskeleton.png");

            case 32:
                return MoCreatures.proxy.getModelTexture("horsebat.png");

            case 38:
                if (!MoCreatures.proxy.getAnimateTextures()) {
                    return MoCreatures.proxy.getModelTexture("horsenightmare1.png");
                }
                this.textCounter++;
                if (this.textCounter < 10) {
                    this.textCounter = 10;
                }
                if (this.textCounter > 59) {
                    this.textCounter = 10;
                }
                String NTA = "horsenightmare";
                String NTB = String.valueOf(this.textCounter);
                NTB = NTB.substring(0, 1);
                String NTC = ".png";

                return MoCreatures.proxy.getModelTexture(NTA + NTB + NTC);

            default:
                return MoCreatures.proxy.getModelTexture("horseundead.png");
        }
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_HORSE_DEATH_UNDEAD;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        stand();
        return MoCSoundEvents.ENTITY_HORSE_HURT_UNDEAD;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (this.rand.nextInt(10) == 0) {
            stand();
        }
        return MoCSoundEvents.ENTITY_HORSE_AMBIENT_UNDEAD;
    }

    @Override
    protected void playStepSound(BlockPos pos, Block blockIn) {
        if (!blockIn.getDefaultState().getMaterial().isLiquid()) {
            SoundType soundtype = blockIn.getSoundType();

            if (this.world.getBlockState(pos.up()).getBlock() == Blocks.SNOW_LAYER) {
                soundtype = Blocks.SNOW_LAYER.getSoundType();
            } else if (soundtype == SoundType.WOOD) {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP_WOOD, soundtype.getVolume() * 0.15F, soundtype.getPitch());
            } else {
                this.playSound(SoundEvents.ENTITY_HORSE_STEP, soundtype.getVolume() * 0.15F, soundtype.getPitch());
            }
        }
    }

    public boolean isOnAir() {
        return this.world.isAirBlock(new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(this.getPosY() - 0.2D), MathHelper
                .floor(this.getPosZ())));
    }

    @Override
    public void tick() {
        super.tick();

        if (this.mouthCounter > 0 && ++this.mouthCounter > 30) {
            this.mouthCounter = 0;
        }

        if (this.standCounter > 0 && ++this.standCounter > 20) {
            this.standCounter = 0;
        }

        if (this.tailCounter > 0 && ++this.tailCounter > 8) {
            this.tailCounter = 0;
        }

        if (this.eatingCounter > 0 && ++this.eatingCounter > 50) {
            this.eatingCounter = 0;
        }

        if (this.wingFlapCounter > 0 && ++this.wingFlapCounter > 20) {
            this.wingFlapCounter = 0;
            //TODO flap sound!
        }
    }

    @Override
    public boolean isFlyer() {
        return this.getTypeMoC() == 25 //undead pegasus
                || this.getTypeMoC() == 32 // bat horse
                || this.getTypeMoC() == 28; // skeleton pegasus
    }

    /**
     * Has a unicorn? to render it and buckle entities!
     */
    public boolean isUnicorned() {
        return this.getTypeMoC() == 24 || this.getTypeMoC() == 27 || this.getTypeMoC() == 32;
    }

    @Override
    public void livingTick() {

        super.livingTick();

        if (isOnAir() && isFlyer() && this.rand.nextInt(5) == 0) {
            this.wingFlapCounter = 1;
        }

        if (this.rand.nextInt(200) == 0) {
            moveTail();
        }

        if (!isOnAir() && (!this.isBeingRidden()) && this.rand.nextInt(250) == 0) {
            stand();
        }

        if (this.world.isRemote && getTypeMoC() == 38 && this.rand.nextInt(50) == 0) {
            LavaFX();
        }

        if (this.world.isRemote && getTypeMoC() == 23 && this.rand.nextInt(50) == 0) {
            UndeadFX();
        }

        if (!this.world.isRemote) {
            if (isFlyer() && this.rand.nextInt(500) == 0) {
                wingFlap();
            }

            if (!isOnAir() && (!this.isBeingRidden()) && this.rand.nextInt(300) == 0) {
                setEating();
            }

            if (!this.isBeingRidden() && this.rand.nextInt(100) == 0) {
                MoCTools.findMobRider(this);
                /*List list = this.world.getEntitiesWithinAABBExcludingEntity(this, getBoundingBox().grow(4D));
                for (int i = 0; i < list.size(); i++) {
                    Entity entity = (Entity) list.get(i);
                    if (!(entity instanceof MonsterEntity)) {
                        continue;
                    }
                    MonsterEntity entitymob = (MonsterEntity) entity;
                    if (entitymob.getRidingEntity() == null
                            && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                        entitymob.mountEntity(this);
                        break;
                    }
                }*/
            }
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void moveTail() {
        this.tailCounter = 1;
    }

    private void setEating() {
        this.eatingCounter = 1;
    }

    private void stand() {
        this.standCounter = 1;
    }

    public void wingFlap() {
        this.wingFlapCounter = 1;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (this.getTypeMoC() == 32 && MoCreatures.proxy.rareItemDropChance < 25) {
            flag = (this.rand.nextInt(100) < 25);
        }

        if (flag && (this.getTypeMoC() == 36 || (this.getTypeMoC() >= 50 && this.getTypeMoC() < 60))) //unicorn
        {
            return MoCItems.unicornhorn;
        }

        if (this.getTypeMoC() == 38 && flag && this.world.provider.doesWaterVaporize()) //nightmare
        {
            return MoCItems.heartfire;
        }
        if (this.getTypeMoC() == 32 && flag) //bat horse
        {
            return MoCItems.heartdarkness;
        }
        if (this.getTypeMoC() == 26)//skely
        {
            return Items.BONE;
        }
        if ((this.getTypeMoC() == 23 || this.getTypeMoC() == 24 || this.getTypeMoC() == 25)) {
            if (flag) {
                return MoCItems.heartundead;
            }
            return Items.ROTTEN_FLESH;
        }

        if (this.getTypeMoC() == 21 || this.getTypeMoC() == 22) {
            return Items.GHAST_TEAR;
        }

        return Items.LEATHER;
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        if (this.onGround && !isOnAir()) {
            stand();
        }
        openMouth();
        MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_HORSE_ANGRY_UNDEAD);
        return super.attackEntityAsMob(entityIn);
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);

        if ((this.getTypeMoC() == 23) || (this.getTypeMoC() == 24) || (this.getTypeMoC() == 25)) {
            MoCTools.spawnSlimes(this.world, this);
        }
    }

    @Override
    public double getMountedYOffset() {
        return (this.getHeight() * 0.75D) - 0.1D;
    }

    @Override
    public boolean getCanSpawnHere() {
        if (this.getPosY() < 50D && !this.world.provider.doesWaterVaporize()) {
            setTypeMoC(32);
        }
        return super.getCanSpawnHere();
    }

    public void UndeadFX() {
        MoCreatures.proxy.UndeadFX(this);
    }

    public void LavaFX() {
        MoCreatures.proxy.LavaFX(this);
    }

    /**
     * Get this Entity's CreatureAttribute
     */
    @Override
    public CreatureAttribute getCreatureAttribute() {
        if (getTypeMoC() == 23 || getTypeMoC() == 24 || getTypeMoC() == 25) {
            return CreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    protected boolean isHarmedByDaylight() {
        return true;
    }

    @Override
    public int maxFlyingHeight() {
        return 10;
    }

    @Override
    public void updatePassenger(Entity passenger) {
        double dist = (0.4D);
        double newPosX = this.getPosX() + (dist * Math.sin(this.renderYawOffset / 57.29578F));
        double newPosZ = this.getPosZ() - (dist * Math.cos(this.renderYawOffset / 57.29578F));
        passenger.setPosition(newPosX, this.getPosY() + getMountedYOffset() + passenger.getYOffset(), newPosZ);
        passenger.rotationYaw = this.rotationYaw;
    }

    // Adjusted to avoid most of the roof suffocation for now
    public float getEyeHeight() {
        return this.getHeight() * 0.9F;
    }
}
