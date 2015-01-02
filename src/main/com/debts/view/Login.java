package main.com.debts.view;

import java.awt.Color;
import java.awt.TextField;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
//import java.awt.event.*;

import javax.swing.*;

import main.com.debts.api.User;
import main.com.debts.management.dao.UserDataService;

public class Login extends JPanel implements ActionListener {
	private static final long serialVersionUID = 1L;
	private Color grey = new Color(100, 100, 100);
	private TextField userTextF, passwordTextF;
	private JButton login;
	private JLabel error;
	private MeniuPanel meniuPanel;

	private UserDataService userDataService;
	private View mainView;

	public Login(View mainView, UserDataService userDataService) {
		super();
		
		this.userDataService = userDataService;
		this.mainView = mainView;
		setLayout(null);
		setSize(400, 200);
		setBackground(grey);

		error = new JLabel();
		error.setBounds(80, 20, 250, 20);
		add(error);

		JLabel userLabel = new JLabel("Username:");
		userLabel.setBounds(80, 50, 100, 30);
		add(userLabel);

		this.userTextF = new TextField();
		userTextF.setBounds(210, 50, 100, 30);
		add(userTextF);

		JLabel passLabel = new JLabel("Password:");
		passLabel.setBounds(80, 100, 100, 30);
		add(passLabel);

		this.passwordTextF = new TextField();
		passwordTextF.setBounds(210, 100, 100, 30);
		add(passwordTextF);

		this.login = new JButton("Login");
		this.login.setBounds(210, 150, 100, 30);
		add(this.login);
		this.login.addActionListener(this);
	};

	private void performeLogin() {
		try {
			User logedUser = userDataService.login(userTextF.getText(),passwordTextF.getText());
			if (logedUser != null) {
				error.setText("");
				createMeniuPanel(logedUser);

			} else {
				error.setText("Ussername and Password doesn't exist");
			}
		} catch (Exception ex) {
			error.setText("Enter a numeric password");
		}
	}

	private void createMeniuPanel(User logedUser) {
		meniuPanel = new MeniuPanel(mainView, userDataService, logedUser);
		meniuPanel.setBounds(70, 50, 650, 400);
		meniuPanel.setVisible(true);
		mainView.add(meniuPanel);
		this.setVisible(false);
		mainView.getTitleLabel().setText("Welcome " + logedUser.getFullname());
		mainView.getTitleLabel().setVisible(true);
		mainView.repaint();
	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == login) {
			performeLogin();
		}
	}

}
