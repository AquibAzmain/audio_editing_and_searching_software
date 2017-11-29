package GUI;

import java.awt.Component;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JTextField;
import javax.swing.filechooser.FileNameExtensionFilter;

public class Merge {
	private JFrame mergeFrame;
	private JTextField txtFile1, txtFile2;
	private JTextField txtMergeName;
	private JTextField txtSave;

	
	private String direc1=null, direc2=null;
	String userhome = System.getProperty("user.home");

	public static void main(String[] args) {
		Merge m = new Merge();
		m.merge();
	}

	public void merge() {
		mergeFrame = new JFrame();
		mergeFrame.setBounds(100, 100, 450, 462);
		mergeFrame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		mergeFrame.getContentPane().setLayout(null);
		
		JLabel lblChoose1 = new JLabel("Choose File 1:");
		lblChoose1.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChoose1.setBounds(24, 33, 161, 29);
		mergeFrame.getContentPane().add(lblChoose1);
		
		txtFile1 = new JTextField();
		txtFile1.setEditable(false);
		txtFile1.setBounds(24, 67, 275, 20);
		mergeFrame.getContentPane().add(txtFile1);
		txtFile1.setColumns(10);
		
		JButton btnBrowse1 = new JButton("Browse");
		btnBrowse1.setActionCommand("1");
		btnBrowse1.setBounds(309, 66, 89, 23);
		mergeFrame.getContentPane().add(btnBrowse1);
		
		JButton btnBrowse2 = new JButton("Browse");
		btnBrowse2.setBounds(309, 150, 89, 23);
		mergeFrame.getContentPane().add(btnBrowse2);
		
		txtFile2 = new JTextField();
		txtFile2.setEditable(false);
		txtFile2.setColumns(10);
		txtFile2.setBounds(24, 151, 275, 20);
		mergeFrame.getContentPane().add(txtFile2);
		
		JLabel lblChoose2 = new JLabel("Choose File 2:");
		lblChoose2.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblChoose2.setBounds(24, 117, 161, 29);
		mergeFrame.getContentPane().add(lblChoose2);
		JLabel lblMergeName = new JLabel("Enter Merged File Name:");
		lblMergeName.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMergeName.setBounds(24, 192, 257, 29);
		mergeFrame.getContentPane().add(lblMergeName);
		
		txtMergeName = new JTextField();
		txtMergeName.setColumns(10);
		txtMergeName.setBounds(24, 227, 275, 20);
		mergeFrame.getContentPane().add(txtMergeName);
		
		JLabel lblMergeSave = new JLabel("Enter Save Directory");
		lblMergeSave.setFont(new Font("Tahoma", Font.PLAIN, 16));
		lblMergeSave.setBounds(24, 258, 161, 29);
		mergeFrame.getContentPane().add(lblMergeSave);
		
		txtSave = new JTextField();
		txtSave.setEditable(false);
		txtSave.setColumns(10);
		txtSave.setBounds(24, 292, 275, 20);
		mergeFrame.getContentPane().add(txtSave);
		
		JButton btnBrowseSave = new JButton("Browse");
		btnBrowseSave.setBounds(309, 291, 89, 23);
		mergeFrame.getContentPane().add(btnBrowseSave);
		btnBrowseSave.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				JFileChooser fc = new JFileChooser(userhome +"\\Desktop");
								
				fc.setDialogTitle("Choose Save Location");
				fc.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
				
				if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
					txtSave.setText(fc.getSelectedFile().getAbsolutePath());					
				}

			}
		});
		JButton btnBack = new JButton("Back");
		btnBack.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnBack.setBounds(250, 335, 100, 35);
		btnBack.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Intro intro = new Intro();
				intro.start();
				mergeFrame.dispose();
			}
		});
		
		JButton btnMerge = new JButton("Merge");
		btnMerge.setFont(new Font("Tahoma", Font.PLAIN, 17));
		btnMerge.setBounds(100, 335, 100, 35);
		mergeFrame.getContentPane().add(btnMerge);
		mergeFrame.getContentPane().add(btnBack);
		
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent b) {
				if(txtFile1.getText().equals(""))return;
				if(txtFile2.getText().equals(""))return;
				if(txtMergeName.getText().equals(""))return;
				if(txtSave.getText().equals(""))return;
				
				BufferedOutputStream bw = null;
				try {
					FileInputStream in1 = new FileInputStream(direc1);
					FileInputStream in2 = new FileInputStream(direc2);
					FileOutputStream out = new FileOutputStream(txtSave.getText()+"/"+txtMergeName.getText()+".mp3");
					//FileOutputStream out = new FileOutputStream(userhome + txtSave.getText() + txtMergeName.getText()+".mp3");
					BufferedInputStream br1 = new BufferedInputStream(in1);
					BufferedInputStream br2 = new BufferedInputStream(in2);
					bw= new BufferedOutputStream(out);

					int i;
					while ((i = br1.read()) != -1) {
						bw.write(i);
						// System.out.println(s);
					}
					while ((i = br2.read()) != -1) {
						bw.write(i);
					}
					br1.close();
					br2.close();
					bw.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				} finally{
					try {
						bw.close();
					} catch (IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		});
		
		Browser br = new Browser();
		btnBrowse1.addActionListener(br);
		btnBrowse2.addActionListener(br);	
		
		
		mergeFrame.setVisible(true);
	}
	
	class Browser implements ActionListener{

		public void actionPerformed(ActionEvent e) {
			JFileChooser fc = new JFileChooser(userhome +"\\Desktop");
			
			FileNameExtensionFilter filter = new FileNameExtensionFilter("MP3 files", "mp3");
			fc.setFileFilter(filter);
			fc.setAcceptAllFileFilterUsed(false);
			
			fc.setDialogTitle("Choose MP3");
			fc.setFileSelectionMode(JFileChooser.FILES_ONLY);
			
			if (fc.showOpenDialog((Component) e.getSource()) == JFileChooser.APPROVE_OPTION) {
				File one = fc.getSelectedFile();
				if(e.getActionCommand().equals("1")) {
					txtFile1.setText(fc.getName(one));
					direc1=one.getAbsolutePath();
				}
				else {
					txtFile2.setText(fc.getName(one));
					direc2=one.getAbsolutePath();
				}
			}
	
		}
		
	}

}
