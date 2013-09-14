package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.net.*;
import java.util.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;
import javax.swing.border.*;
import javax.swing.text.html.*;

public class DocumentPropsDialog extends JDialog 
{
	protected boolean m_succeeded = false;
	MutableAttributeSet m_attr=null;
	protected HTMLDocument m_doc;
	protected Color m_backgroundColor;
	protected Color m_textColor;
	protected Color m_linkColor;
	protected Color m_viewedColor;
	protected JTextField m_titleTxt;
	protected JTextPane m_previewPane;
	public DocumentPropsDialog(JFrame parent, HTMLDocument doc) 
	{
		super(parent, "Page Properties", true);
		m_doc = doc;
		Element body = getElementByTag(HTML.Tag.BODY);
		if (body != null) 
		{
			AttributeSet attr = body.getAttributes();
			StyleSheet syleSheet = m_doc.getStyleSheet();
			Object obj = attr.getAttribute(HTML.Attribute.BGCOLOR);
			if (obj != null)
				m_backgroundColor = syleSheet.stringToColor((String)obj);
				obj = attr.getAttribute(HTML.Attribute.TEXT);
			if (obj != null)
				m_textColor = syleSheet.stringToColor((String)obj);
				obj = attr.getAttribute(HTML.Attribute.LINK);
			if (obj != null)
				m_linkColor = syleSheet.stringToColor((String)obj);
				obj = attr.getAttribute(HTML.Attribute.VLINK);
			if (obj != null)
				m_viewedColor = syleSheet.stringToColor((String)obj);
		}
		ActionListener lst;
		JButton bt;
		JPanel pp = new JPanel();
		pp.setBorder(new EmptyBorder(10, 10, 5, 10));
		pp.add(new JLabel("Page title:"));
		m_titleTxt = new JTextField(getTitle(), 24);
		pp.add(m_titleTxt);
		JPanel pa = new JPanel(new BorderLayout(5, 5));
		Border ba = new TitledBorder(new EtchedBorder(EtchedBorder.RAISED), "Appearance");
		pa.setBorder(new CompoundBorder(ba, new EmptyBorder(0, 5, 5, 5)));
		JPanel pb = new JPanel(new GridLayout(4, 1, 5, 5));
		bt = new JButton("Background");
		bt.setMnemonic('b');
		lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_backgroundColor =JColorChooser.showDialog(DocumentPropsDialog.this,"Document Background", m_backgroundColor);
				showColors();
			}
		};
		
		
		bt.addActionListener(lst);
		pb.add(bt);
		bt = new JButton("Text");
		bt.setMnemonic('t');
		lst = new ActionListener() 
		{
				public void actionPerformed(ActionEvent e) 
				{
					m_textColor = JColorChooser.showDialog(DocumentPropsDialog.this,"Text Color", m_textColor);
					showColors();
				}
		};
		
		bt.addActionListener(lst);
		pb.add(bt);
		bt = new JButton("Link");
		bt.setMnemonic('l');
		lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_linkColor = JColorChooser.showDialog(DocumentPropsDialog.this,"Links Color", m_linkColor);
				showColors();
			}
		};
		bt.addActionListener(lst);
		pb.add(bt);
		bt = new JButton("Viewed");
		bt.setMnemonic('v');
		lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				m_viewedColor = JColorChooser.showDialog(DocumentPropsDialog.this,"Viewed Links Color", m_viewedColor);
				showColors();
			}

		};
		bt.addActionListener(lst);
		pb.add(bt);
		pa.add(pb, BorderLayout.WEST);
		m_previewPane = new JTextPane();
		m_previewPane.setBackground(Color.white);
		m_previewPane.setEditable(false);
		m_previewPane.setBorder(new CompoundBorder(new BevelBorder(BevelBorder.LOWERED),new EmptyBorder(10, 10, 10, 10)));
		showColors();
		pa.add(m_previewPane, BorderLayout.CENTER);
		pp.add(pa);
		bt = new JButton("Save");
		lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				saveData();
			dispose();
			}
		};
		bt.addActionListener(lst);
		pp.add(bt);
		bt = new JButton("Cancel");
		lst = new ActionListener() 
		{
			public void actionPerformed(ActionEvent e) 
			{
				dispose();
			}
		};
		bt.addActionListener(lst);
		pp.add(bt);
		getContentPane().add(pp, BorderLayout.CENTER);
		pack();
		setResizable(false);
		setLocationRelativeTo(parent);
		}
		public boolean succeeded() 
		{
			return m_succeeded;
		}
		public MutableAttributeSet getAttributes()
		{
			return m_attr;
		}
		protected void saveData() 
		{
			setTitle(m_titleTxt.getText());
			Element body = getElementByTag(HTML.Tag.BODY);
			MutableAttributeSet attr = new SimpleAttributeSet();
			if (m_backgroundColor != null)
				attr.addAttribute(HTML.Attribute.BGCOLOR,colorToHex(m_backgroundColor));
			if (m_textColor != null)
				attr.addAttribute(HTML.Attribute.TEXT,colorToHex(m_textColor));
			if (m_linkColor != null)
				attr.addAttribute(HTML.Attribute.LINK,colorToHex(m_linkColor));
			if (m_viewedColor != null)
				attr.addAttribute(HTML.Attribute.VLINK,colorToHex(m_viewedColor));
			
			//MutableAttributeSet mattr=new SimpleAttributeSet();
			if(body!=null)
			{
				try
				{
					//attr.addAttributes(body.getAttributes());
					//mattr =(MutableAttributeSet)body.getAttributes();
					//mattr.addAttributes(attr);
				}
				finally
				{
				}
			}
			//MutableAttributeSet mattr =m_doc.getAttributes();
			m_succeeded = true;
			m_attr=attr;
		}
		protected void showColors() 
		{
			DefaultStyledDocument doc = new DefaultStyledDocument();
			SimpleAttributeSet attr = new SimpleAttributeSet();
			StyleConstants.setFontFamily(attr, "Arial");
			StyleConstants.setFontSize(attr, 14);
			if (m_backgroundColor != null) 
			{
				StyleConstants.setBackground(attr, m_backgroundColor);
				m_previewPane.setBackground(m_backgroundColor);
			}
			try 
			{
				StyleConstants.setForeground(attr, m_textColor!=null ?	m_textColor : Color.black);
				doc.insertString(doc.getLength(),"Plain text preview\n\n", attr);
				StyleConstants.setForeground(attr, m_linkColor!=null ?m_linkColor : Color.blue);
				StyleConstants.setUnderline(attr, true);
				doc.insertString(doc.getLength(), "Link preview\n\n", attr);
				StyleConstants.setForeground(attr, m_viewedColor!=null ? m_viewedColor : Color.magenta);
				StyleConstants.setUnderline(attr, true);
				doc.insertString(doc.getLength(), "Viewed link preview\n", attr);
			}
			catch (BadLocationException be) 
			{
				be.printStackTrace();
			}
			m_previewPane.setDocument(doc);
		}
		
		public static String colorToHex(Color color) 
		{
			String colorstr = new String("#");
			// Red
			String str = Integer.toHexString(color.getRed());
			if (str.length() > 2)
			str = str.substring(0, 2);
			else if (str.length() < 2)
			colorstr += "0" + str;
			else
			colorstr += str;
			// Green
			str = Integer.toHexString(color.getGreen());
			if (str.length() > 2)
			str = str.substring(0, 2);
			else if (str.length() < 2)
			colorstr += "0" + str;
			else
			colorstr += str;
			// Blue
			str = Integer.toHexString(color.getBlue());
			if (str.length() > 2)
			str = str.substring(0, 2);
			else if (str.length() < 2)
			colorstr += "0" + str;
			else
			colorstr += str;
			return colorstr;
		}
		
	public Element getElementByTag(HTML.Tag tag) 
	{
		Element root = m_doc.getDefaultRootElement();
		return getElementByTag(root, tag);
	}
	
	public Element getElementByTag(Element parent, HTML.Tag tag) 
	{
		if (parent == null || tag == null)
		return null;
		for (int k=0; k<parent.getElementCount(); k++) 
		{
			Element child = parent.getElement(k);
			if (child.getAttributes().getAttribute(StyleConstants.NameAttribute).equals(tag))
			return child;
			Element e = getElementByTag(child, tag);
			if (e != null)
			return e;
		}
		return null;
	}
	
	public String getTitle() 
	{
		return (String)m_doc.getProperty(Document.TitleProperty);
	}
	public void setTitle(String title) 
	{
		Dictionary di = m_doc.getDocumentProperties();
		di.put(Document.TitleProperty, title);
		m_doc.setDocumentProperties(di);
	}
		
}