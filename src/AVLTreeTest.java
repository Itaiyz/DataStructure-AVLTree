
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

//import AVLTree.AVLNode;

class AVLTreeTest {

	public static void assertTrue(boolean b) {
		assert b == true;
	}

	public static void assertEquals(String a, String b) {
		if (a == null && b == null) {
			return;
		}
		if (a == null) {
			System.err.println("invalid: " + a + "!=" + b);
		}
		if (b == null) {
			System.err.println("invalid: " + a + "!=" + b);
		}
		if (!a.equals(b)) // String equality is not tested with != but rather
							// with the equals function
			System.err.println("invalid: " + a + "!=" + b);
	}

	public static void assertEquals(AVLTree.IAVLNode nd1,
			AVLTree.IAVLNode nd2) {
		if (nd1 == null && nd2 == null) {
			return;
		}
		if (nd1 == null) {
			System.err.println("invalid: " + nd1 + "!=" + nd2);
		}
		if (nd2 == null) {
			System.err.println("invalid: " + nd1 + "!=" + nd2);
		}
		if (!nd1.equals(nd2))
			System.err.println("invalid" + "(" + nd1.getKey() + ", "
					+ nd1.getValue() + ")" + "!= " + "(" + nd2.getKey() + ", "
					+ nd2.getValue() + ")");
	}

	public static void assertEquals(int a, int b) {
		if (a != b)
			System.err.println("invalid: " + a + "!=" + b);
	}

	public void assertFalse(boolean b) {
		assert b == false;
	}

	public void assertIntEquals(int a, int b) {
		assert a == b;
	}

	public void assertStringEquals(String a, String b) {
		assert a.equals(b);
	}

	public void assertArrayIntEquals(int[] arr1, int[] arr2) {
		assert arr1.length == arr2.length;
		for (int i = 0; i < arr1.length; i++) {
			assert arr1[i] == arr2[i];
		}
	}

	public void assertArrayStringEquals(String[] strings, String[] strings2) {
		assert strings.length == strings2.length;
		for (int i = 0; i < strings.length; i++) {
			assertStringEquals(strings[i], strings2[i]);
		}
	}

	public void assertArrayStringEquals(String[] strings, Object[] objects2) {
		assert strings.length == objects2.length;
		for (int i = 0; i < strings.length; i++) {
			assert strings[i].equals(objects2[i]);
		}
	}

	private static int[] randomArray(int size, int min, int max) {
		int[] arr = new int[size];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = (int) (Math.random() * (max - min) + min);
		}

		return arr;
	}

	private static AVLTree arrayToTree(int[] arr) {
		System.out.println("tree from " + Arrays.toString(arr));
		AVLTree tree = new AVLTree();
		for (int x : arr) {
			// System.out.println("inserting " + x);
			tree.insert(x, Integer.toString(x));
			assert AVLSanitizer.sanitizeTree(tree) == true;
		}

		return tree;
	}

	static // @Test
	void testEmpty() {
		AVLTree tree = new AVLTree();

		assert tree.empty() == true;

		tree.insert(1, "1");
		assert tree.empty() == false;

		tree.delete(1);
		assertTrue(tree.empty());
	}

	// @Test
	static void testSearch() {
		AVLTree tree = new AVLTree();
		if (tree.search(1) != null)
			System.err.println("Invalid: tree.search(1)!=null ");

		tree.insert(1, "1");

		assertEquals(tree.search(1), "1");

		tree.delete(1);
		assertEquals(tree.search(1), null);
	}

	// @Test
	static void testInsert() {
		AVLTree tree = new AVLTree();
		System.out.println(tree.insert(1, "1") + ": " + 0);
		System.out.println(tree.insert(1, "1") + ": " + -1);
		System.out.println(tree.insert(2, "2") + ": " + 0);
		// RR
		// This next line used to say 1, but there are 3 rebalance operations
		// because of
		// 2 demotes
		System.out.println(tree.insert(3, "3") + ": " + 3);

		AVLTree.print2DUtil(tree.getRoot(), 0);
	}

	// @Test
	static void testDelete() {
		AVLTree tree = new AVLTree();
		if (tree.delete(-1) != -1)
			System.err.println("wrong output in node not exist: Delete");

		tree.insert(1, "1");
		if (tree.delete(1) != 0)
			System.err.println("wrong output in delete: tree.delete(1)!=0");

		tree.insert(2, "2");
		tree.insert(1, "1");
		tree.insert(3, "3");
		tree.insert(4, "4");

		if (tree.delete(1) != 3) // Old test expected 1, this semsester's
									// requirements as explained in forum should
									// expect 3 since we perform 1 rotation and
									// demote twice
			System.err.println("wrong output in delete: tree.delete(1)!=3");

		assertTrue(AVLSanitizer.sanitizeTree(tree));

		int[] values = randomArray(100, 0, 100);
		// int[] values = randomArray(10, 0, 100);
		tree = arrayToTree(values);
		// AVLTree.print2DUtil(tree.getRoot(), 0);
		List<Integer> valuesShuffled = Arrays.stream(values).boxed()
				.collect(Collectors.toList());
		Collections.shuffle(valuesShuffled);
		for (int x : valuesShuffled) {
			System.out.format("deleting %d%n", x);
			tree.delete(x);
			assertTrue(AVLSanitizer.sanitizeTree(tree));
		}
	}

	// Simulate random insert and delete operations
	// @Test
	static void testInsertAndDelete() {
		AVLTree tree = new AVLTree();

		for (int tries = 0; tries < 50; tries++) {
			int[] values = randomArray(100, -30, 30);

			List<Integer> valuesShuffled = Arrays.stream(values).boxed()
					.collect(Collectors.toList());
			Collections.shuffle(valuesShuffled);

			List<Integer> valuesShuffled2 = new ArrayList<Integer>(
					valuesShuffled);
			Collections.shuffle(valuesShuffled2);
			for (int j = 0; j < 5; j++) {
				for (int i = 0; i < valuesShuffled.size(); i++) {
					tree.insert(valuesShuffled.get(i),
							Integer.toString(valuesShuffled.get(i)));
					tree.delete(valuesShuffled2.get(i));
					assertTrue(AVLSanitizer.sanitizeTree(tree));
				}
			}
			for (int x : valuesShuffled) {
				if (x < 0)
					tree.delete(-x);
				else
					tree.insert(x, Integer.toString(x));
			}
		}
	}

	static // @Test
	void testMinMax() {
		for (int i = 0; i < 10; i++) {
			int[] values = randomArray(100, 0, 100);

			System.out.println(Arrays.toString(values));
			AVLTree tree = arrayToTree(values);
			values = Arrays.stream(values).distinct().toArray();
			Arrays.sort(values);
			System.out.println(Arrays.toString(values));

			System.out.println("checking min max");
			assertEquals(tree.max(),
					Integer.toString(values[values.length - 1]));
			assertEquals(tree.min(), Integer.toString(values[0]));

			System.out.println("deleting min max");
			tree.delete(Integer.parseInt(tree.max()));

			assertEquals(tree.max(),
					Integer.toString(values[values.length - 2]));

			tree.delete(Integer.parseInt(tree.min()));
			assertEquals(tree.min(), Integer.toString(values[1]));
		}

		System.out.println("checking empty tree");
		// Empty tree
		AVLTree tree = new AVLTree();
		assertEquals(tree.max(), null);
		assertEquals(tree.min(), null);
	}

	// @Test
	void testKeysToArray() {
		int[] values = randomArray(100, 0, 100);
		AVLTree tree = arrayToTree(values);
		Arrays.sort(values);

		assertArrayIntEquals(tree.keysToArray(),
				Arrays.stream(values).distinct().toArray());
	}

	// @Test
	void testInfoToArray() {
		int[] values = randomArray(100, 0, 100);
		AVLTree tree = arrayToTree(values);
		Arrays.sort(values);

		assertArrayStringEquals(tree.infoToArray(), Arrays.stream(values)
				.distinct().mapToObj(x -> Integer.toString(x)).toArray());
	}

	static // @Test
	void testSize() {
		int[] values = randomArray(100, 0, 100);
		AVLTree tree = arrayToTree(values);
		int realSize = (int) Arrays.stream(values).distinct().count();

		System.out.println(realSize + " vs " + tree.size());
		assertEquals(tree.size(), realSize);
	}

	// @Test
	static void testGetRoot() {
		AVLTree tree = new AVLTree();
		assertEquals(null, tree.getRoot());

		tree.insert(1, "1");
		assertEquals(1, tree.getRoot().getKey());

		tree.insert(2, "2");
		assertEquals(1, tree.getRoot().getKey());

		tree.delete(1);
		assertEquals(2, tree.getRoot().getKey());

		assertEquals(null, tree.getRoot().getParent());
	}

	/*
	 * @Test void testSelect() { { AVLTree tree = new AVLTree();
	 * assertEquals(null, tree.select(0)); }
	 * 
	 * int[] values = randomArray(100, 0, 100); AVLTree tree =
	 * arrayToTree(values); Arrays.sort(values);
	 * 
	 * values = Arrays.stream(values).distinct().toArray(); for (int i = 0; i <
	 * values.length; i++) {
	 * //System.out.format("select %d received %s should be %d%n", i,
	 * tree.select(i + 1), values[i]); assertEquals(tree.select(i + 1),
	 * Integer.toString(values[i])); }
	 * 
	 * assertEquals(tree.select(values.length + 1), null);
	 * assertEquals(tree.select(0), null); }
	 * 
	 * @Test void testLess() { { AVLTree tree = new AVLTree(); assertEquals(0,
	 * tree.less(0)); }
	 * 
	 * int[] values = randomArray(100, 0, 100); AVLTree tree =
	 * arrayToTree(values); Arrays.sort(values); values =
	 * Arrays.stream(values).distinct().toArray();
	 * 
	 * int sum = 0; for (int i = 0; i < values.length; i++) { sum += values[i];
	 * assertEquals(tree.less(values[i]), sum); }
	 * 
	 * assertEquals(tree.less(values[values.length - 1] + 1), sum); }
	 */

	public static void main(String[] args) {

		/*testEmpty();
		testSearch();
		testInsert();
		testDelete();
		testMinMax();
		testSize();
		testGetRoot();*/
		
		AVLTree t = new AVLTree();
		int k;
		String i="";
		for(k=0;k<11;k++) {
			t.insert(k, i);
		}
		BTreePrinter.printNode(t.getRoot(), "");

	}
}
