package com.dasatti.uhtml;

import java.awt.*;
import javax.swing.*;


public abstract class UMasterWindow extends JFrame
{
	String m_window_title;
        String version = "2.0";
	int m_window_width, m_window_height;
	
	Toolkit m_tKit=getToolkit();
	
	//UMasterMenu menu;
	
	
	/*****************
	   CONSTRUCTORS
	******************/
	UMasterWindow()
	{
		_setTitle("Urdu HTML Master V"+version);
		int _w=m_tKit.getScreenSize().width;
		int _h=m_tKit.getScreenSize().height-30;
		_setWidthHeight(_w, _h);
		displayWindow();
	}
	
	UMasterWindow(String _t)
	{
		_setTitle(_t);
		int _w=m_tKit.getScreenSize().width;
		int _h=m_tKit.getScreenSize().height-30;
		_setWidthHeight(_w, _h);
		displayWindow();
	}
	
	UMasterWindow(int _x, int _y)
	{
		_setTitle("Urdu HTML Master");
		_setWidthHeight(_x, _y);
		displayWindow();
	}
	
	UMasterWindow(String _t,int _x, int _y)
	{
		_setTitle(_t);
		_setWidthHeight(_x, _y);
		displayWindow();
	}
	
	/*****************
	      SETERS
	******************/
	public void _setTitle(String _t)
	{
		m_window_title=_t;
	}
	
	public void _setWidthHeight(int _w, int _h)
	{
		m_window_width=_w;
		m_window_height=_h;
	}
	
	
	/*****************
	   OTHER METHODS
	******************/
	public void displayWindow()
	{
		try 
		{
        	//UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
        	//UIManager.setLookAndFeel(new com.nilo.plat.nimrod.NimRODLookAndFeel());
    	} 
    	catch (Exception evt) {}
		setTitle(m_window_title);
		setSize(m_window_width,m_window_height);
		//menu=new UMasterMenu();
		//setJMenuBar(menu);
		displaySplash();
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}
	
	public void setWindowsLookAndFeel()
	{
		try 
		{
        	UIManager.setLookAndFeel("com.sun.java.swing.plaf.windows.WindowsLookAndFeel");
    	} 
    	catch (Exception evt) {}
	}
	
	public void setDefaultLookAndFeel()
	{
		new UMasterLookAndFeel();
	}
	
	public void displaySplash()
	{
		new UMasterLookAndFeel();
		UMasterSplashScreen splash=new UMasterSplashScreen("./res/images/splash/sscreen.gif",this);
	}

}