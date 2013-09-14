package com.dasatti.uhtml;


public class UMasterAppletFrame extends UMasterWindow

{
	UMasterApplet m_uMasterApplet;
	
	UMasterAppletFrame()
	{
		m_uMasterApplet = new UMasterApplet();
		m_uMasterApplet.init();
		m_uMasterApplet.start();
		
		this.getContentPane().add(m_uMasterApplet);
		//validate();
		setVisible(true);		
	}
	
}