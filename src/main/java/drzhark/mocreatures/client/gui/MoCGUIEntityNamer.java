package drzhark.mocreatures.client.gui;

import drzhark.mocreatures.MoCConstants;
import drzhark.mocreatures.entity.IMoCEntity;
import drzhark.mocreatures.entity.tameable.IMoCTameable;
import drzhark.mocreatures.network.MoCMessageHandler;
import drzhark.mocreatures.network.message.MoCMessageUpdatePetName;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.client.gui.components.Button;
import net.minecraft.client.gui.components.EditBox;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.Mob;
import net.minecraftforge.api.distmarker.Dist;
import net.minecraftforge.api.distmarker.OnlyIn;
import org.lwjgl.glfw.GLFW;

@OnlyIn(Dist.CLIENT)
public class MoCGUIEntityNamer extends Screen {

    @SuppressWarnings("removal")
    private static final ResourceLocation TEXTURE = new ResourceLocation(MoCConstants.MOD_ID, "textures/gui/pet_naming.png");

    private final IMoCEntity namedEntity;
    private final String defaultName;
    private EditBox nameInput;
    private final int imageWidth = 256;
    private final int imageHeight = 181;

    public MoCGUIEntityNamer(IMoCEntity entity, String defaultName) {
        super(Component.literal("Choose your Pet's name:"));
        this.namedEntity = entity;
        this.defaultName = defaultName;
    }

    @Override
    protected void init() {
        super.init();
        int top = (this.height - (imageHeight + 16)) / 2;

        String value = this.nameInput != null ? this.nameInput.getValue() : this.defaultName;

        this.nameInput = new EditBox(this.font, this.width / 2 - 75, top + 68, 150, 20, Component.literal("Pet Name"));
        this.nameInput.setMaxLength(30);
        this.nameInput.setValue(value);
        this.nameInput.setBordered(false);
        this.addRenderableWidget(this.nameInput);
        this.setInitialFocus(this.nameInput);

        this.addRenderableWidget(
                Button.builder(Component.literal("Done"), btn -> updateName())
                        .bounds(this.width / 2 - 100, top + 150, 200, 20)
                        .build()
        );
    }

    private void updateName() {
        String petName = this.nameInput.getValue().trim();
        if (!petName.isEmpty()) {
            this.namedEntity.setPetName(petName);
            if (this.namedEntity instanceof Mob mob) {
                MoCMessageHandler.INSTANCE.sendToServer(new MoCMessageUpdatePetName(mob.getId(), petName));
            }
        }
        this.onClose();
    }

    @Override
    public boolean keyPressed(int keyCode, int scanCode, int modifiers) {
        // Enter confirms, like clicking Done
        if (keyCode == GLFW.GLFW_KEY_ENTER || keyCode == GLFW.GLFW_KEY_KP_ENTER) {
            updateName();
            return true;
        }
        return super.keyPressed(keyCode, scanCode, modifiers);
    }

    @Override
    public void render(GuiGraphics guiGraphics, int mouseX, int mouseY, float partialTicks) {
        this.renderBackground(guiGraphics);
        int left = (this.width - imageWidth) / 2;
        int top = (this.height - (imageHeight + 16)) / 2;
        guiGraphics.blit(TEXTURE, left, top, 0, 0, imageWidth, imageHeight);
        guiGraphics.drawCenteredString(this.font, this.title, this.width / 2, top + 29, 0xFFFFFF);

        super.render(guiGraphics, mouseX, mouseY, partialTicks); // draws the field and button
    }

    @Override
    public void removed() {
        super.removed();
        if (this.namedEntity instanceof IMoCTameable tamed) {
            tamed.playTameEffect(true);
        }
    }
}