import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

public class Graph {
    private Map<String, Vertex> vertexMap = new HashMap<>();
    private List<Vertex> vertexList = new ArrayList<>();
    private List<Edge> edgeList = new ArrayList<>();
    private Vertex startDestination;
    private Vertex slutDestination;

    public Graph(String startDestination, String slutDestination) {
    
        this.startDestination = vertexMap.get(startDestination);
        this.slutDestination = vertexMap.get(slutDestination);

//        if (this.startVertex == null) {
//            throw new NoSuchElementException("Start vertex not found");
//        }
    }

    public void readVertexFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine(); // Skip header line
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String name = parts[0];
                int population = Integer.parseInt(parts[1]);
                double longitude = Double.parseDouble(parts[2].replace(",", "."));
                double latitude = Double.parseDouble(parts[3].replace(",", "."));
                Vertex vertex = new Vertex(name,population, longitude, latitude);
                vertexMap.put(name, vertex);

            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public void readEdgeFile(String filename) {
        try (Scanner scanner = new Scanner(new File(filename))) {
            scanner.nextLine(); 
            while (scanner.hasNextLine()) {
                String[] parts = scanner.nextLine().split(";");
                String from = parts[0];
                String to = parts[1];
                double weight = Double.parseDouble(parts[2].replace(",", "."));
                Vertex fromVertex = vertexMap.get(from);
                Vertex toVertex = vertexMap.get(to);
                if (fromVertex != null && toVertex != null) {
                    Edge edge = new Edge(fromVertex, toVertex, weight);
                    edgeList.add(edge);
                    fromVertex.addEdge(edge);
                }
               
                System.out.println(weight);
                System.out.println(from);
                System.out.println(to);
                
            }
            
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }

    public Collection<Vertex> getVertices() {
        return vertexMap.values();
    }

    public List<Edge> getEdges() {
        return edgeList;
    }

    public List<String> findShortestPath(String start, String destination) {
        PriorityQ<Vertex, Double> priorityQ = new PriorityQ<>(vertexMap.size());
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
            priorityQ.insert(vertex, vertex.getDistance());
        }

        while (priorityQ.size() > 0) {
            Vertex currentVertex = priorityQ.extract();
            for (Edge edge : currentVertex.getEdges()) {
                Vertex neighbor = edge.getToVertex();
                double newDistance = currentVertex.getDistance() + edge.getWeight();
                if (newDistance < neighbor.getDistance()) {
                    neighbor.setDistance(newDistance);
                    neighbor.setPredecessor(currentVertex);
                    priorityQ.insert(neighbor, newDistance);
                }
            }
        }

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
}
