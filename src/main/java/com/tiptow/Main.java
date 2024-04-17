package com.tiptow;

import net.luckperms.api.LuckPerms;
import net.luckperms.api.LuckPermsProvider;
import org.bukkit.plugin.java.JavaPlugin;

public final class Main extends JavaPlugin {

    private LuckPerms luckPerms;

    @Override
    public void onEnable() {
        // Initialize LuckPerms
        this.luckPerms = LuckPermsProvider.get();

        // Register the TeamCommand as a command executor
        this.getCommand("team").setExecutor(new TeamCommand(luckPerms));
    }


    @Override
    public void onDisable() {
        // Disable any necessary cleanup or saving
    }
}
