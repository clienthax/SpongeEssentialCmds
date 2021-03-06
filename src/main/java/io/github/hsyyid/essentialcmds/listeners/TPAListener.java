package io.github.hsyyid.essentialcmds.listeners;

import io.github.hsyyid.essentialcmds.EssentialCmds;
import io.github.hsyyid.essentialcmds.events.TPAAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereAcceptEvent;
import io.github.hsyyid.essentialcmds.events.TPAHereEvent;
import io.github.hsyyid.essentialcmds.utils.PendingInvitation;
import org.spongepowered.api.event.Listener;
import org.spongepowered.api.service.scheduler.SchedulerService;
import org.spongepowered.api.service.scheduler.Task;
import org.spongepowered.api.text.Texts;
import org.spongepowered.api.text.format.TextColors;

import java.util.concurrent.TimeUnit;

public class TPAListener
{
	@Listener
	public void tpaEventHandler(TPAEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(
			Texts.of(TextColors.BLUE, "TPA Request From: ", TextColors.GOLD, senderName + ".", TextColors.RED,
				" You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		SchedulerService scheduler = EssentialCmds.game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite")
			.submit(EssentialCmds.game.getPluginManager().getPlugin("EssentialCmds").get().getInstance());
	}
	
	@Listener
	public void tpaAcceptEventHandler(TPAAcceptEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(Texts.of(TextColors.GREEN, senderName, TextColors.WHITE, " accepted your TPA Request."));
		event.getRecipient().setLocation(event.getSender().getLocation());
	}

	@Listener
	public void tpaHereAcceptEventHandler(TPAHereAcceptEvent event)
	{
		String recipientName = event.getRecipient().getName();
		event.getSender().sendMessage(Texts.of(TextColors.GREEN, recipientName, TextColors.WHITE, " accepted your TPA Here Request."));
		event.getSender().setLocation(event.getRecipient().getLocation());
	}

	@Listener
	public void tpaHereEventHandler(TPAHereEvent event)
	{
		String senderName = event.getSender().getName();
		event.getRecipient().sendMessage(
			Texts.of(TextColors.BLUE, senderName, TextColors.GOLD, " has requested for you to teleport to them.", TextColors.RED,
				" You have 10 seconds to do /tpaccept to accept the request"));

		// Adds Invite to List
		final PendingInvitation invite = new PendingInvitation(event.getSender(), event.getRecipient());
		invite.isTPAHere = true;
		EssentialCmds.pendingInvites.add(invite);

		// Removes Invite after 10 Seconds
		SchedulerService scheduler = EssentialCmds.game.getScheduler();
		Task.Builder taskBuilder = scheduler.createTaskBuilder();

		taskBuilder.execute(() -> {
			if (EssentialCmds.pendingInvites.contains(invite))
			{
				EssentialCmds.pendingInvites.remove(invite);
			}
		}).delay(10, TimeUnit.SECONDS).name("EssentialCmds - Remove Pending Invite")
			.submit(EssentialCmds.game.getPluginManager().getPlugin("EssentialCmds").get().getInstance());
	}
}
