/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.compat;

import net.minecraft.client.resources.I18n;
import net.minecraft.util.text.TranslationTextComponent;

import java.util.ArrayList;
import java.util.List;

public class CompatScreen extends GuiScreen {
    public static boolean showScreen = true;
    private final List<String> messages = new ArrayList<>();
    private int textHeight;

    public CompatScreen() {
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms").getFormattedText());
        this.messages.add("");
        this.messages.add("");
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms1").getFormattedText());
        this.messages.add("");
        this.messages.add(new TranslationTextComponent("msg.mocreatures.compat.cms2").getFormattedText());
        this.messages.add("");
    }

    @Override
    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        this.drawDefaultBackground();
        int i = this.height / 2 - this.textHeight / 2;
        for (String s : this.messages) {
            List<String> lines = this.fontRenderer.listFormattedStringToWidth(s, this.width);
            for (String line : lines) {
                this.drawCenteredString(this.fontRenderer, line, this.width / 2, i, 16777215);
                i += this.fontRenderer.FONT_HEIGHT;
            }
        }
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    @Override
    public void actionPerformed(GuiButton button) {
        if (button.id == 0) this.mc.displayGuiScreen(new GuiMainMenu());
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        this.textHeight = 0;
        for (String s : this.messages) {
            List<String> lines = this.fontRenderer.listFormattedStringToWidth(s, this.width);
            this.textHeight += lines.size() * this.fontRenderer.FONT_HEIGHT;
        }
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, Math.min(this.height / 2 + this.textHeight / 2 + this.fontRenderer.FONT_HEIGHT, this.height - 30), I18n.format("gui.done")));
    }
}
