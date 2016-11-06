package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.image.ImageRepository;
import ca.ulaval.glo2004.visualigue.domain.play.Play;
import ca.ulaval.glo2004.visualigue.domain.sport.playingsurface.PlayingSurface;
import ca.ulaval.glo2004.visualigue.ui.models.PlayModel;
import javax.inject.Inject;

public class PlayModelConverter {

    private ImageRepository imageRepository;
    private BallModelConverter ballModelConverter;

    @Inject
    public PlayModelConverter(ImageRepository imageRepository, BallModelConverter ballModelConverter) {
        this.imageRepository = imageRepository;
        this.ballModelConverter = ballModelConverter;
    }

    public PlayModel convert(Play play) {
        PlayModel model = new PlayModel();
        update(model, play);
        return model;
    }

    private void convertPlayingSurface(PlayingSurface playingSurface, PlayModel model) {
        model.playingSurfaceWidth.set(playingSurface.getWidth());
        model.playingSurfaceLength.set(playingSurface.getLength());
        model.playingSurfaceWidthUnits.set(playingSurface.getWidthUnits());
        model.playingSurfaceLengthUnits.set(playingSurface.getLengthUnits());
        if (playingSurface.hasCustomImage()) {
            model.customPlayingSurfaceImagePathName.set(imageRepository.get(playingSurface.getCustomImageUUID()));
        }
        model.builtInPlayingSurfaceImagePathName.set(playingSurface.getBuiltInImagePathName());
    }

    public void update(PlayModel model, Play play) {
        model.setUUID(play.getUUID());
        model.setIsNew(false);
        model.title.set(play.getTitle());
        if (play.hasThumbnail()) {
            model.thumbnailImagePathName.set(imageRepository.get(play.getThumbnailImageUUID()));
        }
        model.defaultThumbnailImagePathName.set(play.getDefaultThumbnailImage());
        model.ballModel = ballModelConverter.convert(play.getSport().getBall());
        model.sportUUID.set(play.getSport().getUUID());
        model.playLength.set(play.getLength());
        model.timelineLength.set(play.getTimelineLength());
        model.keyPointInterval.set(play.getKeyPointInterval());
        model.numberOfKeyPoints.set(play.getNumberOfKeyPoints());
        convertPlayingSurface(play.getSport().getPlayingSurface(), model);
    }

}
