package rings_of_saturn.github.io.extra_classes.item.event;

import net.fabricmc.fabric.api.event.lifecycle.v1.ServerTickEvents;
import net.fabricmc.fabric.api.event.player.AttackBlockCallback;
import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.fabricmc.fabric.api.event.player.UseBlockCallback;
import net.fabricmc.fabric.api.event.player.UseItemCallback;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import rings_of_saturn.github.io.extra_classes.item.component.ModItemComponents;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import static rings_of_saturn.github.io.extra_classes.util.ClassesUtil.canUseItem;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.WRONG_CLASS_PENALTY_DURATION;

public class ItemProtector {
    public static void registerProtection() {
        UseItemCallback.EVENT.register((player, world, hand) -> {
                    if (blockOffhand(player, hand)) {
                        return ActionResult.FAIL;
                    }
                    return onInteract(player, world, player.getStackInHand(hand));
        });

        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) ->
                onInteract(player, world, player.getStackInHand(hand)));

        AttackBlockCallback.EVENT.register((player, world, hand, pos, direction) ->
                onInteract(player, world, player.getStackInHand(hand)));

        ServerTickEvents.END_SERVER_TICK.register(server -> {
            for (ServerPlayerEntity player : server.getPlayerManager().getPlayerList()) {
                dropRestrictedItems(player);
            }
        });

        UseBlockCallback.EVENT.register((player, world, hand, hitResult) -> {
            if (blockOffhand(player, hand)) {
                return ActionResult.FAIL;
            }
            return ActionResult.PASS;
        });

    }

    private static ActionResult onInteract(PlayerEntity player, World world, ItemStack stack) {
        if (world.isClient() || canUseItem(player, stack)) {
            return ActionResult.PASS;
        }

        if (!player.hasStatusEffect(StatusEffects.WITHER)) {
            player.addStatusEffect(new StatusEffectInstance(
                    StatusEffects.WITHER, WRONG_CLASS_PENALTY_DURATION, 1));
            player.sendMessage(Text.literal("This weapon is not meant for you"), false);
        }

        return ActionResult.FAIL;
    }

    private static boolean blockOffhand(PlayerEntity player, Hand hand) {
        if (hand != Hand.OFF_HAND) {
            return false;
        }
        return player.getMainHandStack().contains(ModItemComponents.WAND_SPELLS);
    }

    public static void dropRestrictedItems(PlayerEntity player) {
        if (!(player instanceof ServerPlayerEntity serverPlayer)) {
            return;
        }

        if (player.isCreative()) {
            return;
        }

        PlayerInventory inventory = serverPlayer.getInventory();
        for (int i = 0; i < inventory.size(); i++) {
            ItemStack stack = inventory.getStack(i);
            if (!stack.isEmpty() && !ClassesUtil.canUseItem(player, stack)) {
                serverPlayer.dropItem(inventory.removeStack(i), false);
            }
        }
    }
}
