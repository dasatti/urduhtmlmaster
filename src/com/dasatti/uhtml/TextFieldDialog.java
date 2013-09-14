package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class TextFieldDialog extends JDialog
{
	JLabel lblFieldName,lblFieldSize,lblMaxSize,lblFieldValue;
	JRadioButton rbText,rbPassword,rbHidden;
	JTextField tfFieldName,tfFieldValue,tfTAreaName;
	JButton btnInsert,btnClose,btnInsert2,btnClose2;
	JSpinner spnFieldSize,spnMaxSize;
	
	int searchIndex=0;
	String insertHTML="";
	Boolean success=false;
	public static final int TF_TEXT=0;
	public static final int TF_PASS=1;
	public static final int TF_HIDDEN=2;
	private int tf_type=0;
	

	public TextFieldDialog(JFrame parent)
	//FindandReplace()
	{
		super(parent,"Insert Field",true);
		//super();
		setTitle("Insert Field");
		JPanel pnlField=new JPanel();
		pnlField.setLayout(null);
		/*
		pnlField.setLayout(new BorderLayout());
		Box boxFind=Box.createVerticalBox();
		Box boxFindRow1=Box.createHorizontalBox();
		Box boxFindRow2=Box.createHorizontalBox();
		Box boxFindRow3=Box.createHorizontalBox();
		*/
		/****************T FIELD****************/
		lblFieldName=new JLabel("Field Name:");
		tfFieldName=new JTextField(20);
		btnInsert=new JButton("Insert");
		btnInsert.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				success=true;
				//System.out.println(generateHTML());
				String s=generateHTML();
				dispose();
			}
		});
		lblFieldSize=new JLabel("Field Size: ");
		spnFieldSize=new JSpinner(new SpinnerNumberModel(new Integer(15), new Integer(0), null, new Integer(1)));
		lblMaxSize=new JLabel("Maximum Size: ");
		spnMaxSize=new JSpinner(new SpinnerNumberModel(new Integer(50), new Integer(0), null, new Integer(1)));
		lblFieldValue=new JLabel("Field Value:");
		tfFieldValue=new JTextField(20);
		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});
		
		
		
		/*****************Field Panel***************/
		pnlField.add(lblFieldName);
		lblFieldName.setBounds(10,20,100,25);
		pnlField.add(tfFieldName);
		tfFieldName.setBounds(80,20,210,25);
		pnlField.add(lblFieldSize);
		lblFieldSize.setBounds(10,50,100,25);
		pnlField.add(spnFieldSize);
		spnFieldSize.setBounds(80,50,50,25);
		pnlField.add(lblMaxSize);
		lblMaxSize.setBounds(150,50,100,25);
		pnlField.add(spnMaxSize);
		spnMaxSize.setBounds(240,50,50,25);
		pnlField.add(lblFieldValue);
		lblFieldValue.setBounds(10,80,100,25);
		pnlField.add(tfFieldValue);
		tfFieldValue.setBounds(80,80,210,25);
		pnlField.add(btnInsert);
		btnInsert.setSize(10,5);
		btnInsert.setBounds(300,20,98,25);		
		pnlField.add(btnClose);
		btnClose.setBounds(300,50,98,25);
			
		rbText=new JRadioButton("Text",true);
		rbPassword=new JRadioButton("Password");
		rbHidden=new JRadioButton("Hidden");
		
		
		ButtonGroup btnGrp=new ButtonGroup();
		btnGrp.add(rbText);
		btnGrp.add(rbPassword);
		btnGrp.add(rbHidden);
		
		JPanel pnlFieldOptions=new JPanel();
		pnlFieldOptions.add(rbText);
		pnlFieldOptions.add(rbPassword);
		pnlFieldOptions.add(rbHidden);
		pnlFieldOptions.setLayout(new GridLayout(1,3));
		pnlFieldOptions.setBorder(new TitledBorder(new EtchedBorder(), "Field Type")); 
		pnlFieldOptions.setBounds(10,120,280,50);
		
		pnlField.add(pnlFieldOptions);
		
		
		add(pnlField);
		tfFieldName.grabFocus();
		 
		pack();
		//setSize(405,275);
		setBounds(400,200,415,200);
		setResizable(false);
		//setVisible(true);
	}
	
	public String generateHTML()
	{
		StringBuffer _insertHTML=new StringBuffer();
		_insertHTML.append("<INPUT ");
		if(rbText.isSelected())
			_insertHTML.append("TYPE=\"TEXT\" ");
		if(rbPassword.isSelected())
			_insertHTML.append("TYPE=\"PASSWORD\" ");
		if(rbHidden.isSelected())
			_insertHTML.append("TYPE=\"HIDDEN\" ");
		
		String fName=tfFieldName.getText();
		if(fName.length()!=0)
		{
			_insertHTML.append("NAME=\"").append(fName).append("\" ");
		}
		
		String fValue=tfFieldValue.getText();
		if(fValue.length()!=0)
		{
			_insertHTML.append("VALUE=\"").append(fValue+"\" ");
		}
		
		_insertHTML.append("SIZE=\""+spnFieldSize.getValue()+"\" ");
		_insertHTML.append("MAXLENGTH=\""+spnMaxSize.getValue()+"\" ");
		_insertHTML.append(">");
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
	public void setTFType(int _tf_type)
	{
		if(_tf_type==TF_TEXT)
			rbText.setSelected(true);
		if(_tf_type==TF_PASS)
			rbPassword.setSelected(true);
		if(_tf_type==TF_HIDDEN)
			rbHidden.setSelected(true);
	}
	
	///*
	public static void main(String a[])
	{
		TextFieldDialog app=new TextFieldDialog(null);
		app.show();
		if(app.succeeded())
		{
			System.out.println(app.generateHTML());
			//System.out.println(app.getHTML());
			//app.dispose();
		}
	}
	//*/
}