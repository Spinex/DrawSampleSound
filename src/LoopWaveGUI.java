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



public class LoopWaveGUI extends JFrame {

	//ArrayList<Integer> soundSignal = new ArrayList<Integer>();
	WavEncoder enkoder;
	
	Scrollbar pasekPrzesuwu;
	
	SoundGraph wykresDzwieku;
	SoundGraph glownyWykresDzwieku;
	JButton przyciskOdtworz, przyciskPowieksz, przyciskPomniejsz, przyciskPencil, przyciskLine, przyciskCzysc,
	przyciskGenerujSinus;
	JLabel _DEBUG_probkiIlosc, _DEBUG_probkiGestosc, napisOscylator, napisFaza, napisAmplituda, napisCzestotliwosc;
	JTextField polePrzesuniecieFazowe, poleAmplituda, poleCzestotliwosc;
	
	int samplesCount = 44100;
	
	JButton przyciskZastosuj;
	
	private static final long serialVersionUID = 1L;
	
	public LoopWaveGUI(int _samplesCount, SoundGraph _mainSoundGraph) {
		super("W³asna fala okresowa");
		ImageIcon buttonIcon;
		
		samplesCount = _samplesCount;
		glownyWykresDzwieku = _mainSoundGraph;
		
		
		
		Image icon = Toolkit.getDefaultToolkit().getImage("media/music.png");
		setIconImage(icon);
		
		setVisible(true);
		//setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setFocusable(true);
		setLocation(200, 200);
		getContentPane().setBackground(new Color(27,27,27));
		
		int x = 800;
		
		setResizable(false);
		setSize(x, 450);
		
		
		int step = 10;
		int density = 1;
		
		while ((samplesCount / density)*step < x) step += 1;
		
		
		enkoder = new WavEncoder();
		   				
		wykresDzwieku = new SoundGraph(x, 300, step, density, samplesCount);
		
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
		przyciskGenerujSinus = new JButton("Generuj falê");
		przyciskGenerujSinus.setBackground(new Color(27, 27, 27));
		przyciskGenerujSinus.setForeground(new Color(157, 157, 157));
		przyciskGenerujSinus.setBorderPainted(false);
		
		polePrzesuniecieFazowe = new JTextField("0");
		polePrzesuniecieFazowe.setBackground(new Color(27, 27, 27));
		polePrzesuniecieFazowe.setForeground(new Color(157, 157, 157));
		polePrzesuniecieFazowe.setPreferredSize(new Dimension(100,20));
		
		poleCzestotliwosc = new JTextField("440");
		poleCzestotliwosc.setBackground(new Color(27, 27, 27));
		poleCzestotliwosc.setForeground(new Color(157, 157, 157));
		poleCzestotliwosc.setPreferredSize(new Dimension(100,20));
		
		poleAmplituda = new JTextField("10000");
		poleAmplituda.setBackground(new Color(27, 27, 27));
		poleAmplituda.setForeground(new Color(157, 157, 157));
		poleAmplituda.setPreferredSize(new Dimension(100,20));
		
		
		przyciskZastosuj = new JButton("Zastosuj");
		przyciskZastosuj.setBackground(new Color(27, 27, 27));
		przyciskZastosuj.setForeground(new Color(157, 157, 157));
		
		przyciskZastosuj.setBorderPainted(false);
		
						
		_DEBUG_probkiIlosc = new JLabel("Iloœæ próbek: " + samplesCount);
		_DEBUG_probkiGestosc = new JLabel("Gêstoœæ próbek miêdzy kontrolnymi: " + density);
		napisOscylator = new JLabel("Oscylator");
		napisOscylator.setSize(100, 20);
		napisFaza = new JLabel("Przesuniêcie fazowe:");
		napisAmplituda = new JLabel("Amplituda:");
		napisCzestotliwosc = new JLabel("Czêstotliwoœæ (Hz): ");
		
		
		add(przyciskOdtworz);
		add(przyciskPowieksz);
		add(przyciskPomniejsz);
		add(przyciskZastosuj);
		add(przyciskGenerujSinus);
		add(napisOscylator);
		add(napisFaza);
		add(polePrzesuniecieFazowe);
		add(napisAmplituda);
		add(poleAmplituda);
		add(napisCzestotliwosc);
		add(poleCzestotliwosc);
		
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
        pasekPrzesuwu.setPreferredSize(new Dimension(x, 20)); 
        
        
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
        
        przyciskGenerujSinus.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
        		int amplitude = Integer.parseInt(poleAmplituda.getText());
        		int phaseMove = Integer.parseInt(polePrzesuniecieFazowe.getText());
        		int frequency = Integer.parseInt(poleCzestotliwosc.getText());
        		
				wykresDzwieku.setSinWave(amplitude, phaseMove, frequency);
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
        
        przyciskZastosuj.addMouseListener(new MouseListener() {
        	
        	@Override
			public void mouseClicked(MouseEvent arg0) {
				
				glownyWykresDzwieku.addSignal(wykresDzwieku.getSoundSignal());
				glownyWykresDzwieku.repaint();
				dispose();
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
				
        		wykresDzwieku.setScroll(0);
				wykresDzwieku.zoom(-1);
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
				
        		wykresDzwieku.setScroll(0);
				wykresDzwieku.zoom(1);
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
