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

	public int[] runExperiment(int i) {
		int n = i*10000;
		
		List<Integer> keysToInsert = IntStream.range(0, n).boxed().collect(Collectors.toList());
		java.util.Collections.shuffle(keysToInsert);
		
		return null;
	}
	
}
