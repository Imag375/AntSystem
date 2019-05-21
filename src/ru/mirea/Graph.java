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

    public float[] getGraphWithPherLine(int node) {
        return graphWithPher[node];
    }

    public synchronized void setPherConcentration(float concentration, int i, int j) {
        graphWithPher[i][j] = concentration;
    }

    public void getRoute() {
        float max;
        int node = 0;
        int nextNode = 0;
        int sum = 0;
        System.out.print("Итоговый маршрут:\n0");
        while(nextNode != getSize()-1) {
            max = getGraphWithPherLine(node)[0];
            nextNode = 0;
            for(int i = 1; i < getSize(); i++) {
                if(max < getGraphWithPherLine(node)[i]) {
                    max = getGraphWithPherLine(node)[i];
                    nextNode = i;
                }
            }
            sum += getGraphLine(node)[nextNode];
            System.out.print(" -> " + nextNode);
            node = nextNode;
        }
        System.out.println("\nДлина итогового маршрута равна " + sum);
    }

}
