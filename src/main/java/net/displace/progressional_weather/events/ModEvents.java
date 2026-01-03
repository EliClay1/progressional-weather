package net.displace.progressional_weather.events;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.command.StormDebugCommand;
import net.displace.progressional_weather.storm.managers.StormDataLoader;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;
import net.neoforged.neoforge.network.registration.PayloadRegistrar;
import net.neoforged.neoforge.server.command.ConfigCommand;

@EventBusSubscriber(modid = ProgressionalWeather.MODID)
public class ModEvents {

    public static ServerLevel LEVEL;

    @SubscribeEvent
    public static void onCommandRegister(RegisterCommandsEvent event) {
        new StormDebugCommand(event.getDispatcher());
        ConfigCommand.register(event.getDispatcher());
        ProgressionalWeather.LOGGER.info("Successfully registered commands.");
    }

    @SubscribeEvent
    public static void onDatapackReload(AddServerReloadListenersEvent event) {
        Identifier key = Identifier.fromNamespaceAndPath(ProgressionalWeather.MODID, "storms");
        StormDataLoader loader = new StormDataLoader(event.getRegistryAccess());
        event.addListener(key, loader);
        ProgressionalWeather.LOGGER.info("Successfully reloaded Storms!");
    }

    @SubscribeEvent
    public static void packetRegistry(RegisterPayloadHandlersEvent event) {
        final PayloadRegistrar registrar = event.registrar("1");
    }
}
