package simulator.view;

import java.awt.Component;
import java.awt.Dimension;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.List;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JDialog;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.AbstractTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumnModel;

import org.json.JSONObject;

import simulator.control.Controller;


public class PhysicDialog extends JDialog {

	private static final long serialVersionUID = 1L;

	private int _status;
	private Controller _ctrl;
	private JsonTableModel _dataTableModel;
	private List<JSONObject> LawsData;
	private JComboBox<String> comboBox;
	// This table model stores internally the content of the table. Use
	// getData() to get the content as JSON.
	//
	private class JsonTableModel extends AbstractTableModel {

		/**
		 * 
		 */
		private static final long serialVersionUID = 1L;
		private static final int INI_ROW = 6;
		private static final int INI_COLUM = 3;
		private String[] _header = { "Key", "Value", "Description" };
		String[][] _data;
		JsonTableModel() {
			_data = new String[INI_ROW][INI_COLUM];
			clear();
		}

		
		public void clear() {
			for (int i = 0; i < INI_ROW; i++)
				for (int j = 0; j < INI_COLUM; j++)
					_data[i][j] = "";
			fireTableStructureChanged();
		}

		@Override
		public String getColumnName(int column) {
			return _header[column];
		}

		@Override
		public int getRowCount() {
			return _data.length;
		}

		@Override
		public int getColumnCount() {
			return _header.length;
		}

		@Override
		public boolean isCellEditable(int rowIndex, int columnIndex) {
			if(columnIndex == 1) return true;
			else return false;
		}

		@Override
		public Object getValueAt(int rowIndex, int columnIndex) {
			return _data[rowIndex][columnIndex];
		}

		@Override
		public void setValueAt(Object o, int rowIndex, int columnIndex) {
			_data[rowIndex][columnIndex] = o.toString();
		}

		// Method getData() returns a String corresponding to a JSON structure
		// with column 1 as keys and column 2 as values.

		// This method return the coIt is important to build it as a string, if
		// we create a corresponding JSONObject and use put(key,value), all values
		// will be added as string. This also means that if users want to add a
		// string value they should add the quotes as well as part of the
		// value (2nd column).
		//
		public String getData() {
			StringBuilder s = new StringBuilder();
			s.append("{\"data\":{");

			for (int i = 0; i < _data.length; i++) {
				if (!_data[i][0].isEmpty() && !_data[i][1].isEmpty()&& !_data[i][2].isEmpty()) {
					s.append('"');
					s.append(_data[i][0]);
					s.append('"');
					s.append(':');
					s.append(_data[i][1]);
					s.append(',');
				}
			}
			s.append("},");
			
			
			String param = LawsData.get(comboBox.getSelectedIndex()).getString("type");
			s.append("\"type\":" + "\""+param+"\"");
			s.append('}');
			
			return s.toString();
		}
	}

	public PhysicDialog(Frame parent, Controller ctrl) {
		super(parent, true);
		_ctrl = ctrl;
		initGUI();
	}

	private void initGUI() {

		_status = 0;

		setTitle("Force Laws Selection");
		JPanel mainPanel = new JPanel();
		mainPanel.setLayout(new BoxLayout(mainPanel, BoxLayout.Y_AXIS));
		setContentPane(mainPanel);

		// help
		//No sale en negrita
		JLabel help = new JLabel("<html><p>Select a Force Law and provide values for the parameters in the <b>Value column</b> (default values are used for the parameters with no value)</p><html>");
		help.setAlignmentX(CENTER_ALIGNMENT);
		mainPanel.add(help);
		mainPanel.add(Box.createRigidArea(new Dimension(10, 20)));

		// data table
		_dataTableModel = new JsonTableModel();
		JTable dataTable = new JTable(_dataTableModel) {
			private static final long serialVersionUID = 1L;
			
			// we override prepareRenderer to resized rows to fit to content
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				TableColumnModel cmodel = getColumnModel();
				cmodel.getColumn(0).setPreferredWidth(60);
				cmodel.getColumn(1).setPreferredWidth(60);
				cmodel.getColumn(2).setPreferredWidth(200);
				cmodel.getColumn(0).setMaxWidth(60);
				cmodel.getColumn(1).setMaxWidth(60);
				return component;
			}
		};
		
		JScrollPane tabelScroll = new JScrollPane(dataTable, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED,
				JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
		tabelScroll.setPreferredSize(new Dimension(250,120));
		tabelScroll.setMaximumSize(new Dimension(1000, 120));
		mainPanel.add(tabelScroll);

		mainPanel.add(Box.createRigidArea(new Dimension(0, 20)));
		
		//Combo box
		comboBox = new JComboBox<String>();
		comboBox.setMaximumSize(new Dimension(275,20));
		if(LawsData ==  null) {LawsData =  _ctrl.getForceLawsInfo();}
    	
    	for (JSONObject jsonObject : LawsData) {
			comboBox.addItem(jsonObject.getString("desc"));
		}
    	comboBox.setSelectedIndex(0);
    	ChangedLaw(0);
    	comboBox.addActionListener(new ActionListener() {
    		@Override
    		public void actionPerformed(ActionEvent e) {
    			ChangedLaw(comboBox.getSelectedIndex());
    		}
    		
    	});
    	
    	mainPanel.add(comboBox);
    	

		// bottons
		JPanel buttonsPanel = new JPanel();
		buttonsPanel.setAlignmentX(CENTER_ALIGNMENT);

		mainPanel.add(buttonsPanel);

		JButton cancelButton = new JButton("Cancel");
		cancelButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 0;
				PhysicDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(cancelButton);

		JButton clearButton = new JButton("Clear");
		clearButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_dataTableModel.clear();
			}
		});
		buttonsPanel.add(clearButton);

		JButton okButton = new JButton("OK");
		okButton.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {
				_status = 1;
				PhysicDialog.this.setVisible(false);
			}
		});
		buttonsPanel.add(okButton);

		setPreferredSize(new Dimension(720, 300));

		pack();
		setResizable(true); // change to 'true' if you want to allow resizing
		setVisible(false); // we will show it only whe open is called
	}

	public int open() {

		if (getParent() != null)
			setLocation(//
					getParent().getLocation().x + getParent().getWidth() / 2 - getWidth() / 2, //
					getParent().getLocation().y + getParent().getHeight() / 2 - getHeight() / 2);
		pack();
		setVisible(true);
		return _status;
	}

	public String getJSON() {
		return _dataTableModel.getData();
	}
	public void ChangedLaw(int law) {
		_dataTableModel.clear();
		int row = 0;
		JSONObject param = LawsData.get(law).getJSONObject("data");
		for (String key : param.keySet()) {
			_dataTableModel.setValueAt(key, row, 0);
			_dataTableModel.fireTableCellUpdated(row, 0);
			_dataTableModel.setValueAt(param.getString(key), row, 2);
			_dataTableModel.fireTableCellUpdated(row, 2);
			row++;
		}
		_dataTableModel.fireTableStructureChanged();
	}
}

