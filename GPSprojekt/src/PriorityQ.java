import java.lang.reflect.Array;

public class PriorityQ<DATA, WEIGHT extends Comparable<WEIGHT>> {
	private Heapish<Node> heap;

	public PriorityQ(int capacity) {
		@SuppressWarnings("unchecked")
		Node[] array = (Node[]) Array.newInstance(Node.class, capacity);
		heap = new MinHeap<Node>(array);
	}

	public int size() {
		return heap.size();
	}

	public void insert(DATA data, WEIGHT weight) {
		Node node = new Node(data, weight);
		heap.insert(node);
	}

	public DATA extract() {
		Node nynode = heap.extract();
		return nynode.getData();
	}

	private class Node implements Comparable<Node> {
		private DATA data;
		private WEIGHT weight;

		public Node(DATA data, WEIGHT weight) {
			this.data = data;
			this.weight = weight;
		}

		public DATA getData() {
			return data;
		}

		public WEIGHT getWeight() {
			return weight;
		}

		@Override
		public int compareTo(Node other) {
			return getWeight().compareTo(other.weight);
		}
	}
}
