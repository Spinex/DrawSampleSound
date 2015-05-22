import java.awt.Color;
import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.Image;
import java.awt.Toolkit;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;


public class ToolBoxGUI extends JFrame {
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	JButton przyciskPencil, przyciskLine, przyciskCzysc;
	
	public ToolBoxGUI() {
		
		super("Narzêdzia");
		ImageIcon buttonIcon;
						
		Image icon = Toolkit.getDefaultToolkit().getImage("media/music.png");
		setIconImage(icon);
		
		setVisible(true);
	//	setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setFocusable(true);
		setLocation(100, 300);
		getContentPane().setBackground(new Color(27,27,27));
		
		setResizable(false);
		setSize(40, 120);
		
		setLayout(new GridLayout(3, 1));
		
		buttonIcon = new ImageIcon("media/pencil_medium.png");
		przyciskPencil = new JButton(buttonIcon);
		przyciskPencil.setBackground(new Color(27, 27, 27));
		przyciskPencil.setForeground(new Color(157, 157, 157));
		przyciskPencil.setPreferredSize(new Dimension(30,30));
		przyciskPencil.setBorderPainted(false);
		add(przyciskPencil);
		
		buttonIcon = new ImageIcon("media/line_normal_begin.png");
		przyciskLine = new JButton(buttonIcon);
		przyciskLine.setBackground(new Color(27, 27, 27));
		przyciskLine.setForeground(new Color(157, 157, 157));
		przyciskLine.setPreferredSize(new Dimension(30,30));
		przyciskLine.setBorderPainted(false);
		add(przyciskLine);
		
		buttonIcon = new ImageIcon("media/edit_clear.png");
		przyciskCzysc = new JButton(buttonIcon);
		przyciskCzysc.setBackground(new Color(27, 27, 27));
		przyciskCzysc.setForeground(new Color(157, 157, 157));
		przyciskCzysc.setPreferredSize(new Dimension(30,30));
		przyciskCzysc.setBorderPainted(false);
		add(przyciskCzysc);
		
		add(przyciskPencil);
		add(przyciskLine);
		add(przyciskCzysc);
	}

}
