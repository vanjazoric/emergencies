/**
 * 
 */
package beans;

import java.io.Serializable;

/**
 * @author Vanja
 *
 */
public class Territory implements Serializable{
	
	private static final long serialVersionUID = 1L;
	private String name;
	//km2
	private double area;
	private int population;
	
	public Territory(String name, double area, int population) {
		super();
		this.name = name;
		this.area = area;
		this.population = population;
	}

	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public double getArea() {
		return area;
	}

	public void setArea(double area) {
		this.area = area;
	}

	public int getPopulation() {
		return population;
	}

	public void setPopulation(int population) {
		this.population = population;
	}
}
