
public class MagicSquare2D 
{
	private static int magicSquare[][];
	private static int order;
	
	public static void main(String args[])
	{
		//System.out.println(-1%3);
		MagicSquare2D.generate(3);
		MagicSquare2D.print();
	}
	
	public static void generate(int n)
	{
		order=n;
		magicSquare = new int[n][n];
		for(int i=0;i<n;i++)
		{
			for(int j=0;j<n;j++)
				magicSquare[i][j]=-1;	
		}
		if(n%2==1)
			generateOddMagicSquare();
		
	}
	
	public static int MagicSquareConstant()
	{
		return order*(order*order +1)/2;
	}

	public static Position findPosition(int m)
	{
		if(m<1 || m>9)
			return null;
		for(int i=0;i<order;i++)
		{
			for(int j=0;j<order;j++)
			{
				if(magicSquare[i][j]==m)
					return new Position(i,j);
			}
		}
		return null;
	}
	
	private static void generateOddMagicSquare() 
	{
		int r=0,c=order/2;
		int start=1;
		while(start<=order*order)
		{
			magicSquare[r][c]=start;
			if(magicSquare[modulo(r-1)][modulo(c+1)]!=-1)
				r=modulo(r+1);
			else
			{
				r=modulo(r-1);
				c=modulo(c+1);
			}		
			start++;	
		}
	}
	
	private static int modulo(int x)
	{
		if(x<0)
			return (x+order)%order;
		else
			return x%order;
	}
	
	public static int findMagicNumber(Position p)
	{
		return magicSquare[p.r][p.c];
	}
	
	public static void print()
	{
		for(int i=0;i<order;i++)
		{
			for(int j=0;j<order;j++)
				System.out.print(magicSquare[i][j] + "  ");
			System.out.println();
		}
		
	}
}
