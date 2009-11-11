package client.test;
/*
* Created on Dec 10, 2005
*/

import java.awt.Color;

import javax.swing.JButton;
import javax.swing.JDesktopPane;
import javax.swing.JTextPane;

import com.jme.app.BaseGame;
import com.jme.app.SimpleGame;
import com.jme.renderer.Renderer;
import com.jme.scene.Node;
import com.jme.scene.state.LightState;
import com.jmex.awt.swingui.JMEDesktop;

/**
* @author Matthew D. Hicks
*/
public class SwingGameTest extends SimpleGame {
    private JMEDesktop desktop;
    private Node desktopNode;

    protected void simpleInitGame() {
        desktop = new JMEDesktop("Test Frame");
        desktop.setup(display.getWidth(), display.getHeight(), false, input);
        desktop.setLightCombineMode(LightState.OFF);
       
        desktopNode = new Node("Desktop Node");
        desktopNode.attachChild(desktop);
        rootNode.attachChild(desktopNode);
       
        desktopNode.getLocalTranslation().set(display.getWidth() / 2, display.getHeight() / 2, 0);
        desktopNode.setRenderQueueMode(Renderer.QUEUE_ORTHO);
       
        JButton button3 = new JButton("Test Button");
        button3.setLocation(300, 100);
        button3.setSize(button3.getPreferredSize());
       
        JDesktopPane pane = desktop.getJDesktop();
        pane.add(button3);
        pane.setBackground(new Color(0.5f, 0.5f, 1.0f, 0.0f));
       
        JTextPane text = new JTextPane();
        text.setSize(100, display.getHeight() - 25);
        text.setText("Testing\r\nTesting Again\r\nTesting even more text hopefully wrapping to the next line.");
        //text.setOpaque(false);
        text.setBackground(new Color(1.0f, 1.0f, 1.0f, 0.2f));
        text.setEditable(false);
        text.setForeground(Color.WHITE);
        text.setLocation(0, 0);
        pane.add(text);
       
        pane.repaint();
        pane.revalidate();
       
        input.setEnabled(true);
    }
   
    protected void simpleUpdate() {
    }

    public static void main(String[] args) {
        SwingGameTest sgt = new SwingGameTest();
        sgt.setDialogBehaviour(BaseGame.ALWAYS_SHOW_PROPS_DIALOG);
        sgt.start();
    }
}