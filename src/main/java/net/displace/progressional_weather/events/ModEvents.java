package net.displace.progressional_weather.events;

import net.displace.progressional_weather.ProgressionalWeather;
import net.displace.progressional_weather.command.StormDebugCommand;
import net.displace.progressional_weather.storm.Storm;
import net.displace.progressional_weather.storm.StormDataLoader;
import net.displace.progressional_weather.storm.StormManager;
import net.minecraft.resources.Identifier;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.neoforge.event.AddServerReloadListenersEvent;
import net.neoforged.neoforge.event.RegisterCommandsEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;
import net.neoforged.neoforge.server.command.ConfigCommand;

import java.util.Map;

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
}
