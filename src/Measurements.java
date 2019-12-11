import java.util.List;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

/**
 *
 * AVLTree
 *
 * Measuring the performance of an AVL Tree with distinct integer keys and info
 *
 * Submitted by:
 * 
 * Itai Zemah itaizemah 209637453
 *
 * Oded Carmon odedcarmon 208116517
 * 
 */

public class Measurements {

	public static Number[] runExperiment(int n) {

		List<Integer> keysToInsert = IntStream.range(0, n).boxed()
				.collect(Collectors.toList());
		java.util.Collections.shuffle(keysToInsert);

		AVLTree t = new AVLTree();

		int maxInsert = 0;
		int totalInsert = 0;
		int currentInsert;

		for (int j = 0; j < n; j++) {
			currentInsert = t.insert(keysToInsert.get(j),
					Integer.toString(keysToInsert.get(j)));
			if (currentInsert > maxInsert) {
				maxInsert = currentInsert;
			}
			totalInsert += currentInsert;
		}

		int maxDelete = 0;
		int totalDelete = 0;
		int currentDelete;

		for (int j = n - 1; j >= 0; j--) {
			currentDelete = t.delete(j);
			if (currentDelete > maxDelete) {
				maxDelete = currentDelete;
			}
			totalDelete += currentDelete;
		}

		return new Number[] { (float) totalInsert / n, (float) totalDelete / n,
				maxInsert, maxDelete };
	}

	public static void main(String[] args) {
		int[] problemSequence= {3,1,9,7,2,0,8,6,4,5};
		AVLTree t = new AVLTree();
		for(int i:problemSequence) {
			t.insert(i, Integer.toString(i));
		}
		for(int j=9;j>=-1;j--) {
			t.delete(j);
		}
		
		Number[] res = runExperiment(10);
		for (Number num : res) {
			System.out.print(num + " ");
		}
		System.out.println();

		for (int i = 1; i < 11; i++) {
			res = runExperiment(i * 10000);
			for (Number num : res) {
				System.out.print(num + " ");
			}
			System.out.println();
		}
	}

}
