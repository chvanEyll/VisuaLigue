package ca.ulaval.glo2004.visualigue.utils;

import java.util.TimerTask;

public class TimerTaskUtils {

    public static TimerTask wrap(Runnable r) {
        return new TimerTask() {

            @Override
            public void run() {
                r.run();
            }
        };
    }

}
