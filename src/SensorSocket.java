/**
 * encapsulates reading from a hardware sensor
 *
 */
public class SensorSocket {
	PinReader pin;
	Sensor device;
	
	/**
	 * Constructor
	 * @param d device to bind to
	 */
	public SensorSocket(Sensor d)
	{
		device = d;
		pin = new PinReader();
	}
	
	/**
	 * Read
	 * @return bytes from the sensor
	 */
//	"SOMEFLAG VALUE"
	public double read() 
	{
		return pin.read();
	}
	
}