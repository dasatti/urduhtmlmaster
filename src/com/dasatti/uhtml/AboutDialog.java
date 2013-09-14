package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.event.*;

public class AboutDialog extends JDialog
{
	
	

	public AboutDialog(JFrame parent)
	{
		super(parent,"About HTML Master",true);
		//super();
              ///  org.eclipse.swt.browser.
		ImageIcon IcnAbt=new ImageIcon("./res/images/splash/About.jpg");
		JLabel lblAbt=new JLabel(IcnAbt);
		JLabel lblCredits=new JLabel("Developed By: ");
		JLabel lblCredits2=new JLabel("Danish Altaf Satti");
		JLabel lblCredits3=new JLabel("PCIT Campus");
		JLabel lblCredits4=new JLabel("University of Centeral Punjab");
		JLabel lblCredits5=new JLabel("Rawalpindi");
		JLabel lblCredits6=new JLabel("dasatti@gmail.com");
		
		setLayout(null);
		add(lblAbt);
		add(lblCredits);
		add(lblCredits2);
		add(lblCredits3);
		add(lblCredits4);
		add(lblCredits5);
		add(lblCredits6);
		lblAbt.setBounds(0,5,330,130);
		lblCredits.setBounds(15,150,330,20);
		lblCredits2.setBounds(85,170,330,20);
		lblCredits3.setBounds(85,190,330,20);
		lblCredits4.setBounds(85,210,330,20);
		lblCredits5.setBounds(85,230,330,20);
		lblCredits6.setBounds(85,250,330,20);
		
		pack();
		//setSize(405,275);
		setBounds(400,200,335,320);
		setResizable(false);
		setVisible(true);
	}
	


	///*
	public static void main(String a[])
	{
		AboutDialog app=new AboutDialog(null);
		app.show();
		
	}
	//*/
}