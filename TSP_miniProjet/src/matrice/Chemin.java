package matrice;

/**
 * Cette classe reprséente les distances stockees dans la matrice du probleme. Afin de faciliter les operations, nous stockerons egalement
 * les villes de depart et d'arrivee associees.
 * @author allibmat
 *
 */
public class Chemin {

	private int depart;
	private int arrivee;
	private int distance;
	
	/**
	 * Constructeur classique
	 * @param depart = ID de la ville de depart
	 * @param arrivee= ID de la ville d'arrivee
	 * @param distance= distance entre depart et arrivee
	 */
	public Chemin(int depart, int arrivee, int distance) {
		this.depart = depart;
		this.arrivee = arrivee;
		this.distance = distance;
	}

	public int getDepart() {
		return depart;
	}

	public int getArrivee() {
		return arrivee;
	}

	public int getDistance() {
		return distance;
	}

	public void setDistance(int distance) {
		this.distance = distance;
	}
	
	
	
}
