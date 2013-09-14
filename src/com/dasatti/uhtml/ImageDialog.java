package com.dasatti.uhtml;

import java.io.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;
import java.util.*;

public class ImageDialog extends JDialog
{
	
	JTextField tfImageURL,tfAltText;
	JButton btnBrowse,btnInsert,btnCancel;
	JCheckBox chkSpecifySize;
	JSpinner spnWidth,spnHeight;
	JRadioButton rbtnPixcels,rbtnPercent;
	JFrame parent=null;

	String imgHTML="";
        String imgUrl = "";
        String imgPath = "";
	Boolean success=false;
	

	public ImageDialog(JFrame _parent)
	{
		super(_parent,"Insert Image",true);
		//super();
		parent=_parent;
		
		JPanel pnlParent=new JPanel();
		pnlParent.setLayout(null);
		
		JLabel lblImageURL=new JLabel("Location:");
		tfImageURL=new JTextField(20);
		btnBrowse=new JButton("Browse");
		btnBrowse.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					JFileChooser jfc=new JFileChooser();
					if(jfc.showOpenDialog(parent)==JFileChooser.APPROVE_OPTION)
					{
						File f=jfc.getSelectedFile();
						try
						{
							imgUrl=f.toURI().toString();
                                                        imgPath = f.getAbsolutePath();
							if(imgUrl!=null)
							{
								tfImageURL.setText(imgUrl);
							}
						}
						catch(Exception ex)
						{
						}
					}
				}
			}
			);
		btnInsert=new JButton("Insert");
		btnInsert.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					String s=generateHTML();
					dispose();
				}
			}
			);
		btnCancel=new JButton("Cancel");
		btnCancel.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					dispose();
				}
			}
			);
		JLabel lblAltText=new JLabel("Alt Text:");
		tfAltText=new JTextField(20);
		
		JPanel pnlLName=new JPanel();
		//pnlLName.setLayout(null);
		add(lblImageURL);
		lblImageURL.setBounds(20,20,100,25);
		add(tfImageURL);
		tfImageURL.setBounds(100,20,170,25);
		add(btnBrowse);
		btnBrowse.setBounds(280,20,100,25);
		add(lblAltText);
		lblAltText.setBounds(20,50,100,25);
		add(tfAltText);
		tfAltText.setBounds(100,50,280,25);
		add(btnInsert);
		btnInsert.setBounds(20,200,150,25);
		add(btnCancel);
		btnCancel.setBounds(230,200,150,25);
		
		//pnlParent.add(pnlLName);
		
		chkSpecifySize=new JCheckBox("Specify Size");
		chkSpecifySize.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(chkSpecifySize.isSelected())
					{
						spnWidth.setEnabled(true);
						spnHeight.setEnabled(true);
						rbtnPixcels.setEnabled(true);
						rbtnPercent.setEnabled(true);
					}
					else
					{
						spnWidth.setEnabled(false);
						spnHeight.setEnabled(false);
						rbtnPixcels.setEnabled(false);
						rbtnPercent.setEnabled(false);
					}
				}
			}
			);
		
		JPanel pnlSize=new JPanel();
		pnlSize.setLayout(new GridLayout(3,1));
		JPanel pnlChkBox=new JPanel(new GridLayout(2,2));
		JPanel pnlSize2=new JPanel(new GridLayout(1,6));
		JPanel pnlSize3=new JPanel(new GridLayout(1,2));
		pnlChkBox.add(chkSpecifySize);
		spnWidth=new JSpinner(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(1)));
		spnHeight=new JSpinner(new SpinnerNumberModel(new Integer(20), new Integer(0), null, new Integer(1)));
		rbtnPixcels=new JRadioButton("In Pixels",true);
		rbtnPercent=new JRadioButton("In Percentage");
		spnWidth.setEnabled(false);
		spnHeight.setEnabled(false);
		rbtnPixcels.setEnabled(false);
		rbtnPercent.setEnabled(false);
		pnlChkBox.add(new JLabel(""));
		pnlChkBox.add(new JLabel(""));
		pnlSize2.add(new JLabel("Width:"));
		pnlSize2.add(spnWidth);
		pnlSize2.add(new JLabel(" "));
		pnlSize2.add(new JLabel("Height:"));
		pnlSize2.add(new JLabel(" "));
		pnlSize2.add(spnHeight);
		pnlSize3.add(rbtnPixcels);
		pnlSize3.add(rbtnPercent);
		pnlSize.add(pnlChkBox);
		pnlSize.add(pnlSize2);
		pnlSize.add(pnlSize3);
		pnlSize.setBorder(new TitledBorder(new EtchedBorder(), "Size")); 
		
		pnlSize.setBounds(20,80,370,110);
		
		pnlParent.add(pnlSize);
		
		
		add(pnlParent);
		pack();
		setBounds(400,200,415,275);
		setResizable(false);
		//setVisible(true);
	}
	
	public String generateHTML()
	{
		String _html="<IMG SRC=\""+tfImageURL.getText().toString()+"\" ALT=\""+tfAltText.getText().toString()+"\"";
		if(chkSpecifySize.isSelected())
		{
			String unit="";
			if(rbtnPercent.isSelected())
				unit="%";
			
			int Width =((Integer) spnWidth.getValue()).intValue();
			int Height =((Integer) spnHeight.getValue()).intValue();
			
			_html+=" WIDTH=\""+Width+unit+"\" HEIGHT=\""+Height+unit+"\"";
			
		}
		_html+=">";
		success=true;
		imgHTML=_html;
		return _html;
	}
	
	public Boolean succeeded()
	{
		return success;
	}
	public String getHTML()
	{
		return imgHTML;
	}
	public String getImageURL()
	{
		return imgUrl;
	}
        public String getImagePath()
	{
		return imgPath;
	}
	///*
	public static void main(String a[])
	{
		ImageDialog app=new ImageDialog(null);
		app.show();
		if(app.succeeded())
		{
			System.out.println(app.getHTML());
			//System.out.println(app.getHTML());
			//app.dispose();
		}
	}
	//*/
}