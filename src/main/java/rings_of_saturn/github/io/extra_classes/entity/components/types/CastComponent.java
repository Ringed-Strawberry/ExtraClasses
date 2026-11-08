package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.storage.ReadView;
import net.minecraft.storage.WriteView;
import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;
import rings_of_saturn.github.io.extra_classes.components.types.SpellCastingComponent;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.spell.ModSpells;
import rings_of_saturn.github.io.extra_classes.spell.Spell;

public class CastComponent implements ServerTickingComponent, AutoSyncedComponent, SpellCastingComponent {
    private Identifier activeSpell = null;
    private int remainingTicks = 0;
    private int elapsedTicks = 0;
    private final Object provider;

    public CastComponent(Object provider) {
        this.provider = provider;
    }

    @Override
    public void serverTick() {
        if (!(this.provider instanceof PlayerEntity player)) {
            return;
        }

        if (this.activeSpell == null) {
            return;
        }

        Spell spell = ModSpells.get(this.activeSpell).orElse(null);
        if (spell == null) {
            this.cancel();
            return;
        }

        ServerPlayerEntity serverPlayer = (ServerPlayerEntity) player;

        spell.onCastTick(serverPlayer, this.elapsedTicks);

        this.remainingTicks = this.remainingTicks - 1;
        this.elapsedTicks = this.elapsedTicks + 1;

        if (this.remainingTicks <= 0) {
            spell.onCastEnd(serverPlayer);
            this.activeSpell = null;
            this.remainingTicks = 0;
            this.elapsedTicks = 0;
            ModEntityComponents.CAST.sync(this.provider);
        }
    }

    @Override
    public Identifier getActiveSpell() {
        return this.activeSpell;
    }

    @Override
    public int getRemainingTicks() {
        return this.remainingTicks;
    }

    @Override
    public int getElapsedTicks() {
        return this.elapsedTicks;
    }

    public boolean isCasting() {
        return this.activeSpell != null;
    }

    @Override
    public void start(Identifier spellId, int durationTicks) {
        this.activeSpell = spellId;
        this.remainingTicks = durationTicks;
        this.elapsedTicks = 0;
        ModEntityComponents.CAST.sync(this.provider);
    }

    @Override
    public void cancel() {
        this.activeSpell = null;
        this.remainingTicks = 0;
        this.elapsedTicks = 0;
        ModEntityComponents.CAST.sync(this.provider);
    }

    @Override
    public void readData(ReadView readView) {
        String spellString = readView.getString("spell", "");
        if (!spellString.isEmpty()) {
            this.activeSpell = Identifier.of(spellString);
        } else {
            this.activeSpell = null;
        }
        this.remainingTicks = readView.getInt("remaining", 0);
        this.elapsedTicks = readView.getInt("elapsed", 0);
    }

    @Override
    public void writeData(WriteView writeView) {
        if (this.activeSpell == null) {
            writeView.putString("spell", "");
        } else {
            writeView.putString("spell", this.activeSpell.toString());
        }
        writeView.putInt("remaining", this.remainingTicks);
        writeView.putInt("elapsed", this.elapsedTicks);
    }

}
