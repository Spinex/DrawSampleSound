import java.awt.Color;
import java.awt.Dimension;
import java.awt.FlowLayout;
import java.awt.Image;
import java.awt.Scrollbar;
import java.awt.Toolkit;
import java.awt.event.AdjustmentEvent;
import java.awt.event.AdjustmentListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.io.File;
import java.util.ArrayList;

import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.Clip;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;



public class MainWindowGUI extends JFrame {

	//ArrayList<Integer> soundSignal = new ArrayList<Integer>();
	WavEncoder enkoder;
	
	JFrame windowRefference;
	
	Scrollbar pasekPrzesuwu;
	
	SoundGraph wykresDzwieku;
	JButton przyciskOdtworz;
	JLabel _DEBUG_probkiIlosc, _DEBUG_probkiGestosc;
	JTextField poleZoom;
	
	int samplesCount = 44100;
	int zoomStrength = 1;
	
	JButton przyciskPencil, przyciskLine, przyciskCzysc, przyciskPowieksz, przyciskPomniejsz, przyciskZaznacz,
	przyciskFalaPetla;
	
	private static final long serialVersionUID = 1L;
	
	public MainWindowGUI() {
		super("SoundDesigner - By Spinex");
		ImageIcon buttonIcon;
		
		windowRefference = this;
				
		Image icon = Toolkit.getDefaultToolkit().getImage("media/music.png");
		setIconImage(icon);
		
		setVisible(true);
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
	//	setLocation(200, 200);
		getContentPane().setBackground(new Color(27,27,27));
		
		setResizable(false);
	//	setSize(800, 350);
		
		Dimension screenSize = Toolkit.getDefaultToolkit().getScreenSize();
		double width = screenSize.getWidth();
		double height = screenSize.getHeight();
		
		setSize(Toolkit.getDefaultToolkit().getScreenSize());
		
		int x = (int) width;
	//	int y = 200;
		int y = (int) (height) - 150;
		
		int step = 10;
		int density = 5;
		
		
		enkoder = new WavEncoder();
		
		poleZoom = new JTextField("1");
		poleZoom.setBackground(new Color(27, 27, 27));
		poleZoom.setForeground(new Color(157, 157, 157));
		poleZoom.setPreferredSize(new Dimension(100,20));
		
		buttonIcon = new ImageIcon("media/selection_select.png");
		przyciskZaznacz = new JButton(buttonIcon);
		przyciskZaznacz.setBackground(new Color(27, 27, 27));
		przyciskZaznacz.setForeground(new Color(157, 157, 157));
		przyciskZaznacz.setPreferredSize(new Dimension(30,30));
		przyciskZaznacz.setBorderPainted(false);
		add(przyciskZaznacz);
		
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
	    				
		wykresDzwieku = new SoundGraph(x, y, step, density, samplesCount);
				
		buttonIcon = new ImageIcon("media/play.png");
		przyciskOdtworz = new JButton(buttonIcon);
		przyciskOdtworz.setBackground(new Color(27, 27, 27));
		przyciskOdtworz.setForeground(new Color(157, 157, 157));
		przyciskOdtworz.setPreferredSize(new Dimension(30,30));
		przyciskOdtworz.setBorderPainted(false);
		
		buttonIcon = new ImageIcon("media/magnifier_zoom_in.png");
		przyciskPowieksz = new JButton(buttonIcon);
		przyciskPowieksz.setBackground(new Color(27, 27, 27));
		przyciskPowieksz.setForeground(new Color(157, 157, 157));
		przyciskPowieksz.setPreferredSize(new Dimension(30,30));
		przyciskPowieksz.setBorderPainted(false);
		
		buttonIcon = new ImageIcon("media/magnifier_zoom_out.png");
		przyciskPomniejsz = new JButton(buttonIcon);
		przyciskPomniejsz.setBackground(new Color(27, 27, 27));
		przyciskPomniejsz.setForeground(new Color(157, 157, 157));
		przyciskPomniejsz.setPreferredSize(new Dimension(30,30));
		przyciskPomniejsz.setBorderPainted(false);
		
		buttonIcon = new ImageIcon("media/14_sinus.png");
		przyciskFalaPetla = new JButton(buttonIcon);
		przyciskFalaPetla.setBackground(new Color(27, 27, 27));
		przyciskFalaPetla.setForeground(new Color(157, 157, 157));
		przyciskFalaPetla.setPreferredSize(new Dimension(30,30));
		przyciskFalaPetla.setBorderPainted(false);
				
		_DEBUG_probkiIlosc = new JLabel("Iloœæ próbek: " + samplesCount);
		_DEBUG_probkiGestosc = new JLabel("Gêstoœæ próbek miêdzy kontrolnymi: " + density);
		
		add(przyciskFalaPetla);
		add(przyciskZaznacz);
		add(przyciskOdtworz);
		add(przyciskPowieksz);
		add(przyciskPomniejsz);
		add(poleZoom);
		add(wykresDzwieku);
             
        pasekPrzesuwu = new Scrollbar(Scrollbar.HORIZONTAL, 0, 0, 0, (samplesCount/density)-1);
        pasekPrzesuwu.addAdjustmentListener(new AdjustmentListener() {

			@Override
			public void adjustmentValueChanged(AdjustmentEvent arg0) {
				// TODO Auto-generated method stub
				wykresDzwieku.setScroll(arg0.getValue());
				
			}
        	
        	
        	
        });
        add(pasekPrzesuwu);
        pasekPrzesuwu.setBackground(new Color(27, 27, 27));
		pasekPrzesuwu.setForeground(new Color(157, 157, 157));
        pasekPrzesuwu.setLocation(0 ,0);
        pasekPrzesuwu.setPreferredSize(new Dimension((int) width, 20)); 
        
        add(_DEBUG_probkiIlosc);
        add(_DEBUG_probkiGestosc);
        
        setLayout(new FlowLayout());
        
        
        
        przyciskPencil.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
				wykresDzwieku.changeTool(0);
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        przyciskZaznacz.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
				wykresDzwieku.changeTool(2);
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        
        przyciskPowieksz.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
        		zoomStrength = Integer.parseInt(poleZoom.getText());
        		
        		wykresDzwieku.setScroll(0);
				wykresDzwieku.zoom(-zoomStrength);
				pasekPrzesuwu.setValue(0);
				pasekPrzesuwu.setMaximum((samplesCount/wykresDzwieku.getSampleDensity())-1);
				_DEBUG_probkiGestosc.setText("Gêstoœæ próbek miêdzy kontrolnymi: " + wykresDzwieku.getSampleDensity());
				wykresDzwieku.repaint();
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        przyciskPomniejsz.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
        		zoomStrength = Integer.parseInt(poleZoom.getText());
        		
        		wykresDzwieku.setScroll(0);
				wykresDzwieku.zoom(zoomStrength);
				pasekPrzesuwu.setValue(0);
				pasekPrzesuwu.setMaximum((samplesCount/wykresDzwieku.getSampleDensity())-1);
				_DEBUG_probkiGestosc.setText("Gêstoœæ próbek miêdzy kontrolnymi: " + wykresDzwieku.getSampleDensity());
				wykresDzwieku.repaint();
				
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        przyciskCzysc.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
				wykresDzwieku.resetSoundSignal();
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        przyciskFalaPetla.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
			        		
        		if (wykresDzwieku.selectedSamples != 0) {
        			
        			new LoopWaveGUI(wykresDzwieku.selectedSamples, wykresDzwieku);
        		}
				
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        
        przyciskLine.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
				wykresDzwieku.changeTool(1);
			}
			
						
			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
			//	wykresDzwieku.scroll(1);
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
        	
        });
        
        przyciskOdtworz.addMouseListener(new MouseListener() {

			@Override
			public void mouseClicked(MouseEvent arg0) {
				// TODO Auto-generated method stub
				int loop = 1;
				ArrayList<Integer> sum = new ArrayList<Integer>();
				ArrayList<Integer> substrate = wykresDzwieku.getSoundSignal();
				
				for (int i = 0; i < loop; i++) {
					
					sum.addAll(substrate);
				}
				
				enkoder.encodeSoundData(sum);
				enkoder.encodeChunksSizes();
				enkoder.concatenateByteArrays();
				enkoder.writeByteStreamToAFile("sound.wav");
				
				 try
				    {
				        Clip clip = AudioSystem.getClip();
				        clip.open(AudioSystem.getAudioInputStream(new File("sound.wav")));
				        clip.start();
				    }
				    catch (Exception exc)
				    {
				        exc.printStackTrace(System.out);
				    }
			}

			@Override
			public void mouseEntered(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseExited(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mousePressed(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}

			@Override
			public void mouseReleased(MouseEvent arg0) {
				// TODO Auto-generated method stub
				
			}
			
			
			
		});
        
        
        
	}     
	
	
	
	

}
