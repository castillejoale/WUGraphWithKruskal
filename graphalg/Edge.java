package graphalg;

/**
* This class represents the edges in the Kruskal algorithm. It holds the weight
* of the edge and the two vertices its connected to.
**/

public class Edge implements Comparable {

	protected Object v1;
	protected Object v2;
	protected int w;


	/**
	* This constructor creates edge
	* @param vertex1 represents one of the vertices
	* @param vertex2 represents the other one of the vertices
	* @param weigth represents the weight of the edge
	**/
	protected Edge(Object vertex1, Object vertex2, int weight){
		v1 = vertex1;
		v2 = vertex2;
		w = weight;
	}

	/**
	* This getter method returns the weight of this edge.
	**/
	public int getWeight(){
		int s = w;
		return s;
	}

	/**
	* This getter method returns the vertex1 of this edge.
	**/
	public Object getVertex1(){
		return v1;
	}

	/**
	* This getter method returns the vertex2 of this edge.
	**/
	public Object getVertex2(){
		return v2;
	}

	/**
	* This method compared two edges by weight.
	* @param e is the edge that will be compared to this edge
	* @return 0 if both edges are the same, 1 if this edge has higher weight and 
	* -1 if e has higher weight
	**/
	public int compareTo(Object e){
		if(this.getWeight() == ((Edge)e).getWeight()){
			return 0;
		} else if (this.getWeight() < ((Edge)e).getWeight()){
			return -1;
		} else {
			return 1;
		}
	}

	/**
	* This method is a helper method that returns this edge as a String
	**/
	public String toString(){
		String s = "V1: " + getVertex1()+ " V2: " + getVertex2()+ " Weight: " + getWeight();
		return s;
	}

}