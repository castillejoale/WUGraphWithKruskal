/* VertexNode.java */

package graph;
import list.*;

/**
* This class holds the items in our DListNodes of our adjacency list for vertices.
* An instance of this object is the item of a DListNode.
* This is for an internal representation of our vertices, stored inside a DListNode.
* We need several fields that are not supported in DList/DListNodes.
**/

public class VertexNode {

	Object appVertex; // this is a reference to the object the application hashes into our internal implementation
	DList edgeList; // this is a reference to the DList of edges corresponding to each vertex
	int degreeCount; // the degree of this vertex

	
	/**
	* This constructor creates an internal representation of a node, with a reference to the application vertex.
	* @param appVertex, the object being hashed
	**/
	protected VertexNode(Object appVertex) {
		this.appVertex = appVertex;
		this.degreeCount = 0;
	}

	/**
	* This getter method returns the reference to the object of each vertex.
	**/
	protected Object getAppVertex() {
		return appVertex;
	}

	/**
	* This method adds an edge into the DList of edges for each vertex.  If there are no edges for a node, this method creates a new DList.
	* @param edgeNode, the edge to be added into our internal representation of the graph
	**/
	protected void addEdgeNode(EdgeNode edgeNode) {
		if (edgeList == null) {
			this.edgeList = new DList();
			edgeList.insertBack(edgeNode);
		} else {
			edgeList.insertBack(edgeNode);
		}
		degreeCount++;
	}
	/**
	* This getter method allows us to access the edgeList, which contains all the edges of this vertex.
	* If a vertex has no edges, this method yells at us.
	**/
	protected DList getEdgeList() {
		if (edgeList == null) {
			System.out.println("This vertex doesn't have any edges!!");
			return null;
		} else {
			return edgeList;
		}
	}

	/**
	* This getter method returns the degree count of this vertex.
	**/
	protected int getDegreeCount() {
		return degreeCount;
	}

	/**
	* This setter method sets the degree count for this vertex.
	**/
	protected void setDegreeCount(int degreeCount) {
		this.degreeCount = degreeCount;
	}


}