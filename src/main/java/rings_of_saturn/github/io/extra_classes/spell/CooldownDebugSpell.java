package rings_of_saturn.github.io.extra_classes.spell;

import net.minecraft.particle.ParticleTypes;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

import java.util.List;

public class CooldownDebugSpell extends Spell{

    protected CooldownDebugSpell(Identifier id, List<Click> inputs, int manaCost, int cooldown) {
        super(id, inputs, manaCost, cooldown);
    }

    @Override
    public void cast(ServerPlayerEntity caster) {
        super.cast(caster);
        caster.getEntityWorld().spawnParticles(ParticleTypes.CRIMSON_SPORE, caster.getX(), caster.getY(), caster.getZ(), 30, 0.25,0.25,0.25,0);
        caster.sendMessage(Text.literal("uwu"), false);
    }
}
