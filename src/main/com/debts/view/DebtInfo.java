package main.com.debts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.Date;
import java.util.List;
import java.util.Properties;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import org.jdatepicker.impl.JDatePanelImpl;
import org.jdatepicker.impl.JDatePickerImpl;
import org.jdatepicker.impl.UtilDateModel;

import main.com.debts.api.Debt;
import main.com.debts.api.User;
import main.com.debts.api.helpers.DateLabelFormatter;
import main.com.debts.management.dao.DebtDataService;
import main.com.debts.management.dao.UserDataService;

public class DebtInfo extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private TextField value, description;
	private JButton create, exit;
	private JComboBox<User> fromUsername, toUsername;
	private JLabel valueError;

	private DebtDataService debtDataService;
	private MeniuPanel mainePanel;
	
	private User loggedUser;
	
	private JDatePickerImpl datePicker;

	public DebtInfo(MeniuPanel mainePanel, DebtDataService debtDataService,
			UserDataService userDataService, User loggedUser) {
		super();
		this.debtDataService = debtDataService;
		this.mainePanel = mainePanel;
		this.loggedUser = loggedUser;
		
		setLayout(null);
		setSize(400, 400);
		setBackground(Color.gray);
		setTitle("Create a Debt");

		JLabel fromUserNameLabel = new JLabel("From Username");
		fromUserNameLabel.setBounds(10, 10, 150, 30);
		add(fromUserNameLabel);

		List<User> allUsers = userDataService.getAll();

		fromUsername = new JComboBox<User>();
		fromUsername.setBounds(120, 10, 150, 30);
		populateComboBox(fromUsername, allUsers);
		add(fromUsername);

		JLabel toUserNameLabel = new JLabel("To Username");
		toUserNameLabel.setBounds(10, 50, 150, 30);
		add(toUserNameLabel);

		toUsername = new JComboBox<User>();
		toUsername.setBounds(120, 50, 150, 30);
		populateComboBox(toUsername, allUsers);
		add(toUsername);

		JLabel valueLabel = new JLabel("Value:");
		valueLabel.setBounds(10, 90, 100, 30);
		add(valueLabel);

		valueError = new JLabel();
		valueError.setBounds(10, 330, 200, 30);
		valueError.setVisible(false);
		valueError.setFont(new Font("Verdana", Font.BOLD, 16));
		valueError.setForeground(Color.RED);
		add(valueError);

		value = new TextField();
		value.setBounds(120, 90, 100, 30);
		add(value);

		JLabel datePickerLabel = new JLabel("Date: ");
		datePickerLabel.setBounds(10, 130, 100, 30);
		add(datePickerLabel);
		
		UtilDateModel model = new UtilDateModel();
		Properties p = new Properties();
		p.put("text.today", "Today");
		p.put("text.month", "Month");
		p.put("text.year", "Year");
		JDatePanelImpl datePanel = new JDatePanelImpl(model, p);
		datePicker = new JDatePickerImpl(datePanel, new DateLabelFormatter());
		datePicker.setBounds(120, 130, 150, 30);
		add(datePicker);
		
		JLabel descriptionLabel = new JLabel("Description:");
		descriptionLabel.setBounds(10, 170, 100, 30);
		add(descriptionLabel);

		description = new TextField();
		description.setBounds(120, 170, 250, 100);
		add(description);

		this.create = new JButton("Create");
		this.create.setBounds(280, 290, 100, 30);
		add(this.create);
		this.create.addActionListener(this);

		this.exit = new JButton("Exit");
		this.exit.setBounds(280, 330, 100, 30);
		add(this.exit);
		this.exit.addActionListener(this);

		setVisible(false);
		setDefaultCloseOperation(3);
	}

	private void populateComboBox(JComboBox<User> comboBox, List<User> users) {
		DefaultComboBoxModel<User> comboBoxModel = new DefaultComboBoxModel<User>();
		for (User user : users) {
			comboBoxModel.addElement(user);
		}
		comboBox.setModel(comboBoxModel);
	}

	private void initializeFrame() {
		description.setText("");
		value.setText("");
		valueError.setVisible(false);
		initializeComboBox(fromUsername);
		initializeComboBox(toUsername);
		this.repaint();
	}

	private void initializeComboBox(JComboBox<User> comboBox) {
		if (comboBox.getModel().getSize() > 0) {
			comboBox.setSelectedIndex(0);
		}
	}

	private void createDebt() {
		try {
			Debt debt = new Debt();
			debt.setDescription(description.getText());
			debt.setFromUsername(((User) fromUsername.getSelectedItem())
					.getUsername());
			debt.setToUsername(((User) toUsername.getSelectedItem())
					.getUsername());
			debt.setValue(new BigDecimal(value.getText()));
			debt.setDate((Date)datePicker.getModel().getValue());
			debtDataService.create(debt, loggedUser);
			initializeFrame();
			mainePanel.updateDebtsTable();
		} catch (Exception e) {
			valueError.setText("Enter a numeric value");
			valueError.setVisible(true);
		}

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == create) {
			createDebt();
		}
		if (e.getSource() == exit) {
			this.setVisible(false);
		}
	}
}
