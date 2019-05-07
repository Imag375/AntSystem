package ru.mirea;

public class AntAlgorithm implements Runnable {

    private Graph graph;
    public int threadCount = 0; //количество потоков, которые выполнили свою работу

    private final float a = 0.6f; //коэффициент важности феромона
    private final float b = 0.6f; //коэффициент важности эвристической информации
    private final float p = 0.6f; //коэффициент испарения феромона
    private final float q = 10f; //запас феромона в брюшке муравья
    private final float t0 = 0.1f; //количество феромона на начало алгоритма


    AntAlgorithm(Graph g) {
        this.graph = g;
    }

    @Override
    public void run() {
        Ant ant = new Ant();
        int node = 0; //текущий узел (узел, в котором муравей сейчас находится)
        ant.addNode(node);
        float maxProbab;
        int nextNode;
        while(node != graph.getSize()-1) {
            maxProbab = 0;
            nextNode = node;
            for(int i = 0; i < graph.getSize(); i++) {
                if(probabOfChoosingPath(graph.getSize(), node, i) > maxProbab) {
                    maxProbab = probabOfChoosingPath(graph.getSize(), node, i);
                    nextNode = i;
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
        synchronized (graph) {
            node = ant.getNode(0);
            for (int i = 1; i < ant.getSizeRoute(); i++) {
                nextNode = ant.getNode(i);
                graph.setPherConcentration((graph.getGraphWithPherLine(node)[nextNode] + graph.getGraphLine(node)[nextNode] * t), node, nextNode);
                node = nextNode;

            }
            for (int i = 0; i < graph.getSize(); i++) {
                for (int j = 0; j < graph.getSize(); j++) {
                    graph.setPherConcentration((1 - p) * graph.getGraphWithPherLine(node)[nextNode], node, nextNode);
                }
            }
        }
        threadCount++;
    }

    private float probabOfChoosingPath(int size, int node, int nextNode) {
        float sum = 0;
        for(int i = 0; i < size; i++) {
            if(graph.getGraphLine(node)[i] > 0) {
                sum += Math.pow(graph.getGraphWithPherLine(node)[i], a) * Math.pow(1.0 / graph.getGraphLine(node)[i], b);
            }
        }
        return (float)Math.pow(graph.getGraphWithPherLine(node)[nextNode], a) * (float)Math.pow((1.0/graph.getGraphLine(node)[nextNode]), b) / sum;
    }
}
