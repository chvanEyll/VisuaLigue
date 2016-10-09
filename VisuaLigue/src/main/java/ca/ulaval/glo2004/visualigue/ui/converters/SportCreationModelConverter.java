package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageLoadException;
import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.playercategory.PlayerCategory;
import ca.ulaval.glo2004.visualigue.domain.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.domain.sport.Sport;
import ca.ulaval.glo2004.visualigue.ui.models.PlayerCategoryModel;
import ca.ulaval.glo2004.visualigue.ui.models.SportCreationModel;
import java.awt.image.BufferedImage;
import java.util.Map;
import java.util.UUID;
import java.util.logging.Level;
import java.util.logging.Logger;
import javafx.embed.swing.SwingFXUtils;
import javax.inject.Inject;

public class SportCreationModelConverter {

    ImageRepository imageRepository;

    @Inject
    public SportCreationModelConverter(ImageRepository imageRepository) {
        this.imageRepository = imageRepository;
    }

    public SportCreationModel convert(Sport sport) {
        SportCreationModel model = new SportCreationModel();
        model.setUUID(sport.getUUID());
        model.setIsNew(false);
        model.name.set(sport.getName());
        model.builtInIconPathName.set(sport.getBuiltInIconPathName());
        model.playingSurfaceWidth.set(sport.getPlayingSurface().getWidth());
        model.playingSurfaceLength.set(sport.getPlayingSurface().getLength());
        model.playingSurfaceWidthUnits.set(sport.getPlayingSurface().getWidthUnits());
        model.playingSurfaceLengthUnits.set(sport.getPlayingSurface().getLengthUnits());
        convertPlayingSurfaceImage(sport.getPlayingSurface(), model);
        convertPlayerCategories(sport.getPlayerCategories(), model);
        return model;
    }

    private void convertPlayingSurfaceImage(PlayingSurface playingSurface, SportCreationModel model) {
        if (playingSurface.hasImage()) {
            try {
                BufferedImage image = imageRepository.get(playingSurface.getImageUUID());
                model.currentPlayingSurfaceImage.set(SwingFXUtils.toFXImage(image, null));
            } catch (ImageLoadException ex) {
                Logger.getLogger(SportCreationModelConverter.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    private void convertPlayerCategories(Map<UUID, PlayerCategory> playerCategories, SportCreationModel model) {
        playerCategories.values().forEach(playerCategory -> {
            PlayerCategoryModel playerCategoryModel = new PlayerCategoryModel();
            playerCategoryModel.setUUID(playerCategory.getUUID());
            playerCategoryModel.setIsNew(false);
            playerCategoryModel.name.set(playerCategory.getName());
            playerCategoryModel.allyPlayerColor.set(playerCategory.getAllyColor());
            playerCategoryModel.opponentPlayerColor.set(playerCategory.getOpponentColor());
            playerCategoryModel.defaultNumberOfPlayers.set(playerCategory.getDefaultNumberOfPlayers());
            model.playerCategoryModels.add(playerCategoryModel);
        });
    }
}
