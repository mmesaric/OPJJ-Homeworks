package hr.fer.zemris.java.raytracer;

import java.util.List;
import hr.fer.zemris.java.raytracer.model.GraphicalObject;
import hr.fer.zemris.java.raytracer.model.IRayTracerProducer;
import hr.fer.zemris.java.raytracer.model.IRayTracerResultObserver;
import hr.fer.zemris.java.raytracer.model.LightSource;
import hr.fer.zemris.java.raytracer.model.Point3D;
import hr.fer.zemris.java.raytracer.model.Ray;
import hr.fer.zemris.java.raytracer.model.RayIntersection;
import hr.fer.zemris.java.raytracer.model.Scene;
import hr.fer.zemris.java.raytracer.viewer.RayTracerViewer;

/**
 * This class represents the implementation of simplified ray-tracer for
 * rendering 3D scenes.
 * 
 * @author Marko Mesarić
 *
 */
public class RayCaster {

	/**
	 * Constant which defines the threshold when comparing two distances.
	 */
	private static final double DISTANCE_THRESHOLD = 10E-8;

	/**
	 * Main method responsible for initial view of Ray tracer. Begins the rendering
	 * of 3D scene.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(getIRayTracerProducer(), new Point3D(10, 0, 0), new Point3D(0, 0, 0),
				new Point3D(0, 0, 10), 20, 20);
	}

	/**
	 * This method generates and returns custom simplified Ray tracer based on eye,
	 * view and viewup points. Additional parameters like width, height, etc are
	 * also passed. Starts the calculation of the necessary parts in order for
	 * rendering to work. Scene is created and objects placed in it.
	 * 
	 * @return
	 */
	private static IRayTracerProducer getIRayTracerProducer() {
		return new IRayTracerProducer() {
			@Override
			public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
					int width, int height, long requestNo, IRayTracerResultObserver observer) {

				System.out.println("Započinjem izračune...");
				short[] red = new short[width * height];
				short[] green = new short[width * height];
				short[] blue = new short[width * height];

				Point3D xAxis = viewUp.vectorProduct(eye.sub(view)).normalize();
				Point3D yAxis = eye.sub(view).vectorProduct(xAxis).normalize();

				Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
						.add(yAxis.scalarMultiply(vertical / 2));

				Scene scene = RayTracerViewer.createPredefinedScene();

				short[] rgb = new short[3];
				int offset = 0;
				for (int y = 0; y < height; y++) {
					for (int x = 0; x < width; x++) {

						Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1))
								.sub(yAxis.scalarMultiply(vertical * y / (height - 1))));
						Ray ray = Ray.fromPoints(eye, screenPoint);

						tracer(scene, ray, rgb);

						red[offset] = rgb[0] > 255 ? 255 : rgb[0];
						green[offset] = rgb[1] > 255 ? 255 : rgb[1];
						blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

						offset++;
					}
				}
				System.out.println("Izračuni gotovi...");
				observer.acceptResult(red, green, blue, requestNo);
				System.out.println("Dojava gotova...");
			}
		};
	}

	/**
	 * Auxiliary method used for tracing the ray and coloring the pixels
	 * accordingly.
	 * 
	 * @param scene
	 *            in which rendering is performed
	 * @param ray
	 *            context ray
	 * @param rgb
	 *            array of rgb
	 */
	protected static void tracer(Scene scene, Ray ray, short[] rgb) {
		rgb[0] = 0;
		rgb[1] = 0;
		rgb[2] = 0;
		RayIntersection closest = findClosestIntersection(scene, ray);
		if (closest == null) {
			return;
		}
		determineColorFor(closest, rgb, ray, scene);
	}

	/**
	 * Auxiliary method used for determining the color of the pixel, based on light
	 * sources and position of objects in scene.
	 * 
	 * @param closest
	 *            ray intersection
	 * @param rgb
	 *            rgb array
	 * @param ray
	 *            observed ray
	 * @param scene
	 *            scene in which image is rendered
	 */
	protected static void determineColorFor(RayIntersection closest, short[] rgb, Ray ray, Scene scene) {
		rgb[0] = 15;
		rgb[1] = 15;
		rgb[2] = 15;

		List<LightSource> lightSources = scene.getLights();

		for (LightSource source : lightSources) {

			Ray lightRay = Ray.fromPoints(source.getPoint(), closest.getPoint());

			RayIntersection closest2 = findClosestIntersection(scene, lightRay);

			if (closest2 != null) {
				double distance1 = source.getPoint().sub(closest.getPoint()).norm();
				double distance2 = source.getPoint().sub(closest2.getPoint()).norm();

				if (Math.abs(distance2 - distance1) > DISTANCE_THRESHOLD) {
					continue;
				}

				Point3D normal = closest2.getNormal();
				Point3D lightVector = source.getPoint().sub(closest2.getPoint()).normalize();

				diffuseComponent(rgb, closest2, source, normal, lightVector);
				reflectiveComponent(rgb, closest2, source, ray, normal, lightVector);
			}
		}
	}

	/**
	 * Auxiliary method used for calculating reflective component.
	 * 
	 * @param rgb
	 *            rgb array
	 * @param closest2
	 *            intersection of light ray and graphical object
	 * @param source
	 *            light source
	 * @param ray
	 *            observed ray
	 * @param normal
	 *            vector normal from second point of intersection
	 * @param lightVector
	 *            light vector
	 */
	private static void reflectiveComponent(short[] rgb, RayIntersection closest2, LightSource source, Ray ray,
			Point3D normal, Point3D lightVector) {

		Point3D r = normal.normalize().scalarMultiply(lightVector.scalarProduct(normal) * 2 / normal.norm())
				.sub(lightVector).normalize();
		Point3D v = ray.start.sub(closest2.getPoint()).normalize();

		double angle2 = r.normalize().scalarProduct(v.normalize());

		if (angle2 < 0) {
			return;
		}

		angle2 = Math.pow(angle2, closest2.getKrn());

		rgb[0] += (short) (source.getR() * closest2.getKrr() * angle2);
		rgb[1] += (short) (source.getG() * closest2.getKrg() * angle2);
		rgb[2] += (short) (source.getB() * closest2.getKrb() * angle2);
	}

	/**
	 * Auxiliary method used for calculating diffuse component.
	 * 
	 * @param rgb
	 *            rgb array
	 * @param closest2
	 *            intersection of light ray and graphical object
	 * @param source
	 *            light source
	 * @param normal
	 *            vector normal from second point of intersection
	 * @param closest2Normal
	 *            
	 */
	private static void diffuseComponent(short[] rgb, RayIntersection closest2, LightSource source,
			Point3D closest2Normal, Point3D lightVector) {

		double angle = closest2Normal.scalarProduct(lightVector);

		rgb[0] += (short) (source.getR() * closest2.getKdr() * angle);
		rgb[1] += (short) (source.getG() * closest2.getKdg() * angle);
		rgb[2] += (short) (source.getB() * closest2.getKdb() * angle);
	}

	/**
	 * Auxiliary method used for finding the closest intersection from list of
	 * graphical objects in scene.
	 * 
	 * @param scene
	 *            in which image is rendered
	 * @param ray
	 *            relevant ray
	 * @return the closest intersection
	 */
	protected static RayIntersection findClosestIntersection(Scene scene, Ray ray) {

		List<GraphicalObject> graphicalObjects = scene.getObjects();

		RayIntersection minRayIntersection = null;
		for (GraphicalObject object : graphicalObjects) {

			RayIntersection rayIntersection = object.findClosestRayIntersection(ray);
			if (rayIntersection != null) {

				if (minRayIntersection == null) {
					minRayIntersection = rayIntersection;
				} else {
					if (rayIntersection.getDistance() < minRayIntersection.getDistance()) {
						minRayIntersection = rayIntersection;
					}
				}
			}
		}
		return minRayIntersection;
	}

}
