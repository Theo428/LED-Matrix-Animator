package main;

import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Toolkit;
import java.awt.geom.AffineTransform;
import java.util.ArrayList;

public class Map
{

	public static final int IMAGE_WIDTH = 8;
	public static final int IMAGE_HEIGHT = 8;
	
	private static final Image shipImg = Toolkit.getDefaultToolkit().getImage("rooms/ship.png");
	
	private static final Image[][] mapRooms = {
			{getImage("rooms/Blank_Room")}, 	//Empty Room
			
			//Single Door Rooms
			{getImage("rooms/Black_Up.png"), getImage("rooms/Blue_Up.png")}, 			//Up	1
			{getImage("rooms/Black_Right.png"), getImage("rooms/Blue_Right.png")},		//Right	2
			{getImage("rooms/Black_Down.png"), getImage("rooms/Blue_Down.png")},		//Down	3
			{getImage("rooms/Black_Left.png"), getImage("rooms/Blue_Left.png")},		//Left 	4
			
			//Double Door Rooms
			{getImage("rooms/Black_Up_Right.png"), getImage("rooms/Blue_Up_Right.png")},			//Up, Right		5
	  		{getImage("rooms/Black_Down_Right.png"), getImage("rooms/Blue_Down_Right.png")},		//Right, Down	6
		    {getImage("rooms/Black_Down_Left.png"), getImage("rooms/Blue_Down_Left.png")},			//Down, Left	7
		    {getImage("rooms/Black_Up_Left.png"), getImage("rooms/Blue_Up_Left.png")},				//Left, Up		8
    		{getImage("rooms/Black_Up_Down.png"), getImage("rooms/Blue_Up_Down.png")},				//Up, Down		9
    		{getImage("rooms/Black_Left_Right.png"), getImage("rooms/Blue_Left_Right.png")},		//Right, Left 	10
	
			//Three Door Rooms
			{getImage("rooms/Black_Up_Down_Right.png"), getImage("rooms/Blue_Up_Down_Right.png")},			//Up, Right, Down	11
			{getImage("rooms/Black_Down_Right_Left.png"), getImage("rooms/Blue_Down_Right_Left.png")},		//Right, Down, Left	12
			{getImage("rooms/Black_Up_Down_Left.png"), getImage("rooms/Blue_Up_Down_Left.png")},			//Down. Left, Up	13
			{getImage("rooms/Black_Up_Left_Right.png"), getImage("rooms/Blue_Up_Left_Right.png")},			//Left, Up, Right	14

			//Four Door Rooms
			{getImage("rooms/Black_Up_Down_Left_Right.png"), getImage("rooms/Blue_Up_Down_Left_Right.png")}};		//Up, Right, Down, Left 15

	private AffineTransform transform = new AffineTransform();;
	
	private ArrayList<ArrayList<String>> map;
	
	public Map(ArrayList<ArrayList<String>> map) 
	{
		this.map = map;
	}

	public void render(Graphics graphics) 
	{
		double roomWidth = Game.WIDTH/map.size();
		double roomHeight = Game.HEIGHT/map.get(0).size();
		
		for(int i = 0; i < map.size(); i++)
		{
			for(int j = 0; j < map.get(i).size(); j++)
			{
				String roomValue = map.get(i).get(j);
				int roomType = Integer.parseInt(roomValue.split(" ")[0]);
				int roomVersion = Integer.parseInt(roomValue.split(" ")[1]);
				
				Graphics2D graphics2d = (Graphics2D)graphics;
				
				transform.setToTranslation((roomWidth * i), (roomHeight * j));
				transform.scale(roomWidth/IMAGE_WIDTH, roomHeight/IMAGE_HEIGHT);
				
				graphics2d.drawImage(mapRooms[roomType][roomVersion], transform, null);
			}
		}
	}
	
	private static Image getImage(String url)
	{
		return Toolkit.getDefaultToolkit().getImage(url);
	}

}
