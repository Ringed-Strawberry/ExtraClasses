package rings_of_saturn.github.io.extra_classes.components.types;


import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.Component;

public interface SpellCastingComponent extends Component {
    int getRemainingTicks();
    Identifier getActiveSpell();
    int getElapsedTicks();

    void start(Identifier spellId, int durationTicks);

    void cancel();
}
