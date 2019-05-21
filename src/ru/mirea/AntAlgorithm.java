package ru.mirea;

public class AntAlgorithm implements Runnable {

    private Graph graph;
    public int threadCount = 0; //количество потоков, которые выполнили свою работу

    private final float a = 0.5f; //коэффициент важности феромона
    private final float b = 0.5f; //коэффициент важности эвристической информации
    private final float p = 0.3f; //коэффициент испарения феромона
    private final float q = 1f; //запас феромона в брюшке муравья
    private final float t0 = 0.5f; //количество феромона на начало алгоритма


    AntAlgorithm(Graph g) {
        this.graph = g;
    }

    @Override
    public void run() {
        Ant ant = new Ant();
        int node = 0; //текущий узел (узел, в котором муравей сейчас находится)
        ant.addNode(node);
        int nextNode;
        while(node != graph.getSize()-1) {
            double probability = 0;
            double randPath = Math.random();
            nextNode = node;
            for(int i = 0; i < graph.getSize(); i++) {  //выбор дальнейшего пути
                if(graph.getGraphLine(node)[i] > 0) {
                    probability += probabOfChoosingPath(graph.getSize(), node, i);
                    if (randPath <= probability) {
                        nextNode = i;
                        break;
                    }
                }
            }
            ant.addNode(nextNode);
            node = nextNode;
        }
        node = ant.getNode(0); //узел, из которого мы идем
        nextNode = 0; //узел, в который мы пойдем
        int length = 0; //длина пройденного пути
        for(int i = 1; i < ant.getSizeRoute(); i++) {
            nextNode = ant.getNode(i);
            length += graph.getGraphLine(node)[nextNode];
            node = nextNode;
        }
        float t = q/length; //концентрация феромона на длину пути
        try {
            Thread.sleep(10);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        synchronized (graph) {
            node = ant.getNode(0);
            for (int i = 1; i < ant.getSizeRoute(); i++) {
                nextNode = ant.getNode(i);
                graph.setPherConcentration((graph.getGraphWithPherLine(node)[nextNode] + graph.getGraphLine(node)[nextNode] * t), node, nextNode);
                node = nextNode;
            }
            for (int i = 0; i < graph.getSize(); i++) {
                for (int j = 0; j < graph.getSize(); j++) {
                    if(graph.getGraphLine(i)[j] > 0) {
                        graph.setPherConcentration((1 - p) * graph.getGraphWithPherLine(i)[j], i, j);
                    }
                }
            }
            System.out.println("\n" + Thread.currentThread().getName());
            System.out.format("%5d %5d %5d %5d\n", 0, 1, 2, 3);
            for (int i = 0; i < graph.getSize(); i++) {
                System.out.print(i + " ");
                for (float j : graph.getGraphWithPherLine(i)) {
                    System.out.format("%5.2f ", j);
                }
                System.out.print("\n");
            }
        }
        threadCount++;
    }

    private double probabOfChoosingPath(int size, int node, int nextNode) {
        double sum = 0;
        for(int i = 0; i < size; i++) {
            if(graph.getGraphLine(node)[i] > 0) {
                sum += Math.pow(graph.getGraphWithPherLine(node)[i], a) * Math.pow(1.0 / graph.getGraphLine(node)[i], b);
            }
        }
        return Math.pow(graph.getGraphWithPherLine(node)[nextNode], a) * Math.pow((1.0/graph.getGraphLine(node)[nextNode]), b) / sum;
    }
}
