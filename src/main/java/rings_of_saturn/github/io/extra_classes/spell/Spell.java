package rings_of_saturn.github.io.extra_classes.spell;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.util.CooldownsUtil;

import java.util.List;

public abstract class Spell {
    private final Identifier id;
    private final List<Click> inputs;
    private final int manaCost;
    private final int cooldown;
    private final int castDuration;

    protected Spell(Identifier id, List<Click> inputs, int manaCost, int cooldown) {
        this.cooldown = cooldown;
        this.castDuration = 0;
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.manaCost = manaCost;
    }
    protected Spell(Identifier id, List<Click> inputs, int manaCost, int cooldown, int castDuration) {
        this.castDuration = castDuration;
        this.cooldown = cooldown;
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.manaCost = manaCost;
    }

    protected Spell(Identifier id, List<Click> inputs, int manaCost) {
        this.cooldown = 0;
        this.castDuration = 0;
        this.id = id;
        this.inputs = List.copyOf(inputs);
        this.manaCost = manaCost;
    }

    public Identifier getId() {
        return this.id;
    }

    public List<Click> getInputs() {
        return this.inputs;
    }

    public int getManaCost() {
        return this.manaCost;
    }

    public void cast(ServerPlayerEntity caster) {
        CooldownsUtil.resetCooldown(this, caster);
    }

    public int getCooldown() {
        return this.cooldown;
    }

    public int getCastDuration() {
        return this.castDuration;
    }

    public void onCastStart(ServerPlayerEntity caster) {
    }

    public void onCastTick(ServerPlayerEntity caster, int elapsedTicks) {
    }

    public void onCastEnd(ServerPlayerEntity caster) {
    }


}
