package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import javafx.scene.shape.Rectangle;

public class RectangleTransition extends Transition<Rectangle> {

    public RectangleTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Rectangle animate(Rectangle startRect, Rectangle endRect, Long elapsedTime, Long duration) {
        Double newX = endRect.getX();
        Double newY = endRect.getY();
        Double newWidth = endRect.getWidth();
        Double newHeight = endRect.getHeight();
        if (startRect.getX() != endRect.getX()) {
            newX = easingFunction.ease(startRect.getX(), endRect.getX(), elapsedTime, duration);
        }
        if (startRect.getY() != endRect.getY()) {
            newY = easingFunction.ease(startRect.getY(), endRect.getY(), elapsedTime, duration);
        }
        if (startRect.getWidth() != endRect.getWidth()) {
            newWidth = easingFunction.ease(startRect.getWidth(), endRect.getWidth(), elapsedTime, duration);
        }
        if (startRect.getHeight() != endRect.getHeight()) {
            newHeight = easingFunction.ease(startRect.getHeight(), endRect.getHeight(), elapsedTime, duration);
        }
        return new Rectangle(newX, newY, newWidth, newHeight);
    }
}
