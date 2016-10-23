package mylifegame;

import java.awt.Color;
import java.awt.Graphics;
import javax.swing.JPanel;

public class World extends JPanel implements Runnable
{
	private final int rows;
	private final int columns;
	private final CellStatus[][] generation1;
	private final CellStatus[][] generation2;
	private CellStatus[][] currentGeneration;//当前
	private CellStatus[][] nextGeneration;//下一时刻
	private volatile boolean isChanging = false; 
	private int size=5;
	private Color foreground=Color.blue;
	private Color background=Color.GRAY;
	private boolean isDrawBackground=true;
	private int time=100;
	private int model=1;
	
	
	public World(int rows, int columns)
	{
		this.rows = rows;
		this.columns = columns;
		generation1 = new CellStatus[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				generation1[i][j] = CellStatus.Dead;
			}
		}
		
		generation2 = new CellStatus[rows][columns];
		for(int i = 0; i < rows; i++)
		{
			for(int j = 0; j < columns; j++)
			{
				generation2[i][j] = CellStatus.Dead;
			}
		}
		
		currentGeneration = generation1;
		nextGeneration = generation2;
	}
	
	@Override
	public void run()
	{
		while(true)
		{
			synchronized(this)
			{
				while(isChanging)
				{
					try 
					{
						this.wait();
					} catch (InterruptedException e) 
					{
						e.printStackTrace();
					}
				}
				
				repaint();
				sleep(1);
				
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < columns; j++)
					{
						evolve(i, j);
					}
				}
			
				CellStatus[][] temp = null;
				temp = currentGeneration;
				currentGeneration = nextGeneration;
				nextGeneration = temp;
				
				for(int i = 0; i < rows; i++)
				{
					for(int j = 0; j < columns; j++)
					{
						nextGeneration[i][j] = CellStatus.Dead;
					}
				}
			}
		}
	}
	
	@Override
	public void paintComponent(Graphics g) {
		super.paintComponent(g);

		for (int i = 0; i < rows; i++) {
			for (int j = 0; j < columns; j++) {
				if (currentGeneration[i][j] == CellStatus.Active) {
					g.setColor(foreground);
					g.fillRect(j * size, i * size, size, size);
				} else {
					if (isDrawBackground) {
						g.setColor(background);
						g.drawRect(j * size, i * size, size, size); // 画背景格子
					}
				}
			}
		}
		
//		if(model==1){
//			this.addMouseMotionListener(new MouseMotionListener() {
//				
//				@Override
//				public void mouseMoved(MouseEvent e) {
//					// TODO Auto-generated method stub
//					
//				}
//				
//				@Override
//				public void mouseDragged(MouseEvent e) {
//					// TODO Auto-generated method stub
//					nextGeneration[e.getX()][e.getY()]=CellStatus.Active;
//				}
//			});
//		}
	}

	public void setArrow()// 箭头
	{
		int[][] arrow = new int[80][80];
		arrow[9+40][14+30]=1;
		arrow[10+40][13+30]=1;
		for (int i = 12; i < 42; i++) {
			arrow[11+40][i+30]=1;
		}
		for (int i = 11; i < 42; i++) {
			arrow[12+40][i+30]=1;
		}
		for (int i = 12; i < 42; i++) {
			arrow[13+40][i+30]=1;
		}
		arrow[14+40][13+30]=1;
		arrow[15+40][14+30]=1;
		setShape(arrow);
	}
	
	public void setSquare()//方块
	{
		int[][] square=new int[90][90];
		for (int i = 10; i < 40; i++) {
			for (int j = 10; j <40; j++) {
				square[i+40][j+30]=1;
			}
		}
		setShape(square);
	}	
	
	public void setOneLine() {
		int[][]oneLine=new int[90][90];
		for (int i = 4; i < 7; i++) {
			oneLine[50][i+20]=1;
		}
		for(int i=17;i<22;i++){
			oneLine[50][i+20]=1;
		}
		for(int i=26;i<32;i++){
			oneLine[50][i+20]=1;
		}
		for(int i=37;i<44;i++){
			oneLine[50][i+20]=1;
		}
		for(int i=54;i<64;i++){
			oneLine[50][i+20]=1;
		}
		setShape(oneLine);
	}
	
	public void setOneLine2() {
		int[][]OneLine2=new int[90][90];
		for (int i = 4; i < 12; i++) {
			OneLine2[50][i+20]=1;
		}
		for(int i=13;i<18;i++){
			OneLine2[50][i+20]=1;
		}
		for(int i=21;i<24;i++){
			OneLine2[50][i+20]=1;
		}
		for(int i=30;i<37;i++){
			OneLine2[50][i+20]=1;
		}
		for(int i=38;i<43;i++){
			OneLine2[50][i+20]=1;
		}
		setShape(OneLine2);
	}
	
	public void setCustom() {
		model=1;
	}
	
	/**
	 随机产生
	 */
	public void setRandom() {
		int row=(int) (Math.random()*(rows-10)+10);//10--rows
		int col=(int) (Math.random()*(columns-10)+10);//10--columns
		int [][]random=new int[row][col];
		for (int i = 0; i < row; i++) {
			for (int j = 0; j < col; j++) {
				random[i][j]=Math.random()<0.35?1:0;
				//System.out.print(random[i][j]+" ");
			}
			//System.out.println();
		}
		setShape(random);
	}
	
	private void setShape(int[][] shape)
	{
		isChanging = true;
		
		int arrowsRows = shape.length;
		int arrowsColumns = shape[0].length;
		
		int minimumRows = (arrowsRows < rows) ? arrowsRows: rows;
		int minimumColumns = (arrowsColumns < columns) ? arrowsColumns : columns;
		
		synchronized(this)
		{
			for(int i = 0; i < rows; i++)
			{
				for(int j = 0; j < columns; j++)
				{
					currentGeneration[i][j] = CellStatus.Dead;
				}
			}
			
			for(int i = 0; i < minimumRows; i++)
			{
				for(int j = 0; j < minimumColumns; j++)
				{
					if(shape[i][j] == 1)
					{
						//System.out.println("x="+i+"  y="+j);
						currentGeneration[i][j] = CellStatus.Active;
					}
				}
			}
			
			isChanging = false;
			this.notifyAll();
		}		
	}
	
	public void setColor(Color foreground,Color background) {
		this.foreground=foreground;
		this.background=background;
	}
	
	public void setIsDrawBackground(boolean isDrawBackground) {
		this.isDrawBackground=isDrawBackground;
	}
	
	public void setTime(int time) {
		this.time=time;
	}
	
	private void evolve(int x, int y)//计算周围细胞存活数量并改变状态
	{
		int activeSurroundingCell = 0;
		for (int i = x-1; i <=x+1; i++) {
			for (int j = y-1; j <= y+1; j++) {
				if(i==x&&j==y){
					continue;
				}
				activeSurroundingCell += isAlive(i, j);
			}
		}
//		activeSurroundingCell += isAlive(x - 1, y - 1);
//		activeSurroundingCell += isAlive(x, y - 1);
//		activeSurroundingCell += isAlive(x + 1, y - 1);
//		activeSurroundingCell += isAlive(x + 1, y);
//		activeSurroundingCell += isAlive(x + 1, y + 1);
//		activeSurroundingCell += isAlive(x, y + 1);
//		activeSurroundingCell += isAlive(x - 1, y + 1);
//		activeSurroundingCell += isAlive(x - 1, y);

		switch (activeSurroundingCell) {
		case 3:
			nextGeneration[x][y] = CellStatus.Active;
			break;
		case 2:
			nextGeneration[x][y] = currentGeneration[x][y];
			break;
		default:
			nextGeneration[x][y] = CellStatus.Dead;
			break;
		}
	}
	
	private boolean isValidCell(int x, int y)
	{
		if((x >= 0) && (x < rows) && (y >= 0) && (y < columns))
		{
			return true;
		}
		return false;
	}
	
	private int isAlive(int x,int y){
		if(isValidCell(x , y) && (currentGeneration[x][y] == CellStatus.Active))
		{
			return 1;
		}
		return 0;	
	}
	
	private void sleep(int x)
	{
		try 
		{
			Thread.sleep(time * x);
		} catch (InterruptedException e) 
		{
			e.printStackTrace();
		}
	}
	
	static enum CellStatus
	{
		Active,
		Dead;
	}
}