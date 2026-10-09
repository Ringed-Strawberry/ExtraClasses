package rings_of_saturn.github.io.extra_classes.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;

import java.util.List;
import java.util.Objects;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class ClassesUtil {
    public static Identifier getClass(PlayerEntity player){
        return Identifier.of(ModEntityComponents.CLASS.get(player).getValue());
    }

    public static void setClass(PlayerEntity player, Identifier newClass){
        ModEntityComponents.CLASS.get(player).setValue(newClass.toString());
    }

    public static void setClass(PlayerEntity player, String className){
        setClass(player, Identifier.of(MOD_ID, className));
    }

    public static void setClass(PlayerEntity player, boolean endMage){
        if(endMage)
            setClass(player, END_WIZARD_NAME);
        else
            setClass(player, EYE_WARLOCK_NAME);
    }

    public static boolean isClass(PlayerEntity player, Identifier classID){
        return getClass(player).equals(classID);
    }

    public static boolean isClass(PlayerEntity player, String className){
        return Objects.equals(getClass(player).getPath(), className);
    }

    public static boolean hasClass(PlayerEntity player){
        return !Objects.equals(
                ModEntityComponents.CLASS.get(player).getValue(), DEFAULT_CLASS_VALUE);
    }

    public static boolean isEyeWarlock(PlayerEntity player){
        return isClass(player, EYE_WARLOCK_ID);
    }

    public static boolean isEndWizard(PlayerEntity player){
        return isClass(player, END_WIZARD_ID);
    }
}
