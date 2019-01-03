package me.kingtux.tuxcommand.jda;

import me.kingtux.tuxcommand.common.TuxCommand;
import net.dv8tion.jda.core.Permission;
import net.dv8tion.jda.core.entities.Member;
import net.dv8tion.jda.core.entities.TextChannel;

@FunctionalInterface
public interface JDAMissingPermission {
    void handleLackOfPermission(Permission permission, Member member, TextChannel textChannel, TuxCommand tuxCommand);
}
