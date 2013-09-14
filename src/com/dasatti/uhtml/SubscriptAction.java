package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class SubscriptAction extends StyledEditorKit.StyledTextAction
{
			public SubscriptAction()
			{
							super(StyleConstants.Subscript.toString());	
			}				
			public void actionPerformed(ActionEvent ae)
			{			
			JEditorPane editor = getEditor(ae);
						if (editor != null) 
						{				
						StyledEditorKit kit = getStyledEditorKit(editor);
						MutableAttributeSet attr = kit.getInputAttributes();
						boolean subscript = (StyleConstants.isSubscript(attr)) ? false : true;				
						SimpleAttributeSet sas = new SimpleAttributeSet();				
						StyleConstants.setSubscript(sas, subscript);				
						setCharacterAttributes(editor, sas, false);	
						editor.grabFocus();		
						}					
			}	
}