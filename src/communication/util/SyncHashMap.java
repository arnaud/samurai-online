package communication.util;

import java.util.HashMap;

public class SyncHashMap extends HashMap {
	
	private static final long serialVersionUID = 1L;

	public SyncHashMap(int i) {
		super(i);
	}
	
	public synchronized Object put(Object key, Object value) {
		Object o = super.put(key, value);
		notify();
		return o;
	}

	public synchronized boolean isEmpty() {
		boolean res = super.isEmpty();
		if(res) {
			System.out.println(res);
			try {
				wait();
			} catch(InterruptedException ex) {
				ex.printStackTrace();
			}
		}
		return super.isEmpty();
	}
	
	public synchronized Object get(Object key)  {
		boolean res = super.isEmpty();
		if(res) {
			try {
				wait();
			} catch(InterruptedException ex) {}
		}
		return super.get(key);
	}
}
