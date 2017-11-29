package GUI;

import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.ButtonGroup;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JPanel;


public class Intro {
	private JFrame intro;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	JPanel panelSearch = null, panelEdit = null;

	public static void main(String[] args) {
		Intro intro = new Intro();
		intro.start();
	}

	public void start() {
		intro = new JFrame();
		intro.setBounds(100, 100, 450, 350);
		intro.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		intro.getContentPane().setLayout(null);
		intro.setTitle("A software for Audio Editing and Searching");

		JButton btnEdit = new JButton("Edit");
		btnEdit.setActionCommand("E");
		buttonGroup.add(btnEdit);
		btnEdit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnEdit.setBounds(151, 27, 130, 36);
		intro.getContentPane().add(btnEdit);
		
		JButton btnSearch = new JButton("Search");
		buttonGroup.add(btnSearch);
		btnSearch.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnSearch.setBounds(151, 74, 130, 36);
		intro.getContentPane().add(btnSearch);
		
		JButton btnExit = new JButton("Exit");
		buttonGroup.add(btnExit);
		btnExit.setFont(new Font("Tahoma", Font.BOLD, 18));
		btnExit.setBounds(151, 230, 130, 36);
		intro.getContentPane().add(btnExit);
		
		btnExit.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				intro.setEnabled(false);
				System.exit(0);
			}
		});
		
		MainAction ac = new MainAction();
		btnEdit.addActionListener(ac);
		btnSearch.addActionListener(ac);
		
		panelSearch = new JPanel();
		panelSearch.setBounds(10, 121, 414, 117);
		intro.getContentPane().add(panelSearch);
		panelSearch.setLayout(null);
		panelSearch.setVisible(false);
		
		JButton btnMeta = new JButton("Via Metadata");
		btnMeta.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchMeta sm = new SearchMeta();
				sm.search();
				intro.dispose();
			}
		});
		btnMeta.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMeta.setBounds(10, 42, 156, 46);
		panelSearch.add(btnMeta);
		
		
	
		JButton btnByte = new JButton("Via Bytes");
		btnByte.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnByte.setBounds(248, 42, 156, 46);
		panelSearch.add(btnByte);
		
		btnByte.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				SearchByte sb = new SearchByte();
				sb.search();
				intro.dispose();
			}
		});
		
		panelEdit = new JPanel();
		panelEdit.setLayout(null);
		panelEdit.setBounds(10, 121, 414, 117);
		intro.getContentPane().add(panelEdit);
		panelEdit.setVisible(false);
		
		JButton btnMerge = new JButton("Merge");
		btnMerge.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnMerge.setBounds(70, 42, 90, 46);
		panelEdit.add(btnMerge);
		
		btnMerge.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Merge m = new Merge();
				m.merge();
				intro.dispose();
			}
		});
		
		JButton btnTrim = new JButton("Trim");
		btnTrim.setFont(new Font("Tahoma", Font.PLAIN, 18));
		btnTrim.setBounds(248, 42, 90, 46);
		panelEdit.add(btnTrim);
		
		btnTrim.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Trim t = new Trim();
				t.trim();
				intro.dispose();
			}
		});
		
		
		intro.setVisible(true);
	}
	
	class MainAction implements ActionListener{
		public void actionPerformed(ActionEvent b) {
			if(b.getActionCommand().equals("E")){
				panelEdit.setVisible(true);
				panelSearch.setVisible(false);
			}
			else{
				panelEdit.setVisible(false);
				panelSearch.setVisible(true);
			}
		}
		
	}

}
