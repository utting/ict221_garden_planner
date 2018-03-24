package garden_planner.textui;

import garden_planner.model.GardenPlanner;
import garden_planner.model.Rectangle;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

/**
 * A simple text user interface for a garden planner tool.
 *
 * This analyses one garden design and prints the costs.
 *
 * @author Mark Utting
 */
public class TextUI {

	private GardenPlanner planner;

	public TextUI(double gardenSoil, double sleeperCost) {
		planner = new GardenPlanner(gardenSoil, sleeperCost);
	}

	/**
	 * Prints a summary of the garden beds, plus total materials requirements and cost.
	 */
	public void printGarden() {
		planner.recalculateTotals();
		System.out.println(planner.GARDEN_PLANNER_VERSION);
		System.out.println("Garden design is:");
		for (Rectangle bed : planner.getBeds()) {
			System.out.println("    " + bed);
		}
		System.out.printf("Total garden area is: %8.2f m2.\n", planner.getTotalGardenArea());
		System.out.printf("Total wall length is: %8.2f m.\n", planner.getTotalWallLength());
		System.out.printf("Total soil required:  %8.2f m3.\n", planner.getTotalGardenArea() * planner.SOIL_DEPTH);
		System.out.printf("Total garden cost is: $%7.2f.\n", planner.getTotalCost());
	}

	/**
	 * Calculates costs for a given garden layout.
	 * This can take up to three optional command line arguments.
	 *
	 * @param args [soilPricePerCubicMetre wallPricePerMetre  [designFile.txt]]
	 * @throws FileNotFoundException
	 */
	public static void main(String[] args) throws FileNotFoundException {
		// Example prices are roughly based on http://centenarylandscaping.com.au.
		double gardenSoil = 90.00 * 0.9;   // 1 cubic metre of Ultima Organic Garden Soil.
		double sleeperCost = 51.00 / 3.0;  // 200x75mm CCA Hardwood Sleeper, 3.0m long.
		if (args.length >= 2) {
			gardenSoil = Double.parseDouble(args[0]);
			sleeperCost = Double.parseDouble(args[1]);
		}
		TextUI ui = new TextUI(gardenSoil, sleeperCost);

		// set up the garden design
		if (args.length == 3) {
			ui.planner.readBeds(new Scanner(new File(args[2])));
		} else {
			List<Rectangle> beds = ui.planner.getBeds();
			// use our default layout: two rectangles with a square in the middle
			beds.add(new Rectangle(2.0, 1.0));
			beds.add(new Rectangle(2.0, 2.0));
			beds.add(new Rectangle(2.0, 1.0));
		}

		ui.printGarden();
	}

}
