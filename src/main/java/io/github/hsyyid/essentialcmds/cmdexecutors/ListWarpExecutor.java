package io.github.hsyyid.essentialcmds.cmdexecutors;

import io.github.hsyyid.essentialcmds.utils.PaginatedList;
import io.github.hsyyid.essentialcmds.utils.Utils;

import org.spongepowered.api.entity.living.player.Player;
import org.spongepowered.api.text.Text;
import org.spongepowered.api.text.TextBuilder;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.action.TextActions;
import org.spongepowered.api.text.format.TextColors;
import org.spongepowered.api.text.format.TextStyles;
import org.spongepowered.api.util.command.CommandException;
import org.spongepowered.api.util.command.CommandResult;
import org.spongepowered.api.util.command.CommandSource;
import org.spongepowered.api.util.command.args.CommandContext;
import org.spongepowered.api.util.command.source.CommandBlockSource;
import org.spongepowered.api.util.command.source.ConsoleSource;
import org.spongepowered.api.util.command.spec.CommandExecutor;

import java.util.ArrayList;
import java.util.Optional;

public class ListWarpExecutor implements CommandExecutor
{
	public CommandResult execute(CommandSource src, CommandContext ctx) throws CommandException
	{
		if (src instanceof Player)
		{
			Player player = (Player) src;
			ArrayList<String> warps;

			try
			{
				warps = Utils.getWarps();
			}
			catch (NullPointerException e)
			{
				player.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No warps set!"));
				return CommandResult.success();
			}

			Optional<Integer> arguments = ctx.<Integer> getOne("page no");

			int pgNo;

			if (arguments.isPresent())
			{
				pgNo = arguments.get();
			}
			else
			{
				pgNo = 1;
			}

			if (warps.size() > 0)
			{
				// Add List
				PaginatedList pList = new PaginatedList("/warps");
				for (String name : warps)
				{
					Text item = Texts.builder(name)
						.onClick(TextActions.runCommand("/warp " + name))
						.onHover(TextActions.showText(Texts.of(TextColors.WHITE, "Warp to ", TextColors.GOLD, name)))
						.color(TextColors.DARK_AQUA)
						.style(TextStyles.UNDERLINE)
						.build();

					pList.add(item);
				}
				pList.setItemsPerPage(10);
				// Header
				TextBuilder header = Texts.builder();
				header.append(Texts.of(TextColors.GREEN, "------------"));
				header.append(Texts.of(TextColors.GREEN, " Showing Warps page " + pgNo + " of " + pList.getTotalPages() + " "));
				header.append(Texts.of(TextColors.GREEN, "------------"));

				pList.setHeader(header.build());
				// Send List
				src.sendMessage(pList.getPage(pgNo));
			}
			else
			{
				src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "No warps set!"));
			}
		}
		else if (src instanceof ConsoleSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warps!"));
		}
		else if (src instanceof CommandBlockSource)
		{
			src.sendMessage(Texts.of(TextColors.DARK_RED, "Error! ", TextColors.RED, "Must be an in-game player to use /warps!"));
		}

		return CommandResult.success();
	}
}
