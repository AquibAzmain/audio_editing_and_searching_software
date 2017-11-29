package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;
import java.util.*;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;


public class SearchByte {
	private JFrame metaFrame;
	private JTextField txtFile;
	private JTextField txtDirec;
	private String direc = null;
	private String userhome = System.getProperty("user.home");
	private File one = null;
	HashMap<String, Integer> hm = new HashMap<String, Integer>();

	public static void main(String[] args) {
		SearchByte sb = new SearchByte();
		sb.search();
	}

	public void search() {
		metaFrame = new JFrame("Search By Bytes");
		metaFrame.setBounds(100, 40, 450, 700);
		metaFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		metaFrame.getContentPane().setLayout(null);

		JLabel lblFile = new JLabel("Choose File:");
		lblFile.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblFile.setBounds(28, 11, 161, 29);
		metaFrame.getContentPane().add(lblFile);

		txtFile = new JTextField();
		txtFile.setEditable(false);
		txtFile.setColumns(10);
		txtFile.setBounds(28, 45, 275, 20);
		metaFrame.getContentPane().add(txtFile);

		JButton btnBrowse1 = new JButton("Browse");
		btnBrowse1.setBounds(313, 44, 89, 23);
		metaFrame.getContentPane().add(btnBrowse1);

		btnBrowse1.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(userhome + "\\Desktop");

				FileNameExtensionFilter filter = new FileNameExtensionFilter("WAV Files", "WAV");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);

				fc.setDialogTitle("Choose MP3");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);

				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					one = fc.getSelectedFile();
					txtFile.setText(fc.getName(one));
					direc = one.getAbsolutePath();

				}
			}
		});

		JLabel lblDirec = new JLabel("Enter Search Directory");
		lblDirec.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblDirec.setBounds(28, 78, 161, 29);
		metaFrame.getContentPane().add(lblDirec);

		txtDirec = new JTextField();
		txtDirec.setEditable(false);
		txtDirec.setColumns(10);
		txtDirec.setBounds(28, 112, 275, 20);
		metaFrame.getContentPane().add(txtDirec);

		JButton btnBrowse2 = new JButton("Browse");
		btnBrowse2.setBounds(313, 111, 89, 23);
		metaFrame.getContentPane().add(btnBrowse2);

		btnBrowse2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(userhome + "\\Desktop");

				fc.setDialogTitle("Choose Save Location");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);

				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					txtDirec.setText(fc.getSelectedFile().getAbsolutePath());
				}
			}
		});

		JTextArea textArea = new JTextArea();
		textArea.setEditable(false);
		textArea.setLineWrap(true);
		textArea.setBounds(28, 186, 376, 350);
		metaFrame.getContentPane().add(textArea);

		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBack.setBounds(250, 151, 89, 23);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Intro intro = new Intro();
				intro.start();
				metaFrame.dispose();
			}
		});

		JButton btnSearch = new JButton("Search");
		btnSearch.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnSearch.setBounds(100, 151, 89, 23);

		btnSearch.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				if (txtFile.getText().equals(""))
					return;
				if (txtDirec.getText().equals(""))
					return;

				textArea.setText("");
				ReadWAV rw = new ReadWAV();
				LCS LA = new LCS();
				File folder = new File(txtDirec.getText());
				File[] listOfFiles = folder.listFiles();

				for (File file : listOfFiles) {
					if (file.isFile()) {
						try {
							rw.read(direc, "Database\\first.txt");
							rw.read(file.getAbsolutePath(), "Database\\second.txt");

							int lcs = LA.extractLCS("Database\\first.txt", "Database\\second.txt");
							hm.put(file.getAbsolutePath(), lcs);
						} catch (IOException e1) {
							e1.printStackTrace();
						}
						String toWrite = "";
						Set<?> set = hm.entrySet();
						Iterator<?> i = set.iterator();
						while (i.hasNext()) {
							Map.Entry<?, ?> me = (Map.Entry<?, ?>) i.next();
							toWrite += me.getKey() + " : " + me.getValue() + "\n\n";

						}
						toWrite += "Predicted Audio: \n-----------------------------------------------------------------------\n";
						Map.Entry<String, Integer> maxEntry = null;

						for (Map.Entry<String, Integer> entry : hm.entrySet()) {
							if (maxEntry == null || entry.getValue().compareTo(maxEntry.getValue()) > 0) {
								maxEntry = entry;
							}
						}
						toWrite += maxEntry.getKey();
						textArea.setText(toWrite);
					}
				}

			}
		});

		metaFrame.getContentPane().add(btnSearch);
		metaFrame.getContentPane().add(btnBack);
		metaFrame.setVisible(true);
	}



}
