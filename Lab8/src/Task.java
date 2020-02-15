import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Lock;

public class Task implements Runnable {
    private DirectedGraph graph;
    private int startingNode;
    private int secondNode;
    private Lock lock;
    private List<Integer> result;
    private List<Integer> path;

    public Task(DirectedGraph graph, int startingNode, int secondNode, Lock lock, List<Integer> result) {
        this.graph = graph;
        this.startingNode = startingNode;
        this.secondNode = secondNode;
        this.lock = lock;
        this.result = result;
        this.path = new ArrayList<>();
        this.path.add(startingNode);
    }
    private void setResult() {
        this.lock.lock();
        this.result.clear();
        this.result.addAll(this.path);
        this.lock.unlock();
    }


    private void visit(int node) {
        this.path.add(node);
        if (this.path.size() == this.graph.size()) {
            if (this.graph.getNodesOf(node).contains(startingNode)) {
                setResult();
            }
            return;
        }
        for (int neighbour: graph.getNodesOf(node)) {
            if (!path.contains(neighbour)) {
                visit(neighbour);
            }
        }
    }

    @Override
    public void run() {
        visit(secondNode);
    }
}
