package FlappyBird;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Rectangle;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.util.ArrayList;
import java.util.Random;
import javax.swing.JFrame;
import javax.swing.Timer;





public class FlappyBird extends JFrame implements ActionListener ,MouseListener{
	boolean running,gamestart;
	static  FlappyBird flappybird;
	static int HEIGHT=500,WIDTH=965,WALLWIDTH=55,WALLHEIGHT,COUNTER=0,BIRDHEIGHT=40,BIRDWIDTH=50,APPLEY,APPLEWIDTH=55,APPLEHEIGHT=50;
	Demo dm;
	Timer tm;
	final int space =200;
	ArrayList<Rectangle> wall,applelist;
	Rectangle bird,apple;
	int ymotion,count,score,ym,bonus;
	int style = Font.BOLD;
	Image   birdimg = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/bird.png");
	Image   wallimg = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/wall.png");
	Image   boardimg = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/scoreboard.jpg"); 
	Image   playimg = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/back2.png");
	Image   appleimg = Toolkit.getDefaultToolkit().createImage("/G:/java/Flappy_Bird/bin/FlappyBird/apple.gif");
	
	
	
    public FlappyBird()
	{
	
    	gamestart=false;
    	running=false;
		dm=new Demo();
		tm=new Timer(20,this);
		JFrame jf=new JFrame("Flappy Bird");
		jf.setBounds(0,0,WIDTH,HEIGHT+100);
		jf.setVisible(true);
		jf.setResizable(false);
		jf.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		jf.add(dm);
		jf.addMouseListener(this);
		wall=new ArrayList<Rectangle>();
		applelist=new ArrayList<Rectangle>();
		bird=new Rectangle(WIDTH/2,100,BIRDWIDTH,BIRDHEIGHT);
		tm.start();
		
	}
	
	private void addWall(boolean b) {
		Random r=new Random();
		WALLHEIGHT=5+r.nextInt(245);
		wall.add(new Rectangle(WIDTH+wall.size()*200,0,WALLWIDTH,WALLHEIGHT));
		wall.add(new Rectangle(WIDTH+(wall.size()-1)*200,WALLHEIGHT+space,WALLWIDTH,HEIGHT-WALLHEIGHT-space));
		
		
		APPLEY=5+r.nextInt(space-50)+WALLHEIGHT;
		applelist.add(new Rectangle(WIDTH+(wall.size()-2)*200,APPLEY,APPLEWIDTH,APPLEHEIGHT));
		
	}

	public static void main(String[] args) {
		
		flappybird =new FlappyBird();

	}


	

	private void update() {
		Rectangle ptr,ptr1;
		count++;
		if(count%2==0 && ymotion<10)
			ymotion+=2;                      // always try to going down.....
		
		//detect collision.......
		
		for(int i=0;i<wall.size();i++)
		{
			ptr=wall.get(i);
			if(ptr.intersects(bird)){
				running=false;
				
				break;
			}
		}
		
		if(bird.y<0 || bird.y+BIRDHEIGHT>HEIGHT)
		{
			running=false;
					}
		//calculate score.........
		
		for(int i=0;i<wall.size();i=i+2)
		{
			ptr=wall.get(i);
			if(ptr.x<bird.x && ptr.x+ptr.width>bird.x+bird.width && running){
				score++;
			}
			
		}
		for(int j=0;j<applelist.size();j++)
		{
			ptr1=applelist.get(j);
			if(bird.intersects(ptr1) && running){
				bonus=1;
				applelist.remove(ptr1);
				break;
			}
			else
				bonus=0;
			
			
		}
		score+=bonus;
		
		
		bird.y+=ymotion;
		
	}

	private void makeWall() {
		
		Rectangle ptr;
		for(int i=0;i<wall.size();i++)
		{
			ptr=wall.get(i);
			ptr.x-=5;
			if(ptr.x<-WALLWIDTH)
			{
				wall.remove(ptr);
			
			}
		}
		
		
		for(int i=0;i<applelist.size();i++)
		{
			ptr=applelist.get(i);
			ptr.x-=5;
			if(ptr.x<-APPLEWIDTH)
			{
				applelist.remove(ptr);
			
			}
		}
		
		
		if(wall.size()<20 && wall.size()%2==0)
			addWall(running);
			
	}
	
	private void jump() {
		
		if(!gamestart ){
			gamestart=true;
			running=true;
			
		}
		else if(!running)
		{
			running=true;
			
			wall.clear();
			applelist.clear();
			score=0;
			addWall(running);
			addWall(running);
			ymotion=0;
			bird.y=100;
		}
		else {
			if(ymotion>0)
				ymotion=0;
			
		   ymotion-=10;   //motion decrease....becomes negative.....
		 
		}
			
		
	}
	public void actionPerformed(ActionEvent arg0) {
		
		if(gamestart)
			update();
		if(running)
			makeWall();

		dm.repaint();
	}




	public void repaint(Graphics g) {
	
		if(gamestart && running){
			
			g.drawImage(playimg, 0, 0,965,600,null);
			
			g.setColor(Color.red.yellow);
			g.fillRect(0, HEIGHT-12, WIDTH, 10);
			
			g.drawImage(birdimg, bird.x,bird.y, bird.width, bird.height, null);
			
			g.setColor(Color.red.darker());
			g.setFont(new Font("Jokerman",1,70));
			g.drawString(String.valueOf(score), WIDTH/2,HEIGHT+65);
			
			
			for(Rectangle r:wall)
			{
				
				g.drawImage(wallimg, r.x, r.y, r.width,r.height,null);
				
			}
			for(Rectangle r:applelist)
			{
				g.drawImage(appleimg, r.x, r.y, r.width,r.height,null);
				
			}
		}
		else if(!running && gamestart)
		{
			
			

			//g.drawImage(playimg, 0, 0,965,600,null);
			
			
			g.setColor(Color.white);
			g.setFont(new Font ("Jokerman", style ,50));
			
			//g.setFont(new Font ("Serif", style , 50));
			g.drawString("SCORE :  ", 350,450);
			g.drawString(String.valueOf(score), 580,450);
		}
		/*else
		{

			g.setColor(Color.black.brighter());
			//g.setFont(new Font ("Jokerman", style , 85));
			//g.drawString("CLICK TO START", 100, HEIGHT+60);
			//g.drawImage(playimg, 0, 0,965,600,null);
		}*/
	}


	
	public void mouseClicked(MouseEvent e) {
		
		jump();
		
	}



	public void mouseEntered(MouseEvent arg0) {				
	}

	@Override
	public void mouseExited(MouseEvent arg0) {
		// TODO Auto-generated method stub
		
	}

	
	public void mousePressed(MouseEvent arg0) {	
				
	}

	public void mouseReleased(MouseEvent arg0) {
		
	}
	

}
