package com.dasatti.uhtml;

import java.awt.*;
//import java.applet.*;
import javax.swing.*;


/*

<APPLET code=MasterApplet width=1000 height=900>

</APPLET>

*/

public class UMasterApplet extends JApplet
{
	String m_title;
	int m_width;
	int m_height;
	UMasterUI m_MasterUI;
	
	public void init()
	{
		drawUI();
	}
	
	public void start()
	{
	}
	
	public void stop()
	{
	}
	
	public void destroy()
	{
	}
	
	//public void paint(Graphics g)
	//{
	//}
	
	public void drawUI()
	{
		m_MasterUI=new UMasterUI();
		getContentPane().add(m_MasterUI);
	}
}