package com.kisus.playerHider;

import cpw.mods.fml.client.registry.ClientRegistry;
import cpw.mods.fml.common.FMLCommonHandler;
import cpw.mods.fml.common.Mod;
import cpw.mods.fml.common.Mod.EventHandler;
import cpw.mods.fml.common.event.FMLInitializationEvent;
import cpw.mods.fml.common.event.FMLPreInitializationEvent;
import cpw.mods.fml.common.eventhandler.SubscribeEvent;
import cpw.mods.fml.common.gameevent.InputEvent;
import cpw.mods.fml.relauncher.Side;
import cpw.mods.fml.relauncher.SideOnly;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityClientPlayerMP;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.util.ChatComponentText;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.common.MinecraftForge;
import org.lwjgl.input.Keyboard;

@Mod(modid = PlayerHiderMod.MODID, version = PlayerHiderMod.VERSION)
public class PlayerHiderMod {
    public static final String MODID = "player-hider";
    public static final String VERSION = "1.0";

    private static KeyBinding toggleKey;
    private static boolean playersHidden = false;
    private static final Minecraft minecraft = Minecraft.getMinecraft();

    @EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        FMLCommonHandler.instance().bus().register(this);
        MinecraftForge.EVENT_BUS.register(this);
    }

    @EventHandler
    public void init(FMLInitializationEvent event) {
        toggleKey = new KeyBinding("Hide/Show Players", Keyboard.KEY_LEFT, "Player Hider");

        ClientRegistry.registerKeyBinding(toggleKey);
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onKeyPress(InputEvent.KeyInputEvent event) {
        if (toggleKey.isPressed()) {
            playersHidden = !playersHidden;

            EntityClientPlayerMP player = GetPlayer();
            player.addChatMessage(new ChatComponentText("Player Hider status: " + playersHidden));
        }
    }

    @SubscribeEvent
    @SideOnly(Side.CLIENT)
    public void onPlayerRender(RenderPlayerEvent.Pre event) {
        if (playersHidden && event.entityPlayer.getDisplayName() != GetPlayer().getDisplayName()) {
            event.setCanceled(true);
        }
    }

    private EntityClientPlayerMP GetPlayer() {
        Minecraft minecraft = Minecraft.getMinecraft();

        return minecraft.thePlayer;
    }
}
