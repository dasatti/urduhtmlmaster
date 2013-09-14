package com.dasatti.uhtml;

import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class InsertTagAction extends HTMLEditorKit.InsertHTMLTextAction
{
	HTML.Tag addTag;
	HTML.Tag parentTag;
	String tagAttributes;
	boolean isSingleTag=false;
	
	public InsertTagAction(HTML.Tag _addTag)
	{
		super("InsertTagAction","",null,_addTag);
		addTag=_addTag;
		parentTag=null;
		tagAttributes=null;		
	}
	public InsertTagAction(HTML.Tag _addTag,boolean _isSingleTag)
	{
		super("InsertTagAction","",null,_addTag);
		addTag=_addTag;
		parentTag=null;
		tagAttributes=null;		
		isSingleTag=_isSingleTag;		
	}
	public InsertTagAction(HTML.Tag _addTag,String _tagAttributes,boolean _isSingleTag)
	{
		super("InsertTagAction","",null,_addTag);
		addTag=_addTag;
		parentTag=null;
		tagAttributes= _tagAttributes;		
		isSingleTag=_isSingleTag;		
	}
	public InsertTagAction(HTML.Tag _addTag,String _tagAttributes)
	{
		super("InsertTagAction","",null,_addTag);
		addTag=_addTag;
		parentTag=null;
		tagAttributes=_tagAttributes;		
	}
	public InsertTagAction(HTML.Tag _addTag,HTML.Tag _parentTag,String _tagAttributes)
	{
		super("InsertTagAction","",_parentTag,_addTag);
		addTag=_addTag;
		parentTag=_parentTag;
		tagAttributes=_tagAttributes;		
	}	
	public InsertTagAction(HTML.Tag _addTag,HTML.Tag _parentTag,String _tagAttributes, boolean _isSingleTag)
	{
		super("InsertTagAction","",_parentTag,_addTag);
		addTag=_addTag;
		parentTag=_parentTag;
		tagAttributes=_tagAttributes;		
		isSingleTag=_isSingleTag;
	}			
	public void actionPerformed(ActionEvent ae)
	{			
		JEditorPane editor = getEditor(ae);
		if (editor != null) 
		{				
					
			try
			{
						HTMLDocument htmlDoc = (HTMLDocument)(editor.getDocument());
						String selTextBase = editor.getSelectedText();
						int textLength = -1;
						if(selTextBase != null)
						{
							textLength = selTextBase.length();
						}

						if(selTextBase == null || textLength < 1)
						{
							StringBuffer sbNew = new StringBuffer();
							
							sbNew.append("<" + addTag + ">&nbsp;</" + addTag + ">");
							if(isSingleTag)
								sbNew.append("<" + addTag + ">");
							insertHTML(editor, htmlDoc, editor.getCaretPosition(), sbNew.toString(), 1, 1, HTML.Tag.UL);
						}
						else
						{
						int iStart = editor.getSelectionStart();
						int iEnd   = editor.getSelectionEnd();
						String selText = htmlDoc.getText(iStart, iEnd - iStart);
						StringBuffer sbNew = new StringBuffer();
						if(isSingleTag)
						{
							sbNew.append("<" + addTag + ">");
						}
						else
						{
						sbNew.append("<" + addTag + ">" + selText +"</"+ addTag + ">");
						htmlDoc.remove(iStart, iEnd - iStart);
						}
					 	insertHTML(editor,htmlDoc,iStart,sbNew.toString(),1,1,null);
					 	editor.select(iStart,iEnd);
					 	}
					 	//editor.setText(editor.getText());
						editor.grabFocus();
             }
             catch (Exception e)
             {
             } 
                      //catch(IOException ie){}	
		}					
	}	
}