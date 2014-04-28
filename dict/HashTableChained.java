/* HashTableChained.java */

package dict;

import list.*;

/**
 *  HashTableChained implements a Dictionary as a hash table with chaining.
 *  All objects used as keys must have a valid hashCode() method, which is
 *  used to determine which bucket of the hash table an entry is stored in.
 *  Each object's hashCode() is presumed to return an int between
 *  Integer.MIN_VALUE and Integer.MAX_VALUE.  The HashTableChained class
 *  implements only the compression function, which maps the hash code to
 *  a bucket in the table's range.
 *
 *  DO NOT CHANGE ANY PROTOTYPES IN THIS FILE.
 **/

public class HashTableChained implements Dictionary {
	
	DList[] hashTable;
	int N; //N is the number of buckets in the hashTable
	int numOfEntries; 

  /** 
   *  Construct a new empty hash table intended to hold roughly sizeEstimate
   *  entries.  (The precise number of buckets is up to you, but we recommend
   *  you use a prime number, and shoot for a load factor between 0.5 and 1.)
   **/

  public HashTableChained(int sizeEstimate) {
	  
	  int min = (int) Math.floor(sizeEstimate/1.0);
	  int max = (int) Math.floor(sizeEstimate/0.5);
	  
	  N = min;
	  
	  for(int i = min; i<=max; i++){
		  if(isPrime(i)){
			  N = i;
			  break;
		  }
	  }
	  
	  hashTable = new DList[N];
	 
  }

  /** 
   *  Construct a new empty hash table with a default size.  Say, a prime in
   *  the neighborhood of 100.
   **/

  public HashTableChained() {
    N = 101;
	hashTable = new DList[N];
    
  }

  /**
   *  Converts a hash code in the range Integer.MIN_VALUE...Integer.MAX_VALUE
   *  to a value in the range 0...(size of hash table) - 1.
   *
   *  This function should have package protection (so we can test it), and
   *  should be used by insert, find, and remove.
   **/

  int compFunction(int code) {
    int compressionCode;
    
    compressionCode = (((2*code + 8) % 16908799) % N);
    
    if(compressionCode<0){
    	compressionCode = compressionCode + N;
    }
    
    return compressionCode;
  }

  /** 
   *  Returns the number of entries stored in the dictionary.  Entries with
   *  the same key (or even the same key and value) each still count as
   *  a separate entry.
   *  @return number of entries in the dictionary.
   **/

  public int size() { 
    return numOfEntries;
  }

  /** 
   *  Tests if the dictionary is empty.
   *
   *  @return true if the dictionary has no entries; false otherwise.
   **/

  public boolean isEmpty() {
    if(size() == 0){
    	return true;
    }else{
    	return false;
    }
   
  }

  /**
   *  Create a new Entry object referencing the input key and associated value,
   *  and insert the entry into the dictionary.  Return a reference to the new
   *  entry.  Multiple entries with the same key (or even the same key and
   *  value) can coexist in the dictionary.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the key by which the entry can be retrieved.
   *  @param value an arbitrary object.
   *  @return an entry containing the key and value.
   **/

  public Entry insert(Object key, Object value) {
    numOfEntries++;
	  
	Entry insertEntry = new Entry();
    insertEntry.key = key;
    insertEntry.value = value;
    
    int hashCode = key.hashCode();
   
    if(hashTable[compFunction(hashCode)] == null){ //if no DList, create a new one
    	hashTable[compFunction(hashCode)] = new DList();
    }
    
    hashTable[compFunction(hashCode)].insertBack(insertEntry);
   
    
    return insertEntry;
  }

  /** 
   *  Search for an entry with the specified key.  If such an entry is found,
   *  return it; otherwise return null.  If several entries have the specified
   *  key, choose one arbitrarily and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   **/

  public Entry find(Object key) {
    int hashCode = key.hashCode();
    
    
    Entry entry = null;
    
    try{
    	if(hashTable[compFunction(hashCode)] == null){
    		return null;
    	}else{
    		ListNode pointer = hashTable[compFunction(hashCode)].front();
    		
    		for(int i =0; i<hashTable[compFunction(hashCode)].length(); i++){
    			if(((Entry) pointer.item()).key().equals(key)){
    				entry = ((Entry) pointer.item());
    				break;
    			}
    			pointer = pointer.next();
    		}
    	}
    }catch (InvalidNodeException e){
    }
    return entry;
  }
    
   

  /** 
   *  Remove an entry with the specified key.  If such an entry is found,
   *  remove it from the table and return it; otherwise return null.
   *  If several entries have the specified key, choose one arbitrarily, then
   *  remove and return it.
   *
   *  This method should run in O(1) time if the number of collisions is small.
   *
   *  @param key the search key.
   *  @return an entry containing the key and an associated value, or null if
   *          no entry contains the specified key.
   */

  public Entry remove(Object key) {
	  int hashCode = key.hashCode();
	  
	  try{
	  if(find(key)!=null){
		  ListNode pointer = hashTable[compFunction(hashCode)].front();
  		
  			for(int i =0; i<hashTable[compFunction(hashCode)].length(); i++){
  				if(((Entry) pointer.item()).key().equals(key)){
  					pointer.remove();
  					numOfEntries--;
  					break;
  			}
  			pointer = pointer.next();
  		}
	  }
	  }catch (InvalidNodeException e){
		  
	  }
	  return find(key);
    
  }

  /**
   *  Remove all entries from the dictionary.
   */
  public void makeEmpty() {
    for(int i=0; i<N;i++){
    	hashTable[i]=null;
    }
    numOfEntries = 0;
  }

  public boolean isPrime(int n) {
      int divisor = 2;
      while (divisor < n) {         
        if (n % divisor == 0) {      
          return false;              
        }                             
        divisor++;                 
      }
      return true;
    }

public int collisions(){
	int numCollisions = 0;
	
	
	for(int i=0; i<N; i++){
		if(hashTable[i]!=null && hashTable[i].length()>1){
			numCollisions++;
		}
	}
	return numCollisions;
}

public void resize(){ 
	double loadFactor = (double) numOfEntries/ (double)N; 
	
	if(loadFactor>0.75) {
		try{
		DList[] oldHashTable = hashTable;
		
		N = 2*N;
		
		hashTable = new DList[N];
		
		for(int i = 0; i< N; i++){ //loops through each DList in the hashTable
			DListNode pointer = (DListNode) oldHashTable[i].front();
			while (pointer.isValidNode()) { //loops through each node in a given DList[i]
				Object insertKey = ((Entry) pointer.item()).key;
				Object insertValue = ((Entry) pointer.item()).value;
				this.insert(insertKey, insertValue);
				pointer = (DListNode) pointer.next();			
			}
		}
		//rehash everything to newHashTable
		//make hashTable = newHashTable
		}catch(InvalidNodeException e){
			
		}
	}
}


public static void main(String[] args){
	
	HashTableChained table2 = new HashTableChained(126);
	System.out.println("Table 2 number of buckets N: " + table2.N);
	
	System.out.println("\nempty table 2: " + table2.isEmpty());
	
	System.out.println("\nTesting Insert");
	table2.insert("hello", 2);
	table2.insert("hello", 4);
	table2.insert("hello", 4);
	System.out.println("size should be 2: " + table2.size());
	System.out.println("empty?: " + table2.isEmpty());
	System.out.println("number of collisions: " + table2.collisions());
	
	System.out.println("\nTesting Remove");
	table2.remove("hello");
	System.out.println(table2.find("hello").key() + " " + table2.find("hello").value());
	
	System.out.println("\nTesting makeEmpty");
	table2.makeEmpty();
	System.out.println("Testing makeEmptry - size: " + table2.size());
	System.out.println(table2.find("hello"));
	
	
	HashTableChained table3 = new HashTableChained();
	System.out.println("\n\nTable 3 number of buckets N: " + table3.N);
	
	
}
}
