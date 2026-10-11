package rings_of_saturn.github.io.extra_classes.network;

import net.fabricmc.fabric.api.networking.v1.PayloadTypeRegistry;
import net.fabricmc.fabric.api.networking.v1.ServerPlayNetworking;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.item.component.ModItemComponents;
import rings_of_saturn.github.io.extra_classes.spell.ModSpells;
import rings_of_saturn.github.io.extra_classes.spell.Spell;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import java.util.List;

public class ModPayloads {
    public static void register() {
        PayloadTypeRegistry.playC2S().register(CastSpellPayload.ID, CastSpellPayload.CODEC);

        ServerPlayNetworking.registerGlobalReceiver(CastSpellPayload.ID, (payload, context) -> {
            var player = context.player();
            ItemStack stack = player.getMainHandStack();

            if (!ClassesUtil.canUseItem(player, stack)) {
                player.sendMessage(Text.literal("This weapon is not meant for you"), true);
                return;
            }

            Spell spell = ModSpells.get(payload.spellId()).orElse(null);
            if (spell == null) {
                return;
            }

            List<Identifier> granted = stack.getOrDefault(ModItemComponents.WAND_SPELLS, List.of());
            if (!granted.contains(spell.getId())) {
                player.sendMessage(Text.literal("No spell exists"), true);
                return;
            }

            if (!ModEntityComponents.MANA.get(player).trySpend(spell.getManaCost())) {
                player.sendMessage(Text.literal("Not enough mana"), true);
                return;
            }

            spell.cast(player);
        });
    }
}
