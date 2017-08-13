import java.util.Scanner;

public class TicTacToe2D 
{
	int board[][];
	private Player computer;
	private Player human;
	
	public static void main(String args[])
	{
		TicTacToe2D game =new TicTacToe2D("C");
		game.start();
	}
	
	public void start()
	{

		Scanner s =new Scanner(System.in);
		for(int i=1;i<=9;i++)
		{
			if((this.computer.id==3 && i%2==1) || (this.computer.id==5 && i%2==0))	
			{
				System.out.println("Computer's turn");
				System.out.println();
				if(playTurn(i)==1)
				{
					System.out.println("--------- COMPUTER WINS -------------");
					return;
				}
			}
			else
			{
				int r,c;
				System.out.print("Enter Position in row and column - ");
				r=s.nextInt();
				c=s.nextInt();
				System.out.println();
				go(new Position(r,c),human);
			}
			printBoard();
		}
		System.out.println("--------- TIE-------------");		
		s.close();
		
	}
	
	void printBoard()
	{
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
			{
				if(board[i][j]==2)
					System.out.print("|   ");
				else if(board[i][j]==3)
					System.out.print("| X ");
				else if(board[i][j]==5)
					System.out.print("| O ");
				if(j==2)
					System.out.print("|");

			}
			System.out.println();
			System.out.println();
		}
	}
	
	public void reset(String player)
	{
		board =new int[3][3];
		for(int i=0;i<3;i++)
		{
			for(int j=0;j<3;j++)
				board[i][j]=2;	
		}
		MagicSquare2D.generate(3);
		if(player.equals("C"))
		{	
			computer =new Player(3);
			human    =new Player(5);
		}
		else
		{
			computer= new Player(5);
			human   = new Player(3);
		}
	}
	
	public TicTacToe2D(String player)
	{
		reset(player);
	}	

	void go(Position p,Player player)
	{
		if(board[p.r][p.c]==2)
		{
			board[p.r][p.c]=player.id;
			player.addPosition(p);
		}
	}
	
	
	//returns 1 if computer wins else 0
	public int playTurn(int step)
	{
		Position p1,p2;
		switch(step)
		{
			case 1:
			case 2:
			case 3:
				go(make2(),computer);
				break;
			case 4:
			case 5:
			case 6:
			case 7:
			case 8:
			case 9:
				if(step==7)
				{
					System.out.println();
				}
				p1=checkWin(computer);
				p2=checkWin(human);
				if(p1!=null)
				{
					go(p1,computer);
					return 1;
				}
				else if(p2!=null)
					go(p2,computer);
				else
					go(make2(),computer);
				break;

		}
		return 0;	
	}
	
	Position checkWin(Player player)
	{
		for(int i=0;i<player.positions.size();i++)
		{
			for(int j=i+1;j<player.positions.size();j++)
			{
				Position p = check(player.getPosition(i),player.getPosition(j));
				if(p!=null)
					return p;
			}
		}
		return null;
	}
	
	Position check(Position i,Position j)
	{
		int sum =MagicSquare2D.MagicSquareConstant();
		int x=MagicSquare2D.findMagicNumber(i);
		int y=MagicSquare2D.findMagicNumber(j);
		Position p=MagicSquare2D.findPosition(sum-x-y);
		if(p!= null && board[p.r][p.c]==2)
			return p;
		return null;
		
	}

	
	Position make2()
	{
		if(board[1][1]==2)
			return new Position(1,1);
		else if(board[2][2]==2)
			return new Position(2,2);
		else if(board[2][0]==2)
			return new Position(2,0);
		else if(board[0][2]==2)
			return new Position(0,2);
		else if(board[0][1]==2)
			return new Position(0,1);
		else if(board[1][0]==2)
			return new Position(1,0);
		else if(board[1][2]==2)
			return new Position(1,2);
		else if(board[2][1]==2)
			return new Position(2,1);
		else 
			return new Position(0,0);	
	}
	

}