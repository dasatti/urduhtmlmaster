package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;
import java.util.*;

public class ListBoxDialog extends JDialog
{
	
	JTextField tfListName,tfFieldName,tfFieldValue;
	JButton btnInsert,btnAdd,btnDelete;
	JList lstFieldNames,lstFieldValues;

	String lboxHTML="";
	Boolean success=false;
	ArrayList fieldNames;
	ArrayList fieldValues;
	

	public ListBoxDialog(JFrame parent)
	{
		super(parent,"Insert List",true);
		//super();
		fieldNames=new ArrayList();
		fieldValues=new ArrayList();
		
		JPanel pnlParent=new JPanel();
		pnlParent.setLayout(null);
		
		JLabel lblListName=new JLabel("List Name:");
		tfListName=new JTextField(20);
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
		JPanel pnlLName=new JPanel();
		//pnlLName.setLayout(null);
		add(lblListName);
		lblListName.setBounds(20,20,100,25);
		add(tfListName);
		tfListName.setBounds(100,20,170,25);
		add(btnInsert);
		btnInsert.setBounds(280,20,100,25);
		
		//pnlParent.add(pnlLName);
		
		JLabel lblFieldName=new JLabel("Field Name:");
		JLabel lblFieldValue=new JLabel("Field Value:");
		tfFieldName=new JTextField();
		tfFieldValue=new JTextField();
		btnAdd=new JButton("Add");
		btnAdd.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					addItem(tfFieldName.getText().toString(),tfFieldValue.getText().toString());
					tfFieldName.setText("");
					tfFieldValue.setText("");
					tfFieldName.grabFocus();
				}
			}
			);
		btnDelete=new JButton("delete");
		btnDelete.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					deleteItem(tfFieldName.getText().toString(),tfFieldValue.getText().toString());
					tfFieldName.setText("");
					tfFieldValue.setText("");
					tfFieldName.grabFocus();
				}
			}
			);
		lstFieldNames=new JList(fieldNames.toArray());
		lstFieldNames.addListSelectionListener(
			new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					if(!lstFieldNames.isSelectionEmpty())
					{
						int index=lstFieldNames.getSelectedIndex();
						lstFieldValues.setSelectedIndex(index);
						tfFieldName.setText(lstFieldNames.getSelectedValue().toString());
						tfFieldValue.setText(lstFieldValues.getSelectedValue().toString());
					}
				}
			}
			);
		lstFieldValues=new JList(fieldValues.toArray());
		lstFieldValues.addListSelectionListener(
			new ListSelectionListener()
			{
				public void valueChanged(ListSelectionEvent e)
				{
					if(!lstFieldValues.isSelectionEmpty())
					{
						int index=lstFieldValues.getSelectedIndex();
						lstFieldNames.setSelectedIndex(index);
						tfFieldName.setText(lstFieldNames.getSelectedValue().toString());
						tfFieldValue.setText(lstFieldValues.getSelectedValue().toString());
					}
				}
			}
			);
		lstFieldNames.setVisibleRowCount(4);
		lstFieldValues.setVisibleRowCount(4);
		
		JPanel pnlFields=new JPanel();
		pnlFields.setLayout(new GridLayout(2,1));
		JPanel pnlAddFields=new JPanel(new GridLayout(3,2));
		JPanel pnlPreviewFields=new JPanel(new GridLayout(1,2));
		pnlAddFields.add(lblFieldName);
		pnlAddFields.add(lblFieldValue);
		pnlAddFields.add(tfFieldName);
		pnlAddFields.add(tfFieldValue);
		pnlAddFields.add(btnAdd);
		pnlAddFields.add(btnDelete);
		pnlPreviewFields.add(new JScrollPane(lstFieldNames));
		pnlPreviewFields.add(new JScrollPane(lstFieldValues));
		pnlFields.add(pnlAddFields);
		pnlFields.add(pnlPreviewFields);
		pnlFields.setBorder(new TitledBorder(new EtchedBorder(), "Fields")); 
		
		pnlFields.setBounds(20,60,370,175);
		
		pnlParent.add(pnlFields);
		
		
		add(pnlParent);
		pack();
		setBounds(400,200,415,300);
		setResizable(false);
		//setVisible(true);
	}
	
	public void addItem(String _name, String _value)
	{
		if(_name.length()>0)
		{
		if(_value.length()<=0)
		{
			_value=" ";
		}
		fieldNames.add(_name);
		fieldValues.add(_value);
		
		lstFieldNames.setListData(fieldNames.toArray());
		lstFieldValues.setListData(fieldValues.toArray());
		}

	}
	
	public void deleteItem(String _name, String _value)
	{
		fieldNames.remove(_name);
		fieldValues.remove(_value);
		
		lstFieldNames.setListData(fieldNames.toArray());
		lstFieldValues.setListData(fieldValues.toArray());

	}
	
	public void updateFields(String _name, String _value)
	{
		tfFieldName.setText(_name);
		tfFieldValue.setText(_value);

	}

	public String generateHTML()
	{
		String _lboxHTML="<SELECT";
		if(tfListName.getText().length()>0)
			_lboxHTML+=" Name=\""+tfListName.getText().toString()+"\"";
		_lboxHTML+=">";
		
		Object arrFieldNames[]=fieldNames.toArray();
		Object arrFieldValues[]=fieldValues.toArray();
		for(int i=0;i<fieldNames.size();i++)
		{
			_lboxHTML+="<OPTION VALUE=\""+((String)arrFieldValues[i]).toString()+"\">"+((String)arrFieldNames[i]).toString()+"</OPTION>";
		}
		if(fieldNames.size()<=0)
			_lboxHTML+="<OPTION></OPTION>";
		
		_lboxHTML+="</SELECT>";
		lboxHTML=_lboxHTML;
		success=true;
		return _lboxHTML.toString();
	}
	
	public Boolean succeeded()
	{
		return success;
	}
	public String getHTML()
	{
		return lboxHTML;
	}
	
	///*
	public static void main(String a[])
	{
		ListBoxDialog app=new ListBoxDialog(null);
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