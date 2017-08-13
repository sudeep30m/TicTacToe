/*
 * Class containing methods to access magic cube and various methods fot Tic Tac Toe use
 */
import java.util.Vector;

public class MagicCube3D {
	private int magicCube[][][]; // To store magic cube numbers
	private int order; // Size of magic cube
	private Vector<Triplet> nonCollinearExceptions; // exceptions sum is 42 but
													// non-collinear
	private Vector<Triplet> collinearExceptions; // exceptions sum is not 42 but
													// collinear

	public static void main(String args[]) {
		MagicCube3D magicCube = new MagicCube3D(3);
		magicCube.printCube();
		magicCube.printExceptions();
	}
	
	// Constructor taking size as argument
	public MagicCube3D(int n) {
		generate(n);
		generateExceptions();
	}

	// To initialize and store magic cube in class variable magicCube
	private void generate(int n) {
		order = n;
		magicCube = new int[n][n][n];
		for (int i = 0; i < n; i++) {
			for (int j = 0; j < n; j++) {
				for (int k = 0; k < n; k++)
					magicCube[i][j][k] = -1;
			}
		}
		if (n % 2 == 1)
			generateOddmagicCube();
	}
	
	public int magicCubeConstant() {
		return order * ((int) Math.pow(order, 3) + 1) / 2;
	}

	// Returns 3d 1 in form of Position3D class of input magic number
	public Position3D getPosition(int m) {
		if (m < 1 || m > Math.pow(order, 3))
			return null;
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				for (int k = 0; k < order; k++) {
					if (magicCube[i][j][k] == m)
						return new Position3D(i, j, k);
				}

			}
		}
		return null;
	}

	private void generateOddmagicCube() {
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++) {
				for (int k = 0; k < order; k++) {
					int a, b, c;
					a = (i - j + k + 2 * order) % order;
					b = (i - j - k - 1 + 2 * order) % order;
					c = (i + j + k + 1 + order) % order;
					magicCube[i][j][k] = order * order * a + order * b + c + 1;
				}
			}
		}
	}

	// Given a Cartesian position returns magic number
	public int getMagicNumber(Position3D p) {
		return magicCube[p.i][p.j][p.k];
	}

	private void printCube() {
		System.out.println("------------------- TOP ----------------------\n");

		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][j][0] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- MIDDLE-TOP ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][j][order / 2] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- BOTTOM ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][j][order - 1] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- LEFT ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][0][j] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- MIDDLE-LEFT ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][order / 2][j] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- RIGHT ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[i][order - 1][j] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- FRONT ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[0][i][j] + "  ");
			System.out.println();
		}

		System.out.println("\n------------------- MIDDLE-FRONT ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[order / 2][i][j] + "  ");
			System.out.println();
		}
		System.out.println();

		System.out.println("\n------------------- BACK ----------------------\n");
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[order - 1][i][j] + "  ");
			System.out.println();
		}

		System.out.println(" --------------- DIAGONAL FACES ----------------\n");

		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[j][j][i] + "  ");
			System.out.println();
		}
		System.out.println();
		for (int i = 0; i < order; i++) {
			for (int j = 0; j < order; j++)
				System.out.print(magicCube[j][order - j - 1][i] + "  ");
			System.out.println();
		}

	}

	// Returns true if given three positions are collinear
	public boolean checkIfCollinear(Position3D p1, Position3D p2, Position3D p3) {
		Position3D p1_p2 = p1.difference(p2);
		Position3D p2_p3 = p2.difference(p3);
		int dot = (p1_p2.i * p2_p3.i + p1_p2.j * p2_p3.j + p1_p2.k * p2_p3.k);
		return dot * dot == p1_p2.moduloSquare() * p2_p3.moduloSquare();
	}

	// Generate both collinear and non-collinear exceptions
	private void generateExceptions() {
		nonCollinearExceptions = new Vector<Triplet>();
		collinearExceptions = new Vector<Triplet>();
		int cons = magicCubeConstant();
		int m = order * order * order;
		for (int i = 1; i <= m; i++) {
			for (int j = i + 1; j <= m; j++) {
				for (int k = j + 1; k <= m; k++) {
					if (i + j + k == cons) {
						Position3D p1 = getPosition(i);
						Position3D p2 = getPosition(j);
						Position3D p3 = getPosition(k);
						if (!(checkIfCollinear(p1, p2, p3)))
							nonCollinearExceptions.add(new Triplet(p1, p2, p3));
					}
				}
			}
		}
		for (int i = 0; i < order; i = i + 2) { //Collinear exceptions are the surface diagonals
			collinearExceptions
					.add(new Triplet(new Position3D(0, 0, i), new Position3D(1, 1, i), new Position3D(2, 2, i)));
			collinearExceptions
					.add(new Triplet(new Position3D(0, 2, i), new Position3D(1, 1, i), new Position3D(2, 0, i)));
			collinearExceptions
					.add(new Triplet(new Position3D(i, 0, 0), new Position3D(i, 1, 1), new Position3D(i, 2, 2)));
			collinearExceptions
					.add(new Triplet(new Position3D(i, 0, 2), new Position3D(i, 1, 1), new Position3D(i, 2, 0)));
			collinearExceptions
					.add(new Triplet(new Position3D(0, i, 0), new Position3D(1, i, 1), new Position3D(2, i, 2)));
			collinearExceptions
					.add(new Triplet(new Position3D(0, i, 2), new Position3D(1, i, 1), new Position3D(2, i, 0)));

		}
	}

	private void printExceptions() {
		System.out.println("\nNon Collinear Exceptions:");
		for (Triplet e : nonCollinearExceptions) {
			System.out.println(getMagicNumber(e.a) + ", " + getMagicNumber(e.b) + ", " + getMagicNumber(e.c));
		}
		System.out.println("\n Collinear Exceptions:");
		for (Triplet e : collinearExceptions) {
			System.out.println(getMagicNumber(e.a) + ", " + getMagicNumber(e.b) + ", " + getMagicNumber(e.c));
		}
		System.out.println();
	}

	// Returns true if sum = 42 and Non-Collinear
	// Triplets is a tuple of positions
	public boolean checkNonCollinearException(Triplet t) {
		int a = getMagicNumber(t.a);
		int b = getMagicNumber(t.b);
		int c = getMagicNumber(t.c);
		if (a == b || b == c || a == c)
			return true;
		for (Triplet t1 : nonCollinearExceptions) {
			if (t1.equals(t)) {
				return true;
			}
		}
		return false;
	}

	//Returns -1 if positions of magic number x and y do not match any collinear exception
	public int checkCollinearException(int x, int y) {
		for (Triplet t1 : collinearExceptions) {
			int i = getMagicNumber(t1.a);
			int j = getMagicNumber(t1.b);
			int k = getMagicNumber(t1.c);
			if ((i == x && j == y) || (i == y && j == x))
				return k;
			if ((k == x && j == y) || (k == y && j == x))
				return i;
			if ((i == x && k == y) || (i == y && k == x))
				return j;

		}
		return -1;
	}
}


// Class to store tuple of 3 3D cartesian coordinates in form o f Position3D class
class Triplet {
	Position3D a;
	Position3D b;
	Position3D c;

	public Triplet(Position3D a, Position3D b, Position3D c) {
		this.a = a;
		this.b = b;
		this.c = c;

	}

	public boolean equals(Object obj) {
		if (obj == null)
			return false;
		Triplet t = (Triplet) obj;
		return t.a.equals(a) && t.b.equals(b) && t.c.equals(c);
	}

}