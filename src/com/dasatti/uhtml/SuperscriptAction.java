package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class SuperscriptAction extends StyledEditorKit.StyledTextAction
{
			public SuperscriptAction()
			{
							super(StyleConstants.Superscript.toString());	
			}				
			public void actionPerformed(ActionEvent ae)
			{			
			JEditorPane editor = getEditor(ae);
						if (editor != null) 
						{				
						StyledEditorKit kit = getStyledEditorKit(editor);
						MutableAttributeSet attr = kit.getInputAttributes();
						boolean supscript = (StyleConstants.isSuperscript(attr)) ? false : true;				
						SimpleAttributeSet sas = new SimpleAttributeSet();				
						StyleConstants.setSuperscript(sas, supscript);				
						setCharacterAttributes(editor, sas, false);		
						editor.grabFocus();	
						}					
			}	
}