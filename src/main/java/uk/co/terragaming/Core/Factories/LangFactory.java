package uk.co.terragaming.Core.Factories;

import javax.inject.Singleton;

import uk.co.terragaming.Core.Util.UtilModule;
import uk.co.terragaming.Core.Util.Language.Lang;
import dagger.Component;


@Component(modules = UtilModule.class)
@Singleton
public interface LangFactory extends BaseFactory<Lang>{
	Lang make();
}
