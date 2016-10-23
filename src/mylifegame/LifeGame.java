package mylifegame;

import java.awt.Color;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;






import javax.swing.JCheckBoxMenuItem;
import javax.swing.JColorChooser;
import javax.swing.JFrame;
import javax.swing.JMenu;
import javax.swing.JMenuBar;
import javax.swing.JMenuItem;
import javax.swing.JOptionPane;


public class LifeGame extends JFrame
{
	private final World world;
	static JMenuItem background;
	static JCheckBoxMenuItem DrawBackground=new JCheckBoxMenuItem("DrawBackground");
	private Color foregroundColor = Color.BLUE;
	private Color backgroundColor = Color.GRAY;
	
	public LifeGame(int rows, int columns)
	{
		world = new World(rows, columns);
		new Thread(world).start();
		add(world);
	}
	
    public static void main(String[] args) 
    {
    	LifeGame frame = new LifeGame(800, 800);
    	
        JMenuBar menu = new JMenuBar();
        frame.setJMenuBar(menu);
        
        JMenu options = new JMenu("Options");
        menu.add(options);
        
        JMenuItem arrow = options.add("Arrow");
        arrow.addActionListener(frame.new OptionsActionListener());
        
        JMenuItem square = options.add("Square");
        square.addActionListener(frame.new OptionsActionListener());   
        
        JMenuItem oneLine = options.add("OneLine");
        oneLine.addActionListener(frame.new OptionsActionListener()); 
        
        JMenuItem OneLine2 = options.add("OneLine2");
        OneLine2.addActionListener(frame.new OptionsActionListener()); 
        
        JMenuItem random = options.add("Random");
        random.addActionListener(frame.new OptionsActionListener());    
        
            
        
        JMenu help = new JMenu("Help");  
        JMenuItem helpItem = help.add("Help");
        helpItem.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent e) {
				// TODO Auto-generated method stub
				new Help();
			}
		});
        menu.add(help);
        
        JMenu setting=new JMenu("Setting");
        JMenuItem foreground=setting.add("Foreground");
        background=setting.add("Background"); 
        DrawBackground.setState(true);
        JMenuItem time=setting.add("Time");
        
        setting.add(DrawBackground);
        
        foreground.addActionListener(frame.new SettingActionListener());
        background.addActionListener(frame.new SettingActionListener());
        time.addActionListener(frame.new SettingActionListener());
        DrawBackground.addActionListener(frame.new SettingActionListener());
        
        menu.add(setting);
        
        
        
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setSize(1007, 859);
        frame.setTitle("Game of Life--c and w");
        frame.setVisible(true);
        frame.setResizable(false);
    }	
    
    class OptionsActionListener implements ActionListener
    {
    	public void actionPerformed(ActionEvent e) 
    	{		
    		if(e.getActionCommand().equals("Arrow")) {
				world.setArrow();
    		System.out.println("Arrow");
			}
    		else if (e.getActionCommand().equals("Square")) {
				world.setSquare();
				System.out.println("Square");
			}
    		else if(e.getActionCommand().equals("OneLine")){
    			world.setOneLine();
        		System.out.println("oneline");
			}
    		else if(e.getActionCommand().equals("OneLine2")){
    			world.setOneLine2();
    			System.out.println("OneLine2");
    		}
    		else if (e.getActionCommand().equals("Random")) {
    			world.setRandom();
        		System.out.println("random");
			}
    		
    	}
    }
    
    class SettingActionListener implements ActionListener{

		/* (non-Javadoc)
		 * @see java.awt.event.ActionListener#actionPerformed(java.awt.event.ActionEvent)
		 */
		@Override
		public void actionPerformed(ActionEvent e) {
			// TODO Auto-generated method stub
			if(e.getActionCommand().equals("Foreground")){
				
				foregroundColor=JColorChooser.showDialog(null, "请选择细胞的颜色", foregroundColor);
				world.setColor(foregroundColor, backgroundColor);
				System.out.println("Foreground");
			} else if (e.getActionCommand().equals("Background")) {

				backgroundColor = JColorChooser.showDialog(null, "请选择背景格子的颜色",
						backgroundColor);
				world.setColor(foregroundColor, backgroundColor);
				System.out.println("background");
			} else if (e.getActionCommand().equals("DrawBackground")) {
				world.setIsDrawBackground(DrawBackground.getState());
				background.setEnabled(DrawBackground.getState());
			} else if (e.getActionCommand().equals("Time")) {
				try {
					int timeer = Integer.parseInt(JOptionPane
							.showInputDialog("请输入你期待的刷新频率(ms)"));
					if (timeer > 0) {
						world.setTime(timeer);
						System.out.println("Time");
					} else {
						JOptionPane.showMessageDialog(null, "请输入大于0的数据");
					}
				} catch (Exception e2) {
					JOptionPane.showMessageDialog(null, "输入格式有误");
					e2.printStackTrace();
					// TODO: handle exception
				}

			}
		}

	}
//    
//    class SquareActionListener implements ActionListener
//    {
//    	public void actionPerformed(ActionEvent e) 
//    	{
//    		world.setSquare();
//    		System.out.println("Square");
//    	}
//    }  
//    
//    class RandomActionListener implements ActionListener
//    {
//    	public void actionPerformed(ActionEvent e) 
//    	{
//    		world.setRandom();
//    		System.out.println("random");
//    	}
//    }
//    
//    class OneLineActionListener implements ActionListener
//    {
//    	public void actionPerformed(ActionEvent e) 
//    	{
//    		world.setOneLine();
//    		System.out.println("oneline");
//    	}
//    }
}
