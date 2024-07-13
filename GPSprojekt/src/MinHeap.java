import java.util.Arrays;
import java.util.NoSuchElementException;

public class MinHeap<T extends Comparable<T>> implements Heapish<T> {
	private T[] storage;
	private int N = 0;

	public MinHeap(T[] storage) {
		this.storage = storage;
	}

	public void insert(T data) {
		if (N >= storage.length) {
			storage = Arrays.copyOf(storage, N + 1);
		}
		int pos = N++;
		while (pos > 0) {
			int parent = (pos - 1) / 2;
			if (data.compareTo(storage[parent]) >= 0)
				break;
			storage[pos] = storage[parent];
			pos = parent;
		}
		storage[pos] = data;
	}

	public T extract() {
		if (N == 0)
			throw new NoSuchElementException("Heap is empty.");
		T result = storage[0];
		T data = storage[--N];
		int pos = 0;
		while (true) {
			int leftChild = 2 * pos + 1;
			if (leftChild >= N)
				break;
			int rightChild = leftChild + 1;
			int minChild = (rightChild < N && storage[rightChild].compareTo(storage[leftChild]) < 0) ? rightChild
					: leftChild;
			if (data.compareTo(storage[minChild]) <= 0)
				break;
			storage[pos] = storage[minChild];
			pos = minChild;
		}
		storage[pos] = data;
		return result;
	}

	public T top() {
		if (N == 0)
			throw new NoSuchElementException("Heap is empty.");
		return storage[0];
	}

	public void clear() {
		N = 0;
	}

	public int size() {
		return N;
	}

	public boolean empty() {
		return N == 0;
	}
}
