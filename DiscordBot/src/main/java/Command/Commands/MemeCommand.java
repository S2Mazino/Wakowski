package Command.Commands;

import java.awt.Color;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;

import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

import Command.CommandContext;
import Command.ICommand;
import net.dv8tion.jda.api.EmbedBuilder;

/**
 * Meme generator that uses heroku meme api, retrieves JSON data and parse it into usable data.
 * Use EmbedBuilder to start building the components and sends the final product.
 * @author Nordine
 *
 */
public class MemeCommand implements ICommand {

	@Override
	public void handle(CommandContext ctx) {
		//expected output variables needed
		JSONParser parser = new JSONParser();
		String postLink = "";
		String title = "";
		String url = "";
		
		try {
			//open connection
			URL memeURL = new URL("https://meme-api.herokuapp.com/gimme");
			BufferedReader reader = new BufferedReader(new InputStreamReader(memeURL.openConnection().getInputStream()));
			String lines;
			
			//parse data
			while((lines = reader.readLine()) != null) {
				JSONArray array = new JSONArray();
				array.add(parser.parse(lines));
			
				for(Object o: array) {
					JSONObject jsonObject = (JSONObject) o;
					postLink = (String) jsonObject.get("postLink");
					title = (String) jsonObject.get("title");
					url = (String) jsonObject.get("url");
				}
			}
			reader.close();
			//delete the command on discord
			ctx.getEvent().getMessage().delete();
			//setup embeder and send output
			EmbedBuilder builder = new EmbedBuilder()
					.setTitle(title, postLink)
					.setImage(url)
					.setColor(Color.GREEN);
			ctx.getEvent().getChannel().sendMessageEmbeds(builder.build()).queue();
			
			
		} catch (Exception e) {
			ctx.getEvent().getChannel().sendMessage("Something went wrong").queue();
			e.printStackTrace();
		}
		
		
	}

	@Override
	public String getName() {
		return "meme";
	}

	@Override
	public String getHelp() {
		return "Generates random meme hosted by meme-api heroku";
	}


}
