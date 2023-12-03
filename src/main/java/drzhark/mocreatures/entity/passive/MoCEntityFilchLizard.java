/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.entity.passive;

import com.google.common.collect.Sets;
import drzhark.mocreatures.MoCTools;
import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.init.MoCLootTables;
import net.minecraft.block.Block;
import net.minecraft.entity.CreatureEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.init.Items;
import net.minecraft.init.SoundEvents;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.ItemStack;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.DifficultyInstance;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.storage.loot.LootContext;
import net.minecraft.world.storage.loot.LootTable;
import net.minecraftforge.common.BiomeDictionary;

import javax.annotation.Nullable;
import java.util.List;
import java.util.Set;

// Courtesy of Daveyx0, permission given
public class MoCEntityFilchLizard extends MoCEntityAnimal {

    protected ItemStack[] stealItems;

    public MoCEntityFilchLizard(World worldIn) {
        super(worldIn);
        this.inventoryHandsDropChances[0] = 0f;
        this.inventoryHandsDropChances[1] = 0f;
        this.setSize(0.6f, 0.5f);
        this.experienceValue = 3;
    }

    @Override
    protected void initEntityAI() {
        stealItems = getCustomLootItems(this, this.getStealLootTable(), new ItemStack(Items.IRON_INGOT));
        this.tasks.addTask(0, new EntityAISwimming(this));
        this.tasks.addTask(1, new EntityAIPanic(this, 1.25D));
        this.tasks.addTask(2, new EntityAIGrabItemFromFloor(this, 1.2D, Sets.newHashSet(stealItems), true));
        this.tasks.addTask(3, new EntityAIStealFromPlayer(this, 0.8D, Sets.newHashSet(stealItems), true));
        this.tasks.addTask(4, new MoCEntityFilchLizard.AIAvoidWhenNasty(this, PlayerEntity.class, 16.0F, 1.0D, 1.33D));
        this.tasks.addTask(5, new EntityAIWander(this, 1.0D));
        this.tasks.addTask(6, new EntityAIWatchClosest(this, PlayerEntity.class, 6.0F));
        this.tasks.addTask(7, new EntityAILookIdle(this));
    }

    @Override
    public void selectType() {
        checkSpawningBiome();
    }

    @Override
    public ResourceLocation getTexture() {
        switch (getTypeMoC()) {
            case 2:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand.png");
            case 3:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand_red.png");
            case 4:
                return MoCreatures.proxy.getModelTexture("lizard_filch_sand_silver.png");
            default:
                return MoCreatures.proxy.getModelTexture("lizard_filch.png");
        }
    }

    @Override
    public boolean checkSpawningBiome() {
        BlockPos pos = new BlockPos(MathHelper.floor(this.getPosX()), MathHelper.floor(getBoundingBox().minY), this.getPosZ());
        Biome biome = MoCTools.biomeKind(this.world, pos);
        if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.SANDY)) {
            setTypeMoC(2);
        } else if (BiomeDictionary.hasType(biome, BiomeDictionary.Type.MESA)) {
            setTypeMoC(3);
        } else if (this.dimension == MoCreatures.proxy.wyvernDimension) {
            setTypeMoC(4);
        } else {
            setTypeMoC(1);
        }
        return true;
    }

    @Override
    public void onLivingUpdate() {
        super.onLivingUpdate();
        if (!this.getHeldItemMainhand().isEmpty()) {
            this.setSize(0.6f, 0.75f);
        } else {
            this.setSize(0.6f, 0.3f);
        }
    }

    @Override
    protected int getExperiencePoints(PlayerEntity player) {
        return experienceValue;
    }

    @Nullable
    protected ResourceLocation getSpawnLootTable() {
        return MoCLootTables.FILCH_LIZARD_SPAWN;
    }

    @Nullable
    protected ResourceLocation getStealLootTable() {
        return MoCLootTables.FILCH_LIZARD_STEAL;
    }

    @Override
    protected void applyEntityAttributes() {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(2.0D);
        this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.25D);
    }

    @Override
    protected void updateAITasks() {
        super.updateAITasks();
    }

    @Override
    public IEntityLivingData onInitialSpawn(DifficultyInstance difficulty, @Nullable IEntityLivingData livingdata) {
        if (rand.nextInt(100 / MoCreatures.proxy.filchLizardSpawnItemChance) == 0) {
            while (this.getHeldItemMainhand().isEmpty() && !getEntityWorld().isRemote) {
                this.setItemStackToSlot(EquipmentSlotType.MAINHAND, getCustomLootItem(this, this.getSpawnLootTable(), new ItemStack(Items.IRON_INGOT)));
            }
        }
        return super.onInitialSpawn(difficulty, livingdata);
    }

    public void dropItemStack(ItemStack itemIn, float offsetY) {
        this.entityDropItem(itemIn, offsetY);
    }

    @Override
    public boolean attackEntityFrom(DamageSource par1DamageSource, float par2) {
        if (par1DamageSource.getTrueSource() != null) {
            this.setLastAttackedEntity(par1DamageSource.getTrueSource());
        }
        ItemStack stack = this.getHeldItemMainhand();
        if (!stack.isEmpty() && !getEntityWorld().isRemote) {
            ItemStack newStack = new ItemStack(stack.getItem(), 1, stack.getMetadata());
            this.dropItemStack(newStack, 1);
            this.setItemStackToSlot(EquipmentSlotType.MAINHAND, ItemStack.EMPTY);
        }
        return super.attackEntityFrom(par1DamageSource, par2);
    }

    @Nullable
    public ItemStack getCustomLootItem(Entity entityIn, ResourceLocation resourceLootTable, ItemStack defaultItem) {
        if (resourceLootTable != null) {
            LootTable loottable = entityIn.world.getLootTableManager().getLootTableFromLocation(resourceLootTable);
            LootContext.Builder lootContextBuilder = (new LootContext.Builder((WorldServer) entityIn.world)).withLootedEntity(entityIn);
            for (ItemStack itemstack : loottable.generateLootForPools(entityIn.getEntityWorld().rand, lootContextBuilder.build())) {
                return itemstack;
            }
        }
        return defaultItem;
    }

    @Nullable
    public ItemStack[] getCustomLootItems(Entity entityIn, ResourceLocation resourceLootTable, ItemStack defaultItem) {
        ItemStack[] arrayOfItems = null;
        if (resourceLootTable != null) {
            LootTable loottable = entityIn.world.getLootTableManager().getLootTableFromLocation(resourceLootTable);
            LootContext.Builder lootContextBuilder = (new LootContext.Builder((WorldServer) entityIn.world)).withLootedEntity(entityIn);
            List<ItemStack> listOfItems = loottable.generateLootForPools(entityIn.getEntityWorld().rand, lootContextBuilder.build());
            arrayOfItems = new ItemStack[listOfItems.size()];
            int i = 0;
            for (ItemStack itemstack : listOfItems) {
                arrayOfItems[i] = itemstack;
                i += 1;
            }
        }
        if (arrayOfItems == null) {
            arrayOfItems = new ItemStack[]{defaultItem};
        }
        return arrayOfItems;
    }

    // Sneaky...
    @Override
    protected void playStepSound(BlockPos pos, Block block) {
    }

    @Nullable
    protected ResourceLocation getLootTable() {
        return MoCLootTables.FILCH_LIZARD;
    }

    static class AIAvoidWhenNasty extends EntityAIAvoidEntity {
        public AIAvoidWhenNasty(CreatureEntity theEntityIn, Class classToAvoidIn, float avoidDistanceIn, double farSpeedIn, double nearSpeedIn) {
            super(theEntityIn, classToAvoidIn, avoidDistanceIn, farSpeedIn, nearSpeedIn);
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            return !entity.getHeldItemMainhand().isEmpty() && super.shouldExecute();
        }
    }

    static class EntityAIGrabItemFromFloor extends Goal {
        /**
         * The entity using this AI that is tempted by the player.
         */
        private final CreatureEntity temptedEntity;
        private final double speed;
        private final Set<ItemStack> temptItem;
        private final boolean canGetScared;
        /**
         * X position of player tempting this mob
         */
        private double targetX;
        /**
         * Y position of player tempting this mob
         */
        private double targetY;
        /**
         * Z position of player tempting this mob
         */
        private double targetZ;
        /**
         * Tempting player's pitch
         */
        private double pitch;
        /**
         * Tempting player's yaw
         */
        private double yaw;
        /**
         * The player that is tempting the entity that is using this AI.
         */
        private ItemEntity temptingItem;
        private boolean isRunning;
        private int stealDelay = 0;

        public EntityAIGrabItemFromFloor(CreatureEntity temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared) {
            this.temptedEntity = temptedEntityIn;
            this.speed = speedIn;
            this.temptItem = temptItemIn;
            this.canGetScared = canGetScared;
            if (!(temptedEntityIn.getNavigator() instanceof PathNavigateGround)) {
                throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
            }
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            if (temptedEntity.getLastAttackedEntity() != null && canGetScared && stealDelay <= 0) {
                this.resetTask();
                return false;
            }
            if (!this.temptedEntity.getHeldItemMainhand().isEmpty()) {
                return false;
            }
            List<Entity> list = this.temptedEntity.getEntityWorld().getEntitiesWithinAABBExcludingEntity(temptedEntity, temptedEntity.getBoundingBox().expand(6D, 4D, 6D));
            if (this.stealDelay > 0) {
                --this.stealDelay;
                if (stealDelay == 0) {
                    temptedEntity.setLastAttackedEntity(null);
                }
                return false;
            } else if (!list.isEmpty()) {
                for (Entity entity : list) {
                    if (entity instanceof ItemEntity) {
                        ItemEntity item = (ItemEntity) entity;
                        ItemStack stack = item.getItem();
                        if (!stack.isEmpty() && this.isTempting(stack)) {
                            this.temptingItem = item;
                            return true;
                        }
                    }
                }
            }
            return false;
        }

        protected boolean isTempting(ItemStack stack) {
            if (!stack.isEmpty()) {
                for (ItemStack item : temptItem) {
                    if (item != null && item.getItem() == stack.getItem() && item.getMetadata() == stack.getMetadata()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.targetX = this.temptingItem.getPosX();
            this.targetY = this.temptingItem.getPosY();
            this.targetZ = this.temptingItem.getPosZ();
            this.isRunning = true;
        }

        /**
         * Resets the task
         */
        public void resetTask() {
            this.temptingItem = null;
            this.temptedEntity.getNavigator().clearPath();
            this.isRunning = false;
            if (canGetScared) {
                this.stealDelay = 50;
            }
        }

        /**
         * Updates the task
         */
        public void tick() {
            this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingItem, (float) (this.temptedEntity.getHorizontalFaceSpeed() + 20), (float) this.temptedEntity.getVerticalFaceSpeed());
            if (this.temptedEntity.getDistanceSq(this.temptingItem) < 1.0D) {
                this.temptedEntity.getNavigator().clearPath();
                ItemStack loot = temptingItem.getItem().copy();
                temptingItem.setDead();
                this.temptedEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, loot);
            } else {
                this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingItem, this.speed);
            }
        }

        /**
         * @see #isRunning
         */
        public boolean isRunning() {
            return this.isRunning;
        }
    }

    public class EntityAIStealFromPlayer extends Goal {
        /**
         * The entity using this AI that is tempted by the player.
         */
        private final CreatureEntity temptedEntity;
        private final double speed;
        private final Set<ItemStack> temptItem;
        private final boolean canGetScared;
        /**
         * X position of player tempting this mob
         */
        private double targetX;
        /**
         * Y position of player tempting this mob
         */
        private double targetY;
        /**
         * Z position of player tempting this mob
         */
        private double targetZ;
        /**
         * Tempting player's pitch
         */
        private double pitch;
        /**
         * Tempting player's yaw
         */
        private double yaw;
        /**
         * The player that is tempting the entity that is using this AI.
         */
        private PlayerEntity temptingPlayer;
        private boolean isRunning;
        private int stealDelay = 0;

        public EntityAIStealFromPlayer(CreatureEntity temptedEntityIn, double speedIn, Set<ItemStack> temptItemIn, boolean canGetScared) {
            this.temptedEntity = temptedEntityIn;
            this.speed = speedIn;
            this.temptItem = temptItemIn;
            this.canGetScared = canGetScared;
            if (!(temptedEntityIn.getNavigator() instanceof PathNavigateGround)) {
                throw new IllegalArgumentException("Unsupported mob type for TemptGoal");
            }
        }

        /**
         * Returns whether the Goal should begin execution.
         */
        public boolean shouldExecute() {
            if (temptedEntity.getLastAttackedEntity() != null && canGetScared && stealDelay <= 0) {
                this.resetTask();
                return false;
            }
            if (!this.temptedEntity.getHeldItemMainhand().isEmpty()) {
                return false;
            }
            this.temptingPlayer = this.temptedEntity.getEntityWorld().getClosestPlayer(this.temptedEntity, 10.0D);
            if (this.stealDelay > 0) {
                --this.stealDelay;
                if (stealDelay == 0) {
                    temptedEntity.setLastAttackedEntity(null);
                }
                return false;
            } else if (temptingPlayer != null) {
                for (int i = 0; i < this.temptingPlayer.inventory.getSizeInventory(); i++) {
                    ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
                    if (!item.isEmpty() && this.isTempting(item)) {
                        return true;
                    }
                }
            }
            return false;
        }

        protected boolean isTempting(ItemStack stack) {
            if (!stack.isEmpty()) {
                for (ItemStack item : temptItem) {
                    if (item != null && item.getItem() == stack.getItem() && item.getMetadata() == stack.getMetadata()) {
                        return true;
                    }
                }
            }
            return false;
        }

        /**
         * Returns whether an in-progress Goal should continue executing
         */
        public boolean shouldContinueExecuting() {
            return this.shouldExecute();
        }

        /**
         * Execute a one shot task or start executing a continuous task
         */
        public void startExecuting() {
            this.targetX = this.temptingPlayer.getPosX();
            this.targetY = this.temptingPlayer.getPosY();
            this.targetZ = this.temptingPlayer.getPosZ();
            this.isRunning = true;
        }

        /**
         * Resets the task
         */
        public void resetTask() {
            this.temptingPlayer = null;
            this.temptedEntity.getNavigator().clearPath();
            this.isRunning = false;
            if (canGetScared) {
                this.stealDelay = 100;
            }
        }

        /**
         * Updates the task
         */
        public void tick() {
            this.temptedEntity.getLookHelper().setLookPositionWithEntity(this.temptingPlayer, (float) (this.temptedEntity.getHorizontalFaceSpeed() + 20), (float) this.temptedEntity.getVerticalFaceSpeed());
            if (temptingPlayer.capabilities.isCreativeMode) return;
            if (this.temptedEntity.getDistanceSq(this.temptingPlayer) < 3.25D) {
                this.temptedEntity.getNavigator().clearPath();
                for (int i = 0; i < this.temptingPlayer.inventory.getSizeInventory(); i++) {
                    ItemStack item = this.temptingPlayer.inventory.getStackInSlot(i);
                    if (!item.isEmpty()) {
                        for (ItemStack itemstack : temptItem) {
                            if (itemstack != null && !itemstack.isEmpty() && itemstack.getItem() == item.getItem() && itemstack.getMetadata() == item.getMetadata()) {
                                MoCTools.playCustomSound(this.temptedEntity, SoundEvents.ENTITY_ITEM_PICKUP);
                                ItemStack loot = item.copy();
                                this.temptedEntity.setItemStackToSlot(EquipmentSlotType.MAINHAND, loot);
                                item.shrink(1);
                                return;
                            }
                        }
                    }
                }
            } else {
                this.temptedEntity.getNavigator().tryMoveToEntityLiving(this.temptingPlayer, this.speed);
            }
        }

        /**
         * @see #isRunning
         */
        public boolean isRunning() {
            return this.isRunning;
        }
    }
}
