/**
 * Code revised wrt the A3-Solutions posted on myCourses
 * @author ferrie
 *
 * This class provides a single instance of a ball falling under the influence of gravity.
 * Because it is an extension of the Thread class, each instance will run concurrently, with animations on the screen as a side effect.
 * We take advantage here of the fact that the run method associated with the Graphics Program class runs in a separate thread.
 *
 * /

import java.awt.Color;
import acm.graphics.*;

/**
 * code from assignment 2 and the solution to assignment 3 with modification
 */
public class gBall extends Thread{

    /**
     * Constructor specifies parameters for simulation
     */

    double Xi;
    double Yi;
    double bSize;
    Color bColor;
    double bLoss;
    double bVol;
    boolean bState;
    public GOval myBall;
    double Xt;
    double Yt;

    private static final double G = 9.8;
    private static final double INTERVAL_TIME = 0.1;

//    boolean isRunning = false;

    public gBall(double Xi, double Yi, double bSize, Color bColor, double bLoss, double bVol){
        super();
        this.Xi = Xi;
        this.Yi = Yi;
        this.bSize = bSize;
        this.bColor = bColor;
        this.bLoss = bLoss;
        this.bVol = bVol;

        // create instance of ball using specified parameters
//        myBall = new GOval(Xi,Yi,bSize,bSize);
        myBall = new GOval(gUtil.XtoScreen(Xi-bSize),gUtil.YtoScreen(Yi+bSize),gUtil.LtoScreen(2*bSize),gUtil.LtoScreen(2*bSize));
        myBall.setColor(bColor);
        myBall.setFilled(true);
        myBall.setVisible(true);
        bState = true;              // simulation on by default
    }

    /**
     * the run method implements the simulation of the bouncing ball
     */

    public void run() {
        double h = Yi;              // initial height of drop
        double totalTime = 0;       // total running time from the start of the simulation
        double t = 0;               // simulation clock
//        double initialUpPosition = 0;
//        boolean directionUp = false;
        int dir = 0;                // 0 for down, 1 for up
        double lastTop = Yi;        // height of last drop
        double xPos = Xi;
        double vx = bVol;

        double vt = Math.sqrt(2*G*Yi);
        double elSqRt = Math.sqrt(1.0 - bLoss);
//        boolean run = true;

        while(bState) {
//            isRunning = true;

            if (dir == 0) {
                h = lastTop - 0.5 * G * Math.pow(t, 2);
                if (h <= bSize){
                    dir = 1;
                    lastTop = bSize;
                    Yi = h;
//                    initialUpPosition = h;
//                    directionUp = true;
                    t = 0;
                    vt = vt * elSqRt;

//                    if (vt <= 0.5){
//                        run = false;
//                        break;
//                    }
                }
            }

            else {
                h = bSize + vt * t - 0.5 * G * Math.pow(t,2);
                if (h < lastTop){
                    if (h <= bSize){
                        break;      // stop simulation when top of last excursion is ball diameter
                    }
                    dir = 0;
                    t = 0;
//                    Yi = h;
                }
                lastTop = h;
//                else {
//                    directionUp = false;
//                    t = 0;
//                }
            }

            try{
                Thread.sleep((long) (INTERVAL_TIME*500));
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

            Yt = Math.max(bSize, h);
            Xt = Xi + bVol * totalTime;

//            Xi = Xi + bVol * INTERVAL_TIME;
            t += INTERVAL_TIME;
            totalTime += INTERVAL_TIME;

//            myBall.setLocation(Xi, 600 - bSize - h);
            myBall.setLocation(gUtil.XtoScreen(Xt - bSize), gUtil.YtoScreen(Yt+bSize));
        }
        // jump out of the bouncing simulation
//        while (vt <= 0.5){
//            isRunning = false;
//        }
        bState = false;
    }

    /**
     * Enable/Disable simulation asynchronously
     * @param boolean state
     * @return void
     */
    void setBState(boolean state){
        bState = state;
    }

    /**
     * method to move the ball to a specific location
     * @param x
     * @param y
     */
    public void move(double x, double y){
        Xt = x;
        Yt = y;
//        dy = 600 - bSize*8;
//        myBall.setLocation(x,600 - bSize);
        myBall.setLocation(gUtil.XtoScreen(Xt),gUtil.YtoScreen(Yt));
    }
}
