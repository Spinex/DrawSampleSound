import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;


public class WavEncoder {
	
	// Trzeba liczyc ChunkSize i Subchunk2Size na bajty, reszta sie nie zmienia
	// Do dodawania tablic bajtow String[] both = ArrayUtils.addAll(first, second); lub
	/* 	T[] concat(T[] A, T[] B) {
   		int aLen = A.length;
   		int bLen = B.length;
   		T[] C= new T[aLen+bLen];
   		System.arraycopy(A, 0, C, 0, aLen);
   		System.arraycopy(B, 0, C, aLen, bLen);
   		return C;
		} */
	
	short soundDataBuffer[];
	
	int Subchunk2Size;
	int ChunkSize;
	
	byte[] finalBuffer;
	byte[] byteChunkSize = new byte[4];
	byte[] byteSubchunk2Size = new byte[4];
	byte byteSoundDataBuffer[];
	
	final byte[] dataLetters = {'d', 'a', 't', 'a'};
	final byte[] fmt_Letters = {'f', 'm', 't', ' '}; 
	final byte[] ChunkID = {'R', 'I', 'F', 'F'};
	final byte[] Format = {'W', 'A', 'V', 'E'};
	final byte[] SampleRate = {68, -84, 0, 0}; //44100
	final byte[] Subchunk1Size = {16, 0, 0, 0};
	final byte[] AudioFormat = {1, 0};
	final byte[] NumChannels = {1, 0};
	final byte[] ByteRate = {-120,88,1,0}; //88200
	final byte[] BlockAlign = {2, 0};
	final byte[] BitsPerSample = {16, 0};
	
	
	final int byteRate = 88200;
	final int sampleRate = 44100;
	final short blockAlign = 2;
	final short bitsPerSample = 16;
	
	public WavEncoder() {
		
		
	}
	
	public void encodeChunksSizes() {
		
		int cryptoByte1, cryptoByte2, cryptoByte3, cryptoByte4;
		
		cryptoByte1 = Subchunk2Size % 256;
		cryptoByte2 = (Subchunk2Size / 256) % 256;
		cryptoByte3 = (Subchunk2Size / (256*256)) % 256;
		cryptoByte4 = (Subchunk2Size / (256*256*256)) % 256;
		
		if (cryptoByte1 > 127) {
			
			cryptoByte1 = 256 - cryptoByte1;
			byteSubchunk2Size[0] = (byte) (-cryptoByte1);
		}
		else byteSubchunk2Size[0] = (byte) (cryptoByte1);
		if (cryptoByte2 > 127) {
			
			cryptoByte2 = 256 - cryptoByte2;
			byteSubchunk2Size[1] = (byte) (-cryptoByte2);
		}
		else byteSubchunk2Size[1] = (byte) (cryptoByte2);
		
		if (cryptoByte3 > 127) {
			
			cryptoByte3 = 256 - cryptoByte3;
			byteSubchunk2Size[2] = (byte) (-cryptoByte3);
		}
		else byteSubchunk2Size[2] = (byte) (cryptoByte3);
		if (cryptoByte4 > 127) {
			
			cryptoByte4 = 256 - cryptoByte4;
			byteSubchunk2Size[3] = (byte) (-cryptoByte4);
		}
		else byteSubchunk2Size[3] = (byte) (cryptoByte4);
		
		
		// Now we encode Chunksize to bytes
		
		cryptoByte1 = ChunkSize % 256;
		cryptoByte2 = (ChunkSize / 256) % 256;
		cryptoByte3 = (ChunkSize / (256*256)) % 256;
		cryptoByte4 = (ChunkSize / (256*256*256)) % 256;
		
		if (cryptoByte1 > 127) {
			
			cryptoByte1 = 256 - cryptoByte1;
			byteChunkSize[0] = (byte) (-cryptoByte1);
		}
		else byteChunkSize[0] = (byte) (cryptoByte1);
		if (cryptoByte2 > 127) {
			
			cryptoByte2 = 256 - cryptoByte2;
			byteChunkSize[1] = (byte) (-cryptoByte2);
		}
		else byteChunkSize[1] = (byte) (cryptoByte2);
		
		if (cryptoByte3 > 127) {
			
			cryptoByte3 = 256 - cryptoByte3;
			byteChunkSize[2] = (byte) (-cryptoByte3);
		}
		else byteChunkSize[2] = (byte) (cryptoByte3);
		if (cryptoByte4 > 127) {
			
			cryptoByte4 = 256 - cryptoByte4;
			byteChunkSize[3] = (byte) (-cryptoByte4);
		}
		else byteChunkSize[3] = (byte) (cryptoByte4);
	}
	
	public void encodeSoundData(ArrayList<Integer> soundSignal) {
		
		soundDataBuffer = new short[soundSignal.size()];
		
		for (int i = 0; i < soundSignal.size(); i++) 					
			soundDataBuffer[i] = (short) soundSignal.get(i).intValue();
			
		
		Subchunk2Size = soundSignal.size() * 1 * 2;
		ChunkSize = 36 + Subchunk2Size;
		
		byteSoundDataBuffer = new byte[soundSignal.size()*2];
		int helpVariable;
		
		int cryptoByte1, cryptoByte2;
		
		int k = 0;
		
		for (int i = 0; i < soundSignal.size(); i++) {
									
			if (soundDataBuffer[i] < 0) {
				
				 helpVariable = Math.abs(soundDataBuffer[i]);
				 int helpVariable2 = 65535 - helpVariable;
				 cryptoByte1 = helpVariable2 % 256;
				 cryptoByte2 = (helpVariable2 / 256) % 256;
			}
			else {
				
				cryptoByte1 = soundDataBuffer[i] % 256;
				cryptoByte2 = (soundDataBuffer[i] / 256) % 256;
			}
			
			if (cryptoByte1 > 127) {
				
				cryptoByte1 = 256 - cryptoByte1;
				byteSoundDataBuffer[k] = (byte) (-cryptoByte1);
			}
			else byteSoundDataBuffer[k] = (byte) (cryptoByte1);
			if (cryptoByte2 > 127) {
				
				cryptoByte2 = 256 - cryptoByte2;
				byteSoundDataBuffer[k+1] = (byte) (-cryptoByte2);
			}
			else byteSoundDataBuffer[k+1] = (byte) (cryptoByte2);
			
			k+=2;
			
		}
	}	
	
	    byte[] concat(byte[] A, byte[] B) {
		   int aLen = A.length;
		   int bLen = B.length;
		   byte[] C= new byte[aLen+bLen];
		   System.arraycopy(A, 0, C, 0, aLen);
		   System.arraycopy(B, 0, C, aLen, bLen);
		   return C;
		}
		
	public void concatenateByteArrays() {
		
		byte[] buf1 = concat(ChunkID, byteChunkSize);
		byte[] buf2 = concat(buf1, Format);
		byte[] buf3 = concat(buf2, fmt_Letters);
		byte[] buf4 = concat(buf3, Subchunk1Size);
		byte[] buf5 = concat(buf4, AudioFormat);
		byte[] buf6 = concat(buf5, NumChannels);
		byte[] buf7 = concat(buf6, SampleRate);
		byte[] buf8 = concat(buf7, ByteRate);
		byte[] buf9 = concat(buf8, BlockAlign);
		byte[] buf10 = concat(buf9, BitsPerSample);
		byte[] buf11 = concat(buf10, dataLetters);
		byte[] buf12 = concat(buf11, byteSubchunk2Size);
		finalBuffer = concat(buf12, byteSoundDataBuffer);
		
	}
	
	public void writeByteStreamToAFile(String strFilePath) {
		
		
		
		  File f;
		  f=new File(strFilePath);
		  if(!f.exists()){
		  try {	  
			  f.createNewFile();
		  } catch (IOException e) {
			  
		  }
		  		System.out.println("New file has been created to the current directory");
		  }
		
		 try
	     {
	      FileOutputStream fos = new FileOutputStream(strFilePath);
	      	         
	      /*
	       * To write byte array to a file, use
	       * void write(byte[] bArray) method of Java FileOutputStream class.
	       *
	       * This method writes given byte array to a file.
	       */
	     
	       fos.write(finalBuffer);
	     
	      /*
	       * Close FileOutputStream using,
	       * void close() method of Java FileOutputStream class.
	       *
	       */
	     
	       fos.close();
	     
	     }
	     catch(FileNotFoundException ex)
	     {
	      System.out.println("FileNotFoundException : " + ex);
	     }
	     catch(IOException ioe)
	     {
	      System.out.println("IOException : " + ioe);
	     }
	   
	}
	

}
