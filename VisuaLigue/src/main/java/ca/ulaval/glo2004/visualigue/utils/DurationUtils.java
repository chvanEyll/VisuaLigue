package ca.ulaval.glo2004.visualigue.utils;

import java.time.Duration;

public class DurationUtils {

    public static String toHHmmSS(Duration duration) {
        long secondsAbs = Math.abs(duration.getSeconds());
        String positiveDurationString = String.format("%d:%02d:%02d", secondsAbs / 3600, (secondsAbs % 3600) / 60, secondsAbs % 60);
        return duration.getSeconds() < 0 ? "-" + positiveDurationString : positiveDurationString;
    }

    public static String toMMSSddd(Duration duration) {
        long millisAbs = Math.abs(duration.toMillis());
        String positiveDurationString = String.format("%d:%02d.%03d", millisAbs / 1000 / 60, millisAbs / 1000 % 60, (int) ((millisAbs / 1000.0 % 1) * 1000));
        return duration.getSeconds() < 0 ? "-" + positiveDurationString : positiveDurationString;
    }

}
