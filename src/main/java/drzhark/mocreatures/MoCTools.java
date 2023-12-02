/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures;

import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.MoCEntityAnimal;
import drzhark.mocreatures.entity.ambient.MoCEntityMaggot;
import drzhark.mocreatures.entity.hostile.MoCEntityOgre;
import drzhark.mocreatures.entity.hostile.MoCEntitySilverSkeleton;
import drzhark.mocreatures.entity.inventory.MoCAnimalChest;
import drzhark.mocreatures.entity.item.MoCEntityThrowableRock;
import drzhark.mocreatures.entity.passive.MoCEntityHorse;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.entity.tameable.MoCEntityTameableAnimal;
import drzhark.mocreatures.init.MoCItems;
import drzhark.mocreatures.init.MoCSoundEvents;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageNameGUI;
import net.minecraft.block.Block;
import net.minecraft.block.BlockJukebox;
import net.minecraft.block.BlockJukebox.TileEntityJukebox;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.BlockState;
import net.minecraft.block.material.Material;
import net.minecraft.entity.Entity;
import net.minecraft.entity.MobEntity;
import net.minecraft.entity.item.EntityBoat;
import net.minecraft.entity.item.ItemEntity;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.entity.monster.EntitySlime;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityTameable;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.ServerPlayerEntity;
import net.minecraft.init.Blocks;
import net.minecraft.init.Effects;
import net.minecraft.init.Items;
import net.minecraft.inventory.EquipmentSlotType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemFood;
import net.minecraft.item.ItemSeeds;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundNBT;
import net.minecraft.pathfinding.Path;
import net.minecraft.potion.EffectInstance;
import net.minecraft.util.*;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.vector.Vector3d;
import net.minecraft.util.text.TextFormatting;
import net.minecraft.util.text.TranslationTextComponent;
import net.minecraft.world.Explosion;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.chunk.Chunk;
import net.minecraftforge.common.DimensionManager;
import net.minecraftforge.common.util.FakePlayerFactory;
import net.minecraftforge.event.ForgeEventFactory;
import net.minecraftforge.event.world.BlockEvent;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.eventhandler.Event;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import net.minecraftforge.fml.common.registry.EntityType;
import net.minecraftforge.fml.relauncher.Side;

import java.util.*;

public class MoCTools {

    /**
     * Spawns entities during world gen
     */
    public static void performCustomWorldGenSpawning(World world, Biome biome, int centerX, int centerZ, int diameterX, int diameterZ, Random random, List<Biome.MobSpawnInfo.Spawners> spawnList, LivingEntity.SpawnPlacementType placementType) {
        if (spawnList == null || spawnList.isEmpty()) return;
        while (random.nextFloat() < Math.min(biome.getSpawningChance() * MoCreatures.proxy.spawnMultiplier, 0.5F)) {
            Biome.MobSpawnInfo.Spawners spawnListEntry = WeightedRandom.getRandomItem(random, spawnList);
            int minCount = Math.min(spawnListEntry.minGroupCount, 1);
            int maxCount = Math.min(spawnListEntry.maxGroupCount, 6);
            int groupCount = minCount + random.nextInt(1 + maxCount - minCount);
            IEntityLivingData livingData = null;
            int xPos = centerX + random.nextInt(diameterX);
            int zPos = centerZ + random.nextInt(diameterZ);
            int xPosOrig = xPos;
            int zPosOrig = zPos;
            for (int i = 0; i < groupCount; i++) {
                boolean spawned = false;
                for (int j = 0; !spawned && j < 4; j++) {
                    BlockPos blockPos = MoCTools.getActualTopSolidOrLiquidBlock(world, new BlockPos(xPos, 0, zPos));
                    if (placementType == LivingEntity.SpawnPlacementType.IN_WATER) blockPos = blockPos.down();
                    if (WorldEntitySpawner.canCreatureTypeSpawnAtLocation(placementType, world, blockPos)) {
                        LivingEntity entityliving;
                        try {
                            entityliving = spawnListEntry.newInstance(world);
                        } catch (Exception exception) {
                            exception.printStackTrace();
                            continue;
                        }
                        if (ForgeEventFactory.canEntitySpawn(entityliving, world, xPos, blockPos.getY(), zPos, false) == Event.Result.DENY)
                            continue;
                        entityliving.setLocationAndAngles(xPos, blockPos.getY(), zPos, random.nextFloat() * 360.0F, 0.0F);
                        if (entityliving.isNotColliding()) {
                            livingData = entityliving.onInitialSpawn(world.getDifficultyForLocation(new BlockPos(entityliving)), livingData);
                            world.addEntity(entityliving);
                            spawned = true;
                        } else entityliving.setDead();
                    }
                    xPos += random.nextInt(5) - random.nextInt(5);
                    for (zPos += random.nextInt(5) - random.nextInt(5); xPos < centerX || xPos >= centerX + diameterX || zPos < centerZ || zPos >= centerZ + diameterX; zPos = zPosOrig + random.nextInt(5) - random.nextInt(5)) {
                        xPos = xPosOrig + random.nextInt(5) - random.nextInt(5);
                    }
                }
            }
        }
    }

    /**
     * {@link World#getTopSolidOrLiquidBlock} but actually returning top blocks AND liquid blocks.
     * Thanks for nothing, MCP!
     */
    public static BlockPos getActualTopSolidOrLiquidBlock(World world, BlockPos pos) {
        Chunk chunk = world.getChunk(pos);
        BlockPos blockPos;
        BlockPos blockPosDown;

        for (blockPos = new BlockPos(pos.getX(), chunk.getTopFilledSegment() + 16, pos.getZ()); blockPos.getY() >= 0; blockPos = blockPosDown) {
            blockPosDown = blockPos.down();
            BlockState blockStateDown = chunk.getBlockState(blockPosDown);

            if (blockStateDown.getMaterial().isLiquid() || blockStateDown.getMaterial().blocksMovement() && !blockStateDown.getBlock().isLeaves(blockStateDown, world, blockPosDown) && !blockStateDown.getBlock().isFoliage(world, blockPosDown)) {
                break;
            }
        }

        return blockPos;
    }

    /**
     * spawns tiny slimes
     */
    public static void spawnSlimes(World world, Entity entity) {
        if (!world.isRemote) {
            //changed so it only spawns 0 - 1 slime, as it now spawns also big slimes
            int var2 = 1 + world.rand.nextInt(1);

            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = ((float) i / 2 - 0.5F) * 1 / 4.0F;
                EntitySlime var6 = new EntitySlime(world);
                var6.setLocationAndAngles(entity.getPosX() + var4, entity.getPosY() + 0.5D, entity.getPosZ() + var5, world.rand.nextFloat() * 360.0F, 0.0F);
                world.addEntity(var6);
            }
        }
    }

    /**
     * Drops saddle
     */
    public static void dropSaddle(MoCEntityAnimal entity, World world) {
        if (!entity.getIsRideable() || world.isRemote) {
            return;
        }
        dropCustomItem(entity, world, new ItemStack(MoCItems.horsesaddle, 1));
        entity.setRideable(false);
    }

    /**
     * Drops item
     */
    public static void dropCustomItem(Entity entity, World world, ItemStack itemstack) {
        if (world.isRemote) {
            return;
        }

        ItemEntity entityitem = new ItemEntity(world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), itemstack);
        float f3 = 0.05F;
        entityitem.getMotion().getX() = (float) world.rand.nextGaussian() * f3;
        entityitem.getMotion().getY() = ((float) world.rand.nextGaussian() * f3) + 0.2F;
        entityitem.getMotion().getZ() = (float) world.rand.nextGaussian() * f3;
        world.addEntity(entityitem);
    }

    public static void bigSmack(Entity entity, Entity entity1, float force) {
        double d = entity.getPosX() - entity1.getPosX();
        double d1;
        for (d1 = entity.getPosZ() - entity1.getPosZ(); ((d * d) + (d1 * d1)) < 0.0001D; d1 = (entity.world.rand.nextDouble() - entity.world.rand.nextDouble()) * 0.01D) {
            d = (entity.world.rand.nextDouble() - entity.world.rand.nextDouble()) * 0.01D;
        }

        float f = MathHelper.sqrt((d * d) + (d1 * d1));
        entity1.getMotion().getX() /= 2D;
        entity1.getMotion().getY() /= 2D;
        entity1.getMotion().getZ() /= 2D;
        entity1.getMotion().getX() -= (d / f) * force;
        entity1.getMotion().getY() += force;
        entity1.getMotion().getZ() -= (d1 / f) * force;
        if (entity1.getMotion().getY() > force) {
            entity1.getMotion().getY() = force;
        }
    }

    public static void buckleMobs(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getEntityBoundingBox().grow(dist, 2D, dist));
        for (Entity entitytarget : list) {
            if (!(entitytarget instanceof LivingEntity) || (entityattacker.isBeingRidden() && entitytarget == entityattacker.getRidingEntity())) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigSmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
        }
    }

    public static void buckleMobsNotPlayers(LivingEntity entityattacker, Double dist, World world) {
        List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(entityattacker, entityattacker.getEntityBoundingBox().grow(dist, 2D, dist));
        for (Entity entitytarget : list) {
            if (!(entitytarget instanceof LivingEntity) || entityattacker.isBeingRidden() && entitytarget == entityattacker.getRidingEntity()) {
                continue;
            }

            entitytarget.attackEntityFrom(DamageSource.causeMobDamage(entityattacker), 2);
            bigSmack(entityattacker, entitytarget, 0.6F);
            playCustomSound(entityattacker, MoCSoundEvents.ENTITY_GENERIC_TUD);
        }
    }

    public static void spawnNearPlayer(PlayerEntity player, int entityId, int numberToSpawn) {
        WorldServer world = FMLCommonHandler.instance().getMinecraftServerInstance().getWorld(player.world.provider.getDimensionType().getId());
        for (int i = 0; i < numberToSpawn; i++) {
            LivingEntity entityliving = null;
            try {
                Class<? extends LivingEntity> entityClass = MoCreatures.instaSpawnerMap.get(entityId);
                entityliving = entityClass.getConstructor(World.class).newInstance(world);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (entityliving != null) {
                entityliving.setLocationAndAngles(player.getPosX() - 1, player.getPosY(), player.getPosZ() - 1, player.rotationYaw, player.rotationPitch);
                world.addEntity(entityliving);
            }
        }
    }

    public static void playCustomSound(Entity entity, SoundEvent customSound) {
        playCustomSound(entity, customSound, 1.0F);
    }

    public static void playCustomSound(Entity entity, SoundEvent customSound, float volume) {
        entity.playSound(customSound, volume, 1.0F + ((entity.world.rand.nextFloat() - entity.world.rand.nextFloat()) * 0.2F));
    }

    public static TileEntityJukebox nearJukeBoxRecord(Entity entity, Double dist) {
        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(dist, dist / 2D, dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.world.getBlockState(pos);
                    if (!entity.world.isAirBlock(pos) && blockstate.getBlock() instanceof BlockJukebox) {
                        return (TileEntityJukebox) entity.world.getTileEntity(pos);
                    }
                }
            }
        }
        return null;
    }

    public static void checkForTwistedEntities(World world) {
        for (int l = 0; l < world.loadedEntityList.size(); l++) {
            Entity entity = world.loadedEntityList.get(l);
            if (entity instanceof LivingEntity) {
                LivingEntity twisted = (LivingEntity) entity;
                if (twisted.deathTime > 0 && twisted.getRidingEntity() == null && twisted.getHealth() > 0) {
                    twisted.deathTime = 0;
                }
            }
        }
    }

    public static double getSqDistanceTo(Entity entity, double i, double j, double k) {
        double l = entity.getPosX() - i;
        double i1 = entity.getPosY() - j;
        double j1 = entity.getPosZ() - k;
        return Math.sqrt((l * l) + (i1 * i1) + (j1 * j1));
    }

    public static int[] returnNearestMaterialCoord(Entity entity, Material material, Double double1, Double yOff) {
        double shortestDistance = -1D;
        double distance;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(double1, yOff, double1);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.world.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getMaterial() == material)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.getPosX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getPosZ() > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[]{x, y, z});
    }

    public static int[] returnNearestBlockCoord(Entity entity, Block block1, Double dist) {
        double shortestDistance = -1D;
        double distance;
        int x = -9999;
        int y = -1;
        int z = -1;

        AxisAlignedBB axisalignedbb = entity.getEntityBoundingBox().grow(dist);
        int i = MathHelper.floor(axisalignedbb.minX);
        int j = MathHelper.floor(axisalignedbb.maxX + 1.0D);
        int k = MathHelper.floor(axisalignedbb.minY);
        int l = MathHelper.floor(axisalignedbb.maxY + 1.0D);
        int i1 = MathHelper.floor(axisalignedbb.minZ);
        int j1 = MathHelper.floor(axisalignedbb.maxZ + 1.0D);
        for (int k1 = i; k1 < j; k1++) {
            for (int l1 = k; l1 < l; l1++) {
                for (int i2 = i1; i2 < j1; i2++) {
                    BlockPos pos = new BlockPos(k1, l1, i2);
                    BlockState blockstate = entity.world.getBlockState(pos);
                    if ((blockstate.getBlock() != Blocks.AIR) && (blockstate.getBlock() == block1)) {
                        distance = getSqDistanceTo(entity, k1, l1, i2);
                        if (shortestDistance == -1D) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }

                        if (distance < shortestDistance) {
                            x = k1;
                            y = l1;
                            z = i2;
                            shortestDistance = distance;
                        }
                    }
                }
            }
        }

        if (entity.getPosX() > x) {
            x -= 2;
        } else {
            x += 2;
        }
        if (entity.getPosZ() > z) {
            z -= 2;
        } else {
            z += 2;
        }
        return (new int[]{x, y, z});
    }

    public static BlockPos getTreeTop(World world, Entity entity, int range) {
        BlockPos entityPos = new BlockPos(entity.getPosX(), entity.getPosY() + entity.getEyeHeight(), entity.getPosZ());
        // Search for wood blocks around the entity within the specified range
        for (int x = -range; x <= range; x++) {
            for (int y = -range; y <= range; y++) {
                for (int z = -range; z <= range; z++) {
                    BlockPos pos = entityPos.add(x, y, z);
                    BlockState blockState = world.getBlockState(pos);
                    if (blockState.getMaterial() == Material.WOOD) {
                        // Iterate upwards from the wood block to find the topmost leaf block or air block
                        for (int yOffset = 1; yOffset < 256; yOffset++) {
                            BlockPos currentPos = pos.up(yOffset);
                            BlockState currentState = world.getBlockState(currentPos);
                            if (currentState.getMaterial() == Material.AIR) {
                                // Found the topmost leaf block or air block, return its position
                                return currentPos;
                            } else if (currentState.getMaterial() != Material.LEAVES) {
                                // Found a block that is not leaves or air, stop searching
                                break;
                            }
                        }
                    }
                }
            }
        }
        // Return null if no suitable block was found
        return null;
    }

    public static void moveCreatureToXYZ(CreatureEntity movingEntity, int x, int y, int z, float f) {
        Path pathEntity = movingEntity.getNavigator().getPathToXYZ(x, y, z);
        if (pathEntity != null) {
            movingEntity.getNavigator().setPath(pathEntity, f);
        }
    }

    public static void moveToWater(CreatureEntity entity) {
        int[] ai = MoCTools.returnNearestMaterialCoord(entity, Material.WATER, 20D, 2D);
        if (ai[0] > -1000) {
            MoCTools.moveCreatureToXYZ(entity, ai[0], ai[1], ai[2], 24F);
        }
    }

    /**
     * Gives angles in the range 0-360 i.e. 361 will be returned like 1
     */
    public static float realAngle(float origAngle) {
        return origAngle % 360F;
    }

    public static double waterSurfaceAtGivenPosition(double getPosX(),

    double getPosY(),

    double getPosZ(),World worldIn)

    {
        int i = MathHelper.floor(getPosX());
        int j = MathHelper.floor(getPosY());
        int k = MathHelper.floor(getPosZ());
        BlockState blockstate = worldIn.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = worldIn.getBlockState(new BlockPos(i, j + x, k));
                if (blockstate.getBlock() == Blocks.AIR || blockstate.getMaterial() != Material.WATER) {
                    return j + x;
                }
            }
        }
        return 0F;
    }

    public static double waterSurfaceAtGivenEntity(Entity entity) {
        return waterSurfaceAtGivenPosition(entity.getPosX(), entity.getPosY(), entity.getPosZ(), entity.world);
    }

    public static float distanceToSurface(double getPosX(),

    double getPosY(),

    double getPosZ(),World worldIn)

    {
        int i = MathHelper.floor(getPosX());
        int j = MathHelper.floor(getPosY());
        int k = MathHelper.floor(getPosZ());
        BlockState blockstate = worldIn.getBlockState(new BlockPos(i, j, k));
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == Material.WATER) {
            for (int x = 1; x < 64; x++) {
                blockstate = worldIn.getBlockState(new BlockPos(i, j + x, k));
                if (blockstate.getBlock() == Blocks.AIR || blockstate.getMaterial() != Material.WATER) {
                    return x;
                }
            }
        }
        return 0F;
    }

    public static int distanceToFloor(Entity entity) {
        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(entity.getPosY());
        int k = MathHelper.floor(entity.getPosZ());
        for (int x = 0; x < 64; x++) {
            Block block = entity.world.getBlockState(new BlockPos(i, j - x, k)).getBlock();
            if (block != Blocks.AIR) {
                return x;
            }
        }

        return 0;
    }

    public static String biomeName(World world, BlockPos pos) {
        BiomeProvider biomeProvider = world.getBiomeProvider();
        Biome biome = biomeProvider.getBiome(pos);
        return biome.biomeName;
    }

    public static Biome biomeKind(World world, BlockPos pos) {
        return world.getBiome(pos);
    }

    public static void destroyDrops(Entity entity, double d) {

        if (!MoCreatures.proxy.destroyDrops) {
            return;
        }

        List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().grow(d));

        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem = (ItemEntity) entity1;
            if (entityitem.getAge() < 50) {
                entityitem.setDead();
            }
        }
    }

    public static boolean mobGriefing(World world) {
        return world.getGameRules().getBoolean("mobGriefing");
    }

    public static void destroyBlast(Entity entity, double d, double d1, double d2, float f, boolean flag) {
        PlayerEntity player = entity instanceof PlayerEntity ? (PlayerEntity) entity : null;
        entity.world.playSound(player, d, d1, d2, MoCSoundEvents.ENTITY_GENERIC_DESTROY, SoundCategory.HOSTILE, 4F, (1.0F + ((entity.world.rand.nextFloat() - entity.world.rand.nextFloat()) * 0.2F)) * 0.7F);

        boolean mobGriefing = mobGriefing(entity.world);

        HashSet<BlockPos> hashset = new HashSet<>();
        float f1 = f;
        int i = 16;
        for (int j = 0; j < i; j++) {
            for (int l = 0; l < i; l++) {
                label0:
                for (int j1 = 0; j1 < i; j1++) {
                    if ((j != 0) && (j != (i - 1)) && (l != 0) && (l != (i - 1)) && (j1 != 0) && (j1 != (i - 1))) {
                        continue;
                    }
                    double d3 = ((j / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d4 = ((l / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d5 = ((j1 / (i - 1.0F)) * 2.0F) - 1.0F;
                    double d6 = Math.sqrt((d3 * d3) + (d4 * d4) + (d5 * d5));
                    d3 /= d6;
                    d4 /= d6;
                    d5 /= d6;
                    float f2 = f * (0.7F + (entity.world.rand.nextFloat() * 0.6F));
                    double d8 = d;
                    double d10 = d1;
                    double d12 = d2;
                    float f3 = 0.3F;
                    float f4 = 5F;
                    do {
                        if (f2 <= 0.0F) {
                            continue label0;
                        }
                        int k5 = MathHelper.floor(d8);
                        int l5 = MathHelper.floor(d10);
                        int i6 = MathHelper.floor(d12);
                        BlockPos pos = new BlockPos(k5, l5, i6);
                        BlockState blockstate = entity.world.getBlockState(pos);
                        if (blockstate.getBlock() != Blocks.AIR) {
                            f4 = blockstate.getBlockHardness(entity.world, pos);
                            f2 -= (blockstate.getBlock().getExplosionResistance(entity) + 0.3F) * (f3 / 10F);
                        }
                        if ((f2 > 0.0F) && (d10 > entity.getPosY()) && (f4 < 3F)) {
                            hashset.add(pos);
                        }
                        d8 += d3 * f3;
                        d10 += d4 * f3;
                        d12 += d5 * f3;
                        f2 -= f3 * 0.75F;
                    } while (true);
                }

            }

        }

        f *= 2.0F;
        if (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) {
            int k = MathHelper.floor(d - f - 1.0D);
            int i1 = MathHelper.floor(d + f + 1.0D);
            int k1 = MathHelper.floor(d1 - f - 1.0D);
            int l1 = MathHelper.floor(d1 + f + 1.0D);
            int i2 = MathHelper.floor(d2 - f - 1.0D);
            int j2 = MathHelper.floor(d2 + f + 1.0D);
            List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, new AxisAlignedBB(k, k1, i2, i1, l1, j2));
            Vector3d vec3d = new Vector3d(d, d1, d2);
            for (Entity entity1 : list) {
                double d7 = entity1.getDistance(d, d1, d2) / f;
                if (d7 > 1.0D) {
                    continue;
                }
                double d9 = entity1.getPosX() - d;
                double d11 = entity1.getPosY() - d1;
                double d13 = entity1.getPosZ() - d2;
                double d15 = MathHelper.sqrt((d9 * d9) + (d11 * d11) + (d13 * d13));
                d9 /= d15;
                d11 /= d15;
                d13 /= d15;
                double d17 = entity.world.getBlockDensity(vec3d, entity1.getEntityBoundingBox());
                double d19 = (1.0D - d7) * d17;

                //attacks entities in server
                if (!(entity1 instanceof MoCEntityOgre)) {
                    entity1.attackEntityFrom(DamageSource.GENERIC, (int) (((((d19 * d19) + d19) / 2D) * 3D * f) + 1.0D));
                    entity1.getMotion().getX() += d9 * d19;
                    entity1.getMotion().getY() += d11 * d19;
                    entity1.getMotion().getZ() += d13 * d19;
                }
            }
        }

        f = f1;
        ArrayList<BlockPos> arraylist = new ArrayList<>(hashset);

        for (int l2 = arraylist.size() - 1; l2 >= 0; l2--) {
            BlockPos chunkposition = arraylist.get(l2);
            BlockState blockstate = entity.world.getBlockState(chunkposition);
            for (int j5 = 0; j5 < 5; j5++) {
                double d14 = chunkposition.getX() + entity.world.rand.nextFloat();
                double d16 = chunkposition.getY() + entity.world.rand.nextFloat();
                double d18 = chunkposition.getZ() + entity.world.rand.nextFloat();
                double d20 = d14 - d;
                double d22 = d16 - d1;
                double d23 = d18 - d2;
                double d24 = MathHelper.sqrt((d20 * d20) + (d22 * d22) + (d23 * d23));
                d20 /= d24;
                d22 /= d24;
                d23 /= d24;
                double d25 = 0.5D / ((d24 / f) + 0.10000000000000001D);
                d25 *= (entity.world.rand.nextFloat() * entity.world.rand.nextFloat()) + 0.3F;
                d25--;
                d20 *= d25;
                d22 *= d25 - 1.0D;
                d23 *= d25;

                /*
                  shows explosion on clients!
                 */
                if (FMLCommonHandler.instance().getEffectiveSide() == Side.CLIENT) {
                    entity.world.addParticle(ParticleTypes.EXPLOSION_NORMAL, (d14 + (d)) / 2D, (d16 + (d1)) / 2D, (d18 + (d2)) / 2D, d20, d22, d23);
                    entity.getMotion().getX() -= 0.0010000000474974511D;
                    entity.getMotion().getY() -= 0.0010000000474974511D;
                }

            }

            //destroys blocks on server!
            if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && blockstate.getBlock() != Blocks.AIR) {
                BlockEvent.BreakEvent event = null;
                if (!entity.world.isRemote) {
                    try {
                        event = new BlockEvent.BreakEvent(entity.world, chunkposition, blockstate, FakePlayerFactory.get(DimensionManager.getWorld(entity.world.provider.getDimensionType().getId()), MoCreatures.MOCFAKEPLAYER));
                    } catch (Throwable ignored) {
                    }
                }
                if (event != null && !event.isCanceled()) {
                    blockstate.getBlock().dropBlockAsItemWithChance(entity.world, chunkposition, blockstate, 0.3F, 1);
                    entity.world.setBlockToAir(chunkposition);
                    // pass explosion instance to fix BlockTNT NPEs
                    Explosion explosion = new Explosion(entity.world, entity, chunkposition.getX(), chunkposition.getY(), chunkposition.getZ(), 3f, false, false);
                    blockstate.getBlock().onBlockExploded(entity.world, chunkposition, explosion);
                }
            }
        }

        //sets world on fire on server
        if (mobGriefing && (FMLCommonHandler.instance().getEffectiveSide() == Side.SERVER) && flag) {
            for (int i3 = arraylist.size() - 1; i3 >= 0; i3--) {
                BlockPos chunkposition1 = arraylist.get(i3);
                BlockState blockstate = entity.world.getBlockState(chunkposition1);
                if ((blockstate.getBlock() == Blocks.AIR) && (entity.world.rand.nextInt(8) == 0)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event = new BlockEvent.BreakEvent(entity.world, chunkposition1, blockstate, FakePlayerFactory.get((WorldServer) entity.world, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.world.setBlockState(chunkposition1, Blocks.FIRE.getDefaultState(), 3);
                    }
                }
            }
        }
    }

    public static void updatePlayerArmorEffects(PlayerEntity player) {
        if (!MoCreatures.proxy.armorSetEffects) return;

        Item boots = player.getItemStackFromSlot(EquipmentSlotType.FEET).getItem(); // Boots
        Item legs = player.getItemStackFromSlot(EquipmentSlotType.LEGS).getItem(); // Leggings
        Item plate = player.getItemStackFromSlot(EquipmentSlotType.CHEST).getItem(); // Chestplate
        Item helmet = player.getItemStackFromSlot(EquipmentSlotType.HEAD).getItem(); // Helmet

        // Cave Scorpion Armor Set Effect - Night Vision
        if (boots == MoCItems.scorpBootsCave && legs == MoCItems.scorpLegsCave && plate == MoCItems.scorpPlateCave && helmet == MoCItems.scorpHelmetCave) {
            player.addPotionEffect(new EffectInstance(Effects.NIGHT_VISION, 300, 0));
            return;
        }

        // Fire Scorpion Armor Set Effect - Fire Resistance
        if (boots == MoCItems.scorpBootsNether && legs == MoCItems.scorpLegsNether && plate == MoCItems.scorpPlateNether && helmet == MoCItems.scorpHelmetNether) {
            player.addPotionEffect(new EffectInstance(Effects.FIRE_RESISTANCE, 300, 0));
            return;
        }

        // Frost Scorpion Armor Set Effect - Resistance
        if (boots == MoCItems.scorpBootsFrost && legs == MoCItems.scorpLegsFrost && plate == MoCItems.scorpPlateFrost && helmet == MoCItems.scorpHelmetFrost) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 300, 0));
            return;
        }

        // Dirt Scorpion Armor Set Effect - Health Boost
        if (boots == MoCItems.scorpBootsDirt && legs == MoCItems.scorpLegsDirt && plate == MoCItems.scorpPlateDirt && helmet == MoCItems.scorpHelmetDirt) {
            player.addPotionEffect(new EffectInstance(Effects.HEALTH_BOOST, 300, 1));
        }

        // Undead Scorpion Armor Set Effect - Strength
        if (boots == MoCItems.scorpBootsUndead && legs == MoCItems.scorpLegsUndead && plate == MoCItems.scorpPlateUndead && helmet == MoCItems.scorpHelmetUndead) {
            player.addPotionEffect(new EffectInstance(Effects.STRENGTH, 300, 0));
        }
    }

    public static BlockState destroyRandomBlockWithIBlockState(Entity entity, double distance) {
        int l = (int) (distance * distance * distance);
        for (int i = 0; i < l; i++) {
            int x = (int) (entity.getPosX() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int y = (int) (entity.getPosY() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            int z = (int) (entity.getPosZ() + entity.world.rand.nextInt((int) (distance)) - (int) (distance / 2));
            BlockPos pos = new BlockPos(MathHelper.floor(x), MathHelper.floor(y), MathHelper.floor(z));
            BlockState stateAbove = entity.world.getBlockState(pos.up());
            BlockState stateTarget = entity.world.getBlockState(pos);

            if (pos.getY() == (int) entity.getPosY() - 1D && (pos.getX() == (int) Math.floor(entity.getPosX()) && pos.getZ() == (int) Math.floor(entity.getPosZ()))) {
                continue;
            }
            if (stateTarget.getBlock() != Blocks.AIR && stateTarget.getBlock() != Blocks.WATER && stateTarget.getBlock() != Blocks.BEDROCK && stateAbove.getBlock() == Blocks.AIR) // ignore bedrock
            {
                if (mobGriefing(entity.world)) {
                    BlockEvent.BreakEvent event = null;
                    if (!entity.world.isRemote) {
                        event = new BlockEvent.BreakEvent(entity.world, pos, stateTarget, FakePlayerFactory.get((WorldServer) entity.world, MoCreatures.MOCFAKEPLAYER));
                    }
                    if (event != null && !event.isCanceled()) {
                        entity.world.setBlockToAir(pos);

                    } else {
                        stateTarget = null;
                    }
                }
                if (stateTarget != null) {
                    return stateTarget;
                }
            }
        }
        return null;
    }

    public static BlockPos getRandomSurfaceBlockPos(Entity entity, int distance) {
        BlockPos pos = entity.getPosition();
        World world = entity.getEntityWorld();

        int x = pos.getX() + world.rand.nextInt(distance * 2 + 1) - distance;
        int z = pos.getZ() + world.rand.nextInt(distance * 2 + 1) - distance;
        int y = world.getHeight(new BlockPos(x, 0, z)).getY() - 1;

        return new BlockPos(x, y, z);
    }

    /**
     * Method called to tame an entity, it will check that the player has slots
     * for taming, increase the taming count of the player, add the
     * player.getName() as the owner of the entity, and name the entity.
     */
    public static boolean tameWithName(PlayerEntity ep, IMoCTameable storedCreature) {
        if (ep == null) {
            return false;
        }

        if (MoCreatures.proxy.enableOwnership) {
            if (storedCreature == null) {
                ep.sendMessage(new TranslationTextComponent(TextFormatting.RED + "ERROR:" + TextFormatting.WHITE + "The stored creature is NULL and could not be created. Report to admin."));
                return false;
            }
            int max;
            max = MoCreatures.proxy.maxTamed;
            // only check count for new pets as owners may be changing the name
            if (!MoCreatures.instance.mapData.isExistingPet(ep.getUniqueID(), storedCreature)) {
                int count = MoCTools.numberTamedByPlayer(ep);
                if (isThisPlayerAnOP(ep)) {
                    max = MoCreatures.proxy.maxOPTamed;
                }
                if (count >= max) {
                    String message = "\2474" + ep.getName() + " can not tame more creatures, limit of " + max + " reached";
                    ep.sendMessage(new TranslationTextComponent(message));
                    return false;
                }
            }
        }

        storedCreature.setOwnerId(ep.getUniqueID()); // ALWAYS SET OWNER. Required for our new pet save system.
        if (MoCreatures.proxy.alwaysNamePets) {
            MoCMessageHandler.INSTANCE.sendTo(new MoCMessageNameGUI(((Entity) storedCreature).getEntityId()), (ServerPlayerEntity) ep);
        }
        storedCreature.setTamed(true);
        // Required to update petId data for pet amulets
        if (MoCreatures.instance.mapData != null && storedCreature.getOwnerPetId() == -1) {
            MoCreatures.instance.mapData.updateOwnerPet(storedCreature);
        }
        return true;
    }

    public static int numberTamedByPlayer(PlayerEntity ep) {
        if (MoCreatures.instance.mapData != null && MoCreatures.instance.mapData.getPetData(ep.getUniqueID()) != null) {
            return MoCreatures.instance.mapData.getPetData(ep.getUniqueID()).getTamedList().size();
        }
        return 0;
    }

    /**
     * Destroys blocks in front of entity
     *
     * @param distance used to calculate the distance where the target block is
     *                 located
     * @param strength int 1 - 3. Checked against block hardness, also used to
     *                 calculate how many blocks are recovered
     * @param height   how many rows of blocks are destroyed in front of the
     *                 entity
     * @return the count of blocks destroyed
     */
    public static int destroyBlocksInFront(Entity entity, double distance, int strength, int height) {
        if (strength == 0) {
            return 0;
        }
        int count = 0;
        double newPosX = entity.getPosX() - (distance * Math.cos((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosZ = entity.getPosZ() - (distance * Math.sin((MoCTools.realAngle(entity.rotationYaw - 90F)) / 57.29578F));
        double newPosY = entity.getPosY();
        int x = MathHelper.floor(newPosX);
        int y = MathHelper.floor(newPosY);
        int z = MathHelper.floor(newPosZ);

        for (int i = 0; i < height; i++) {
            BlockPos pos = new BlockPos(x, y + i, z);
            BlockState blockstate = entity.world.getBlockState(pos);
            if (blockstate.getBlock() != Blocks.AIR && blockstate.getBlockHardness(entity.world, pos) <= strength) {
                BlockEvent.BreakEvent event = null;
                if (!entity.world.isRemote) {
                    event = new BlockEvent.BreakEvent(entity.world, pos, blockstate, FakePlayerFactory.get((WorldServer) entity.world, MoCreatures.MOCFAKEPLAYER));
                }
                if (event != null && !event.isCanceled()) {
                    blockstate.getBlock().dropBlockAsItemWithChance(entity.world, pos, blockstate, 0.20F * strength, 1);
                    entity.world.setBlockToAir(pos);
                    if (entity.world.rand.nextInt(3) == 0) {
                        playCustomSound(entity, MoCSoundEvents.ENTITY_GOLEM_WALK);
                        count++; //only counts recovered blocks
                    }
                }

            }
        }
        return count;
    }

    public static void dropInventory(Entity entity, MoCAnimalChest animalchest) {
        if (animalchest == null || entity.world.isRemote) {
            return;
        }

        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(entity.getEntityBoundingBox().minY);
        int k = MathHelper.floor(entity.getPosZ());

        for (int l = 0; l < animalchest.getSizeInventory(); l++) {
            ItemStack itemstack = animalchest.getStackInSlot(l);
            if (itemstack.isEmpty()) {
                continue;
            }
            float f = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f1 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f2 = (entity.world.rand.nextFloat() * 0.8F) + 0.1F;
            float f3 = 0.05F;

            ItemEntity entityitem = new ItemEntity(entity.world, i + f, j + f1, k + f2, itemstack);
            entityitem.getMotion().getX() = ((float) entity.world.rand.nextGaussian() * f3);
            entityitem.getMotion().getY() = (((float) entity.world.rand.nextGaussian() * f3) + 0.2F);
            entityitem.getMotion().getZ() = ((float) entity.world.rand.nextGaussian() * f3);
            entity.world.addEntity(entityitem);
            animalchest.setInventorySlotContents(l, ItemStack.EMPTY);
        }
    }

    /**
     * Drops an amulet with the stored information of the entity passed
     */
    public static void dropHorseAmulet(MoCEntityTameableAnimal entity) {
        if (!entity.world.isRemote) {
            ItemStack stack = getProperAmulet(entity);
            if (stack == null) {
                return;
            }
            if (stack.getTag() == null) {
                stack.put(new CompoundNBT());
            }
            CompoundNBT nbtt = stack.getTag();
            UUID ownerId = entity.getOwnerId();
            PlayerEntity epOwner = null;
            if (ownerId != null) {
                epOwner = entity.world.getPlayerEntityByUUID(entity.getOwnerId());
            }

            try {
                nbtt.putString("SpawnClass", "WildHorse");
                nbtt.putFloat("Health", entity.getHealth());
                nbtt.putInt("Edad", entity.getAge());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putBoolean("Rideable", entity.getIsRideable());
                nbtt.putInt("Armor", entity.getArmorType());
                nbtt.putInt("CreatureType", entity.getTypeMoC());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putString("OwnerName", epOwner != null ? epOwner.getName() : "");
                if (entity.getOwnerId() != null) {
                    nbtt.putUniqueId("OwnerUUID", entity.getOwnerId());
                }
                nbtt.putInt("PetId", entity.getOwnerPetId());
                int amuletType = 1;
                if (stack.getItem() == MoCItems.petamuletfull) {
                    amuletType = 2;
                } else if (stack.getItem() == MoCItems.amuletghostfull) {
                    amuletType = 3;
                }
                nbtt.putBoolean("Ghost", amuletType == 3);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (epOwner != null && epOwner.inventory.getFirstEmptyStack() != -1) // don't attempt to set if player inventory is full
            {
                epOwner.inventory.addItemStackToInventory(stack);
            } else {
                ItemEntity entityitem = new ItemEntity(entity.world, entity.getPosX(), entity.getPosY(), entity.getPosZ(), stack);
                entityitem.setPickupDelay(20);
                entity.world.addEntity(entityitem);
            }
        }
    }

    /**
     * Drops a new amulet/fishnet with the stored information of the entity
     */
    public static void dropAmulet(IMoCEntity entity, int amuletType, PlayerEntity player) {
        if (!player.world.isRemote) {
            ItemStack stack = new ItemStack(MoCItems.fishnetfull, 1, 0);
            if (amuletType == 2) {
                stack = new ItemStack(MoCItems.petamuletfull, 1, 0);
            }
            if (amuletType == 3) {
                stack = new ItemStack(MoCItems.amuletghostfull, 1, 0);
            }
            if (stack.getTag() == null) {
                stack.put(new CompoundNBT());
            }
            CompoundNBT nbtt = stack.getTag();
            try {
                final EntityType entry = EntityRegistry.getEntry((Class<? extends Entity>) entity.getClass());
                final String petClass = entry.getName().replace(MoCConstants.MOD_PREFIX, "");
                nbtt.putString("SpawnClass", petClass);
                nbtt.putUniqueId("OwnerUUID", player.getUniqueID());
                nbtt.putString("OwnerName", player.getName());
                nbtt.putFloat("Health", ((LivingEntity) entity).getHealth());
                nbtt.putInt("Edad", entity.getAge());
                nbtt.putString("Name", entity.getPetName());
                nbtt.putInt("CreatureType", entity.getTypeMoC());
                nbtt.putBoolean("Adult", entity.getIsAdult());
                nbtt.putInt("PetId", entity.getOwnerPetId());
                nbtt.putBoolean("Ghost", amuletType == 3);
            } catch (Exception e) {
                e.printStackTrace();
            }

            if (!player.inventory.addItemStackToInventory(stack)) {
                ItemEntity entityitem = new ItemEntity(((LivingEntity) entity).world, ((LivingEntity) entity).getPosX(), ((LivingEntity) entity).getPosY(), ((LivingEntity) entity).getPosZ(), stack);
                entityitem.setPickupDelay(20);
                ((LivingEntity) entity).world.addEntity(entityitem);
            }
        }
    }

    /**
     * Returns the right full amulet based on the MoCEntityAnimal passed
     */
    public static ItemStack getProperAmulet(MoCEntityAnimal entity) {
        if (entity instanceof MoCEntityHorse) {
            if (entity.getTypeMoC() == 26 || entity.getTypeMoC() == 27 || entity.getTypeMoC() == 28) {
                return new ItemStack(MoCItems.amuletbonefull, 1, 0);
            }
            if (entity.getTypeMoC() > 47 && entity.getTypeMoC() < 60) {
                return new ItemStack(MoCItems.amuletfairyfull, 1, 0);
            }
            if (entity.getTypeMoC() == 39 || entity.getTypeMoC() == 40) {
                return new ItemStack(MoCItems.amuletpegasusfull, 1, 0);
            }
            if (entity.getTypeMoC() == 21 || entity.getTypeMoC() == 22) {
                return new ItemStack(MoCItems.amuletghostfull, 1, 0);
            }
        }
        return null;
    }

    public static boolean isThisPlayerAnOP(PlayerEntity player) {
        if (player.world.isRemote) {
            return false;
        }

        return FMLCommonHandler.instance().getMinecraftServerInstance().getPlayerList().canSendCommands(player.getGameProfile());
    }

    public static void spawnMaggots(World world, Entity entity) {
        if (!world.isRemote) {
            int var2 = 1 + world.rand.nextInt(4);
            for (int i = 0; i < var2; ++i) {
                float var4 = (i % 2 - 0.5F) * 1 / 4.0F;
                float var5 = ((float) i / 2 - 0.5F) * 1 / 4.0F;
                MoCEntityMaggot maggot = new MoCEntityMaggot(world);
                maggot.setLocationAndAngles(entity.getPosX() + var4, entity.getPosY() + 0.5D, entity.getPosZ() + var5, world.rand.nextFloat() * 360.0F, 0.0F);
                world.addEntity(maggot);
            }
        }
    }

    public static void setPathToEntity(LivingEntity creatureToMove, Entity entityTarget, float distance) {
        Path pathentity = creatureToMove.getNavigator().getPathToEntityLiving(entityTarget);
        if (pathentity != null && distance < 12F) {
            creatureToMove.getNavigator().setPath(pathentity, 1D);
        }
    }

    public static void runLikeHell(LivingEntity runningEntity, Entity boogey) {
        Random rand = runningEntity.getRNG();

        double d = runningEntity.getPosX() - boogey.getPosX();
        double d1 = runningEntity.getPosZ() - boogey.getPosZ();
        double d2 = Math.atan2(d, d1);
        d2 += (rand.nextFloat() - rand.nextFloat()) * 0.75D;
        double d3 = runningEntity.getPosX() + (Math.sin(d2) * 8D);
        double d4 = runningEntity.getPosZ() + (Math.cos(d2) * 8D);

        int i = MathHelper.floor(d3);
        int j = MathHelper.floor(runningEntity.getEntityBoundingBox().minY);
        int k = MathHelper.floor(d4);

        for (int l = 0; l < 16; l++) {
            int i1 = i + rand.nextInt(4) - rand.nextInt(4);
            int j1 = j + rand.nextInt(3) - rand.nextInt(3);
            int k1 = k + rand.nextInt(4) - rand.nextInt(4);
            BlockPos pos = new BlockPos(i1, j1, k1);

            if (j1 > 4 && ((runningEntity.world.getBlockState(pos).getBlock() == Blocks.AIR || runningEntity.world.getBlockState(pos).getBlock() == Blocks.SNOW) && runningEntity.world.getBlockState(pos.down()).getBlock() != Blocks.AIR)) {
                runningEntity.getNavigator().tryMoveToXYZ(i1, j1, k1, 1.5D);
                break;
            }
        }
    }

    /**
     * Finds a near vulnerable player and poisons it if the player is in the
     * water and not riding anything
     *
     * @param needsToBeInWater: the target needs to be in water for poison to be
     *                          successful?
     * @return true if was able to poison the player
     */
    public static boolean findNearPlayerAndPoison(Entity poisoner, boolean needsToBeInWater) {
        PlayerEntity entityplayertarget = poisoner.world.getClosestPlayer(poisoner, 2D);
        if (entityplayertarget != null && (!needsToBeInWater || entityplayertarget.isInWater()) && poisoner.getDistance(entityplayertarget) < 2.0F && !entityplayertarget.capabilities.disableDamage && !(entityplayertarget.getRidingEntity() instanceof EntityBoat)) {
            //don't poison players on boats
            entityplayertarget.addPotionEffect(new EffectInstance(Effects.POISON, 120, 0));
            return true;
        }
        return false;
    }

    public static boolean isTamed(Entity entity) {
        if (entity instanceof EntityTameable && ((EntityTameable) entity).isTamed()) {
            return true;
        }
        CompoundNBT nbt = new CompoundNBT();
        entity.writeToNBT(nbt);
        if (nbt.contains("Owner") && !nbt.getString("Owner").isEmpty()) {
            return true; // ignore
        }
        return nbt.contains("Tamed") && nbt.getBoolean("Tamed"); // ignore
    }

    /**
     * Throws stone at entity
     */
    public static void throwStone(Entity throwerEntity, Entity targetEntity, BlockState state, double speedMod, double height) {
        throwStone(throwerEntity, (int) targetEntity.getPosX(), (int) targetEntity.getPosY(), (int) targetEntity.getPosZ(), state, speedMod, height);
    }

    public static void throwStone(Entity throwerEntity, int x, int y, int z, BlockState state, double speedMod, double height) {
        MoCEntityThrowableRock etrock = new MoCEntityThrowableRock(throwerEntity.world, throwerEntity, throwerEntity.getPosX(), throwerEntity.getPosY() + 0.5D, throwerEntity.getPosZ());
        throwerEntity.world.addEntity(etrock);
        etrock.setState(state);
        etrock.setBehavior(0);
        etrock.getMotion().getX() = ((x - throwerEntity.getPosX()) / speedMod);
        etrock.getMotion().getY() = ((y - throwerEntity.getPosY()) / speedMod + height);
        etrock.getMotion().getZ() = ((z - throwerEntity.getPosZ()) / speedMod);
    }

    /**
     * Calculates the moving speed of the entity
     */
    public static float getMyMovementSpeed(Entity entity) {
        return MathHelper.sqrt((entity.getMotion().getX() * entity.getMotion().getx()) + (entity.getMotion().getZ() * entity.getMotion().getZ()));
    }

    public static ItemEntity getClosestFood(Entity entity, double d) {
        double d1 = -1D;
        ItemEntity entityitem = null;
        List<Entity> list = entity.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().grow(d));
        for (Entity entity1 : list) {
            if (!(entity1 instanceof ItemEntity)) {
                continue;
            }
            ItemEntity entityitem1 = (ItemEntity) entity1;
            if (!isItemEdible(entityitem1.getItem().getItem())) {
                continue;
            }
            double d2 = entityitem1.getDistanceSq(entity.getPosX(), entity.getPosY(), entity.getPosZ());
            if (((d < 0.0D) || (d2 < (d * d))) && ((d1 == -1D) || (d2 < d1))) {
                d1 = d2;
                entityitem = entityitem1;
            }
        }

        return entityitem;
    }

    /**
     * List of edible foods
     */
    public static boolean isItemEdible(Item item1) {
        return (item1 instanceof ItemFood) || (item1 instanceof ItemSeeds) || item1 == Items.WHEAT || item1 == Items.SUGAR || item1 == Items.CAKE || item1 == Items.EGG;
    }

    public static boolean isItemEdibleforCarnivores(Item item1) {
        return item1 == Items.BEEF || item1 == Items.CHICKEN || item1 == Items.COOKED_BEEF || item1 == Items.COOKED_CHICKEN || item1 == Items.COOKED_FISH || item1 == Items.RABBIT || item1 == Items.COOKED_MUTTON || item1 == Items.COOKED_PORKCHOP || item1 == Items.MUTTON || item1 == Items.COOKED_RABBIT || item1 == Items.FISH || item1 == Items.PORKCHOP;
    }

    public static CompoundNBT getEntityData(Entity entity) {
        if (!entity.getEntityData().contains(MoCConstants.MOD_ID)) {
            entity.getEntityData().put(MoCConstants.MOD_ID, new CompoundNBT());
        }

        return entity.getEntityData().getCompoundTag(MoCConstants.MOD_ID);
    }

    public static void findMobRider(Entity mountEntity) {
        List<Entity> list = mountEntity.world.getEntitiesWithinAABBExcludingEntity(mountEntity, mountEntity.getEntityBoundingBox().grow(4D, 2D, 4D));
        for (Entity entity : list) {
            if (!(entity instanceof MobEntity)) {
                continue;
            }
            MobEntity entitymob = (MobEntity) entity;
            if (entitymob.getRidingEntity() == null && (entitymob instanceof EntitySkeleton || entitymob instanceof EntityZombie || entitymob instanceof MoCEntitySilverSkeleton)) {
                if (!mountEntity.world.isRemote) {
                    entitymob.startRiding(mountEntity);
                }
                break;
            }
        }
    }

    public static void copyDataFromOld(Entity source, Entity target) {
        CompoundNBT nbttagcompound = target.writeToNBT(new CompoundNBT());
        nbttagcompound.remove("Dimension");
        source.readFromNBT(nbttagcompound);
    }

    public static void dismountSneakingPlayer(LivingEntity entity) {
        if (!entity.isRiding()) return;
        Entity entityRidden = entity.getRidingEntity();
        if (entityRidden instanceof LivingEntity && entityRidden.isSneaking()) {
            entity.dismountRidingEntity();
            double dist = (-1.5D);
            double newPosX = entityRidden.getPosX() + (dist * Math.sin(((LivingEntity) entityRidden).renderYawOffset / 57.29578F));
            double newPosZ = entityRidden.getPosZ() - (dist * Math.cos(((LivingEntity) entityRidden).renderYawOffset / 57.29578F));
            entity.setPositionAndUpdate(newPosX, entityRidden.getPosY() + 2D, newPosZ);
            MoCTools.playCustomSound(entity, SoundEvents.ENTITY_CHICKEN_EGG);
        }
    }

    public static boolean isInsideOfMaterial(Material material, Entity entity) {
        double d = entity.getPosY() + entity.getEyeHeight();
        int i = MathHelper.floor(entity.getPosX());
        int j = MathHelper.floor(MathHelper.floor(d));
        int k = MathHelper.floor(entity.getPosZ());
        BlockPos pos = new BlockPos(i, j, k);
        BlockState blockstate = entity.world.getBlockState(pos);
        if (blockstate.getBlock() != Blocks.AIR && blockstate.getMaterial() == material) {
            float f = BlockLiquid.getLiquidHeightPercent(blockstate.getBlock().getMetaFromState(blockstate)) - 0.1111111F;
            float f1 = j + 1 - f;
            return d < f1;
        } else {
            return false;
        }
    }

    public static double roundToNearest90Degrees(double degrees) {
        double radians = Math.toRadians(degrees);
        double roundedRadians = Math.round(radians / (Math.PI / 2)) * (Math.PI / 2);
        return Math.toDegrees(roundedRadians);
    }
}
