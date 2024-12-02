package fr.hybanos.globalmusic.mixin;

import fr.hybanos.globalmusic.GlobalMusicClient;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.ingame.StatusEffectsDisplay;
import net.minecraft.client.render.RenderLayer;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.stat.Stat;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(StatusEffectsDisplay.class)
public class AbstractInventoryScreenMixin {

    @Inject(method="drawStatusEffects(Lnet/minecraft/client/gui/DrawContext;II)V", at=@At("HEAD"))
    private void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        StatusEffectsDisplay thisObject = (StatusEffectsDisplay) (Object) this;

        // int x = 100;
        // int y = 200;

        int x = (( (StatusEffectsDisplayAccessor) thisObject).getParent().width - 176) / 2;
        if (((RecipeBookScreenAccessor) (RecipeBookScreen) ((StatusEffectsDisplayAccessor) thisObject).getParent()).getRecipeBook().isOpen())
            if (((StatusEffectsDisplayAccessor) thisObject).getParent() instanceof InventoryScreen) {
                x += 77;
            }
        int y = (( (StatusEffectsDisplayAccessor) thisObject).getParent().height - 166) / 2 - 32 - 2;

        boolean enabled = GlobalMusicClient.getInstance().isEnabled();

        SoundInstance soundInstance = ((MusicTrackerAccessor) MinecraftClient.getInstance().getMusicTracker()).getCurrent();
        ItemStack item;
        Identifier background;
        int width = 120;

        if (enabled) {
            item = new ItemStack(Registries.ITEM.getEntry(Identifier.of("music_disc_ward")).get());
            background = soundInstance != null ? Identifier.of("container/inventory/effect_background_large") : Identifier.of("container/inventory/effect_background_small");
        } else {
            item = new ItemStack(Registries.ITEM.getEntry(Identifier.of("music_disc_11")).get());
            background = Identifier.of("container/inventory/effect_background_small");
            width = 32;
        }

        String top = "";
        String name = "";
        boolean drawTooltip = false;
        if (soundInstance != null && soundInstance.getSound() != null) {
            name = soundInstance.getSound().getIdentifier().getPath().split("/")[soundInstance.getSound().getIdentifier().getPath().split("/").length - 1];
            name = name.replace("_", " ");
            top = "Current music";
        } else {
            width = 32;
            drawTooltip = true;
        }

        context.drawGuiTexture(RenderLayer::getGuiTextured, background, x, y, width, 32);
        context.drawItem(item, x+8, y+8);

        context.drawTextWithShadow(( (StatusEffectsDisplayAccessor) thisObject).getParent().getTextRenderer(), Text.of(top), x + 10 + 18, y + 6, 0xFFFFFF);
        context.drawTextWithShadow(( (StatusEffectsDisplayAccessor) thisObject).getParent().getTextRenderer(), Text.of(name), x + 10 + 18, y + 6 + 10, 0x7F7F7F);

        if (mouseX > x + 4 && mouseX < x + 28 && mouseY > y + 4 && mouseY < y + 28 && drawTooltip) {
            context.drawTooltip(((StatusEffectsDisplayAccessor) thisObject).getParent().getTextRenderer(), Text.of(GlobalMusicClient.getInstance().timeUntilNextSong() + "s until next song."), mouseX, mouseY);
        }
    }
}
