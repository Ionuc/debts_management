package main.com.debts.view;

import java.awt.Color;
import java.awt.Font;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.com.debts.api.Bill;
import main.com.debts.api.User;
import main.com.debts.api.enums.BillCompanies;
import main.com.debts.management.dao.BillDataservice;
import main.com.debts.management.dao.UserDataService;

public class BillInfo extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;

	private JComboBox<String> companiesComboBox;
	private JComboBox<User> ownerComboBox;
	private TextField value;
	private JLabel valueError;
	private List<JCheckBox> usersCheckBox;

	private JButton create, exit;

	private BillDataservice billDataservice;
	private MeniuPanel meniuPanel;
	private User loggedUser;

	public BillInfo(MeniuPanel meniuPanel, UserDataService userDataService,
			BillDataservice billDataservice, User loggedUser) {
		super();

		this.loggedUser = loggedUser;
		this.billDataservice = billDataservice;
		this.meniuPanel = meniuPanel;

		List<User> users = userDataService.getAll();

		setLayout(null);
		setSize(400, 400);
		setBackground(Color.gray);
		setTitle("Create Bill");

		JLabel companiesComboBoxLabel = new JLabel("Company");
		companiesComboBoxLabel.setBounds(10, 10, 150, 30);
		add(companiesComboBoxLabel);

		companiesComboBox = new JComboBox<String>();
		companiesComboBox.setBounds(120, 10, 150, 30);
		populateCompanyComboBox(companiesComboBox);
		add(companiesComboBox);

		JLabel valueLabel = new JLabel("Value (lei) :");
		valueLabel.setBounds(10, 50, 150, 30);
		add(valueLabel);

		valueError = new JLabel("Enter a valid number");
		valueError.setBounds(10, 330, 200, 30);
		valueError.setFont(new Font("Verdana", Font.BOLD, 16));
		valueError.setForeground(Color.RED);
		valueError.setVisible(false);
		add(valueError);

		value = new TextField();
		value.setBounds(120, 50, 100, 30);
		add(value);
		add(valueLabel);

		JLabel ownerLabel = new JLabel("Owner : ");
		ownerLabel.setBounds(10, 90, 150, 30);
		add(ownerLabel);

		ownerComboBox = new JComboBox<User>();
		ownerComboBox.setBounds(120, 90, 150, 30);
		populateOwnerComboBox(ownerComboBox, users);
		add(ownerComboBox);

		JLabel checkBoxLabel = new JLabel("Participants :");
		checkBoxLabel.setBounds(10, 130, 150, 30);
		add(checkBoxLabel);

		populateCheckBoxGroup(users);

		create = new JButton("Create");
		create.setBounds(280, 290, 100, 30);
		add(create);
		create.addActionListener(this);

		exit = new JButton("Exit");
		exit.setBounds(280, 330, 100, 30);
		add(exit);
		exit.addActionListener(this);

		setVisible(false);
		setDefaultCloseOperation(3);
	}

	private void populateCheckBoxGroup(List<User> users) {
		usersCheckBox = new ArrayList<JCheckBox>();
		int index = 130;
		for (User user : users) {
			JCheckBox checkBox = new JCheckBox(user.getFullname());
			checkBox.setName(user.getUsername());
			checkBox.setBounds(120, index, 150, 30);
			checkBox.setSelected(true);
			add(checkBox);
			index += 40;
			usersCheckBox.add(checkBox);
		}
	}

	private void populateCompanyComboBox(JComboBox<String> comboBox) {
		DefaultComboBoxModel<String> comboBoxModel = new DefaultComboBoxModel<String>();
		for (BillCompanies billCompany : BillCompanies.values()) {
			comboBoxModel.addElement(billCompany.getDescription());
		}
		comboBox.setModel(comboBoxModel);
	}

	private void populateOwnerComboBox(JComboBox<User> comboBox,
			List<User> users) {
		DefaultComboBoxModel<User> comboBoxModel = new DefaultComboBoxModel<User>();
		for (User user : users) {
			comboBoxModel.addElement(user);
		}
		comboBox.setModel(comboBoxModel);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			this.setVisible(false);
		}
		if (e.getSource() == create) {
			try {
				valueError.setVisible(false);
				createBill();

			} catch (Exception exception) {
				valueError.setVisible(true);
			}
		}
	}

	private void createBill() {
		Bill bill = new Bill();
		bill.setValue(new BigDecimal(value.getText()));
		bill.setOwner(((User) ownerComboBox.getSelectedItem()).getUsername());
		bill.setParticipants(new ArrayList<String>());
		for (JCheckBox checkBox : usersCheckBox) {
			if (checkBox.isSelected()) {
				bill.getParticipants().add(checkBox.getName());
			}
		}
		bill.setCompanyDescription(companiesComboBox.getSelectedItem()
				.toString());
		billDataservice.createBill(bill, loggedUser);
		meniuPanel.updateDebtsTable();
	}

}
