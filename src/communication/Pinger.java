package communication;

public class Pinger extends Thread {
	private CommClient commClient;
	
	public Pinger(CommClient commClient) {
		this.commClient = commClient;
	}
	
	public void run() {
		while(true) {
			if(commClient.server_address!=null) {
				commClient.ping();
			}
			
			try {
				Thread.sleep(5000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
