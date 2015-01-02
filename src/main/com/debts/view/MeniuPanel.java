package main.com.debts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.Point;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.List;

import javax.swing.AbstractAction;
import javax.swing.Action;
import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableColumn;

import main.com.debts.api.Debt;
import main.com.debts.api.ManagementObject;
import main.com.debts.api.User;
import main.com.debts.api.enums.DebtsColumns;
import main.com.debts.api.enums.UserColumns;
import main.com.debts.management.dao.AbstractDataService;
import main.com.debts.management.dao.BillDataservice;
import main.com.debts.management.dao.DebtDataService;
import main.com.debts.management.dao.UserDataService;

public class MeniuPanel extends JPanel implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton newDebt, payBill, createUser, optimizeDets;
	private DebtInfo debtInfo;

	private JTable debtsTable, usersTable;

	private UserDataService userDataService;
	private DebtDataService debtDataService;
	private BillDataservice billDataservice;
	private View mainView;

	private User loggedUser;

	public MeniuPanel(View mainView, UserDataService userDataService,
			User logedUser) {
		super();
		this.userDataService = userDataService;
		this.debtDataService = new DebtDataService();
		this.billDataservice = new BillDataservice(debtDataService);
		this.mainView = mainView;
		this.loggedUser = logedUser;

		JLabel title = new JLabel("All debts");
		title.setBounds(10, 10, 200, 50);
		title.setFont(new Font("Verdana", Font.BOLD, 16));
		title.setVisible(true);
		add(title);

		setLayout(null);
		setSize(700, 500);
		setBackground(Color.cyan);

		newDebt = new JButton("Add new debt");
		newDebt.setBounds(70, 330, 150, 30);
		add(newDebt);
		newDebt.addActionListener(this);

		payBill = new JButton("Pay new bill");
		payBill.setBounds(250, 330, 150, 30);
		add(payBill);
		payBill.addActionListener(this);

		createUser = new JButton("Create user");
		createUser.setBounds(250, 370, 150, 30);
		add(createUser);
		createUser.addActionListener(this);
		
		optimizeDets = new JButton("Optimeze debts");
		optimizeDets.setBounds(430, 330, 150, 30);
		add(optimizeDets);
		optimizeDets.addActionListener(this);
		
		debtsTable = new JTable();
		createDebtsTable(debtDataService.getAll());

		usersTable = new JTable();
		createUsersTable(userDataService.getAll());
		mainView.repaint();
	}

	public void updateDebtsTable() {
		createBasicDebtsTable(debtDataService.getAll());
		debtsTable.repaint();
	}
	
	public void updateUsersTable() {
		createBasicUserTable(userDataService.getAll());
		usersTable.repaint();
	}

	private void createDebtsTable(List<Debt> debts) {
		createBasicDebtsTable(debts);
		JScrollPane scrollPane = new JScrollPane(debtsTable);
		scrollPane.setBounds(95, 100, 600, 150);
		debtsTable.setFillsViewportHeight(true);
		debtsTable.setVisible(true);
		debtsTable.setOpaque(true);
		scrollPane.setVisible(true);

		mainView.add(scrollPane);
	}
	
	private void createBasicDebtsTable(List<Debt> debts) {
		Object[][] objects = new Object[debts.size()][DebtsColumns.values().length];
		for (int i = 0; i < debts.size(); i++) {
			Debt debt = debts.get(i);
			objects[i][DebtsColumns.FROM_USERNAME.getColumnPosition()] = debt
					.getFromUsername();
			objects[i][DebtsColumns.TO_USERNAME.getColumnPosition()] = debt
					.getToUsername();
			objects[i][DebtsColumns.VALUE.getColumnPosition()] = debt.getValue();
			objects[i][DebtsColumns.DESCRIPTION.getColumnPosition()] = debt
					.getDescription();
			objects[i][DebtsColumns.PAY.getColumnPosition()] = DebtsColumns.PAY.getValue();
			objects[i][DebtsColumns.ID.getColumnPosition()] = debt.getId();
			objects[i][DebtsColumns.DATE.getColumnPosition()] = debt.getDate();
		}

		debtsTable.setModel(new DebtTableModel(objects, DebtsColumns.getAllValues()));
		
		TableColumn column = null;
		for (DebtsColumns debtsColumns: DebtsColumns.values())
		{
			 column = debtsTable.getColumnModel().getColumn(debtsColumns.getColumnPosition());
			 column.setPreferredWidth(debtsColumns.getColumnWidth());
		}

		Action createPayView = new PayAction<Debt>(loggedUser, debtDataService, DebtsColumns.ID.getColumnPosition());

		ButtonColumn buttonColumn = new ButtonColumn(debtsTable, createPayView,
				DebtsColumns.PAY.getColumnPosition());
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

	private void createUsersTable(List<User> users) {
		createBasicUserTable(users);
		usersTable.addMouseListener(new MeniuMouseAdapter(this));
		JScrollPane scrollPane = new JScrollPane(usersTable);
		scrollPane.setBounds(95, 300, 600, 70);
		usersTable.setFillsViewportHeight(true);
		usersTable.setVisible(true);
		usersTable.setOpaque(true);
		scrollPane.setVisible(true);

		mainView.add(scrollPane);
	}
	
	private void createBasicUserTable(List<User> users) {
		Object[][] objects = new Object[users.size()][UserColumns.values().length];
		for (int i = 0; i < users.size(); i++) {
			User user = users.get(i);
			objects[i][UserColumns.USERNAME.getColumnPosition()] = user.getUsername();
			objects[i][UserColumns.FULLNAME.getColumnPosition()] = user.getFullname();
			objects[i][UserColumns.NAME.getColumnPosition()] = user.getName();
			objects[i][UserColumns.SURNAME.getColumnPosition()] = user.getSurname();
			objects[i][UserColumns.AGE.getColumnPosition()] = user.getAge();
			objects[i][UserColumns.DELETE_BUTTON.getColumnPosition()] = UserColumns.DELETE_BUTTON.getValue();
			objects[i][UserColumns.ID.getColumnPosition()] = user.getId();
		}
		usersTable.setModel(new DebtTableModel(objects, UserColumns.getAllValues()));
		
		Action createPayView = new PayAction<User>(loggedUser, userDataService, UserColumns.ID.getColumnPosition());

		ButtonColumn buttonColumn = new ButtonColumn(usersTable, createPayView,
				UserColumns.DELETE_BUTTON.getColumnPosition());
		buttonColumn.setMnemonic(KeyEvent.VK_D);
	}

	private class MeniuMouseAdapter extends MouseAdapter
	{
		private MeniuPanel meniuPanel;
		private MeniuMouseAdapter(MeniuPanel meniuPanel)
		{
			this.meniuPanel = meniuPanel;
		}
		
		public void mousePressed(MouseEvent me) {
	        JTable table =(JTable) me.getSource();
	        Point p = me.getPoint();
	        int row = table.rowAtPoint(p);
	        int col = table.columnAtPoint(p);
	        if (row < 0)
	        {
	        	return;
	        }
	        if (me.getClickCount() == 1 && col != UserColumns.DELETE_BUTTON.getColumnPosition()) {
	            User user = userDataService.getUser(table.getValueAt(row, UserColumns.USERNAME.getColumnPosition()).toString());
		       	UserInfo userInfo = new UserInfo(meniuPanel, userDataService, user, loggedUser);
		       	userInfo.setVisible(true);
	        }
	    }
	}
	
	private class DebtTableModel extends DefaultTableModel {
		private static final long serialVersionUID = 1L;

		private DebtTableModel(Object[][] data, Object[] columns) {
			super(data, columns);
		}

		@Override
		public boolean isCellEditable(int row, int col) {
			return col == DebtsColumns.PAY.getColumnPosition();
		}
	}

	private class  PayAction <OBJECT extends ManagementObject> extends AbstractAction {

		private User loggedUser;
		private AbstractDataService<OBJECT> abstractDataService;
		private int idColumnPosition;
		
		private PayAction(User loggedUser, AbstractDataService<OBJECT> abstractDataService, int idColumnPosition) {
			this.loggedUser = loggedUser;
			this.abstractDataService = abstractDataService;
			this.idColumnPosition = idColumnPosition;
		}

		private static final long serialVersionUID = 1L;

		public void actionPerformed(ActionEvent e) {
			if (!loggedUser.isAdministrator()) {
				return;
			}
			String title , body;
			if (abstractDataService instanceof DebtDataService)
			{
				title = "Delete the debt";
				body = "Are you sure the debt was payed ?";
			}
			else
			{
				title = "Delete the user";
				body = "Are you sure you want to delete the corresponding user ?";
			}
			if (JOptionPane.showConfirmDialog(null,	body, title,
					JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE) == JOptionPane.YES_OPTION) {
				JTable table = (JTable) e.getSource();
				int modelRow = Integer.valueOf(e.getActionCommand());
				long objectId = (long) table.getModel().getValueAt(modelRow, idColumnPosition);
				abstractDataService.delete(objectId, loggedUser);
				((DefaultTableModel) table.getModel()).removeRow(modelRow);
			}
		}
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == newDebt) {
			addNewDebt();
		}
		if (e.getSource() == payBill) {
			payNewBill();
		}
		if (e.getSource() == createUser)
		{
			UserInfo userInfo = new UserInfo(this, userDataService, null, loggedUser);
			userInfo.setVisible(true);
		}
		if(e.getSource() == optimizeDets)
		{
			debtDataService.optimizeDebts(loggedUser);
			updateDebtsTable();
		}
	}

	private void addNewDebt() {
		debtInfo = new DebtInfo(this, debtDataService, userDataService,
				loggedUser);
		debtInfo.setVisible(true);
	}

	private void payNewBill() {
		BillInfo billInfo = new BillInfo(this, userDataService,
				billDataservice, loggedUser);
		billInfo.setVisible(true);
	}
}
