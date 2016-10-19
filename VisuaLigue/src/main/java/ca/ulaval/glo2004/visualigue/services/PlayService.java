package ca.ulaval.glo2004.visualigue.services;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.*;
import ca.ulaval.glo2004.visualigue.utils.EventHandler;
import java.util.List;
import java.util.UUID;
import java.util.function.Function;
import javax.inject.Inject;
import javax.inject.Singleton;
import javax.swing.SortOrder;

@Singleton
public class PlayService {

    private final PlayRepository playRepository;
    private final PlayFactory playFactory;
    private final ImageRepository imageRepository;

    public EventHandler<Play> onPlayCreated = new EventHandler<>();
    public EventHandler<Play> onPlayUpdated = new EventHandler<>();
    public EventHandler<Play> onPlayDeleted = new EventHandler<>();

    @Inject
    public PlayService(final PlayRepository playRepository, final ImageRepository imageRepository, final PlayFactory playFactory) {
        this.playRepository = playRepository;
        this.imageRepository = imageRepository;
        this.playFactory = playFactory;
    }

    public UUID createPlay(String name) throws PlayAlreadyExistsException {
        Play play = playFactory.create(name);
        playRepository.persist(play);
        onPlayCreated.fire(this, play);
        return play.getUUID();
    }

    public void updatePlay(UUID playUUID, String title) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        play.setTitle(title);
        playRepository.update(play);
        onPlayUpdated.fire(this, play);
    }

    public void deletePlay(UUID playUUID) throws PlayNotFoundException {
        Play play = playRepository.get(playUUID);
        playRepository.delete(playUUID);
        onPlayDeleted.fire(this, play);
    }

    public Play getPlay(UUID playUUID) throws PlayNotFoundException {
        return playRepository.get(playUUID);
    }

    public List<Play> getPlays(Function<Play, Comparable> sortFunction, SortOrder sortOrder) {
        return playRepository.getAll(sortFunction, sortOrder);
    }
}
