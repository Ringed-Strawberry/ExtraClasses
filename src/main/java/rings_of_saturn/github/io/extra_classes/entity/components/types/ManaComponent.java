package rings_of_saturn.github.io.extra_classes.entity.components.types;

import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.Component;

public interface ManaComponent extends Component {
    int getMana();
    int getMaxMana();
    void setMana(int amount);
    boolean trySpend(int amount);
    void replenish(int amount);
    void applyClassDefaults(Identifier classId);
}
