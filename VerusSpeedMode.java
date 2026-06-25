package pub.astralis.client.module.impl.movement.speed;

import net.minecraft.core.Direction;
import pub.astralis.client.event.EventTarget;
import pub.astralis.client.event.events.impl.game.MotionEvent;
import pub.astralis.client.event.events.impl.input.InputTickEvent;
import pub.astralis.client.module.impl.movement.SpeedModule;
import pub.astralis.client.module.value.impl.ModeValue;
import pub.astralis.client.module.value.mode.Mode;
import pub.astralis.client.utility.player.MovementUtility;
import pub.astralis.client.utility.player.PlayerUtility;

public class VerusSpeedMode extends Mode<SpeedModule> {
    private final ModeValue mode = new ModeValue("Mode")
            .add("Glide")
            .add("Strafe")
            .add("Low Hop")
            .set("Glide");

    public VerusSpeedMode(SpeedModule parent) {
        super("Verus", parent);
        this.addValues(mode);
    }

    @EventTarget
    public void onMotion(MotionEvent event) {
        if (!MovementUtility.isMoving()) return;
        switch (mode.getValue().getName()) {
            case "Glide": {
                mc.player.setDeltaMovement(mc.player.getDeltaMovement().with(Direction.Axis.Y, -0.078400001525878));
                if (!mc.player.onGround()) {
                    double speed = mc.options.keyUp.isDown() ? 0.37 : 0.34;
                    MovementUtility.strafe(speed);
                }
                break;
            }
            case "Strafe": {
                if (mc.player.hurtTime == 0) {
                    MovementUtility.strafe(0.33);
                }else {
                    MovementUtility.strafe(1);
                }
                break;
            }

            case "Low Hop": {
                if (mc.player.onGround()) {
                    MovementUtility.strafe(0.35);
                } else {
                    if (MovementUtility.OFF_GROUND_TICKS <= 1) {
                        PlayerUtility.setMotionY(-0.0980000019D);
                    }

                    MovementUtility.strafe(MovementUtility.getSpeed() * 0.98);
                }
                break;
            }
        }
    }

    @EventTarget
    public void onInputTick(InputTickEvent event) {
        if (mc.player.onGround()) {
            if (!getParent().getOnlyWhileMoving().getValue() || MovementUtility.isMoving()) {
                event.setJump(true);
            }
        }
    }
}