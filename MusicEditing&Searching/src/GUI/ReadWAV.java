package GUI;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.sound.sampled.UnsupportedAudioFileException;

public class ReadWAV {

	public void read(String filename,String binary) throws IOException {
		byte[] data = readByte(filename);

		// code for create file with binary data
		FileOutputStream out = new FileOutputStream(binary);
		out.write(data);
		out.close();
	}

	private byte[] readByte(String filename) {
		byte[] data = null;
		AudioInputStream ais = null;
		try {

			// try to read from file
			File file = new File(filename);
			if (file.exists()) {
				ais = AudioSystem.getAudioInputStream(file);
				data = new byte[ais.available()];
				ais.read(data);
			}

		} catch (IOException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException("Could not read " + filename);
		}

		catch (UnsupportedAudioFileException e) {
			System.out.println(e.getMessage());
			throw new RuntimeException(filename + " in unsupported audio format");
		}

		return data;
	}


}
