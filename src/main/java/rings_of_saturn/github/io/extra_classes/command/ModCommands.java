package rings_of_saturn.github.io.extra_classes.command;

import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import net.fabricmc.fabric.api.command.v2.CommandRegistrationCallback;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import rings_of_saturn.github.io.extra_classes.entity.components.ModEntityComponents;
import rings_of_saturn.github.io.extra_classes.components.types.SpendableIntComponent;
import rings_of_saturn.github.io.extra_classes.util.ClassesUtil;

import java.util.Collection;
import java.util.function.Predicate;

import static com.mojang.brigadier.arguments.StringArgumentType.getString;
import static rings_of_saturn.github.io.extra_classes.ExtraClasses.MOD_ID;
import static rings_of_saturn.github.io.extra_classes.util.ModConstants.*;

public class ModCommands {
    private static final SimpleCommandExceptionType UNKNOWN_CLASS =
            new SimpleCommandExceptionType(Text.literal("Unknown class"));

    private static final SimpleCommandExceptionType ALREADY_HAS_CLASS =
            new SimpleCommandExceptionType(Text.literal("You already have a class"));

    private static final Predicate<ServerCommandSource> ADMIN_ONLY =
            CommandManager.requirePermissionLevel(CommandManager.GAMEMASTERS_CHECK);

    private static final SuggestionProvider<ServerCommandSource> CLASS_SUGGESTIONS =
            (context, builder) -> {
                for (String name : KNOWN_CLASSES) {
                    builder.suggest(name);
                }
                return builder.buildFuture();
            };

    public static void register() {
        CommandRegistrationCallback.EVENT.register((dispatcher, registryAccess, environment) -> {
            dispatcher.register(rootCommand());
        });
    }
    private static LiteralArgumentBuilder<ServerCommandSource> rootCommand() {
        return CommandManager.literal(ROOT_COMMAND)
                .then(classCommand())
                .then(manaCommand());
    }

    private static LiteralArgumentBuilder<ServerCommandSource> classCommand() {
        return CommandManager.literal(CLASS_COMMAND)
                .then(CommandManager.literal(CHOOSE_COMMAND)
                        .then(CommandManager.argument("className", StringArgumentType.word())
                                .suggests(CLASS_SUGGESTIONS)
                                .executes(ModCommands::chooseClass)))
                .then(CommandManager.literal("list")
                        .requires(ADMIN_ONLY)
                        .executes(context -> listClasses(context.getSource())))
                .then(CommandManager.literal("get")
                        .requires(ADMIN_ONLY)
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(ModCommands::getPlayersClass)))
                .then(CommandManager.literal("set")
                        .requires(ADMIN_ONLY)
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .then(CommandManager.argument("className", StringArgumentType.word())
                                        .suggests(CLASS_SUGGESTIONS)
                                        .executes(ModCommands::setPlayersClass))))
                .then(CommandManager.literal(CLEAR_COMMAND)
                        .requires(ADMIN_ONLY)
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(ModCommands::clearPlayersClass)));
    }

    private static LiteralArgumentBuilder<ServerCommandSource> manaCommand() {
        return CommandManager.literal(MANA_COMMAND)
                .requires(ADMIN_ONLY)
                .then(CommandManager.literal("get")
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(ModCommands::getMana)))
                .then(CommandManager.literal("set")
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .then(CommandManager.argument("amount", IntegerArgumentType.integer(0))
                                        .executes(ModCommands::setMana))))
                .then(CommandManager.literal("refill")
                        .then(CommandManager.argument("targets", EntityArgumentType.players())
                                .executes(ModCommands::refillMana)));
    }

    private static int listClasses(ServerCommandSource source) {
        source.sendFeedback(
                () -> Text.literal("Known classes: " + String.join(", ", KNOWN_CLASSES)), false);
        return KNOWN_CLASSES.size();
    }

    private static int getPlayersClass(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            Identifier classId = ClassesUtil.getClass(player);
            sourceFeedback(context, player.getName().getString() + "'s class is " + classId.getPath());
        }
        return targets.size();
    }

    private static int setPlayersClass(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        String className = getString(context, "className");
        if (!KNOWN_CLASSES.contains(className)) {
            throw UNKNOWN_CLASS.create();
        }

        Identifier classId = Identifier.of(MOD_ID, className);
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            ClassesUtil.setClass(player, classId);
            ModEntityComponents.MANA.get(player).applyClassDefaults(classId);
            sourceFeedback(context, "Set " + player.getName().getString() + "'s class to " + className);
        }
        return targets.size();
    }

    private static int getMana(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            SpendableIntComponent mana = ModEntityComponents.MANA.get(player);
            sourceFeedback(context, player.getName().getString() + ": " + mana.getValue() + "/" + mana.getMaxValue());
        }
        return targets.size();
    }

    private static int setMana(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        int amount = IntegerArgumentType.getInteger(context, "amount");
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            SpendableIntComponent mana = ModEntityComponents.MANA.get(player);
            mana.setValue(amount);
            sourceFeedback(context, player.getName().getString() + ": " + mana.getValue() + "/" + mana.getMaxValue());
        }
        return targets.size();
    }

    private static int refillMana(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            SpendableIntComponent mana = ModEntityComponents.MANA.get(player);
            mana.setValue(mana.getMaxValue());
            sourceFeedback(context, player.getName().getString() + ": " + mana.getValue() + "/" + mana.getMaxValue());
        }
        return targets.size();
    }

    private static void sourceFeedback(CommandContext<ServerCommandSource> context, String message) {
        context.getSource().sendFeedback(() -> Text.literal(message), false);
    }

    private static int chooseClass(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        ServerPlayerEntity player = context.getSource().getPlayerOrThrow();

        String className = getString(context, "className");
        if (!KNOWN_CLASSES.contains(className)) {
            throw UNKNOWN_CLASS.create();
        }
        if (ClassesUtil.hasClass(player)) {
            throw ALREADY_HAS_CLASS.create();
        }

        Identifier classId = Identifier.of(MOD_ID, className);
        ClassesUtil.setClass(player, classId);
        ModEntityComponents.MANA.get(player).applyClassDefaults(classId);

        sourceFeedback(context, "You are now a " + className);
        return 1;
    }

    private static int clearPlayersClass(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
        Identifier classId = Identifier.of(DEFAULT_CLASS_VALUE);
        Collection<ServerPlayerEntity> targets = EntityArgumentType.getPlayers(context, "targets");
        for (ServerPlayerEntity player : targets) {
            ClassesUtil.setClass(player, classId);
            ModEntityComponents.MANA.get(player).applyClassDefaults(classId);
            sourceFeedback(context, "Cleared " + player.getName().getString() + "'s class");
        }
        return targets.size();
    }

}
