package rings_of_saturn.github.io.extra_classes.util;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;

import java.util.Objects;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public class ClassesUtil {
    public static final String END_MAGE_NAME = "end_mage";
    public static final Identifier END_MAGE_ID = Identifier.of(MOD_ID, END_MAGE_NAME);

    public static final String EYE_WARLOCK_NAME = "eye_warlock";
    public static final Identifier EYE_WARLOCK_ID = Identifier.of(MOD_ID, END_MAGE_NAME);


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
            setClass(player, END_MAGE_NAME);
        else
            setClass(player, EYE_WARLOCK_NAME);
    }

    public static boolean isClass(PlayerEntity player, Identifier classID){
        return getClass(player) == classID;
    }

    public static boolean isClass(PlayerEntity player, String className){
        return Objects.equals(getClass(player).getPath(), className);
    }

    public static boolean isEyeWarlock(PlayerEntity player){
        return isClass(player, EYE_WARLOCK_ID);
    }

    public static boolean isEndMage(PlayerEntity player){
        return isClass(player, END_MAGE_ID);
    }
}
