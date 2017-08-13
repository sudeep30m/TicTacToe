import java.util.Vector;

public class Player3D {
	int id; // 3 for "X" and 5 for "0"
	Vector<Position3D> positions;

	public Player3D(int id) {
		this.id = id;
		positions = new Vector<Position3D>();
	}

	public void addPosition(Position3D p) {
		this.positions.add(p);
	}

	public Position3D getPosition(int i) {
		return positions.get(i);
	}
}