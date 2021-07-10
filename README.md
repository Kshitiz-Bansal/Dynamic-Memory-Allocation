# Dynamic Memory Allocation using LinkedLists and Trees

This is a java-based Dynamic memory allocator, capable of undertaking all the tasks of an actual memory allocator. Course project: COL106 (Data Structures & Algorithms) | Fall 2020

## Introduction

What is memory allocation? It is the reservation of portions of the Computer memory for execution of processes. Thus, this system will be running on our machines and it will give out memories to the programs as requested by them. (Ever used alloc or malloc in C?)

There are mainly two types of memory allocation:

1) Static Memory Allocation: The system knows the amount of memory required in advance. From this, it can be inferred that memory allocation that would take place while defining an array would be static as we specify it’s size earlier.
2) Dynamic Memory Allocation: The system does not exactly know the amount of memory required. And so in this case, it would get requested for memory Dynamically. Linked Lists creation is an example of Dynamic Memory Allocation.

This project focuses on Dynamic Allocation of Memory. Whenever a program asks for memory only the memory block that is free (or currently available) is given to that program. When a memory block is assigned to a program, it is marked as occupied so that other programs do not access it. When the program finishes its execution, the concerned memory block is freed (or made available).

The allocator has to decide on which block to give when asked for by a program. At any time, multiple addresses will be available and the allocator should efficiently choose one so as to not waste small fragments of memory and also not waste a lot of time. Some of the algorithms the allocator uses are:

1) First Fit Algorithm: This assigns the first found block which is greater than or equal to the required size. This process is good in time but bad in space utilisation.

2) Best Fit Algorithm: This assigns the block having the minimum size which is greater than or equal to the required  size. This process is slower as it has to go through the entire data structure but there is a minimum loss of memory.

The allocator implements both these algorithms. Can be toggled using a boolean.

Note: Another criterion which is extremely important for allocation of memory is that the free blocks that are contiguous should be merged into one larger free block. So a defragmentor is required. It checks for free blocks that are next to each other and combines them into larger free blocks. Running a defragmentor periodically reduces the fragmentation of memory and avoids space wastage.

<p align="center">
  <img src="img/fragmented.png" width="500"/>
</p>

# Approach and Features

First the allocator uses a doubly-linked-list as its main data structure for implementing the dictionary. It is highly optimised using Binary Search Tree and later AVL Tree.

The following functions are implemented with all the above variants:
1) Insert: To insert a new element to dictionary
2) Delete: To delete an element from the dictionary
3) Find: To find a suitable block (Changes with different algos)
4) Sanity: Checks invariants to ensure everything works fine. Raises error when something goes wrong. Very helpful for debugging.
5) getNext: To reach the next element. Used for traversal.
6) getFirst: To reach to the first element. Used for starting the traversal and holding the data structure.

# Time complexitites:

| Data Structure | Linked-List | BSTree | AVL Tree |
| ------ | ------ | ------ | ------ |
| Insert  | O(1) | O(h) = O(n) | O(h) = O(logN) |
| Delete  | O(n) | O(h) = O(n) | O(h) = O(logN) |
| Find  | O(n) | O(h) = O(n) | O(h) = O(logN) |
| Sanity  | O(n) | O(h) = O(n) | O(h) = O(n) |
| getNext  | O(1) | O(h) = O(n) | O(h) = O(logN) |
| getFirst  | O(n) | O(h) = O(n) | O(h) = O(logN) |
