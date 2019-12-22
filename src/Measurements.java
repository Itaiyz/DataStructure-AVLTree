
	import java.util.List;
import java.util.Random;
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

		public static Number[] runExperiment1(int n) {

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
		
		public static int runExperiment2(int i, boolean random)
		{

			int[] arr= combinedTester.randomArray(10000*i, 0, 10000*i);
			AVLTree t= combinedTester.arrayToTree(arr);
			int count=0;
			if(random)
			{
				Random rnd= new Random();
				int val= rnd.nextInt(10000*i+1);
				AVLTree.IAVLNode x= t.searchNode(val);
				count= t.split1(val);
				
			}
			else
			{
				AVLTree.IAVLNode x= t.getRoot();
				if(x.getLeft()!=t.EXT && x.getLeft()!=null)
					x=x.getLeft();
				while(x.getRight().isRealNode())
				{
					x=x.getRight();
				}
				int val= x.getKey();
				count= t.split1(val);
			}
			return count;
		}

		public static void main(String[] args) {
			double [] mean=  new double[10];
			double [] most= new double[10];
			for (int i=7; i<11; i++)
			{
				double meanV=0;
				double mostV=0;
				int j=0;
				for(j=0; j<200; j++)
				{
					int res= runExperiment2(i, false);
					meanV+= res;
					if(mostV<res)
						mostV=res;
				}
				
				meanV=meanV/((double)j);
				System.out.println(i+": most: "+mostV+ " Mean: "+meanV);
				
			}
			for(int i=1; i<11; i++)
			{
				double meanV=0;
				double mostV=0;
				int j;
				for(j=0; j<200; j++)
				{
					double res= runExperiment2(i, true);
					meanV+=res;
					if(res>mostV)
						mostV=res;
				}
				meanV=meanV/((double)j);
				System.out.println(i+": most: "+mostV+ " Mean: "+meanV);
				
			}

		}

	}


