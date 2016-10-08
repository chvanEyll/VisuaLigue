package ca.ulaval.glo2004.visualigue.contexts;

import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.domain.sport.SportAlreadyExistsException;
import ca.ulaval.glo2004.visualigue.domain.sport.SportFactory;
import ca.ulaval.glo2004.visualigue.domain.sport.SportRepository;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;

public class DefaultContext extends ContextBase {

    private final SportFactory sportFactory;
    private final SportRepository sportRepository;

    @Inject
    public DefaultContext(final SportFactory sportFactory, final SportRepository sportRepository) {
        this.sportFactory = sportFactory;
        this.sportRepository = sportRepository;
    }

    @Override
    protected void applyFillers() throws Exception {
        if (!sportRepository.isEmpty()) {
            System.out.println("Repositories are not empty: Skipping default context repository filling.");
            return;
        }
        fill();
    }

    private void fill() throws SportAlreadyExistsException {
        List<Sport> sportPool = new ArrayList<>();
        Sport hockeySport = sportFactory.create("Hockey");
        Sport soccerSport = sportFactory.create("Soccer");
        Sport footballSport = sportFactory.create("Football");
        sportPool.add(hockeySport);
        sportPool.add(soccerSport);
        sportPool.add(footballSport);
        persistSports(sportPool);
    }

    private void persistSports(List<Sport> sportPool) throws SportAlreadyExistsException {
        for (Sport sport : sportPool) {
            sportRepository.persist(sport);
        }
    }
}
