package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;

import javax.sound.sampled.AudioFileFormat;
import javax.sound.sampled.AudioFormat;
import javax.sound.sampled.AudioInputStream;
import javax.sound.sampled.AudioSystem;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JSpinner;
import javax.swing.JTextField;
import javax.swing.SpinnerNumberModel;
import javax.swing.filechooser.FileNameExtensionFilter;

import com.xuggle.mediatool.IMediaReader;
import com.xuggle.mediatool.IMediaWriter;
import com.xuggle.mediatool.ToolFactory;

public class Trim {
	private JFrame trimmer;
	private JTextField txtFile;
	private JTextField txtName;
	private JTextField txtSave;
	String userhome = System.getProperty("user.home"), direc1 = null;
	File one = null;
	File two = null;

	public static void main(String[] args) {
		Trim trm = new Trim();
		trm.trim();
	}

	public void trim() {

		trimmer = new JFrame();
		trimmer.setBounds(100, 100, 450, 422);
		trimmer.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		trimmer.getContentPane().setLayout(null);

		JLabel lblFile = new JLabel("Choose File:");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFile.setBounds(30, 11, 161, 29);
		trimmer.getContentPane().add(lblFile);

		txtFile = new JTextField();
		txtFile.setEditable(false);
		txtFile.setColumns(10);
		txtFile.setBounds(30, 45, 275, 20);
		trimmer.getContentPane().add(txtFile);

		JButton btnBrowse1 = new JButton("Browse");
		btnBrowse1.setBounds(315, 44, 89, 23);
		trimmer.getContentPane().add(btnBrowse1);
		btnBrowse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(userhome + "\\Desktop");

				FileNameExtensionFilter filter = new FileNameExtensionFilter("mp3 files", "mp3","wav");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);

				fc.setDialogTitle("Choose file");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					one = fc.getSelectedFile();
					txtFile.setText(fc.getName(one));
					direc1 = one.getAbsolutePath();
					IMediaReader mediaReader = ToolFactory.makeReader(direc1);
					String output =  "Database\\a.wav";
					IMediaWriter mediaWriter = ToolFactory.makeWriter(output, mediaReader);
					mediaReader.addListener(mediaWriter);
					while (mediaReader.readPacket() == null);
					
					two = new File(output);
				}
			}
		});

		JLabel lblStart = new JLabel("Start Time (second):");
		lblStart.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblStart.setBounds(30, 76, 189, 29);
		trimmer.getContentPane().add(lblStart);

		JLabel lblSpan = new JLabel("Span (seconds):");
		lblSpan.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSpan.setBounds(30, 116, 161, 29);
		trimmer.getContentPane().add(lblSpan);

		JSpinner spinner1 = new JSpinner();
		spinner1.setModel(new SpinnerNumberModel(new Integer(0), new Integer(0), null, new Integer(1)));
		spinner1.setBounds(251, 82, 54, 20);
		trimmer.getContentPane().add(spinner1);

		JSpinner spinner2 = new JSpinner();
		spinner2.setModel(new SpinnerNumberModel(new Integer(1), new Integer(0), null, new Integer(1)));
		spinner2.setBounds(251, 122, 54, 20);
		trimmer.getContentPane().add(spinner2);

		JLabel lblName = new JLabel("Enter Trimmed File Name:");
		lblName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblName.setBounds(30, 182, 257, 29);
		trimmer.getContentPane().add(lblName);

		txtName = new JTextField();
		txtName.setColumns(10);
		txtName.setBounds(30, 217, 275, 20);
		trimmer.getContentPane().add(txtName);

		JLabel lblSave = new JLabel("Enter Save Directory");
		lblSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblSave.setBounds(30, 248, 161, 29);
		trimmer.getContentPane().add(lblSave);

		txtSave = new JTextField();
		txtSave.setEditable(false);
		txtSave.setColumns(10);
		txtSave.setBounds(30, 282, 275, 20);
		trimmer.getContentPane().add(txtSave);

		JButton btnBrowse2 = new JButton("Browse");
		btnBrowse2.setBounds(315, 281, 89, 23);
		trimmer.getContentPane().add(btnBrowse2);
		btnBrowse2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(userhome + "\\Desktop");

				fc.setDialogTitle("Choose Save Location");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					txtSave.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBack.setBounds(250, 325, 100, 35);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Intro intro = new Intro();
				intro.start();
				trimmer.dispose();
			}
		});
		
		JButton btnTrim = new JButton("Trim");
		btnTrim.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnTrim.setBounds(100, 325, 100, 35);
		trimmer.getContentPane().add(btnTrim);
		trimmer.getContentPane().add(btnBack);

		btnTrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent b) {
				if (txtFile.getText().equals(""))
					return;
				if (txtName.getText().equals(""))
					return;
				if (txtSave.getText().equals(""))
					return;

				AudioInputStream inputStream = null;
				AudioInputStream shortenedStream = null;

				try {
					File file = two;
					AudioFileFormat fileFormat = AudioSystem.getAudioFileFormat(file);
					AudioFormat format = fileFormat.getFormat();
					inputStream = AudioSystem.getAudioInputStream(file);
					int bytesPerSecond = format.getFrameSize() * (int) format.getFrameRate();
					inputStream.skip((int) (spinner1.getValue()) * bytesPerSecond);
					long framesOfAudioToCopy = (int) (spinner2.getValue()) * (int) format.getFrameRate();
					shortenedStream = new AudioInputStream(inputStream, format, framesOfAudioToCopy);
					String path1 = "Database\\b.wav";
					File destinationFile = new File(path1);
					//File destinationFile = new File(txtSave.getText() + "/" + txtName.getText() + ".wav");
					AudioSystem.write(shortenedStream, fileFormat.getType(), destinationFile);
					
					IMediaReader mediaReader = ToolFactory.makeReader(path1);
					String output = txtSave.getText() + "/" + txtName.getText() + ".mp3";
					IMediaWriter mediaWriter = ToolFactory.makeWriter(output, mediaReader);
					mediaReader.addListener(mediaWriter);
					while (mediaReader.readPacket() == null)
						;

				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if (inputStream != null) {
						try {
							inputStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
					if (shortenedStream != null) {
						try {
							shortenedStream.close();
						} catch (Exception e) {
							e.printStackTrace();
						}
					}
				}
			}
		});

		trimmer.setVisible(true);
	}

}
