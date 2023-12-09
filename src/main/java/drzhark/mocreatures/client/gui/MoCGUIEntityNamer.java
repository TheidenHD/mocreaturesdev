/*
 * GNU GENERAL PUBLIC LICENSE Version 3
 */
package drzhark.mocreatures.client.gui;

import drzhark.mocreatures.MoCreatures;
import drzhark.mocreatures.proxy.MoCProxyClient;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.entity.MobEntity;
import net.minecraft.util.ChatAllowedCharacters;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.lwjgl.input.Keyboard;

import java.io.IOException;

@SideOnly(Side.CLIENT)
public class MoCGUIEntityNamer extends GuiScreen {

    private static final TextureManager textureManager = MoCProxyClient.mc.getTextureManager();
    private static final ResourceLocation TEXTURE_MOCNAME = MoCreatures.proxy.getGuiTexture("pet_naming.png");
    private final IMoCEntity namedEntity;
    protected String screenTitle;
    protected int xSize;
    protected int ySize;
    @SuppressWarnings("unused")
    private int updateCounter;
    private String nameToSet;

    public MoCGUIEntityNamer(IMoCEntity mocanimal, String s) {
        this.xSize = 256;
        this.ySize = 181;
        this.screenTitle = "Choose your Pet's name:";
        this.namedEntity = mocanimal;
        this.nameToSet = s;
    }

    @Override
    public void initGui() {
        this.buttonList.clear();
        Keyboard.enableRepeatEvents(true);
        this.buttonList.add(new GuiButton(0, this.width / 2 - 100, (this.height - (this.ySize + 16)) / 2 + 150, "Done"));
    }

    public void updateName() {
        this.namedEntity.setPetName(this.nameToSet);
        MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(((MobEntity) this.namedEntity).getEntityId(), this.nameToSet));
        this.mc.displayGuiScreen(null);
    }

    @Override
    protected void actionPerformed(GuiButton guibutton) {
        if (!guibutton.enabled) {
            return;
        }
        if (guibutton.id == 0 && this.nameToSet != null) {
            updateName();
        }
    }

    @Override
    public void drawScreen(int i, int j, float f) {
        drawDefaultBackground();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        textureManager.bindTexture(TEXTURE_MOCNAME);
        int l = (this.width - this.xSize) / 2;
        int i1 = (this.height - (this.ySize + 16)) / 2;
        drawTexturedModalRect(l, i1, 0, 0, this.xSize, this.ySize);
        drawCenteredString(this.fontRenderer, this.screenTitle, this.width / 2, (this.height - (this.ySize + 16)) / 2 + 29, 0xffffff);
        drawCenteredString(this.fontRenderer, this.nameToSet + "_", this.width / 2, (this.height - (this.ySize + 16)) / 2 + 74, 0xffffff);
        super.drawScreen(i, j, f);
    }

    @Override
    public void handleKeyboardInput() throws IOException {
        if (Keyboard.getEventKeyState() && Keyboard.getEventKey() == 28) { // Handle Enter Key
            updateName();
        }
        super.handleKeyboardInput();
    }

    @Override
    protected void keyTyped(char c, int i) {
        if (i == 14) { // Handle Backspace Key
            this.nameToSet = this.nameToSet.substring(0, this.nameToSet.length() - 1);
        } else if (ChatAllowedCharacters.isAllowedCharacter(c)) {
            this.nameToSet = this.nameToSet + c;
        }
    }

    @Override
    public void onGuiClosed() {
        Keyboard.enableRepeatEvents(false);
        if (this.namedEntity instanceof IMoCTameable) {
            IMoCTameable tamedEntity = (IMoCTameable) this.namedEntity;
            tamedEntity.playTameEffect(true);
        }
    }

    @Override
    public void updateScreen() {
        this.updateCounter++;
    }
}
