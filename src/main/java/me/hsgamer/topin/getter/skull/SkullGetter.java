package me.hsgamer.topin.getter.skull;

import static me.hsgamer.topin.TopIn.getInstance;

import me.hsgamer.topin.getter.Getter;

public class SkullGetter extends Getter {
  private final SkullCommand skullCommand = new SkullCommand();

  @Override
  public boolean register() {
    getInstance().getCommandManager().register(skullCommand);
    return true;
  }

  @Override
  public void unregister() {
    getInstance().getCommandManager().unregister(skullCommand);
  }

  @Override
  public String getName() {
    return "Skull";
  }
}
