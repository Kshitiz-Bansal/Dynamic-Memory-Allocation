// Class: Height balanced AVL Tree
// Binary Search Tree

public class AVLTree extends BSTree {
    
    private AVLTree left, right;     // Children. 
    private AVLTree parent;          // Parent pointer. 
    private int height;  // The height of the subtree
        
    public AVLTree() { 
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node !.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
        
    }

    public AVLTree(int address, int size, int key) { 
        super(address, size, key);
        this.height = 1;
    }

    // Implement the following functions for AVL Trees.
    // You need not implement all the functions. 
    // Some of the functions may be directly inherited from the BSTree class and nothing needs to be done for those.
    // Remove the functions, to not override the inherited functions.
    


    // height(leaf) = 1

    private AVLTree go_to_senti(AVLTree node) {
        AVLTree curr = node;
        while(curr.parent != null) {
            curr = curr.parent;
        }
        return curr; 
    }

    public AVLTree Insert(int address, int size, int key) 
    { 
        AVLTree senti = go_to_senti(this);
        AVLTree ins = new AVLTree(address, size, key);
        ins.height = 1;
        // if tree empty
        if(senti.right == null) {
            senti.right = ins;
            ins.parent = senti;
            return ins;
        }
        //
        AVLTree curr = senti.right;
        AVLTree prev = null;
        while(curr != null) {
            prev = curr;
            if(curr.key == ins.key) {
                if(curr.address < ins.address) {
                    curr = curr.right;
                } else { // curr.address > ins.address
                    curr = curr.left;
                }
            } else if(curr.key < ins.key) {
                curr = curr.right;
            } else if(curr.key > ins.key) { // else also ok
                curr = curr.left;
            }
        }

        if(prev.key == ins.key) {
            if(prev.address < ins.address) {
                prev.right = ins;
                ins.parent = prev;
            } else {
                prev.left = ins;
                ins.parent = prev;
            }
        } else if(prev.key < ins.key) {
            prev.right = ins;
            ins.parent = prev;
        } else {
            prev.left = ins;
            ins.parent = prev;
        }
        insert_balance(ins);
        return ins;    
    }

    private int height(AVLTree node) {
        if(node == null) return 0;
        return node.height;
    }

    private void insert_balance(AVLTree node) {
        // node was inserted
        AVLTree curr = node.parent;
        if(curr.parent.parent == null) { // curr == root
            upd_height(curr);
            return;
        }
        AVLTree pprev = new AVLTree();
        AVLTree prev = new AVLTree();
        prev = curr;
        curr = curr.parent;
        pprev = node; // pprev -> prev -> curr

        while(curr.parent.parent != null) {
            if(Math.abs(height(curr.left) - height(curr.right)) > 1) {
                break;
            }
            upd_height(curr);
            pprev = prev;
            prev = curr;
            curr = curr.parent;
        }
        
        if(Math.abs(height(curr.left) - height(curr.right)) <= 1) {
            upd_height(curr);
            return;
        }
        // now rotate  // curr = z, prev = y, pprev = x
        AVLTree z = curr;
        AVLTree y = prev;
        AVLTree x = pprev;
        // rebalance(z, y, x);
        if(y == z.right && x == y.right) {
            //      z                       y
            //        y      -- -- >   z           x
            //          x
            left(z, y, x);
        } else if(y == z.right && x == y.left) {
            //      z
            //        y
            //      x
            right_left(z, y, x);
        } else if(y == z.left && x == y.left) {
            //      z
            //     y
            //    x
            right(z, y, x);
        } else if(y == z.left && x == y.right) {
            //      z
            //    y
            //      x   
            left_right(z, y, x);
        }


    
    }

    private void upd_height(AVLTree a) {
        a.height = 1 + Math.max(height(a.left), height(a.right));
    }

    private void left(AVLTree z, AVLTree y, AVLTree x) {
        AVLTree par = z.parent;
        // z cannot be root
        if(z.parent.right == z) {
            par.right = y;
        } else {
            par.left = y;
        }
        y.parent = par;
        z.right = y.left;
        if(y.left != null) {
            y.left.parent = z;
        }
        z.parent = y;
        y.left = z;
        upd_height(z);
        upd_height(x);
        upd_height(y);
        upd_height(y.parent);
    }

    private void right(AVLTree z, AVLTree y, AVLTree x) {
        AVLTree par = z.parent;
        // z cannot be root
        if(z.parent.right == z) {
            par.right = y;
        } else {
            par.left = y;
        }
        y.parent = par;
        z.left = y.right;
        if(y.right != null) {
            y.right.parent = z;
        }
        z.parent = y;
        y.right = z;
        upd_height(x);
        upd_height(z);
        upd_height(y);
        upd_height(y.parent);
    }

    private void left_right(AVLTree z, AVLTree y, AVLTree x) {
        y.right = x.left;
        if(x.left != null) {
            x.left.parent = y;
        }
        x.left = y;
        y.parent = x;
        x.parent = z;
        z.left = x;
        upd_height(y);
        upd_height(x);
        upd_height(z);
        right(z, x, y);
    }

    private void right_left(AVLTree z, AVLTree y, AVLTree x) {
        y.left = x.right;
        if(x.right != null) {
            x.right.parent = y;
        }
        x.right = y;
        y.parent = x;
        z.right = x;
        x.parent = z;
        upd_height(y);
        upd_height(x);
        upd_height(z);
        left(z, x, y);
    }

    public boolean Delete(Dictionary e)
    {
        AVLTree senti = go_to_senti(this);
        AVLTree root = senti.right;
        if(root == null) return false;

        while(root != null) {
            if(root.key == e.key) {
                if(root.address == e.address && root.size == e.size) {
                    root.delete_root();
                    return true;
                } else if(root.address > e.address){
                    root = root.left;
                } else if(root.address < e.address) {
                    root = root.right;
                }
            }
            else if(root.key < e.key) {
                root = root.right;
            } else { // root.key > key
                root = root.left;
            }
        }
        return false;
    }

    private void delete_root() {
        // // 3 cases --> leaf, one child, two children
        AVLTree root = this;
        AVLTree t = new AVLTree();
        if(root.left == null && root.right == null) {
            if(root.parent.left == root) {
                root.parent.left = null;
                t = root.parent;
                root.parent = null;
                root = null;
            } else {
                root.parent.right = null;
                t = root.parent;
                root.parent = null;
                root = null;
            }
            del(t);
            // root = null;
        } else if(root.left == null) {
            if(root.parent.left == root) {
                root.parent.left = root.right;
                t = root.parent;
                root.right.parent = root.parent;
                root = null;
            } else {
                root.parent.right = root.right;
                t = root.parent;
                root.right.parent = root.parent;
                root = null;
            }
            del(t);
        } else if(root.right == null) {
            if(root.parent.left == root) {
                root.parent.left = root.left;
                t = root.parent;
                root.left.parent = root.parent;
                root = null;
            } else {
                root.parent.right = root.left;
                t = root.parent;
                root.left.parent = root.parent;
                root = null;
            }
            del(t);
        } else {
            // both not null
            AVLTree temp = root.getPrev();
            // AVLTree temp = root.getNext();
            root.key = temp.key;
            root.size = temp.size;
            root.address = temp.address;
            // root = temp;
            temp.delete_root();
        }
    }

    private void del(AVLTree node) {
        AVLTree curr = node;
        upd_height(curr);
        AVLTree z = new AVLTree();
        AVLTree y = new AVLTree();
        AVLTree x = new AVLTree();
        while(curr.parent != null) {
            if(Math.abs(height(curr.left) - height(curr.right)) > 1) {
                if(height(curr.left) > height(curr.right)) {
                    // l
                    if(height(curr.left.left) > height(curr.left.right)) {
                        //ll
                        z = curr;
                        y = curr.left;
                        x = curr.left.left;
                        // right(curr, curr.left, curr.left.left);
                        right(z, y, x);
                    } else {
                        // lr
                        z = curr;
                        y = curr.left;
                        x = curr.left.right;
                        // left_right(curr, curr.left, curr.left.right);
                        left_right(z, y, x);
                    }
                } else {
                    // r
                    if(height(curr.right.left) > height(curr.right.right)) {
                        //rl
                        z = curr;
                        y = curr.right;
                        x = curr.right.left;
                        // right_left(curr, curr.right, curr.right.left);
                        right_left(z, y, x);
                    } else {
                        //rr
                        z = curr;
                        y = curr.right;
                        x = curr.right.right;
                        // left(curr, curr.right, curr.right.right);
                        left(z, y, x);
                    }
                }
            }
            if(curr.left != null) upd_height(curr.left);
            if(curr.right != null) upd_height(curr.right);
            upd_height(curr);
            curr = curr.parent;
        }
    }
    
    // had wrongly understood left <= parent < right earlier

    public AVLTree Find(int key, boolean exact)
    { 
        AVLTree curr = this;
        if(curr == null) {
            return null;
        }
        if(exact) {
            while(curr != null) {
                if(key == curr.key) {
                    if(curr.left == null) {
                        return curr;
                    } else {
                        AVLTree temp = curr.left.Find(key, true);
                        if(temp == null) return curr;
                        else return temp;
                    }
                } else if(key < curr.key) {
                    curr = curr.left;
                } else {
                    curr = curr.right;
                }
            }
        } else {
            while(curr != null) {                
                if(key == curr.key) { // < vs <=
                    if(curr.left == null) {
                        return curr;
                    } else {
                        AVLTree temp = curr.left.Find(key, true);
                        if(temp != null) return temp;
                        else return curr;
                    }
                } else if(key < curr.key) { 
                    if(curr.left == null) {
                        return curr;
                    } else {
                        curr = curr.left;
                    }
                } else { // key > curr.key
                    if(curr.right != null) {
                        curr = curr.right;
                    } else {
                        while(curr.key < key && curr != this) {
                            curr = curr.parent;
                        }
                        if(curr.key > key) {
                            return curr;
                        }
                        return null;
                    }
                }
            }
        }
        return null;
    }

    // Find in O(n) // used for debugging only

    // public AVLTree Find(int key, boolean exact) {
    //     AVLTree curr = this.getFirst();
    //     if(curr == null) {
    //         return null;
    //     }
    //     if(exact) {
    //         while(curr != null && curr.key != key) {
    //             curr = curr.getNext();
    //         }
    //         if(curr == null) {
    //             return null;
    //         }
    //         return curr;
    //     } else {
    //         while(curr != null && curr.key < key) {
    //             curr = curr.getNext();
    //         }
    //         if(curr == null) {
    //             return null;
    //         }
    //         return curr;
    //     }
    // }

    private AVLTree getPrev() {
        AVLTree curr = this;
        if(curr == null || curr.parent == null) {
            return null;
        }
        if(curr.left == null) { 
            while(curr.parent != null && curr.parent.right != curr) {
                curr = curr.parent;
            }
            if(curr.parent ==  null) return null;
            return curr.parent;
        } else {
            // curr.left.getLast()
            curr = curr.left;
            while(curr.right != null) {
                curr = curr.right;
            }
            return curr;
        }
    }

    public AVLTree getFirst() {
        AVLTree curr = this;
        AVLTree senti = go_to_senti(curr);
        AVLTree root = senti.right;
        if(root == null) return null;
        while(root.left != null) {
            root = root.left;
        }
        return root;
    }

    public AVLTree getNext() 
    {
        AVLTree curr = this;
        if(curr == null || curr.parent == null) {
            return null;
        }
        if(curr.right == null) {
            while(curr.parent != null && curr.parent.left != curr) {
                curr = curr.parent;
            }
            if(curr.parent == null) return null;
            return curr.parent;
        } else {
            // return curr.right.getFirst();            // major bug found // get first def was changed
            curr = curr.right;
            while(curr.left != null) {
                curr = curr.left;
            }
            return curr;
        }
    }

    public boolean sanity()
    { 
        AVLTree root = go_to_senti(this);
        root = root.right;
        // return true;
        boolean c1 = true,
                c2 = true,
                c3 = true,
                c4 = true;
        // 1 >>
            // sentinel
            AVLTree senti = go_to_senti(this);                
            if( senti.key != -1 ||
                senti.address != -1 ||
                senti.size != -1 || 
                senti.left != null) c1 = false;

        // 2 >>
            // bst property
            AVLTree prev = this.getFirst();
            if(prev != null) {
                for(AVLTree curr = prev.getNext(); curr != null; curr = curr.getNext()) {
                    if(!(prev.key <= curr.key)) {
                        c2 = false;
                        break;
                    }
                    if(prev.key == curr.key) {
                        if(!(prev.address < curr.address)) {
                            c2 = false;
                            break;
                        }
                    }
                    prev = curr;
                }
            }

        // 3 >> 
            // check height values && height balance
            check_bal_(root);

        // 4 >>
            // parent-child links
            for(AVLTree curr = this.getFirst(); curr != null; curr = curr.getNext()) {
                if(curr.right != null) {
                    if(!(curr.right.parent == curr)) {
                        c4 = false;
                        break;
                    }
                }
                if(curr.left != null) {
                    if(!(curr.left.parent == curr)) {
                        c4 = false;
                        break;
                    }
                }

                if(!(curr.parent.right == curr || curr.parent.left == curr)) {
                    c4 = false;
                    break;
                }
            }
        
        


        return c1 && c2 && c3 && c4;
    }

    private boolean check_bal_(AVLTree node) {
        if(node == null) return true;

        int h = 1 + Math.max(height(node.left), height(node.right));
        if(node.height != h) {
            return false;
        }
        if(Math.abs(height(node.left) - height(node.right)) > 1) {
            return false;
        }
        
        return (check_bal_(node.left) && check_bal_(node.right));
    }
}