package com.dasatti.uhtml;

//import com.gui.menu.UMasterMenu;
import java.awt.event.*;
import javax.swing.event.*;
import javax.swing.*;

public class UMasterFrame extends UMasterWindow
{
	//UMasterMenu menu;
	UMasterUI m_MasterUI;
	
	
	UMasterFrame()
	{
		drawUI();
	}
	
	public void drawUI()
	{
		//menu=new UMasterMenu();
		//setJMenuBar(menu);
		m_MasterUI=new UMasterUI();
		m_MasterUI._setParentFrame(this);
		setJMenuBar(m_MasterUI._getMenuBar());
		getContentPane().add(m_MasterUI);
		setVisible(true);
		
		WindowListener wndListener=new WindowAdapter()
		{
			public void windowClosing(WindowEvent e)
			{
				if(!m_MasterUI._closeAllDocuments())
				{
					setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
					//return;
				}
				else
				{
					setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
				}
			}
			public void windowActivated(WindowEvent e)
			{
				m_MasterUI.m_internalFrame_document.requestFocus();
			}
		};
		addWindowListener(wndListener);
	}
	
	/*********************************
	INNER CLASSES
	*********************************/
	

	
}