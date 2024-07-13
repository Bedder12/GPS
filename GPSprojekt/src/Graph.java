import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

public class Graph implements IGraph {
    private Map<String, Vertex> vertexMap = new HashMap<>();
    private List<Vertex> vertexList = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();
    private Vertex startVertex;
    private Vertex targetVertex;

    public Graph() {
        // Default constructor
    }

    @Override
    public void setStartVertex(String startVertexName) {
        this.startVertex = vertexMap.get(startVertexName);
        if (this.startVertex == null) {
            throw new NoSuchElementException("Start vertex not found");
        }
    }

    public void readVertexFile(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            scanner.nextLine(); // Skip header line
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String name = parts[0];
                int population = Integer.parseInt(parts[1]);
                double longitude = Double.parseDouble(parts[2].replace(",", "."));
                double latitude = Double.parseDouble(parts[3].replace(",", "."));
                Vertex vertex = new Vertex(name, population, longitude, latitude);
                vertexMap.put(name, vertex);
                vertexList.add(vertex);
            }
        } catch (FileNotFoundException e) {
            System.err.println("Vertex file not found: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing vertex file: " + e.getMessage());
        }
    }

    public void readEdgeFile(String filename) throws IOException {
        try (Scanner scanner = new Scanner(new File(filename), StandardCharsets.UTF_8)) {
            scanner.nextLine(); // Skip header line
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String from = parts[0];
                String to = parts[1];
                double weight = Double.parseDouble(parts[2].replace(",", "."));
                Vertex fromVertex = vertexMap.get(from);
                Vertex toVertex = vertexMap.get(to);
                if (fromVertex != null && toVertex != null) {
                    addEdge(fromVertex, toVertex, weight);
                }
            }
        } catch (FileNotFoundException e) {
            System.err.println("Edge file not found: " + filename);
        } catch (NumberFormatException e) {
            System.err.println("Error parsing edge file: " + e.getMessage());
        }
    }

    private void addEdge(Vertex fromVertex, Vertex toVertex, double weight) {
        Edge edge = new Edge(fromVertex, toVertex, weight);
        edgeList.add(edge);
        fromVertex.addEdge(edge);
        // Adding the edge in the opposite direction for bidirectional graph
        Edge reverseEdge = new Edge(toVertex, fromVertex, weight);
        toVertex.addEdge(reverseEdge);
    }

    @Override
    public List<Edge> getEdges() {
        return edgeList;
    }

    @Override
    public List<String> findShortestPath(String start, String destination) {
        dijkstra(start);

        List<String> path = new LinkedList<>();
        Vertex step = vertexMap.get(destination);
        if (step == null) {
            throw new NoSuchElementException("Destination vertex not found");
        }

        while (step != null) {
            path.add(0, step.getName());
            step = step.getPredecessor();
        }

        return path;
    }

    private void dijkstra(String start) {
        PriorityQueue<Vertex> priorityQueue = new PriorityQueue<>(Comparator.comparingDouble(Vertex::getDistance));
        Vertex startVertex = vertexMap.get(start);

        if (startVertex == null) {
            throw new NoSuchElementException("Start vertex not found");
        }

        for (Vertex vertex : vertexMap.values()) {
            vertex.setPredecessor(null);
            if (vertex == startVertex) {
                vertex.setDistance(0);
            } else {
                vertex.setDistance(Double.POSITIVE_INFINITY);
            }
            priorityQueue.add(vertex);
        }

        while (!priorityQueue.isEmpty()) {
            Vertex currentVertex = priorityQueue.poll();
            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getToVertex();
                double newDistance = currentVertex.getDistance() + edge.getWeight();
                if (newDistance < neighbor.getDistance()) {
                    priorityQueue.remove(neighbor); // Remove and reinsert to update priority
                    neighbor.setDistance(newDistance);
                    neighbor.setPredecessor(currentVertex);
                    priorityQueue.add(neighbor);
                }
            }
        }
    }

    @Override
    public void addVertex(String id, int population, double longitude, double latitude) {
        Vertex vertex = new Vertex(id, population, longitude, latitude);
        vertexMap.put(id, vertex);
        vertexList.add(vertex);
    }

    @Override
    public void connectVertices(String id1, String id2, double weight) {
        Vertex fromVertex = vertexMap.get(id1);
        Vertex toVertex = vertexMap.get(id2);
        if (fromVertex != null && toVertex != null) {
            addEdge(fromVertex, toVertex, weight);
        }
    }

    @Override
    public Vertex getStartVertex() {
        return startVertex;
    }

    @Override
    public void setTargetVertex(Vertex target) {
        this.targetVertex = target;
    }

    @Override
    public Vertex getTargetVertex() {
        return targetVertex;
    }

    @Override
    public void clear() {
        startVertex = null;
        targetVertex = null;
    }

    @Override
    public void findShortestPath(String start_id) {
        dijkstra(start_id);
    }

    @Override
    public void findShortestPath(Vertex start_vertex) {
        dijkstra(start_vertex.getName());
    }

    @Override
    public List<Vertex> getVertices() {
        return vertexList;
    }
}
