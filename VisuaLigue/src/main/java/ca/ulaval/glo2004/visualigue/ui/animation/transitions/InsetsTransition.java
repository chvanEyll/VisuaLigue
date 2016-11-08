package ca.ulaval.glo2004.visualigue.ui.animation.transitions;

import ca.ulaval.glo2004.visualigue.utils.math.easing.EasingFunction;
import javafx.geometry.Insets;

public class InsetsTransition extends Transition<Insets> {

    public InsetsTransition(EasingFunction easingFunction) {
        super(easingFunction);
    }

    @Override
    public Insets animate(Insets startInset, Insets endInset, Long elapsedTime, Long duration) {
        Double newLeft = endInset.getLeft();
        Double newRight = endInset.getRight();
        Double newTop = endInset.getTop();
        Double newBottom = endInset.getBottom();
        if (startInset.getLeft() != endInset.getLeft()) {
            newLeft = easingFunction.ease(startInset.getLeft(), endInset.getLeft(), elapsedTime, duration);
        }
        if (startInset.getRight() != endInset.getRight()) {
            newRight = easingFunction.ease(startInset.getRight(), endInset.getRight(), elapsedTime, duration);
        }
        if (startInset.getTop() != endInset.getTop()) {
            newTop = easingFunction.ease(startInset.getTop(), endInset.getTop(), elapsedTime, duration);
        }
        if (startInset.getBottom() != endInset.getBottom()) {
            newBottom = easingFunction.ease(startInset.getBottom(), endInset.getBottom(), elapsedTime, duration);
        }
        return new Insets(newLeft, newRight, newTop, newBottom);
    }
}
