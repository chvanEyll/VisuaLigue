package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actor.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerCreationCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Position position;
    @Inject private PlayRepository playRepository;

    private Play play;
    private PlayerInstance playerInstance;

    public PlayerCreationCommand(UUID playUUID, Integer time, UUID playerCategoryUUID, TeamSide teamSide, Double orientation, Position position) {
        this.playUUID = playUUID;
        this.time = time;
        this.playerCategoryUUID = playerCategoryUUID;
        this.teamSide = teamSide;
        this.orientation = orientation;
        this.position = position;
    }

    @Override
    public void execute() throws PlayNotFoundException {
        play = playRepository.get(playUUID);
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.of(orientation));
        playerInstance = new PlayerInstance(play.getSport().getPlayerCategory(playerCategoryUUID), teamSide);
        play.mergeActorState(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, playerInstance, Optional.empty());
    }

}
