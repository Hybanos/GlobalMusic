package fr.hybanos.globalmusic;

import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.*;
import net.minecraft.sound.MusicSound;
import net.minecraft.text.Text;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Calendar;
import java.util.Date;

public class GlobalMusicClient implements ClientModInitializer {

    private static GlobalMusicClient instance;
    private static final MinecraftClient mc = MinecraftClient.getInstance();
    private MusicTracker musicTracker;
    private static final String MOD_ID = "globalmusic";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);
    private Calendar cal;
    private int i = 0;
    private long last_start = 0;

    @Override
    public void onInitializeClient() {
        instance = this;
        this.musicTracker = mc.getMusicTracker();
        cal = Calendar.getInstance();
        ClientTickEvents.END_CLIENT_TICK.register(mc -> tick());
    }

    public void tick() {
        if (musicTracker == null) {
            musicTracker = mc.getMusicTracker();
            return;
        }
        if (shouldPlay() && cal.getTimeInMillis() - last_start > 10000) {
            MusicSound type = mc.getMusicType();
            musicTracker.stop();
            musicTracker.play(type);
            last_start = cal.getTimeInMillis();
        }
        i++;
    }

    private boolean shouldPlay() {
        return true;
    }

    public long getRandomSeed() {
        return getTimeStamp() / 10000;
    }

    private long getTimeStamp() {
        cal.setTime(new Date());
        return cal.getTimeInMillis();
    }

    public static GlobalMusicClient getInstance() {
        return instance;
    }

    private void info(String s) {
        if (mc.player != null) {
            mc.player.sendMessage(Text.of(s));
        }
    }
}
