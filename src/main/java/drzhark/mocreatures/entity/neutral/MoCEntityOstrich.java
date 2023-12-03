/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.neutral;

import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.ai.EntityAIFollowAdult;
import drzhark.mocreatures.entity.ai.EntityAIWanderMoC2;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.item.MoCEntityEgg;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCLootTables;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageAnimation;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.nbt.ListNBT;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.*;
import net.minecraft.world.World;

import javax.annotation.Nullable;
import java.util.List;

public class MoCEntityOstrich extends MoCEntityTameableAnimal {

    private static final DataParameter<Boolean> RIDEABLE = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> EGG_WATCH = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> CHESTED = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> IS_HIDING = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Boolean> FERTILE = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.BOOLEAN);
    private static final DataParameter<Integer> HELMET_TYPE = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    private static final DataParameter<Integer> FLAG_COLOR = EntityDataManager.createKey(MoCEntityOstrich.class, DataSerializers.VARINT);
    public int mouthCounter;
    public int wingCounter;
    public int sprintCounter;
    public int jumpCounter;
    public int transformCounter;
    public int transformType;
    public MoCAnimalChest localchest;
    public ItemStack localstack;
    private int eggCounter;
    private int hidingCounter;


    public MoCEntityOstrich(World world) {
        super(world);
        setSize(0.8F, 2.225F);
        setAdult(true);
        setAge(35);
        this.eggCounter = this.rand.nextInt(1000) + 1000;
        this.stepHeight = 1.0F;
    }

    @Override
    protected void initEntityAI() {
        this.tasks.addTask(1, new EntityAISwimming(this));
        this.tasks.addTask(4, new EntityAIFollowAdult(this, 1.0D));
        this.tasks.addTask(5, new EntityAIAttackMelee(this, 1.0D, true));
        this.tasks.addTask(6, new EntityAIWanderMoC2(this, 1.0D));
        this.tasks.addTask(7, new EntityAIWatchClosest(this, PlayerEntity.class, 8.0F));
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(16.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
        this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE);
        this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(3.0D);
    }

    @Override
    protected void entityInit() {
        super.entityInit();
        this.dataManager.register(EGG_WATCH, Boolean.FALSE);
        this.dataManager.register(CHESTED, Boolean.FALSE);
        this.dataManager.register(RIDEABLE, Boolean.FALSE);
        this.dataManager.register(IS_HIDING, Boolean.FALSE);
        this.dataManager.register(FERTILE, Boolean.FALSE);
        this.dataManager.register(HELMET_TYPE, 0);
        this.dataManager.register(FLAG_COLOR, 0);
    }

    @Override
    public boolean getIsRideable() {
        return this.dataManager.get(RIDEABLE);
    }

    @Override
    public void setRideable(boolean flag) {
        this.dataManager.set(RIDEABLE, flag);
    }

    public boolean getEggWatching() {
        return this.dataManager.get(EGG_WATCH);
    }

    public void setEggWatching(boolean flag) {
        this.dataManager.set(EGG_WATCH, flag);
    }

    public boolean getHiding() {
        return this.dataManager.get(IS_HIDING);
    }

    public void setHiding(boolean flag) {
        this.dataManager.set(IS_HIDING, flag);
    }

    public int getHelmet() {
        return this.dataManager.get(HELMET_TYPE);
    }

    public void setHelmet(int i) {
        this.dataManager.set(HELMET_TYPE, i);
    }

    public int getFlagColor() {
        return this.dataManager.get(FLAG_COLOR);
    }

    public void setFlagColor(int i) {
        this.dataManager.set(FLAG_COLOR, i);
    }

    public boolean getIsChested() {
        return this.dataManager.get(CHESTED);
    }

    public void setIsChested(boolean flag) {
        this.dataManager.set(CHESTED, flag);
    }

    public boolean getIsFertile() {
        return this.dataManager.get(FERTILE);
    }

    public void setFertile(boolean flag) {
        this.dataManager.set(FERTILE, flag);
    }

    @Override
    public boolean isMovementCeased() {
        return (getHiding() || this.isBeingRidden());
    }

    @Override
    public boolean isNotScared() {
        return (getTypeMoC() == 2 && getAttackTarget() != null) || (getTypeMoC() > 2);
    }

    @Override
    public boolean attackEntityFrom(DamageSource damagesource, float i) {
        //dmg reduction
        if (getIsTamed() && getHelmet() != 0) {
            int j = 0;
            switch (getHelmet()) {
                case 1:
                    j = 1;
                    break;
                case 5:
                case 6:
                case 2:
                    j = 2;
                    break;
                case 7:
                case 3:
                    j = 3;
                    break;
                case 4:
                case 9:
                case 10:
                case 11:
                case 12:
                    j = 4;
                    break;
            }
            i -= j;
            if (i <= 0) {
                i = 1;
            }
        }

        if (super.attackEntityFrom(damagesource, i)) {
            Entity entity = damagesource.getTrueSource();

            if (!(entity instanceof LivingEntity) || ((this.isBeingRidden()) && (entity == this.getRidingEntity())) || (entity instanceof PlayerEntity && getIsTamed())) {
                return false;
            }

            if ((entity != this) && (super.shouldAttackPlayers()) && getTypeMoC() > 2) {
                setAttackTarget((LivingEntity) entity);
                flapWings();
            }
            return true;
        } else {
            return false;
        }
    }

    @Override
    public void onDeath(DamageSource damagesource) {
        super.onDeath(damagesource);
        dropMyStuff();
    }

    @Override
    public boolean attackEntityAsMob(Entity entityIn) {
        if (entityIn instanceof PlayerEntity && !shouldAttackPlayers()) {
            return false;
        }
        openMouth();
        flapWings();
        return super.attackEntityAsMob(entityIn);
    }

    public float calculateMaxHealth() {
        switch (getTypeMoC()) {
            case 1:
                return 10;
            case 3:
            case 4:
                return 25;
            case 5:
            case 6:
                return 35;
            default:
                return 20;
        }
    }

    @Override
    public boolean canBeCollidedWith() {
        return !this.isBeingRidden();
    }

    @Override
    public void selectType() {
        if (getTypeMoC() == 0) {
            /*
             * 1 = chick /2 = female /3 = male /4 = albino male /5 = nether ostrich /6 = wyvern
             */
            int j = this.rand.nextInt(100);
            if (j <= (20)) {
                setTypeMoC(1);
            } else if (j <= (65)) {
                setTypeMoC(2);
            } else if (j <= (95)) {
                setTypeMoC(3);
            } else {
                setTypeMoC(4);
            }
        }
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(calculateMaxHealth());
        this.setHealth(getMaxHealth());
    }

    @Override
    public ResourceLocation getTexture() {
        if (this.transformCounter != 0 && this.transformType > 4) {
            String newText = "ostrich_male.png";
            if (this.transformType == 5) {
                newText = "ostrich_fire.png";
            }
            if (this.transformType == 6) {
                newText = "ostrich_dark.png";
            }
            if (this.transformType == 7) {
                newText = "ostrich_undead.png";
            }
            if (this.transformType == 8) {
                newText = "ostrich_light.png";
            }

            if ((this.transformCounter % 5) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
            if (this.transformCounter > 50 && (this.transformCounter % 3) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
            if (this.transformCounter > 75 && (this.transformCounter % 4) == 0) {
                return MoCreatures.proxy.getModelTexture(newText);
            }
        }

        switch (getTypeMoC()) {
            case 1:
                return MoCreatures.proxy.getModelTexture("ostrich_baby.png");
            case 2:
                return MoCreatures.proxy.getModelTexture("ostrich_female.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("ostrich_white.png");
            case 5:
                return MoCreatures.proxy.getModelTexture("ostrich_fire.png");
            case 6:
                return MoCreatures.proxy.getModelTexture("ostrich_dark.png");
            case 7:
                return MoCreatures.proxy.getModelTexture("ostrich_undead.png");
            case 8:
                return MoCreatures.proxy.getModelTexture("ostrich_light.png");
            default:
                return MoCreatures.proxy.getModelTexture("ostrich_male.png");
        }
    }

    @Override
    public double getCustomSpeed() {
        double ostrichSpeed = 0.8D;
        if (getTypeMoC() == 3) {
            ostrichSpeed = 1.1D;
        } else if (getTypeMoC() == 4) {
            ostrichSpeed = 1.3D;
        } else if (getTypeMoC() == 5) {
            ostrichSpeed = 1.4D;
            this.isImmuneToFire = true;
        }
        if (this.sprintCounter > 0 && this.sprintCounter < 200) {
            ostrichSpeed *= 1.5D;
        }
        if (this.sprintCounter > 200) {
            ostrichSpeed *= 0.5D;
        }
        return ostrichSpeed;
    }

    @Override
    public boolean rideableEntity() {
        return true;
    }

    @Override
    public void tick() {
        super.tick();

        if (getHiding()) {
            this.prevRenderYawOffset = this.renderYawOffset = this.rotationYaw = this.prevRotationYaw;
        }

        if (this.mouthCounter > 0 && ++this.mouthCounter > 20) {
            this.mouthCounter = 0;
        }

        if (this.wingCounter > 0 && ++this.wingCounter > 80) {
            this.wingCounter = 0;
        }

        if (this.jumpCounter > 0 && ++this.jumpCounter > 8) {
            this.jumpCounter = 0;
        }

        if (this.sprintCounter > 0 && ++this.sprintCounter > 300) {
            this.sprintCounter = 0;
        }

        if (this.transformCounter > 0) {
            if (this.transformCounter == 40) {
                MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_TRANSFORM);
            }

            if (++this.transformCounter > 100) {
                this.transformCounter = 0;
                if (this.transformType != 0) {
                    dropArmor();
                    setTypeMoC(this.transformType);
                    selectType();
                }
            }
        }
    }

    public void transform(int tType) {
        if (!this.world.isRemote) {
            MoCMessageHandler.INSTANCE.sendToAllAround(new MoCMessageAnimation(this.getEntityId(), tType), new TargetPoint(this.world.provider.getDimensionType().getId(), this.getPosX(), this.getPosY(), this.getPosZ(), 64));
        }
        this.transformType = tType;
        if (!this.isBeingRidden() && this.transformType != 0) {
            dropArmor();
            this.transformCounter = 1;
        }
    }

    @Override
    public void performAnimation(int animationType) {
        if (animationType >= 5 && animationType < 9) //transform 5 - 8
        {
            this.transformType = animationType;
            this.transformCounter = 1;
        }

    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();

        if (getIsTamed() && !this.world.isRemote && (this.rand.nextInt(300) == 0) && (getHealth() <= getMaxHealth()) && (this.deathTime == 0)) {
            this.setHealth(getHealth() + 1);
        }

        if (!this.world.isRemote) {
            //ostrich buckle!
            if (getTypeMoC() == 8 && (this.sprintCounter > 0 && this.sprintCounter < 150) && (this.isBeingRidden()) && rand.nextInt(15) == 0) {
                MoCTools.buckleMobs(this, 2D, this.world);
            }

            // TODO
            //shy ostriches will run and hide
            /*if (!isNotScared() && fleeingTick > 0 && fleeingTick < 2) {
                fleeingTick = 0;
                setHiding(true);
                this.getNavigator().clearPath();
            }*/

            if (getHiding()) {
                //wild shy ostriches will hide their heads only for a short term
                //tamed ostriches will keep their heads hidden until the whip is used again
                if (++this.hidingCounter > 500 && !getIsTamed()) {
                    setHiding(false);
                    this.hidingCounter = 0;
                }
            }

            if (getTypeMoC() == 1 && (this.rand.nextInt(200) == 0)) {
                //when is chick and becomes adult, change over to different type
                setAge(getAge() + 1);
                if (getAge() >= 100) {
                    setAdult(true);
                    setTypeMoC(0);
                    selectType();
                }
            }

            //egg laying
            if (getIsFertile() && getTypeMoC() == 2 && !getEggWatching() && --this.eggCounter <= 0 && this.rand.nextInt(5) == 0) {
                int ostrichEggType = 30;
                MoCEntityOstrich maleOstrich = getClosestMaleOstrich(this, 8D);
                if (maleOstrich != null && this.rand.nextInt(100) < MoCreatures.proxy.ostrichEggDropChance) {
                    MoCEntityEgg entityegg = new MoCEntityEgg(this.world, ostrichEggType);
                    entityegg.setPosition(this.getPosX(), this.getPosY(), this.getPosZ());
                    this.world.addEntity(entityegg);

                    if (!this.getIsTamed()) {
                        setEggWatching(true);
                        maleOstrich.setEggWatching(true);
                        openMouth();
                    }

                    //TODO change sound
                    MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
                    this.eggCounter = this.rand.nextInt(2000) + 2000;
                    setFertile(false);
                }
            }

            //egg protection
            if (getEggWatching()) {
                //look for and protect eggs and move close
                MoCEntityEgg myEgg = (MoCEntityEgg) getBoogey(8D);
                if (myEgg != null && MoCTools.getSqDistanceTo(myEgg, this.getPosX(), this.getPosY(), this.getPosZ()) > 4D) {
                    Path pathEntity = this.navigator.getPathToPos(myEgg.getPosition());
                    this.navigator.setPath(pathEntity, 2D);
                }
                //didn't find egg
                if (myEgg == null) {
                    setEggWatching(false);

                    PlayerEntity eggStealer = this.world.getClosestPlayer(this, 10D);
                    if (eggStealer != null) {
                        this.world.getDifficulty();
                        if (!getIsTamed() && this.world.getDifficulty() != EnumDifficulty.PEACEFUL) {
                            setAttackTarget(eggStealer);
                            flapWings();
                        }
                    }
                }
            }
        }
    }

    protected MoCEntityOstrich getClosestMaleOstrich(Entity entity, double d) {
        double d1 = -1D;
        MoCEntityOstrich entityliving = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof MoCEntityOstrich) || ((MoCEntityOstrich) entity1).getTypeMoC() < 3) {
                continue;
            }

            double d2 = entity1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityliving = (MoCEntityOstrich) entity1;
            }
        }
        return entityliving;
    }

    @Override
    public boolean entitiesToInclude(Entity entity) {
        return ((entity instanceof MoCEntityEgg) && (((MoCEntityEgg) entity).eggType == 30));
    }

    @Override
    public boolean processInteract(PlayerEntity player, Hand hand) {
        final Boolean tameResult = this.processTameInteract(player, hand);
        if (tameResult != null) {
            return tameResult;
        }

        final ItemStack stack = player.getHeldItem(hand);
        if (getIsTamed() && (getTypeMoC() > 1) && !stack.isEmpty() && !getIsRideable() && (stack.getItem() == MoCItems.horsesaddle || stack.getItem() instanceof ItemSaddle)) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            setRideable(true);
            return true;
        }

        if (!getIsFertile() && !stack.isEmpty() && getTypeMoC() == 2 && stack.getItem() == Items.MELON_SEEDS) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            openMouth();
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_EATING);
            setFertile(true);
            return true;
        }

        //makes the ostrich stay by hiding their heads
        if (!stack.isEmpty() && (stack.getItem() == MoCItems.whip) && getIsTamed() && (!this.isBeingRidden())) {
            setHiding(!getHiding());
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getTypeMoC() > 1 && stack.getItem() == MoCItems.essencedarkness) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getTypeMoC() == 6) {
                this.setHealth(getMaxHealth());
            } else {
                transform(6);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getTypeMoC() > 1 && stack.getItem() == MoCItems.essenceundead) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getTypeMoC() == 7) {
                this.setHealth(getMaxHealth());
            } else {
                transform(7);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getTypeMoC() > 1 && stack.getItem() == MoCItems.essencelight) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getTypeMoC() == 8) {
                this.setHealth(getMaxHealth());
            } else {
                transform(8);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }

        if (!stack.isEmpty() && this.getIsTamed() && getTypeMoC() > 1 && stack.getItem() == MoCItems.essencefire) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            if (stack.isEmpty()) {
                player.setHeldItem(hand, new ItemStack(Items.GLASS_BOTTLE));
            } else {
                player.inventory.addItemStackToInventory(new ItemStack(Items.GLASS_BOTTLE));
            }
            if (getTypeMoC() == 5) {
                this.setHealth(getMaxHealth());
            } else {
                transform(5);
            }
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_DRINKING);
            return true;
        }
        if (getIsTamed() && getIsChested() && (getTypeMoC() > 1) && !stack.isEmpty() && stack.getItem() == Item.getItemFromBlock(Blocks.WOOL)) {
            int colorInt = (stack.getItemDamage());
            if (colorInt == 0) {
                colorInt = 16;
            }
            if (!player.capabilities.isCreativeMode) stack.shrink(1);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            dropFlag();
            setFlagColor((byte) colorInt);
            return true;
        }

        if (!stack.isEmpty() && (getTypeMoC() > 1) && getIsTamed() && !getIsChested() && (stack.getItem() == Item.getItemFromBlock(Blocks.CHEST))) {
            if (!player.capabilities.isCreativeMode) stack.shrink(1);

            //entityplayer.inventory.addItemStackToInventory(new ItemStack(MoCreatures.key));
            setIsChested(true);
            MoCTools.playCustomSound(this, SoundEvents.ENTITY_CHICKEN_EGG);
            return true;
        }

        if (player.isSneaking() && getIsChested()) {
            // if first time opening a chest, we must initialize it
            if (this.localchest == null) {
                this.localchest = new MoCAnimalChest("OstrichChest", 9);
            }
            if (!this.world.isRemote) {
                player.displayGUIChest(this.localchest);
            }
            return true;
        }

        if (getIsTamed() && (getTypeMoC() > 1) && !stack.isEmpty()) {

            Item item = stack.getItem();
            if (item instanceof ItemArmor && ((ItemArmor) item).armorType == EquipmentSlotType.HEAD) {
                final ItemArmor itemArmor = (ItemArmor) stack.getItem();
                byte helmetType = 0;
                if (stack.getItem() == Items.LEATHER_HELMET) {
                    helmetType = 1;
                } else if (stack.getItem() == Items.IRON_HELMET) {
                    helmetType = 2;
                } else if (stack.getItem() == Items.GOLDEN_HELMET) {
                    helmetType = 3;
                } else if (stack.getItem() == Items.DIAMOND_HELMET) {
                    helmetType = 4;
                } else if (stack.getItem() == MoCItems.helmetHide) {
                    helmetType = 5;
                } else if (stack.getItem() == MoCItems.helmetFur) {
                    helmetType = 6;
                } else if (stack.getItem() == MoCItems.helmetCroc) {
                    helmetType = 7;
                } else if (stack.getItem() == MoCItems.scorpHelmetDirt) {
                    helmetType = 9;
                } else if (stack.getItem() == MoCItems.scorpHelmetFrost) {
                    helmetType = 10;
                } else if (stack.getItem() == MoCItems.scorpHelmetCave) {
                    helmetType = 11;
                } else if (stack.getItem() == MoCItems.scorpHelmetNether) {
                    helmetType = 12;
                }

                if (helmetType != 0) {
                    player.setHeldItem(hand, ItemStack.EMPTY);
                    dropArmor();
                    this.setItemStackToSlot(itemArmor.armorType, stack);
                    MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_ARMOR_OFF);
                    setHelmet(helmetType);
                    return true;
                }
            }
        }
        if (this.getIsRideable() && this.getIsAdult() && (!this.getIsChested() || !player.isSneaking()) && !this.isBeingRidden()) {
            if (!this.world.isRemote && player.startRiding(this)) {
                player.rotationYaw = this.rotationYaw;
                player.rotationPitch = this.rotationPitch;
                setHiding(false);
            }
            return true;
        }
        return super.processInteract(player, hand);
    }

    /**
     * Drops a block of the color of the flag if carrying one
     */
    private void dropFlag() {
        if (!this.world.isRemote && getFlagColor() != 0) {
            int color = getFlagColor();
            if (color == 16) {
                color = 0;
            }
            ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), new ItemStack(Blocks.WOOL, 1, color));
            entityitem.setDefaultPickupDelay();
            this.world.addEntity(entityitem);
            setFlagColor((byte) 0);
        }
    }

    private void openMouth() {
        this.mouthCounter = 1;
    }

    private void flapWings() {
        this.wingCounter = 1;
    }

    @Override
    protected SoundEvent getHurtSound(DamageSource source) {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_HURT;
    }

    @Override
    protected SoundEvent getAmbientSound() {
        openMouth();
        if (getTypeMoC() == 1) {
            return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT_BABY;
        }
        return MoCSoundEvents.ENTITY_OSTRICH_AMBIENT;
    }

    @Override
    protected SoundEvent getDeathSound() {
        openMouth();
        return MoCSoundEvents.ENTITY_OSTRICH_DEATH;
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        if (!getIsAdult()) {
            return null;
        }

        return MoCLootTables.OSTRICH;
    }

    @Override
    protected Item getDropItem() {
        boolean flag = (this.rand.nextInt(100) < MoCreatures.proxy.rareItemDropChance);
        if (flag && (this.getTypeMoC() == 8)) // unicorn
        {
            return MoCItems.unicornhorn;
        }
        if (this.getTypeMoC() == 5 && flag) {
            return MoCItems.heartfire;
        }
        if (this.getTypeMoC() == 6 && flag) // bat horse
        {
            return MoCItems.heartdarkness;
        }
        if (this.getTypeMoC() == 7) {
            if (flag) {
                return MoCItems.heartundead;
            }
            return Items.ROTTEN_FLESH;
        }

        return null;
    }

    @Override
    public void readAdditional(CompoundNBT nbttagcompound) {
        super.readAdditional(nbttagcompound);
        setRideable(nbttagcompound.getBoolean("Saddle"));
        setEggWatching(nbttagcompound.getBoolean("EggWatch"));
        setHiding(nbttagcompound.getBoolean("Hiding"));
        setHelmet(nbttagcompound.getInt("Helmet"));
        setFlagColor(nbttagcompound.getInt("FlagColor"));
        setIsChested(nbttagcompound.getBoolean("Bagged"));
        setFertile(nbttagcompound.getBoolean("Fertile"));

        if (getIsChested()) {
            ListNBT nbttaglist = nbttagcompound.getList("Items", 10);
            this.localchest = new MoCAnimalChest("OstrichChest", 18);
            for (int i = 0; i < nbttaglist.size(); i++) {
                CompoundNBT nbttagcompound1 = nbttaglist.getCompound(i);
                int j = nbttagcompound1.getByte("Slot") & 0xff;
                if (j < this.localchest.getSizeInventory()) {
                    this.localchest.setInventorySlotContents(j, new ItemStack(nbttagcompound1));
                }
            }
        }
    }

    @Override
    public void writeAdditional(CompoundNBT nbttagcompound) {
        super.writeAdditional(nbttagcompound);
        nbttagcompound.putBoolean("Saddle", getIsRideable());
        nbttagcompound.putBoolean("EggWatch", getEggWatching());
        nbttagcompound.putBoolean("Hiding", getHiding());
        nbttagcompound.putInt("Helmet", getHelmet());
        nbttagcompound.putInt("FlagColor", getFlagColor());
        nbttagcompound.putBoolean("Bagged", getIsChested());
        nbttagcompound.putBoolean("Fertile", getIsFertile());

        if (getIsChested() && this.localchest != null) {
            ListNBT nbttaglist = new ListNBT();
            for (int i = 0; i < this.localchest.getSizeInventory(); i++) {
                this.localstack = this.localchest.getStackInSlot(i);
                if (!this.localstack.isEmpty()) {
                    CompoundNBT nbttagcompound1 = new CompoundNBT();
                    nbttagcompound1.putByte("Slot", (byte) i);
                    this.localstack.writeToNBT(nbttagcompound1);
                    nbttaglist.add(nbttagcompound1);
                }
            }
            nbttagcompound.put("Items", nbttaglist);
        }
    }

    @Override
    public int nameYOffset() {
        if (getTypeMoC() > 1) {
            return -105;
        } else {
            return (-5 - getAge());
        }
    }

    /*@Override
    public boolean updateMount() {
        return getIsTamed();
    }*/

    /* @Override
     public boolean forceUpdates() {
         return getIsTamed();
     }*/

    @Override
    public boolean isMyHealFood(ItemStack par1ItemStack) {
        return MoCTools.isItemEdible(par1ItemStack.getItem());
    }

    @Override
    public void dropMyStuff() {
        if (!this.world.isRemote) {
            dropArmor();
            MoCTools.dropSaddle(this, this.world);

            if (getIsChested()) {
                MoCTools.dropInventory(this, this.localchest);
                MoCTools.dropCustomItem(this, this.world, new ItemStack(Blocks.CHEST, 1));
                setIsChested(false);
            }
        }
    }

    /**
     * Drops the helmet
     */
    @Override
    public void dropArmor() {
        if (!this.world.isRemote) {
            final ItemStack itemStack = this.getItemStackFromSlot(EquipmentSlotType.HEAD);
            if (!itemStack.isEmpty() && itemStack.getItem() instanceof ItemArmor) {
                final ItemEntity entityitem = new ItemEntity(this.world, this.getPosX(), this.getPosY(), this.getPosZ(), itemStack.copy());
                entityitem.setDefaultPickupDelay();
                this.world.addEntity(entityitem);
            }
            setHelmet((byte) 0);
        }
    }

    @Override
    public boolean isFlyer() {
        return this.isBeingRidden() && (getTypeMoC() == 5 || getTypeMoC() == 6);
    }

    @Override
    public void fall(float f, float f1) {
        if (isFlyer()) {
            return;
        }
        super.fall(f, f1);
    }

    @Override
    protected double myFallSpeed() {
        return 0.89D;
    }

    @Override
    protected double flyerThrust() {
        return 0.8D;
    }

    @Override
    protected float flyerFriction() {
        return 0.96F;
    }

    @Override
    protected boolean selfPropelledFlyer() {
        return getTypeMoC() == 6;
    }

    @Override
    public void makeEntityJump() {
        if (this.jumpCounter > 5) {
            this.jumpCounter = 1;
        }
        if (this.jumpCounter == 0) {
            MoCTools.playCustomSound(this, MoCSoundEvents.ENTITY_GENERIC_WINGFLAP);
            this.jumpPending = true;
            this.jumpCounter = 1;
        }
    }

    @Override
    public EnumCreatureAttribute getCreatureAttribute() {
        if (getTypeMoC() == 7) {
            return EnumCreatureAttribute.UNDEAD;
        }
        return super.getCreatureAttribute();
    }

    @Override
    public int getMaxSpawnedInChunk() {
        return 1;
    }

    //TODO 
    //improve fall flapping wing animation
    //IMPROVE DIVE CODE
    //ATTACK!
    //EGG LYING

    @Override
    public int getMaxAge() {
        return 20;
    }

    public float getEyeHeight() {
        return this.getHeight() * 0.945F;
    }
}
