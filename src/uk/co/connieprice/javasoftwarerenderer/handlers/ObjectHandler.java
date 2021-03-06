package uk.co.connieprice.javasoftwarerenderer.handlers;

import java.util.ArrayList;
import java.util.List;

import uk.co.connieprice.javasoftwarerenderer.objects.Object3D;

/**
 * <h1>ObjectHandler</h1>
 * The main object handler. Handles the management of objects and any updates they might want to do.
 * 
 * @author Connie Price
 *
 */
public class ObjectHandler implements Runnable {
	private List<Object3D> objects = new ArrayList<Object3D>();

	/**
	 * Get all the objects currently being handled by the object handler.
	 * @return List of objects.
	 */
	public List<Object3D> getObjects() {
		return objects;
	}

	/**
	 * Add an object to be handled by the object handler.
	 * @param object The object to add.
	 */
	public void addObject(Object3D object) {
		objects.add(object);
	}

	/**
	 * Remove an object from the object handler.
	 * @param object The object to remove.
	 */
	public void removeObject(Object3D object) {
		objects.remove(object);
	}


	private long updateInterval = 8; // About 120 updates per second.
	@Override
	public void run() {
		long deltaTime = 0;

		while(true) {
			long startTime = System.currentTimeMillis();

			// Set i to the size and go backwards as this is marginally
			// faster than constantly comparing against the size every time.
			for (int i = this.objects.size() - 1; i >= 0; i--) {
				Object3D object = this.objects.get(i);
				object.update(startTime, deltaTime);
			}

			long endTime = System.currentTimeMillis();
			deltaTime = endTime - startTime;

			try {
				long delay = updateInterval - deltaTime;
				if (delay > 0) { // If we are slower than the target update rate than just don't delay, keep going.
					Thread.sleep(updateInterval - deltaTime);
				}
			} catch (InterruptedException e) {
				System.out.print("Thread Interrupted");
			}
		}
	}
}
