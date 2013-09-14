/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dasatti.uhtml;

import java.awt.BorderLayout;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTabbedPane;

/**
 *
 * @author Danish
 */
public class UMasterProjectPanel extends JPanel
{
    ProjectExplorer projectExplorer;
    UMasterDocumentsPanel documentPanel;
    JTabbedPane tPane;
    

    public UMasterProjectPanel()
    {
        tPane = new JTabbedPane();
        projectExplorer = new ProjectExplorer();
        documentPanel = new UMasterDocumentsPanel();

        tPane.addTab("Documents Explorer", documentPanel);
        tPane.addTab("Project Explorer", projectExplorer);

        JScrollPane jsp = new JScrollPane(tPane);
        setLayout(new BorderLayout());
        add(jsp,BorderLayout.CENTER);
    }

    public void addHTMLFile(String _name,int _unique)
    {
        documentPanel.addHTMLFile(_name, _unique);
    }

    public void removeHTMLFile(int _unique)
    {
        documentPanel.removeHTMLFile(_unique);
    }

}