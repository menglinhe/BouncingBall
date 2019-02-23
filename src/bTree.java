/**
 * Code revised wrt the A3-Solutions posted on myCourses
 * Implements a binary tree class for storing gBall objects
 * @author ferrie
 */

import acm.graphics.GOval;
import acm.program.GraphicsProgram;

public class bTree extends GraphicsProgram {

    // Instance variables
    bNode root = null;
    gBall data;
    boolean running;
    double nextX;
    private static final double SEP = 0;

    /**
     * addNode method - wrapper for rNode
     */

    public void addNode(gBall data){
        root = rNode(root, data);
    }

    /**
     * rNode method - recursivelt adds a new entry into the binary tree
     * @param root
     * @param data
     * @return
     */

    private bNode rNode(bNode root, gBall data){
        if (root == null){
            bNode node = new bNode();
            node.data = data;
            node.left = null;
            node.right = null;
            root = node;
            return root;
        }
        else if (data.bSize < root.data.bSize){
            root.left = rNode(root.left, data);
        }
        else{
            root.right = rNode(root.right,data);
        }
        return root;
    }

    /**
     * inorder method - inorder traversal via call to recursive method
     */

    public void inorder(){
        traverse_inorder(root);
    }

    /**
     * traverse_inorder method - recursively traverses tree in order and prints each node
     * @param root
     */
    private void traverse_inorder(bNode root){
        if (root.left != null){
            traverse_inorder(root.left);
        }
        System.out.println(root.data.bSize);
        if (root.right != null){
            traverse_inorder(root.right);
        }
    }

    /**
     * travers the binary tree to see if the simulation has stopes
     * return true is there is still ball boucing
     * isRunning predicate - determines if simulation is still running
     */

    public boolean isRunning() {
        running = false;
        recScan(root);
        return running;
    }

    void recScan(bNode root){

        if(root.left != null){
            recScan(root.left);
        }
        if(root.data.bState){
            running = true;
        }
        if(root.right != null){
            recScan(root.right);
        }
    }

    /**
     * clearBalls - removes all balls from display
     * note: need to pass a reference to the display
     * @param display
     */

    void clearBalls(bSim display){
        recClear(display, root);
    }

    void recClear(bSim display, bNode root){
        if (root.left != null){
            recClear(display,root.left);
        }
        display.remove (root.data.myBall);
        if (root.right != null){
            recClear(display,root.right);
        }
    }

    /**
     * drawSort - sorts balls by size and plots from left to right on display
     */
    void moveSort(){
        nextX = 0;
        recMove(root);
    }

    void recMove(bNode root) {
        if (root.left != null){
            recMove(root.left);
        }
        double X = nextX;
        double Y = root.data.bSize*2;
        nextX = X + root.data.bSize*2 + SEP;
        root.data.move(X,Y);
        if (root.right != null){
            recMove(root.right);
        }
    }

    /**
     * Recursive methods related to finding the gBall in the bTree with a particular member GOval
     */
    public bNode findNode(GOval value) {
        return findNodeRecursive(root, value);
    }

    private bNode findNodeRecursive(bNode current, GOval value){
        // code to traverse tree and find GOval that matches instance variable in data of particular node and return that node
        if (current.left!= null){
            // search the left
            bNode result = findNodeRecursive(current.left, value);
            if (result != null){
                return result;
            }
        }
        // compare the value of the chosen instance to find a match
        boolean isEqual = (current.data.myBall.getHeight() == value.getHeight()) && (current.data.myBall.getX()== value.getX()) &&(current.data.myBall.getY()== value.getY()) ;
        if (isEqual){
            return current;
        }
        if (current.right != null){
            bNode result = findNodeRecursive(current.right, value);
            if (result != null){
                return result;
            }
        }
        return null;
    }
}
