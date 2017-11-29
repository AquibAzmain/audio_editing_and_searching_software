package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

import org.apache.tika.exception.TikaException;
import org.xml.sax.SAXException;

public class SearchMeta {
	private JFrame metaFrame;
	private JTextField txtFile;
	private JTextField txtDirec;
	private String direc=null;
	private String userhome = System.getProperty("user.home");
	private File one = null;


	public static void main(String[] args) {
		SearchMeta sm = new SearchMeta();
		sm.search();
	}

	public void search() {
		metaFrame = new JFrame("Search By Metadata");
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
				JFileChooser fc = new JFileChooser(userhome +"\\Desktop");
				
				FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 files", "mp3");
				fc.setFileFilter(filter);
				fc.setAcceptAllFileFilterUsed(false);
				
				fc.setDialogTitle("Choose MP3");
				fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
				
				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					one = fc.getSelectedFile();
					txtFile.setText(fc.getName(one));
					direc=one.getAbsolutePath();
					
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
				JFileChooser fc = new JFileChooser(userhome +"\\Desktop");
								
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
		textArea.setBounds(28, 186, 376, 450);
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
				if(txtFile.getText().equals("")) return;
				if(txtDirec.getText().equals("")) return;
				
				textArea.setText("");
				GetMetadata T = new GetMetadata();
				boolean flag = true;
				String TargetFilePath = direc;
				// String b = T.getSampleRate("E:\\My songs\\Tumi boruna hole.mp3");
				// System.out.println(a+b);
				File folder = new File(txtDirec.getText());
				File[] listOfFiles = folder.listFiles();

				for (File file : listOfFiles) {
					if (file.isFile()) {

						// System.out.println(file.getAbsolutePath());
						// String c = T.getChannels(file.getAbsolutePath());
						// System.out.println(c);
						try {
							if ((T.getDuration(TargetFilePath)).equals(T.getDuration(file.getAbsolutePath()))
									&& (T.getChannels(TargetFilePath)).equals(T.getChannels(file.getAbsolutePath()))
									&& (T.getChannelType(TargetFilePath)).equals(T.getChannelType(file.getAbsolutePath()))
									&& (T.getSampleRate(TargetFilePath)).equals(T.getSampleRate(file.getAbsolutePath()))
									&& (T.getVersion(TargetFilePath)).equals(T.getVersion(file.getAbsolutePath()))) {
								flag = true;
							}

							else{
								flag = false;
							}
						} catch (IOException | SAXException | TikaException e1) {
							e1.printStackTrace();
						}	
						if (flag) {
							String toWrite="";
							toWrite += "Given file directory: "+ TargetFilePath + "\n";
							toWrite += "Matched with the following mp3 :\n";
							
							try {
								toWrite+= T.PrintDetails(file.getAbsolutePath());
							} catch (IOException | SAXException | TikaException e1) {
								e1.printStackTrace();
							}
							textArea.setText(toWrite);
							break;
						}
						//else if (flag == false)System.out.println("No match found");
						
					}
				}
				if (!flag){
					String out = "";
					out+="Given file directory: "+TargetFilePath+"\n";
					//T.PrintDetails(file.getAbsolutePath());
					out+="No match found\n";
					textArea.setText(out);
				}
			}
		});

		metaFrame.getContentPane().add(btnSearch);	
		metaFrame.getContentPane().add(btnBack);
		metaFrame.setVisible(true);
	}

}
