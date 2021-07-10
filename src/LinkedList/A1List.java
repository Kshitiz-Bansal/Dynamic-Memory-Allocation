// Implements Dictionary using Doubly Linked List (DLL)
// Implement the following functions using the specifications provided in the class List

public class A1List extends List {

    private A1List  next; // Next Node
    private A1List prev;  // Previous Node 

    public A1List(int address, int size, int key) { 
        super(address, size, key);
    }
    
    public A1List(){
        super(-1,-1,-1);
        // This acts as a head Sentinel

        A1List tailSentinel = new A1List(-1,-1,-1); // Intiate the tail sentinel
        
        this.next = tailSentinel;
        tailSentinel.prev = this;
    }

    public A1List Insert(int address, int size, int key)
    {   
// call only at head
        // if(!sanity()) {
        //     System.out.println("bad boy");
        // } 
        A1List new_node = new A1List(address, size, key);
        new_node.next = this.next;
        new_node.prev = this;
        this.next.prev = new_node;
        this.next = new_node;

        // if(!sanity()) {
        //     System.out.println("bad boy");
        // }
        return new_node;
    }

    public boolean Delete(Dictionary d) 
    {   
        // if(!sanity()) {
        //     System.out.println("bad boy");
        // }
        A1List curr = this;
        while(curr.prev != null) {
            curr = curr.prev;
        }
        // reached head sentinel node
        curr = curr.next;
        // or
        // while(curr.prev.address != -1) {
        //     curr = curr.prev;
        // }                                // cleared from piazza
        while(curr.next != null) {
            if(curr.key == d.key) {
                if((curr.address == d.address) && (curr.size == d.size)) {
                    //delete curr
                    curr.next.prev = curr.prev;
                    curr.prev.next = curr.next;
                    curr = null;

                    return true;
                }
            }
            curr = curr.next;
        }
        // if(!sanity()) {
        //     System.out.println("bad boy");
        // }
        return false;
    }

    public A1List Find(int k, boolean exact)
    { 

        // System.out.println("in find");
        A1List curr = this.getFirst();
        if(curr == null) return null;

        if(exact == true) {
            // System.out.println("ok1");
            if(curr.key == k) return curr;
            while(curr.next != null) {
                // System.out.println("ok2");
                if(curr.key == k) {
                    return curr;
                }
                curr = curr.next;
            }
        } else {    
            if(curr.key >= k) return curr;
            while(curr.next != null) {
                if(curr.key >= k) {
                    return curr;
                }
                curr = curr.next;
            }
        }
        // System.out.println("bad boy");
        return null;
    }

    public A1List getFirst()
    {
        // System.out.println("in get first");
        A1List curr = this;
        while(curr.prev != null) {
            curr = curr.prev;
        }
        // reached head sentinel node
        curr = curr.next;
        if(curr.next == null) return null;
        return curr;
    }
    
    public A1List getNext() 
    {
        A1List nex = this.next;
        if(nex.next == null) return null;                   // cleared      // doubt in (-1, -1, -1) vs null
        // if(nex.address == -1) return null;
        return nex;
    }

    public boolean sanity()
    {
        A1List first = this.getFirst();
        A1List last = first;
        boolean ok = true;
        if(last != null) {
            // list not empty
            while(last.next != null) {
                last = last.next;
            }
            last = last.prev;
            A1List head_senti = first.prev;
            A1List tail_senti = last.next;
            boolean c1 = true,
                    c2 = true,
                    c3 = true,
                    c4 = true,
                    c5 = true;
        // 1 >>
            if(head_senti.prev != null) c1 = false;
            if(tail_senti.next != null) c1 = false;

        // 2 >>
            A1List curr = first;
            while(curr.next != null) {
                if(curr.next.prev != curr) c2 = false;
                if(curr.prev.next != curr) c2 = false; 
                curr = curr.next;
            }

        // 3 >>
            A1List one = first;
            A1List two = first;
            while(two != null && two.next != null) {
                one = one.next;
                two = two.next.next;

                if(one == two) {
                    c3 = false;
                    break;
                }
            }
        
        // 4 >>
            if((head_senti.key != -1) || 
               (head_senti.address != -1) ||
               (head_senti.size != -1)) c4 = false;

            if((tail_senti.key != -1) || 
               (tail_senti.address != -1) ||
               (tail_senti.size != -1)) c4 = false;

            return c1 && c2 && c3 && c4;
            
        } else {
            // list empty
            // can still do some checks // actually no
            return true;
        }
    }

}
