/**
 * Code revised wrt the A3-Solutions posted on myCourses
 * @author ferrie
 *
 * The main class in Assignemnt 3 and 4
 *
 * Course project of ECSE202 - Introduction to Software Development
 * Project descrption: generate random ball with random color, random velocity, random number, random size by
 * setting a range using sliders, once all the random generated balls stop bouncing, click to button to sort
 * the ball in ascending size order.
 */

import acm.graphics.GLabel;
import acm.graphics.GObject;
import acm.graphics.GOval;
import acm.graphics.GRect;
import acm.program.GraphicsProgram;
import acm.util.RandomGenerator;

import javax.swing.*;
import javax.swing.event.ChangeEvent;
import javax.swing.event.ChangeListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.awt.event.MouseEvent;

/**
 * code from assignment 2 and solution to assignment 3 with modification
 */
public class bSim extends GraphicsProgram implements ChangeListener, ItemListener{

    // initialize some variables and constants

    private static final int WIDTH = 1400;
    private static final int HEIGHT = 600;
    private static final int OFFSET = 200;

    public int NUMBALLS=15;
    private double MINSIZE = 1*100;
    private double MAXSIZE = 8*100;
    private double XMIN = 10;
    private double XMAX = 50;
    private double YMIN = 50;
    private double YMAX = 100;
    private double EMIN = 0.4*100;
    private double EMAX = 0.9*100;
    private double VMIN = 0.5*100;
    private double VMAX = 3.0*100;

//    public static ArrayList<gBall> sorted = new ArrayList<gBall>();

    RandomGenerator rgen = new RandomGenerator();
    bTree myTree= new bTree();

    // variables for the bonus part in the assignment
    private GObject gobj;           // the object being dragged
    private double lastX;           // the last mouse X position
    private double lastY;           // the last mouse Y position
    private GOval clickedOval;      // will cast GObject to this variable
    private bNode clickedNode;      // you will pass ClickedOval to a function that will return a node containing the underlying ball object that this GOval belongs to

    // sliders for all the variable parameters
    private JComboBox<String> bSimC;
    private sliderBox numballs;
    private sliderBox minSize;
    private sliderBox maxSize;
    private sliderBox minX;
    private sliderBox maxX;
    private sliderBox minY;
    private sliderBox maxY;
    private sliderBox minLoss;
    private sliderBox maxLoss;
    private sliderBox minVol;
    private sliderBox maxVol;
//    private sliderBox colorSlider;
//    private sliderBox sizeSlider;
//    private sliderBox eLoss;
//    private sliderBox xVol;

    public static void main(String[] args) { new bSim().start(args);
    }

    public gBall[] iBall;

    public void run() {

        setChoosers();          // set the JCombox for bSimC

        this.resize(WIDTH, HEIGHT+OFFSET);          // set up the display canvas
        GRect ground = new GRect (0, 600, 1200, 3);     // draw a ground line
        ground.setFilled(true);
        add(ground);
        this.setVisible(true);

//        while (myTree.isRunning());     // if simulation stops, do the following

        // prompt out a message to let user do click
//        GLabel prompt = new GLabel("Click to continue");
//        prompt.setLocation(WIDTH - prompt.getWidth()-50, HEIGHT - prompt.getHeight());
//        prompt.setColor(Color.RED);
//        add(prompt);

        // wait for the action of user
//        waitForClick();

        // remove the prompt out message and sorted the ball
//        remove(prompt);
//        myTree.moveSort();
    }

    /**
     * initialize the JButton event, to remove all the balls on screen and print a message to user, also to initialize the slider
     */

    public void init(){

        JButton click = new JButton("Click to finish!");        // button for Click to finish!
        click.addActionListener(this);
        add(click, SOUTH);
        addActionListeners();

        JButton start = new JButton("Start");                   // the Start button, to start the simulation
        start.addActionListener(this);
        add(start,NORTH);

        JButton sort = new JButton("Sort");                     // Sort button, to sort the ball by size
        sort.addActionListener(this);
        add(sort,NORTH);

        addActionListeners();
        addMouseListener(this);

        JPanel eastP = new JPanel();                                 // set up a panel to handle all the sliders
        JLabel generate = new JLabel("General Simulation Parameters");
//        JLabel info = new JLabel("Please change all the parameters using slider and start the simulation");
        eastP.add(generate);
//        eastP.add(info);

        eastP.setLayout(new GridLayout(17,1));

        numballs = new sliderBox("NUMBALLS", 1, 15, 25);    //slider for the number of balls
        eastP.add(numballs.myPanel, "EAST");
        numballs.mySlider.addChangeListener((ChangeListener)this);

        minSize = new sliderBox("MIN SIZE (%)", 1.0*100, 13.0*100, 25.0*100);   // slider for the minimize ball size
        eastP.add(minSize.myPanel, "EAST");                             // multiply by 100 in order to use the double value (same for all double values)
        minSize.mySlider.addChangeListener((ChangeListener)this);

        maxSize = new sliderBox("MAX SIZE (%)", 1.0*100, 13.0*100, 25.0*100);   // slider for the max ball size
        eastP.add(maxSize.myPanel, "EAST");
        maxSize.mySlider.addChangeListener((ChangeListener)this);

        minX = new sliderBox("X MIN", 1.0, 100.0, 200.0);       // slider for min x position
        eastP.add(minX.myPanel, "EAST");
        minX.mySlider.addChangeListener((ChangeListener)this);

        maxX = new sliderBox("X MAX", 1.0, 100.0, 200.0);       // slider for max x position
        eastP.add(maxX.myPanel, "EAST");
        maxX.mySlider.addChangeListener((ChangeListener)this);

        minY = new sliderBox("Y MIN", 1.0, 50.0, 100.0);        // slider for min y position
        eastP.add(minY.myPanel, "EAST");
        minY.mySlider.addChangeListener((ChangeListener)this);

        maxY = new sliderBox("Y MAX", 1.0, 50.0, 100.0);        // slider for max y position
        eastP.add(maxY.myPanel, "EAST");
        maxY.mySlider.addChangeListener((ChangeListener)this);

        minLoss = new sliderBox("LOSS MIN (%)", 0.0, 0.5*100, 1.0*100);            // slider for min energy loss
        eastP.add(minLoss.myPanel, "EAST");
        minLoss.mySlider.addChangeListener((ChangeListener)this);

        maxLoss = new sliderBox("LOSS MAX (%)", 0.0, 0.5*100, 1.0*100);           // slider for max energy loss
        eastP.add(maxLoss.myPanel, "EAST");
        maxLoss.mySlider.addChangeListener((ChangeListener)this);

        minVol = new sliderBox("X VEL MIN (%)", 0.0, 5.0*100, 10.0*100);          // slider for min x velocity
        eastP.add(minVol.myPanel, "EAST");
        minVol.mySlider.addChangeListener((ChangeListener)this);

        maxVol = new sliderBox("X VEL MIN (%)", 0.0, 5.0*100, 10.0*100);          // slider for max x velocity
        eastP.add(maxVol.myPanel, "EAST");
        maxVol.mySlider.addChangeListener((ChangeListener)this);

//        JLabel singleGen = new JLabel("Single Ball Instance Parameters");
//        eastP.add(singleGen);
//
//        colorSlider = new sliderBox("Color", );                         // how to add code for changing the color
//        eastP.add(colorSlider.myPanel, "EAST");
//        colorSlider.mySlider.addChangeListener((ChangeListener)this);
//
//        sizeSlider = new sliderBox("Ball Size", 1.0*100, 4.0*100, 8.0*100);
//        eastP.add(sizeSlider.myPanel, "EAST");
//        sizeSlider.mySlider.addChangeListener((ChangeListener)this);
//
//        eLoss = new sliderBox("E Loss", 0.4*100, 0.7*100, 1.0*100);
//        eastP.add(eLoss.myPanel, "EAST");
//        eLoss.mySlider.addChangeListener((ChangeListener)this);
//
//        xVol = new sliderBox("X Vel", 1.0*100, 3.0*100, 5.0*100);
//        eastP.add(xVol.myPanel, "EAST");
//        xVol.mySlider.addChangeListener((ChangeListener)this);

        add(eastP);         // add the panel to canvas
    }


    /**
     * method for the choosers, JCombox
     * do not have start inside the JCombox, have a separate start button to start the simulation
     */
    void setChoosers(){
        bSimC = new JComboBox<String>();
        bSimC.addItem("bSim");
        bSimC.addItem("Clear");
        bSimC.addItem("Stop");
        bSimC.addItem("Quit");
        add(bSimC,NORTH);
        addJComboListeners();
    }

    void addJComboListeners(){
        bSimC.addItemListener((ItemListener) this);
    }

    /**
     * method to set function to each chooser inside the JCombox
     * @param e
     */
    public void itemStateChanged(ItemEvent e) {
        // code related to the actions of JComboBox
        JComboBox source = (JComboBox) e.getSource();
        if (source == bSimC) {
            if (bSimC.getSelectedIndex() == 0) {                // bSim
                System.out.println("Starting simulation");

            } else if (bSimC.getSelectedIndex()==1){            // Clear
                removeAll();
                GRect ground = new GRect (0, 600, 1200, 3);
                ground.setFilled(true);
                add(ground);
                this.setVisible(true);
            }
            else if (bSimC.getSelectedIndex()==2){              // Stop
                pause(10000);                   // a 10s pause for the whole applet
            }
            else if (bSimC.getSelectedIndex()==3){              // Quit
                System.exit(0);             // kill the whole applet
            }
        }

    }

    /**
     * get & set slider value
     * @param e
     */

    public void stateChanged(ChangeEvent e){
        JSlider source = (JSlider)e.getSource();
        if (source == numballs.mySlider){
            NUMBALLS = numballs.getISlider();
            numballs.setISlider(NUMBALLS);
        }
        else if (source == minSize.mySlider){
            MINSIZE = minSize.getISlider();
            minSize.setISlider((int)MINSIZE);
        }
        else if (source == maxSize.mySlider){
            MAXSIZE = maxSize.getISlider();
            maxSize.setISlider((int)MAXSIZE);
        }
        else if (source == minX.mySlider){
            XMIN = minX.getISlider();
            minX.setISlider((int)XMIN);
        }
        else if (source == maxX.mySlider){
            XMAX = maxX.getISlider();
            maxX.setISlider((int)XMAX);
        }
        else if (source == minY.mySlider){
            YMIN = minY.getISlider();
            minY.setISlider((int)YMIN);
        }
        else if (source == maxY.mySlider){
            YMAX = maxY.getISlider();
            maxY.setISlider((int)YMAX);
        }
        else if (source == minLoss.mySlider){
            EMIN = minLoss.getISlider();
            minLoss.setISlider((int)EMIN);
        }
        else if (source == maxLoss.mySlider){
            EMAX = maxLoss.getISlider();
            maxLoss.setISlider((int)EMAX);
        }
        else if (source == minVol.mySlider){
            VMIN = minVol.getISlider();
            minVol.setISlider((int)VMIN);
        }
        else if (source == maxVol.mySlider){
            VMAX = maxVol.getISlider();
            maxVol.setISlider((int)VMAX);
        }
//        else if (source == colorSlider.mySlider){
//            colorSliderVal = colorSlider.getISlider();
//            colorSlider.setISlider(colorSliderVal);
//        }
//        else if (source == sizeSlider.mySlider){
//            sizeSliderVal = sizeSlider.getISlider();
//            sizeSlider.setISlider(sizeSliderVal);
//        }
//        else if (source == eLoss.mySlider){
//            eLossVal = eLoss.getISlider();
//            eLoss.setISlider(eLossVal);
//        }
//        else if (source == xVol.mySlider){
//            xVolVal = xVol.getISlider();
//            xVol.setISlider(xVolVal);
//        }
    }

    public void actionPerformed(ActionEvent e){

        // button for Click to finish!
        if (e.getActionCommand().equals("Click to finish!")) {
            removeAll();
            GLabel done = new GLabel("Have a nice day!");
            done.setFont(new Font("Serif", Font.BOLD, 25));
            double doneWidth = done.getWidth();
            double doneHeight = done.getHeight();
            done.setLocation((400-doneWidth)/2, (HEIGHT-doneHeight)/2);
            add(done);
            System.out.println("===== min lost val =====" + EMIN);
        }

        // button for Sort
        if (e.getActionCommand().equals("Sort")){
            myTree.moveSort();
        }

        /**
         * the sliders work
         * start the simulation only when all the parameters have been changed
         * can not start the program without modifying parameters
         * need to change at least 3 parameters (numballs, xmin, xmax) to start the simulation
         * if not modifying xvol -> bouncing vertically
         * if not modifying iloss -> no energy loss
         */
        if (e.getActionCommand().equals("Start")){

            for (int i = 0; i < NUMBALLS; i++){

                double Xi = rgen.nextDouble(XMIN/10, XMAX/10);
                double Yi = rgen.nextDouble(YMIN, YMAX);
                double iSize = rgen.nextDouble(MINSIZE/100, MAXSIZE/100);       // divide by 100 and cast to double in order to use the double value
                Color iColor = rgen.nextColor();
                double iLoss = rgen.nextDouble(EMIN/100, EMAX/100);
                double iVol = rgen.nextDouble(VMIN/100, VMAX/100);
                gBall iBall = new gBall(Xi, Yi, iSize, iColor, iLoss, iVol);
                add(iBall.myBall);
                myTree.addNode(iBall);
                iBall.start();

                System.out.println("Xi === " + Xi);
                System.out.println("Yi === " + Yi);
                System.out.println("iSize === " + iSize);
                System.out.println("iLoss === " + iLoss);
                System.out.println("iVol === " + iVol);
                System.out.println("iBall === " + iBall);
            }
            System.out.println("NUM OF BALLS === " + NUMBALLS);
            System.out.println("X MAX IS ==== "+ XMIN + " TO " + XMAX);
            System.out.println("Y MAX IS ==== "+ YMIN + " TO " + YMAX);
            System.out.println("SIZE MAX IS ==== "+ MINSIZE + " TO " + MAXSIZE);
            System.out.println("E MAX IS ==== "+ EMIN + " TO " + EMAX);
            System.out.println("V MAX IS ==== "+ VMIN + " TO " + VMAX);
        }

    }

    /**
     * method called on mouse press to record the coordinates of the click
     * it's for the bonus part
     */
    public void mousePressed(MouseEvent e) {
        lastX = e.getX();
        lastY = e.getY();
        gobj = getElementAt(lastX, lastY);
        System.out.println("lastX " + lastX + " ===== lastY " + lastY);

        if (!(gobj == null)){
            clickedOval = (GOval)gobj;      // casting to GOval
            // next find the object in the bTree and pass back the cantaining node - this example uses a mouse called
            // findNode - existing methods you have previously written
            clickedNode = myTree.findNode(clickedOval);
            // change parameters of object as needed
            // the following is an example that stops the simulation for the assocaited ball thread
            clickedNode.data.setBState(false);
        }
        else {
            println("null");        // show in console that click was not on an object
        }
    }
}



