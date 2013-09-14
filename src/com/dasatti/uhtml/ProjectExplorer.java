/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package com.dasatti.uhtml;

/**
 *
 * @author Danish
 */
import java.awt.*;
import java.awt.event.*;
import java.io.*;
import java.util.*;

import javax.swing.*;
import javax.swing.tree.*;
import javax.swing.event.*;


public class ProjectExplorer extends JPanel
{

    public static final ImageIcon ICON_COMPUTER = new ImageIcon("./res/images/icons/computer.gif");
    public static final ImageIcon ICON_DISK = new ImageIcon("./res/images/icons/disk.jpg");
    public static final ImageIcon ICON_FOLDER = new ImageIcon("./res/images/icons/folder.jpg");
    public static final ImageIcon ICON_EXPANDEDFOLDER = new ImageIcon("./res/images/icons/expandedfolder.jpg");
    public static final ImageIcon ICON_HTML = new ImageIcon("./res/images/icons/html.jpg");
    public static final ImageIcon ICON_FILE = new ImageIcon("./res/images/icons/file2.jpg");
    protected JTree m_tree;
    protected DefaultTreeModel m_model;
    protected JTextField m_display;

    protected TreePopup m_folderPopup;
    protected TreePopup m_filePopup;
    protected TreePath m_clickedPath;

    public ProjectExplorer() 
    {

//        UIManager.put("Tree.leafIcon", ICON_FILE);
//        UIManager.put("Tree.openIcon", null);
//        UIManager.put("Tree.closedIcon", ICON_FOLDER);
//        UIManager.put("Tree.extendedIcon", null);
//        UIManager.put("Tree.openIcon", ICON_EXPANDEDFOLDER);
        
        DefaultMutableTreeNode top = new DefaultMutableTreeNode( new IconData(ICON_COMPUTER, null, "Computer"));

        DefaultMutableTreeNode node;
        File[] roots = File.listRoots();
        for (int k = 0; k < roots.length; k++)
        {
            node = new DefaultMutableTreeNode(new IconData(ICON_DISK, null, new FileNode(roots[k])));
            top.add(node);
            node.add(new DefaultMutableTreeNode(new Boolean(true)));
        }

        m_model = new DefaultTreeModel(top);
        m_tree = new JTree(m_model);

        m_tree.putClientProperty("JTree.lineStyle", "Angled");

        TreeCellRenderer renderer = new IconCellRenderer();
        m_tree.setCellRenderer(renderer);

        m_tree.addTreeExpansionListener(new DirExpansionListener());
        m_tree.addTreeSelectionListener(new DirSelectionListener());
        m_tree.addMouseListener(new TreeMouseListener());

        m_tree.getSelectionModel().setSelectionMode(TreeSelectionModel.SINGLE_TREE_SELECTION);
        m_tree.setShowsRootHandles(true);
        m_tree.setEditable(false);

        JScrollPane s = new JScrollPane(m_tree);
//        s.getViewport().add(m_tree);
        setLayout(new BorderLayout());
        add(s,BorderLayout.CENTER);

        m_display = new JTextField();
        m_display.setEditable(false);
        add(m_display, BorderLayout.NORTH);

// NEW
        m_folderPopup = new TreePopup();
        m_filePopup = new TreePopup(true);
        m_tree.add(m_folderPopup);
        m_tree.add(m_filePopup);
        m_tree.addMouseListener(new PopupTrigger());


        
    }

    DefaultMutableTreeNode getTreeNode(TreePath path) {
        return (DefaultMutableTreeNode) (path.getLastPathComponent());
    }

    FileNode getFileNode(DefaultMutableTreeNode node) {
        if (node == null) {
            return null;
        }
        Object obj = node.getUserObject();
        if (obj instanceof IconData) {
            obj = ((IconData) obj).getObject();
        }
        if (obj instanceof FileNode) {
            return (FileNode) obj;
        } else {
            return null;
        }
    }


    boolean deleteFile(File f)
    {
        int reply = JOptionPane.showConfirmDialog(null, 
                "Are you sure you want to delete " +
                "this file?","Delete File",JOptionPane.YES_NO_OPTION);
        if(reply==JOptionPane.YES_OPTION)
        {
            try
            {
//                System.out.println("Deleting File : "+f.getName());
                f.delete();
                return true;
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, 
                        "Could not delete file","Delete File",
                        JOptionPane.ERROR_MESSAGE);
                return false;
            }
        }
        return false;
    }

    String renameFile(File f)
    {
        String newName = JOptionPane.showInputDialog(m_tree,"Rename",f.getName());
        if(newName!=null)
        {
            try
            {
                String parent = f.getParent();
                String newFullName = parent+File.separator+newName;
//                System.out.println("Renaimg File : "+f.getAbsolutePath()+" to "+newFullName);
                f.renameTo(new File(newFullName));
                return newName;
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Could not rename file",
                        "Rename File",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }

    File newFile(String path)
    {
        String fileName = JOptionPane.showInputDialog(m_tree,"FileName",
                "New File", JOptionPane.PLAIN_MESSAGE);
        if(fileName!=null)
        {
            try
            {
                String newFileName  = path+File.separator+fileName;
//                
                File newFile = new File(newFileName);
                if(!newFile.exists())
                    newFile.createNewFile();
                
                return newFile;
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Could not create file",
                        "New File",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
    
    File newFolder(String path)
    {
        String fileName = JOptionPane.showInputDialog(m_tree,"Folder Name",
                "New Folder", JOptionPane.PLAIN_MESSAGE);
        if(fileName!=null)
        {
            try
            {
                String newFileName  = path+File.separator+fileName;
                File newFile = new File(newFileName);
                if(!newFile.exists())
                    newFile.mkdirs();
                
                return newFile;
            }
            catch(Exception e)
            {
                JOptionPane.showMessageDialog(null, "Could not create file",
                        "New File",JOptionPane.ERROR_MESSAGE);
                return null;
            }
        }
        return null;
    }
    
    
    protected ImageIcon getIconForFile(File f)
    {
        if(f.isDirectory())
            return ProjectExplorer.ICON_FOLDER;
        
        if(f.isFile())
        {
            if(f.getName().endsWith("htm") || 
                    f.getName().endsWith("html"))
                return ProjectExplorer.ICON_HTML;
            else
                return ProjectExplorer.ICON_FILE;
        }
        return ProjectExplorer.ICON_DISK;
    }

    protected ImageIcon getExpandedIconForFile(File f)
    {
        if(f.isDirectory())
            return ProjectExplorer.ICON_EXPANDEDFOLDER;
        
        return null;
    }
// NEW

    class TreePopup extends JPopupMenu
    {
//        public Action m_action;
        
        private boolean isFilePopup = false;
        public Action m_action;
        
        public TreePopup()
        {
            this(false);
        }
        
        public TreePopup(boolean _isFilePopup)
        {
           isFilePopup = _isFilePopup;
           
           m_action = new AbstractAction() {

                public void actionPerformed(ActionEvent e) {
                    if (m_clickedPath == null) {
                        return;
                    }
                    if (m_tree.isExpanded(m_clickedPath)) {
                        m_tree.collapsePath(m_clickedPath);
                    } else {
                        m_tree.expandPath(m_clickedPath);
                    }
                }
            };
            m_action.putValue(Action.NAME, "Expand/Collapse");

            Action a1 = new AbstractAction("Delete")
            {
                public void actionPerformed(ActionEvent e)
                {
                   m_tree.repaint();
                   DefaultMutableTreeNode rClickedNode = getTreeNode(m_clickedPath);
                   FileNode rClickedFileNode = getFileNode(rClickedNode);
                   File rClickedFile = rClickedFileNode.getFile();
                   if(deleteFile(rClickedFile))
                    m_model.removeNodeFromParent(rClickedNode);
                }
            };

            Action a2 = new AbstractAction("Rename") {

                public void actionPerformed(ActionEvent e) {
                    m_tree.repaint();

                   DefaultMutableTreeNode rClickedNode = getTreeNode(m_clickedPath);
                   FileNode rClickedFileNode = getFileNode(rClickedNode);
                   File rClickedFile = rClickedFileNode.getFile();
                   String newName = renameFile(rClickedFile);
                   if(newName!=null)
                    m_model.valueForPathChanged(m_clickedPath, newName);

                }
            };

            Action aNewFolder = new AbstractAction("New Folder") {

                public void actionPerformed(ActionEvent e) {
                   m_tree.repaint();
                   DefaultMutableTreeNode rClickedNode = getTreeNode(m_clickedPath);
                   FileNode rClickedFileNode = getFileNode(rClickedNode);
                   File rClickedFile = rClickedFileNode.getFile();
                   String path = rClickedFile.getPath();
                   if(rClickedFile.isFile())
                       path = rClickedFile.getParent();
                   File newFolder = newFolder(path);
//                   rClickedNode.add(new DefaultMutableTreeNode(new IconData(ICON_FOLDER, null, new FileNode(rClickedFile))));
                   m_model.insertNodeInto(new DefaultMutableTreeNode(
                           new IconData(getIconForFile(newFolder), null, 
                           new FileNode(newFolder))), rClickedNode,rClickedNode.getChildCount());
                   
                }
            };
            Action aNewFile = new AbstractAction("New File") {

                public void actionPerformed(ActionEvent e) {
                   m_tree.repaint();
                   DefaultMutableTreeNode rClickedNode = getTreeNode(m_clickedPath);
                   FileNode rClickedFileNode = getFileNode(rClickedNode);
                   File rClickedFile = rClickedFileNode.getFile();
                   String path = rClickedFile.getPath();
                   if(rClickedFile.isFile())
                       path = rClickedFile.getParent();
                   File newFile = newFile(path);                 
                   m_model.insertNodeInto(new DefaultMutableTreeNode(
                       new IconData(getIconForFile(newFile), null, 
                       new FileNode(newFile))), rClickedNode,rClickedNode.getChildCount());

                }
            };

            JMenuItem m_miNew = new JMenu("New");
            JMenuItem m_miNewFolder = new JMenuItem(aNewFolder);
            JMenuItem m_miNewFile = new JMenuItem(aNewFile);
            m_miNew.add(m_miNewFolder);
            m_miNew.add(m_miNewFile);
            
            if(!isFilePopup)
            {
                add(m_action);
                add(m_miNew);
                addSeparator();
            }
            add(a1);
            add(a2);
        }
        
        public void show(int x, int y)
        {
            if(m_tree.isExpanded(m_clickedPath))
            {
                m_action.putValue(Action.NAME, "Collapse");
            }
            else
            {
                m_action.putValue(Action.NAME, "Expand");
            }
            super.show(m_tree, x, y);
        }
        
        public void setFilePopup(boolean f)
        {
            isFilePopup = f;
        }
    }

    class PopupTrigger extends MouseAdapter {

        public void mouseReleased(MouseEvent e) {
            if (e.isPopupTrigger()) {
                int x = e.getX();
                int y = e.getY();
                TreePath path = m_tree.getPathForLocation(x, y);
                if (path != null) {
                    m_clickedPath = path;
                    DefaultMutableTreeNode rClickedNode = getTreeNode(m_clickedPath);
                    FileNode rClickedFileNode = getFileNode(rClickedNode);
                    if(!rClickedFileNode.getFile().isDirectory())
                    {
                        m_filePopup.show(x, y);
                    }
                    else
                    {
                        m_folderPopup.show(x, y);
                    }
                }
            }
        }
    }

    // Make sure expansion is threaded and updating the tree model
    // only occurs within the event dispatching thread.
    class DirExpansionListener implements TreeExpansionListener {

        public void treeExpanded(TreeExpansionEvent event) {
            final DefaultMutableTreeNode node = getTreeNode(
                    event.getPath());
            final FileNode fnode = getFileNode(node);

            Thread runner = new Thread() {

                public void run() {
                    if (fnode != null && fnode.expand(node)) {
                        Runnable runnable = new Runnable() {

                            public void run() {
                                m_model.reload(node);
                            }
                        };
                        SwingUtilities.invokeLater(runnable);
                    }
                }
            };
            runner.start();
        }

        public void treeCollapsed(TreeExpansionEvent event) {
        }
    }

    class DirSelectionListener
            implements TreeSelectionListener {

        public void valueChanged(TreeSelectionEvent event) {
            DefaultMutableTreeNode node = getTreeNode(
                    event.getPath());
            FileNode fnode = getFileNode(node);
            if (fnode != null) {
                m_display.setText(fnode.getFile().getAbsolutePath());
//                if(fnode.getFile().isFile())
//                    UMasterUI.getMasterUI()._openDocument(fnode.getFile());
            } else {
                m_display.setText("");
            }
        }
    }

    
    class TreeMouseListener implements MouseListener
    {
        public void mousePressed(MouseEvent e) {
            int selRow = m_tree.getRowForLocation(e.getX(), e.getY());
            if (selRow != -1)
            {
                if (e.getClickCount() == 1) {
                } else if (e.getClickCount() == 2)
                {
                    TreePath selPath = m_tree.getPathForLocation(e.getX(), e.getY());
                    DefaultMutableTreeNode node = getTreeNode(selPath);
                    FileNode fnode = getFileNode(node);
                    if(fnode.getFile().isFile())
                        UMasterUI.getMasterUI()._openDocument(fnode.getFile());
                }
            }
        }

        public void mouseClicked(MouseEvent e) {
        }

        public void mouseReleased(MouseEvent e) {
        }

        public void mouseEntered(MouseEvent e) {
        }

        public void mouseExited(MouseEvent e) {
        }
    };



    public static void main(String argv[]) {
        JPanel pnl = new ProjectExplorer();
        JFrame frm = new JFrame("Project Explorer");
        frm.setSize(400, 300);
        frm.setLayout(new BorderLayout());
        frm.getContentPane().add(pnl, BorderLayout.CENTER);
        
        WindowListener wndCloser = new WindowAdapter() {

            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        };
        frm.addWindowListener(wndCloser);
        frm.setVisible(true);
    }
}
class IconCellRenderer
        extends JLabel
        implements TreeCellRenderer {

    protected Color m_textSelectionColor;
    protected Color m_textNonSelectionColor;
    protected Color m_bkSelectionColor;
    protected Color m_bkNonSelectionColor;
    protected Color m_borderSelectionColor;
    protected boolean m_selected;

    public IconCellRenderer() {
        super();
        m_textSelectionColor = UIManager.getColor(
                "Tree.selectionForeground");
        m_textNonSelectionColor = UIManager.getColor(
                "Tree.textForeground");
        m_bkSelectionColor = UIManager.getColor(
                "Tree.selectionBackground");
        m_bkNonSelectionColor = UIManager.getColor(
                "Tree.textBackground");
        m_borderSelectionColor = UIManager.getColor(
                "Tree.selectionBorderColor");
        setOpaque(false);
    }

    public Component getTreeCellRendererComponent(JTree tree,
            Object value, boolean sel, boolean expanded, boolean leaf,
            int row, boolean hasFocus) {
        DefaultMutableTreeNode node = (DefaultMutableTreeNode) value;
        Object obj = node.getUserObject();
        setText(obj.toString());

        if (obj instanceof Boolean) {
            setText("Retrieving data...");
        }

        if (obj instanceof IconData) {
            IconData idata = (IconData) obj;
            if (expanded) {
                setIcon(idata.getExpandedIcon());
            } else {
                setIcon(idata.getIcon());
            }
        } else {
            setIcon(null);
        }

        setFont(tree.getFont());
        setForeground(sel ? m_textSelectionColor : m_textNonSelectionColor);
        setBackground(sel ? m_bkSelectionColor : m_bkNonSelectionColor);
        m_selected = sel;
        return this;
    }

    public void paintComponent(Graphics g)
    {
        Color bColor = getBackground();
        Icon icon = getIcon();

        g.setColor(bColor);
        int offset = 0;
        if (icon != null && getText() != null) {
            offset = (icon.getIconWidth() + getIconTextGap());
        }
        g.fillRect(offset, 0, getWidth() - 1 - offset,
                getHeight() - 1);

        if (m_selected) {
            g.setColor(m_borderSelectionColor);
            g.drawRect(offset, 0, getWidth() - 1 - offset, getHeight() - 1);
        }

        super.paintComponent(g);
    }
}

class IconData 
{

    protected Icon m_icon;
    protected Icon m_expandedIcon;
    protected Object m_data;

    public IconData(Icon icon, Object data) {
        m_icon = icon;
        m_expandedIcon = null;
        m_data = data;
    }

    public IconData(Icon icon, Icon expandedIcon, Object data) {
        m_icon = icon;
        m_expandedIcon = expandedIcon;
        m_data = data;
    }

    public Icon getIcon() {
        return m_icon;
    }

    public Icon getExpandedIcon() {
        return m_expandedIcon != null ? m_expandedIcon : m_icon;
    }

    public Object getObject() {
        return m_data;
    }

    public String toString() {
        return m_data.toString();
    }
}

class FileNode {

    protected File m_file;

    public FileNode(File file) {
        m_file = file;
    }

    public File getFile() {
        return m_file;
    }

    public String toString() {
        return m_file.getName().length() > 0 ? m_file.getName() : m_file.getPath();
    }

    public boolean expand(DefaultMutableTreeNode parent)
    {
        DefaultMutableTreeNode flag =(DefaultMutableTreeNode) parent.getFirstChild();
        if (flag == null) // No flag
        {
            return false;
        }
        Object obj = flag.getUserObject();
        if (!(obj instanceof Boolean)) {
            return false;
        }      // Already expanded

        parent.removeAllChildren();  // Remove Flag

        File[] files = listFiles();
        if (files == null) {
            return true;
        }

        Vector v = new Vector();

        for (int k = 0; k < files.length; k++) {
            File f = files[k];
            if (!(f.isDirectory())) {              
//                if(!f.getName().endsWith("html"))
//                    continue;
            }

            FileNode newNode = new FileNode(f);

            boolean isAdded = false;
            for (int i = 0; i < v.size(); i++) {
                FileNode nd = (FileNode) v.elementAt(i);
                if (newNode.compareTo(nd) < 0) {
                    v.insertElementAt(newNode, i);
                    isAdded = true;
                    break;
                }
            }
            if (!isAdded) {
                v.addElement(newNode);
            }
        }

        for (int i = 0; i < v.size(); i++) 
        {
            FileNode nd = (FileNode) v.elementAt(i);
            IconData idata;
            if(nd.getFile().isDirectory())
            {
                idata = new IconData(ProjectExplorer.ICON_FOLDER,
                    ProjectExplorer.ICON_EXPANDEDFOLDER, nd);
            }
            else
            {
                if(nd.getFile().getName().endsWith(".html") || 
                        nd.getFile().getName().endsWith(".htm"))
                    idata = new IconData(ProjectExplorer.ICON_HTML,null, nd);
                else
                    idata = new IconData(ProjectExplorer.ICON_FILE,null, nd);
            }
            DefaultMutableTreeNode node = new DefaultMutableTreeNode(idata);
            parent.add(node);

            if (nd.hasSubDirs() || nd.hasFiles()) {
                node.add(new DefaultMutableTreeNode(
                        new Boolean(true)));
            }
        }

        return true;
    }

    public boolean hasSubDirs() {
        File[] files = listFiles();
        if (files == null) {
            return false;
        }
        for (int k = 0; k < files.length; k++) {
            if (files[k].isDirectory()) {
                return true;
            }
        }
        return false;
    }

    public int compareTo(FileNode toCompare) {
        return m_file.getName().compareToIgnoreCase(
                toCompare.m_file.getName());
    }

    protected File[] listFiles() {
        if (!m_file.isDirectory()) {
//            return null;
        }
        try {
            return m_file.listFiles();
        } catch (Exception ex) {
            JOptionPane.showMessageDialog(null,
                    "Error reading directory " + m_file.getAbsolutePath(),
                    "Warning", JOptionPane.WARNING_MESSAGE);
            return null;
        }
    }

    protected boolean hasFiles()
    {
        if(m_file.isDirectory())
            return true;
        File[] subFiles = m_file.listFiles();
        if(subFiles!=null && subFiles.length>0)
            return true;
        else
            return false;
    }
    
}
