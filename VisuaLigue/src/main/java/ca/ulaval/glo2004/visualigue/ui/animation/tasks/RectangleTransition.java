package ca.ulaval.glo2004.visualigue.ui.animation.tasks;

import ca.ulaval.glo2004.visualigue.ui.animation.easing.EasingFunction;
import java.time.Duration;
import javafx.scene.shape.Rectangle;

public class RectangleTransition extends Transition<Rectangle> {

    public RectangleTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Rectangle animate(Rectangle startRect, Rectangle endRect, Duration elapsedTime, Duration duration) {
        Double newX = endRect.getX();
        Double newY = endRect.getY();
        Double newWidth = endRect.getWidth();
        Double newHeight = endRect.getHeight();
        if (startRect.getX() != endRect.getX()) {
            newX = easingFunction.ease(startRect.getX(), endRect.getX(), elapsedTime.toMillis(), duration.toMillis());
        }
        if (startRect.getY() != endRect.getY()) {
            newY = easingFunction.ease(startRect.getY(), endRect.getY(), elapsedTime.toMillis(), duration.toMillis());
        }
        if (startRect.getWidth() != endRect.getWidth()) {
            newWidth = easingFunction.ease(startRect.getWidth(), endRect.getWidth(), elapsedTime.toMillis(), duration.toMillis());
        }
        if (startRect.getHeight() != endRect.getHeight()) {
            newHeight = easingFunction.ease(startRect.getHeight(), endRect.getHeight(), elapsedTime.toMillis(), duration.toMillis());
        }
        return new Rectangle(newX, newY, newWidth, newHeight);
    }
}
