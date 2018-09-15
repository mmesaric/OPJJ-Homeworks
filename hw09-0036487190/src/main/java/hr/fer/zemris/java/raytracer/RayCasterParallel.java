package hr.fer.zemris.java.raytracer;

import java.util.List;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.RecursiveAction;

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
 * This class represents the implementation of the Ray caster which parallelizes
 * the rendering of the image using Fork-Join and RecursiveAction.
 * 
 * @author Marko Mesarić
 *
 */
public class RayCasterParallel {

	/**
	 * Constant which defines the threshold when comparing two distances.
	 */
	private static final double DISTANCE_THRESHOLD = 10E-8;

	/**
	 * Method which starts the rendering process.
	 * 
	 * @param args
	 */
	public static void main(String[] args) {
		RayTracerViewer.show(new RayCasterProducer());
	}

	/**
	 * Class which implements the Recursive Action and offers the implementation of
	 * all necessary calculations which are performed parallelized by multiple
	 * threads.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class RayCasterCalculator extends RecursiveAction {

		/**
		 * Default serial version UID
		 */
		private static final long serialVersionUID = 1L;
		/**
		 * eye point
		 */
		private Point3D eye;
		/**
		 * view point
		 */
		private Point3D view;
		/**
		 * view up point
		 */
		private Point3D viewUp;
		/**
		 * horizontal value
		 */
		private double horizontal;
		/**
		 * vertical value
		 */
		private double vertical;
		/**
		 * width value
		 */
		private int width;
		/**
		 * height value
		 */
		private int height;
		/**
		 * max number of iterations
		 */
		private int m;
		/**
		 * array for red color shorts
		 */
		private short[] red;
		/**
		 * array for green color shorts
		 */
		private short[] green;
		/**
		 * array for blue color shorts
		 */
		private short[] blue;
		/**
		 * i vector
		 */
		private Point3D xAxis;
		/**
		 * j vector
		 */
		private Point3D yAxis;
		/**
		 * Screen corner point
		 */
		private Point3D screenCorner;
		/**
		 * Scene in which image is rendered
		 */
		private Scene scene;
		/**
		 * y minimum
		 */
		private int minimum;
		/**
		 * y maximum
		 */
		private int maximum;

		/**
		 * Default constructor which sets up the object with passed values before
		 * starting the calculation
		 * 
		 * @param eye
		 * @param view
		 * @param viewUp
		 * @param horizontal
		 * @param vertical
		 * @param width
		 * @param height
		 * @param m
		 * @param red
		 * @param green
		 * @param blue
		 * @param xAxis
		 * @param yAxis
		 * @param screenCorner
		 * @param scene
		 * @param minimum
		 * @param maximum
		 */
		public RayCasterCalculator(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical,
				int width, int height, int m, short[] red, short[] green, short[] blue, Point3D xAxis, Point3D yAxis,
				Point3D screenCorner, Scene scene, int minimum, int maximum) {
			super();
			this.eye = eye;
			this.view = view;
			this.viewUp = viewUp;
			this.horizontal = horizontal;
			this.vertical = vertical;
			this.width = width;
			this.height = height;
			this.m = m;
			this.red = red;
			this.green = green;
			this.blue = blue;
			this.xAxis = xAxis;
			this.yAxis = yAxis;
			this.screenCorner = screenCorner;
			this.scene = scene;
			this.minimum = minimum;
			this.maximum = maximum;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		protected void compute() {

			if (maximum - minimum + 1 <= m) {
				computeDirect();
				return;
			}
			invokeAll(
					new RayCasterCalculator(eye, view, viewUp, horizontal, vertical, width, height, m, red, green, blue,
							xAxis, yAxis, screenCorner, scene, minimum, minimum + (maximum - minimum) / 2),
					new RayCasterCalculator(eye, view, viewUp, horizontal, vertical, width, height, m, red, green, blue,
							xAxis, yAxis, screenCorner, scene, minimum + (maximum - minimum) / 2 + 1, maximum));

		}

		/**
		 * This method iterates through pixels and computes the necessary points and
		 * ray. Based on those calculations, coloring of pixels is performed.
		 */
		private void computeDirect() {

			short[] rgb = new short[3];

			int offset = minimum * width;
			for (int y = minimum; y <= maximum; y++) {
				for (int x = 0; x < width; x++) {

					Point3D screenPoint = screenCorner.add(xAxis.scalarMultiply(horizontal * x / (width - 1))
							.sub(yAxis.scalarMultiply(vertical * y / (height - 1))));
					Ray ray = Ray.fromPoints(eye, screenPoint);

					tracer(ray, rgb);

					red[offset] = rgb[0] > 255 ? 255 : rgb[0];
					green[offset] = rgb[1] > 255 ? 255 : rgb[1];
					blue[offset] = rgb[2] > 255 ? 255 : rgb[2];

					offset++;
				}
			}

		}

		/**
		 * {@link package hr.fer.zemris.java.raytracer.RayCaster#tracer(Scene, Ray,
		 * short[])}
		 */
		private void tracer(Ray ray, short[] rgb) {

			rgb[0] = 0;
			rgb[1] = 0;
			rgb[2] = 0;
			RayIntersection closest = findClosestIntersection(scene, ray);
			if (closest == null) {
				return;
			}
			determineColorFor(closest, rgb, ray);
		}

		/**
		 * {@link package
		 * hr.fer.zemris.java.raytracer.RayCaster#determineColorFor(RayIntersection,
		 * short[], Ray, Scene)}
		 */
		private void determineColorFor(RayIntersection closest, short[] rgb, Ray ray) {

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
		 * {@link package
		 * hr.fer.zemris.java.raytracer.RayCaster#findClosestIntersection(Scene, Ray)}
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

	}

	/**
	 * This class represents the implementation of ray tracer producer. It does the
	 * initial setup of necessary configurations in order for the system to run.
	 * Creates a new fork join pool and begins the their execution, that is, begins
	 * the calculation of necessary things for rendering the final image.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public static class RayCasterProducer implements IRayTracerProducer {

		ForkJoinPool pool;

		public RayCasterProducer() {
			pool = new ForkJoinPool();
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public void produce(Point3D eye, Point3D view, Point3D viewUp, double horizontal, double vertical, int width,
				int height, long requestNo, IRayTracerResultObserver observer) {

			System.out.println("Starting calculation..");

			short[] red = new short[width * height];
			short[] green = new short[width * height];
			short[] blue = new short[width * height];

			Point3D xAxis = viewUp.vectorProduct(eye.sub(view)).normalize();
			Point3D yAxis = eye.sub(view).vectorProduct(xAxis).normalize();

			Point3D screenCorner = view.sub(xAxis.scalarMultiply(horizontal / 2))
					.add(yAxis.scalarMultiply(vertical / 2));

			Scene scene = RayTracerViewer.createPredefinedScene();

			int m = 16;
			int minimum = 0;
			int maximum = height - 1;

			pool.invoke(new RayCasterCalculator(eye, view, viewUp, horizontal, vertical, width, height, m, red, green,
					blue, xAxis, yAxis, screenCorner, scene, minimum, maximum));
			pool.shutdown();

			System.out.println("Calculation complete.");
			observer.acceptResult(red, green, blue, requestNo);

		}

	}
}
