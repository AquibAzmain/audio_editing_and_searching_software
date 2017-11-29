package GUI;

import java.io.File;
import java.io.FileInputStream;
//import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;

import org.apache.tika.exception.TikaException;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.parser.mp3.Mp3Parser;
import org.xml.sax.ContentHandler;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

//import exceptionHandler.HandleException;

public class GetMetadata {

	/**
	 * @param args
	 * @throws TikaException
	 * @throws SAXException
	 * @throws IOException
	 */
	public String getDuration(String fileLocation) throws IOException, SAXException, TikaException {
		// String fileLocation = "E:\\My songs\\Tumi boruna hole.mp3";

		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		// List all metadata
		// String[] metadataNames = metadata.names();

		/*
		 * String[] metadataNames =
		 * {"xmpDM:duration","xmpDM:audioSampleRate","channels",
		 * "xmpDM:audioChannelType","version"}; for (String name :
		 * metadataNames) { System.out.println(name + ": " +
		 * metadata.get(name)); } System.out.println("xmpDM:duration: " +
		 * metadata.get("xmpDM:duration"));
		 */

		return metadata.get("xmpDM:duration");

	}

	public String getSampleRate(String fileLocation) throws IOException, SAXException, TikaException {
		// String fileLocation = "E:\\My songs\\Tumi boruna hole.mp3";

		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		return metadata.get("xmpDM:audioSampleRate");

	}

	public String getChannels(String fileLocation) throws IOException, SAXException, TikaException {
		// String fileLocation = "E:\\My songs\\Tumi boruna hole.mp3";

		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		return metadata.get("channels");

	}

	public String getChannelType(String fileLocation) throws IOException, SAXException, TikaException {
		// String fileLocation = "E:\\My songs\\Tumi boruna hole.mp3";

		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		return metadata.get("xmpDM:audioChannelType");

	}

	public String getVersion(String fileLocation) throws IOException, SAXException, TikaException {
		// String fileLocation = "E:\\My songs\\Tumi boruna hole.mp3";

		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		return metadata.get("version");

	}

	public String PrintDetails(String fileLocation) throws IOException, SAXException, TikaException {
		InputStream input = new FileInputStream(new File(fileLocation));
		ContentHandler handler = new DefaultHandler();
		Metadata metadata = new Metadata();
		Parser parser = new Mp3Parser();
		ParseContext parseCtx = new ParseContext();
		parser.parse(input, handler, metadata, parseCtx);
		input.close();

		String[] metadataNames = metadata.names();

		String out = "";
		
		out += "----------------------------------------------\n";
		out += "Title: " + metadata.get("title") + "\n";
		out += "Artists: " + metadata.get("xmpDM:artist") + "\n";
		out += "Composer : " + metadata.get("xmpDM:composer") + "\n";
		out += "Genre : " + metadata.get("xmpDM:genre") + "\n";
		out += "Album : " + metadata.get("xmpDM:album") + "\n";
		out += "----------------------------------------------\n";
		out += "Full information:\n\n";
		for (String name : metadataNames) {
			out += name + ": " + metadata.get(name) + "\n";
		}
		/*System.out.println("----------------------------------------------");
		System.out.println("Title: " + metadata.get("title"));
		System.out.println("Artists: " + metadata.get("xmpDM:artist"));
		System.out.println("Composer : " + metadata.get("xmpDM:composer"));
		System.out.println("Genre : " + metadata.get("xmpDM:genre"));
		System.out.println("Album : " + metadata.get("xmpDM:album"));
		System.out.println("----------------------------------------------");
		System.out.println("Full information:\n");
		for (String name : metadataNames) {
			System.out.println(name + ": " + metadata.get(name));
		}*/
		return out;
	}
}
