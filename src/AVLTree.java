/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with distinct integer keys and info
 *
 */

public class AVLTree {

	private IAVLNode root;
	private int size;
	private AVLTree left;
	private AVLTree right;

	public AVLTree(IAVLNode root) {
		this.root = root;
		this.left = null;
		this.right = null;
		if (root == null || root.isRealNode() == false) { // The || operator
															// will
															// short-circuit
															// when root is null
															// and so we won't
															// get a
															// nullPointerException
															// from the second
															// condition
			this.size = 0;
		} else {
			this.size = 1;
		}
	}

	public AVLTree getLeft() {
		return this.left;
	}

	public AVLTree getRight() {
		return this.right;
	}

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return this.size == 0;
	}

	/**
	 * public String search(int k)
	 *
	 * returns the info of an item with key k if it exists in the tree
	 * otherwise, returns null
	 * 
	 * uses private method searchNode(int k)
	 */
	public String search(int k) {
		if (this.empty()) {
			return null;
		}
		IAVLNode node = this.searchNode(k);
		if (node.getKey() != k) {
			return null;
		}
		return node.getValue();
	}

	/**
	 * private IAVLNode searchNode(int k)
	 * 
	 * Internal method implementing search algorithm seen in class in BST
	 * presentation on slide 17.
	 * (https://www.cs.tau.ac.il/~schechik/Data-Structures-2020/BST.pptx)
	 * 
	 * @param {int} k - key of node we're searching for.
	 * @return {IAVLNode} the desired node, with key == k , or the correct
	 *         insertion point for it if no such node exists in the tree
	 */
	private IAVLNode searchNode(int k) {
		IAVLNode x = this.root;
		IAVLNode y = x;
		while (x != null) {
			y = x;
			if (k == x.getKey()) {
				return x;
			} else {
				if (k < x.getKey()) {
					x = x.getLeft();
				} else {
					x = x.getRight();
				}
			}
		}
		return y; // If we get here, we should return the insertion point for a
					// node with key k
	}

	/**
	 * public int insert(int k, String i)
	 *
	 * inserts an item with key k and info i to the AVL tree. the tree must
	 * remain valid (keep its invariants). returns the number of rebalancing
	 * operations, or 0 if no rebalancing operations were necessary. returns -1
	 * if an item with key k already exists in the tree.
	 */
	public int insert(int k, String i) {
		return 42; // to be replaced by student code
	}

	/**
	 * public int delete(int k)
	 *
	 * deletes an item with key k from the binary tree, if it is there; the tree
	 * must remain valid (keep its invariants). returns the number of
	 * rebalancing operations, or 0 if no rebalancing operations were needed.
	 * returns -1 if an item with key k was not found in the tree.
	 */
	public int delete(int k) {
		return 42; // to be replaced by student code
	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (this.empty()) {
			return null;
		}
		IAVLNode x = this.root;
		IAVLNode y = x;
		while (x != null && x.isRealNode() == true) {
			y = x;
			x = x.getLeft();
		}
		return y.getValue();
	}

	/**
	 * public String max()
	 *
	 * Returns the info of the item with the largest key in the tree, or null if
	 * the tree is empty
	 */
	public String max() {
		if (this.empty()) {
			return null;
		}
		IAVLNode x = this.root;
		IAVLNode y = x;
		while (x != null && x.isRealNode() == true) {
			y = x;
			x = x.getRight();
		}
		return y.getValue();
	}
	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		IAVLNode[] nodeArr = this.nodesToArray();
		int[] arr = new int[nodeArr.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = nodeArr[i].getKey();
		}
		return arr;
	}

	/**
	 * public String[] infoToArray()
	 *
	 * Returns an array which contains all info in the tree, sorted by their
	 * respective keys, or an empty array if the tree is empty.
	 */
	public String[] infoToArray() {
		IAVLNode[] nodeArr = this.nodesToArray();
		String[] arr = new String[nodeArr.length];
		for (int i = 0; i < arr.length; i++) {
			arr[i] = nodeArr[i].getValue();
		}
		return arr;
	}

	/**
	 * public int size()
	 *
	 * Returns the number of nodes in the tree.
	 *
	 * precondition: none postcondition: none
	 */
	public int size() {
		return this.size;
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 *
	 * precondition: none postcondition: none
	 */
	public IAVLNode getRoot() {
		return this.root;
	}

	/**
	 * public string split(int x)
	 *
	 * splits the tree into 2 trees according to the key x. Returns an array
	 * [t1, t2] with two AVL trees. keys(t1) < x < keys(t2). precondition:
	 * search(x) != null postcondition: none
	 */
	public AVLTree[] split(int x) {
		return null;
	}

	/**
	 * public join(IAVLNode x, AVLTree t)
	 *
	 * joins t and x with the tree. Returns the complexity of the operation
	 * (rank difference between the tree and t) precondition: keys(x,t) < keys()
	 * or keys(x,t) > keys() postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {
		return 0;
	}

	/**
	 * public interface IAVLNode ! Do not delete or modify this - otherwise all
	 * tests will fail !
	 */
	public interface IAVLNode {
		public int getKey(); // returns node's key (for virtuval node return -1)

		public String getValue(); // returns node's value [info] (for virtuval
									// node return null)

		public void setLeft(IAVLNode node); // sets left child

		public IAVLNode getLeft(); // returns left child (if there is no left
									// child return null)

		public void setRight(IAVLNode node); // sets right child

		public IAVLNode getRight(); // returns right child (if there is no right
									// child return null)

		public void setParent(IAVLNode node); // sets parent

		public IAVLNode getParent(); // returns the parent (if there is no
										// parent return null)

		public boolean isRealNode(); // Returns True if this is a non-virtual
										// AVL node

		public void setHeight(int height); // sets the height of the node

		public int getHeight(); // Returns the height of the node (-1 for
								// virtual nodes)
	}

	/**
	 * public class AVLNode
	 *
	 * If you wish to implement classes other than AVLTree (for example
	 * AVLNode), do it in this file, not in another file. This class can and
	 * must be modified. (It must implement IAVLNode)
	 */
	public class AVLNode implements IAVLNode {

		private int key;
		private String value;
		private IAVLNode left;
		private IAVLNode right;
		private IAVLNode parent;
		private boolean realNode;
		private int height;

		/**
		 * public AVLNode(int key, String value)
		 * 
		 * Constructor for real node, with all relevant information passed as
		 * arguments
		 */
		public AVLNode(int key, String value) {
			this.key = key;
			this.value = value;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.realNode = true;
			this.height = 0;
		}

		/**
		 * public AVLNode()
		 * 
		 * Constructor for virtual node, doesn't require any additional
		 * information
		 */
		public AVLNode() {
			this.key = -1;
			this.value = null;
			this.left = null;
			this.right = null;
			this.parent = null;
			this.realNode = false;
			this.height = -1;
		}

		public int getKey() {
			return this.key; // Works for both real and virtual nodes
		}

		public String getValue() {
			return this.value; // Works for both real and virtual nodes
		}

		public void setLeft(IAVLNode node) {
			this.left = node;
		}

		public IAVLNode getLeft() {
			return this.left;
		}

		public void setRight(IAVLNode node) {
			this.right = node;
		}

		public IAVLNode getRight() {
			return this.right;
		}

		public void setParent(IAVLNode node) {
			this.parent = node;
		}

		public IAVLNode getParent() {
			return this.parent;
		}

		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode() {
			return this.realNode;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getHeight() {
			return this.height;
		}
	}

}
