package com.rappytv.calc;

import com.rappytv.calc.event.ChatEvent;
import net.labymod.api.LabyModAddon;
import net.labymod.settings.elements.ControlElement;
import net.labymod.settings.elements.SettingsElement;
import net.labymod.settings.elements.StringElement;
import net.labymod.utils.Consumer;
import net.labymod.utils.Material;
import net.labymod.utils.ModColor;

import java.util.List;

public class Calculator extends LabyModAddon {

    public static String prefix = ModColor.DARK_PURPLE + "Calculator " + ModColor.DARK_GRAY + "\u00bb ";
    public static String cmd = "calc";
    public static Calculator instance;

    public static Calculator get() {
        return instance;
    }

    @Override
    public void onEnable() {
        instance = this;

        getApi().getEventManager().register(new ChatEvent());
    }

    @Override
    public void loadConfig() {
        cmd = getConfig().has("cmd") ? getConfig().get("cmd").getAsString() : cmd;
    }

    @Override
    protected void fillSettings(List<SettingsElement> list) {
        StringElement channelStringElement = new StringElement(ModColor.YELLOW + "Calculator Command", new ControlElement.IconData(Material.COMMAND), cmd, new Consumer<String>() {

            @Override
            public void accept(String accepted) {
                cmd = accepted;

                getConfig().addProperty("cmd", cmd);
                saveConfig();
            }
        });

        list.add(channelStringElement);
    }
}
