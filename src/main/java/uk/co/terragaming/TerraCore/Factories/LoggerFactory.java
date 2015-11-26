package uk.co.terragaming.TerraCore.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.Util.UtilModule;
import uk.co.terragaming.TerraCore.Util.Logger.TerraLogger;
import dagger.Component;


@Component(modules = UtilModule.class)
@Singleton
public interface LoggerFactory extends BaseFactory<TerraLogger>{
	TerraLogger make();
}
