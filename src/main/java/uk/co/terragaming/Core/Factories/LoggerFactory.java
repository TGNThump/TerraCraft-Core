package uk.co.terragaming.Core.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.Core.Util.UtilModule;
import uk.co.terragaming.Core.Util.Logger.TerraLogger;
import dagger.Component;


@Component(modules = UtilModule.class)
@Singleton
public interface LoggerFactory extends BaseFactory<TerraLogger>{
	TerraLogger make();
}
