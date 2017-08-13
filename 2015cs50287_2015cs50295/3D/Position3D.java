/*
 * Class to store 3D coordinates
 */

class Position3D {
	int i;
	int j;
	int k;

	public Position3D(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}
	
	public boolean equals(Object p) {
		if (p == null)
			return false;
		Position3D pos = (Position3D)p;
		return (pos.i == i && pos.j == j && pos.k == k);
	}

	public void set(int i, int j, int k) {
		this.i = i;
		this.j = j;
		this.k = k;
	}

	public Position3D difference(Position3D p2) {
		return new Position3D(i - p2.i, j - p2.j, k - p2.k);
	}

	public int moduloSquare() {
		return i * i + j * j + k * k;
	}

	public boolean checkCollinear(Position3D p) {
		int dot = (i * p.i + j * p.j + k * p.k);
		return (dot * dot) == (moduloSquare() * p.moduloSquare());
	}
}
