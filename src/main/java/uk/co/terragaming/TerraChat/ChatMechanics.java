package uk.co.terragaming.TerraChat;

import javax.inject.Inject;

import uk.co.terragaming.TerraCore.Foundation.Mechanic;
import uk.co.terragaming.TerraCore.Mechanics.Database.Database;
import dagger.Module;

@Module
public class ChatMechanics extends Mechanic{
	
	@Inject Database database;
}
