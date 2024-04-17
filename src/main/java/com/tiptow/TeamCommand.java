package com.tiptow;

import org.bukkit.command.Command;
import org.bukkit.command.CommandExecutor;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.bukkit.Location;
import net.luckperms.api.LuckPerms;
import net.luckperms.api.model.user.User;
import net.luckperms.api.node.Node;
import net.luckperms.api.node.types.InheritanceNode;
import net.luckperms.api.model.group.Group;

public class TeamCommand implements CommandExecutor {

    private final LuckPerms luckPerms;

    public TeamCommand(LuckPerms luckPerms) {
        this.luckPerms = luckPerms;
    }

    @Override
    public boolean onCommand(CommandSender sender, Command command, String label, String[] args) {
        if (!(sender instanceof Player)) {
            sender.sendMessage("Only players can use this command!");
            return true;
        }

        if (args.length < 3) {
            sender.sendMessage("Usage: /team <add/remove> <player> <team>");
            return true;
        }

        String action = args[0].toLowerCase();
        String playerName = args[1];
        String teamName = args[2].toLowerCase();

        Player targetPlayer = sender.getServer().getPlayer(playerName);

        if (targetPlayer == null) {
            sender.sendMessage("Player not found!");
            return true;
        }

        User user = luckPerms.getUserManager().getUser(targetPlayer.getUniqueId());

        if (user == null) {
            sender.sendMessage("User not found!");
            return true;
        }

        Group group = luckPerms.getGroupManager().getGroup(teamName);

        if (group == null) {
            sender.sendMessage("Team not found!");
            return true;
        }

        switch (teamName) {
            case "valoria":
                teleportPlayer(targetPlayer, 1024, 86, 3445);
                break;
            case "eldoria":
                teleportPlayer(targetPlayer, 13302, 100, -1555);
                break;
            case "sylvania":
                teleportPlayer(targetPlayer, 4987, 88, -1177);
                break;
            default:
                sender.sendMessage("Invalid team name! Available teams are: valoria, eldoria, sylvania");
                return true;
        }

        if (action.equals("add")) {
            // Add the player to the group
            user.data().add(InheritanceNode.builder(group).build());
            luckPerms.getUserManager().saveUser(user);
            sender.sendMessage("Player " + playerName + " added to team " + teamName);
        } else if (action.equals("remove")) {
            // Remove the player from the group
            user.data().remove(InheritanceNode.builder(group).build());
            luckPerms.getUserManager().saveUser(user);
            sender.sendMessage("Player " + playerName + " removed from team " + teamName);
        } else {
            sender.sendMessage("Invalid action! Use 'add' or 'remove'.");
        }

        return true;
    }

    private void teleportPlayer(Player player, double x, double y, double z) {
        Location location = new Location(player.getWorld(), x, y, z);
        player.teleport(location);
    }
}
