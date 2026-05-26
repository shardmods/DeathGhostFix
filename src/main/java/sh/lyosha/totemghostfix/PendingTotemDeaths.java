package sh.lyosha.totemghostfix;

import net.minecraft.advancements.CriteriaTriggers;
import net.minecraft.core.component.DataComponents;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.tags.DamageTypeTags;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.DeathProtection;
import net.minecraft.world.level.gameevent.GameEvent;
import net.minecraft.stats.Stats;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.UUID;

public final class PendingTotemDeaths {

    private static final Map<UUID, PendingDeath> PENDING = new LinkedHashMap<>();

    private PendingTotemDeaths() {}

    public static boolean defer(ServerPlayer player, DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }
        if (player.isRemoved() || player.isDeadOrDying() && player.isSpectator()) {
            return false;
        }
        PENDING.put(player.getUUID(), new PendingDeath(player, damageSource, player.level().getGameTime()));
        return true;
    }

    public static void processEndOfTick() {
        Iterator<PendingDeath> iterator = PENDING.values().iterator();
        while (iterator.hasNext()) {
            PendingDeath pending = iterator.next();
            iterator.remove();

            ServerPlayer player = pending.player;
            if (player.isRemoved() || player.isDeadOrDying() && player.isSpectator()) {
                continue;
            }
            if (tryUseDeathProtection(player, pending.damageSource)) {
                continue;
            }
            player.die(pending.damageSource);
        }
    }

    private static boolean tryUseDeathProtection(ServerPlayer player, DamageSource damageSource) {
        if (damageSource.is(DamageTypeTags.BYPASSES_INVULNERABILITY)) {
            return false;
        }

        ItemStack usedStack = null;
        DeathProtection deathProtection = null;
        for (InteractionHand hand : InteractionHand.values()) {
            ItemStack handStack = player.getItemInHand(hand);
            deathProtection = handStack.get(DataComponents.DEATH_PROTECTION);
            if (deathProtection != null) {
                usedStack = handStack.copy();
                handStack.shrink(1);
                break;
            }
        }

        if (usedStack == null) {
            return false;
        }

        player.awardStat(Stats.ITEM_USED.get(usedStack.getItem()));
        CriteriaTriggers.USED_TOTEM.trigger(player, usedStack);
        usedStack.causeUseVibration(player, GameEvent.ITEM_INTERACT_FINISH);
        player.setHealth(1.0F);
        deathProtection.applyEffects(usedStack, player);
        player.level().broadcastEntityEvent(player, (byte)35);
        return true;
    }

    private record PendingDeath(ServerPlayer player, DamageSource damageSource, long gameTime) {}
}
