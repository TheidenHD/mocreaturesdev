/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.item;

import drzhark.mocreatures.init.MoCItems;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Food;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.EffectInstance;
import net.minecraft.potion.Effects;
import net.minecraft.world.World;

public class MoCItemFood extends MoCItem {
    public int itemUseDuration;

    private MoCItemFood(MoCItemFood.Builder builder) {
        super(builder.properties.food(builder.foodBuilder.build()), builder.name);
        itemUseDuration = builder.itemUseDuration;
    }

    private MoCItemFood(Item.Properties properties, String name) {
        super(properties, name);
    }
    private MoCItemFood(Item.Properties properties, String name, int eatingSpeed) {
        super(properties, name);
        itemUseDuration = eatingSpeed;
    }

    @Override
    public int getUseDuration(ItemStack stack) {
        if (itemUseDuration == 0) {
            return 32;
        }

        return itemUseDuration;
    }

    @Override
    protected void onFoodEaten(ItemStack stack, World world, PlayerEntity player) {
        if (this == MoCItems.mysticPear) {
            player.addPotionEffect(new EffectInstance(Effects.RESISTANCE, 10 * 20, 1));
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 10 * 20, 1));
        }

        if (this == MoCItems.sugarlump) {
            player.addPotionEffect(new EffectInstance(Effects.SPEED, 4 * 20, 0));
        }

        super.onFoodEaten(stack, world, player);
    }


    public static class Builder {
        private Food.Builder foodBuilder;
        private int itemUseDuration;
        private Item.Properties properties;
        private String name;

        public Builder(Item.Properties properties, String name, int amount) {
            this(properties, name, amount, 0.6F, false);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood) {
            this(properties, name, amount, saturation, isWolfFood, 32);
        }

        public Builder(Item.Properties properties, String name, int amount, float saturation, boolean isWolfFood, int eatingSpeed) {
            this.properties = properties;
            this.name = name;
            foodBuilder = (new Food.Builder()).hunger(amount).saturation(saturation);
            if(isWolfFood)
                foodBuilder.meat();
            itemUseDuration = eatingSpeed; // 32 by default
        }

        public Builder setAlwaysEdible() {
            foodBuilder.setAlwaysEdible();
            return this;
        }

        public Builder setPotionEffect(EffectInstance effectIn, float probability) {
            foodBuilder.effect(() -> effectIn, probability);
            return this;
        }

        public MoCItemFood build() {
            return new MoCItemFood(this);
        }
    }
}
