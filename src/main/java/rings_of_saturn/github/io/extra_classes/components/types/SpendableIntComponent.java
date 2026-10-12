package rings_of_saturn.github.io.extra_classes.components.types;

import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.Component;

public interface SpendableIntComponent extends Component {
    int getValue();
    int getMaxValue();
    void setValue(int amount);
    boolean trySpend(int amount);
    void replenish(int amount);
    boolean canSpend(int amount);
    void applyClassDefaults(Identifier classId);
}
