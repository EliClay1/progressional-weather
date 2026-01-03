package net.displace.progressional_weather.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import net.displace.progressional_weather.storm.managers.ActiveStormManager;
import net.displace.progressional_weather.storm.managers.StormDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.commands.arguments.IdentifierArgument;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.Identifier;

public class StormHandlerCommand {

    public StormHandlerCommand(CommandDispatcher<CommandSourceStack> dispatcher) {

        dispatcher.register(Commands.literal("progressional_weather")
                .then(Commands.literal("storm")
                        .then(Commands.argument("storm_id", IdentifierArgument.id())
                                .then(Commands.argument("duration", IntegerArgumentType.integer(1, 24000))
                                .executes(context -> {
                                    CommandSourceStack source = context.getSource();
                                    Identifier stormId = IdentifierArgument.getId(context, "storm_id");
                                    int duration = IntegerArgumentType.getInteger(context, "duration");

                                    source.sendFailure(Component.literal(stormId.toString()));
                                    source.sendFailure(Component.literal("" + duration));

                                    if (StormDataManager.getStorm(stormId) == null) {
                                        source.sendFailure(Component.literal("Storm not found: " + stormId));
                                        return 0;
                                    }

                                    try {
                                        ActiveStormManager.initializeStorm(source.getLevel(), stormId, duration);
                                        source.sendSuccess(() -> Component.literal(
                                                "Storm initialized: " + stormId + " for " + duration + " ticks"), true);
                                        return 1;
                                    } catch (Exception e) {
                                        source.sendFailure(Component.literal("Error: " + e.getMessage()));
                                        return 0;
                                    }
                                }))
                        )
                )
        );

    }
}
