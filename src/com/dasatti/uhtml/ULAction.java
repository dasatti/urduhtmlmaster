package com.dasatti.uhtml;

import java.util.StringTokenizer;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.event.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class ULAction extends HTMLEditorKit.InsertHTMLTextAction
{
			public ULAction()
			{
				super("ULAction","",HTML.Tag.UL,HTML.Tag.LI);		
			}				
			public void actionPerformed(ActionEvent ae)
			{			
			JEditorPane editor = getEditor(ae);
				if (editor != null) 
				{				
						/*StyledEditorKit kit = getStyledEditorKit(editor);
						MutableAttributeSet attr = kit.getInputAttributes();
						boolean strikeThrough = (StyleConstants.isStrikeThrough(attr)) ? false : true;				
						SimpleAttributeSet sas = new SimpleAttributeSet();				
						StyleConstants.setStrikeThrough(sas, strikeThrough);				
						setCharacterAttributes(editor, sas, false);	*/
					//StyledEditorKit kit = getStyledEditorKit(editor);
					//MutableAttributeSet attr = kit.getInputAttributes();
					//HTML.Tag bullet=HTML.Tag.LI;
					//attr.addAttribute(StyleConstants.NameAttribute,bullet);
					//setParagraphAttributes(editor,attr,false);	
					//editor.setText(editor.getText());
					//editor.grabFocus();
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
							
							sbNew.append("<li></li>");
							insertHTML(editor, htmlDoc, editor.getCaretPosition(), sbNew.toString(), 0, 0, HTML.Tag.UL);
						}
						else
						{
						int iStart = editor.getSelectionStart();
						int iEnd   = editor.getSelectionEnd();
						String selText = htmlDoc.getText(iStart, iEnd - iStart);
						StringBuffer sbNew = new StringBuffer();
						String sToken = ((selText.indexOf("\r") > -1) ? "\r" : "\n");
						StringTokenizer stTokenizer = new StringTokenizer(selText, sToken);
						sbNew.append("<UL>");
						while(stTokenizer.hasMoreTokens())
						{
							sbNew.append("<LI>");
							sbNew.append(stTokenizer.nextToken());
							sbNew.append("</LI>");
						}
						sbNew.append("</UL>");

						htmlDoc.remove(iStart, iEnd - iStart);
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