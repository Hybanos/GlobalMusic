package fr.hybanos.globalmusic.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Mutable;
import org.spongepowered.asm.mixin.gen.Accessor;

@Mixin(MusicTracker.class)
public interface MusicTrackerAccessor {
    @Accessor
    static int getDEFAULT_TIME_UNTIL_NEXT_SONG() {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor
    static void setDEFAULT_TIME_UNTIL_NEXT_SONG(int DEFAULT_TIME_UNTIL_NEXT_SONG) {
        throw new UnsupportedOperationException();
    }

    @Mutable
    @Accessor
    void setRandom(Random random);

    @Mutable
    @Accessor
    void setClient(MinecraftClient client);

    @Accessor
    void setCurrent(SoundInstance current);

    @Accessor
    void setTimeUntilNextSong(int timeUntilNextSong);

    @Accessor
    Random getRandom();

    @Accessor
    MinecraftClient getClient();

    @Accessor
    SoundInstance getCurrent();

    @Accessor
    int getTimeUntilNextSong();
}
