/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dasatti.uhtml;

import java.awt.BorderLayout;
import javax.swing.JLabel;
import javax.swing.JPanel;

/**
 *
 * @author Danish
 */
	public class UMasterStatusMenu extends JPanel
	{
		JLabel lblStatusLang;
		
		public UMasterStatusMenu()
		{
			lblStatusLang=new JLabel("English");
			
			setLayout(new BorderLayout());
			add(lblStatusLang,BorderLayout.CENTER);
		}
		
		public void setStatus(String _lang)
		{
			lblStatusLang.setText(_lang);
		}
	}
