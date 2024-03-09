package fr.hybanos.globalmusic.mixin;

import fr.hybanos.globalmusic.GlobalMusicClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.WeightedSoundSet;
import net.minecraft.text.Text;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicTracker.class)
public class MusicTrackerMixin {
    @Inject(method="tick", at=@At("HEAD"), cancellable=true)
    private void onTick(CallbackInfo info) {
        MusicTracker thisObject = (MusicTracker) (Object) this;
        if (GlobalMusicClient.getInstance().isEnabled()) {
            ((MusicTrackerAccessor) thisObject).setTimeUntilNextSong(Integer.MAX_VALUE);
            GlobalMusicClient.getInstance().tick();
            info.cancel();
        }
    }
}
