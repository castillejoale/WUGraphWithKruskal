/** EdgeNode.java **/

package graph;
import list.*;

/**
* This class holds the items in our DListNodes of our adjacency list for edges.
* An instance of this object is the item of a DListNode.
* This is for an internal representation of our edges, stored inside a DListNode.
* We need several fields that are not supported in DList/DListNodes.
**/

public class EdgeNode {

	protected int weight;
	protected DListNode vertexOne; // one of the vertices of the edge
	protected DListNode vertexTwo; // the other vertex of the edge, this is null of the edge is a self edge
	protected DListNode partnerEdge; // this is a reference to the other DListNode for the edge in the other vertex's adjacency list, which is null if the edge is a self edge

	public final static int SELF = 0;
	public final static int NONSELF = 1;

	protected int edgeType;

	/**
	* This two parameter constructor creates an EdgeNode for a self edge.
	**/
	protected EdgeNode(int weight, DListNode vertexOne) {
		this.edgeType = SELF;

		this.weight = weight;
		this.vertexOne = vertexOne;
	}

	/**
	* This three parameter constructor creates an EdgeNode for a non self edge.
	**/
	protected EdgeNode(int weight, DListNode vertexOne, DListNode vertexTwo) {
		this.edgeType = NONSELF;

		this.weight = weight;
		this.vertexOne = vertexOne;
		this.vertexTwo = vertexTwo;
	}

	/**
	* This getter method returns the edge type of this edge.
	* This method either returns SELF or NONSELF edge type.
	**/
	protected int getEdgeType() {
		return this.edgeType;
	}


	/**
	* This getter method returns the weight of this edge.
	**/
	protected int getWeight() {
		return weight;
	}

	/**
	* This setter method updates the weight of this edge.
	* @param weight, the new weight of the edge, passed in as an int
	**/
	protected void setWeight(int weight) {
		this.weight = weight;
	}

	/**
	* This method returns one vertex of this edge.
	**/
	protected DListNode getVertexOne() {
		return vertexOne;
	}

	/**
	* This method returns the other vertex of this edge.  If the edge is a self-edge, this method should not be used, and would return null.
	**/
	protected DListNode getVertexTwo() {
		return vertexTwo;
	}

	/**
	* This getter method returns the DListNode of the other edgeList for the other vertex corresponding to this edge.
	* If this edge is a self edge, this method should not be used, and would return null.
	**/
	protected DListNode getPartnerEdge() {
		return partnerEdge;
	}

	/**
	* This setter method sets the partner edges for non self edges.
	**/
	protected void setPartnerEdge(DListNode partnerEdge) {
		this.partnerEdge = partnerEdge;
	}



}