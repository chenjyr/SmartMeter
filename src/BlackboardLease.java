import java.util.concurrent.Semaphore;
import java.util.Random;

/*
 * implements a lease class that is a mutex with a password
 */
public class BlackboardLease{
	
	Semaphore sem;
	String password;
	long series;
	
	/**
	 * instantiates a semaphore with 1 permit;
	 */
	public BlackboardLease() 
	{
		sem = new Semaphore(1);
		series = 0;
	}
	
	
	/**
	 * Acquire method for underlying semaphore. Returns password for later releasing the semaphore 
	 * @return the password for later release
	 * @throws InterruptedException
	 */
	public String acquire() throws InterruptedException 
	{
		sem.acquire();
		return createPassword();
	}
	
	public void release(String psw) throws Exception
	{
		// check if password is correct, if not throw exception
		if(psw != password) {
			System.out.println("BlackBoardLease.release(): bad password");
			throw new Exception("BlackBoardLease.release(): bad password");
		}
		
		// check that semaphore has zero permits
		if(sem.availablePermits() != 0) {
			System.out.println("BlackBoardLease.release(): already released");
			throw new Exception("BlackBoardLease.release(): already released");
		}
		
		// else release the semaphore
		sem.release();
		
	}
	
	/**
	 * Call to generate a new password for the BlackBoardLease instance 
	 * @return
	 */
	private String createPassword()
	{
		return (password = (new Long(++series)).toString());
	}
	
}
