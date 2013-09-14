package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.text.html.*;
import javax.swing.event.*;

public class FontFamilyAction extends StyledEditorKit.StyledTextAction
{
	String fontName;
	JEditorPane editor;
			public FontFamilyAction(String _font)
			{
							super("setFontFamily");
							fontName=_font;	
			}
			public FontFamilyAction(String _font,JEditorPane _editor)
			{
							super("setFontFamily");
							fontName=_font;		
							editor=_editor;
			}				
			public void actionPerformed(ActionEvent ae)
			{			
						//editor = getEditor(ae);
						if (editor != null) 
						{
							int xStart=	editor.getSelectionStart();
							int xEnd= editor.getSelectionEnd();		
							MutableAttributeSet sas = new SimpleAttributeSet();				
							StyleConstants.setFontFamily(sas, fontName);
							HTMLDocument doc=(HTMLDocument)editor.getDocument();
							doc.setCharacterAttributes(xStart,xEnd-xStart,sas,false);
							editor.grabFocus();			
						}
						else
						System.out.println("here");	
			}
}