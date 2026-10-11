package rings_of_saturn.github.io.extra_classes.client.spell;

import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.networking.v1.ClientPlayNetworking;
import net.fabricmc.fabric.api.event.client.player.ClientPreAttackCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.item.component.ModItemComponents;
import rings_of_saturn.github.io.extra_classes.network.CastSpellPayload;
import rings_of_saturn.github.io.extra_classes.spell.Click;
import rings_of_saturn.github.io.extra_classes.spell.ModSpells;
import rings_of_saturn.github.io.extra_classes.spell.Spell;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class WandInputsTracker {
    private static final List<Click> clicks = new ArrayList<>();

    private static boolean prevLeft = false;
    private static boolean prevRight = false;
    private static int timeoutTicks = 0;

    public static void registerInputs() {
        ClientPreAttackCallback.EVENT.register((client, player, clickCount) -> {
            ItemStack stack = player.getMainHandStack();
            return stack.contains(ModItemComponents.WAND_SPELLS)
                    && ClassesUtil.canUseItem(player, stack);
        });

        ClientTickEvents.END_CLIENT_TICK.register(WandInputsTracker::tick);
    }

    private static void tick(MinecraftClient client) {
        boolean left = client.options.attackKey.isPressed();
        boolean right = client.options.useKey.isPressed();

        boolean canCast = false;
        List<Spell> available = List.of();

        if (client.player != null && client.currentScreen == null) {
            ItemStack stack = client.player.getMainHandStack();
            available = ModSpells.getByIds(
                    stack.getOrDefault(ModItemComponents.WAND_SPELLS, List.of()));
            canCast = ClassesUtil.canUseItem(client.player, stack) && !available.isEmpty();
        }

        if (!canCast) {
            reset();
            prevLeft = left;
            prevRight = right;
            return;
        }

        boolean newInput = false;
        if (left && !prevLeft) {
            clicks.add(Click.LEFT);
            newInput = true;
        }
        if (right && !prevRight) {
            clicks.add(Click.RIGHT);
            newInput = true;
        }

        prevLeft = left;
        prevRight = right;

        if (clicks.isEmpty()) {
            return;
        }

        if (newInput) {
            timeoutTicks = CLICKS_TIMEOUT_TICKS;
        } else if (--timeoutTicks <= 0) {
            reset();
            return;
        }

        if (!ModSpells.anyStartsWith(available, clicks)) {
            client.inGameHud.setOverlayMessage(
                    Text.literal(hint() + "  →  Failed"), false);
            reset();
            return;
        }

        Optional<Spell> match = ModSpells.findComplete(available, clicks);
        if (match.isPresent()) {
            Spell spell = match.get();
            if (!ModEntityComponents.MANA.get(client.player).canSpend(spell.getManaCost())) {
                client.inGameHud.setOverlayMessage(
                        Text.literal(hint() + "  →  Not enough Mana"), false);
                reset();
                return;
            }
            ClientPlayNetworking.send(new CastSpellPayload(spell.getId()));
            client.player.swingHand(Hand.MAIN_HAND);
            client.inGameHud.setOverlayMessage(
                    Text.literal(hint() + "  →  Cast: " + spell.getId().getPath()), false);
            reset();
            return;
        }
        client.inGameHud.setOverlayMessage(Text.literal(hint()), false);

    }

    private static void reset() {
        clicks.clear();
        timeoutTicks = 0;
    }

    private static String hint() {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < clicks.size(); i++) {
            if (i > 0) {
                builder.append(CLICKS_HINT_SEPARATOR);
            }
            builder.append(clicks.get(i).getSymbol());
        }
        return builder.toString();
    }


}
