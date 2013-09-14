package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.text.*;
import javax.swing.event.*;

public class StrikeThroughAction extends StyledEditorKit.StyledTextAction
{
			public StrikeThroughAction()
			{
							super(StyleConstants.StrikeThrough.toString());		
			}				
			public void actionPerformed(ActionEvent ae)
			{			
			JEditorPane editor = getEditor(ae);
						if (editor != null) 
						{				
						StyledEditorKit kit = getStyledEditorKit(editor);
						MutableAttributeSet attr = kit.getInputAttributes();
						boolean strikeThrough = (StyleConstants.isStrikeThrough(attr)) ? false : true;				
						SimpleAttributeSet sas = new SimpleAttributeSet();				
						StyleConstants.setStrikeThrough(sas, strikeThrough);				
						setCharacterAttributes(editor, sas, false);	
						editor.grabFocus();		
						}					
			}	
}