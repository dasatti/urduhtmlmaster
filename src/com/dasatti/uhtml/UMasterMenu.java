package com.gui.menu;

import javax.swing.*;

public class UMasterMenu extends JMenuBar
{
	//JMenuBar=new JMenuBar();
	JMenu menu_file;
	JMenu menu_edit;
	JMenu menu_view;
	JMenu menu_insert;
	JMenu menu_format;
	JMenu menu_tools;
	JMenu menu_table;
	JMenu menu_help;
	public UMasterMenu()
	{
		menu_file=new JMenu("File");
		menu_edit=new JMenu("Edit");
		menu_view=new JMenu("View");
		menu_insert=new JMenu("Insert");
		menu_format=new JMenu("Formtat");
		menu_tools=new JMenu("Tools");
		menu_table=new JMenu("Table");
		menu_help=new JMenu("Help");
		
		this.add(menu_file);
		this.add(menu_edit);
		this.add(menu_view);
		this.add(menu_insert);
		this.add(menu_format);
		this.add(menu_tools);
		this.add(menu_table);
		this.add(menu_help);
	}
}
