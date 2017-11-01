import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.util.HashMap;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JTabbedPane;

public class PyramidWindowUI extends JFrame implements java.awt.event.ActionListener
{
  HashMap<String, JPanel> panels;
  JRadioButton one;
  JRadioButton two;
  JRadioButton three;
  JTabbedPane tabbedPane;
  
  public PyramidWindowUI(String title)
  {
    super(title);
    initialize();
    pack();
    setVisible(true);
  }
  
  public void initialize()
  {
    panels = new HashMap();
    tabbedPane = new JTabbedPane();
    
    JLabel num_materials = new JLabel("Number of materials", 2);
    

    JPanel radioPanel = new JPanel();
    one = new JRadioButton("1");
    two = new JRadioButton("2");
    three = new JRadioButton("3");
    radioPanel.setLayout(new GridLayout(1, 3));
    radioPanel.add(one);
    radioPanel.add(two);
    radioPanel.add(three);
    
    one.addActionListener(this);
    two.addActionListener(this);
    three.addActionListener(this);
    
    JPanel p = new JPanel(new GridLayout(0, 2));
    panels.put("Materials", p);
    tabbedPane.addTab("Materials", p);
    
    ((JPanel)panels.get("Materials")).add(num_materials);
    ((JPanel)panels.get("Materials")).add(radioPanel);
    
    add(tabbedPane);
  }
  

  public void actionPerformed(ActionEvent e)
  {
    if (e.getSource() == one)
    {
      PyrWindow wd = new PyrWindow("Pyramid Gcode Generator for single material", 1);
      wd.setDefaultCloseOperation(3);
    }
    else if (e.getSource() == two)
    {
      PyrWindow wd = new PyrWindow("Pyramid Gcode Generator for single material", 2);
      wd.setDefaultCloseOperation(3);
    }
    else if (e.getSource() == three)
    {
      PyrWindow wd = new PyrWindow("Pyramid Gcode Generator for single material", 3);
      wd.setDefaultCloseOperation(3);
    }
  }
}
