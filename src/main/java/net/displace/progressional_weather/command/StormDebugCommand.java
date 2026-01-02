package net.displace.progressional_weather.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.displace.progressional_weather.storm.StormRegistry;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.commands.Commands;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;

public class StormDebugCommand {
    private static final SimpleCommandExceptionType ERROR_FAILED =
            new SimpleCommandExceptionType(Component.literal("Storm debug command failed."));

    public StormDebugCommand(CommandDispatcher<CommandSourceStack> dispatcher) {
        dispatcher.register(Commands.literal("debug_storm").executes(this::execute));
    }

    private int execute(CommandContext<CommandSourceStack> context) {
        ServerLevel server = context.getSource().getLevel();

        var registry = server.registryAccess().lookup(StormRegistry.STORM_REGISTRY_KEY);

        if (registry.isEmpty()) {
            context.getSource().sendFailure(Component.literal("No storm registry found."));
            return 0;
        }

        context.getSource().sendSuccess(() -> Component.literal("Storm Registry Found. Listing registered id's:"), false);

        for (var storm : registry.get()) {
            context.getSource().sendSuccess(() -> Component.literal(storm.id()), false);
        }

        return 1;
    }

}
