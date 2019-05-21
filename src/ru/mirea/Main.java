package ru.mirea;

public class Main {

    private final static int NUMBER_OF_ANTS = 1000;
    /*private final static int[][] MATRIX = {
           //0, 1, 2, 3, 4, 5, 6, 7, 8, 9,10,11
            {0, 2, 7, 0, 0, 0, 0, 0, 0, 0, 0, 0}, //0
            {2, 0, 3, 8, 4, 0, 0, 7, 0, 0, 0, 0}, //1
            {7, 3, 0, 0,12, 6, 0, 0, 2, 0, 0, 0}, //2
            {0, 8, 0, 0, 0, 0, 0, 6, 0, 0, 0, 0}, //3
            {0, 4,12, 0, 0, 4, 2, 0, 0,13, 0, 0}, //4
            {0, 0, 6, 0, 4, 0, 0,18, 6, 0, 0, 0}, //5
            {0, 0, 0, 0, 2, 0, 0, 6, 0,12, 0, 0}, //6
            {0, 7, 0, 6, 0,18, 6, 0, 4, 0, 7, 0}, //7
            {0, 0, 2, 0, 0, 6, 0, 4, 0, 4, 1, 0}, //8
            {0, 0, 0, 0,13, 0,12, 0, 4, 0, 0,15}, //9
            {0, 0, 0, 0, 0, 0, 0, 7, 1, 0, 0, 4}, //10
            {0, 0, 0, 0, 0, 0, 0, 0, 0,15, 4, 0}  //11
    };*/

    private final static int[][] MATRIX = {
           //0, 1, 2, 3
            {0, 3, 6, 0}, //0
            {3, 0, 4, 5}, //1
            {6, 4, 0, 1}, //2
            {0, 5, 1, 0}  //3
    };

    public static void main(String[] args) {
        Graph graph = new Graph(MATRIX, 4);
        AntAlgorithm antJob = new AntAlgorithm(graph);
        Thread[] ant = new Thread[NUMBER_OF_ANTS];
        for (int i = 0; i < NUMBER_OF_ANTS; i++) {
            ant[i] = new Thread(antJob, "ant " + i);
            ant[i].start();
        }
        while (antJob.threadCount != NUMBER_OF_ANTS) {
            Thread.yield();
        }
        System.out.println("Итоговая матрица графа с дорожкой из феромонов:");
        System.out.format("%5d %5d %5d %5d\n", 0, 1, 2, 3);
        for (int i = 0; i < graph.getSize(); i++) {
            System.out.print(i + " ");
            for (float j : graph.getGraphWithPherLine(i)) {
                System.out.format("%5.2f ", j);
            }
            System.out.print("\n");
        }
        graph.getRoute();
    }
}
