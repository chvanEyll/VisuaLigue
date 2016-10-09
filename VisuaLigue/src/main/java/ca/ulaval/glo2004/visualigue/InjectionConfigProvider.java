package ca.ulaval.glo2004.visualigue;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.persistence.FileBasedImageRepository;
import ca.ulaval.glo2004.visualigue.persistence.XmlSportRepository;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class InjectionConfigProvider extends AbstractModule {

    @Override
    protected void configure() {
        bind(SportRepository.class).to(XmlSportRepository.class);
        bind(ImageRepository.class).to(FileBasedImageRepository.class);
    }

    @Provides
    XmlRepositoryMarshaller<Sport> provideXmlSportRepository() {
        return new XmlRepositoryMarshaller<>(Sport.class, VisuaLigue.getRepositoryDirectory() + "/sports");
    }
}
