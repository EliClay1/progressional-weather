package net.displace.progressional_weather.storm;

import net.displace.progressional_weather.ProgressionalWeather;
import net.minecraft.core.HolderLookup;
import net.minecraft.resources.Identifier;
import net.minecraft.server.packs.resources.ResourceManager;
import net.minecraft.server.packs.resources.SimpleJsonResourceReloadListener;
import net.minecraft.util.profiling.ProfilerFiller;
import org.jspecify.annotations.NonNull;

import java.util.HashMap;
import java.util.Map;

public class StormDataLoader extends SimpleJsonResourceReloadListener<Storm> {

    public StormDataLoader(HolderLookup.Provider provider) {
        super(provider, Storm.CODEC, Storm.STORM_REGISTRY_KEY);
    }

    /**
     * A note for future development:
     * <p> The data will not be reloaded unless the IDE is re-built. So either rebuilt the IDE to test /reload functionality.
     * Or, the smarter option, create a datapack that will allow you to do instant reloading.
     * */

    @Override
    protected void apply(Map<Identifier, Storm> identifierStormMap, @NonNull ResourceManager resourceManager, @NonNull ProfilerFiller profilerFiller) {
        Map<Identifier, Storm> storms = this.prepare(resourceManager, profilerFiller);
        storms.forEach((id, storm) ->
                ProgressionalWeather.LOGGER.debug("Loaded storm via reload: {}", id)
        );
        StormManager.setStorms(storms);
    }
}
