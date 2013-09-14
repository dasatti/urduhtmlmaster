package com.lang;

import java.awt.*;
import javax.swing.*;
import javax.swing.text.*;
import java.awt.event.*;
import javax.swing.event.*;

public class LangControler
{
	public static Keymap english,urdu;

		static class ChangeKeymapAction extends TextAction 
		{
       
        	public ChangeKeymapAction( String keymapName ) 
        	{
            	super("change-keymap-" + keymapName);
            	this.keymapName = keymapName;
        	}

        	public void actionPerformed(ActionEvent e) 
        	{
            	JTextComponent target = getTextComponent(e);
            	if (target != null) {
               		Keymap map = target.getKeymap( keymapName );
                if( map != null )
                    target.setKeymap( map );
            }
        }
        
        private String keymapName;
}


	   public static class InsertMeAction extends TextAction 
	   {

       		public InsertMeAction( String s ) 
       		{
            	super("insert-me " + s);
            	this.s = s;
        	}

        	public void actionPerformed(ActionEvent e) 
        	{
            	JTextComponent target = getTextComponent(e);
            	if (target != null) 
            	{
                	target.replaceSelection(s);
            	}
        	}
        
        	private String s;
    	}


        public static final JTextComponent.KeyBinding[] urduBindings =
    	{
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('a'),
                                      "insert-me ا"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('A'),
                                      "insert-me آ"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('b'),
                                      "insert-me \u0628"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('B'),
                                      "insert-me \u0613"),//\u0634
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('c'),
                                      "insert-me \u0686"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('C'),
                                      "insert-me \u062b"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('d'),
                                      "insert-me \u062F"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('D'),
                                      "insert-me \u0688"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('e'),
                                      "insert-me \u0639"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('E'),
                                      "insert-me \u0611"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('f'),
                                      "insert-me \u0641"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('F'),
                                      "insert-me \u0627"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('g'),
                                      "insert-me \u06AF"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('G'),
                                      "insert-me \u063A"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('h'),
                                      "insert-me \u06BE"), //\u0647
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('H'),
                                      "insert-me \u062D"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('i'),
                                      "insert-me \u06CC"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('I'),
                                      "insert-me \u064A"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('j'),
                                      "insert-me \u062C"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('J'),
                                      "insert-me \u0636"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('k'),
                                      "insert-me \u06A9"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('K'),
                                      "insert-me \u062E"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('l'),
                                      "insert-me \u0644"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('L'),
                                      "insert-me \u0612"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('m'),
                                      "insert-me \u0645"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('M'),
                                      "insert-me \u0627"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('n'),
                                      "insert-me \u0646"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('N'),
                                      "insert-me \u06BA"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('o'),
                                      "insert-me \u0647"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('O'),
                                      "insert-me \u0629"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('p'),
                                      "insert-me \u067E"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('P'),
                                      "insert-me \u064F"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('q'),
                                      "insert-me \u0642"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('Q'),
                                      "insert-me \u0648"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('r'),
                                      "insert-me \u0631"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('R'),
                                      "insert-me \u0691"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('s'),
                                      "insert-me \u0633"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('S'),
                                      "insert-me \u0635"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('t'),
                                      "insert-me \u062A"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('T'),
                                      "insert-me \u0679"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('u'),
                                      "insert-me \u0626"), //\u0621
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('U'),
                                      "insert-me \u0647"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('v'),
                                      "insert-me \u0637"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('V'),
                                      "insert-me \u0638"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('w'),
                                      "insert-me \u0648"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('W'),
                                      "insert-me \u0610"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('x'),
                                      "insert-me \u0634"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('X'),
                                      "insert-me \u0698"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('y'),
                                      "insert-me \u06D2"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('Y'),
                                      "insert-me \u0600"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('z'),
                                      "insert-me \u0632"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('Z'),
                                      "insert-me \u0630"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke(';'),
                                      "insert-me \u061B"),
		new JTextComponent.KeyBinding(KeyStroke.getKeyStroke(','),
                                      "insert-me \u060C"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('?'),
                                      "insert-me \u061F"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('.'),
                                      "insert-me \u06D4"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('{'),
                                      "insert-me {"),
        new JTextComponent.KeyBinding(KeyStroke.getKeyStroke('}'),
                                      "insert-me }")
    };

    public static final Action[] urduActions = {
        new InsertMeAction("ا"),
        new InsertMeAction("آ"),
        new InsertMeAction("\u0628"),
        new InsertMeAction("\u0613"), //0634
        new InsertMeAction("\u0686"),
        new InsertMeAction("\u062B"),
        new InsertMeAction("\u062F"),
        new InsertMeAction("\u0688"),
        new InsertMeAction("\u0639"),
        new InsertMeAction("\u0611"),
        new InsertMeAction("\u0641"),
        new InsertMeAction("\u0627"),
        new InsertMeAction("\u06AF"),
        new InsertMeAction("\u063A"),
        new InsertMeAction("\u06BE"), //0647
        new InsertMeAction("\u062D"),
        new InsertMeAction("\u06CC"),
        new InsertMeAction("\u064A"),
        new InsertMeAction("\u062C"),
        new InsertMeAction("\u0636"),
        new InsertMeAction("\u06A9"),
        new InsertMeAction("\u062E"),
        new InsertMeAction("\u0644"),
        new InsertMeAction("\u0612"),
        new InsertMeAction("\u0645"),
        new InsertMeAction("\u0627"),
        new InsertMeAction("\u0646"),
        new InsertMeAction("\u06BA"),
        new InsertMeAction("\u0647"),
        new InsertMeAction("\u0629"),
        new InsertMeAction("\u067E"),
        new InsertMeAction("\u064F"),
        new InsertMeAction("\u0642"),
        new InsertMeAction("\u0648"),
        new InsertMeAction("\u0631"),
        new InsertMeAction("\u0691"),
        new InsertMeAction("\u0633"),
        new InsertMeAction("\u0635"),
        new InsertMeAction("\u062A"),
        new InsertMeAction("\u0679"),
        new InsertMeAction("\u0626"),//621
        new InsertMeAction("\u0647"),
        new InsertMeAction("\u0637"),
        new InsertMeAction("\u0638"),
        new InsertMeAction("\u0648"),
        new InsertMeAction("\u0610"),
        new InsertMeAction("\u0634"),
        new InsertMeAction("\u0698"),
        new InsertMeAction("\u06D2"),
        new InsertMeAction("\u0600"),
        new InsertMeAction("\u0632"),
        new InsertMeAction("\u0630"),
        new InsertMeAction("\u061B"),
        new InsertMeAction("\u060C"),
        new InsertMeAction("\u061F"),
        new InsertMeAction("\u06D4"),
        new InsertMeAction("{"),
        new InsertMeAction("}")
    };
}