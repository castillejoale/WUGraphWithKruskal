/* WUGraph.java */

package graph;
import dict.*;
import list.*;

/**
 * The WUGraph class represents a weighted, undirected graph.  Self-edges are
 * permitted.
 */

public class WUGraph {
	int vertexCount; // this is all the vertices in the graph, which is n in hashtable
	int edgeCount; // this is all the edges in the graph 
	
	HashTableChained vertexHashTable;
	HashTableChained edgeHashTable;
	
	DList vertexList;  // DListNodes in the vertexList have VertexNode entries
	DList edgeList; // DListNodes in the edgeList have EdgeNode entries

	
  /**
   * WUGraph() constructs a graph having no vertices or edges.
   *
   * Running time:  O(1).
   */
  public WUGraph() {
	  vertexHashTable = new HashTableChained(); // create a hashtable with 101 buckets (default constructor)
	  vertexList = new DList();
	  
	  vertexCount = 0;
	  edgeCount = 0;
  }

  /**
   * vertexCount() returns the number of vertices in the graph.
   *
   * Running time:  O(1).
   */
  public int vertexCount() {
  	return vertexCount;
  }
  
  /**
   * edgeCount() returns the total number of edges in the graph.
   *
   * Running time:  O(1).
   */
  public int edgeCount() {
	  return edgeCount;
  }

  /**
   * getVertices() returns an array containing all the objects that serve
   * as vertices of the graph.  The array's length is exactly equal to the
   * number of vertices.  If the graph has no vertices, the array has length
   * zero.
   *
   * (NOTE:  Do not return any internal data structure you use to represent
   * vertices!  Return only the same objects that were provided by the
   * calling application in calls to addVertex().)
   *
   * Running time:  O(|V|).
   */
  public Object[] getVertices() {
	  Object[] verticesArray = new Object[vertexList.length()]; // creates an array with the same length as vertexList

	  try {
	 	 DListNode pointer = (DListNode) vertexList.front(); // begin at the beginning of vertexList
	  
	  	for(int i = 0; i < vertexList.length(); i++) {
		  verticesArray[i] = ((VertexNode) pointer.item()).getAppVertex(); // adds each vertex hashed into vertexList to the output array
		  pointer = (DListNode) pointer.next();
	  	} 			  
	  } catch (InvalidNodeException e) {}
	  return verticesArray;
  }
 

  /**
   * addVertex() adds a vertex (with no incident edges) to the graph.
   * The vertex's "name" is the object provided as the parameter "vertex".
   * If this object is already a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(1).
   */
  public void addVertex(Object vertex) {
	  if(!isVertex(vertex)) { // if it is not a vertex already, we can add it
		  VertexNode newVertexNode = new VertexNode(vertex); // the item of DListNode is a VertexNode
		  vertexList.insertBack(newVertexNode); // insert a DListNode into vertexList with VertexNode item
		  
		  // hash the vertex into correct bucket of the vertexHashTable and insert an Entry with the key = Object, value = DListNode that's part of your DList of vertices
		  vertexHashTable.insert(vertex, vertexList.back()); // pair .insertBack() with .back()
		  vertexHashTable.resize();
		  
		  vertexCount++;
	  } else { //if this object is already a vertex of the graph, the graph is unchanged
		  return;
	  }
  }
  
  /**
   * removeVertex() removes a vertex from the graph.  All edges incident on the
   * deleted vertex are removed as well.  If the parameter "vertex" does not
   * represent a vertex of the graph, the graph is unchanged.
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public void removeVertex(Object vertex) {
	  try {
	  if(!isVertex(vertex)) {
		  return;
	  } else {
		  vertexCount--;
		  
		  VertexNode vNode = (VertexNode) ((DListNode) vertexHashTable.find(vertex).value()).item();
		  
		  if(vNode.getDegreeCount() > 0) { // remove any instance of the vertex in any edge	

			  DList edgeList = vNode.getEdgeList();
			  DListNode pointer =  (DListNode) edgeList.front();

			  while(pointer.isValidNode()) {		  
				  if(((EdgeNode) pointer.item()).getEdgeType() == EdgeNode.SELF) { // self edge case
					  Object appVertex1 = ((VertexNode) ((EdgeNode) pointer.item()).getVertexOne().item()).getAppVertex();
					  removeEdge(appVertex1, appVertex1);
				  } else { // non self edge case		  
					  Object appVertex1 = ((VertexNode) ((EdgeNode)pointer.item()).getVertexOne().item()).getAppVertex();
					  Object appVertex2 = ((VertexNode) ((EdgeNode) pointer.item()).getVertexTwo().item()).getAppVertex();
					  removeEdge(appVertex1, appVertex2);					  
				  }
				  
				  pointer = (DListNode) edgeList.front();
				  
			  }
		  }
		  
		  
		  ((DListNode) vertexHashTable.find(vertex).value()).remove(); //removes the vertex from the vertex DList 
		  vertexHashTable.remove(vertex); //remove the vertex from the vertex Hashtable 
		  
	  }
	  } catch(InvalidNodeException e) {}
  }

  /**
   * isVertex() returns true if the parameter "vertex" represents a vertex of
   * the graph.
   *
   * Running time:  O(1).
   */
  public boolean isVertex(Object vertex) {
	  if(vertexHashTable.find(vertex) == null) {
		  return false;
	  } else {
		  return true;
	  }
  }

  /**
   * degree() returns the degree of a vertex.  Self-edges add only one to the
   * degree of a vertex.  If the parameter "vertex" doesn't represent a vertex
   * of the graph, zero is returned.
   *
   * Running time:  O(1).
   */
  public int degree(Object vertex) {
	  int degreeCount = 0;
	  try {
		  if(isVertex(vertex)) { //if "vertex" is not a vertex of the graph, 0 will be returned
			  degreeCount = ((VertexNode) ((DListNode) vertexHashTable.find(vertex).value()).item()).getDegreeCount();
		  }
	  } catch (InvalidNodeException e) {}
	  
	  return degreeCount;
  }

  /**
   * getNeighbors() returns a new Neighbors object referencing two arrays.  The
   * Neighbors.neighborList array contains each object that is connected to the
   * input object by an edge.  The Neighbors.weightList array contains the
   * weights of the corresponding edges.  The length of both arrays is equal to
   * the number of edges incident on the input vertex.  If the vertex has
   * degree zero, or if the parameter "vertex" does not represent a vertex of
   * the graph, null is returned (instead of a Neighbors object).
   *
   * The returned Neighbors object, and the two arrays, are both newly created.
   * No previously existing Neighbors object or array is changed.
   *
   * (NOTE:  In the neighborList array, do not return any internal data
   * structure you use to represent vertices!  Return only the same objects
   * that were provided by the calling application in calls to addVertex().)
   *
   * Running time:  O(d), where d is the degree of "vertex".
   */
  public Neighbors getNeighbors(Object vertex) {
	  if(!isVertex(vertex)) {
		  return null;
	  }
	  
	  Neighbors vertexNeighbors = new Neighbors();
	  
	  try { 

	  // get the VertexNode and degree counted associated with the given vertex
	  VertexNode vertexNode = (VertexNode) ((DListNode) vertexHashTable.find(vertex).value()).item();
	  int vertexDegreeCount = vertexNode.getDegreeCount();
	  
	  // create two empty arrays for the Neighbor class that have the same number of indices as the degree of the given vertex
	  vertexNeighbors.neighborList = new Object[vertexDegreeCount];
	  vertexNeighbors.weightList = new int[vertexDegreeCount];
	  
	  if(vertexDegreeCount == 0) { // if the vertex has no edges, then we return null 
		  return null;
	  }
	  
	  // loop through edges of the given vertex
	  DListNode pointer = (DListNode) vertexNode.getEdgeList().front();

	  for(int i = 0; i < vertexDegreeCount; i++) {
		
		 EdgeNode edgeNode = (EdgeNode) pointer.item();

		 vertexNeighbors.weightList[i] =  edgeNode.getWeight(); // set the weightList

		 // set the neighborList
		 if(edgeNode.getEdgeType() == EdgeNode.SELF) { // self edge case
			 Object appVertex1 = ((VertexNode) edgeNode.getVertexOne().item()).getAppVertex();
			 vertexNeighbors.neighborList[i] =  appVertex1;
		 } else { // non self edge case
			 Object appVertex1 = ((VertexNode) edgeNode.getVertexOne().item()).getAppVertex();
			 Object appVertex2 = ((VertexNode) edgeNode.getVertexTwo().item()).getAppVertex();
			 // make sure we're adding the vertex's neighbor, and not itself
			 if(!appVertex1.equals(vertex)) {
				 vertexNeighbors.neighborList[i] = appVertex1;
			 } else if(!appVertex2.equals(vertex)) {
				 vertexNeighbors.neighborList[i] = appVertex2;
			 }
		 }
		 pointer = (DListNode) pointer.next();
				  
	  }
	  return vertexNeighbors;
	  } catch (InvalidNodeException e) {}
	  
	  return vertexNeighbors;
  }
  	
  /**
   * addEdge() adds an edge (u, v) to the graph.  If either of the parameters
   * u and v does not represent a vertex of the graph, the graph is unchanged.
   * The edge is assigned a weight of "weight".  If the graph already contains
   * edge (u, v), the weight is updated to reflect the new value.  Self-edges
   * (where u == v) are allowed.
   *
   * Running time:  O(1).
   */
  public void addEdge(Object u, Object v, int weight) {
	  try {
	  if(!isVertex(u) || !isVertex(v)) { // graph is unchanged if neither vertices are in the graph
		  return; 
	  } else { 
		  if (edgeHashTable == null) { // initialize the edgeHashTable when adding the first edge 
			  edgeHashTable = new HashTableChained();
		  }

		  VertexPair edgeVertexPair = new VertexPair(u,v);
		  if(isEdge(u,v) ){ //if the edge already exists in the hashtable
			  if(u.equals(v)){ //if it is a self-edge
				  ((EdgeNode) ((DListNode) edgeHashTable.find(edgeVertexPair).value()).item()).setWeight(weight);
			  }else{
				  EdgeNode edgeNode = (EdgeNode) ((DListNode) edgeHashTable.find(edgeVertexPair).value()).item();
				  edgeNode.setWeight(weight);
				  ((EdgeNode) edgeNode.getPartnerEdge().item()).setWeight(weight); //set the partner's weight
			  }
		  }else{ //if the edge does not exist in the hashtable;
			  edgeCount++;
			  if(u.equals(v)) { // self edge case
				  DListNode vertexDListNode = (DListNode) vertexHashTable.find(u).value(); // find vertex from vertexHashTable

				  EdgeNode edgeNode = new EdgeNode(weight,vertexDListNode); // self edge constructor

				  ((VertexNode) vertexDListNode.item()).addEdgeNode(edgeNode); // add edge to edgeList

				  edgeHashTable.insert(edgeVertexPair, ((VertexNode) vertexDListNode.item()).getEdgeList().back()); // hash edge
				  edgeHashTable.resize(); // check to see if hash table needs to be resized
			  } else { // non self-edge case
				  DListNode vertexDListNode1 = (DListNode) vertexHashTable.find(u).value();
				  DListNode vertexDListNode2 = (DListNode) vertexHashTable.find(v).value(); // find vertices from hashtable
				  
				  EdgeNode edgeNode1 = new EdgeNode(weight,vertexDListNode1, vertexDListNode2);
				  EdgeNode edgeNode2 = new EdgeNode(weight,vertexDListNode2, vertexDListNode1); // non self edge constructor
		
				  
				  ((VertexNode) vertexDListNode1.item()).addEdgeNode(edgeNode1);
				  ((VertexNode) vertexDListNode2.item()).addEdgeNode(edgeNode2); // add edge into both vertices' edgeLists
				  
				  edgeNode1.setPartnerEdge((DListNode) ((VertexNode)vertexDListNode2.item()).getEdgeList().back());
				  edgeNode2.setPartnerEdge((DListNode) ((VertexNode)vertexDListNode1.item()).getEdgeList().back()); // set partners pointers for each vertex of the edge
				  
				  edgeHashTable.insert(edgeVertexPair, ((VertexNode) vertexDListNode1.item()).getEdgeList().back()); // hash edge
				  edgeHashTable.resize(); // check to see if hash table needs to be resized
			  }
		  }
	  }
	
	  } catch(InvalidNodeException e){}
	  
  }

  /**
   * removeEdge() removes an edge (u, v) from the graph.  If either of the
   * parameters u and v does not represent a vertex of the graph, the graph
   * is unchanged.  If (u, v) is not an edge of the graph, the graph is
   * unchanged.
   *
   * Running time:  O(1).
   */
  public void removeEdge(Object u, Object v){
	  try {
	  if(!isEdge(u,v)) {
		  return;
	  } else {
		  edgeCount--;
		  VertexPair edgeVertexPair = new VertexPair(u,v);
		  if(!u.equals(v)){ // non self edge case
			  DListNode edgeDListNode = (DListNode) edgeHashTable.find(edgeVertexPair).value();
			  DListNode partnerEdgeDListNode = ((EdgeNode) edgeDListNode.item()).getPartnerEdge();
			  
			  int vertex1Degree = ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexOne().item()).getDegreeCount();
			  int vertex2Degree = ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexTwo().item()).getDegreeCount();
			  
			  // decrease the degree count of each vertex of the edge 
			  ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexOne().item()).setDegreeCount(vertex1Degree-1);
			  ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexTwo().item()).setDegreeCount(vertex2Degree-1);
			  
			  
			  edgeDListNode.remove(); // remove DListNode from the edgelist 
			  partnerEdgeDListNode.remove(); // remove its partner
			  edgeHashTable.remove(edgeVertexPair); // remove from hash table
			  
		  } else { // self edge case
			  DListNode edgeDListNode = (DListNode) edgeHashTable.find(edgeVertexPair).value();
			  
			  // decrease the degree count of the vertex
			  int vertex1Degree = ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexOne().item()).getDegreeCount();
			  ((VertexNode) ((EdgeNode) edgeDListNode.item()).getVertexOne().item()).setDegreeCount(vertex1Degree-1);
			  
			  edgeDListNode.remove(); // remove DListNode from the edgeList 
			  edgeHashTable.remove(edgeVertexPair); // remove from hash table
			  
		  }
	  }
	  } catch(InvalidNodeException e) {}
  }

  /**
   * isEdge() returns true if (u, v) is an edge of the graph.  Returns false
   * if (u, v) is not an edge (including the case where either of the
   * parameters u and v does not represent a vertex of the graph).
   *
   * Running time:  O(1).
   */
  public boolean isEdge(Object u, Object v) {
	  VertexPair edgeVertexPair = new VertexPair(u,v);
	  if (edgeCount() == 0) { // if there are no edges in the WUGraph, (u,v) is certainly not an edge
		  return false;
	  }
	  if(edgeHashTable.find(edgeVertexPair) != null) {
		  return true;
	  } if(!isVertex(u) || !isVertex(v)) {
		  return false;
	  } else {
		  return false;
	  }
	  
  }

  /**
   * weight() returns the weight of (u, v).  Returns zero if (u, v) is not
   * an edge (including the case where either of the parameters u and v does
   * not represent a vertex of the graph).
   *
   * (NOTE:  A well-behaved application should try to avoid calling this
   * method for an edge that is not in the graph, and should certainly not
   * treat the result as if it actually represents an edge with weight zero.
   * However, some sort of default response is necessary for missing edges,
   * so we return zero.  An exception would be more appropriate, but also more
   * annoying.)
   *
   * Running time:  O(1).
   */
  public int weight(Object u, Object v) {
	  int weight = 0;
	  try {
	  if(isEdge(u,v)) {	 
		  VertexPair edgeVertexPair = new VertexPair(u,v);
		  weight = ((EdgeNode) ((DListNode) edgeHashTable.find(edgeVertexPair).value()).item()).getWeight();
	  }
	  } catch(InvalidNodeException e) {}
	  return weight;	  
}

}