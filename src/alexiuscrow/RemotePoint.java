package alexiuscrow;

public class RemotePoint {
	private String address;
	private String distanceToPoint;
	private String timeDuration;
	
	public RemotePoint(String address, String distanceToPoint, String timeDuration) {
		super();
		this.address = address;
		this.distanceToPoint = distanceToPoint;
		this.timeDuration = timeDuration;
	}
	
	public String getAddress() {
		return address;
	}
	
	public String getDistanceToPoint() {
		return distanceToPoint;
	}

	public String getTimeDuration() {
		return timeDuration;
	}
	
}
