package fr.hybanos.globalmusic.mixin;

import fr.hybanos.globalmusic.GlobalMusicClient;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.math.random.Random;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(PositionedSoundInstance.class)
public class PositionedSoundInstanceMixin {

    @Inject(method = "music", at = @At("HEAD"), cancellable = true)
    private static void onMusic(SoundEvent sound, CallbackInfoReturnable<PositionedSoundInstance> cir) {
        if (GlobalMusicClient.getInstance().isEnabled()) {
            Random random = SoundInstance.createRandom();
            long seed = GlobalMusicClient.getInstance().getRandomSeed();
            random.setSeed(seed);
            random.nextInt();
            cir.setReturnValue(new PositionedSoundInstance(sound.getId(), SoundCategory.MUSIC, 1.0f, 1.0f, random, false, 0, SoundInstance.AttenuationType.NONE, 0.0, 0.0, 0.0, true));
        }
    }
}
