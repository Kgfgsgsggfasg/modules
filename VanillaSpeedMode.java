package pub.astralis.client.module.impl.movement.speed;

import pub.astralis.client.event.EventTarget;
import pub.astralis.client.event.events.impl.game.MotionEvent;
import pub.astralis.client.module.impl.movement.SpeedModule;
import pub.astralis.client.module.value.impl.NumberValue;
import pub.astralis.client.module.value.mode.Mode;
import pub.astralis.client.utility.player.MovementUtility;

public class VanillaSpeedMode extends Mode<SpeedModule> {
    private final NumberValue speed = new NumberValue("Speed", 1f, 0f, 10f, 0.1f);

    public VanillaSpeedMode(SpeedModule parent) {
        super("Vanilla", parent);
        this.addValues(speed);
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        MovementUtility.strafe(speed.getValue());

        if (mc.player.onGround())
            if (!getParent().getOnlyWhileMoving().getValue() || MovementUtility.isMoving()) {
                mc.player.jumpFromGround();
            }
    }
}