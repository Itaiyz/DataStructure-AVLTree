/**
 *
 * AVLTree
 *
 * An implementation of a AVL Tree with distinct integer keys and info
 *
 * Submitted by:
 * 
 * Itai Zemah itaizemah 209637453
 *
 * Oded Carmon odedcarmon 208116517
 * 
 */

public class AVLTree {

	public void print2DUtil(IAVLNode root, int space) {
		// Base case
		if (root == null)
			return;

		// Increase distance between levels
		space += 10;

		// Process right child first
		print2DUtil(root.getRight(), space);

		// Print current node after space
		// count
		System.out.print("\n");
		for (int i = 10; i < space; i++)
			System.out.print(" ");
		System.out.print(root.getKey() + "," + root.getHeight() + "\n");

		// Process left child
		print2DUtil(root.getLeft(), space);
	}

	private int size = 0;
	private IAVLNode EXT = new AVLNode(); // Shared external leaf
	private IAVLNode root = EXT;

	/**
	 * public boolean empty()
	 *
	 * returns true if and only if the tree is empty
	 *
	 */
	public boolean empty() {
		return size == 0;
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
		if (empty()) {
			return null;
		}
		IAVLNode node = searchNode(k);
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
		IAVLNode x = root;
		IAVLNode y = x;
		while (x.isRealNode()) {
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
	 * Performs rotation based on given parent and child nodes (rotates on the
	 * edge between them). If child.getParent()!=parent does nothing.
	 * 
	 * Based on diagram seen in class in BST presentation on slide 31.
	 * (https://www.cs.tau.ac.il/~schechik/Data-Structures-2020/BST.pptx)
	 */
	private void rotate(IAVLNode parent, IAVLNode child) {

		if (child.getParent() != parent) {
			return;
		}

		// Ensure AVLTree's pointers are correct
		if (root == parent) {
			root = child;
			child.setParent(null);
		}
		// Ensure parent of parent's pointers are correct
		else {
			IAVLNode grandparent = parent.getParent();
			if (grandparent.getLeft() == parent) {
				grandparent.setLeft(child);
			} else {
				grandparent.setRight(child);
			}
			child.setParent(grandparent);
		}

		// Perform left or right rotation appropriately
		if (parent.getLeft() == child) {

			IAVLNode x = child;
			IAVLNode y = parent;
			IAVLNode B = x.getRight();

			x.setRight(y);
			y.setParent(x);
			y.setLeft(B);
			if (B.isRealNode()) {
				B.setParent(y);
			}
		} else {
			IAVLNode x = parent;
			IAVLNode y = child;
			IAVLNode B = y.getLeft();

			y.setLeft(x);
			x.setParent(y);
			x.setRight(B);
			if (B.isRealNode()) {
				B.setParent(x);
			}
		}

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

		if (empty()) {
			IAVLNode newNode = new AVLNode(k, i);
			newNode.setLeft(EXT);
			newNode.setRight(EXT);
			root = newNode;
			size += 1;
			return 0;
		}

		IAVLNode insertionPoint = searchNode(k);
		if (insertionPoint.getKey() == k) {
			return -1;
		}

		// Insert the node
		IAVLNode newNode = new AVLNode(k, i);
		size += 1;
		newNode.setLeft(EXT);
		newNode.setRight(EXT);
		newNode.setParent(insertionPoint);

		if (insertionPoint.getKey() > k) {
			insertionPoint.setLeft(newNode);
		} else {
			insertionPoint.setRight(newNode);
		}

		// The parent is not a leaf, no rebalancing needed
		if (insertionPoint.getHeight() > 0) {

			return 0;
		}

		// Implementing rebalancing cases
		IAVLNode y = newNode;
		IAVLNode x = insertionPoint;

		return rebalanceInsert(x, y);

	}

	/**
	 * private rebalanceInsert(IAVLNode x)
	 * 
	 * Performs all rebalancing cases for insertion into AVL tree, as appearing
	 * in WAVL presentation on slide 22
	 * https://www.cs.tau.ac.il/~schechik/Data-Structures-2020/WAVL.pptx
	 *
	 * Used by insert and join functions
	 * 
	 * @param y = x's new child
	 */
	private int rebalanceInsert(IAVLNode x, IAVLNode y) {

		int rebalanceCount = 0;

		while (x != null) {
			// If balance is restored, stop
			if (((x.getHeight() - x.getLeft().getHeight() == 1)
					&& (x.getHeight() - x.getRight().getHeight() == 1))
					|| ((x.getHeight() - x.getLeft().getHeight() == 1)
							&& (x.getHeight() - x.getRight().getHeight() == 2))
					|| ((x.getHeight() - x.getRight().getHeight() == 1)
							&& (x.getHeight()
									- x.getLeft().getHeight() == 2))) {
				return rebalanceCount;
			}

			// Case 1 (rank differences are 0,1 and so their sum is 1)
			if (2 * x.getHeight() - x.getLeft().getHeight()
					- x.getRight().getHeight() == 1) {

				x.setHeight(x.getHeight() + 1);
				rebalanceCount += 1;
				y = x;
				x = x.getParent();
			} else {
				// Case 2, with both symmetric cases
				if (((x.getLeft() == y)
						&& (y.getHeight() - y.getLeft().getHeight() == 1))
						|| ((x.getRight() == y) && (y.getHeight()
								- y.getRight().getHeight() == 1))) {

					rotate(x, y);
					rebalanceCount += 1;
					x.setHeight(x.getHeight() - 1);
					rebalanceCount += 1;

					return rebalanceCount;
				}
				// Case 3
				IAVLNode b;
				if (x.getLeft() == y) {
					b = y.getRight();
				} else {
					b = y.getLeft();
				}
				rotate(y, b);
				rebalanceCount += 1;
				rotate(x, b);
				rebalanceCount += 1;
				x.setHeight(x.getHeight() - 1);
				rebalanceCount += 1;
				y.setHeight(y.getHeight() - 1);
				rebalanceCount += 1;
				b.setHeight(b.getHeight() + 1);
				rebalanceCount += 1;

				return rebalanceCount;

			}

		}

		return rebalanceCount;

	}

	/**
	 * private IAVLNode deleteLeaf(IAVLNode node)
	 * 
	 * deletes the leaf node, returns node.getParent() to start rebalancing
	 */
	private IAVLNode deleteLeaf(IAVLNode node) {
		if (root == node) {
			node.setRight(null);
			node.setLeft(null);
			root = EXT;
			return root;
		}

		if (node.getParent().getLeft() == node) {
			node.getParent().setLeft(EXT);

		} else {
			node.getParent().setRight(EXT);
		}

		IAVLNode z = node.getParent();

		// Clearing node's pointers, as if it was newly created
		node.setParent(null);
		node.setRight(null);
		node.setLeft(null);

		return z;
	}

	/**
	 * private IAVLNode deleteUnary(IAVLNode node)
	 * 
	 * deletes the unary node, returns node.getParent() to start rebalancing
	 */
	private IAVLNode deleteUnary(IAVLNode node) {
		if (root == node) {
			if (node.getLeft().isRealNode()) {
				root = node.getLeft();
				node.getLeft().setParent(null);
			} else {
				root = node.getRight();
				node.getRight().setParent(null);
			}

			// Clearing node's pointers, as if it was newly created
			node.setParent(null);
			node.setRight(null);
			node.setLeft(null);
			return EXT;
		} else {
			if (node.getParent().getLeft() == node) {
				if (node.getLeft().isRealNode()) {
					node.getParent().setLeft(node.getLeft());
					node.getLeft().setParent(node.getParent());
				} else {
					node.getParent().setLeft(node.getRight());
					node.getRight().setParent(node.getParent());
				}
			} else {
				if (node.getLeft().isRealNode()) {
					node.getParent().setRight(node.getLeft());
					node.getLeft().setParent(node.getParent());
				} else {
					node.getParent().setRight(node.getRight());
					node.getRight().setParent(node.getParent());
				}
			}
		}
		IAVLNode z = node.getParent();

		// Clearing node's pointers, as if it was newly created
		node.setParent(null);
		node.setRight(null);
		node.setLeft(null);

		return z;
	}

	/**
	 * private IAVLNode deleteBinary(IAVLNode node)
	 * 
	 * deletes the binary node, returns a pointer to a dummy node replacing
	 * node's successor in the tree for unary deletion
	 */
	private IAVLNode deleteBinary(IAVLNode node) {
		IAVLNode successor = node.getRight();
		IAVLNode dummy = successor.getLeft();

		while (dummy.isRealNode()) {
			successor = dummy;
			dummy = dummy.getLeft();
		}

		dummy = new AVLNode();
		dummy.setParent(successor.getParent());
		dummy.setLeft(successor.getLeft());
		dummy.setRight(successor.getRight());

		if (root == node) {
			root = successor;
			successor.setParent(null);

		} else {
			successor.setParent(node.getParent());

			if (node.getParent().getLeft() == node) {
				successor.getParent().setLeft(successor);
			} else {
				successor.getParent().setRight(successor);
			}

		}

		successor.setRight(node.getRight());
		successor.setLeft(node.getLeft());
		successor.getRight().setParent(successor);
		successor.getLeft().setParent(successor);

		if (dummy.getParent().getLeft() == successor) {
			dummy.getParent().setLeft(dummy);
		} else {
			dummy.getParent().setRight(dummy);
		}

		// Only need to set right child's parent since the other is
		// definitely virtual
		if (dummy.getRight().isRealNode()) {
			dummy.getRight().setParent(dummy);
		}

		return dummy;
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

		int rebalanceCount = 0;

		if (empty()) {
			return -1;
		}

		IAVLNode node = searchNode(k);
		if (node.getKey() != k) {
			return -1;
		}

		size -= 1;

		// Remove the node appropriately, whether it is a leaf, a unary node, or
		// a binary node, and assigns the node's parent to z

		IAVLNode z = null;

		// Node is leaf
		if (!node.getLeft().isRealNode() && !node.getRight().isRealNode()) {
			z = deleteLeaf(node);
		} else {
			// Node is unary, checked using XOR operator
			if (node.getLeft().isRealNode() ^ node.getRight().isRealNode()) {
				z = deleteUnary(node);
			} else {
				// Node is binary, we find successor, switch between them,
				// and
				// then delete as unary node

				z = deleteUnary(deleteBinary(node));

			}
		}
		if (!z.isRealNode()) {
			return rebalanceCount;
		}

		if (z.getHeight() < 1) {
			throw (new RuntimeException(
					"z is someone's parent, must be at least height 1"));
		}

		// Rebalance, starting from z, going up the tree until we stop
		// having a
		// 2,2 or 3,1 node
		while ((z != null) && (2 * z.getHeight() - z.getLeft().getHeight()
				- z.getRight().getHeight() == 4)) {

			// Case 1
			if ((z.getHeight() - z.getLeft().getHeight() == 2)
					&& (z.getHeight() - z.getRight().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 1);
				if (z.getHeight() < 0) {
					throw (new RuntimeException(
							"internal node height cannot drop below 0"));
				}
				rebalanceCount += 1;
				z = z.getParent();
				continue; // Don't continue to check other cases, return to
							// start of loop
			}

			// Case 2, as appearing in the presentation
			if ((z.getHeight() - z.getLeft().getHeight() == 3)
					&& (2 * z.getRight().getHeight()
							- z.getRight().getRight().getHeight()
							- z.getRight().getLeft().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 1);
				rebalanceCount += 1;
				z.getRight().setHeight(z.getRight().getHeight() + 1);
				rebalanceCount += 1;
				rotate(z, z.getRight());
				rebalanceCount += 1;
				break;
			}

			// Case 2, mirror image
			if ((z.getHeight() - z.getRight().getHeight() == 3)
					&& (2 * z.getLeft().getHeight()
							- z.getLeft().getRight().getHeight()
							- z.getLeft().getLeft().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 1);
				rebalanceCount += 1;
				z.getLeft().setHeight(z.getLeft().getHeight() + 1);
				rebalanceCount += 1;
				rotate(z, z.getLeft());
				rebalanceCount += 1;
				break;
			}

			// Case 3, as appearing in the presentation
			if ((z.getHeight() - z.getLeft().getHeight() == 3)
					&& (z.getRight().getHeight()
							- z.getRight().getLeft().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 2);
				rebalanceCount += 2;// Should this be 2 or 1?
				rotate(z, z.getRight());
				rebalanceCount += 1;
				z = z.getParent().getParent(); // Since z's parent is now
												// one of
												// his previous children
			}

			// Case 3, mirror image
			if ((z.getHeight() - z.getRight().getHeight() == 3)
					&& (z.getLeft().getHeight()
							- z.getLeft().getRight().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 2);
				rebalanceCount += 2;// Should this be 2 or 1?
				rotate(z, z.getLeft());
				rebalanceCount += 1;
				z = z.getParent().getParent(); // Since z's parent is now
												// one of
												// his previous children
			}

			// Case 4, as appearing in the presentation
			if ((z.getHeight() - z.getLeft().getHeight() == 3)
					&& (z.getRight().getHeight()
							- z.getRight().getRight().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 2);
				rebalanceCount += 2;// Should this be 2 or 1?
				z.getRight().setHeight(z.getRight().getHeight() - 1);
				rebalanceCount += 1;
				z.getRight().getLeft()
						.setHeight(z.getRight().getLeft().getHeight() + 1);
				rebalanceCount += 1;
				rotate(z.getRight(), z.getRight().getLeft());
				rebalanceCount += 1;
				rotate(z, z.getRight());
				rebalanceCount += 1;
				z = z.getParent().getParent(); // Since z's parent is now
												// one of
												// his previous children's
												// children
			}

			// Case 4, mirror image
			if ((z.getHeight() - z.getRight().getHeight() == 3)
					&& (z.getLeft().getHeight()
							- z.getLeft().getLeft().getHeight() == 2)) {
				z.setHeight(z.getHeight() - 2);
				rebalanceCount += 2;// Should this be 2 or 1?
				z.getLeft().setHeight(z.getLeft().getHeight() - 1);
				rebalanceCount += 1;
				z.getLeft().getRight()
						.setHeight(z.getLeft().getRight().getHeight() + 1);
				rebalanceCount += 1;
				rotate(z.getLeft(), z.getLeft().getRight());
				rebalanceCount += 1;
				rotate(z, z.getLeft());
				rebalanceCount += 1;
				z = z.getParent().getParent(); // Since z's parent is now
												// one of
												// his previous children's
												// children
			}

		}

		// If deleted last node, put virtual node as root, like when
		// constructing new tree
		if (empty()) {
			root = EXT;
		}

		return rebalanceCount;

	}

	/**
	 * public String min()
	 *
	 * Returns the info of the item with the smallest key in the tree, or null
	 * if the tree is empty
	 */
	public String min() {
		if (empty()) {
			return null;
		}
		IAVLNode x = root;
		IAVLNode y = x;
		while (x.isRealNode()) {
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
		if (empty()) {
			return null;
		}
		IAVLNode x = root;
		IAVLNode y = x;
		while (x.isRealNode()) {
			y = x;
			x = x.getRight();
		}
		return y.getValue();
	}

	/**
	 * Calls an internal recursive version. Returns an array which contains all
	 * nodes in the tree, sorted by their respective keys, or an empty array if
	 * the tree is empty.
	 */
	private IAVLNode[] nodesToArray() {
		return nodesToArray(root, new IAVLNode[size()], new int[] { 0 });
	}

	/**
	 * Recursively traverses the tree in-order and adds nodes to a pre-supplied
	 * array of the proper size, each function call returns a pointer to the
	 * array up the call tree, which will eventually be returned to the
	 * non-recursive version of the function.
	 * 
	 * The array i holds a single integer which signifies what is the next index
	 * in the node array to be filled.
	 */
	private IAVLNode[] nodesToArray(IAVLNode node, IAVLNode[] arr, int[] i) {
		if (node.isRealNode()) {

			nodesToArray(node.getLeft(), arr, i);
			arr[i[0]] = node;
			i[0] += 1;
			nodesToArray(node.getRight(), arr, i);
			return arr;

		}

		return arr;

	}

	/**
	 * public int[] keysToArray()
	 *
	 * Returns a sorted array which contains all keys in the tree, or an empty
	 * array if the tree is empty.
	 */
	public int[] keysToArray() {
		IAVLNode[] nodeArr = nodesToArray();
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
		IAVLNode[] nodeArr = nodesToArray();
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
		return size;
	}

	/**
	 * public int getRoot()
	 *
	 * Returns the root AVL node, or null if the tree is empty
	 *
	 * precondition: none postcondition: none
	 */
	public IAVLNode getRoot() {
		if (empty()) {
			return null;
		}
		return root;
	}

	/**
	 * private void setSubtreeSize(AVLTree t) Calculates subtree size in
	 * O(#nodes), using nodesToArray
	 */
	private void setSubtreeSize(AVLTree t) {
		IAVLNode[] nodeArray = nodesToArray(t.getRoot(), new IAVLNode[size()],
				new int[] { 0 });
		int size = 0;
		for (IAVLNode node : nodeArray) {
			if (node != null) {
				size += 1;
			} else {
				break;
			}
		}
		t.size = size;
	}

	/**
	 * public string split(int x)
	 *
	 * splits the tree into 2 trees according to the key x. Returns an array
	 * [t1, t2] with two AVL trees. keys(t1) < x < keys(t2). precondition:
	 * search(x) != null postcondition: none
	 */
	public AVLTree[] split(int x) {
		IAVLNode node = searchNode(x);
		AVLTree left = new AVLTree();
		AVLTree right = new AVLTree();

		AVLTree t;

		left.root = node.getLeft();
		setSubtreeSize(left);

		right.root = node.getRight();
		setSubtreeSize(right);

		IAVLNode p = node.getParent();
		while (p != null) {
			t = new AVLTree();
			if (p.getRight() == node) {
				t.root = p.getLeft();
				setSubtreeSize(t);
				left.join(p, t);
			} else {
				t.root = p.getRight();
				setSubtreeSize(t);
				right.join(p, t);
			}
			node = p;
			p = p.getParent();
		}

		return new AVLTree[] { left, right };
	}

	/**
	 * public join(IAVLNode x, AVLTree t)
	 *
	 * joins t and x with the tree. Returns the complexity of the operation
	 * (1+rank difference between the tree and t) precondition: keys(x,t) <
	 * keys() or keys(x,t) > keys() postcondition: none
	 */
	public int join(IAVLNode x, AVLTree t) {

		size = size + t.size() + 1;

		if (root.getHeight() == t.getRoot().getHeight()) {
			if (x.getKey() < root.getKey()) {
				x.setRight(root);
				x.setLeft(t.getRoot());

			} else {
				x.setLeft(root);
				x.setRight(t.getRoot());
			}
			root.setParent(x);
			t.getRoot().setParent(x);
			x.setHeight(root.getHeight() + 1);
			root = x;
		}

		if (root.getHeight() > t.getRoot().getHeight()) {
			IAVLNode b = root;
			while (b.getHeight() > t.getRoot().getHeight()) {
				b = b.getLeft();
			}
			IAVLNode c = b.getParent();
			x.setParent(c);
			x.setHeight(t.getRoot().getHeight() + 1);
			if (x.getKey() < c.getKey()) {
				c.setLeft(x);
				x.setLeft(t.getRoot());
				x.setRight(b);
			} else {
				c.setRight(x);
				x.setRight(t.getRoot());
				x.setLeft(b);
			}
			t.getRoot().setParent(x);
			b.setParent(x);

			rebalanceInsert(c, x);
		}

		if (root.getHeight() < t.getRoot().getHeight()) {
			IAVLNode b = t.getRoot();
			while (b.getHeight() > root.getHeight()) {
				b = b.getLeft();
			}
			IAVLNode c = b.getParent();
			x.setParent(c);
			x.setHeight(root.getHeight() + 1);
			if (x.getKey() < c.getKey()) {
				c.setLeft(x);
				x.setLeft(root);
				x.setRight(b);
			} else {
				c.setRight(x);
				x.setRight(root);
				x.setLeft(b);
			}
			root.setParent(x);
			b.setParent(x);
			root = t.getRoot();
			rebalanceInsert(c, x);
		}

		return 1 + Math.abs(root.getHeight() - t.getRoot().getHeight());
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
			left = null;
			right = null;
			parent = null;
			realNode = true;
			height = 0;

		}

		/**
		 * public AVLNode()
		 * 
		 * Constructor for virtual node, doesn't require any additional
		 * information
		 */
		public AVLNode() {
			key = -1;
			value = null;
			left = null;
			right = null;
			parent = null;
			realNode = false;
			height = -1;

		}

		public int getKey() {
			return key; // Works for both real and virtual nodes
		}

		public String getValue() {
			return value; // Works for both real and virtual nodes
		}

		public void setLeft(IAVLNode node) {
			left = node;
		}

		public IAVLNode getLeft() {
			return left;
		}

		public void setRight(IAVLNode node) {
			right = node;
		}

		public IAVLNode getRight() {
			return right;
		}

		public void setParent(IAVLNode node) {
			parent = node;
		}

		public IAVLNode getParent() {
			return parent;
		}

		// Returns True if this is a non-virtual AVL node
		public boolean isRealNode() {
			return realNode;
		}

		public void setHeight(int height) {
			this.height = height;
		}

		public int getHeight() {
			return height;
		}

	}

}
