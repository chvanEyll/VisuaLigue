package ca.ulaval.glo2004.visualigue.ui.converters;

import ca.ulaval.glo2004.visualigue.domain.play.frame.Frame;
import ca.ulaval.glo2004.visualigue.ui.models.FrameModel;

public class FrameModelConverter {

    public FrameModel convert(Frame frame) {
        FrameModel model = new FrameModel();
        model.setUUID(frame.getUUID());
        model.setIsNew(false);

        return model;
    }

}
