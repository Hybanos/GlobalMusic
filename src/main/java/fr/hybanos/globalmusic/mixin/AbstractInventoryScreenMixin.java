package fr.hybanos.globalmusic.mixin;

import fr.hybanos.globalmusic.GlobalMusicClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.ingame.AbstractInventoryScreen;
import net.minecraft.client.gui.screen.ingame.InventoryScreen;
import net.minecraft.client.sound.MusicTracker;
import net.minecraft.client.sound.Sound;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.texture.Sprite;
import net.minecraft.item.Item;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Optional;

@Mixin(AbstractInventoryScreen.class)
public class AbstractInventoryScreenMixin {

    @Inject(method="drawStatusEffects", at=@At("HEAD"))
    public void onDrawStatusEffects(DrawContext context, int mouseX, int mouseY, CallbackInfo ci) {
        AbstractInventoryScreen thisObject = (AbstractInventoryScreen) (Object) this;
        int x = (thisObject.width - 176) / 2;
        if (thisObject instanceof InventoryScreen && ((InventoryScreen) thisObject).getRecipeBookWidget().isOpen()) x += 77;
        int y = (thisObject.height - 166) / 2 - 32 - 2;
        boolean enabled = GlobalMusicClient.getInstance().isEnabled();

        SoundInstance soundInstance = ((MusicTrackerAccessor) MinecraftClient.getInstance().getMusicTracker()).getCurrent();
        ItemStack item;
        Identifier background;
        int width = 120;

        if (enabled) {
            item = new ItemStack(Registries.ITEM.get(new Identifier("music_disc_ward")));
            background = soundInstance != null ? new Identifier("container/inventory/effect_background_large") : new Identifier("container/inventory/effect_background_small");
        } else {
            item = new ItemStack(Registries.ITEM.get(new Identifier("music_disc_11")));
            background = new Identifier("container/inventory/effect_background_small");
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

        context.drawGuiTexture(background, x, y, width, 32);
        ((HandledScreenAccessor) thisObject).callDrawItem(context, item, x + 8, y + 8, "");

        context.drawTextWithShadow(((ScreenAccessor) thisObject).getTextRenderer(), Text.of(top), x + 10 + 18, y + 6, 0xFFFFFF);
        context.drawTextWithShadow(((ScreenAccessor) thisObject).getTextRenderer(), Text.of(name), x + 10 + 18, y + 6 + 10, 0x7F7F7F);

        if (mouseX > x + 4 && mouseX < x + 28 && mouseY > y + 4 && mouseY < y + 28 && drawTooltip) {
            context.drawTooltip(((ScreenAccessor) thisObject).getTextRenderer(), Text.of(GlobalMusicClient.getInstance().timeUntilNextSong() + "s until next song."), mouseX, mouseY);
        }
    }
}
