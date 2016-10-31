package ca.ulaval.glo2004.visualigue;

import com.google.inject.Guice;
import javax.inject.Injector;
import javax.inject.Singleton;

@Singleton
public class GuiceInjector {

    private static final Injector injector = Guice.createInjector(new InjectionConfigProvider());

    public static Injector getInstance() {
        return injector;
    }

}
