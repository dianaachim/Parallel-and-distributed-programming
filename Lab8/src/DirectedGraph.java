import java.util.ArrayList;
import java.util.List;

public class DirectedGraph {
    private List<List<Integer>> container;
    private List<Integer> nodes;

    DirectedGraph(int nodeCount) {
        container = new ArrayList<>(nodeCount);
        nodes = new ArrayList<>();

        for (int i = 0; i< nodeCount; i++) {
            container.add(new ArrayList<>());
            this.nodes.add(i);
        }
    }

    public List<Integer> getNodes() {
        return this.nodes;
    }

    public int getFirstNode() {
        return nodes.get(0);
    }

    public void addEdge(int node1, int node2) {
        this.container.get(node1).add(node2);
    }

    public List<Integer> getNodesOf(int node) {
        return this.container.get(node);
    }

    public int size() {
        return this.container.size();
    }
}
