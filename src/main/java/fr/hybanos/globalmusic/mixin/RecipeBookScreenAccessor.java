package fr.hybanos.globalmusic.mixin;

import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.ScreenPos;
import net.minecraft.client.gui.screen.ingame.RecipeBookScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.screen.slot.Slot;
import net.minecraft.screen.slot.SlotActionType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Accessor;
import org.spongepowered.asm.mixin.gen.Invoker;

@Mixin(RecipeBookScreen.class)
public interface RecipeBookScreenAccessor {
    @Accessor
    RecipeBookWidget<?> getRecipeBook();

    @Accessor
    boolean isNarrow();

    @Invoker
    void callInit();

    @Invoker
    ScreenPos callGetRecipeBookButtonPos();

    @Invoker
    void callAddRecipeBook();

    @Invoker
    void callOnRecipeBookToggled();

    @Invoker
    void callDrawSlots(DrawContext context);

    @Invoker
    boolean callShouldAddPaddingToGhostResult();

    @Invoker
    boolean callIsPointWithinBounds(int x, int y, int width, int height, double pointX, double pointY);

    @Invoker
    boolean callIsClickOutsideBounds(double mouseX, double mouseY, int left, int top, int button);

    @Invoker
    void callOnMouseClick(Slot slot, int slotId, int button, SlotActionType actionType);
}
