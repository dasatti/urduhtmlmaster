/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */

package com.dasatti.uhtml;

import java.awt.BorderLayout;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import javax.swing.ImageIcon;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTree;
import javax.swing.UIManager;
import javax.swing.tree.DefaultMutableTreeNode;
import javax.swing.tree.TreePath;

/**
 *
 * @author Danish
 */
public class UMasterDocumentsPanel extends JPanel
{
        JTree treeProject;
        DefaultMutableTreeNode tnodeProject;
        public DefaultMutableTreeNode tnodeHTML;
        public DefaultMutableTreeNode tnodeCSS;
        ArrayList alNodes,alIds;

        public UMasterDocumentsPanel()
        {
                UIManager.put("Tree.leafIcon", new ImageIcon("./res/images/icons/html.jpg"));
                tnodeProject=new DefaultMutableTreeNode("Urdu HTML Master");
                tnodeHTML=new DefaultMutableTreeNode("HTML Documents");
                tnodeCSS=new DefaultMutableTreeNode("CSS Documents");

                tnodeProject.add(tnodeHTML);
                //tnodeProject.add(tnodeCSS);

                treeProject=new JTree(tnodeProject);
                treeProject.addMouseListener(
                        new MouseListener()
                        {
                                public void mouseClicked(MouseEvent e)
                                {
                                        int x=treeProject.getRowForLocation(e.getX(),e.getY());
                                        //System.out.println(x);
                                }
                                public void mouseEntered(MouseEvent e)
                                {
                                }
                                public void mouseExited(MouseEvent e)
                                {
                                }
                                public void mouseReleased(MouseEvent e)
                                {
                                }
                                public void mousePressed(MouseEvent e)
                                {
                                }
                        }
                        );
                JScrollPane jsp=new JScrollPane(treeProject);

                alNodes=new ArrayList();
                alIds=new ArrayList();

                setLayout(new BorderLayout());
                add(jsp,BorderLayout.CENTER);
        }

        public void addHTMLFile(String _name,int _unique)
        {
                DefaultMutableTreeNode tempNode=new DefaultMutableTreeNode(_name);
                tnodeHTML.add(tempNode);
                alNodes.add(tempNode);
                alIds.add(_unique);
                treeProject.expandPath(new TreePath(tnodeHTML));
                treeProject.scrollRowToVisible(treeProject.getRowForPath(new TreePath(tempNode)));
                treeProject.makeVisible(new TreePath(tempNode));
                treeProject.updateUI();
        }

        public void removeHTMLFile(int _unique)
        {
                int index=alIds.indexOf(_unique);
                if(index>=0)
                {
                        tnodeHTML.remove((DefaultMutableTreeNode)alNodes.get(index));
                        alNodes.remove(index);
                        alIds.remove(index);
                        treeProject.updateUI();
                }
        }

}