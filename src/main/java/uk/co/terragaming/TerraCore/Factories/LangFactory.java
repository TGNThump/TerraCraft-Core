package uk.co.terragaming.TerraCore.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.TerraCore.Util.UtilModule;
import uk.co.terragaming.TerraCore.Util.Language.Lang;
import dagger.Component;


@Component(modules = UtilModule.class)
@Singleton
public interface LangFactory extends BaseFactory<Lang>{
	Lang make();
}
