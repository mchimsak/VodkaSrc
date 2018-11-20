package net.minecraft.client.gui;

import com.google.common.collect.Lists;

import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URI;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.concurrent.atomic.AtomicInteger;

import me.lc.vodka.AltManager.GuiAltLogin;
import me.lc.vodka.Vodka;
import me.lc.vodka.font.Fonts;
import me.lc.vodka.helper.TimeHelper;
import me.lc.vodka.utils.util.RenderUtil;
import me.lc.vodka.vitox.particle.Particle;
import me.lc.vodka.vitox.particle.ParticleGenerator;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.WorldRenderer;
import net.minecraft.client.renderer.texture.DynamicTexture;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.client.resources.I18n;
import net.minecraft.realms.RealmsBridge;
import net.minecraft.util.EnumChatFormatting;
import net.minecraft.util.MathHelper;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.demo.DemoWorldServer;
import net.minecraft.world.storage.ISaveFormat;
import net.minecraft.world.storage.WorldInfo;
import org.apache.commons.io.Charsets;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.input.Keyboard;
import org.lwjgl.opengl.GL11;
import org.lwjgl.opengl.GLContext;
import org.lwjgl.util.glu.Project;

public class GuiMainMenu extends GuiScreen implements GuiYesNoCallback {
    private static final AtomicInteger field_175373_f = new AtomicInteger(0);
    private static final Logger logger = LogManager.getLogger();
    private static final Random RANDOM = new Random();
    TimeHelper delay = new TimeHelper();
    int background;

    /**
     * Counts the number of screen updates.
     */
    private float updateCounter;

    /**
     * The splash message.
     */
    private String splashText;
    private GuiButton buttonResetDemo;

    /**
     * Timer used to rotate the panorama, increases every tick.
     */
    private int panoramaTimer;

    // TODO Particle
    private ParticleGenerator particles;
    private Random random = new Random();

    /**
     * Texture allocated for the current viewport of the main menu's panorama background.
     */
    private DynamicTexture viewportTexture;
    private boolean field_175375_v = true;

    /**
     * The Object object utilized as a thread lock when performing non thread-safe operations
     */
    private final Object threadLock = new Object();

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning1;

    /**
     * OpenGL graphics card warning.
     */
    private String openGLWarning2;

    /**
     * Link to the Mojang Support about minimum requirements
     */
    private String openGLWarningLink;
    private static final ResourceLocation splashTexts = new ResourceLocation("texts/splashes.txt");
    private static final ResourceLocation minecraftTitleTextures = new ResourceLocation("textures/gui/title/minecraft.png");

    /**
     * An array of all the paths to the panorama pictures.
     */
    private static final ResourceLocation[] titlePanoramaPaths = new ResourceLocation[]{new ResourceLocation("textures/gui/title/background/panorama_0.png"), new ResourceLocation("textures/gui/title/background/panorama_1.png"), new ResourceLocation("textures/gui/title/background/panorama_2.png"), new ResourceLocation("textures/gui/title/background/panorama_3.png"), new ResourceLocation("textures/gui/title/background/panorama_4.png"), new ResourceLocation("textures/gui/title/background/panorama_5.png")};
    public static final String field_96138_a = "Please click " + EnumChatFormatting.UNDERLINE + "here" + EnumChatFormatting.RESET + " for more information.";
    private int field_92024_r;
    private int field_92023_s;
    private int field_92022_t;
    private int field_92021_u;
    private int field_92020_v;
    private int field_92019_w;
    private ResourceLocation backgroundTexture;

    /**
     * Minecraft Realms button.
     */
    private GuiButton realmsButton;

    public GuiMainMenu() {
        this.openGLWarning2 = field_96138_a;
        this.splashText = "missingno";
        BufferedReader bufferedreader = null;

        try {
            List<String> list = Lists.<String>newArrayList();
            bufferedreader = new BufferedReader(new InputStreamReader(Minecraft.getMinecraft().getResourceManager().getResource(splashTexts).getInputStream(), Charsets.UTF_8));
            String s;

            while ((s = bufferedreader.readLine()) != null) {
                s = s.trim();

                if (!s.isEmpty()) {
                    list.add(s);
                }
            }

            if (!list.isEmpty()) {
                while (true) {
                    this.splashText = (String) list.get(RANDOM.nextInt(list.size()));

                    if (this.splashText.hashCode() != 125780783) {
                        break;
                    }
                }
            }
        } catch (IOException var12) {
            ;
        } finally {
            if (bufferedreader != null) {
                try {
                    bufferedreader.close();
                } catch (IOException var11) {
                    ;
                }
            }
        }

        this.updateCounter = RANDOM.nextFloat();
        this.openGLWarning1 = "";

        if (!GLContext.getCapabilities().OpenGL20 && !OpenGlHelper.areShadersSupported()) {
            this.openGLWarning1 = I18n.format("title.oldgl1", new Object[0]);
            this.openGLWarning2 = I18n.format("title.oldgl2", new Object[0]);
            this.openGLWarningLink = "https://help.mojang.com/customer/portal/articles/325948?ref=game";
        }
    }

    /**
     * Called from the main game loop to update the screen.
     */
    public void updateScreen() {
        ++this.panoramaTimer;
    }

    /**
     * Returns true if this GUI should pause the game when it is displayed in single-player
     */
    public boolean doesGuiPauseGame() {
        return false;
    }

    /**
     * Fired when a key is typed (except F11 which toggles full screen). This is the equivalent of
     * KeyListener.keyTyped(KeyEvent e). Args : character (character on the key), keyCode (lwjgl Keyboard key code)
     */
    protected void keyTyped(char typedChar, int keyCode) throws IOException {
    }

    /**
     * Adds the buttons (and other controls) to the screen in question. Called when the GUI is displayed and when the
     * window resizes, the buttonList is cleared beforehand.
     */
    public void initGui() {
        this.viewportTexture = new DynamicTexture(256, 256);
        this.backgroundTexture = this.mc.getTextureManager().getDynamicTextureLocation("background", this.viewportTexture);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(new Date());

        if (calendar.get(2) + 1 == 12 && calendar.get(5) == 24) {
            this.splashText = "Merry X-mas!";
        } else if (calendar.get(2) + 1 == 1 && calendar.get(5) == 1) {
            this.splashText = "Happy new year!";
        } else if (calendar.get(2) + 1 == 10 && calendar.get(5) == 31) {
            this.splashText = "OOoooOOOoooo! Spooky!";
        }

        int i = 24;
        int j = this.height / 4 + 48;

        if (this.mc.isDemo()) {
            this.addDemoButtons(j, 24);
        } else {
            this.addSingleplayerMultiplayerButtons(j, 24);
        }

        this.buttonList.add(new GuiButton(0, 10, 100, 98, 20, I18n.format("menu.options", new Object[0])));
        this.buttonList.add(new GuiButton(4, 10, 130, 98, 20, I18n.format("menu.quit", new Object[0])));

        synchronized (this.threadLock) {
            this.field_92023_s = this.fontRendererObj.getStringWidth(this.openGLWarning1);
            this.field_92024_r = this.fontRendererObj.getStringWidth(this.openGLWarning2);
            int k = Math.max(this.field_92023_s, this.field_92024_r);
            this.field_92022_t = (this.width - k) / 2;
            this.field_92021_u = ((GuiButton) this.buttonList.get(0)).yPosition - 24;
            this.field_92020_v = this.field_92022_t + k;
            this.field_92019_w = this.field_92021_u + 24;
        }

        this.mc.func_181537_a(false);
        this.particles = new ParticleGenerator(100, this.width, this.height);
    }

    /**
     * Adds Singleplayer and Multiplayer buttons on Main Menu for players who have bought the game.
     */
    private void addSingleplayerMultiplayerButtons(int p_73969_1_, int p_73969_2_) {
        Runnable r2 = () -> System.out.println("233333333");
        this.buttonList.add(new GuiButton(1, 10, 10, 98, 20, I18n.format("menu.singleplayer", new Object[0])));
        this.buttonList.add(new GuiButton(2, 10, 40, 98, 20, I18n.format("menu.multiplayer", new Object[0])));
        this.buttonList.add(new GuiButton(14, 10, 70, 98, 20, I18n.format("AltLogin", new Object[0])));
    }

    /**
     * Adds Demo buttons on Main Menu for players who are playing Demo.
     */
    private void addDemoButtons(int p_73972_1_, int p_73972_2_) {
        this.buttonList.add(new GuiButton(11, this.width / 2 - 100, p_73972_1_, I18n.format("menu.playdemo", new Object[0])));
        this.buttonList.add(this.buttonResetDemo = new GuiButton(12, this.width / 2 - 100, p_73972_1_ + p_73972_2_ * 1, I18n.format("menu.resetdemo", new Object[0])));
        ISaveFormat isaveformat = this.mc.getSaveLoader();
        WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

        if (worldinfo == null) {
            this.buttonResetDemo.enabled = false;
        }
    }

    /**
     * Called by the controls from the buttonList when activated. (Mouse pressed for buttons)
     */
    protected void actionPerformed(GuiButton button) throws IOException {
        if (button.id == 0) {
            this.mc.displayGuiScreen(new GuiOptions(this, this.mc.gameSettings));
        }

        if (button.id == 1) {
            this.mc.displayGuiScreen(new GuiSelectWorld(this));
        }

        if (button.id == 2) {
            this.mc.displayGuiScreen(new GuiMultiplayer(this));
        }

        if (button.id == 14) {
            this.mc.displayGuiScreen(new GuiAltLogin(this));
        }

        if (button.id == 4) {
            this.mc.shutdown();
        }

        if (button.id == 11) {
            this.mc.launchIntegratedServer("Demo_World", "Demo_World", DemoWorldServer.demoWorldSettings);
        }

        if (button.id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            WorldInfo worldinfo = isaveformat.getWorldInfo("Demo_World");

            if (worldinfo != null) {
                GuiYesNo guiyesno = GuiSelectWorld.func_152129_a(this, worldinfo.getWorldName(), 12);
                this.mc.displayGuiScreen(guiyesno);
            }
        }
    }

    private void switchToRealms() {
        RealmsBridge realmsbridge = new RealmsBridge();
        realmsbridge.switchToRealms(this);
    }

    public void confirmClicked(boolean result, int id) {
        if (result && id == 12) {
            ISaveFormat isaveformat = this.mc.getSaveLoader();
            isaveformat.flushCache();
            isaveformat.deleteWorldDirectory("Demo_World");
            this.mc.displayGuiScreen(this);
        } else if (id == 13) {
            if (result) {
                try {
                    Class<?> oclass = Class.forName("java.awt.Desktop");
                    Object object = oclass.getMethod("getDesktop", new Class[0]).invoke((Object) null, new Object[0]);
                    oclass.getMethod("browse", new Class[]{URI.class}).invoke(object, new Object[]{new URI(this.openGLWarningLink)});
                } catch (Throwable throwable) {
                    logger.error("Couldn\'t open link", throwable);
                }
            }

            this.mc.displayGuiScreen(this);
        }
    }

    /**
     * Draws the main menu panorama
     */

    /**
     * Draws the screen and all the components in it. Args : mouseX, mouseY, renderPartialTicks
     */

    private int rainbowModuleList(int speed, int offset) {
        float color = (System.currentTimeMillis() + offset) % speed;
        color /= speed;
        return Color.getHSBColor(color, 1f, 1f).getRGB();
    }

    public void drawScreen(int mouseX, int mouseY, float partialTicks) {
        ScaledResolution s1 = new ScaledResolution(this.mc);
        Random r = new Random();

        if (this.delay.isDelayComplete(((Double)10.0D).longValue() * 1000L)) {
            this.background = r.nextInt(7);
            delay.reset();
        }
        this.mc.getTextureManager().bindTexture(new ResourceLocation("Vodka/background/background_"+this.background+".jpg"));
        Gui.drawModalRectWithCustomSizedTexture(0, 0, 0, 0, s1.getScaledWidth(), s1.getScaledHeight(), s1.getScaledWidth(), s1.getScaledHeight());
        Gui.drawRect(0, s1.getScaledHeight() - 23, s1.getScaledWidth(), s1.getScaledHeight(), new Color(52,73,94, 255).getRGB());
        Fonts.clickgui.drawString("Vodka",2,s1.getScaledHeight() - 23,0xFFFFFF);
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");//设置日期格式
        Fonts.clickgui.drawString(df.format(new Date()),s1.getScaledWidth() - 120,s1.getScaledHeight() - 17,0xFFFFFF);

//        int a = Fonts.clickgui.getStringHeight("V");
//        Fonts.clickgui.drawString("V", this.width / 2 - 40, height / 2 - (a / 2), rainbowModuleList(5000, 15 * 2));
//        Fonts.clickgui.drawString("O", this.width / 2 - 20, height / 2 - (a / 2), rainbowModuleList(5000, 15 * 13));
//        Fonts.clickgui.drawString("D", this.width / 2 + 0, height / 2 - (a / 2), rainbowModuleList(5000, 15 * 24));
//        Fonts.clickgui.drawString("K", this.width / 2 + 20, height / 2 - (a / 2), rainbowModuleList(5000, 15 * 35));
//        Fonts.clickgui.drawString("A", this.width / 2 + 40, height / 2 - (a / 2), rainbowModuleList(5000, 15 * 46));

        // TODO Particle
        for (Particle p : particles.particles) {
            for (Particle p2 : particles.particles) {
                int xx = (int) (MathHelper.cos(0.1F * (p.x + p.k)) * 10.0F);
                int xx2 = (int) (MathHelper.cos(0.1F * (p2.x + p2.k)) * 10.0F);

                boolean mouseOver = (mouseX >= p.x + xx - 95) && (mouseY >= p.y - 90) && (mouseX <= p.x)
                        && (mouseY <= p.y);

                if (mouseOver) {
                    if (mouseY >= p.y - 80 && mouseX >= p2.x - 100 && mouseY >= p2.y && mouseY <= p2.y + 70
                            && mouseX <= p2.x) {

                        int maxDistance = 100;

                        final int xDif = p.x - mouseX;
                        final int yDif = p.y - mouseY;
                        final int distance = (int) Math.sqrt(xDif * xDif + yDif + yDif);

                        final int xDif1 = p2.x - mouseX;
                        final int yDif1 = p2.y - mouseY;
                        final int distance2 = (int) Math.sqrt(xDif1 * xDif1 + yDif1 + yDif1);

                        if (distance < maxDistance && distance2 < maxDistance) {

                            GL11.glPushMatrix();
                            GL11.glEnable(GL11.GL_LINE_SMOOTH);
                            GL11.glDisable(GL11.GL_DEPTH_TEST);
                            GL11.glColor4f(1.0F, 1.0F, 1.0F, 1.0F);
                            GL11.glDisable(GL11.GL_TEXTURE_2D);
                            GL11.glDepthMask(false);
                            GL11.glBlendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
                            GL11.glEnable(GL11.GL_BLEND);
                            GL11.glLineWidth(1.5F);
                            GL11.glBegin(GL11.GL_LINES);

                            GL11.glVertex2d(p.x + xx, p.y);
                            GL11.glVertex2d(p2.x + xx2, p2.y);
                            GL11.glEnd();
                            GL11.glPopMatrix();

                            if (Keyboard.isKeyDown(Keyboard.KEY_SPACE)) {
                                if (p2.x > mouseX) {
                                    p2.y -= random.nextInt(5);
                                }
                                if (p2.y < mouseY) {
                                    p2.x += random.nextInt(5);
                                }

                            }

                        }
                    }
                }
            }

        }

        this.particles.drawParticles();
        super.drawScreen(mouseX, mouseY, partialTicks);
    }

    /**
     * Called when the mouse is clicked. Args : mouseX, mouseY, clickedButton
     */
    protected void mouseClicked(int mouseX, int mouseY, int mouseButton) throws IOException {
        super.mouseClicked(mouseX, mouseY, mouseButton);

        synchronized (this.threadLock) {
            if (this.openGLWarning1.length() > 0 && mouseX >= this.field_92022_t && mouseX <= this.field_92020_v && mouseY >= this.field_92021_u && mouseY <= this.field_92019_w) {
                GuiConfirmOpenLink guiconfirmopenlink = new GuiConfirmOpenLink(this, this.openGLWarningLink, 13, true);
                guiconfirmopenlink.disableSecurityWarning();
                this.mc.displayGuiScreen(guiconfirmopenlink);
            }
        }
    }
}
