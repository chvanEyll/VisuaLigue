package ca.ulaval.glo2004.visualigue.services.play.commands;

import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.play.PlayNotFoundException;
import ca.ulaval.glo2004.visualigue.domain.play.PlayRepository;
import ca.ulaval.glo2004.visualigue.domain.play.actor.player.Player;
import ca.ulaval.glo2004.visualigue.domain.play.actor.player.TeamSide;
import ca.ulaval.glo2004.visualigue.domain.play.actorstate.PlayerState;
import ca.ulaval.glo2004.visualigue.domain.play.position.Position;
import java.util.UUID;
import javax.inject.Inject;

public class PlayerCreationCommand implements Command {

    private UUID playUUID;
    private Integer time;
    private UUID playerCategoryUUID;
    private TeamSide teamSide;
    private Double orientation;
    private Position position;
    @Inject PlayRepository playRepository;

    private Play play;
    private PlayerState playerState;
    private Player player;

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
        playerState = new PlayerState(position, orientation);
        player = new Player(play.getSport().getPlayerCategory(playerCategoryUUID), teamSide);
        play.mergeActorState(time, player, playerState);
    }

    @Override
    public void revert() {
        play.removeActorState(time, player, playerState);
    }

}
