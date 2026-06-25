package pub.astralis.client.module.impl.movement;

import lombok.Getter;
import pub.astralis.client.module.core.Category;
import pub.astralis.client.module.core.Module;
import pub.astralis.client.module.impl.movement.speed.LegitSpeedMode;
import pub.astralis.client.module.impl.movement.speed.SentinelSpeedMode;
import pub.astralis.client.module.impl.movement.speed.VanillaSpeedMode;
import pub.astralis.client.module.impl.movement.speed.VerusSpeedMode;
import pub.astralis.client.module.value.impl.BooleanValue;
import pub.astralis.client.module.value.impl.ModeValue;

public class SpeedModule extends Module {
    @Getter
    private final BooleanValue onlyWhileMoving = new BooleanValue("Only while Moving", true);

    private final ModeValue mode = new ModeValue("Mode", this)
            .add(new LegitSpeedMode(this))
            .add(new VanillaSpeedMode(this))
            .add(new SentinelSpeedMode(this))
            .add(new VerusSpeedMode(this))
            .set("Legit");

    public SpeedModule() {
        super("Speed", "Allows you to move faster.", Category.MOVEMENT);
        this.registerValues(mode, onlyWhileMoving);
    }

    @Override
    public String getSuffix() {
        return mode.getValue().getName();
    }
}
