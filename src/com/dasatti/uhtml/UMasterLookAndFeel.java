package com.dasatti.uhtml;

import java.awt.*;
import javax.swing.*;


public class UMasterLookAndFeel 
{

	
	
	/*****************
	   CONSTRUCTORS
	******************/
	UMasterLookAndFeel ()
	{
		try 
		{
        	UIManager.setLookAndFeel( new com.nilo.plaf.nimrod.NimRODLookAndFeel()); 
    	} 
    	catch (Exception evt) {}
	}
	
	

}