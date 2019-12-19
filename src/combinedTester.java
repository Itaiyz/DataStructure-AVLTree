import java.util.Arrays;
import java.util.Random;


public class combinedTester {
	
	public static void main(String[] args) {		
		joiner();
		
		if (false) {
			//[80, 189, 19, 64, 248, 88, 89, 174, 222, 8, 231, 263, 40, 134, 25, 32, 273, 222, 55, 208, 278, 180, 252, 126, 73, 51, 54, 3, 121, 78, 64, 155, 32, 45, 144, 236, 155, 122, 240, 224, 108, 75, 59, 34, 107, 75, 176, 42, 52, 188, 213, 235, 238, 53, 16, 15, 157, 248, 130, 64, 94, 116, 230, 219, 168, 7, 95, 158, 153, 176, 117, 45, 233, 137, 262, 174, 197, 5, 143, 251, 273, 113, 187, 52, 57, 274, 216, 249, 117, 76, 183, 41, 81, 49, 65, 82, 4, 178, 48, 277]
			//1118, 988, 916, 1102, 1093, 909, 1130, 1138, 977, 971, 1065, 1083, 1171, 1068, 1109, 1081, 1017, 939, 1085, 932, 944, 1052, 1040, 955, 1099, 1094, 1101, 1142, 1092, 1051, 965, 1002, 1000, 926, 1175, 985, 1124, 1132, 917, 1006, 1013, 960, 914, 1089, 1173, 1146, 1067, 1066, 940, 1032, 1022, 1098, 934, 1063, 982, 973, 916, 1145, 945, 1079, 1014, 937, 1004, 1130, 1131, 940, 1122, 1106, 1157, 934, 995, 1041, 972, 1161, 1035, 1099, 1065, 1038, 986, 969, 934, 1046, 1158, 1100, 914, 997, 1068, 953, 975, 1044, 986, 1106, 1000, 1118, 1017, 985, 1033, 1028, 1093, 1067
			int[] arr1 = {1014, 936, 1130, 941, 1156, 900, 1068, 1126, 955, 1044, 907, 1104, 1078, 1012, 955, 981, 1057, 1119, 1168, 920, 1166, 1152, 1146, 1094, 972, 944, 1168, 1068, 913, 1051, 979, 1153, 1035, 975, 934, 966, 1158, 1083, 1034, 1176, 1161, 932, 904, 1014, 959, 1092, 1112, 1106, 1089, 1123, 1061, 909, 903, 1092, 1132, 987, 1058, 1065, 1159, 1022, 958, 1104, 1101, 1033, 1013, 983, 1171, 1002, 911, 1103, 1146, 977, 948, 907, 902, 1068, 1151, 1039, 1007, 972, 1165, 1113, 1108, 1143, 1062, 908, 952, 1051, 1006, 1009, 1011, 1173, 1047, 966, 1127, 995, 1134, 924, 1127, 1018};
			int[] arr2 = {206, 183, 207, 188, 200, 181, 186, 199, 200, 186};
			AVLTree tree1 = arrayToTree(arr1);
			AVLTree tree2 = arrayToTree(arr2);
			AVLTree.IAVLNode virtualLeaf1 = tree1.new AVLNode();
			AVLTree.IAVLNode node1 = tree1.new AVLNode(209, "");
			tree1.join(node1, tree2);
			print(isLegal(tree1));
		}
		
		splitter();
		
		
		if (false) {
			int[] arr = {125, 131, 145, 136, 131, 127, 132, 141, 137, 139};
			AVLTree tree = arrayToTree(arr);
			int splitPoint = 131;
			System.out.println("splitPoint = " + splitPoint);
			AVLTree[] trees = tree.split(splitPoint);
			AVLTree tree1 = trees[0];
			AVLTree tree2 = trees[1];
			print(isLegal(tree1) && isLegal(tree2));
			
		}
		
		System.out.println("Finished");
		
		}
	
	public static void joiner() {
		AVLTree[] trees = new AVLTree[100];
		for (int i = 0; i < 100; i++) {
			int[] arr = randomArray(100, 300*i, 300*i + 280);
			System.out.println("i = " + i);
			trees[i] = arrayToTree(arr);
			System.out.println(isLegal(trees[i]));
			if (i%2 == 1) {
				AVLTree tree = trees[i];
				AVLTree.IAVLNode virtualLeaf = tree.new AVLNode();
				AVLTree.IAVLNode node = tree.new AVLNode(300*i - 1, Integer.toString(i));
				tree.join(node, trees[i-1]);
				if (!(isLegal(tree))) {
					check(tree, trees[i-1], node);
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
			int[] arr = randomArray(100, 300*i, 300*i + 280);
			System.out.println("i = " + i);
			AVLTree tree = arrayToTree(arr);
			Random generator = new Random();
			int randomIndex = generator.nextInt(arr.length);
			int splitPoint = arr[randomIndex]; 
			System.out.println("splitPoint = " + splitPoint);
			AVLTree[] trees = tree.split(splitPoint);
			AVLTree tree1 = trees[0];
			AVLTree tree2 = trees[1];
			boolean leg =isLegal(tree1) && isLegal(tree2);
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
		rightT.root=T.root.getRight();
		rightT.size=rightT.root.getSize();

		AVLTree leftT = new AVLTree();
		leftT.root=T.root.getLeft();
		leftT.size=leftT.root.getSize();

		boolean order = true;
		if (rightT.root.isRealNode()) {
			order = (T.root.getKey() > leftT.root.getKey() &&
					T.root.getKey() < rightT.root.getKey());
		} else {
			order = (T.root.getKey() > leftT.root.getKey());
		}
		boolean pointers = ((!(T.root.getRight().isRealNode()) ||
				T.root.getRight().getParent() == T.root) &&
				(!(T.root.getLeft().isRealNode()) ||
				T.root.getLeft().getParent() == T.root));
		return (order && pointers &&
				isBST(rightT) && isBST(leftT));
		
	}
	public static boolean isLegal(AVLTree T) {
		if (!(T.root.isRealNode())) {
			return true;
		}
		AVLTree rightT = new AVLTree();
		rightT.root=T.root.getRight();
		rightT.size=rightT.root.getSize();

		AVLTree leftT = new AVLTree();
		leftT.root=T.root.getLeft();
		leftT.size=leftT.root.getSize();
		boolean rootHeight = (T.root.getHeight() ==
				max(rightT.root.getHeight(), leftT.root.getHeight()) + 1);
		boolean avl = Math.abs(rightT.root.getHeight() - leftT.root.getHeight()) <= 1;
		boolean size = (T.root.getSize() ==
				rightT.root.getSize() + leftT.root.getSize() + 1);
		boolean order = true;
		if (rightT.root.isRealNode()) {
			order = (T.root.getKey() > leftT.root.getKey() &&
					T.root.getKey() < rightT.root.getKey());
		} else {
			order = (T.root.getKey() > leftT.root.getKey());
		}
		boolean pointers = ((!(T.root.getRight().isRealNode()) ||
				T.root.getRight().getParent() == T.root) &&
				(!(T.root.getLeft().isRealNode()) ||
				T.root.getLeft().getParent() == T.root));
		boolean res =(rootHeight && avl && size && order && pointers &&
				isLegal(rightT) && isLegal(leftT));
		
		if(res==false) {
			System.out.println("problem");
		}
		return res;
	}
	
	private static int max(int height, int height2) {
		if (height > height2) {
			return height;
		}
		return height2;
	}

	private static int[] randomArray(int size, int min, int max)
	{
		int[] arr = new int[size];
		for (int i = 0; i < arr.length; i++)
		{
			arr[i] = (int)(Math.random() * (max - min) + min);
		}
		
		return arr;
	}
	
	private static AVLTree arrayToTree(int[] arr)
	{
		System.out.println("tree from " + Arrays.toString(arr));
		AVLTree tree = new AVLTree();
		for (int x : arr)
		{
			//System.out.println("inserting " + x);
			tree.insert(x, Integer.toString(x));
			//assertTrue(AVLSanitizer.sanitizeTree(tree));
		}
		
		return tree;
	}

}
