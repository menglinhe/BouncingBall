/**
 * Code revised wrt the A3-Solutions posted on myCourses
 * @author ferrie
 *
 * Some helper methods to translate simulation coordinates to screen coordinates
 */

import sun.tools.jstat.Scale;

public class gUtil {

    private static final int HEIGHT = 600;
    private static final double SCALE = HEIGHT/100;

    /**
     * X cpprdinate to screen x
     * @param X
     * @return x screen coordinate - integer
     */
    static int XtoScreen(double X){
        return (int) (X * SCALE);
    }

    /**
     * Y coordinate to screen y
     * @param Y
     * @return y screen coordinate - integer
     */
    static int YtoScreen(double Y){
        return (int) (HEIGHT - Y * SCALE);
    }

    /**
     * Length to screen length
     * @param length - double
     * @return sLen - integer
     */
    static int LtoScreen(double length){
        return (int) (length * SCALE);
    }

    /**
     * Delay for <int> milliseconds
     * @param time
     * @return void
     */
    static void delay (long time) {
        long start = System.currentTimeMillis();
        while (true){
            long current = System.currentTimeMillis();
            long delta = current - start;
            if (delta >= time){
                break;
            }
        }
    }
}
