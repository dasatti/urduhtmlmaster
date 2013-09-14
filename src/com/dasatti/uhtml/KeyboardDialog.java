package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.text.html.*;
import javax.swing.event.*;
import java.util.*;

public class KeyboardDialog extends JDialog implements KeyListener
{
	
	JButton btnA,btnB,btnC,btnD,btnE,btnF,btnG,btnH,btnI,btnJ,btnK,btnL,btnM,btnN,btnO,btnP,btnQ,btnR,btnS,btnT,btnU,btnV,btnW,btnX,btnY,btnZ;
	JButton btnTab,btnRBrace,btnLBrace,btnFSlash,btnColon,btnComma1,btnEnter,btnShift,btnComma2,btnFullStop,btnSlash,btnShift2;
	JButton btnLQuote,btn1,btn2,btn3,btn4,btn5,btn6,btn7,btn8,btn9,btn0,btnUnderscore,btnEqual,btnBack;
	JToggleButton btnCaps;
	JComboBox cbLangs;

	private final int LANG_EN=0;
	private final int LANG_UR=1;
	private int curLang=0;
	private String[] strLangs={"English","Urdu"}; 
	boolean isCaps=false;
	boolean isShift=false;

	public KeyboardDialog(JFrame parent)
	{
		super(parent,"Urdu HTML Master Keyboard",true);
		//super();
		setLayout(new BorderLayout());
		
		
		JPanel pnlControl=new JPanel();
		JPanel pnlKeys=new JPanel();
		pnlControl.setLayout(new FlowLayout(FlowLayout.LEFT,10,15));
		pnlKeys.setLayout(null);
		
		JLabel lblSelectLang=new JLabel("Select Language: ");
		cbLangs=new JComboBox(strLangs);
		
		cbLangs.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					curLang=cbLangs.getSelectedIndex();
					updateKeyboard();
				}
			}
			);
		
		pnlControl.add(lblSelectLang);
		lblSelectLang.setBounds(10,20,300,45);
		pnlControl.add(cbLangs);
		cbLangs.setBounds(400,20,300,45);
		
		add(pnlControl,BorderLayout.NORTH);
		
		btnA=new JButton("a");
		btnB=new JButton("b");
		btnC=new JButton("c");
		btnD=new JButton("d");
		btnE=new JButton("e");
		btnF=new JButton("f");
		btnG=new JButton("g");
		btnH=new JButton("h");
		btnI=new JButton("i");
		btnJ=new JButton("j");
		btnK=new JButton("k");
		btnL=new JButton("l");
		btnM=new JButton("m");
		btnN=new JButton("n");
		btnO=new JButton("o");
		btnP=new JButton("p");
		btnQ=new JButton("q");
		btnR=new JButton("r");
		btnS=new JButton("s");
		btnT=new JButton("t");
		btnU=new JButton("u");
		btnV=new JButton("v");
		btnW=new JButton("w");
		btnX=new JButton("x");
		btnY=new JButton("y");
		btnZ=new JButton("z");
		
		btnTab=new JButton("Tab");
		btnLBrace=new JButton("[");
		btnRBrace=new JButton("]");
		btnFSlash=new JButton("\\");
		btnCaps=new JToggleButton("Caps");
		btnShift=new JButton("Shift");
		btnColon=new JButton(";");
		btnComma1=new JButton("'");
		btnEnter=new JButton("Enter");
		btnComma2=new JButton(",");
		btnFullStop=new JButton(".");
		btnSlash=new JButton("/");
		btnShift2=new JButton("Shift");
		
		btnLQuote=new JButton("`");
		btn1=new JButton("1");
		btn2=new JButton("2");
		btn3=new JButton("3");
		btn4=new JButton("4");
		btn5=new JButton("5");
		btn6=new JButton("6");
		btn7=new JButton("7");
		btn8=new JButton("8");
		btn9=new JButton("9");
		btn0=new JButton("0");
		btnUnderscore=new JButton("-");
		btnEqual=new JButton("=");
		btnBack=new JButton("Back");
		
		
		btnCaps.addActionListener(
			new ActionListener()
			{
				public void actionPerformed(ActionEvent e)
				{
					if(btnCaps.isSelected())
						isCaps=true;
					else
						isCaps=false;
						
					updateKeyboard();
				}
			}
			);
			
		btnShift.addMouseListener(
			new MouseListener()
			{
				public void mouseClicked(MouseEvent e)
				{
				}
				
				public void mouseEntered(MouseEvent e)
				{
				}
				
				public void mouseExited(MouseEvent e)
				{
				}
				
				public void mousePressed(MouseEvent e)
				{
					/*if(isCaps)
					{
						isCaps=false;
					}
					else
						isCaps=true;
					*/	
					isShift=true;
					updateKeyboard();
				}
				
				public void mouseReleased(MouseEvent r)
				{
					/*if(isCaps)
					{
						isCaps=false;
					}
					else
						isCaps=true;
					*/
					isShift=false;	
					updateKeyboard();
				}
			}
			);
		btnShift2.addMouseListener(
			new MouseListener()
			{
				public void mouseClicked(MouseEvent e)
				{
				}
				
				public void mouseEntered(MouseEvent e)
				{
				}
				
				public void mouseExited(MouseEvent e)
				{
				}
				
				public void mousePressed(MouseEvent e)
				{
					/*if(isCaps)
					{
						isCaps=false;
					}
					else
						isCaps=true;
					*/	
					isShift=true;
					updateKeyboard();
				}
				
				public void mouseReleased(MouseEvent r)
				{
					/*if(isCaps)
					{
						isCaps=false;
					}
					else
						isCaps=true;
					*/	
					isShift=false;
					updateKeyboard();
				}
			}
			);
			
		pnlKeys.add(btnLQuote);
		btnLQuote.setBounds(10,15,45,40);
		pnlKeys.add(btn1);
		btn1.setBounds(60,15,45,40);
		pnlKeys.add(btn2);
		btn2.setBounds(110,15,45,40);
		pnlKeys.add(btn3);
		btn3.setBounds(160,15,45,40);
		pnlKeys.add(btn4);
		btn4.setBounds(210,15,45,40);
		pnlKeys.add(btn5);
		btn5.setBounds(260,15,45,40);
		pnlKeys.add(btn6);
		btn6.setBounds(310,15,45,40);
		pnlKeys.add(btn7);
		btn7.setBounds(360,15,45,40);
		pnlKeys.add(btn8);
		btn8.setBounds(410,15,45,40);
		pnlKeys.add(btn9);
		btn9.setBounds(460,15,45,40);
		pnlKeys.add(btn0);
		btn0.setBounds(510,15,45,40);
		pnlKeys.add(btnUnderscore);
		btnUnderscore.setBounds(560,15,45,40);
		pnlKeys.add(btnEqual);
		btnEqual.setBounds(610,15,45,40);
		pnlKeys.add(btnBack);
		btnBack.setBounds(660,15,65,40);
		
		
		pnlKeys.add(btnTab);
		btnTab.setBounds(10,60,65,40);
		pnlKeys.add(btnQ);
		btnQ.setBounds(80,60,45,40);
		pnlKeys.add(btnW);
		btnW.setBounds(130,60,45,40);
		pnlKeys.add(btnE);
		btnE.setBounds(180,60,45,40);
		pnlKeys.add(btnR);
		btnR.setBounds(230,60,45,40);
		pnlKeys.add(btnT);
		btnT.setBounds(280,60,45,40);
		pnlKeys.add(btnY);
		btnY.setBounds(330,60,45,40);
		pnlKeys.add(btnU);
		btnU.setBounds(380,60,45,40);
		pnlKeys.add(btnI);
		btnI.setBounds(430,60,45,40);
		pnlKeys.add(btnO);
		btnO.setBounds(480,60,45,40);
		pnlKeys.add(btnP);
		btnP.setBounds(530,60,45,40);
		pnlKeys.add(btnLBrace);
		btnLBrace.setBounds(580,60,45,40);
		pnlKeys.add(btnRBrace);
		btnRBrace.setBounds(630,60,45,40);
		pnlKeys.add(btnFSlash);
		btnFSlash.setBounds(680,60,45,40);
		pnlKeys.add(btnCaps);
		btnCaps.setBounds(10,105,75,40);
		pnlKeys.add(btnA);
		btnA.setBounds(90,105,45,40);
		pnlKeys.add(btnS);
		btnS.setBounds(140,105,45,40);
		pnlKeys.add(btnD);
		btnD.setBounds(190,105,45,40);
		pnlKeys.add(btnF);
		btnF.setBounds(240,105,45,40);
		pnlKeys.add(btnG);
		btnG.setBounds(290,105,45,40);
		pnlKeys.add(btnH);
		btnH.setBounds(340,105,45,40);
		pnlKeys.add(btnJ);
		btnJ.setBounds(390,105,45,40);
		pnlKeys.add(btnK);
		btnK.setBounds(440,105,45,40);
		pnlKeys.add(btnL);
		btnL.setBounds(490,105,45,40);
		pnlKeys.add(btnColon);
		btnColon.setBounds(540,105,45,40);
		pnlKeys.add(btnComma1);
		btnComma1.setBounds(590,105,45,40);
		pnlKeys.add(btnEnter);
		btnEnter.setBounds(640,105,85,40);
		pnlKeys.add(btnShift);
		btnShift.setBounds(10,150,100,40);
		pnlKeys.add(btnZ);
		btnZ.setBounds(115,150,45,40);
		pnlKeys.add(btnX);
		btnX.setBounds(165,150,45,40);
		pnlKeys.add(btnC);
		btnC.setBounds(215,150,45,40);
		pnlKeys.add(btnV);
		btnV.setBounds(265,150,45,40);
		pnlKeys.add(btnB);
		btnB.setBounds(315,150,45,40);
		pnlKeys.add(btnN);
		btnN.setBounds(365,150,45,40);
		pnlKeys.add(btnM);
		btnM.setBounds(415,150,45,40);
		pnlKeys.add(btnComma2);
		btnComma2.setBounds(465,150,45,40);
		pnlKeys.add(btnFullStop);
		btnFullStop.setBounds(515,150,45,40);
		pnlKeys.add(btnSlash);
		btnSlash.setBounds(565,150,45,40);
		pnlKeys.add(btnShift2);
		btnShift2.setBounds(615,150,110,40);
		
		add(pnlKeys,BorderLayout.CENTER);
		
		addKeyListener(this);
		pack();
		setBounds(350,350,750,300);
		setResizable(false);
		//setVisible(true);
	}
	
	public void updateKeyboard()
	{
		
		if(curLang==LANG_EN)
		{
			if(isCaps)
			{
				if(!isShift)
				{
					capitalizeEN();
					nshifticizeEN();
				}
				else
				{
					ncapitalizeEN();
					shifticizeEN();
				}
			}
			else
			{
				if(isShift)
				{
					capitalizeEN();
					shifticizeEN();
				}
				else
				{
					ncapitalizeEN();
					nshifticizeEN();
				}
			}
		}
		if(curLang==LANG_UR)
		{
			if(isCaps)
			{
				if(!isShift)
				{
					capitalizeUR();
					nshifticizeUR();
				}
				else
				{
					ncapitalizeUR();
					shifticizeUR();
				}
			}
			else
			{
				if(isShift)
				{
					capitalizeUR();
					shifticizeUR();
				}
				else
				{
					ncapitalizeUR();
					nshifticizeUR();
				}
			}
		}
	}
	
	public void capitalizeEN()
	{
		btnA.setText("A");
		btnB.setText("B");
		btnC.setText("C");
		btnD.setText("D");
		btnE.setText("E");
		btnF.setText("F");
		btnG.setText("G");
		btnH.setText("H");
		btnI.setText("I");
		btnJ.setText("J");
		btnK.setText("K");
		btnL.setText("L");
		btnM.setText("M");
		btnN.setText("N");
		btnO.setText("O");
		btnP.setText("P");
		btnQ.setText("Q");
		btnR.setText("R");
		btnS.setText("S");
		btnT.setText("T");
		btnU.setText("U");
		btnV.setText("V");
		btnW.setText("W");
		btnX.setText("X");
		btnY.setText("Y");
		btnZ.setText("Z");
		btnColon.setText(";");
		btnComma2.setText(",");
		btnFullStop.setText(".");
		btnSlash.setText("/");
	}
	
	public void ncapitalizeEN()
	{
		btnA.setText("a");
		btnB.setText("b");
		btnC.setText("c");
		btnD.setText("d");
		btnE.setText("e");
		btnF.setText("f");
		btnG.setText("g");
		btnH.setText("h");
		btnI.setText("i");
		btnJ.setText("j");
		btnK.setText("k");
		btnL.setText("l");
		btnM.setText("m");
		btnN.setText("n");
		btnO.setText("o");
		btnP.setText("p");
		btnQ.setText("q");
		btnR.setText("r");
		btnS.setText("s");
		btnT.setText("t");
		btnU.setText("u");
		btnV.setText("v");
		btnW.setText("w");
		btnX.setText("x");
		btnY.setText("y");
		btnZ.setText("z");
		btnColon.setText(";");
		btnComma2.setText(",");
		btnFullStop.setText(".");
		btnSlash.setText("/");
	}
	
	public void ncapitalizeUR()
	{
		btnA.setText("\u0627");
		btnB.setText("\u0628");
		btnC.setText("\u0686");
		btnD.setText("\u062F");
		btnE.setText("\u0639");
		btnF.setText("\u0641");
		btnG.setText("\u06AF");
		btnH.setText("\u06BE");
		btnI.setText("\u06CC");
		btnJ.setText("\u062C");
		btnK.setText("\u06A9");
		btnL.setText("\u0644");
		btnM.setText("\u0645");
		btnN.setText("\u0646");
		btnO.setText("\u0647");
		btnP.setText("\u067E");
		btnQ.setText("\u0642");
		btnR.setText("\u0631");
		btnS.setText("\u0633");
		btnT.setText("\u062A");
		btnU.setText("\u0621");
		btnV.setText("\u0637");
		btnW.setText("\u0648");
		btnX.setText("\u0634");
		btnY.setText("\u06D2");
		btnZ.setText("\u0632");
		btnColon.setText("\u061B");
		btnComma2.setText("\u060C");
		btnFullStop.setText("\u06D4");
		btnSlash.setText("\u061F");
		
	}
	
	public void capitalizeUR()
	{
		btnA.setText("\u0622");
		btnB.setText("\u0613");
		btnC.setText("\u062b");
		btnD.setText("\u0688");
		btnE.setText("\u0611");
		btnF.setText("\u0656");
		btnG.setText("\u063A");
		btnH.setText("\u062D");
		btnI.setText("\u064A");
		btnJ.setText("\u0636");
		btnK.setText("\u062E");
		btnL.setText("\u0612");
		btnM.setText("\u0627");
		btnN.setText("\u06BA");
		btnO.setText("\u0629");
		btnP.setText("\u064F");
		btnQ.setText("\u0648");
		btnR.setText("\u0691");
		btnS.setText("\u0635");
		btnT.setText("\u0679");
		btnU.setText("\u0626");
		btnV.setText("\u0638");
		btnW.setText("\u0610");
		btnX.setText("\u0698");
		btnY.setText("\u0600");
		btnZ.setText("\u0630");
		btnColon.setText("\u061B");
		btnComma2.setText("\u060C");
		btnFullStop.setText("\u06D4");
		btnSlash.setText("\u061F");
	}
	
	public void shifticizeEN()
	{
		btnLQuote.setText("~");
		btn1.setText("!");
		btn2.setText("@");
		btn3.setText("#");
		btn4.setText("$");
		btn5.setText("%");
		btn6.setText("^");
		btn7.setText("&");
		btn8.setText("*");
		btn9.setText("(");
		btn0.setText(")");
		btnUnderscore.setText("_");
		btnEqual.setText("+");
		btnLBrace.setText("{");
		btnRBrace.setText("}");
		btnFSlash.setText("\\");
		btnColon.setText(":");
		btnComma1.setText("\"");
		btnComma2.setText("<");
		btnFullStop.setText(">");
		btnSlash.setText("?");
	}
	
	public void nshifticizeEN()
	{
		btnLQuote.setText("`");
		btn1.setText("1");
		btn2.setText("2");
		btn3.setText("3");
		btn4.setText("4");
		btn5.setText("5");
		btn6.setText("6");
		btn7.setText("7");
		btn8.setText("8");
		btn9.setText("9");
		btn0.setText("0");
		btnUnderscore.setText("-");
		btnEqual.setText("=");
		btnLBrace.setText("[");
		btnRBrace.setText("]");
		btnFSlash.setText("|");
		btnColon.setText(";");
		btnComma1.setText("'");
		btnComma2.setText(",");
		btnFullStop.setText(".");
		btnSlash.setText("/");
	}
	
	public void shifticizeUR()
	{
		btnLQuote.setText("~");
		btn1.setText("!");
		btn2.setText("@");
		btn3.setText("#");
		btn4.setText("$");
		btn5.setText("%");
		btn6.setText("^");
		btn7.setText("&");
		btn8.setText("*");
		btn9.setText("(");
		btn0.setText(")");
		btnUnderscore.setText("_");
		btnEqual.setText("+");
		btnLBrace.setText("{");
		btnRBrace.setText("}");
		btnFSlash.setText("\\");
		btnColon.setText(":");
		btnComma1.setText("\"");
		btnComma2.setText("<");
		btnFullStop.setText(">");
		btnSlash.setText("\u061F");
	}
	
	public void nshifticizeUR()
	{
		btnLQuote.setText("`");
		btn1.setText("1");
		btn2.setText("2");
		btn3.setText("3");
		btn4.setText("4");
		btn5.setText("5");
		btn6.setText("6");
		btn7.setText("7");
		btn8.setText("8");
		btn9.setText("9");
		btn0.setText("0");
		btnUnderscore.setText("-");
		btnEqual.setText("=");
		btnLBrace.setText("[");
		btnRBrace.setText("]");
		btnFSlash.setText("|");
		btnColon.setText("\u061B");
		btnComma1.setText("'");
		btnComma2.setText("\u060C");
		btnFullStop.setText("\u06D4");
		btnSlash.setText("/");
	}
	
	
	public void keyPressed(KeyEvent e)
	{
		System.out.println("Action");
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
		{
			isShift=true;
			updateKeyboard();
		}
	}
	public void keyReleased(KeyEvent e)
	{
		if(e.getKeyCode()==KeyEvent.VK_SHIFT)
		{
			isShift=false;
			updateKeyboard();
		}
	}
	public void keyTyped(KeyEvent e)
	{
	}
	
	///*
	public static void main(String a[])
	{
		KeyboardDialog app=new KeyboardDialog(null);
		app.show();
	}
	//*/
}