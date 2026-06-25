package pub.astralis.client.module.impl.movement.noslowdown;

import net.minecraft.network.protocol.game.ServerboundUseItemPacket;
import net.minecraft.tags.ItemTags;
import net.minecraft.world.InteractionHand;
import pub.astralis.client.event.EventTarget;
import pub.astralis.client.event.events.impl.game.TickEvent;
import pub.astralis.client.event.events.impl.player.PlayerItemSprintEvent;
import pub.astralis.client.event.events.impl.player.SlowDownEvent;
import pub.astralis.client.event.types.EventModes;
import pub.astralis.client.module.impl.movement.NoSlowDownModule;
import pub.astralis.client.module.value.impl.BooleanValue;
import pub.astralis.client.module.value.impl.ModeValue;
import pub.astralis.client.module.value.mode.Mode;
import pub.astralis.client.utility.network.PacketUtility;
import pub.astralis.client.utility.player.ItemUtility;

public class GrimNoSlowdownMode extends Mode<NoSlowDownModule> {

    private final ModeValue grimMode = new ModeValue("Grim mode")
            .add("2 Tick")
            .add("1.19")
            .set("2 Tick");

    private final BooleanValue sword = new BooleanValue("Full with Sword (1.8 only)", true);

    public GrimNoSlowdownMode(NoSlowDownModule parent) {
        super("Grim", parent);
        sword.addHideIfs(() -> !grimMode.is("2 Tick"));
        addValues(grimMode, sword);
    }

    private int ticks;

    @EventTarget
    public void onTick(TickEvent event) {
        if (event.getEventMode() != EventModes.PRE) return;
        ticks = mc.player.isUsingItem() ? ticks + 1 : 0;

        if (grimMode.is("1.19") && mc.player.isUsingItem() && parent.isAllowed()) {
            if (ticks > 3 && ticks < 7) {
                boolean hand = mc.player.getUsedItemHand() == InteractionHand.MAIN_HAND;
                PacketUtility.sendSequenced(id -> new ServerboundUseItemPacket(hand ? InteractionHand.OFF_HAND : InteractionHand.MAIN_HAND, id, mc.player.getYRot(), mc.player.getXRot()));
            }
        }
    }

    @EventTarget
    public void onSlowDown(SlowDownEvent event) {
        if (!parent.isAllowed() && !mc.player.isUsingItem()) return;

        switch (grimMode.getValue().getName()) {
            case "2 tick" -> {
                if (ItemUtility.isSword() && sword.getValue() && parent.isAllowed()) {
                    event.setCancelled(true);
                } else if (ticks >= 2) {
                    event.setCancelled(true);
                    ticks = 0;
                }
            }
            case "1.19" -> {
                if (ticks > 2) {
                    event.setCancelled(true);
                }
            }
        }
    }

    @EventTarget
    public void onPlayerSprint(PlayerItemSprintEvent event) {
        if (!parent.isAllowed()) return;

        if (!ItemUtility.isSword()) {
            event.setCancelled(true);
        }
    }
}
