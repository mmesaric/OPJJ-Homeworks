package hr.fer.zemris.java.raytracer.model;

/**
 * This class represents the implementation of sphere graphical object. It is
 * defined by radius and center point. It overrides the method for finding the
 * closest ray intersection used when search for intersections between line and
 * sphere.
 * 
 * @author Marko Mesarić
 *
 */
public class Sphere extends GraphicalObject {

	/**
	 * sphere center point
	 */
	private Point3D center;
	/**
	 * sphere radius
	 */
	private double radius;
	/**
	 * diffuse coefficient for red color
	 */
	private double kdr;
	/**
	 * diffuse coefficient for green color
	 */
	private double kdg;
	/**
	 * diffuse coefficient for blue color
	 */
	private double kdb;
	/**
	 * reflection coefficient for red color
	 */
	private double krr;
	/**
	 * reflection coefficient for green color
	 */
	private double krg;
	/**
	 * reflection coefficient for blue color
	 */
	private double krb;
	/**
	 * n
	 */
	private double krn;

	/**
	 * Default constructor which sets the sphere
	 * 
	 * @param center
	 *            sphere center
	 * @param radius
	 *            sphere radius
	 * @param kdr
	 * @param kdg
	 * @param kdb
	 * @param krr
	 * @param krg
	 * @param krb
	 * @param krn
	 */
	public Sphere(Point3D center, double radius, double kdr, double kdg, double kdb, double krr, double krg, double krb,
			double krn) {
		super();
		this.center = center;
		this.radius = radius;
		this.kdr = kdr;
		this.kdg = kdg;
		this.kdb = kdb;
		this.krr = krr;
		this.krg = krg;
		this.krb = krb;
		this.krn = krn;
	}

	/**
	 * {@inheritDoc}
	 */
	@Override
	public RayIntersection findClosestRayIntersection(Ray ray) {

		Point3D startPoint = ray.start;
		Point3D endPoint = ray.direction.scalarMultiply(2).add(ray.start);

		double x1 = startPoint.x;
		double y1 = startPoint.y;
		double z1 = startPoint.z;

		double x2 = endPoint.x;
		double y2 = endPoint.y;
		double z2 = endPoint.z;

		double x3 = center.x;
		double y3 = center.y;
		double z3 = center.z;

		double a = (x2-x1)*(x2-x1) + (y2 - y1)*(y2-y1) + (z2 - z1)*(z2-z1);
		double b = 2.0 * ((x2 - x1) * (x1 - x3) + (y2 - y1) * (y1 - y3) + (z2 - z1) * (z1 - z3));
		double c = x3*x3 + y3*y3 + z3*z3 + x1*x1 + y1*y1
				+ z1*z1 - 2 * (x3 * x1 + y3 * y1 + z3 * z1) - radius*radius;

		double discriminant = b*b - (4.0 * a * c);

		if (discriminant < 0) {
			return null;
		}

		double t1 = (-b + Math.sqrt(discriminant)) / (2.0 * a);
		double tangentX = x1 * (1 - t1) + t1 * x2;
		double tangentY = y1 * (1 - t1) + t1 * y2;
		double tangentZ = z1 * (1 - t1) + t1 * z2;
		Point3D tangentPoint = new Point3D(tangentX, tangentY, tangentZ);

		if (discriminant == 0) {
			double distance = Math.sqrt(Math.pow(x1 - tangentPoint.x, 2) + Math.pow(y1 - tangentPoint.y, 2)
					+ Math.pow(z1 - tangentPoint.z, 2));
			return new RaySphereIntersection(tangentPoint, distance, true);
		}

		double t2 = (-b - Math.sqrt(discriminant)) / (2.0 * a);
		double tangentX2 = x1 * (1 - t2) + t2 * x2;
		double tangentY2 = y1 * (1 - t2) + t2 * y2;
		double tangentZ2 = z1 * (1 - t2) + t2 * z2;

		Point3D tangentPoint2 = new Point3D(tangentX2, tangentY2, tangentZ2);

		double distance1 = Math.sqrt(
				Math.pow(x1 - tangentPoint.x, 2) + Math.pow(y1 - tangentPoint.y, 2) + Math.pow(z1 - tangentPoint.z, 2));

		double distance2 = Math.sqrt(Math.pow(x1 - tangentPoint2.x, 2) + Math.pow(y1 - tangentPoint2.y, 2)
				+ Math.pow(z1 - tangentPoint2.z, 2));

		if (t1 < 0 && t2 < 0) {
			return null;
		} else if (t1 > 0 && t2 < 0) {
			return new RaySphereIntersection(tangentPoint, distance1, true);
		} else if (t1 < 0 && t2 > 0) {
			return new RaySphereIntersection(tangentPoint2, distance2, true);
		} else if (t1 > 0 && t2 > 0) {
			if (distance1 < distance2) {
				return new RaySphereIntersection(tangentPoint, distance1, true);
			} else {
				return new RaySphereIntersection(tangentPoint2, distance2, true);
			}
		}
		return null;
	}

	/**
	 * This class represents the implementation of a single ray and sphere
	 * intersection. It contains information about point of intersection, total
	 * distance to that point and information if intersection is outer or inner.
	 * 
	 * @author Marko Mesarić
	 *
	 */
	public class RaySphereIntersection extends RayIntersection {
		
		/**
		 * Default constructor
		 * @param point point of intersection
		 * @param distance distance to intersection 
		 * @param outer type of intersection
		 */
		protected RaySphereIntersection(Point3D point, double distance, boolean outer) {
			super(point, distance, outer);
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public Point3D getNormal() {
			return getPoint().sub(center).normalize();
			
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdr() {
			return kdr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdg() {
			return kdg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKdb() {
			return kdb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrr() {
			return krr;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrg() {
			return krg;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrb() {
			return krb;
		}

		/**
		 * {@inheritDoc}
		 */
		@Override
		public double getKrn() {
			return krn;
		}

	}
}
