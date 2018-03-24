package garden_planner.model;

import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

/**
 * A planning tool for costing gardens.
 *
 * This garden planner is useful for costing various layouts of garden beds,
 * made up of different shapes such as rectangular beds, circular beds, etc.
 *
 * This program can calculate the total wood required for the edges of the beds,
 * and the total soil required to fill the beds, plus the cost of those two things.
 */
public class GardenPlanner {
    public static final String GARDEN_PLANNER_VERSION = "Garden Planner v0.2";
    public static final double SOIL_DEPTH = 0.2;  // metres
    private final double soilPrice;
    private final double wallPrice;

    /** The collection of all the garden beds in the current design. */
    private final ArrayList<Rectangle> beds = new ArrayList<>();
    private double totalWallLength;
    private double totalGardenArea;

    /**
     * Creates a new garden planner, with the given soil and wall prices.
     *
     * @param soilPerCubicMetre Price of garden soil, per cubic metre.
     * @param wallPerMetre Price of the wall of a garden bed, per metre.
     */
    public GardenPlanner(double soilPerCubicMetre, double wallPerMetre) {
        this.soilPrice = soilPerCubicMetre;
        this.wallPrice = wallPerMetre;
    }

    /**
     * Gets the list of garden beds in the current design.
     *
     * WARNING: passing out the whole list allows clients to add/remove beds.
     * This is poor encapsulation, but is okay in this context where the client
     * programs are known and we want to give them a lot of flexibility.
     *
     * @return a list of garden beds.
     */
    public List<Rectangle> getBeds() {
        return this.beds;
    }

    /**
     * Get the total length of all walls.
     *
     * @return the total length (in metres) of all the garden bed walls.
     */
    public double getTotalWallLength() {
        return totalWallLength;
    }

    /**
     * Get the total area of all garden beds.
     *
     * @return the total area (in square metres) of all the garden beds.
     */
    public double getTotalGardenArea() {
        return totalGardenArea;
    }

    /**
     * Reads a garden design file (a text file).
     *
     * This adds all the shapes in the design file to the current garden layout.
     *
     * @param in the input stream to read the design from - typically a file.
     */
    public void readBeds(Scanner in) {
        while (in.hasNext()) {
            String line = in.nextLine().trim();
            String[] words = line.split(" +");
            // System.out.println(Arrays.toString(words));  // just for debugging
            if (line.startsWith("#") || line.length() == 0) {
                // we skip comment lines and empty lines.
            } else if (words.length == 3 && words[0].toLowerCase().equals("rectangle")) {
                getBeds().add(new Rectangle(Double.parseDouble(words[1]), Double.parseDouble(words[2])));
            } else {
                throw new IllegalArgumentException("ERROR: illegal garden bed: " + line);
            }
        }
    }

    /**
     * Recalculates the total wall and soil requirements for the current garden layout.
     *
     * This should be called every time a garden bed changes shape or size, or is added or removed.
     */
    public void recalculateTotals() {
        totalWallLength = 0.0;
        totalGardenArea = 0.0;
        for (Rectangle bed : this.beds) {
            totalGardenArea += bed.getArea();
            totalWallLength += bed.getPerimeter();
        }
    }

    /**
     * Get the total materials cost of the whole garden.
     *
     * @return cost of the walls plus soil.
     */
    public double getTotalCost() {
        final double wallCost = totalWallLength * this.wallPrice;
        final double soilVolume = totalGardenArea * this.SOIL_DEPTH;
        final double soilCost = soilVolume * this.soilPrice;
        // just for debugging:
        // System.out.printf("Total wall length is %.2f m, cost $%.2f.\n", totalWallLength, wallCost);
        // System.out.printf("Total garden area is %.2f m2 (%.2f m3 of soil), cost $%.2f.\n", totalGardenArea, soilVolume, soilCost);
        // System.out.printf("Total cost is: $%.2f\n", (wallCost + soilCost));
        return wallCost + soilCost;
    }
}
