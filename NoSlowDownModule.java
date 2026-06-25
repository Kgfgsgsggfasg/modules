package pub.astralis.client.module.impl.movement;

import lombok.Getter;
import net.minecraft.core.component.DataComponents;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.item.*;
import pub.astralis.client.module.core.Category;
import pub.astralis.client.module.core.Module;
import pub.astralis.client.module.impl.movement.noslowdown.GrimNoSlowdownMode;
import pub.astralis.client.module.impl.movement.noslowdown.VanillaNoSlowDownMode;
import pub.astralis.client.module.value.impl.BooleanValue;
import pub.astralis.client.module.value.impl.ModeValue;
import pub.astralis.client.module.value.impl.NumberValue;
import pub.astralis.client.module.value.impl.TreeValue;

public class NoSlowDownModule extends Module {

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new VanillaNoSlowDownMode(this))
            .add(new GrimNoSlowdownMode(this))
            .set("Vanilla");

    private final BooleanValue allowFood = new BooleanValue("Food", true);
    @Getter
    private final BooleanValue allowSwords = new BooleanValue("Swords", true);
    private final BooleanValue allowBows = new BooleanValue("Bows", true);
    private final BooleanValue allowShields = new BooleanValue("Shields", true);
    private final BooleanValue allowPotions = new BooleanValue("Potions", true);

    @Getter
    private final TreeValue allowedTree = new TreeValue("Allowed Items", this)
            .add(allowFood)
            .add(allowSwords)
            .add(allowBows)
            .add(allowShields)
            .add(allowPotions);

    @Getter
    private final NumberValue slowDown =
            new NumberValue("Slowdown Multiplier", 0f, 0f, 1f, 0.1f);

    public NoSlowDownModule() {
        super("No Slowdown", "Prevents you from being slowed down by using items.", Category.MOVEMENT);

        registerValues(mode, allowedTree, slowDown);
    }

    public boolean isAllowed() {
        if (!mc.player.isUsingItem()) {
            return false;
        }

        ItemStack stack = mc.player.getUseItem();
        if (stack.isEmpty()) {
            return false;
        }

        if (stack.has(DataComponents.FOOD)) {
            return allowFood.getValue();
        }

        if (stack.is(ItemTags.SWORDS)) {
            return allowSwords.getValue();
        }


        if (stack.getItem() instanceof ShieldItem) {
            return allowShields.getValue();
        }

        if (stack.getItem() instanceof PotionItem &&
                !(stack.getItem() instanceof SplashPotionItem)) {
            return allowPotions.getValue();

        }

        Item item = stack.getItem();
        if (item instanceof BowItem || item instanceof CrossbowItem) {
            return allowBows.getValue();
        }

        return false;
    }

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }
}