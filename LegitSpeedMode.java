package pub.astralis.client.module.impl.movement.speed;

import pub.astralis.client.event.EventTarget;
import pub.astralis.client.event.events.impl.input.InputTickEvent;
import pub.astralis.client.module.impl.movement.SpeedModule;
import pub.astralis.client.module.value.mode.Mode;
import pub.astralis.client.utility.player.MovementUtility;

public class LegitSpeedMode extends Mode<SpeedModule> {

    public LegitSpeedMode(SpeedModule parent) {
        super("Legit", parent);
    }

    @EventTarget
    public void onInputTick(InputTickEvent event) {
        if (mc.player.onGround() && !mc.options.keyJump.isDown()) {
            if (!getParent().getOnlyWhileMoving().getValue() || MovementUtility.isMoving()) {
                event.setJump(true);
            }
        }
    }
}