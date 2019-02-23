
import acm.gui.TableLayout;
import javax.swing.*;
import java.awt.*;

public class sliderBox {
    JPanel myPanel;
    JLabel nameLabel;
    JLabel minLabel;
    JLabel maxLabel;
    JSlider mySlider;
    JLabel sReadout;
    Double imin;
    Double imax;

    /**
     * sliderBox for Integer values
     * @param name
     * @param min
     * @param dValue
     * @param max
     */
    public sliderBox(String name, Integer min, Integer dValue, Integer max){
        myPanel = new JPanel();
        nameLabel = new JLabel(name);
        minLabel = new JLabel(min.toString());
        maxLabel = new JLabel(max.toString());
        mySlider = new JSlider(min,max,dValue);
        sReadout = new JLabel(dValue.toString());
        sReadout.setForeground(Color.BLUE);
        myPanel.setLayout(new TableLayout(1,5));
        myPanel.add(nameLabel,"width=100");
        myPanel.add(minLabel, "width=50");
        myPanel.add(mySlider, "width=100");
        myPanel.add(maxLabel,"width=100");
        myPanel.add(sReadout, "width=80");
        imin = (double)min;
        imax = (double)max;
    }

    /**
     * sliderBox to handle the double values, because the JSlider only works for int value
     * so use a very stupid and inefficient way to use the double value (multiple by 100 and then devide by 100 when using double values
     * also, the min and max display value for double are set to be %
     * @param name
     * @param min
     * @param dValue
     * @param max
     */
    public sliderBox(String name, Double min, Double dValue, Double max){
        myPanel = new JPanel();
        nameLabel = new JLabel(name);
        minLabel = new JLabel(min.toString());
        maxLabel = new JLabel(max.toString());
        mySlider = new JSlider(min.intValue(),max.intValue(), dValue.intValue());
        sReadout = new JLabel(dValue.toString());
        sReadout.setForeground(Color.BLUE);
        myPanel.setLayout(new TableLayout(1,5));
        myPanel.add(nameLabel, "width=100");
        myPanel.add(minLabel, "width=50");
        myPanel.add(mySlider, "width=100");
        myPanel.add(maxLabel,"width=100");
        myPanel.add(sReadout, "width=80");
        imin = min.doubleValue();
        imax = max.doubleValue();

    }

    public Integer getISlider(){
        return mySlider.getValue();
    }       // get the value of the slider

    public void setISlider(int val){
        mySlider.setValue(val);                                       // set the value of the slider
        sReadout.setText(val + "");
    }

}
