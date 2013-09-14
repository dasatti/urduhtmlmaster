package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class TextAreaDialog extends JDialog
{
	JLabel lblTAreaName,lblTAreaRows,lblTAreaCols,lblTAreaValue;
	JTextField tfTAreaName;
	JTextArea tfTAreaValue;
	JButton btnInsert2,btnClose2;
	JSpinner spnTAreaRows,spnTAreaCols;
	

	String insertHTML="";
	Boolean success=false;
	

	public TextAreaDialog(JFrame parent)
	//FindandReplace()
	{
		super(parent,"Insert Text Area",true);
		//super();
		JPanel pnlTArea=new JPanel();
		pnlTArea.setLayout(null);
		/*
		pnlField.setLayout(new BorderLayout());
		Box boxFind=Box.createVerticalBox();
		Box boxFindRow1=Box.createHorizontalBox();
		Box boxFindRow2=Box.createHorizontalBox();
		Box boxFindRow3=Box.createHorizontalBox();
		*/
		
		
		/****************T AREA****************/
		lblTAreaName=new JLabel("Field Name:");
		tfTAreaName=new JTextField(20);
		btnInsert2=new JButton("Insert");
		btnInsert2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				success=true;
				String s=generateTAreaHTML();
				dispose();
			}
		});
		lblTAreaRows=new JLabel("Rows: ");
		spnTAreaRows=new JSpinner(new SpinnerNumberModel(new Integer(5), new Integer(0), null, new Integer(1)));
		lblTAreaCols=new JLabel("Colums: ");
		spnTAreaCols=new JSpinner(new SpinnerNumberModel(new Integer(15), new Integer(0), null, new Integer(1)));
		lblTAreaValue=new JLabel("Field Value:");
		tfTAreaValue=new JTextArea(20,3);
		btnClose2=new JButton("Close");
		btnClose2.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});
		
		
		
				
		/*****************TArea Panel***************/
		pnlTArea.add(lblTAreaName);
		lblTAreaName.setBounds(10,20,100,25);
		pnlTArea.add(tfTAreaName);
		tfTAreaName.setBounds(80,20,210,25);
		pnlTArea.add(lblTAreaCols);
		lblTAreaCols.setBounds(10,50,100,25);
		pnlTArea.add(spnTAreaCols);
		spnTAreaCols.setBounds(80,50,50,25);
		pnlTArea.add(lblTAreaRows);
		lblTAreaRows.setBounds(200,50,100,25);
		pnlTArea.add(spnTAreaRows);
		spnTAreaRows.setBounds(240,50,50,25);
		pnlTArea.add(lblTAreaValue);
		lblTAreaValue.setBounds(10,80,100,25);
		pnlTArea.add(tfTAreaValue);
		tfTAreaValue.setBounds(80,80,210,50);
		pnlTArea.add(btnInsert2);
		btnInsert2.setSize(10,5);
		btnInsert2.setBounds(300,20,98,25);		
		pnlTArea.add(btnClose2);
		btnClose2.setBounds(300,50,98,25);
		
		
		
		add(pnlTArea);
		tfTAreaName.grabFocus(); 
		
		pack();
		//setSize(405,275);
		setBounds(400,200,415,200);
		setResizable(false);
		//setVisible(true);
	}
	

	public String generateTAreaHTML()
	{
		StringBuffer _insertHTML=new StringBuffer();
		_insertHTML.append("<TEXTAREA ");
		
		String fName=tfTAreaName.getText();
		if(fName.length()!=0)
		{
			_insertHTML.append("NAME=\"").append(fName).append("\" ");
		}
		
		_insertHTML.append("COLS=\""+spnTAreaCols.getValue()+"\" ");
		_insertHTML.append("ROWS=\""+spnTAreaRows.getValue()+"\" ");
		_insertHTML.append(">");
		
		String fValue=tfTAreaValue.getText();
		if(fValue.length()!=0)
		{
			_insertHTML.append(fValue);
		}
		_insertHTML.append("</TEXTAREA>");
		
		//System.out.println(_insertHTML);
		insertHTML=_insertHTML.toString();
		success=true;
		return _insertHTML.toString();
	}
	
	public Boolean succeeded()
	{
		return success;
	}
	public String getHTML()
	{
		return insertHTML;
	}
	
	///*
	public static void main(String a[])
	{
		TextAreaDialog app=new TextAreaDialog(null);
		app.show();
		if(app.succeeded())
		{
			System.out.println(app.generateTAreaHTML());
			//System.out.println(app.getHTML());
			//app.dispose();
		}
	}
	//*/
}