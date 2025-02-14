# GPS Project: Shortest Path Finder Using Dijkstra's Algorithm

This project is a Java application that helps users find the shortest route between two locations. It leverages Dijkstra's algorithm, a fundamental algorithm used to find the shortest paths in a graph. The application uses two CSV datasets containing information about various locations and the distances between them. The program allows users to choose a starting location and a destination, and it calculates the shortest path using the graph data. The results are displayed both graphically (on a map) and textually (in the console).

# Features

- Shortest Path Calculation: Utilizes Dijkstra's algorithm to find the shortest path between two user-selected locations.

- Graphical Display: Visualizes locations and routes on a map.

- Text-based Output: Displays the calculated route and its distance in the console.

- User-friendly Interface: Allows easy selection of start and destination points.


# How to run the program

# Prerequisites

Java Development Kit (JDK) installed

Java emulator or IDE (e.g., IntelliJ IDEA, Eclipse)

# Steps to Run the Program


1. Download the ZIP file: This contains the necessary Java source code and the two CSV files (760_tatorter.csv and edges_760_tatorter.csv)
   
2. Extract the ZIP file: Unzip the contents to your local directory.
   
4. Place the CSV Files: Put the files 760_tatorter.csv and edges_760_tatorter.csv in a directory on your system.
   
5. Configure File Path:
. Open the DemoApp class in your Java emulator.
- In the loadFile method, update the file path to where you placed the CSV files.
  
5. Run the Program: Now, you're ready to run the program! Simply execute the DemoApp class, and the program will start.

# How it works

# Data
CSV Data: The project uses two CSV files:
- 760_tatorter.csv: Contains information about the locations (nodes).
- edges_760_tatorter.csv: Contains the distances (edges) between these locations.

# Dijkstra's Algorithm
The core of the program is Dijkstra's algorithm. It works by iteratively selecting the closest unvisited node (location) and updating the distances to its neighboring nodes until the shortest path is found.

Graphical Display
The graphical display allows users to see the map with locations and the routes between them.
The map will visually highlight the shortest route from the selected start point to the destination.
Console Output
After the route is calculated, the program will print the shortest path in the console.


![Screenshot 2024-12-06 at 02 26 21](https://github.com/user-attachments/assets/8b39e548-6d9a-4ea7-b96e-4d5c0e2a49b1)


