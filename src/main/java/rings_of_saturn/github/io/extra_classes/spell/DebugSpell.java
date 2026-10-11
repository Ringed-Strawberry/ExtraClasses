package rings_of_saturn.github.io.extra_classes.spell;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class DebugSpell extends Spell{
    public DebugSpell(Identifier id, List<Click> inputs, int manaCost) {
        super(id, inputs, manaCost);
    }

    @Override
    public void cast(ServerPlayerEntity caster) {
        caster.sendMessage(Text.literal("Fuck you Minecraft"), false);
    }
}
