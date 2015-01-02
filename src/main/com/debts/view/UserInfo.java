package main.com.debts.view;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;

import main.com.debts.api.User;
import main.com.debts.management.dao.UserDataService;

public class UserInfo extends JFrame implements ActionListener {

	private static final long serialVersionUID = 1L;
	private JButton exit, createOrUpdate;
	private JTextField usernameValue, nameValue, surnameValue, fullnameValue,
					   ageValue, administratorValue, passwordValue;

	private User user, loggedUser;
	private UserDataService userDataService;
	private MeniuPanel meniuPanel;
	
	public UserInfo(MeniuPanel meniuPanel, UserDataService userDataService, User user, User loggedUser) {
		super();
		
		this.userDataService = userDataService;
		this.user = user;
		this.loggedUser = loggedUser;
		this.meniuPanel = meniuPanel;
		
		setLayout(null);
		setSize(400, 400);
		setBackground(Color.gray);
		setTitle("User information");
		
		JLabel usernameLabel = new JLabel("Username: ");
		usernameLabel.setBounds(10, 10, 150, 30);
		add(usernameLabel);
		
		usernameValue = new JTextField(user == null ? "" : user.getUsername());
		usernameValue.setBounds(120, 10, 150, 30);
		add(usernameValue);
		
		JLabel nameLabel = new JLabel("Name: ");
		nameLabel.setBounds(10, 50, 150, 30);
		add(nameLabel);
		
		nameValue = new JTextField(user == null ? "" : user.getName());
		nameValue.setBounds(120, 50, 150, 30);
		add(nameValue);
		
		JLabel surnnameLabel = new JLabel("Surname: ");
		surnnameLabel.setBounds(10, 90, 150, 30);
		add(surnnameLabel);
		
		surnameValue = new JTextField(user == null ? "" : user.getSurname());
		surnameValue.setBounds(120, 90, 150, 30);
		add(surnameValue);
		
		JLabel fullnameLabel = new JLabel("Fullname: ");
		fullnameLabel.setBounds(10, 130, 150, 30);
		add(fullnameLabel);
		
		fullnameValue = new JTextField(user == null ? "" : user.getFullname());
		fullnameValue.setBounds(120, 130, 150, 30);
		add(fullnameValue);
		
		JLabel ageLabel = new JLabel("Age: ");
		ageLabel.setBounds(10, 170, 150, 30);
		add(ageLabel);
		
		ageValue = new JTextField(String.valueOf(user == null ? "" : user.getAge()));
		ageValue.setBounds(120, 170, 150, 30);
		add(ageValue);
		
		JLabel administratorLabel = new JLabel("Administrator: ");
		administratorLabel.setBounds(10, 210, 150, 30);
		add(administratorLabel);
		
		administratorValue = new JTextField(String.valueOf(user == null ? "" : user.isAdministrator()));
		administratorValue.setBounds(120, 210, 150, 30);
		add(administratorValue);
		
		if (user == null)
		{
			JLabel passwordLabel = new JLabel("Password: ");
			passwordLabel.setBounds(10, 250, 150, 30);
			add(passwordLabel);
			
			passwordValue = new JTextField();
			passwordValue.setBounds(120, 250, 150, 30);
			add(passwordValue);
		}
		
		exit = new JButton("Exit");
		exit.setBounds(280, 330, 100, 30);
		add(exit);
		exit.addActionListener(this);
		
		createOrUpdate = new JButton(user == null ? "Create" : "Update");
		createOrUpdate.setBounds(280, 290, 100, 30);
		add(createOrUpdate);
		createOrUpdate.addActionListener(this);
		
		setVisible(false);
		setDefaultCloseOperation(3);
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			this.setVisible(false);
		}
		if (e.getSource() == createOrUpdate)
		{
			createOrUpdateUser();
		}
	}

	private void createOrUpdateUser()
	{
		User newUser = new User();
		newUser.setAge(Integer.parseInt(ageValue.getText()));
		newUser.setFullname(fullnameValue.getText());
		newUser.setName(nameValue.getText());
		newUser.setSurname(surnameValue.getText());
		newUser.setUsername(usernameValue.getText());
		newUser.setAdministrator(Boolean.getBoolean(administratorValue.getText()));
		if (user == null)
		{
			newUser.setPassword(passwordValue.getText());
			userDataService.create(newUser, loggedUser);
		}
		else
		{
			newUser.setId(user.getId());
			newUser.setPassword(user.getPassword());
			userDataService.update(user, newUser, loggedUser);
		}
		meniuPanel.updateUsersTable();
		initializeUserInfo();
	}
	
	private void initializeUserInfo()
	{
		administratorValue.setText("");
		surnameValue.setText("");
		nameValue.setText("");
		usernameValue.setText("");
		fullnameValue.setText("");
		ageValue.setText("");
		passwordValue.setText("");
	}
}
