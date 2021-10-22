package Command.Commands;

import Command.CommandContext;
import Command.ICommand;

/**
 * Simple ping command.
 * @author Nordine
 *
 */
public class PingCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		//get the ping
		String ping = String.valueOf(ctx.getEvent().getJDA().getGatewayPing());
		//delete the command on discord
		ctx.getEvent().getMessage().delete();
		//send output
		ctx.getEvent().getChannel()
		.sendMessage(ping)
		.queue();
	
	}

	@Override
	public String getName() {
		return "ping";
	}


	@Override
	public String getHelp() {
		// TODO Auto-generated method stub
		return "Returns ping from user to discord servers";
	}
}
