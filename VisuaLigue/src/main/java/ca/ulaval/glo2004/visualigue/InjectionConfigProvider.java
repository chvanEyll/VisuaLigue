package ca.ulaval.glo2004.visualigue;

import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import ca.ulaval.glo2004.visualigue.persistence.sport.XmlSportRepository;
import ca.ulaval.glo2004.visualigue.persistence.sport.XmlSportRootElement;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class InjectionConfigProvider extends AbstractModule {

    @Override
    protected void configure() {
        bind(SportRepository.class).to(XmlSportRepository.class);
    }

    @Provides
    XmlRepositoryMarshaller<XmlSportRootElement> provideXmlSportRepository() {
        return new XmlRepositoryMarshaller<>(XmlSportRootElement.class, VisuaLigue.getAppDataDirectory() + "/sports.xml");
    }

}
