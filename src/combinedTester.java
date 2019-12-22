import java.util.Arrays;
import java.util.Random;

public class combinedTester {

	public static void main(String[] args) {
//		joiner();

		if (false) {
			int[] arr1 = { 1014, 936, 1130, 941, 1156, 900, 1068, 1126, 955 };
			int[] arr2 = { 206, 183, 207, 188, 200, 181, 186, 199, 200, 186 };
			AVLTree tree1 = arrayToTree(arr1);
			AVLTree tree2 = arrayToTree(arr2);
			AVLTree.IAVLNode virtualLeaf1 = tree1.new AVLNode();
			AVLTree.IAVLNode node1 = tree1.new AVLNode(209, "");
			tree1.join(node1, tree2);
			print(isLegal(tree1));
		}

//		splitter();

		if (true) {
			// int[] arr = {67, 45, 48, 25, 53, 50, 26, 69, 43, 90};
			// int splitPoint = 53;
			// int[] arr = {145, 95, 81, 74, 32, 134, 73, 108, 274, 258, 113,
			// 46, 141, 214, 252};
			// int splitPoint = 81;
			int[] arr = { 4238, 4341, 4232, 4432, 4280, 4285, 4253, 4378, 4204,
					4408, 4309, 4386, 4280, 4248, 4340 };
			int splitPoint = 4309;
			AVLTree tree = arrayToTree(arr);

			System.out.println("splitPoint = " + splitPoint);
			AVLTree[] trees = tree.split(splitPoint);
			AVLTree tree1 = trees[0];
			AVLTree tree2 = trees[1];
			// BTreePrinter.printNode(tree1.getRoot(),"");
			// BTreePrinter.printNode(tree2.getRoot(),"");
			print(isLegal(tree1) && isLegal(tree2));

		}

		System.out.println("Finished");

	}

	public static void joiner() {
		AVLTree[] trees = new AVLTree[100];
		for (int i = 0; i < 100; i++) {
			int[] arr = randomArray(100, 300 * i, 300 * i + 280);
			System.out.println("i = " + i);
			trees[i] = arrayToTree(arr);
			System.out.println(isLegal(trees[i]));
			if (i % 2 == 1) {
				AVLTree tree = trees[i];
				AVLTree.IAVLNode virtualLeaf = tree.new AVLNode();
				AVLTree.IAVLNode node = tree.new AVLNode(300*i - 1, Integer.toString(i));
				tree.join(node, trees[i-1]);
				if (!(isLegal(tree))) {
					check(tree, trees[i - 1], node);
					return;
				}
			}
		}

	}

	public static void check(AVLTree t1, AVLTree t2, AVLTree.IAVLNode node) {
		System.out.println(node.getKey());
		t1.join(node, t2);

	}

	public static void splitter() {
		for (int i = 0; i < 100; i++) {
			// int[] arr = randomArray(100, 300*i, 300*i + 280);
			int[] arr = randomArray(10, i, 3 * i + 30);
			System.out.println("i = " + i);
			AVLTree tree = arrayToTree(arr);
			Random generator = new Random();
			int randomIndex = generator.nextInt(arr.length);
			int splitPoint = arr[randomIndex];
			System.out.println("splitPoint = " + splitPoint);
			AVLTree[] trees = tree.split(splitPoint);
			AVLTree tree1 = trees[0];
			AVLTree tree2 = trees[1];
			boolean leg = isLegal(tree1) && isLegal(tree2);
			print(leg);
			if (!(leg)) {
				return;
			}

		}

	}

	public static void print(boolean b) {
		System.out.println(b);
	}

	public static boolean isBST(AVLTree T) {
		if (!(T.root.isRealNode())) {
			return true;
		}
		AVLTree rightT = new AVLTree();
		rightT.root = T.root.getRight();
		rightT.size = rightT.root.getSize();

		AVLTree leftT = new AVLTree();
		leftT.root = T.root.getLeft();
		leftT.size = leftT.root.getSize();

		boolean order = true;
		if (rightT.root.isRealNode()) {
			order = (T.root.getKey() > leftT.root.getKey()
					&& T.root.getKey() < rightT.root.getKey());
		} else {
			order = (T.root.getKey() > leftT.root.getKey());
		}
		boolean pointers = ((!(T.root.getRight().isRealNode())
				|| T.root.getRight().getParent() == T.root)
				&& (!(T.root.getLeft().isRealNode())
						|| T.root.getLeft().getParent() == T.root));
		return (order && pointers && isBST(rightT) && isBST(leftT));

	}

	public static boolean isLegal(AVLTree T) {
		if (!(T.root.isRealNode())) {
			return true;
		}
		AVLTree rightT = new AVLTree();
		rightT.root = T.root.getRight();
		rightT.size = rightT.root.getSize();

		AVLTree leftT = new AVLTree();
		leftT.root = T.root.getLeft();
		leftT.size = leftT.root.getSize();
		boolean rootHeight = (T.root.getHeight() == max(rightT.root.getHeight(),
				leftT.root.getHeight()) + 1);
		boolean avl = Math
				.abs(rightT.root.getHeight() - leftT.root.getHeight()) <= 1;
		boolean size = (T.root.getSize() == rightT.root.getSize()
				+ leftT.root.getSize() + 1);
		boolean order = true;
		if (rightT.root.isRealNode()) {
			order = (T.root.getKey() > leftT.root.getKey()
					&& T.root.getKey() < rightT.root.getKey());
		} else {
			order = (T.root.getKey() > leftT.root.getKey());
		}
		boolean pointers = ((!(T.root.getRight().isRealNode())
				|| T.root.getRight().getParent() == T.root)
				&& (!(T.root.getLeft().isRealNode())
						|| T.root.getLeft().getParent() == T.root));
		boolean res = (rootHeight && avl && size && order && pointers
				&& isLegal(rightT) && isLegal(leftT));

		if (res == false) {
			BTreePrinter.printNode(T.getRoot(), "key");
//			BTreePrinter.printNode(T.getRoot(), "size");
			BTreePrinter.printNode(T.getRoot(), "rank");
			// System.out.println("problem: the node "+T.root.getKey()+" should
			// have size "+(rightT.root.getSize() + leftT.root.getSize() + 1)+"
			// but has size "+T.root.getSize()+" instead");
		}
		return res;
	}

	private static int max(int height, int height2) {
		if (height > height2) {
			return height;
		}
		return height2;
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
			// assertTrue(AVLSanitizer.sanitizeTree(tree));
		}

		return tree;
	}

}
