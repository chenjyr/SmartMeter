import java.util.Hashtable;
import java.util.concurrent.Semaphore;

/**
 * Central shared map of values. 
 */
public class Blackboard 
{
	/**
	 * map of keys to message objects 
	 */
	private Hashtable<String, Object> map;
	
	/**
	 * parallel map of corresponding Blackboard leases
	 */
	private Hashtable<String, BlackboardLease> lease_map;
	
	/**
	 * creates blackboard map and map of leases
	 */
	public Blackboard()
	{
		map = new Hashtable<String,Object>();
		lease_map = new Hashtable<String, BlackboardLease>();
		
	}
	
	/**
	 * Method for accessing the underlying map in the blackboard. May block on acquiring a lease
	 * @param key the key of the requested entry
	 * @return a tuple of the object and the password to return the lease
	 * @throws InterruptedException
	 */
	public Tuple<Object, String> get(String key) throws InterruptedException
	{
		BlackboardLease lease;
		String password = null;
		
		// block on acquiring a lease
		lease = lease_map.get(key);
		if(lease != null) {
			password = lease.acquire();
		}
		
		return new Tuple<Object,String>(map.get(key), password);
	}
	
	/**
	 * Method for setting a new reference for an entry or adding a new entry
	 * @param key  key of entry
	 * @param value new object to point key at
	 */
	public synchronized void set(String key, Object value)
	{
		BlackboardLease lease;
		
		// check semaphore, create if does not exist
		lease = lease_map.get(key);
		if(lease == null) {
			// create semaphore with 1 permit (i.e. a mutex)
			lease = new BlackboardLease();
			lease_map.put(key, lease);
		}
		
		map.put(key, value);
	}
	
	/**
	 * Releases the lease on the black board entry.
	 * @param key key to the entry
	 * @param psw password of the lease
	 * @throws Exception throws exception if wrong password supplied, or entry doesnt exist
	 */
	public void release(String key, String psw) throws Exception {
		BlackboardLease lease = lease_map.get(key);
		if (lease != null)
		{
			lease.release(psw);
		}
	}
	
}
