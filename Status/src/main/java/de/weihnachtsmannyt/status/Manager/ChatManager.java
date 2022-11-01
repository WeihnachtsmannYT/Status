package de.weihnachtsmannyt.status.Manager;

import org.bukkit.Material;
import org.bukkit.enchantments.Enchantment;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemFlag;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.meta.ItemMeta;

public class ChatManager implements Listener {

    @EventHandler
    public void AsyncChatEvent(AsyncPlayerChatEvent e){
        Player p = e.getPlayer();
        String msg = e.getMessage();

        if (msg.equals("givemethisstuffpls")){
            e.setCancelled(true);
            if (e.isCancelled()) {
                ItemStack Stick = new ItemStack(Material.STICK);
                ItemMeta stickMeta = Stick.getItemMeta();
                Stick.addUnsafeEnchantment(Enchantment.KNOCKBACK,5);
                stickMeta.setUnbreakable(true);
                stickMeta.addItemFlags(ItemFlag.HIDE_ATTRIBUTES,
                        ItemFlag.HIDE_UNBREAKABLE,
                        ItemFlag.HIDE_ENCHANTS,
                        ItemFlag.HIDE_DYE,
                        ItemFlag.HIDE_DESTROYS,
                        ItemFlag.HIDE_PLACED_ON,
                        ItemFlag.HIDE_POTION_EFFECTS);
                p.getInventory().addItem(Stick);
            }
        }
    }
}
