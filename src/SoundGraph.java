import java.awt.Color;
import java.awt.Dimension;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Point;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ArrayList;

import javax.swing.JPanel;


public class SoundGraph extends JPanel {

	ArrayList<Integer> soundSignal = new ArrayList<Integer>();
	final int testAmplitude = 32767;
	 
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	final int nnX = 900;
	
	int nX;
	int nY;
	int sampleStep;
	int sampleDensity;
	//int multiplyPages;
	int scroll = 0;
	int toolNumber = 0;
	
	int startSelectX, endSelectX;
	int startSelectSample = 0, selectedSamples = 0;
	
	boolean bDrawSelectRectangle = false;			
	boolean firstCallLineTool = true;
	int startXLineTool, startYLineTool;
	int deltaXLineTool = 0;
	int deltaYLineTool = 0;
		
	public SoundGraph(int x, int y, int _sampleStep, int _sampleDensity, int _samplesCount) {
		
		nX = x;
		nY = y;
		sampleStep = _sampleStep;
		sampleDensity = _sampleDensity;
			
		setPreferredSize(new Dimension(nX, nY));
				
		for (int i = 0; i < _samplesCount; i++) {
			
			soundSignal.add(new Integer(0));
		}
		
	   addMouseListener(new MouseAdapter() {
		   
		   public void mouseReleased(MouseEvent me) {
	        	  
	        	  firstCallLineTool = true;
	        	  bDrawSelectRectangle = false;
	        	 
	       }
	   });
		
	   addMouseMotionListener(new MouseAdapter() { 
	          	          
	          public void mouseDragged(MouseEvent me) {
	        	 
	        	  if (toolNumber == 0) {
	        		  int x = me.getX(); 
	        		  int y = me.getY();
		            
	        		  changeSampleLevel(x, y);
	        		  repaint(); 
	        	  }
	        	  else if (toolNumber == 1) {
	        		  if (firstCallLineTool) {
	        			  startXLineTool = me.getX();
	        			  startYLineTool = me.getY();
	        			  firstCallLineTool = !firstCallLineTool;
	        		
	        		  }
	        		  
	        		  int deltaX = Math.abs(me.getX() - startXLineTool);
	        		  int deltaY = me.getY() - startYLineTool;
	        		  float stepDifference;
	        		  
	        		  if (deltaX != 0 && deltaX/sampleStep != 0) {
	        		  stepDifference =  deltaY / (float) (deltaX/ (float) (sampleStep));
	        		  
	        		  float previous = startYLineTool;
	        		  
	        		  int control = 1;
	        		  if (me.getX() >= startXLineTool) control = 2;
	        		  else control = 1;
	        		  
	        			  for (int i = 1; i < (deltaX/sampleStep) + control; i++) {
	        				  
	        				  if (me.getX() >= startXLineTool)
	        				  changeSampleLevel(startXLineTool + i*sampleStep, (int) (previous + stepDifference));
	        				  else if (me.getX() < startXLineTool)
	        				  changeSampleLevel(startXLineTool - i*sampleStep, (int) (previous + stepDifference));	  
	        				  previous = previous + stepDifference;
	        			  } 
	        		  }	  
	        		         		  
	        		 repaint();        		  
	        	  }
	        	  else if (toolNumber == 2) {
	        		  if (firstCallLineTool) {
	        			  startXLineTool = me.getX();
	        			  startYLineTool = me.getY();
	        			  firstCallLineTool = !firstCallLineTool;
	        		
	        		  }
	        		  
	        		  int deltaX = Math.abs(me.getX() - startXLineTool);
	        		  bDrawSelectRectangle = true;
	        		  
	        		  if (me.getX() >= startXLineTool) {
	        			  startSelectX = startXLineTool;
	        			  endSelectX = deltaX;
	        			  startSelectSample = ((startXLineTool / sampleStep)+scroll) * sampleDensity;
	        			  selectedSamples = Math.round((deltaX / (float) sampleStep) * sampleDensity);
	        		  }
	        		  else {
	        			  startSelectX = me.getX();
	        			  endSelectX = deltaX; 
	        			  startSelectSample = ((me.getX() / sampleStep)+scroll) * sampleDensity;
	        			  selectedSamples = (deltaX / sampleStep) * sampleDensity;
	        			  
	        		  }
	        		  repaint();
	        		  
	        	  }
	        	  
	          }
	          
	          
	        	          
	    });
		
	}
			
	public void setSinWave(int _amplitude, int _phaseMove, int _frequency) {
		
		double sekundyOkres = 1 / (double) (_frequency);
		int probkiOkres = Math.round((float)(44100 * sekundyOkres));
		
		double angleDegreeStep = 360 / (double) probkiOkres;
		double angleRadianStep = Math.toRadians(angleDegreeStep);
		
		int size = soundSignal.size();
		
		soundSignal.clear();
		
		for (int i = 0; i < size; i++) {
			
			soundSignal.add(new Integer((int) (_amplitude * Math.sin((angleRadianStep*i) + _phaseMove))));
		}
				
	}
	
	public void addSignal(ArrayList<Integer> signal) {
		
		int size = signal.size();
		int j = 0;
						
		for (int i = startSelectSample; i < startSelectSample+size; i++) {
			
			soundSignal.set(i, new Integer(soundSignal.get(i).intValue() + signal.get(j).intValue()));
			j++;
		}
				
		
	}
	
	public void resetSoundSignal() {
		
		int size = soundSignal.size();
		soundSignal.clear();
		for (int i = 0; i < size; i++) {
			
			soundSignal.add(new Integer(0));
		}
		
		repaint();
	}
	
	public void changeTool(int value) {toolNumber = value;}
	
	public void scroll(int value) {
		
		int copyScroll = scroll;
		scroll += value;
		if (scroll < 0) scroll = 0;
		if ((scroll*sampleStep) + nX >= (soundSignal.size() / sampleDensity) * sampleStep) {
			scroll = copyScroll;
			scroll(value - 1);
		}
		
		repaint();
	}
	
	public void setScroll(int value) {
		
		int copyScroll = scroll;
		scroll = value;
		if (scroll < 0) scroll = 0;
		if ((scroll*sampleStep) + nX >= (soundSignal.size() / sampleDensity) * sampleStep) {
			scroll = copyScroll;
			
		}
		
		repaint();
	}
	
	public void zoom(int value) {
		
		int copy = sampleDensity;
		sampleDensity += value;
		
		if (sampleDensity <= 0) sampleDensity = copy;
		
		for (int i = 0; i < nX/sampleStep; i++) {
			
			if (i*sampleDensity >= soundSignal.size()) sampleDensity = copy;
		}
	}
	
	public void averageSampleDifference(int a, int b) {
		
		if (a > 0) {
		
		int signalDifference = soundSignal.get(b*sampleDensity).intValue() - soundSignal.get(a*sampleDensity).intValue();
		int averageStep = signalDifference / sampleDensity;
		
		for (int i = a*sampleDensity; i < (b*sampleDensity) - 1; i++) {
			
			soundSignal.set(i+1, new Integer(soundSignal.get(i).intValue() + averageStep));
		}
	  }
	}
	
	//*	
	public void changeSampleLevel(int x, int y) {
		
		for (int i = 0; i < nX/sampleStep; i++) {
			// && i < soundSignal.size();
			if (x >= i*sampleStep && x < ((i + 1)*sampleStep)) {
				
				int scaleFactor = testAmplitude / (nY/2);
				
				if (y < nY/2)
				soundSignal.set((i+scroll)*sampleDensity, new Integer(((nY/2)*scaleFactor) - (y*scaleFactor)));
				else if (y > nY/2) {
					y = y - (nY/2);
				   soundSignal.set((i+scroll)*sampleDensity, new Integer(-y * scaleFactor));	
				}
				else soundSignal.set((i+scroll)*sampleDensity, new Integer(0));
				
				averageSampleDifference(i+scroll-1, i+scroll);
				
			}
			
			
		}
	}
	
	int getSampleDensity() {return sampleDensity;}
	
	public void drawWireframe(Graphics2D g2d) {
		g2d.setColor(new Color(50,70,90));
		for (int i = 0; i < nY/10; i++) {
						
			g2d.drawLine(0, 10*i, nX-1, 10*i);
											
		}
		for (int i = 0; i < nX/sampleStep; i++) {
			
			g2d.drawLine(sampleStep*i, 0, sampleStep*i, nY-1);
						
		}
	}
	//*
	public void drawSoundWaveLine(Graphics2D g2d, int amplitude) {
		
		g2d.setColor(new Color(200, 90, 100));
		
		ArrayList<Point> coordinates = new ArrayList<Point>();
		int scaleFactor = amplitude / (nY/2);
		
		//soundSignal.size()/sampleDensity;
		for (int i = 0; i < nX/sampleStep; i++) {
			
			if (soundSignal.get((i+scroll)*sampleDensity).intValue() > 0)
			coordinates.add(new Point(i*sampleStep, (nY/2) - Math.abs(soundSignal.get((i+scroll)*sampleDensity).intValue())/scaleFactor));
			else 
		    coordinates.add(new Point(i*sampleStep, (nY/2) + Math.abs(soundSignal.get((i+scroll)*sampleDensity).intValue())/scaleFactor));	
			
		}
			
		
		for (int i = 0; i < coordinates.size()-1; i++) {
			
			int x1 = coordinates.get(i).x;
			int y1 = coordinates.get(i).y;
			
			int x2 = coordinates.get(i+1).x;
			int y2 = coordinates.get(i+1).y;
			g2d.drawLine(x1, y1, x2, y2);
		}
		
		int x = coordinates.get(coordinates.size()-1).x;
		int y = coordinates.get(coordinates.size()-1).y;
		
		g2d.drawLine(x, y, nX-1, y);
		
		coordinates.clear();
	}
	
	@Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);
        Graphics2D g2d = (Graphics2D) g;
 
        g2d.setColor(new Color(50,50,50));        // prostokat
        g2d.fillRect(0,0,nX,nY);
        
        if (bDrawSelectRectangle) {
        	g2d.setColor(new Color(70,60,60));
        	g2d.fillRect(startSelectX, 0, endSelectX, nY);
        	g2d.setColor(new Color(50,50,50)); 
        }
        
        drawWireframe(g2d);
        
        g2d.setColor(new Color(155,155,155));
        g2d.drawLine(0, nY/2, nX, nY/2);
        
        drawSoundWaveLine(g2d, testAmplitude);
            
    }
	
	ArrayList<Integer> getSoundSignal() {
		
		return soundSignal;
	}
	
	



}
