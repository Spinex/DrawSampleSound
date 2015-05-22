;WAV File

format binary

db "RIFF" ;ChunkID
dd 46	  ;ChunkSize = 36 + SubChunk2Size, or
	  ;4 + (8 + SubChunk1Size) + (8 + SubChunk2Size)
db "WAVE" ;Format

db "fmt " ;Subchunk1ID
dd 16 ;Subchunk1Size, 16 for PCM
dw 1 ;AudioFormat
dw 1 ;NumChannels, mono = 1
dd 44100 ;SampleRate

dd 88200 ;ByteRate = SampleRate * NumChannels * BitsPerSample/8
dw 2 ;BlockAlign = NumChannels * BitsPerSample/8
dw 16 ;BitsPerSample

db "data" ;Subchunk2ID
dd 10 ;Subchunk2Size = NumSamples * NumChannels * BitsPerSample/8
     ;Number of bytes in the data

;The actual sound data:
dw 65535
dw 32767
dw 1024
dw 1025
dw 5