package drzhark.mocreatures.item;

import com.google.common.collect.Multimap;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.MoCreatures;
import net.minecraft.client.renderer.EffectInstance;
import net.minecraft.client.resources.language.I18n;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.effect.MobEffectInstance;
import net.minecraft.world.effect.MobEffects;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Tier;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.MixinEnvironment;

import javax.annotation.Nullable;
import java.util.List;

public class MoCItemWeapon extends Item {

    private final Tier material;
    private final float attackDamage;
    private int specialWeaponType = 0;
    private final Multimap<Attribute, AttributeModifier> attributeModifiers;

    public MoCItemWeapon(Item.Properties properties, Tier material) {
        super(properties.stacksTo(1));
        this.material = material;
        this.setMaxDamage(material.getMaxUses());
        this.attackDamage = 3F + material.getAttackDamage();
    }

    public MoCItemWeapon(Item.Properties properties, Tier material, int damageType) {
        this(properties, material);
        this.specialWeaponType = damageType;
    }

    public float getAttackDamage() {
        return this.material.getAttackDamageBonus();
    }

    public float getDestroySpeed(ItemStack stack, BlockState state) {
        if (state.is(Blocks.COBWEB)) {
            return 15.0F;
        } else {
            //Material material = state.getMaterial();
            //return (material != Material.PLANT && material != Material.REPLACEABLE_PLANT && material != Material.CORAL && material != Material.LEAVES && material != Material.VEGETABLE) ? 1.0F : 1.5F;
            return 1.5F;
        }
    }

    @Override
    public boolean hurtEnemy(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (MoCreatures.proxy.weaponEffects) {
            InteractionHand hand = attacker.getUsedItemHand() == null ? InteractionHand.MAIN_HAND : attacker.getUsedItemHand();
            int timer = 15; // In seconds
            int fire_aspect = 5 * EnchantmentHelper.getFireAspect(attacker); // Fire Aspect
            int poisonous = 5 * EnchantmentHelper.getEnchantmentLevel(Enchantment.getEnchantmentByLocation("mod_lavacow:poisonous"), attacker.getItemInHand(hand)); // Poisonous (Fish's Undead Rising)

            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    target.addEffect(new MobEffectInstance(MobEffects.POISON, (timer * 20) + poisonous, 1));
                    break;
                case 2: // Slowness
                    target.addEffect(new MobEffectInstance(MobEffects.MOVEMENT_SLOWDOWN, timer * 20, 0));
                    break;
                case 3: // Fire
                    target.setSecondsOnFire(timer + fire_aspect);
                    break;
                case 4: // Weakness (Nausea for players)
                    target.addEffect(new MobEffectInstance(target instanceof Player ? MobEffects.CONFUSION : MobEffects.WEAKNESS, timer * 20, 0));
                    break;
                case 5: // Wither (Blindness for players)
                    target.addEffect(new MobEffectInstance(target instanceof Player ? MobEffects.BLINDNESS : MobEffects.WITHER, timer * 20, 0));
                    break;
                default:
                    break;
            }
        }

        stack.hurtAndBreak(1, attacker, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        return true;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        final ItemStack stack = player.getItemInHand(hand);
        player.startUsingItem(hand);
        return InteractionResultHolder.success(stack);
    }

    // TODO: Cobweb harvesting
    /*@Override
    public boolean canHarvestBlock(BlockState state) {
        return state.is(Blocks.COBWEB);
    }*/

    @Override
    public int getEnchantmentValue() {
        return this.material.getEnchantmentValue();
    }

    @Override
    public boolean mineBlock(ItemStack stack, Level world, BlockState state, net.minecraft.core.BlockPos pos, LivingEntity entity) {
        if (!world.isClientSide && state.getDestroySpeed(world, pos) != 0.0F) {
            stack.hurtAndBreak(1, entity, (e) -> e.broadcastBreakEvent(EquipmentSlot.MAINHAND));
        }
        return true;
    }

    public String getToolMaterialName() {
        return this.material.toString();
    }

    @Override
    public boolean isValidRepairItem(ItemStack toRepair, ItemStack repair) {
        return this.material.getRepairIngredient().test(repair) || super.isValidRepairItem(toRepair, repair);
    }

    /**
     * Gets a map of item attribute modifiers, used by SwordItem to increase hit damage.
     */
    public Multimap<String, AttributeModifier> getItemAttributeModifiers(EntityEquipmentSlot equipmentSlot) {
        Multimap<String, AttributeModifier> multimap = super.getItemAttributeModifiers(equipmentSlot);

        if (equipmentSlot == EntityEquipmentSlot.MAINHAND) {
            multimap.put(SharedMonsterAttributes.ATTACK_DAMAGE.getName(), new AttributeModifier(ATTACK_DAMAGE_MODIFIER, "Weapon modifier", this.attackDamage, 0));
        }
        return multimap;
    }

    @Override
    @SideOnly(MixinEnvironment.Side.CLIENT)
    public void addInformation(ItemStack stack, @Nullable Level world, List<String> tooltip, ITooltipFlag flagIn) {
        if (MoCreatures.proxy.weaponEffects) {
            switch (this.specialWeaponType) {
                case 1: // Poison 2
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_dirt", 15));
                    break;
                case 2: // Slowness
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_frost", 15));
                    break;
                case 3: // Fire
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_fire", 15));
                    break;
                case 4: // Weakness (Nausea for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_cave", 15));
                    break;
                case 5: // Wither (Blindness for players)
                    tooltip.add(TextFormatting.BLUE + I18n.format("info." + MoCConstants.MOD_ID + ".sting_weapon_undead", 15));
                    break;
                default:
                    break;
            }
        }
    }
}
