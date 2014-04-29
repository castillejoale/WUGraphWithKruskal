/* Kruskal.java */

package graphalg;

import graph.*;
import set.*;
import list.*;
import dict.*;


/**
 * The Kruskal class contains the method minSpanTree(), which implements
 * Kruskal's algorithm for computing a minimum spanning tree of a graph.
 */

public class Kruskal {

  /**
   * minSpanTree() returns a WUGraph that represents the minimum spanning tree
   * of the WUGraph g.  The original WUGraph g is NOT changed.
   *
   * @param g The weighted, undirected graph whose MST we want to compute.
   * @return A newly constructed WUGraph representing the MST of g.
   */
  public static WUGraph minSpanTree(WUGraph g){

  	// 1: Create new graph t having the same vertices as G, but no edges yet.

    WUGraph t = new WUGraph();

  	for (Object item : g.getVertices()) {
    	t.addVertex(item);
	  }

  	// 2: Make a list of all edges in G by calling multiple times getNeighbors()

  	Object[] verticesList = t.getVertices();

    LinkedQueue queue = new LinkedQueue();

    for(int i = 0; i < t.vertexCount(); i++){
      Neighbors neighbor = g.getNeighbors(verticesList[i]);      
      for (int j = 0; j < neighbor.neighborList.length; j++){
        Object v1 = verticesList[i];
        Object v2 = neighbor.neighborList[j];
        int w = g.weight(v1, v2);
        Edge edge = new Edge(v1, v2, w);     
        queue.enqueue(edge);
      }
    }
 

  	// 3: Sort the edges by weight in O(E*log(E)) time

    mergeSort(queue);
    
  	// 4: Finally, find the edges of the minimun spanning tree t using disjoint sets.

    int queueSize = queue.size();
    DisjointSets ds = new DisjointSets(t.vertexCount());
    HashTableChained hashTable = new HashTableChained(t.vertexCount());

    
    //Insert the vertices in the hashtable. The hashCode will be its position in
    // the verticesList list.
    int index = 0;

    for (Object v : verticesList){
      hashTable.insert(v, index);
      index++;
    }

    //Iterate over the edges in the queue, adding them if the vertices of the edge
    // belong to different sets
    for (int i = 0; i < queueSize; i++){
      try{
        Edge ed = (Edge) queue.dequeue();
        Object vertex1 = ed.getVertex1();
        Object vertex2 = ed.getVertex2();
        int v1 = (Integer) hashTable.find(vertex1).value();
        int v2 = (Integer) hashTable.find(vertex2).value();
        if (ds.find(v1) == ds.find(v2)){
        } else {
          t.addEdge(ed.getVertex1(), ed.getVertex2(), ed.getWeight());
          ds.union(ds.find(v1),ds.find(v2));
        }
      } catch (QueueEmptyException e){}
    }

    return t;

	}


  /**
   *  mergeSort() sorts q from smallest to largest using mergesort.
   *  @param q is a LinkedQueue of Comparable objects.
   **/
  public static void mergeSort(LinkedQueue q) {

    if (q.size() <= 1){
      return;
    }

    LinkedQueue qoq = makeQueueOfQueues(q);

    try{
      while(qoq.size() > 1){
        LinkedQueue item1 = (LinkedQueue) qoq.dequeue();
        LinkedQueue item2 = (LinkedQueue) qoq.dequeue();
        LinkedQueue newQ = mergeSortedQueues(item1,item2);
        qoq.enqueue(newQ);
      }
      q.append((LinkedQueue)qoq.front());
    } catch (QueueEmptyException e){}

  }

  /**
   *  makeQueueOfQueues() makes a queue of queues, each containing one item
   *  of q.  Upon completion of this method, q is empty.
   *  @param q is a LinkedQueue of objects.
   *  @return a LinkedQueue containing LinkedQueue objects, each of which
   *    contains one object from q.
   **/
  public static LinkedQueue makeQueueOfQueues(LinkedQueue q) {

    LinkedQueue newQ = new LinkedQueue();
    int initialSize = q.size();

    try{
      for(int i = 0; i < initialSize; i++){
        LinkedQueue inQ = new LinkedQueue();
        inQ.enqueue(q.front());
        newQ.enqueue(inQ);
        q.dequeue();
      }
    } catch (QueueEmptyException e){}
    return newQ;
  }

  /**
   *  mergeSortedQueues() merges two sorted queues into a third.  On completion
   *  of this method, q1 and q2 are empty, and their items have been merged
   *  into the returned queue.
   *  @param q1 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @param q2 is LinkedQueue of Comparable objects, sorted from smallest 
   *    to largest.
   *  @return a LinkedQueue containing all the Comparable objects from q1 
   *   and q2 (and nothing else), sorted from smallest to largest.
   **/
  public static LinkedQueue mergeSortedQueues(LinkedQueue q1, LinkedQueue q2) {

    LinkedQueue newQ = new LinkedQueue();

    try{
      while (!q1.isEmpty() && !q2.isEmpty()){
        if (((Comparable)q1.front()).compareTo((Comparable)q2.front()) == -1){
          newQ.enqueue(q1.front());
          q1.dequeue();
        } else {
          newQ.enqueue(q2.front());
          q2.dequeue();
        }
      }
      while(!q1.isEmpty()) {
          newQ.enqueue(q1.front());
          q1.dequeue();
      }
      while(!q2.isEmpty()){
          newQ.enqueue(q2.front());
          q2.dequeue();
      }
    } catch (QueueEmptyException e){}

    return newQ;
    
  }

}