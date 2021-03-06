package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.EssentialCmds;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.spec.CommandExecutor;

public class PlayerFreezeExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		Player targetPlayer = ctx.<Player> getOne("player").get();
		
		if(targetPlayer.equals(src))
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "You cannot freeze yourself!"));
			return CommandResult.success();
		}
		
		if(EssentialCmds.frozenPlayers.contains(targetPlayer.getUniqueId()))
		{
			EssentialCmds.frozenPlayers.remove(targetPlayer.getUniqueId());
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Un-froze player."));
		}
		else
		{
			EssentialCmds.frozenPlayers.add(targetPlayer.getUniqueId());
			src.sendMessage(Texts.of(TextColors.GREEN, "Success! ", TextColors.YELLOW, "Froze player."));
		}
		
		return CommandResult.success();
	}
}
