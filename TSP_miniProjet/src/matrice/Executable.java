package matrice;
import java.io.IOException;

/**
 * Voici la classe a executer pour tester le programme
 *
 */
public class Executable {

	public static void main(String[] args) throws IOException {
		System.out.println("test");
		Matrice matrice = new Matrice("TSP010");
		matrice.afficheMatrice();
		System.out.println("Borne sup" + matrice.calcule_bornesup());
		//matrice.affichage_min_max();
		//matrice.reduction();
		//matrice.afficheMatrice();
//		System.out.println(matrice.getDistance_min());
		
		//Matrice matrice_red = matrice.retire(2, 2);
		//matrice_red.afficheMatrice();
	}

}
