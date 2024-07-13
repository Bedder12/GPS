import java.util.ArrayList;
import java.util.List;

public class Vertex {
	private String name;
	private int population;
	private double longitude;
	private double latitude;
	private List<Edge> edges;
	private double distance;
	private Vertex predecessor;

	public Vertex(String name, int population, double longitude, double latitude) {
		this.name = name;
		this.population = population;
		this.longitude = longitude;
		this.latitude = latitude;
		this.edges = new ArrayList<>();
		this.distance = Double.POSITIVE_INFINITY;
		this.predecessor = null;
	}

	public String getName() {
		return name;
	}

	public void addEdge(Edge edge) {
		edges.add(edge);
	}

	public double getLatitude() {
		return latitude;
	}

	public double getLongitude() {
		return longitude;
	}

	public List<Edge> getEdges() {
		return edges;
	}

	public int getPopulation() {
		return population;
	}

	public double getDistance() {
		return distance;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public Vertex getPredecessor() {
		return predecessor;
	}

	public void setPredecessor(Vertex predecessor) {
		this.predecessor = predecessor;
	}

	@Override
	public String toString() {
		return name;
	}
}
