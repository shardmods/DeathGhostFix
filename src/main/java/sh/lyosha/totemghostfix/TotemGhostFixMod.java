package sh.lyosha.totemghostfix;

import net.fabricmc.api.ModInitializer;
import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public final class TotemGhostFixMod implements ModInitializer {

    private static final Logger LOGGER = LoggerFactory.getLogger("TotemGhostFix");

    @Override
    public void onInitialize() {
        ServerTickEvents.END_SERVER_TICK.register(server -> PendingTotemDeaths.processEndOfTick());
        LOGGER.info("[TotemGhostFix] Initialised.");
    }
}
