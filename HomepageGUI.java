
import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;

import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableModel;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Vector;

public class HomepageGUI extends JFrame {
	
	// DBManager
	DBManager dbm;

	// Search (Read)
	JPanel searchPanel;
	JLabel searchLabel;
	JTextField searchTxt;
	JButton searchBtn;
	JButton clearBtn;

	// Table
	String fieldNames[] = {"Number", "ID", "Name", "Title", "Content", "Date"};
	DefaultTableModel dataModel;
	JTable dataTable;
	JScrollPane dataScrlPane;
	
	// Manage Information (Create, Update, Delete)
	JPanel managePanel;
	JTextField boardIdTxt;
	JTextField userIdTxt;
	JTextField nameTxt;
	JTextField titleTxt;
	JTextField contentTxt;
	JButton addBtn;
	JButton updateBtn;
	JButton deleteBtn;
	
	// JFrame
	public HomepageGUI() {
		setTitle("Homepage GUI");
		setLocation(200, 400);
		Dimension dim = new Dimension(800,300);
		setPreferredSize(dim);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		dbm = new DBManager();
	}
	
	public void createSearchPanel() {
		searchPanel = new JPanel();
		searchPanel.setLayout(new BoxLayout(searchPanel, BoxLayout.X_AXIS));
		
		searchLabel = new JLabel("Keyword");
		searchPanel.add(searchLabel);
		
		searchTxt = new JTextField(0);
		searchPanel.add(searchTxt);
		
		searchBtn = new JButton("Search");
		searchBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Vector<BoardData> bdv = new Vector<BoardData>();
				String searchKeyword = searchTxt.getText();
				// DB Select
				bdv = dbm.selectData(searchKeyword);
				
				// DataModel update with new data !!!!
				DefaultTableModel dtm = (DefaultTableModel)dataTable.getModel();
				
				// Records
				Vector<Vector<String>> records = new Vector<Vector<String>>();
				for(BoardData bd: bdv){
					Vector<String> record = new Vector<String>();
					record.add(bd.getBoardId());
					record.add(bd.getUserId());
					record.add(bd.getName());
					record.add(bd.getTitle());
					record.add(bd.getContent());
					record.add(bd.getDate());
					records.add(record);
				}
				
				// Field Name
				Vector<String> fields = new Vector<String>();
				fields.add(fieldNames[0]);
				fields.add(fieldNames[1]);
				fields.add(fieldNames[2]);
				fields.add(fieldNames[3]);
				fields.add(fieldNames[4]);
				fields.add(fieldNames[5]);
				
				dtm.setDataVector(records,fields);
				dataTable.setModel(dtm);
			}
		});
		
		clearBtn = new JButton("Clear");
		clearBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				clearTxtFld();
			}
		});
		
		searchPanel.add(searchBtn);
		searchPanel.add(clearBtn);
		add(searchPanel, BorderLayout.NORTH);
	}
	
	public void createDataTable() {
		Vector<BoardData> bdv = new Vector<BoardData>();
		// DB Select
		bdv = dbm.selectData("");
		
		if(bdv == null)
			return;
		
		dataModel = new DefaultTableModel(fieldNames, 0){
			public boolean isCellEditable(int row, int col){
				return (col!=0) ? true : false;
			}
		};
		
		for(BoardData bd: bdv){
			Vector<String> record = new Vector<String>();
			record.add(bd.getBoardId());
			record.add(bd.getUserId());
			record.add(bd.getName());
			record.add(bd.getTitle());
			record.add(bd.getContent());
			record.add(bd.getDate());
			dataModel.addRow(record);
		}
		
		dataTable = new JTable(dataModel);
		dataTable.addMouseListener(new MouseAdapter() {
		    @Override
		    public void mouseClicked(MouseEvent evt) {
		        int row = dataTable.rowAtPoint(evt.getPoint());
		        int col = dataTable.columnAtPoint(evt.getPoint());
		        if (row >= 0 && col >= 0) {
		        	boardIdTxt.setText(dataTable.getValueAt(row, 0).toString());
		        	userIdTxt.setText(dataTable.getValueAt(row, 1).toString());
		        	nameTxt.setText(dataTable.getValueAt(row, 2).toString());
		        	titleTxt.setText(dataTable.getValueAt(row, 3).toString());
		        	contentTxt.setText(dataTable.getValueAt(row, 4).toString());
		        }
		    }
		});
		
		dataScrlPane = new JScrollPane(dataTable);
		add(dataScrlPane, BorderLayout.CENTER);
	}
	
	public void createManagePanel() {
		managePanel = new JPanel();
		managePanel.setLayout(new BoxLayout(managePanel, BoxLayout.X_AXIS));
		
		boardIdTxt = new JTextField(3);
		userIdTxt  = new JTextField(10);
		nameTxt    = new JTextField(8);
		titleTxt   = new JTextField(17);
		contentTxt = new JTextField(30);
		
		managePanel.add(new JLabel(fieldNames[0]));
		managePanel.add(boardIdTxt);
		managePanel.add(new JLabel(fieldNames[1]));
		managePanel.add(userIdTxt);
		managePanel.add(new JLabel(fieldNames[2]));
		managePanel.add(nameTxt);
		managePanel.add(new JLabel(fieldNames[3]));
		managePanel.add(titleTxt);
		managePanel.add(new JLabel(fieldNames[4]));
		managePanel.add(contentTxt);
		
		addBtn = new JButton("Insert");
		addBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				String inputStr[] = new String[6];
				inputStr[0] = boardIdTxt.getText();
				inputStr[1] = userIdTxt.getText();
				inputStr[2] = nameTxt.getText();
				inputStr[3] = titleTxt.getText();
				inputStr[4] = contentTxt.getText();
		        inputStr[5] = getCurrentTime();
				
				// DB Insert
				if(dbm.insertData(inputStr)) {
					// Model Insert
					dataModel.addRow(inputStr); 
				}
				clearTxtFld();
			}
		});// addActionListener
		
		updateBtn = new JButton("Update");
		updateBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectRow = dataTable.getSelectedRow();
				if(selectRow == -1) {
					return;
				}
				else {
					//System.out.println("Update Row = " + selectRow);

					String uptStr[] = new String[6];
					uptStr[0] = boardIdTxt.getText();
					uptStr[1] = userIdTxt.getText();
					uptStr[2] = nameTxt.getText();
					uptStr[3] = titleTxt.getText();
					uptStr[4] = contentTxt.getText();
			        uptStr[5] = getCurrentTime();

			        // DB Update
					if(dbm.updateData(uptStr)) {
						// Model Update
						dataModel.setValueAt(uptStr[0], selectRow, 0);
						dataModel.setValueAt(uptStr[1], selectRow, 1);
						dataModel.setValueAt(uptStr[2], selectRow, 2);
						dataModel.setValueAt(uptStr[3], selectRow, 3);
						dataModel.setValueAt(uptStr[4], selectRow, 4);
						dataModel.setValueAt(uptStr[5], selectRow, 5);
						dataTable.updateUI();
					}
		        	clearTxtFld();
				}
			}
		});
		
		deleteBtn = new JButton("Delete");
		deleteBtn.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				int selectRow = dataTable.getSelectedRow();
				if(selectRow == -1) {
					return;
				}
				else {
					//System.out.println(selectRow);
					int delRow = Integer.parseInt(dataTable.getValueAt(selectRow, 0).toString());
					//System.out.println(delRow);
					
					// DB Delete
					if(dbm.deleteData(delRow)) {
						// Model Delete
						dataModel.removeRow(selectRow);
					}
		        	clearTxtFld();
				}
			}
		});
		
		managePanel.add(addBtn);
		managePanel.add(updateBtn);
		managePanel.add(deleteBtn);
		
		add(managePanel, BorderLayout.SOUTH);
	}
	
	public String getCurrentTime(){
        LocalDateTime now = LocalDateTime.now();
        String formatedNow = now.format(
        		DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        
        return formatedNow;
	}
	
	public void clearTxtFld() {
		searchTxt.setText("");
		boardIdTxt.setText("");
		userIdTxt.setText("");
		nameTxt.setText("");
		titleTxt.setText("");
		contentTxt.setText("");
	}
	
	@Override
	protected void finalize() {
		dbm.closeConn();
	}
	
	public static void main(String[] args) {
		HomepageGUI hpui = new HomepageGUI();   // 1: JFrame
		hpui.createSearchPanel();               // 2: JPanel (NORTH)
		hpui.createDataTable();                 // 3: JScrollPane (CENTER)
		hpui.createManagePanel();               // 4: JPanel (SOUTH)
		hpui.pack();
		hpui.setVisible(true);
	}
}
