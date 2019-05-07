package ru.mirea;

public class Graph {

    private int[][] graph; //матрица смежности с весами ребер
    private float[][] graphWithPher; // матрица смежности графа с частицами феромона

    Graph(int[][] matrix, int size){
        this.graph = matrix.clone();
        graphWithPher = new float[size][size];
        for(int i = 0; i < size; i++) {
            for(int j = 0; j < size; j++) {
                if(graph[i][j] > 0) {
                    graphWithPher[i][j] = 0.1f;
                }
            }
        }
    }

    public int getSize() {
        return graph.length;
    }

    public int[] getGraphLine(int node) {
        return graph[node];
    }

    public synchronized float[] getGraphWithPherLine(int node) {
        return graphWithPher[node];
    }

    public synchronized void setPherConcentration(float concentration, int i, int j) {
        graphWithPher[i][j] = concentration;
    }


}
