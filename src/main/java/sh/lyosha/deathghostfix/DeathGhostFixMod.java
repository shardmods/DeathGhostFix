package sh.lyosha.deathghostfix;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class DeathGhostFixMod implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("DeathGhostFix");

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> PendingDeaths.processEndOfTick());
        LOGGER.info("[DeathGhostFix] Initialised.");
    }
}
