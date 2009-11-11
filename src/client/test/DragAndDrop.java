package client.test;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import javax.swing.border.*;
public class DragAndDrop {
    public static void main(final String[] args) {
        final ButtonGroup grp = new ButtonGroup();
        final JPanel palette = new JPanel(new FlowLayout(FlowLayout.LEFT));
        palette.setBorder(BorderFactory.createTitledBorder("Palette"));
        final MainPanel mainPanel = new MainPanel();
        mainPanel.setBorder(BorderFactory.createTitledBorder("Main-Panel"));
        mainPanel.setPalette(palette);
        for(int j=0; j<3; j++){
            final JToggleButton btn = new JToggleButton("Panel "+(j+1));
            palette.add(btn);
            grp.add(btn);
            btn.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent e) {
                    mainPanel.setAdding(btn.getText());
                }
            });
        }
        palette.add(new JLabel("<html><font color=blue>Select a panel and click into Main-Panel <br>" +
                "to place a new subpanel (resizable,moveable)"));
        final JFrame f = new JFrame("Drag and drop panels");
        f.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        f.getContentPane().add(palette, BorderLayout.NORTH);
        f.getContentPane().add(mainPanel, BorderLayout.CENTER);
        f.setSize(800, 600);
        f.setVisible(true);
    }
}
class MainPanel extends JPanel implements MouseListener, MouseMotionListener {
    private JPanel palette;
    private String adding="";
    private SubPanel hitPanel;
    private int deltaX, deltaY, oldX, oldY;
    private final int TOL = 3;  //tolerance
    public MainPanel() {
        setLayout(null);
        addMouseListener(this);
        addMouseMotionListener(this);
    }
    public void mousePressed(final MouseEvent e) {
        if( adding != "" ){
            setCursor(new Cursor(Cursor.DEFAULT_CURSOR));
            SubPanel sub = new SubPanel(adding);
            add(sub);
            sub.setSize(sub.getPreferredSize());
            sub.setLocation((int)e.getX(),(int)e.getY());
            revalidate();
            adding = "";
            return;
        }
        Component c = getComponentAt(e.getPoint());
        if (c instanceof SubPanel) {
            hitPanel = (SubPanel) c;
            oldX = hitPanel.getX();
            oldY = hitPanel.getY();
            deltaX = e.getX() - oldX;
            deltaY = e.getY() - oldY;
            if( oldX < e.getX()-TOL ) oldX += hitPanel.getWidth();
            if( oldY < e.getY()-TOL ) oldY += hitPanel.getHeight();
        }
    }
    public void mouseDragged(final MouseEvent e) {
        if (hitPanel != null) {
            int x = e.getX();
            int y = e.getY();
            int xDiff = x-oldX;
            int yDiff = y-oldY;
            int xH = hitPanel.getX();
            int yH = hitPanel.getY();
            int w = hitPanel.getWidth();
            int h = hitPanel.getHeight();
            Dimension min = hitPanel.getMinimumSize();
            Dimension max = hitPanel.getMaximumSize();
            int wMin = (int)min.getWidth();
            int wMax = (int)max.getWidth();
            int hMin = (int)min.getHeight();
            int hMax = (int)max.getHeight();
            int cursorType = hitPanel.getCursor().getType();
            if( cursorType == Cursor.W_RESIZE_CURSOR){           //West resizing
                if( (w <= wMin && xDiff > 0) || (w >= wMax && xDiff < 0) ) return;
                hitPanel.setBounds( x, yH, w - xDiff, h );
            }else if( cursorType == Cursor.N_RESIZE_CURSOR){     //North resizing
                if( (h <= hMin && yDiff > 0) || (h >= hMax && yDiff < 0) ) return;
                hitPanel.setBounds( xH, y, w, h - yDiff );
            }else if( cursorType == Cursor.S_RESIZE_CURSOR){     //South resizing
                if( (h <= hMin && yDiff < 0) || (h >= hMax && yDiff > 0) ) return;
                hitPanel.setSize( w, h + yDiff );
            }else if( cursorType == Cursor.E_RESIZE_CURSOR){     //East resizing
                if( (w <= wMin && xDiff < 0) || (w >= wMax && xDiff > 0) ) return;
                hitPanel.setSize( w + xDiff, h );
            }else if( cursorType == Cursor.NW_RESIZE_CURSOR){     //NorthWest resizing
                if( (h <= hMin && yDiff > 0) || (h >= hMax && yDiff < 0) ) return;
                if( (w <= wMin && xDiff > 0) || (w >= wMax && xDiff < 0) ) return;
                hitPanel.setBounds( x, y, w - xDiff, h - yDiff );
            }else if( cursorType == Cursor.NE_RESIZE_CURSOR){     //NorthEast resizing
                if( (h <= hMin && yDiff > 0) || (h >= hMax && yDiff < 0) ) return;
                if( (w <= wMin && xDiff < 0) || (w >= wMax && xDiff > 0) ) return;
                hitPanel.setBounds( xH, y, w + xDiff, h - yDiff );
            }else if( cursorType == Cursor.SW_RESIZE_CURSOR){     //SouthWest resizing
                if( (h <= hMin && yDiff < 0) || (h >= hMax && yDiff > 0) ) return;
                if( (w <= wMin && xDiff > 0) || (w >= wMax && xDiff < 0) ) return;
                hitPanel.setBounds( x, yH, w - xDiff, h + yDiff );
            }else if( cursorType == Cursor.SE_RESIZE_CURSOR){     //SouthEast resizing
                if( (h <= hMin && yDiff < 0) || (h >= hMax && yDiff > 0) ) return;
                if( (w <= wMin && xDiff < 0) || (w >= wMax && xDiff > 0) ) return;
                hitPanel.setSize( w + xDiff, h + yDiff );
            }else{          //moving subpanel
                hitPanel.setLocation( x-deltaX, y-deltaY );
            }
            oldX = e.getX();
            oldY = e.getY();
        }
    }
    public void mouseMoved(final MouseEvent e) {
        Component c = getComponentAt(e.getPoint());
        if (c instanceof SubPanel) {
            int x  = e.getX();
            int y  = e.getY();
            int xC = c.getX();
            int yC = c.getY();
            int w  = c.getWidth();
            int h  = c.getHeight();
            if(       y >= yC-TOL   && y <= yC+TOL && x >= xC-TOL   && x <= xC+TOL  ){
                c.setCursor(new Cursor(Cursor.NW_RESIZE_CURSOR));
            }else if( y >= yC-TOL   && y <= yC+TOL && x >= xC-TOL+w && x <= xC+TOL+w ){
                c.setCursor(new Cursor(Cursor.NE_RESIZE_CURSOR));
            }else if( y >= yC-TOL+h && y <= yC+TOL+h && x >= xC-TOL   && x <= xC+TOL ){
                c.setCursor(new Cursor(Cursor.SW_RESIZE_CURSOR));
            }else if( y >= yC-TOL+h && y <= yC+TOL+h && x >= xC-TOL+w && x <= xC+TOL+w ){
                c.setCursor(new Cursor(Cursor.SE_RESIZE_CURSOR));
            }else if( x >= xC-TOL   && x <= xC+TOL ){
                c.setCursor(new Cursor(Cursor.W_RESIZE_CURSOR));
            }else if( y >= yC-TOL   && y <= yC+TOL ){
                c.setCursor(new Cursor(Cursor.N_RESIZE_CURSOR));
            }else if( x >= xC-TOL+w && x <= xC+TOL+w ){
                c.setCursor(new Cursor(Cursor.E_RESIZE_CURSOR));
            }else if( y >= yC-TOL+h && y <= yC+TOL+h ){
                c.setCursor(new Cursor(Cursor.S_RESIZE_CURSOR));
            }else{
                c.setCursor(new Cursor(Cursor.MOVE_CURSOR));
            }
        }
    }
    public void mouseReleased(final MouseEvent e) { hitPanel = null; }
    public void mouseClicked(final MouseEvent e) {}
    public void mouseEntered(final MouseEvent e) {}
    public void mouseExited(final MouseEvent e) {}
    public void setAdding(final String string) {
        adding = string;
        setCursor(new Cursor(Cursor.HAND_CURSOR));
    }
    public void setPalette(final JPanel panel) { palette = panel; }
}
class SubPanel extends JPanel {
    public SubPanel(final String name) {
        setPreferredSize(new Dimension(100, 100));
        setMinimumSize(new Dimension(70, 50));
        setMaximumSize(new Dimension(400, 300));
        setBorder(new TitledBorder(new LineBorder(Color.BLACK), name));
    }
}
