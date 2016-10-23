package mylifegame;

import java.awt.Color;
import java.awt.Font;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 * @author Administrator
 *
 */
public class Help extends JFrame{
		private JTextArea help;
		private JScrollPane jScrollPane;
		private JButton OK=new JButton("确定");
		private String maessage="\n\t生命游戏是英国数学家约翰·何顿·康威在1970年发明的细胞自动机，"
				+ "它包括一个二维矩形世界，这个世界中的每个方格居住着一个活着的或死亡的细胞。"
				+ "一个细胞在下一个时刻生死取决于相邻八个方格中活着的或死了的细胞的数量。"
				+ "如果相邻方格活着的细胞数量过多，这个细胞会因为资源匮乏而在下一个时刻死去；"
				+ "相反，如果周围活细胞过少，这个细胞会因太孤单而死去。\n"
				+ "\t你可以通过菜单栏的Options项选择游戏模式，分别有Arrow,Squre,OneLine,OneLine2,Rnadom五种模式可供选择"
				+ "也可以通过";
		public Help() {
			// TODO Auto-generated constructor stub
			
		    help=new JTextArea(6,10);
		    help.setTabSize(2);
		    help.setText(maessage);
		    help.setFont(new Font("楷体", Font.BOLD, 16));
			help.setLineWrap(true);
			help.setWrapStyleWord(true);
			help.setEditable(false);
			
			
			OK.addActionListener(new ActionListener() {
				public void actionPerformed(ActionEvent arg0) {
					Help.this.dispose();
				}
			});
			OK.setFont(new Font("楷体", Font.PLAIN, 15));	
			OK.setFocusable(false);
			OK.setBorder(BorderFactory.createRaisedSoftBevelBorder());
			//OK.setBorderPainted(false);
			OK.setBounds(275, 241, 50,20);
			OK.setForeground(Color.darkGray);
			OK.setBackground(Color.lightGray);
			
			jScrollPane=new JScrollPane(help);
			jScrollPane.setBounds(0, 0, 590, 240);
			jScrollPane.setBorder(BorderFactory.createLoweredSoftBevelBorder());
			jScrollPane.setFocusable(false);
			
			getContentPane().setLayout(null);
			setTitle("帮助");
			getContentPane().add(jScrollPane);
			getContentPane().add(OK);
			setSize(600, 250);
			setLocationRelativeTo(null);
			setResizable(false);
			setVisible(true);
		}
	}
