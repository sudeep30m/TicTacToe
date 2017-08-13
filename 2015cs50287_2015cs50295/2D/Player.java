import java.util.Vector;

public class Player 
{
	int id;
	Vector<Position> positions;
	
	
	public Player(int id)
	{
		this.id=id;	
		positions=new Vector<Position>();
	}
	
	public void addPosition(Position p)
	{
		this.positions.add(p);
	}

	public Position getPosition(int i)
	{
		return positions.get(i);
	}
	
}






class Position
{
	int r;
	int c;
	
	public Position(int r,int c)
	{
		this.r=r;
		this.c=c;
	}
	
	public void set(int r,int c)
	{
		this.r=r;
		this.c=c;
	}
}