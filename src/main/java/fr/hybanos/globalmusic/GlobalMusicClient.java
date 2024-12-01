package fr.hybanos.globalmusic;

import fr.hybanos.globalmusic.mixin.MusicTrackerAccessor;
// import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.MusicSound;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class GlobalMusicClient implements ModInitialize {

    private static GlobalMusicClient instance;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private MusicTracker musicTracker;
    private static final String MOD_ID = "globalmusic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private Calendar cal;
    private int i = 0;
    private boolean enabled = true;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.musicTracker = mc.getMusicTracker();
        cal = Calendar.getInstance();
        // ClientTickEvents.END_CLIENT_TICK.register(mc -> tick());
    }

    public void tick() {
        if (i++ % 20 != 0) return;
        if (musicTracker == null) {
            musicTracker = mc.getMusicTracker();
            return;
        }

        MusicSound type = mc.getMusicType();

        SoundInstance current = ((MusicTrackerAccessor) musicTracker).getCurrent();

        if (current != null) {
            if (!type.getSound().value().getId().equals(current.getId()) && type.shouldReplaceCurrentMusic()) {
                musicTracker.stop();
            }
            if (!mc.getSoundManager().isPlaying(current)) {
                ((MusicTrackerAccessor) musicTracker).setCurrent(null);
            }
        }

        if (shouldPlay()) {
            musicTracker.stop();
            musicTracker.play(type);
        }
    }

    private boolean shouldPlay() {
        // info("" + ((mc.getMusicType().getMaxDelay() / 20) - (getTimeStamp() / 1000) % (mc.getMusicType().getMaxDelay() / 20)));
        // return ((MusicTrackerAccessor) musicTracker).getCurrent() == null;
        return ((MusicTrackerAccessor) musicTracker).getCurrent() == null && ((getTimeStamp() / 1000) % (mc.getMusicType().getMaxDelay() / 20)) == 0;
    }

    public int timeUntilNextSong() {
        return (int) ((mc.getMusicType().getMaxDelay() / 20) - (getTimeStamp() / 1000) % (mc.getMusicType().getMaxDelay() / 20));
    }

    public long getRandomSeed() {
        return getTimeStamp() / 10000;
    }

    public boolean isEnabled() {
        return enabled;
    }

    private long getTimeStamp() {
        cal.setTime(new Date());
        return cal.getTimeInMillis();
    }

    public static GlobalMusicClient getInstance() {
        return instance;
    }

    public static void info(String s) {
        if (mc.player != null) {
            mc.player.sendMessage(Text.of(s));
        }
    }
}
