package com.dasatti.uhtml;


import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

class UMasterSplashScreen extends JWindow
{
	public UMasterSplashScreen(String fileName,Frame frame)
	{
		super(frame);
		JLabel lbl=new JLabel(new ImageIcon(fileName));
		getContentPane().add(lbl,BorderLayout.CENTER);
		pack();
		Dimension screenSize=Toolkit.getDefaultToolkit().getScreenSize();
		Dimension lblSize=lbl.getPreferredSize();
		setLocation(screenSize.width/2-(lblSize.width/2),screenSize.height/2-(lblSize.height/2));
		addMouseListener(
			new MouseAdapter()
			{
				public void mousePressed(MouseEvent e)
				{
					setVisible(false);
					dispose();
				}
			}
			);
		final Runnable closerRunner=new Runnable()
		{
			public void run()
			{
				setVisible(false);
				dispose();
			}
		};
		Runnable waitRunner=new Runnable()
		{
			public void run()
			{
				try
				{
					Thread.sleep(5000);
					SwingUtilities.invokeAndWait(closerRunner);
				}
				catch(Exception e)
				{}
			}
		};
		setVisible(true);
		Thread splashThread=new Thread(waitRunner,"SplashThread");
		splashThread.start();
	}
}