package simulator.view;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;

import javax.swing.BorderFactory;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.border.TitledBorder;

import simulator.control.Controller;

public class BodiesTable extends JPanel {
    /**
     *
     */
    private static final long serialVersionUID = 1L;

    BodiesTable(Controller ctrl) {
   					 
   	 setLayout(new BorderLayout());
   	 setBorder(BorderFactory.createTitledBorder(BorderFactory.createLineBorder(Color.black, 2), "Bodies", TitledBorder.LEFT, TitledBorder.TOP));
   	 
   	 String[] columnNames = { "Id", "Mass", "Position", "Velocity", "Force" };
   	 BodiesTableModel btm = new BodiesTableModel(ctrl);
   	 
   	 btm.setColumnNames(columnNames);
   	 JTable table = new JTable(btm);
   	 table.setFillsViewportHeight(true);
   	 table.setShowGrid(false);
   	 table.getTableHeader().setReorderingAllowed(false);
   	 JScrollPane JSP = new JScrollPane(table, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
   	 //createPanel(Color.white, 500, 200);
   	 //JSP.setBackground(Color.white);
   	 
   	 this.add(JSP);
   	 //Panel.setVisible(true);    
   	      
    }
    
    
    
    protected JPanel createPanel(Color white, int i, int j) {
   	 JPanel panel = new JPanel();
   	  panel.setBackground(white);
   	  panel.setAlignmentY(JPanel.TOP_ALIGNMENT);
   	  panel.setPreferredSize(new Dimension(i, j));
   	  return panel;
    }
}


