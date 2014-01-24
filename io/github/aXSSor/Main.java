package io.github.aXSSor;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintStream;
import java.io.PrintWriter;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Random;
import org.bukkit.Bukkit;
import org.bukkit.Color;
import org.bukkit.FireworkEffect;
import org.bukkit.FireworkEffect.Builder;
import org.bukkit.FireworkEffect.Type;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.Server;
import org.bukkit.World;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.configuration.file.FileConfiguration;
import org.bukkit.configuration.file.FileConfigurationOptions;
import org.bukkit.entity.EntityType;
import org.bukkit.entity.Firework;
import org.bukkit.entity.Player;
import org.bukkit.inventory.ItemStack;
import org.bukkit.inventory.PlayerInventory;
import org.bukkit.inventory.meta.FireworkMeta;
import org.bukkit.inventory.meta.ItemMeta;
import org.bukkit.plugin.PluginManager;
import org.bukkit.plugin.java.JavaPlugin;
import org.bukkit.scheduler.BukkitScheduler;

public class Main extends JavaPlugin
{
  String p = "";
  String cp = "";
  public String path = "";

  public void onEnable()
  {
    System.out.println("----------------------------------------");
    System.out.println("               Created By aXSSor");
    System.out.println("----------------------------------------");
    System.out.println("> Loading listener...");
    System.out.println("> Listener loaded!");
    System.out.println("> Loading config file...");
    loadconfig("load");
    System.out.println("> You are running " + Bukkit.getVersion());
  }

  public void onDisable() {
    System.out.println("----------------------------------------");
    System.out.println("               Created By aXSSor");
    System.out.println("----------------------------------------");
    System.out.println("> Saving config file...");
    System.out.println("> Config Saved! (/plugins/" + getName() + 
      "/config.yml)");
    System.out.println("----------------------------------------");
  }

  public void loadconfig(String load) {
    if (load == "load") {
      File configfile = new File(getDataFolder() + "/config.yml");
      if (!configfile.exists()) {
        genConfig();
      }
      System.out.println("> Config file loaded!");
    } else if (load == "reload") {
      System.out.println("> Reloaded!");
      reloadConfig();
      System.out.println("> Config Reloaded");
    } else if (load == "save") {
      System.out.println("> Saving...");
      saveConfig();
      System.out.println("> Config file saved!");
    } else {
      System.out.println("> Config load process failed!");
      Bukkit.getPluginManager().disablePlugin(this);
    }
  }

  public void genConfig()
  {
    System.out.println("> Generating config file...");
    getConfig().addDefault("CodeAlreadyUsed", 
      "§cYou can use a code only one time!");
    getConfig().addDefault("CodeNotFound", 
      "§cWrong usage: /redeem XXXX-XXXX-XXXX-XXXX");
    getConfig().addDefault("SuccessRedeem", "§aCode successfully used!");
    getConfig().addDefault("LogFile", Boolean.valueOf(true));
    getConfig().addDefault("LaunchFirework", Boolean.valueOf(true));
    getConfig().addDefault("GiveEmerald", Boolean.valueOf(true));
    getConfig().addDefault("EmeraldName", 
      "§a§lThank you for your donation!");
    getConfig().addDefault("OptionCommandSuccess", 
      "manuadd %player% %group%");
    getConfig().options().copyDefaults(true);
    loadconfig("save");
    System.out.println("> Config file generated!");
  }

  public boolean islog()
  {
    return getConfig().getBoolean("LogFile");
  }

  public void log(String playername, String code, Boolean rightcode, String val)
  {
    if (val == "use") {
      try {
        File saveTo = new File(getDataFolder(), "log.txt");
        FileWriter fw = new FileWriter(saveTo, true);
        PrintWriter pw = new PrintWriter(fw);
        DateFormat dateFormat = new SimpleDateFormat(
          "dd/MM/yyyy HH:mm:ss");
        Date date = new Date();
        if (rightcode.booleanValue())
          pw.println("[USE][CORRECT][Timecode: " + 
            dateFormat.format(date) + "] Player " + 
            playername + " used a right Premium Code! :) Code: " + 
            code);
        else {
          pw.println("[USE][Timecode: " + dateFormat.format(date) + 
            "] Player " + playername + 
            " tried to use a Premium Code! Code: " + code);
        }
        pw.flush();
        pw.close();
      }
      catch (IOException e) {
        e.printStackTrace();
      }
    } else {
      File saveTo = new File(getDataFolder(), "log.txt");
      try {
        DateFormat dateFormat = new SimpleDateFormat(
          "dd/MM/yyyy HH:mm:ss");
        Date date = new Date();

        FileWriter fw = new FileWriter(saveTo, true);
        PrintWriter pw = new PrintWriter(fw);
        pw.println("[CREATE][Timecode: " + dateFormat.format(date) + 
          "] Player " + playername + 
          " created a Premium Code! Code: " + code);
        pw.flush();
        pw.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }

  public void launchFirework(Player pl)
  {
    if (pl.isOnline())
      Bukkit.getScheduler().scheduleAsyncRepeatingTask(this, 
        new Runnable(pl) {
        int ID = 0;

        public void run() {
          if (this.ID < 8) {
            if (this.val$pl.isOnline()) {
              Location ploc = this.val$pl.getLocation();
              Firework fw = (Firework)this.val$pl.getWorld()
                .spawnEntity(ploc, 
                EntityType.FIREWORK);
              FireworkMeta fm = fw.getFireworkMeta();
              FireworkEffect.Type t = FireworkEffect.Type.BALL_LARGE;
              FireworkEffect effect = 
                FireworkEffect.builder().withColor(Color.RED)
                .with(t).withFade(Color.ORANGE)
                .build();
              fm.addEffect(effect);
              fm.setPower(0);
              fw.setFireworkMeta(fm);
            }
            this.ID += 1;
          }
        }
      }
      , 0L, 10L);
  }

  public static int makeRandom(int min, int max)
  {
    Random rand = new Random();
    int code = rand.nextInt(9);
    return code;
  }

  public String code() {
    int c1 = makeRandom(1, 9);
    int c2 = makeRandom(1, 9);
    int c3 = makeRandom(1, 9);
    int c4 = makeRandom(1, 9);
    int c5 = makeRandom(1, 9);
    int c6 = makeRandom(1, 9);
    int c7 = makeRandom(1, 9);
    int c8 = makeRandom(1, 9);
    int c9 = makeRandom(1, 9);
    int c10 = makeRandom(1, 9);
    int c11 = makeRandom(1, 9);
    int c12 = makeRandom(1, 9);
    int c13 = makeRandom(1, 9);
    int c14 = makeRandom(1, 9);
    int c15 = makeRandom(1, 9);
    int c16 = makeRandom(1, 9);
    String code = c1 + c2 + c3 + 
      c4 + "-" + c5 + c6 + c7 + 
      c8 + "-" + c9 + c10 + c11 + 
      c12 + "-" + c13 + c14 + c15 + 
      c16;
    return code;
  }

  public boolean onCommand(CommandSender s, Command cmd, String l, String[] args)
  {
    if (l.equalsIgnoreCase("code")) {
      if (s.hasPermission("code.admin")) {
        if (args.length == 0) {
          s.sendMessage(this.p + 
            "/code gen <group> | Generate a Code");
          s.sendMessage(this.p + 
            "/code list | List all active Codes");
          s.sendMessage(this.p + 
            "/code reload | Reload Code config file");
          s.sendMessage(this.p + "/redeem <code> | Use a Code");
        }
        if (args.length == 1) {
          if (args[0].equalsIgnoreCase("gen")) {
            s.sendMessage(this.p + "/code gen <group>");
          } else if (args[0].equalsIgnoreCase("list")) {
            s.sendMessage(this.p + "§cUnused Codes:");
            int ID = 0;
            while (ID < 99999)
              if (getConfig().isSet(this.path + ID))
              {
                if (!getConfig().getBoolean(
                  this.path + ID + ".used")) {
                  s.sendMessage("        - " + 
                    getConfig().getString(
                    new StringBuilder(String.valueOf(this.path)).append(ID).append(".code").toString()) + 
                    " | §a" + 
                    getConfig().getString(
                    new StringBuilder(String.valueOf(this.path)).append(ID).append(".group").toString()));
                }
                ID++;
              } else {
                ID++;
              }
          }
          else if (args[0].equalsIgnoreCase("reload")) {
            reloadConfig();
            s.sendMessage(this.p + "§aReload complete!");
          } else {
            s.sendMessage(this.p + "Command not found.");
          }
        } else if (args.length == 2) {
          if (args[0].equalsIgnoreCase("gen"))
            for (int ID = 0; ID < 99999; )
            {
              if (getConfig().isSet(
                this.path + 
                ID)) {
                ID++;
              } else {
                String code = code();
                getConfig()
                  .addDefault(
                  this.path + 
                  ID + 
                  ".code", 
                  code);
                getConfig()
                  .addDefault(
                  this.path + 
                  ID + 
                  ".used", 
                  Boolean.valueOf(false));
                getConfig()
                  .addDefault(
                  this.path + 
                  ID + 
                  ".group", 
                  args[1]);
                getConfig().options().copyDefaults(true);
                saveConfig();
                s.sendMessage(this.p + "Code §c#" + ID + 
                  "§7 was generated! Code: §a" + code);
                if (islog()) {
                  log(s.getName(), code, Boolean.valueOf(true), "create");
                }
                ID = 99999;
              }
            }
          else
            s.sendMessage(this.p + "§cCommand not found.");
        }
        else if (args.length > 2)
          s.sendMessage(this.p + 
            "Too many arguments! /code gen <group>");
      }
      else {
        s.sendMessage(this.p + 
          "§cYou don't have permission to execute this command!");
      }
      return true;
    }if (l.equalsIgnoreCase("redeem")) {
      if ((s instanceof Player)) {
        if (args.length == 0) {
          s.sendMessage(this.p + getConfig().getString("CodeNotFound"));
        } else if (args.length == 1) {
          for (int ID = 0; ID < 99999; ID++) {
            String ec = args[0];
            if (!getConfig().isSet(this.path + ID))
              continue;
            if (!getConfig().getString(this.path + ID + ".code")
              .equalsIgnoreCase(ec))
              continue;
            if (!getConfig().getBoolean(
              this.path + ID + ".used")) {
              getConfig().set(this.path + ID + ".used", 
                Boolean.valueOf(true));
              s.sendMessage(this.p + 
                getConfig().getString(
                "SuccessRedeem"));
              saveConfig();
              if (islog()) {
                log(s.getName(), args[0].toString(), 
                  Boolean.valueOf(true), "use");
              }
              String exe = 
                getConfig()
                .getString(
                "OptionCommandSuccess")
                .replace("%player%", s.getName());
              String exe_2 = exe.replace(
                "%group%", 
                getConfig().getString(
                this.path + ID + ".group"));
              Bukkit.getServer().dispatchCommand(
                Bukkit.getServer()
                .getConsoleSender(), 
                exe_2);
              if (getConfig().getBoolean("LaunchFirework")) {
                launchFirework((Player)s);
              }
              if (islog()) {
                System.out
                  .println("> Code execute command: " + 
                  exe_2);
              }

              if (getConfig().getBoolean(
                "GiveEmerald")) {
                Player p = (Player)s;
                ItemStack i = new ItemStack(
                  Material.EMERALD);
                ItemMeta m = i.getItemMeta();
                m.setDisplayName(getConfig()
                  .getString(
                  "EmeraldName"));
                i.setItemMeta(m);
                p.getInventory().addItem(new ItemStack[] { i });
                p.updateInventory();
              }
            } else {
              s.sendMessage(this.p + 
                getConfig().getString(
                "CodeAlreadyUsed"));
              ID = 99999;
            }

          }

          if (islog())
            log(s.getName(), args[0].toString(), Boolean.valueOf(false), "use");
        }
        else {
          s.sendMessage(this.p + getConfig().getString("CodeNotFound"));
        }
      }
      else s.sendMessage("Only a player can execute this command! Sorry :/");
    }

    return true;
  }
}