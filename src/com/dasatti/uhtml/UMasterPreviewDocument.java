package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;

public class UMasterPreviewDocument extends UMasterAbstractDocument
{
	JTextPane m_previewDocument;
	DefaultStyledDocument m_styledDoc;
	JScrollPane m_sPane;
	java.net.URL testURL;
	
	UMasterPreviewDocument()
	{
		setLayout(new BorderLayout());
		//m_previewStyledDocument=new DefaultStyledDocument();
		m_styledDoc=new DefaultStyledDocument();
		m_previewDocument=new JTextPane(m_styledDoc);
		m_previewDocument.setContentType("text/html");
		//_setPage("test.html");
		
		m_previewDocument.setEditable(false);
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
		
		m_previewDocument.addFocusListener(fl);
		*/
		
		m_previewDocument. addHyperlinkListener(
			new HyperlinkListener()
			{
				public void hyperlinkUpdate(HyperlinkEvent e)
				{
					if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) 
					{
						JEditorPane pane = (JEditorPane) e.getSource();
						if (e instanceof HTMLFrameHyperlinkEvent) 
						{
						HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
						HTMLDocument doc = (HTMLDocument)pane.getDocument();
						doc.processHTMLFrameHyperlinkEvent(evt);
						} 
						else 
						{
							try 
							{
								pane.setPage(e.getURL());
							} 
							catch (Throwable t) 
							{
								t.printStackTrace();
							}
						}
					}
				}
			}
			);
		
		
		m_sPane=new JScrollPane(m_previewDocument);
		add(m_sPane);
	}
	
	/***************************
	PUBLIC METHODS
	***************************/
	public void _setPage(String _path)
	{
		java.net.URL testURL = UMasterPreviewDocument.class.getResource(_path);
		try
		{
			m_previewDocument.setPage(testURL);
		}
		catch(Exception e)
		{
			JOptionPane.showMessageDialog(m_previewDocument,"File could not be opened", "File Open Error",JOptionPane.ERROR_MESSAGE);

		}
	}
	
	public void _updateCurrentDoc()
	{
		
	}
	
	public void _updateLocalDoc()
	{
		
	}
	
	
}

/*
class Hyperactive implements HyperlinkListener {
public void hyperlinkUpdate(HyperlinkEvent e) {
if (e.getEventType() == HyperlinkEvent.EventType.ACTIVATED) {
JEditorPane pane = (JEditorPane) e.getSource();
if (e instanceof HTMLFrameHyperlinkEvent) {
HTMLFrameHyperlinkEvent  evt = (HTMLFrameHyperlinkEvent)e;
HTMLDocument doc = (HTMLDocument)pane.getDocument();
doc.processHTMLFrameHyperlinkEvent(evt);
} else {
try {
pane.setPage(e.getURL());
} catch (Throwable t) {
t.printStackTrace();
}
}
}
}
}
*/