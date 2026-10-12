package rings_of_saturn.github.io.extra_classes.util;

import net.minecraft.util.Identifier;
import ringed_strawberry.github.io.spacelib.math.util.TimeUtil;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;

import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;

public class ModConstants {
    public static final String END_WIZARD_NAME = "end_wizard";
    public static final Identifier END_WIZARD_ID = Identifier.of(MOD_ID, END_WIZARD_NAME);
    public static final String EYE_WARLOCK_NAME = "eye_warlock";
    public static final Identifier EYE_WARLOCK_ID = Identifier.of(MOD_ID, EYE_WARLOCK_NAME);
    public static final List<String> KNOWN_CLASSES = List.of(END_WIZARD_NAME, EYE_WARLOCK_NAME);
    public static final String DEFAULT_CLASS_VALUE = "extra_classes:end_wizard";
    public static final LinkedHashMap<Identifier, Integer> DEFAULT_COOLDOWNS_VALUE = LinkedHashMap.newLinkedHashMap(0);

    public static final int WIZARD_MAX_MANA = 100;
    public static final float WIZARD_REGEN_PER_SECOND = 5.0f;
    public static final float REGEN_PER_TICK =  TimeUtil.secondsToTicks(WIZARD_REGEN_PER_SECOND);

    public static final String ROOT_COMMAND = "extraclasses";
    public static final String CLASS_COMMAND = "class";
    public static final String MANA_COMMAND = "mana";
    public static final String CHOOSE_COMMAND = "choose";
    public static final String CLEAR_COMMAND = "clear";

    public static final String WAND_ITEM_NAME = "wand";

    public static final String DEBUG_SPELL_NAME = "debug";
    public static final int DEBUG_SPELL_MANA_COST = 10;

    public static final String DEBUG_COOLDOWN_SPELL_NAME = "debug_cooldown";
    public static final int DEBUG_COOLDOWN_SPELL_COOLDOWN = TimeUtil.secondsToTicks(2);
    public static final int DEBUG_COOLDOWN_SPELL_MANA_COST = 25;

    public static final int CLICKS_TIMEOUT_TICKS = TimeUtil.secondsToTicks(1);
    public static final String CLICKS_HINT_SEPARATOR = " > ";

    public static final int WRONG_CLASS_PENALTY_DURATION = 60;

    public static Identifier modID(String path){
        return Identifier.of(MOD_ID, path);
    }


}
