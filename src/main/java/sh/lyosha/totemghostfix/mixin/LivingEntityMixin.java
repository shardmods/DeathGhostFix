package sh.lyosha.totemghostfix.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import sh.lyosha.totemghostfix.PendingTotemDeaths;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin {

    @ModifyExpressionValue(
        method = "hurtServer",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/LivingEntity;checkTotemDeathProtection(Lnet/minecraft/world/damagesource/DamageSource;)Z"
        )
    )
    private boolean totemghostfix$deferPlayerDeath(
            boolean original,
            ServerLevel serverLevel,
            DamageSource damageSource,
            float amount) {
        if (original) {
            return true;
        }

        LivingEntity self = (LivingEntity)(Object)this;
        return self instanceof ServerPlayer player && PendingTotemDeaths.defer(player, damageSource);
    }
}
