package garden_planner.model;

/**
 * Represents a rectangular shape garden.
 */
public class Rectangle {
	private final double width;
	private final double height;

	public Rectangle(double w, double h) {
		this.width = w;
		this.height = h;
	}

	/**
	 * Get the area of this shape.
	 *
	 * @return the total internal area of the shape.
	 */
	public double getArea() {
		return this.width * this.height;
	}

	/**
	 * Get the perimeter of this shape.
	 *
	 * @return the total length of the edges of the shape.
	 */
	public double getPerimeter() {
		return 2 * (this.width + this.height);
	}

	@Override
	public String toString() {
		return "Rectangle " + this.width + " " + this.height;
	}
}
