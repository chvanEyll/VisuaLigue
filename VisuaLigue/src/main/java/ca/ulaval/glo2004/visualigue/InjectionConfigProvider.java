package ca.ulaval.glo2004.visualigue;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.obstacle.Obstacle;
import ca.ulaval.glo2004.visualigue.domain.obstacle.ObstacleRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.sport.playercategory.PlayerCategoryRepository;
import ca.ulaval.glo2004.visualigue.persistence.*;
import ca.ulaval.glo2004.visualigue.persistence.marshalling.XmlRepositoryMarshaller;
import com.google.inject.AbstractModule;
import com.google.inject.Provides;

public class InjectionConfigProvider extends AbstractModule {

    @Override
    protected void configure() {
        bind(SportRepository.class).to(XmlSportRepository.class);
        bind(ImageRepository.class).to(FileBasedImageRepository.class);
        bind(ObstacleRepository.class).to(XmlObstacleRepository.class);
        bind(PlayRepository.class).to(XmlPlayRepository.class);
        bind(PlayerCategoryRepository.class).to(XmlPlayerCategoryRepository.class);
    }

    @Provides
    XmlRepositoryMarshaller<Sport> provideXmlSportRepository() {
        return new XmlRepositoryMarshaller<>(Sport.class, VisuaLigue.getRepositoryDirectory() + "/sports");
    }

    @Provides
    XmlRepositoryMarshaller<Obstacle> provideXmlObstacleRepository() {
        return new XmlRepositoryMarshaller<>(Obstacle.class, VisuaLigue.getRepositoryDirectory() + "/obstacles");
    }

    @Provides
    XmlRepositoryMarshaller<Play> provideXmlPlayRepository() {
        return new XmlRepositoryMarshaller<>(Play.class, VisuaLigue.getRepositoryDirectory() + "/plays");
    }

    @Provides
    XmlRepositoryMarshaller<PlayerCategory> provideXmlPlayerCategoryRepository() {
        return new XmlRepositoryMarshaller<>(PlayerCategory.class, VisuaLigue.getRepositoryDirectory() + "/player-categories");
    }
}
