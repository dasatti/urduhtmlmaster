package com.dasatti.uhtml;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class UMasterHTMLDocument extends UMasterAbstractDocument
{
	JTextPane m_htmlDocument;
	DefaultStyledDocument m_styledDoc;
	JScrollPane m_sPane;
	java.net.URL testURL;
	
	UMasterHTMLDocument()
	{
		setLayout(new BorderLayout());
		m_styledDoc=new DefaultStyledDocument();
		m_htmlDocument=new JTextPane();
		//_setPage("test.html");
		String src=m_htmlDocument.getText();
		m_htmlDocument.setContentType("text/plain");
		//m_htmlDocument.setText(src);
		/*
		FocusListener fl=new FocusListener()
		{
			public void focusGained(FocusEvent fe)
			{
				_updateLocalDoc();
			}
			public void focusLost(FocusEvent fe)
			{
				_updateCurrentDoc();
			}
		};
		
		m_htmlDocument.addFocusListener(fl);
		*/
		m_sPane=new JScrollPane(m_htmlDocument);
		add(m_sPane);
	}
	
	/***************************
	PUBLIC METHODS
	***************************/
	public void _setPage(String _path)
	{
		java.net.URL helpURL = UMasterDesignDocument.class.getResource(_path);
		try
		{
			m_htmlDocument.setPage(helpURL);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(m_htmlDocument,"File could not be opened", "File Open Error",JOptionPane.ERROR_MESSAGE);

		}
	}
	
	public void _updateCurrentDoc()
	{
		
	}
	
	public void _updateLocalDoc()
	{
		
	}
	
	
}
