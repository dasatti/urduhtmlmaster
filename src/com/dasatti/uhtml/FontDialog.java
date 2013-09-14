package com.dasatti.uhtml;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
import javax.swing.text.*;
import javax.swing.text.rtf.*;
import javax.swing.event.*;

public class FontDialog extends JDialog {

    protected boolean m_succeeded = false;
    protected OpenList m_lstFontName;
    protected OpenList m_lstFontSize;
    protected MutableAttributeSet m_attributes;
    protected JCheckBox m_chkBold;
    protected JCheckBox m_chkItalic;
    protected JCheckBox m_chkUnderline;
    protected JCheckBox m_chkStrikethrough;
    protected JCheckBox m_chkSubscript;
    protected JCheckBox m_chkSuperscript;
    protected JComboBox m_cbColor;
    protected JLabel m_preview;

    public FontDialog(JFrame parent) {
        super(parent, "Font", true);
        setModal(true);
        JPanel pp = new JPanel();
        pp.setBorder(new EmptyBorder(5, 5, 5, 5));
        pp.setLayout(new BoxLayout(pp, BoxLayout.Y_AXIS));
        JPanel p = new JPanel(new GridLayout(1, 2, 10, 2));
        p.setBorder(new TitledBorder(new EtchedBorder(), "Font"));
        GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
        String[] names = ge.getAvailableFontFamilyNames();
        String[] sizes = new String[]{"8", "9", "10", "11", "12", "14", "16", "18", "20", "22", "24", "26", "28", "36", "48", "72"};
        m_lstFontName = new OpenList(names, "Name:");
        p.add(m_lstFontName);
        m_lstFontSize = new OpenList(sizes, "Size:");
        p.add(m_lstFontSize);
        pp.add(p);
        p = new JPanel(new GridLayout(2, 3, 10, 5));
        p.setBorder(new TitledBorder(new EtchedBorder(), "Effects"));
        m_chkBold = new JCheckBox("Bold");
        p.add(m_chkBold);
        m_chkItalic = new JCheckBox("Italic");
        p.add(m_chkItalic);
        m_chkUnderline = new JCheckBox("Underline");
        p.add(m_chkUnderline);
        m_chkStrikethrough = new JCheckBox("Strikeout");
        p.add(m_chkStrikethrough);
        m_chkSubscript = new JCheckBox("Subscript");
        p.add(m_chkSubscript);
        m_chkSuperscript = new JCheckBox("Superscript");
        p.add(m_chkSuperscript);
        pp.add(p);
        pp.add(Box.createVerticalStrut(5));
        p = new JPanel();
        p.setLayout(new BoxLayout(p, BoxLayout.X_AXIS));
        p.add(Box.createHorizontalStrut(10));
        p.add(new JLabel("Color:"));
        p.add(Box.createHorizontalStrut(20));
        m_cbColor = new JComboBox();
        int[] values = new int[]{0, 128, 192, 255};
        for (int r = 0; r < values.length; r++) {
            for (int g = 0; g < values.length; g++) {
                for (int b = 0; b < values.length; b++) {
                    Color c = new Color(values[r], values[g], values[b]);
                    m_cbColor.addItem(c);
                }
            }
        }
        m_cbColor.setRenderer(new ColorComboRenderer());
        p.add(m_cbColor);
        p.add(Box.createHorizontalStrut(10));
        pp.add(p);
        ListSelectionListener lsel = new ListSelectionListener() {

            public void valueChanged(ListSelectionEvent e) {
                updatePreview();
            }
        };
        m_lstFontName.addListSelectionListener(lsel);
        m_lstFontSize.addListSelectionListener(lsel);

        ActionListener lst = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                updatePreview();
            }
        };
        m_chkBold.addActionListener(lst);
        m_chkItalic.addActionListener(lst);
        m_cbColor.addActionListener(lst);
        p = new JPanel(new BorderLayout());
        p.setBorder(new TitledBorder(new EtchedBorder(), "Preview"));
        m_preview = new JLabel("Preview Font", JLabel.CENTER);
        m_preview.setBackground(Color.white);
        m_preview.setForeground(Color.black);
        m_preview.setOpaque(true);
        m_preview.setBorder(new LineBorder(Color.black));
        m_preview.setPreferredSize(new Dimension(120, 40));
        p.add(m_preview, BorderLayout.CENTER);
        pp.add(p);
        p = new JPanel(new FlowLayout());
        JPanel p1 = new JPanel(new GridLayout(1, 2, 10, 0));
        JButton btOK = new JButton("OK");
        lst = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                m_succeeded = true;
                dispose();
            }
        };
        btOK.addActionListener(lst);
        p1.add(btOK);
        JButton btCancel = new JButton("Cancel");
        lst = new ActionListener() {

            public void actionPerformed(ActionEvent e) {
                dispose();
            }
        };
        btCancel.addActionListener(lst);
        p1.add(btCancel);
        p.add(p1);
        pp.add(p);
        getContentPane().add(pp, BorderLayout.CENTER);
        pack();
        setResizable(false);
        setLocationRelativeTo(parent);
    }

    public void setAttributes(AttributeSet a) {
        m_attributes = new SimpleAttributeSet(a);
        String name = StyleConstants.getFontFamily(a);
        m_lstFontName.setSelected(name);
        int size = StyleConstants.getFontSize(a);
        m_lstFontSize.setSelectedInt(size);
        m_chkBold.setSelected(StyleConstants.isBold(a));
        m_chkItalic.setSelected(StyleConstants.isItalic(a));
        m_chkUnderline.setSelected(StyleConstants.isUnderline(a));
        m_chkStrikethrough.setSelected(
                StyleConstants.isStrikeThrough(a));
        m_chkSubscript.setSelected(StyleConstants.isSubscript(a));
        m_chkSuperscript.setSelected(StyleConstants.isSuperscript(a));
        m_cbColor.setSelectedItem(StyleConstants.getForeground(a));
        updatePreview();
    }

    public AttributeSet getAttributes() {
        if (m_attributes == null) {
            return null;
        }
        StyleConstants.setFontFamily(m_attributes,
                m_lstFontName.getSelected());
        StyleConstants.setFontSize(m_attributes,
                m_lstFontSize.getSelectedInt());
        StyleConstants.setBold(m_attributes,
                m_chkBold.isSelected());
        StyleConstants.setItalic(m_attributes,
                m_chkItalic.isSelected());
        StyleConstants.setUnderline(m_attributes,
                m_chkUnderline.isSelected());
        StyleConstants.setStrikeThrough(m_attributes,
                m_chkStrikethrough.isSelected());
        StyleConstants.setSubscript(m_attributes,
                m_chkSubscript.isSelected());
        StyleConstants.setSuperscript(m_attributes,
                m_chkSuperscript.isSelected());
        StyleConstants.setForeground(m_attributes,
                (Color) m_cbColor.getSelectedItem());
        return m_attributes;
    }

    public boolean succeeded() {
        return m_succeeded;
    }

    protected void updatePreview() {
        String name = m_lstFontName.getSelected();
        int size = m_lstFontSize.getSelectedInt();
        if (size <= 0) {
            return;
        }
        int style = Font.PLAIN;
        if (m_chkBold.isSelected()) {
            style |= Font.BOLD;
        }
        if (m_chkItalic.isSelected()) {
            style |= Font.ITALIC;
        }
// Bug Alert! This doesn't work if only style is changed.
        Font fn = new Font(name, style, size);
        m_preview.setFont(fn);
        Color c = (Color) m_cbColor.getSelectedItem();
        m_preview.setForeground(c);
        m_preview.repaint();
    }
}

class OpenList extends JPanel
        implements ListSelectionListener, ActionListener {

    protected JLabel m_title;
    protected JTextField m_text;
    protected JList m_list;
    protected JScrollPane m_scroll;

    public OpenList(String[] data, String title) {
        setLayout(null);
        m_title = new JLabel(title, JLabel.LEFT);
        add(m_title);
        m_text = new JTextField();
        m_text.addActionListener(this);
        add(m_text);
        m_list = new JList(data);
        m_list.setVisibleRowCount(4);
        m_list.addListSelectionListener(this);
        m_list.setFont(m_text.getFont());
        m_scroll = new JScrollPane(m_list);
        add(m_scroll);
    }

    public void setSelected(String sel) {
        m_list.setSelectedValue(sel, true);
        m_text.setText(sel);
    }

    public String getSelected() {
        return m_text.getText();
    }

    public void setSelectedInt(int value) {
        setSelected(Integer.toString(value));
    }

    public int getSelectedInt() {
        try {
            return Integer.parseInt(getSelected());
        } catch (NumberFormatException ex) {
            return -1;
        }
    }

    public void valueChanged(ListSelectionEvent e) {
        Object obj = m_list.getSelectedValue();
        if (obj != null) {
            m_text.setText(obj.toString());
        }
    }

    public void actionPerformed(ActionEvent e) {
        ListModel model = m_list.getModel();
        String key = m_text.getText().toLowerCase();

        for (int k = 0; k < model.getSize(); k++) {
            String data = (String) model.getElementAt(k);
            if (data.toLowerCase().startsWith(key)) {
                m_list.setSelectedValue(data, true);
                break;
            }
        }
    }

    public void addListSelectionListener(ListSelectionListener lst) {
        m_list.addListSelectionListener(lst);
    }

    public Dimension getPreferredSize() {
        Insets ins = getInsets();
        Dimension d1 = m_title.getPreferredSize();
        Dimension d2 = m_text.getPreferredSize();
        Dimension d3 = m_scroll.getPreferredSize();
        int w = Math.max(Math.max(d1.width, d2.width), d3.width);
        int h = d1.height + d2.height + d3.height;
        return new Dimension(w + ins.left + ins.right,
                h + ins.top + ins.bottom);
    }

    public Dimension getMaximumSize() {
        Insets ins = getInsets();
        Dimension d1 = m_title.getMaximumSize();
        Dimension d2 = m_text.getMaximumSize();
        Dimension d3 = m_scroll.getMaximumSize();
        int w = Math.max(Math.max(d1.width, d2.width), d3.width);
        int h = d1.height + d2.height + d3.height;
        return new Dimension(w + ins.left + ins.right,
                h + ins.top + ins.bottom);
    }

    public Dimension getMinimumSize() {
        Insets ins = getInsets();
        Dimension d1 = m_title.getMinimumSize();
        Dimension d2 = m_text.getMinimumSize();
        Dimension d3 = m_scroll.getMinimumSize();
        int w = Math.max(Math.max(d1.width, d2.width), d3.width);
        int h = d1.height + d2.height + d3.height;
        return new Dimension(w + ins.left + ins.right,
                h + ins.top + ins.bottom);
    }

    public void doLayout() {
        Insets ins = getInsets();
        Dimension d = getSize();
        int x = ins.left;
        int y = ins.top;
        int w = d.width - ins.left - ins.right;
        int h = d.height - ins.top - ins.bottom;
        Dimension d1 = m_title.getPreferredSize();
        m_title.setBounds(x, y, w, d1.height);
        y += d1.height;
        Dimension d2 = m_text.getPreferredSize();
        m_text.setBounds(x, y, w, d2.height);
        y += d2.height;
        m_scroll.setBounds(x, y, w, h - y);
    }
}

class ColorComboRenderer extends JPanel implements ListCellRenderer {

    protected Color m_color = Color.black;
    protected Color m_focusColor =
            (Color) UIManager.get("List.selectionBackground");
    protected Color m_nonFocusColor = Color.white;

    public Component getListCellRendererComponent(JList list,
            Object obj, int row, boolean sel, boolean hasFocus) {
        if (hasFocus || sel) {
            setBorder(new CompoundBorder(
                    new MatteBorder(2, 10, 2, 10, m_focusColor),
                    new LineBorder(Color.black)));
        } else {
            setBorder(new CompoundBorder(
                    new MatteBorder(2, 10, 2, 10, m_nonFocusColor),
                    new LineBorder(Color.black)));
        }
        if (obj instanceof Color) {
            m_color = (Color) obj;
        }
        return this;
    }

    public void paintComponent(Graphics g) {
        setBackground(m_color);
        super.paintComponent(g);
    }
}
