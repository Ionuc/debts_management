package main.com.debts.view;

/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;

import main.com.debts.management.dao.UserDataService;

public class View extends JFrame implements ActionListener {
	private static final long serialVersionUID = 1L;
	private JButton exit;
	private Login login;
	private JLabel title;
	public View(UserDataService userDataService) {
		super();

		setLayout(null);
		setSize(800, 600);

		title = new JLabel("Welcome ");
		title.setBounds(10, 10, 300, 50);
		title.setFont(new Font("Verdana", Font.BOLD, 16));
		title.setVisible(false);
		add(title);
		
		exit = new JButton("Exit");
		exit.setBounds(550, 500, 100, 30);
		add(exit);
		exit.addActionListener(this);

		login = new Login(this, userDataService);
		login.setBounds(200, 200, 400, 200);
		add(login);

		setVisible(true);
		setDefaultCloseOperation(3);

	}

	@Override
	public void actionPerformed(ActionEvent e) {
		if (e.getSource() == exit) {
			System.exit(-1);
		}
	}
	
	public JLabel getTitleLabel()
	{
		return title;
	}
}
