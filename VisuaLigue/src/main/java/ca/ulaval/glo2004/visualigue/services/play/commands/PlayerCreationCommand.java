package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.PlayerInstance;
import ca.ulaval.glo2004.visualigue.domain.play.actorinstance.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.Optional;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerCreationCommand implements Command {

    private Play play;
    private Integer time;
    private UUID playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Position position;
    @Inject private PlayRepository playRepository;

    private PlayerInstance playerInstance;

    public PlayerCreationCommand(Play play, Integer time, UUID playerCategoryUUID, TeamSide teamSide, Double orientation, Position position) {
        this.play = play;
        this.time = time;
        this.playerCategoryUUID = playerCategoryUUID;
        this.teamSide = teamSide;
        this.orientation = orientation;
        this.position = position;
    }

    @Override
    public void execute() {
        PlayerState playerState = new PlayerState(Optional.of(position), Optional.empty(), Optional.of(orientation));
        playerInstance = new PlayerInstance(play.getSport().getPlayerCategory(playerCategoryUUID), teamSide);
        play.mergeActorState(time, playerInstance, playerState);
    }

    @Override
    public void revert() {
        play.unmergeActorState(time, playerInstance, Optional.empty());
    }

}
