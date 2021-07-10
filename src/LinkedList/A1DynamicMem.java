// Class: A1DynamicMem
// Implements DynamicMem
// Does not implement defragment (which is for A2).

public class A1DynamicMem extends DynamicMem {
      
    public A1DynamicMem() {
        super();
    }

    public A1DynamicMem(int size) {
        super(size);
    }

    public A1DynamicMem(int size, int dict_type) {
        super(size, dict_type);
    }

    public void Defragment() {
        return ;
    }

    // In A1, you need to implement the Allocate and Free functions for the class A1DynamicMem
    // Test your memory allocator thoroughly using Doubly Linked lists only (A1List.java).
    // While inserting into the list, only call insert at the head of the list
    // Please note that ALL insertions in the DLL (used either in A1DynamicMem or used independently as the 'dictionary' class implementation) 
    // e to be made at the HEAD (from the front).
    // Also, the find-first should start searching from the head (irrespective of the use for A1DynamicMem).
     // Similar arguments will follow with regards to the ROOT in the case of trees 
    // (specifying this in case it was not so trivial to anyone of you earlier)
    public int Allocate(int blockSize) {
        // return -1;
        // A1List node = freeBlk.Find(blockSize, false);
        Dictionary node = freeBlk.Find(blockSize, false);
        if(node == null) {
            return -1;
        }
        // if node.key > blockSize
        if(node.key == blockSize) {
            // insert in AMB, delete from FMB
            // delete function takes dictionary type parameter
            Dictionary del = node; // no need to cast as it is upcasting
            freeBlk.Delete(del); // sanity option, if false something is wrong
            allocBlk.Insert(node.address, blockSize, node.address);
            return node.address;
        } else {
            //split and then do
            Dictionary del = node;
            freeBlk.Delete(del);
            allocBlk.Insert(node.address, blockSize, node.address);
            freeBlk.Insert(node.address + blockSize, node.key - blockSize, node.key - blockSize);
            return node.address;
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
        Dictionary node = allocBlk.Find(startAddr, true);
        if(node == null) {
            return -1;
        }
        freeBlk.Insert(node.address, node.size, node.size);
        allocBlk.Delete(node);
        return 0;
    }
}