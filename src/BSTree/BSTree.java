// Class: Implementation of BST in A2
// Implement the following functions according to the specifications provided in Tree.java

public class BSTree extends Tree {

    private BSTree left, right;     // Children.
    private BSTree parent;          // Parent pointer.
        
    public BSTree(){  
        super();
        // This acts as a sentinel root node
        // How to identify a sentinel node: A node with parent == null is SENTINEL NODE
        // The actual tree starts from one of the child of the sentinel node!.
        // CONVENTION: Assume right child of the sentinel node holds the actual root! and left child will always be null.
    }    

    public BSTree(int address, int size, int key){
        super(address, size, key); 
    }

    private BSTree go_to_senti(BSTree node) {
        BSTree curr = node;
        while(curr.parent != null) {
            curr = curr.parent;
        }
        return curr; 
    }

    public BSTree Insert(int address, int size, int key) 
    { 
        BSTree senti = go_to_senti(this);
        BSTree ins = new BSTree(address, size, key);
        // if tree empty
        if(senti.right == null) {
            senti.right = ins;
            ins.parent = senti;
            return ins;
        }
        //
        BSTree curr = senti.right;
        BSTree prev = null;
        while(curr != null) {
            // System.out.println("in insert 1");
            prev = curr;
            if(curr.key == ins.key) {
                if(curr.address < ins.address) {
                    int temp = curr.address;
                    curr.address = ins.address;
                    ins.address = temp;
                    prev = curr;
                    curr = curr.left;
                } else { // curr.address > ins.address
                    curr = curr.left;
                }
            } else if(curr.key < ins.key) {
                curr = curr.right;
            } else if(curr.key > ins.key) { // only else ok
                curr = curr.left;
            }
        }

        if(prev.key == ins.key) {
            if(prev.address < ins.address) {
                int temp = prev.address;
                prev.address = ins.address;
                ins.address = temp;
                prev.left = ins;
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
        return ins;
    }

    private void delete_root() {
        // // 3 cases --> leaf, one child, two children
        // System.out.println("in delete 2");
        BSTree root = this;
        if(root.left == null && root.right == null) {
            if(root.parent.left == root) {
                root.parent.left = null;
                root.parent = null;
                root = null;
            } else {
                root.parent.right = null;
                root.parent = null;
                root = null;
            }
            // root = null;
        } else if(root.left == null) {
            if(root.parent.left == root) {
                root.parent.left = root.right;
                root.right.parent = root.parent;
                root = null;
            } else {
                root.parent.right = root.right;
                root.right.parent = root.parent;
                root = null;
            }
        } else if(root.right == null) {
            if(root.parent.left == root) {
                root.parent.left = root.left;
                root.left.parent = root.parent;
                root = null;
            } else {
                root.parent.right = root.left;
                root.left.parent = root.parent;
                root = null;
            }
        } else {
            // both not null
            BSTree temp = root.getPrev();
            // BSTree temp = root.getNext();
            root.key = temp.key;
            root.size = temp.size;
            root.address = temp.address;
            // root = temp;
            temp.delete_root();
        }
    }


    public boolean Delete(Dictionary e)
    { 
        BSTree senti = go_to_senti(this);
        BSTree root = senti.right;
        if(root == null) return false;

        // boolean br = false;
        while(root != null) {
            // System.out.println("in delete 1");
            // if(br) break;
            // if(root == null) 
            //     return false;
            if(root.key == e.key) {
                if(root.address == e.address && root.size == e.size) {
                    // br = true;
                    root.delete_root();
                    return true;
                    // break;
                } else {
                    root = root.left;
                }
            }
            // if(br) break;
            else if(root.key < e.key) {
            // if(comp(root, e)) {
                root = root.right;
            } else { // root.key > key
                root = root.left;
            }
        }
        // now delete root
        // root.delete_root();
        return false;

    }


        
    public BSTree Find(int key, boolean exact)
    { 
        // BSTree senti = go_to_senti(this);
        // if(senti.right == null) {
        //     return null;
        // }
        // BSTree curr = senti.right;           // find in subtree  /// it will be called on head anyways
        BSTree curr = this;
        if(curr == null) {
            return null;
        }
        if(exact) {
            while(curr != null) {
                if(key == curr.key) {
                    if(curr.left == null) {
                        return curr;
                    } else {
                        BSTree temp = curr.left.Find(key, true);
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
                        BSTree temp = curr.left.Find(key, true);
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
                    if(curr.right == null) {
                        while(curr.key < key && curr != this) {
                            curr = curr.parent;
                        }
                        if(curr.key > key) {
                            return curr;
                        }
                        return null;
                    } else {
                        curr = curr.right;
                    }
                }
            }
        }
        return null;
    }

    // Find in O(n)  // used for debugging O(logn) find

    // public BSTree Find(int key, boolean exact) {
    //     BSTree curr = this.getFirst();
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

    // public BSTree getFirst()     // first go to head  // piazza
    // { 
    //     BSTree node = this;
    //     if(node == null || node.parent == null) return null;
    //     if(node.left == null) {
    //         return node;
    //     }
    //     return node.left.getFirst();
    // }

    public BSTree getFirst() {
        BSTree curr = this;
        BSTree senti = go_to_senti(curr);
        BSTree root = senti.right;
        if(root == null) return null;
        while(root.left != null) {
            root = root.left;
        }
        return root;
    }

    public BSTree getNext() 
    {
        BSTree curr = this;
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

    private BSTree getPrev() {
        BSTree curr = this;
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


    public boolean sanity()
    { 
        boolean c1 = true,
                c2 = true,
                c3 = true,
                c4 = true;
        // 1 >>
            // sentinel
            BSTree senti = go_to_senti(this);                
            if( senti.key != -1 ||
                senti.address != -1 ||
                senti.size != -1 || 
                senti.left != null) c1 = false;

        // 2 >>
            // bst property
            // c2 = help(this.getFirst(), -1, 1000000000, 1000000000);  /// M = 1000000 given
            BSTree prev = this.getFirst();
            if(prev != null) {
                for(BSTree curr = prev.getNext(); curr != null; curr = curr.getNext()) {
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
            // parent-child links
            for(BSTree curr = this.getFirst(); curr != null; curr = curr.getNext()) {
                if(curr.right != null) {
                    if(!(curr.right.parent == curr)) {
                        c3 = false;
                        break;
                    }
                }
                if(curr.left != null) {
                    if(!(curr.left.parent == curr)) {
                        c3 = false;
                        break;
                    }
                }

                if(!(curr.parent.right == curr || curr.parent.left == curr)) {
                    c3 = false;
                    break;
                }
            }
        
        return c1 && c2 && c3;
    }
}

