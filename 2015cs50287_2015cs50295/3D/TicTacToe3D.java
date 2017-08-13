/*
 * Main class to play 3D tic tac toe
 */
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;
import java.util.Vector;

public class TicTacToe3D {
	int board[][][]; // To store tic tac toe board
	private Player3D computer; //player class object for human and computer
	private Player3D human;
	private MagicCube3D magicCube;
	private Vector<Position3D> corner, faceCenter, possibleMoves; // Sets containing unoccupied cells
	private int scoreHuman, scoreComputer; //counter for scores

	public TicTacToe3D(String player) {
		magicCube = new MagicCube3D(3);
		board = new int[3][3][3];
		corner = new Vector<Position3D>();
		faceCenter = new Vector<Position3D>();
		possibleMoves = new Vector<Position3D>();
		for (int i = 0; i < 3; i = i + 2) {
			for (int j = 0; j < 3; j = j + 2) {
				for (int k = 0; k < 3; k = k + 2)
					corner.add(new Position3D(i, j, k));
			}
		}
		for (int i = 0; i < 3; i = i + 2) {
			faceCenter.add(new Position3D(i, 1, 1));
			faceCenter.add(new Position3D(1, i, 1));
			faceCenter.add(new Position3D(1, 1, i));
		}
		for(int i = 0; i < 3; i++) {
			for(int j = 0; j < 3; j++) {
				for(int k = 0; k < 3; k++) {
					possibleMoves.add(new Position3D(i, j, k));
				}
			}
		}
		reset(player);
	}

	public static void main(String args[]) {
		TicTacToe3D game = null;
		System.out.println("Do you wish to start? (Y/N): ");
		Scanner s = new Scanner(System.in);
		String response = s.next();
		if (response.toLowerCase().equals("y"))
			game = new TicTacToe3D("H");
		else
			game = new TicTacToe3D("C");
		game.start();
	}

	public void reset(String player) {
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++)
					board[i][j][k] = 2;
			}
		}
		if (player.equals("C")) {
			computer = new Player3D(3);
			human = new Player3D(5);
		} else {
			computer = new Player3D(5);
			human = new Player3D(3);
		}
	}

	public void start() {
		scoreHuman = 0;
		scoreComputer = 0;
		Scanner s = new Scanner(System.in);
		for (int i = 1; i <= 20; i++) {
			if ((this.computer.id == 3 && i % 2 == 1) || (this.computer.id == 5 && i % 2 == 0)) {
				System.out.println("\n\nComputer's turn\n");
				playTurn(i);
			} else {
				int x, y, z;
				while(true) {
					System.out.print("\nYour turn\nEnter Position in x y z -\n");
					x = s.nextInt();
					y = s.nextInt();
					z = s.nextInt();
					if(board[x][y][z] == 2) {
						go(new Position3D(x, y, z), human);
						break;
					}
				}
			}
			System.out.println("Moves = " + i);
			printBoard();
		}
		s.close();
		if(scoreComputer > scoreHuman)
			System.out.println("\n------------------- I WIN ----------------------\n");
		else if(scoreComputer < scoreHuman)
			System.out.println("\n------------------- YOU WIN ----------------------\n");
		else
			System.out.println("\n------------------- TIE ----------------------\n");
	}

	public void printBoard() {
		System.out.println();
		for (int i = 0; i < 3; i++) {
			for (int j = 0; j < 3; j++) {
				for (int k = 0; k < 3; k++) {
					if (board[k][j][i] == 2)
						System.out.print("|   ");
					else if (board[k][j][i] == 3)
						System.out.print("| X ");
					else if (board[k][j][i] == 5)
						System.out.print("| O ");
				}
				System.out.println("|\n");
			}
			System.out.println("\n");
		}
		System.out.println("Computer Score = "+ scoreComputer);
		System.out.println("Human Score = "+ scoreHuman);
		System.out.println("\n---------------------------------------");
		
	}

	void go(Position3D p, Player3D player) {
		board[p.i][p.j][p.k] = player.id;
		if(p.i != 1 && p.j != 1 && p.k != 1)
			corner.remove(p);
		else if(p.i + p.j + p.k == 2 || p.i + p.j + p.k == 4) {
			faceCenter.remove(p);
		}
		possibleMoves.remove(p);
		int score = computeScore(player, p);
		if(player.id == computer.id)
			scoreComputer += score;
		else
			scoreHuman += score; 
		player.addPosition(p);
	}

	public void playTurn(int step) {
		Random rand = new Random();
		if (step == 1) {
			go(new Position3D(1, 1, 1), computer);
		} else if (step == 2) {
			if (board[1][1][1] == 2)
				go(new Position3D(1, 1, 1), computer);
			else {
				int r = rand.nextInt(8);
				go(corner.get(r), computer);
			}
		} else if (step == 3) {
			int r = rand.nextInt(8);
			go(corner.get(r), computer);
		} else {
			Position3D comp = checkWin(computer);
			Position3D man = checkWin(human);
			int compScore = 0;
			int manScore = 0;
			if(comp != null) {
				compScore = computeScore(computer, comp);
			}
			if(man != null) {
				manScore = computeScore(human, man);
			}
			if(manScore == 0 && compScore == 0) {
				Position3D possible = possibleWin(computer);
				if(possible == null) {
					if(corner.size() != 0) {
						int r = rand.nextInt(corner.size());
						go(corner.get(r), computer);
					} else if(faceCenter.size() != 0) {
						int r = rand.nextInt(faceCenter.size());
						go(faceCenter.get(r), computer);
					} else {
						int r = rand.nextInt(possibleMoves.size());
						go(possibleMoves.get(r), computer);
					}
				} else {
					go(possible, computer);
				}
			} else if(compScore >= manScore) {
				go(comp, computer);
			} else {
				go(man, computer);
			}
		}
	}

	Position3D checkWin(Player3D player) {
		HashMap<Position3D, Integer> map = new HashMap<Position3D, Integer>();
		for (int i = 0; i < player.positions.size(); i++) {
			for (int j = i + 1; j < player.positions.size(); j++) {
				Position3D p = check(player.getPosition(i), player.getPosition(j), player.id);
				if (p != null) {
					if (map.containsKey(p))
						map.put(p, map.get(p) + 1);
					else
						map.put(p, 1);
				}
			}
		}
		int max = 0;
		Position3D p = null;
		for (Position3D pos : map.keySet()) {
			if (map.get(pos) > max) {
				max = map.get(pos);
				p = pos;
			}
		}
		if (max == 0)
			return null;
		return p;
	}
	
	Position3D possibleWin(Player3D player) {
		HashMap<Position3D, Integer> map = new HashMap<Position3D, Integer>();
		for (int i = 0; i < possibleMoves.size(); i++) {
			for (int j = i + 1; j < possibleMoves.size(); j++) {
				Position3D p = check(possibleMoves.get(i), possibleMoves.get(j), player.id);
				if (p != null && board[p.i][p.j][p.k] == player.id) {
					if (map.containsKey(possibleMoves.get(i)))
						map.put(possibleMoves.get(i), map.get(possibleMoves.get(i)) + 1);
					else
						map.put(possibleMoves.get(i), 1);
					if (map.containsKey(possibleMoves.get(j)))
						map.put(possibleMoves.get(j), map.get(possibleMoves.get(j)) + 1);
					else
						map.put(possibleMoves.get(j), 1);
				}
			}
		}
		int max = 0;
		Position3D p = null;
		for (Position3D pos : map.keySet()) {
			if (map.get(pos) > max) {
				max = map.get(pos);
				p = pos;
			}
		}
		if (max == 0)
			return null;
		return p;
	}

	int computeScore(Player3D player, Position3D pos) {
		int score = 0;
		for (int i = 0; i < player.positions.size(); i++) {
			for (int j = i + 1; j < player.positions.size(); j++) {
				if (magicCube.checkIfCollinear(player.getPosition(i), player.getPosition(j), pos)) {
					score++;
				}
			}
		}
		return score;
	}

	Position3D check(Position3D i, Position3D j, int id) {
		int sum = magicCube.magicCubeConstant();
		int x = magicCube.getMagicNumber(i);
		int y = magicCube.getMagicNumber(j);
		Position3D p = magicCube.getPosition(sum - x - y);
		if (p != null && board[p.i][p.j][p.k] == 2 && !(magicCube.checkNonCollinearException(sort(x, y, sum - x - y)))) {
			return p;
		}
		int check = magicCube.checkCollinearException(x, y);
		p = magicCube.getPosition(check);
		if (check != -1 && board[p.i][p.j][p.k] == 2) {
			return p;
		}
		return null;
	}

	private Triplet sort(int x, int y, int z) {
		int temp;
		if (x > y) {
			temp = y;
			y = x;
			x = temp;
		}
		if (y > z) {
			temp = y;
			y = z;
			z = temp;
		}
		if (x > y) {
			temp = y;
			y = x;
			x = temp;
		}
		return new Triplet(magicCube.getPosition(x), magicCube.getPosition(y), magicCube.getPosition(z));
	}
}
