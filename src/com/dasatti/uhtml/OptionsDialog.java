package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class OptionsDialog extends JDialog
{
	JLabel lblFieldName,lblFieldSize,lblMaxSize,lblFieldValue;
	JLabel lblTAreaName,lblTAreaRows,lblTAreaCols,lblTAreaValue;
	JRadioButton rbText,rbPassword,rbHidden;
	JTextField tfFieldName,tfFieldValue,tfTAreaName;
	JTextArea tfTAreaValue;
	JButton btnInsert,btnClose,btnInsert2,btnClose2;
	JTabbedPane tabpane;
	JSpinner spnFieldSize,spnMaxSize,spnTAreaRows,spnTAreaCols;
	
	int searchIndex=0;
	String insertHTML="";
	Boolean success=false;
	public static final int TF_TEXT=0;
	public static final int TF_PASS=1;
	public static final int TF_HIDDEN=2;
	private int tf_type=0;
	

	public OptionsDialog(JFrame parent,int selectedTab)
	//FindandReplace()
	{
		super(parent,"Insert Field",true);
		//super();
		setTitle("Insert Field");
		JPanel pnlField=new JPanel();
		pnlField.setLayout(null);
		JPanel pnlTArea=new JPanel();
		pnlTArea.setLayout(null);
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
		/*
		boxFindRow1.add(lblFindWhat);
		boxFindRow1.add(tfFieldName);
		boxFindRow1.add(btnInsert);
		boxFindRow1.add(Box.createHorizontalStrut(90));
		boxFindRow2.add(btnClose);
		boxFind.add(boxFindRow1);
		boxFind.add(boxFindRow2);
		pnlField.add(boxFind);
		*/
		
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
		

		//pnlField.add(cbWholeWords);
		//pnlField.add(cbMatchCase);
		//pnlField.add(rbText);
		//pnlField.add(rbPassword);
		//cbWholeWords.setBounds(10,120,150,25);
		//cbMatchCase.setBounds(10,150,150,25);
		//rbText.setBounds(160,120,150,25);
		//rbPassword.setBounds(160,150,150,25);
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
		
		
		tabpane=new JTabbedPane();
		tabpane.addTab("Insert Field",pnlField);
		tabpane.addTab("Insert Text Area",pnlTArea);
		tabpane.setSelectedIndex(selectedTab);
		add(tabpane);
		WindowListener flst = new WindowAdapter() 
		{ 
			public void windowActivated(WindowEvent e) 
			{ 
				if (tabpane.getSelectedIndex()==0) 
				tfFieldName.grabFocus();
				if (tabpane.getSelectedIndex()==1) 
				tfTAreaName.grabFocus(); 
			} 
			public void windowDeactivated(WindowEvent e) 
			{ 
			} 
		}; 
		addWindowListener(flst); 
		pack();
		//setSize(405,275);
		setBounds(200,200,415,240);
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
		OptionsDialog app=new OptionsDialog(null,0);
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