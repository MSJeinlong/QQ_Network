package test;

import javax.swing.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

/**
 * @program: Network_QQ
 * @Date: 2018-12-07 22:37
 * @Author: long
 * @Description:
 */
public class JButtonTest extends JFrame
{
    public static void main ( String[] args )
    {
        JButtonTest b = new JButtonTest ();
        b.addWindowListener (new WindowAdapter()
        {
            @Override
            public void windowClosing ( WindowEvent e )
            {
                int exi = JOptionPane.showConfirmDialog (null, "要退出该程序吗？", "友情提示", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (exi == JOptionPane.YES_OPTION)
                {
                    System.exit (0);
                }
                else
                {
                    return;
                }
            }
        });
        b.setSize (300, 300);
        b.setLocationRelativeTo (null);
        b.setDefaultCloseOperation (JFrame.DO_NOTHING_ON_CLOSE);
        b.setVisible (true);
    }


}
