package rings_of_saturn.github.io.extra_classes.components.types;

import net.minecraft.util.math.Vec3d;
import org.ladysnake.cca.api.v3.component.Component;

public interface Vec3dComponent extends Component {
    Vec3d getValue();
    void setValue(Vec3d value);
}
