// Class: A2DynamicMem
// Implements Degragment in A2. No other changes should be needed for other functions.

public class A2DynamicMem extends A1DynamicMem {
      
    public A2DynamicMem() {  super(); }

    public A2DynamicMem(int size) { super(size); }

    public A2DynamicMem(int size, int dict_type) { super(size, dict_type); }

    // In A2, you need to test your implementation using BSTrees and AVLTrees. 
    // No changes should be required in the A1DynamicMem functions. 
    // They should work seamlessly with the newly supplied implementation of BSTrees and AVLTrees
    // For A2, implement the Defragment function for the class A2DynamicMem and test using BSTrees and AVLTrees. 
    //Your BST (and AVL tree) implementations should obey the property that keys in the left subtree <= root.key < keys in the right subtree. 
    // How is this total order between blocks defined? 
    // It shouldn't be a problem when using key=address since those are unique 
    // (this is an important invariant for the entire assignment123 module). 
    // When using key=size, use address to break ties i.e. if there are multiple blocks of the same size, order them by address. 
    // Now think outside the scope of the allocation problem and think of handling tiebreaking in blocks, in case key is neither of the two. 
    public void Defragment() {
        // if(this.getClass().toString() == )
        Class ofthis = freeBlk.getClass();
        if(ofthis.toString().equals("class BSTree")) {	// googled this
            Dictionary address_tree = new BSTree();
            // BSTree curr =  this;
            for(Dictionary node = freeBlk.getFirst(); node != null; node = node.getNext()) {
                address_tree.Insert(node.address, node.size, node.address);
            }
            Dictionary next = new BSTree();
            for(Dictionary node = address_tree.getFirst(); node != null; node = next) {
                next = node.getNext();
                if(next != null){
                    if(node.address + node.size == next.address) {
                        // merge
                        // remove 2 free from both
                        // add merged block to both
                        // check iterator
                        BSTree node1 = new BSTree(node.address, node.size, node.size);
                        BSTree next1 = new BSTree(next.address, next.size, next.size);
                        BSTree node2 = new BSTree(node.address,node.size,node.key);
                        BSTree next2 = new BSTree(next.address,next.size,next.key);
                        BSTree ins1 = new BSTree(node.address, node.size + next.size, node.size + next.size);
                        BSTree ins = new BSTree(node.address, node.size + next.size, node.address);
                        freeBlk.Delete(node1);
                        freeBlk.Delete(next1);
                        address_tree.Delete(node2);
                        address_tree.Delete(next2);
                        freeBlk.Insert(ins1.address, ins1.size , ins1.size);
                        next = address_tree.Insert(ins.address, ins.size , ins.address);
                    }
                }
            }
        } else {   
            Dictionary address_tree = new AVLTree();
            // AVLTree curr =  this;
            for(Dictionary node = freeBlk.getFirst(); node != null; node = node.getNext()) {
                address_tree.Insert(node.address, node.size, node.address);
            }
            Dictionary next = new AVLTree();
            for(Dictionary node = address_tree.getFirst(); node != null; node = next) {
                next = node.getNext();
                if(next != null){
                    if(node.address + node.size == next.address) {
                        // merge
                        // remove 2 free from both
                        // add merged block to both
                        // check iterator
                        AVLTree node1 = new AVLTree(node.address, node.size, node.size);
                        AVLTree next1 = new AVLTree(next.address, next.size, next.size);
                        AVLTree node2 = new AVLTree(node.address,node.size,node.key);
                        AVLTree next2 = new AVLTree(next.address,next.size,next.key);
                        AVLTree ins1 = new AVLTree(node.address, node.size + next.size, node.size + next.size);
                        AVLTree ins = new AVLTree(node.address, node.size + next.size, node.address);
                        freeBlk.Delete(node1);
                        freeBlk.Delete(next1);
                        address_tree.Delete(node2);
                        address_tree.Delete(next2);
                        freeBlk.Insert(ins1.address, ins1.size , ins1.size);
                        next = address_tree.Insert(ins.address, ins.size , ins.address);
                    }
                }
            }
        }   
        // this.defragment_bst();
        // return ;
    }

    // private void defragment_bst() {

    // FOUND BUG IN A1DYNAMIC, SO OVERRIDING
    public int Allocate(int blockSize) {
        // return -1;
        if(blockSize <= 0) return -1;
        // A1List node = freeBlk.Find(blockSize, false);
        Dictionary node = freeBlk.Find(blockSize, false);
        if(node == null) {
            return -1;
        }
        if(node.key == blockSize) {
            // insert in AMB, delete from FMB
            // delete function takes dictionary type parameter
            int t = node.address;
            Dictionary del = node; 
            freeBlk.Delete(del); // sanity option, if false something is wrong
            allocBlk.Insert(t, blockSize, t);
            return t;
        } else {
            //split and then do
            // if node.key > blockSize
            Dictionary del = node;
            int t = node.address;
            int kk = node.key;
            freeBlk.Insert(t + blockSize, kk - blockSize, kk - blockSize);
            freeBlk.Delete(del);
            allocBlk.Insert(t, blockSize, t);
            return t;
            // int size_for_new = node.key - blockSize;
            // int address_for_new = node.address + blockSize;
            // node.size = size_for_new;
            // node.address = address_for_new;
            // node.key = node.size; // as FMB
        }
        
    } 
    // return 0 if successful, -1 otherwise
    // FMB --> KEY = SIZE
    // AMB --> KEY = ADDRESS
    public int Free(int startAddr) {
        // System.out.println("here");
        if(startAddr < 0) return -1;
        Dictionary node = allocBlk.Find(startAddr, true);
        if(node == null) {
            return -1;
        }
        freeBlk.Insert(node.address, node.size, node.size);
        allocBlk.Delete(node);
        return 0;
    }
}