package rings_of_saturn.github.io.extra_classes.components.types;


import net.minecraft.util.Identifier;
import org.ladysnake.cca.api.v3.component.Component;

import java.util.ArrayList;
import java.util.LinkedHashMap;

public interface IntIdentifierMapComponent extends Component {
    LinkedHashMap<Identifier, Integer> getValue();
    void setValue(LinkedHashMap<Identifier, Integer> value);
}
