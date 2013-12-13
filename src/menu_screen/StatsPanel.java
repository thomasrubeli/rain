package menu_screen;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Comparator;
import java.util.Date;
import java.util.Enumeration;
import java.util.Vector;

import javax.swing.Box;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.ListSelectionModel;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;
import javax.swing.table.TableRowSorter;

public class StatsPanel extends JPanel implements ActionListener {
	// stuff for the stats table
	private CustomTableModel model;
	private JTable table;
	private TableRowSorter<DefaultTableModel> sorter;
	private static final String[] columnNames = { "Name", "Score", "Level",
			"Date" };
	private JLabel scores;
	private JButton quit;
	private ArrayList<Vector> scoreList = new ArrayList<Vector>(30);
	private BlocksGame main;
	private JButton clear;

	public StatsPanel(BlocksGame main) {
		this.main = main;
		setPreferredSize(new Dimension(800, 800));
		setLayout(new BoxLayout(this, BoxLayout.PAGE_AXIS));
		setBackground(Color.gray);
		scores = new JLabel("Scores");
		scores.setFont(new Font(Font.MONOSPACED, Font.BOLD, 24));

		scores.setAlignmentX(CENTER_ALIGNMENT);
		quit = new JButton("OK");
		quit.addActionListener(this);
		quit.setAlignmentX(CENTER_ALIGNMENT);
		clear = new JButton("Clear Scores");
		clear.addActionListener(this);
		clear.setAlignmentX(CENTER_ALIGNMENT);
		add(scores);
		add(Box.createHorizontalStrut(15));
		init();
		printScores();
		add(quit);
		add(clear);
		sorter = new TableRowSorter<DefaultTableModel>(model);
		sorter.setComparator(1, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if (Integer.parseInt(o1) > Integer.parseInt(o2)) {
					return -1;
				} else if ((Integer.parseInt(o1) < Integer.parseInt(o2))) {
					return 1;
				} else {
					return 0;
				}
			}

		});
		sorter.setComparator(2, new Comparator<String>() {

			@Override
			public int compare(String o1, String o2) {
				if (Integer.parseInt(o1) > Integer.parseInt(o2)) {
					return -1;
				} else if ((Integer.parseInt(o1) < Integer.parseInt(o2))) {
					return 1;
				} else {
					return 0;
				}
			}

		});

		table.setRowSorter(sorter);
		table.setBackground(Color.lightGray);

	}

	public void init() {
		buildTable();
		setMinimumSize(new Dimension(550, 370));
	}

	public void buildTable() {
		model = new CustomTableModel();

		for (String header : columnNames) {
			model.addColumn(header);
		}

		table = new JTable(model);

		// Color the lines
		for (Enumeration<TableColumn> e = table.getColumnModel().getColumns(); e
				.hasMoreElements();) {
			e.nextElement().setCellRenderer(new CustomTableCellRenderer());
		}

		table.getTableHeader().setReorderingAllowed(false);
		table.setFillsViewportHeight(true);
		table.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);

		JScrollPane scrollPane = new JScrollPane(table);
		add(scrollPane, BorderLayout.CENTER);

	}

	private class CustomTableCellRenderer extends DefaultTableCellRenderer {

		private static final long serialVersionUID = 1L;

		@Override
		public Component getTableCellRendererComponent(JTable table,
				Object value, boolean isSelected, boolean hasFocus, int row,
				int column) {
			Component cell = super.getTableCellRendererComponent(table, value,
					isSelected, hasFocus, row, column);

			if (value instanceof Date) {
				setText(DateFormat.getDateTimeInstance(DateFormat.SHORT,
						DateFormat.SHORT).format(value));
			}

			Object val = table.getValueAt(row, 0);
			if (val instanceof Integer) {

			}
			return cell;
		}

	}

	/**
	 * 
	 * CustomTableModel class
	 * 
	 * @author Thomas Rubeli & John Gaspoz
	 * 
	 */
	private class CustomTableModel extends DefaultTableModel {

		private static final long serialVersionUID = 1L;

		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}

	}

	@SuppressWarnings("rawtypes")
	public void printScores() {
		File score = new File("scores.txt");
		try {
			DataInputStream checkStream = new DataInputStream(
					new FileInputStream(score));
			if (checkStream.available() > 0) {
				ObjectInputStream ip = new ObjectInputStream(
						new BufferedInputStream(new FileInputStream(score)));
				scoreList = (ArrayList<Vector>) ip.readObject();
				ip.close();
			}
			for (Vector v : scoreList) {
				model.addRow(v);
			}
			checkStream.close();

		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	public void updateScores(String name, int score, int level) {
		if (name.equals("")) {
			return;
		}
		DateFormat df = new SimpleDateFormat("MM/dd/yyyy hh:ss");
		Date today = Calendar.getInstance().getTime();
		String reportDate = df.format(today);
		Vector<String> v = new Vector<String>();
		v.add(name);
		v.add(Integer.toString(score));
		v.add(Integer.toString(level));
		v.add(reportDate);
		model.addRow(v);
		scoreList.add(v);
		writeOnFile();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == quit) {
			main.setMain();

		} else if (e.getSource() == clear) {
			scoreList.clear();
			while (model.getRowCount() > 0) {
				model.removeRow(0);
			}
			boolean b = deleteDir(new File("scores.txt"));
			if (b) {
				System.out.println("File list cleared");
			} else {
				System.err.println("File list not cleared");
			}
			File dest = new File("scores.txt");
			try {
				dest.createNewFile();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		}
	}

	public void writeOnFile() {
		File dest = new File("scores.txt");
		try {
			ObjectOutputStream ip = new ObjectOutputStream(
					new BufferedOutputStream(new FileOutputStream(dest)));
			ip.writeObject(scoreList);
			ip.flush();
			ip.close();
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	public boolean deleteDir(File dir) {
		if (dir.isDirectory()) {
			String[] children = dir.list();
			for (int i = 0; i < children.length; i++) {
				boolean success = deleteDir(new File(dir, children[i]));
				if (!success) {
					return false;
				}
			}
		}
		return dir.delete();
	}
}
