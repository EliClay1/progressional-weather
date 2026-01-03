package net.displace.progressional_weather.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import net.displace.progressional_weather.storm.ClientWeatherRenderer;
import net.displace.progressional_weather.storm.dataobjects.ActiveStorm;
import net.displace.progressional_weather.storm.dataobjects.Storm;
import net.displace.progressional_weather.storm.managers.ActiveStormManager;
import net.displace.progressional_weather.storm.managers.StormDataManager;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;

public class StormDebugCommand {
    public StormDebugCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("debug_storm").executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerLevel server = context.getSource().getLevel();

        var registry = server.registryAccess().lookup(Storm.STORM_REGISTRY_KEY);

        if (registry.isEmpty()) {
            context.getSource().sendFailure(Component.literal("No storm registry found."));
            return 0;
        }

//        registry.get().forEach((storm) -> {
//            String info = String.format(
//                    "- %s: T%d %s %s",
//                    storm.id(), storm.tier(), storm.type(),
//                    storm.lightning().isPresent() ? storm.lightning().get().damage() : null
//            );
//            context.getSource().sendSuccess(() -> Component.literal(info), false);
//        });

        try {
            var stormId = ActiveStormManager.getActiveStorm(context.getSource().getLevel()).get().getStormId();
            context.getSource().sendSuccess(() -> Component.literal(stormId.toString()), false);
        } catch (Exception e) {
            context.getSource().sendFailure(Component.literal("No active storm found."));
            return 0;
        }

        ClientWeatherRenderer.getCurrentStorm().ifPresent(currStorm -> {
            context.getSource().sendSuccess(() -> Component.literal(currStorm.getStormId().toString()), false);
        });

        return 1;
    }

}
