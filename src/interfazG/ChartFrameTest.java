
import java.lang.reflect.Field;
import javax.swing.JFrame;
import org.jfree.chart.ChartFrame;
import org.jfree.chart.JFreeChart;

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

/**
 *
 * @author MAC_Sebastian
 */
public class ChartFrameTest extends ChartFrame  {
    
    public ChartFrameTest(String title, JFreeChart chart) {
        super(title, chart);
    }
    
    public void addPanel(JFrame a) throws Exception {
        try{
        Field jpanel = super.getClass().getDeclaredField("chartPanel");
        jpanel.setAccessible(true);
        Object jpanel2 =  jpanel.get(super.getClass());
        }catch(Exception e){
            throw e;
        }
        
    }
    
    
    
}
