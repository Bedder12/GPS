import java.awt.Color;
import java.awt.Font;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.NoSuchElementException;

import javax.swing.*;

@SuppressWarnings("serial")
public class DemoApp extends JFrame {
    private MapPanel mapPanel;
    private Graph graph = new Graph(); // Use Graph directly instead of IGraph

    public DemoApp() throws IOException {
        super("Dijkstra Visualization");
        Font font = new Font(Font.MONOSPACED, Font.BOLD, 16);
        mapPanel = new MapPanel(graph);
        mapPanel.setFont(font);
        add(mapPanel);
        makeMenus();
        setBackground(Color.WHITE);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(800, 600);
        setLocationRelativeTo(null);
        setVisible(true);

        loadData();
        findAndPrintShortestPath();
    }

    public void makeMenus() {
        JMenuBar menuBar = new JMenuBar();
        JMenu help = new JMenu("Help");
        JMenuItem mouseMI = new JMenuItem("Mouse commands");
        mouseMI.addActionListener((e) -> showMouseHelp());
        JMenuItem keybMI = new JMenuItem("Keyboard commands");
        keybMI.addActionListener((e) -> showKeyboardHelp());
        JMenuItem aboutMI = new JMenuItem("About this tool");
        aboutMI.addActionListener((e) -> showAbout());
        help.add(mouseMI);
        help.add(keybMI);
        help.add(aboutMI);
        menuBar.add(help);
        setJMenuBar(menuBar);
    }

    public void showKeyboardHelp() {
        String html = "<html><table>" + "<tr><th align=\"left\">Key</th><th align=\"left\">Action</th></tr>"
                + "<tr><td><b>&#8592</b></td><td>Rotate left</td></tr>"
                + "<tr><td><b>&#8594</b></td><td>Rotate right</td></tr>" + "<tr><td><b>C</b></td><td>Reset</td></tr>"
                + "<tr><td><b>ENTER</b></td><td>Reset</td></tr>" + "<tr><td><b>SPACE</b></td><td>Reset</td></tr>"
                + "</table>";
        JOptionPane.showMessageDialog(this, html, "Keyboard Commands", JOptionPane.PLAIN_MESSAGE);
    }

    public void showMouseHelp() {
        String html = "<html><p>Click and drag to rotate</p><p>Click vertex to show spanning tree</p>";
        JOptionPane.showMessageDialog(this, html, "Mouse Commands", JOptionPane.PLAIN_MESSAGE);
    }

    public void showAbout() {
        String html = "<html><table>" + "<tr><th align=\"center\">Dijkstra Visualization</th></tr>"
                + "<tr><td></td></tr>" + "<tr><td align=\"center\">Version 1.0</td></tr>"
                + "<tr><td align=\"center\">2024-05-15</td></tr>"
                + "<tr><td align=\"center\">Copyright &#169 2024 - 2030 Jonas Boustedt</td></tr>"
                + "<tr><td align=\"center\">All rights reserved</td></tr>" + "</table>";
        JOptionPane.showMessageDialog(this, html, "About Dijkstra Visualization", JOptionPane.PLAIN_MESSAGE);
    }

    private void loadData() throws IOException {
        // Adjust these paths as necessary
        String vertexFile = "C:\\AlgoritmerPROJEKT/760_tatorter.csv";
        String edgeFile = "C:\\AlgoritmerPROJEKT/edges_760_tatorter.csv";

        graph.readVertexFile(vertexFile);
        graph.readEdgeFile(edgeFile);

        // Set the start vertex
        graph.setStartVertex("Gävle");

        // Set the target vertex and print its name
        Vertex targetVertex = graph.getVertices().stream().filter(v -> v.getName().equals("Valbo")).findFirst()
                .orElse(null);
        if (targetVertex != null) {
            graph.setTargetVertex(targetVertex);
            System.out.println("Target vertex set to: " + graph.getTargetVertex().getName());
        } else {
            System.out.println("Valbo not found in the graph.");
        }
    }


    private void findAndPrintShortestPath() {
        // Find the shortest path from Gävle to Valbo
        try {
            List<String> path = graph.findShortestPath("Gävle", "Valbo");
            System.out.println("Shortest path from Gävle to Valbo: " + String.join(" -> ", path));
        } catch (NoSuchElementException e) {
            System.err.println("Error finding shortest path: " + e.getMessage());
        }
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            try {
                new DemoApp();
            } catch (IOException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }
        });
    }

}
