package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;


import com.lang.LangControler;

public class FindDialog extends JDialog
{
	JLabel lblFindWhat,lblReplaceWhat,lblReplaceWith,lblLanguage,lblLanguage2;
	JCheckBox cbWholeWords,cbMatchCase,cbWholeWords2,cbMatchCase2;
	JRadioButton rbSearchUp,rbSearchDown,rbSearchUp2,rbSearchDown2;
	JTextField tfFindWhat,tfReplaceWhat,tfReplaceWith;
	JButton btnFindNext,btnReplace,btnReplaceAll,btnClose,btnClose2;
	JTabbedPane tabpane;
	JComboBox cmbLanguage,cmbLanguage2;
	
	JEditorPane m_ed_Pane;
	HTMLDocument m_doc;
	JEditorPane jtp;
	int searchIndex=0;
	
	LangControler objLang;

        UMasterUI parent;

//	public FindDialog(HTMLDocument _doc,int selectedTab,JEditorPane _jtp)
	//FindandReplace()
        public FindDialog(UMasterUI _parent,int selectedTab)
	{
		//super(parent,"Find and replace",false);
		super();
                setModal(true);
		setTitle("Find and Replace");
                parent = _parent;
		m_doc=(HTMLDocument) parent.m_internalFrame_document.getDesignDocument().getDocument();
		jtp= parent.m_internalFrame_document.getDesignDocument();
		objLang=new LangControler();
		
		JPanel pnlFind=new JPanel();
		pnlFind.setLayout(null);
		JPanel pnlReplace=new JPanel();
		pnlReplace.setLayout(null);
		/*
		pnlFind.setLayout(new BorderLayout());
		Box boxFind=Box.createVerticalBox();
		Box boxFindRow1=Box.createHorizontalBox();
		Box boxFindRow2=Box.createHorizontalBox();
		Box boxFindRow3=Box.createHorizontalBox();
		*/
		lblFindWhat=new JLabel("Find What:");
		tfFindWhat=new JTextField(20);
		btnFindNext=new JButton("Find Next");
		btnFindNext.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Find();
			}
		});
		btnClose=new JButton("Close");
		btnClose.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				dispose();
			}
		});
		lblLanguage=new JLabel("Language:");
		cmbLanguage=new JComboBox(new String[]{"English","Urdu"});
		cmbLanguage.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					setFindKeymap(cmbLanguage.getSelectedItem().toString());
				}
			}
			);
		lblLanguage2=new JLabel("Language:");
		cmbLanguage2=new JComboBox(new String[]{"English","Urdu"});
		cmbLanguage2.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					setReplaceKeymap(cmbLanguage2.getSelectedItem().toString());
				}
			}
			);
		lblReplaceWhat=new JLabel("Replace What:");
		lblReplaceWith=new JLabel("Replace With:");
		tfReplaceWhat=new JTextField(20);
		tfReplaceWith=new JTextField(20);
		btnReplace=new JButton("Replace");
		btnReplace.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				Replace();
			}
		});
		btnReplaceAll=new JButton("Replace All");
		btnReplaceAll.addActionListener(new ActionListener()
		{
			public void actionPerformed(ActionEvent ae)
			{
				ReplaceAll();
			}
		});
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
		boxFindRow1.add(tfFindWhat);
		boxFindRow1.add(btnFindNext);
		boxFindRow1.add(Box.createHorizontalStrut(90));
		boxFindRow2.add(btnClose);
		boxFind.add(boxFindRow1);
		boxFind.add(boxFindRow2);
		pnlFind.add(boxFind);
		*/
		pnlFind.add(lblFindWhat);
		lblFindWhat.setBounds(10,20,100,25);
		pnlFind.add(tfFindWhat);
		tfFindWhat.setBounds(105,20,180,25);
		pnlFind.add(btnFindNext);
		btnFindNext.setSize(10,5);
		btnFindNext.setBounds(290,20,98,25);	
		pnlFind.add(lblLanguage);
		lblLanguage.setBounds(10,50,100,25);	
		pnlFind.add(cmbLanguage);
		cmbLanguage.setBounds(105,50,100,25);	
		pnlFind.add(btnClose);
		btnClose.setBounds(290,50,98,25);
		
		pnlReplace.add(lblReplaceWhat);
		lblReplaceWhat.setBounds(10,20,100,25);
		pnlReplace.add(lblReplaceWith);
		lblReplaceWith.setBounds(10,50,100,25);
		pnlReplace.add(tfReplaceWhat);
		tfReplaceWhat.setBounds(105,20,180,25);
		pnlReplace.add(tfReplaceWith);
		tfReplaceWith.setBounds(105,50,180,25);
		pnlReplace.add(btnReplace);
		btnReplace.setSize(10,5);
		btnReplace.setBounds(290,20,98,25);
		pnlReplace.add(btnReplaceAll);
		btnReplaceAll.setBounds(290,50,98,25);
		pnlReplace.add(lblLanguage2);
		lblLanguage2.setBounds(10,80,100,25);	
		pnlReplace.add(cmbLanguage2);
		cmbLanguage2.setBounds(105,80,100,25);
		pnlReplace.add(btnClose2);
		btnClose2.setBounds(290,80,98,25);
		
		cbWholeWords=new JCheckBox("Whole Words Only");
		cbMatchCase=new JCheckBox("Match Case",true);
		rbSearchUp=new JRadioButton("Search up");
		rbSearchDown=new JRadioButton("Search down",true);
		cbWholeWords2=new JCheckBox("Whole Words Only");
		cbMatchCase2=new JCheckBox("Match Case",true);
		rbSearchUp2=new JRadioButton("Search up");
		rbSearchDown2=new JRadioButton("Search down",true);
		//pnlFind.add(cbWholeWords);
		//pnlFind.add(cbMatchCase);
		//pnlFind.add(rbSearchUp);
		//pnlFind.add(rbSearchDown);
		//cbWholeWords.setBounds(10,120,150,25);
		//cbMatchCase.setBounds(10,150,150,25);
		//rbSearchUp.setBounds(160,120,150,25);
		//rbSearchDown.setBounds(160,150,150,25);
		ButtonGroup btnGrp=new ButtonGroup();
		btnGrp.add(rbSearchUp);
		btnGrp.add(rbSearchDown);
		ButtonGroup btnGrp2=new ButtonGroup();
		btnGrp2.add(rbSearchUp2);
		btnGrp2.add(rbSearchDown2);
		JPanel pnlFindOptions=new JPanel();
		JPanel pnlReplaceOptions=new JPanel();
		pnlFindOptions.add(cbWholeWords);
		pnlFindOptions.add(rbSearchUp);
		pnlFindOptions.add(cbMatchCase);
		pnlFindOptions.add(rbSearchDown);
		pnlFindOptions.setLayout(new GridLayout(2,2));
		pnlFindOptions.setBorder(new TitledBorder(new EtchedBorder(), "Options")); 
		pnlFindOptions.setBounds(10,100,280,100);
		pnlReplaceOptions.add(cbWholeWords2);
		pnlReplaceOptions.add(rbSearchUp2);
		pnlReplaceOptions.add(cbMatchCase2);
		pnlReplaceOptions.add(rbSearchDown2);
		pnlReplaceOptions.setLayout(new GridLayout(2,2));
		pnlReplaceOptions.setBorder(new TitledBorder(new EtchedBorder(), "Options")); 
		pnlReplaceOptions.setBounds(10,100,280,100);
		pnlFind.add(pnlFindOptions);
		pnlReplace.add(pnlReplaceOptions);
		tabpane=new JTabbedPane();
		tabpane.addTab("Find",pnlFind);
		tabpane.addTab("Replace",pnlReplace);
		tabpane.setSelectedIndex(selectedTab);
		add(tabpane);
		WindowListener flst = new WindowAdapter() 
		{ 
			public void windowActivated(WindowEvent e) 
			{ 
				if (tabpane.getSelectedIndex()==0) 
				tfFindWhat.grabFocus(); 
				else 
				tfReplaceWhat.grabFocus(); 
			} 
			public void windowDeactivated(WindowEvent e) 
			{ 
			} 
		}; 
		addWindowListener(flst); 
		pack();
		//setSize(405,275);
		setBounds(200,200,405,275);
		setResizable(false);
		setVisible(true);
	}
	
	public void Find()
	{
		String searchKey=tfFindWhat.getText();
		if(searchKey.length()==0)
		{
			JOptionPane.showMessageDialog(this,"Please specify text you want to find");
		}
		else
		{
			try
			{
				String AllText=m_doc.getText(0,m_doc.getLength());
				String searchFrom="",key="";
				int startIndex=-1;
				int endIndex=-1;
				
				if(!cbMatchCase.isSelected())
				{
					searchFrom=AllText.toLowerCase();
					key=searchKey.toLowerCase();
				}
				else
				{
					searchFrom=AllText;
					key=searchKey;
				}
				if(rbSearchUp.isSelected())
				{
					startIndex=searchFrom.lastIndexOf(key,searchIndex);
					if(!(endIndex>=m_doc.getLength()-1))
					{
						endIndex=startIndex+key.length();
						searchIndex=startIndex-1;
					}
				}
				else
				{
					startIndex=searchFrom.indexOf(key,searchIndex);
					if(!(endIndex>=m_doc.getLength()-1))
					{
						endIndex=startIndex+key.length();
						searchIndex=endIndex;
					}
				}
				if(startIndex<0)
				{
					JOptionPane.showMessageDialog(this,"Input Text not found");
				}
				else
				{
//					JOptionPane.showMessageDialog(this,"Input Text found between index "+startIndex+" and "+endIndex);
					jtp.select(startIndex,endIndex);
//                                    parent._select(startIndex, endIndex);
				}
			}
			catch(BadLocationException e)
			{
				JOptionPane.showMessageDialog(this,"Please specify text you want to find");
			}
		}
	}
	
	public void Replace()
	{
		String searchKey=tfReplaceWhat.getText();
		String replaceWith=tfReplaceWith.getText();
		if(searchKey.length()==0 || replaceWith.length()==0)
		{
			JOptionPane.showMessageDialog(this,"Please specify text you want to Replace or Replace With");
		}
		else
		{
			try
			{
				String AllText=m_doc.getText(0,m_doc.getLength());
				String searchFrom="",key="";
				int startIndex=-1;
				int endIndex=-1;
				
				if(!cbMatchCase2.isSelected())
				{
					searchFrom=AllText.toLowerCase();
					key=searchKey.toLowerCase();
				}
				else
				{
					searchFrom=AllText;
					key=searchKey;
				}
				if(rbSearchUp2.isSelected())
				{
					startIndex=searchFrom.lastIndexOf(key,searchIndex);
					if(!(endIndex>=m_doc.getLength()-1))
					{
						endIndex=startIndex+key.length();
						searchIndex=startIndex-1;
					}
				}
				else
				{
					startIndex=searchFrom.indexOf(key,searchIndex);
					if(!(endIndex>=m_doc.getLength()-1))
					{
						endIndex=startIndex+key.length();
						searchIndex=endIndex;
					}
				}
				if(startIndex<0)
				{
					JOptionPane.showMessageDialog(this,"Input Text not found");
				}
				else
				{
					//ed_Pane.doc.setSelection(startIndex,endIndex,true);
					//JOptionPane.showMessageDialog(this,"Input Text found between index "+startIndex+" and "+endIndex);
					jtp.select(startIndex,endIndex);
					jtp.replaceSelection(replaceWith); 
					jtp.select(startIndex, startIndex+replaceWith.length()); 
				}
			}
			catch(BadLocationException e)
			{
				JOptionPane.showMessageDialog(this,"Please specify text you want to Replace");
			}
		}
	}
	
	
	public void ReplaceAll()
	{
		String searchKey=tfReplaceWhat.getText();
		String replaceWith=tfReplaceWith.getText();
		if(searchKey.length()==0 || replaceWith.length()==0)
		{
			JOptionPane.showMessageDialog(this,"Please specify text you want to Replace or Replace With");
		}
		else
		{
			String searchFrom="",key="";
			int startIndex=-1;
			int endIndex=-1;
			
			while(true)
			{
			try
			{
				String AllText=m_doc.getText(0,m_doc.getLength());
				startIndex=-1;
				
				
				if(!cbMatchCase2.isSelected())
				{
					searchFrom=AllText.toLowerCase();
					key=searchKey.toLowerCase();
				}
				else
				{
					searchFrom=AllText;
					key=searchKey;
				}
				//if(rbSearchUp2.isSelected())
				//{
					//startIndex=searchFrom.lastIndexOf(key,searchIndex);
					//if(!(endIndex>=m_ed_Pane.doc.getLength()-1))
					//{
					//	endIndex=startIndex+key.length();
					//	searchIndex=startIndex-1;
				//	}
				//}
				//else
				//{
					
					startIndex=searchFrom.indexOf(key,searchIndex);
					if(!(endIndex>=m_doc.getLength()-1))
					{
						endIndex=startIndex+key.length();
						searchIndex=endIndex;
					}
					else
					break;
				//}
				if(startIndex<0)
				{
					//JOptionPane.showMessageDialog(this,"Input Text not found");
					break;
				}
				else
				{
					//ed_Pane.doc.setSelection(startIndex,endIndex,true);
					//JOptionPane.showMessageDialog(this,"Input Text found between index "+startIndex+" and "+endIndex);
					jtp.select(startIndex,endIndex);
					jtp.replaceSelection(replaceWith); 
					jtp.select(startIndex, startIndex+replaceWith.length()); 
					searchIndex= startIndex+replaceWith.length();
				}
		
			}
			catch(BadLocationException e)
			{
				JOptionPane.showMessageDialog(this,"Please specify text you want to Replace");
			}
		}
		}
	}
	
	public void addKeymaps()
	{
		Keymap parentKeymap=tfFindWhat.getKeymap();
		objLang.english=tfFindWhat.addKeymap("English",parentKeymap);
		objLang.urdu=tfFindWhat.addKeymap("Urdu",parentKeymap);
		JTextComponent.loadKeymap(objLang.urdu,objLang.urduBindings,objLang.urduActions);
		
	}
		
	public void setFindKeymap(String _lang)
	{
		if(_lang== "English")
		{			
			tfFindWhat.setKeymap(objLang.english);
			tfFindWhat.grabFocus();
			
		}
		if(_lang== "Urdu")
		{
			//JTextComponent.loadKeymap(objLang.urdu,objLang.urduBindings,objLang.urduActions);			
			
			
			tfFindWhat.setKeymap(objLang.urdu);
			tfFindWhat.grabFocus();
		}
		
	}
	
	public void setReplaceKeymap(String _lang)
	{
		if(_lang== "English")
		{			
			tfReplaceWhat.setKeymap(objLang.english);
			tfReplaceWith.setKeymap(objLang.english);
			tfReplaceWhat.grabFocus();
			
		}
		if(_lang== "Urdu")
		{
			//JTextComponent.loadKeymap(objLang.urdu,objLang.urduBindings,objLang.urduActions);			
			
			
			tfReplaceWhat.setKeymap(objLang.urdu);
			tfReplaceWith.setKeymap(objLang.urdu);
			tfReplaceWhat.grabFocus();
		}
		
	}
	
	/*
	public static void main(String a[])
	{
		FindDialog app=new FindDialog(null,null,null);
	}
	*/
}