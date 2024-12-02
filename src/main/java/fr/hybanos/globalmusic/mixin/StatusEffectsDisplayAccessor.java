package fr.hybanos.globalmusic.mixin;

import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.ingame.HandledScreen;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(StatusEffectsDisplay.class)
public interface StatusEffectsDisplayAccessor {
    @Accessor
    static Identifier getEFFECT_BACKGROUND_LARGE_TEXTURE() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    static Identifier getEFFECT_BACKGROUND_SMALL_TEXTURE() {
        throw new UnsupportedOperationException();
    }

    @Accessor
    HandledScreen<?> getParent();

    @Accessor
    MinecraftClient getClient();

    @Invoker
    void callDrawStatusEffects(DrawContext context, int mouseX, int mouseY);

    @Invoker
    void callDrawStatusEffectBackgrounds(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide);

    @Invoker
    void callDrawStatusEffectSprites(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects, boolean wide);

    @Invoker
    void callDrawStatusEffectDescriptions(DrawContext context, int x, int height, Iterable<StatusEffectInstance> statusEffects);

    @Invoker
    Text callGetStatusEffectDescription(StatusEffectInstance statusEffect);
}
