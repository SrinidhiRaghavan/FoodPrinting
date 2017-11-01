import java.awt.event.ActionEvent;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Scanner;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextField;

public class PyrWindow extends JFrame implements java.awt.event.ActionListener
{
  private ArrayList<PyrWindow.Entry> entries;
  HashMap<String, JPanel> panels;
  JButton generate;
  JButton load;
  JTabbedPane tabbedPane;
  public int fileNumber;
  
  public PyrWindow(String title, int fileNumber)
  {
    super(title);
    init(fileNumber);
    pack();
    setVisible(true);
    this.fileNumber = fileNumber;
  }
  
  private class Entry {
    String name;
    String value;
    JTextField field;
    JLabel label;
    String labelText;
    String tab;
    
    Entry(String n, String v, String text, String t) {
      name = n;
      value = v;
      field = new JTextField(10);
      field.setText(v);
      label = new JLabel(text);
      tab = t;
    }
  }
  
  public void init(int fileNumber) {
    entries = new ArrayList();
    entries.add(new PyrWindow.Entry("output_name", "", "Output name", "Basic Settings"));
    entries.add(new PyrWindow.Entry("x_center", "140", "x co-ordinate of the centre of the print", "Basic Settings"));
    entries.add(new PyrWindow.Entry("y_center", "140", "y co-ordinate of the centre of the print", "Basic Settings"));
    entries.add(new PyrWindow.Entry("cook_outer", "1", "Cook outer yes (1) / no (0)", "Cooking Settings"));
    entries.add(new PyrWindow.Entry("cook_speed_outer", "200", "Cook speed of outer material (mm/sec)", "Cooking Settings"));
    entries.add(new PyrWindow.Entry("side_count", "3", "Number of sides in the shape", "Basic Settings"));
    entries.add(new PyrWindow.Entry("print_speed", "1200", "Print Speed (mm/sec)", "Basic Settings"));
    entries.add(new PyrWindow.Entry("num_layers", "25", "Number of Layers", "Size Settings"));
    entries.add(new PyrWindow.Entry("base_width", "25", "Base Width (mm)", "Size Settings"));
    entries.add(new PyrWindow.Entry("layer_height", "1.2", "Layer Height (mm)", "Size Settings"));
    entries.add(new PyrWindow.Entry("twist_angle", "4", "Twist Angle per Layer (degrees)", "Basic Settings"));
    entries.add(new PyrWindow.Entry("top_thickness", "1", "Thickness of Top Layers (layers)", "Size Settings"));
    entries.add(new PyrWindow.Entry("bottom_thickness", "2", "Thickness of Bottom Layers (layers)", "Size Settings"));
    entries.add(new PyrWindow.Entry("bottom_layers", "15", "Number of Bottom Layers", "Size Settings"));
    entries.add(new PyrWindow.Entry("stop_after", "0", "Number of Layers to Print (0 = all)", "Size Settings"));
    entries.add(new PyrWindow.Entry("bed_z", "6", "Z position of print start [6 (slab) / 9 (plate)]", "Basic Settings"));
    entries.add(new PyrWindow.Entry("cook_temp", "255", "Spotlight power (0-255)", "Cooking Settings"));
    entries.add(
      new PyrWindow.Entry("cook_temp_standby", "0", "Spotlight power when not cooking (0-255)", "Cooking Settings"));
    entries.add(new PyrWindow.Entry("cook_lift", "0", "Height change on cook for every layer(mm)", "Cooking Settings"));
    entries.add(
      new PyrWindow.Entry("static_cook_height", "0", "Height change on static cook at the end", "Cooking Settings"));
    entries.add(
      new PyrWindow.Entry("static_cook_time", "5", "Time of static cook at the end (s)", "Cooking Settings"));
    entries.add(new PyrWindow.Entry("retraction", "3", "Retraction amount after print / cook moves", "Size Settings"));
    entries.add(new PyrWindow.Entry("cook_extra_extrude", "0", "Extra extrusion on cook moves (mm)", "Cooking Settings"));
    
    if (fileNumber == 1) {
      entries.add(new PyrWindow.Entry("load_depth", "0", "level of outer material (slot 1)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier", "1.0", "Extrusion Multiplier of outer material", 
        "Multimaterial Settings"));
    } else if (fileNumber == 2) {
      entries.add(new PyrWindow.Entry("fill_layers_count", "20", "number of layers to fill", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("load_depth_2", "0", "level of outer material (slot 3)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("load_depth_1", "0", "level of fill material (slot 1)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier_2", "1.0", "Extrusion Multiplier of outer material", 
        "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier_1", "1.0", "Extrusion Multiplier of fill material", 
        "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_fill", "1", "Cook fill yes (1) / no (0)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_speed_fill", "200", "Cook speed of fill material", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_lap_on_fill", "2", "n : Cook every nth layer on fill", "Multimaterial Settings"));
    } else if (fileNumber == 3) {
      entries.add(
        new PyrWindow.Entry("fill_layers_count", "20", "number of layers to fill (<20)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("load_depth_2", "0", "level of outer material (slot 3)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("load_depth_1", "0", "level of fill material 1 (slot 1)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("load_depth_3", "0", "level of fill material 2 (slot 2)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier_2", "1.0", "Extrusion Multiplier of outer material", 
        "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier_1", "1.0", "Extrusion Multiplier of fill material 1", 
        "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("extrusion_multiplier_3", "1.0", "Extrusion Multiplier of fill material 2", 
        "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_fill", "1", "Cook fill yes (1) / no (0)", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_speed_fill", "200", "Cook speed of fill material", "Multimaterial Settings"));
      entries.add(new PyrWindow.Entry("cook_lap_on_fill", "2", "n : Cook every nth layer on fill", "Multimaterial Settings"));
    }
    
    panels = new HashMap();
    tabbedPane = new JTabbedPane();
    for (PyrWindow.Entry entry : entries) {
      if (panels.containsKey(tab)) {
        ((JPanel)panels.get(tab)).add(label);
        ((JPanel)panels.get(tab)).add(field);
      } else {
        JPanel p = new JPanel(new java.awt.GridLayout(0, 2));
        panels.put(tab, p);
        tabbedPane.addTab(tab, p);
        p.add(label);
        p.add(field);
      }
    }
    
    generate = new JButton("Generate");
    generate.addActionListener(this);
    
    load = new JButton("Load");
    load.addActionListener(this);
    
    for (JPanel p : panels.values()) {
      p.add(generate);
      p.add(load);
    }
    
    ((JPanel)panels.get("Multimaterial Settings")).add(generate);
    ((JPanel)panels.get("Multimaterial Settings")).add(load);
    
    add(tabbedPane);
  }
  

  public void actionPerformed(ActionEvent e)
  {
    String path = null;
    if (e.getSource() == generate) {
      try {
        HashMap<String, String> settings = new HashMap();
        for (PyrWindow.Entry entry : entries) {
          value = field.getText();
          settings.put(name, value);
        }
        if (fileNumber == 1) {
          path = Pyramid.writeGcode(settings);
        } else if (fileNumber == 2) {
          path = OuterAndFill.writeGcode(settings);
        } else if (fileNumber == 3) {
          path = MultiMaterial.writeGcode(settings);
        }
        saveConfig(settings);
        JOptionPane.showMessageDialog(this, "Gcode Generated at " + path);
      } catch (NumberFormatException e1) {
        JOptionPane.showMessageDialog(this, "Invalid input to one or more fields.");
      } catch (IOException e1) {
        JOptionPane.showMessageDialog(this, "Error writing to file. " + e1.getMessage());
      }
    }
    else if (e.getSource() == load) {
      JFileChooser fc = new JFileChooser(new File(".").getAbsolutePath());
      fc.addChoosableFileFilter(new FoodFilter());
      fc.setAcceptAllFileFilterUsed(false);
      
      int returnVal = fc.showOpenDialog(this);
      if (returnVal == 0) {
        File file = fc.getSelectedFile();
        loadConfig(file);
      } else {
        JOptionPane.showMessageDialog(this, "No file was loaded.");
      }
    }
  }
  
  public void saveConfig(HashMap<String, String> settings) throws IOException {
    String name = (String)settings.get("output_name");
    if (name.length() == 0) {
      name = "no_name";
    }
    if (name.endsWith(".gcode")) {
      name = name.substring(0, name.length() - 6);
    }
    File file = new File(name + ".food");
    FileWriter out = new FileWriter(file);
    for (PyrWindow.Entry entry : entries) {
      out.write(name + " " + value + "\n");
    }
    out.close();
  }
  
  public void loadConfig(File f)
  {
    try {
      Scanner in = new Scanner(f);
      Iterator localIterator; for (; in.hasNextLine(); 
          
          localIterator.hasNext())
      {
        String[] line = in.nextLine().split(" ");
        localIterator = entries.iterator(); continue;PyrWindow.Entry e = (PyrWindow.Entry)localIterator.next();
        if (name.equals(line[0])) {
          value = line[1];
          field.setText(value);
        }
      }
      
      in.close();
      JOptionPane.showMessageDialog(this, "Loaded " + f.getName());
    }
    catch (FileNotFoundException e1) {
      JOptionPane.showMessageDialog(this, "Error loading file");
    }
  }
}
