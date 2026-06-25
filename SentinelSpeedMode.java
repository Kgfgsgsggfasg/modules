package pub.astralis.client.module.impl.movement.speed;

import pub.astralis.client.event.EventTarget;
import pub.astralis.client.event.events.impl.game.MotionEvent;
import pub.astralis.client.module.impl.movement.SpeedModule;
import pub.astralis.client.module.value.mode.Mode;
import pub.astralis.client.utility.player.MovementUtility;
import pub.astralis.client.utility.player.PlayerUtility;

public class SentinelSpeedMode extends Mode<SpeedModule> {
    public SentinelSpeedMode(SpeedModule parent) {
        super("Sentinel", parent);
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (!MovementUtility.isMoving())
            return;


        if (mc.player.onGround())
            if (!getParent().getOnlyWhileMoving().getValue() || MovementUtility.isMoving()) {
            mc.player.jumpFromGround();
            }

        if (!mc.player.horizontalCollision) {
            switch (MovementUtility.OFF_GROUND_TICKS) {
                case 1 -> PlayerUtility.setMotionY(PlayerUtility.getMotionY() - 0.5);
                case 5 -> PlayerUtility.setMotionY(PlayerUtility.getMotionY() - 0.4);
            }
        }

        if (mc.player.hurtTime != 0)
            MovementUtility.strafe(0.5f);
        else {
            MovementUtility.strafe(mc.player.onGround() ? 0.55 : MovementUtility.getSpeed() * 0.9);
        }
    }
}