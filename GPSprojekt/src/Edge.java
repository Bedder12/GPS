public class Edge {
	private Vertex fromVertex;
	private Vertex toVertex;
	private double weight;

	public Edge(Vertex fromVertex, Vertex toVertex, double weight) {
		this.fromVertex = fromVertex;
		this.toVertex = toVertex;
		this.weight = weight;
	}

	public Vertex getFromVertex() {
		return fromVertex;
	}

	public Vertex getToVertex() {
		return toVertex;
	}

	public double getWeight() {
		return weight;
	}

	public void setFromVertex(Vertex fromVertex) {
		this.fromVertex = fromVertex;
	}

	public void setToVertex(Vertex toVertex) {
		this.toVertex = toVertex;
	}

	public void setWeight(double weight) {
		this.weight = weight;
	}
}
