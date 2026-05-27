# DeathGhostFix

DeathGhostFix is a Fabric server-side mod that reduces same-tick death ghosting.

Damage still applies immediately, but if it would be lethal, the mod delays only the final death decision until the end of the same server tick. If healing or a totem saves the player before tick end, death is canceled; otherwise the player dies from the original damage source.

This helps with cases like Instant Health splash potions or last-moment totem swaps being processed in the same tick as lethal damage, but after the damage event in server order.
