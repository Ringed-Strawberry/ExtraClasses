package rings_of_saturn.github.io.extra_classes.spell;

import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.Identifier;

import java.util.List;

public abstract class Spell {
    private final Identifier id;
    private final List<Click> inputs;
    private final int manaCost;

    protected Spell(Identifier id, List<Click> inputs, int manaCost) {
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

    public abstract void cast(ServerPlayerEntity caster);

}
