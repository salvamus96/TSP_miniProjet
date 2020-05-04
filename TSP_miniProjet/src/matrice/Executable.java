package matrice;
import java.io.IOException;

/**
 * Voici la classe a executer pour tester le programme
 *
 */
public class Executable {

	public static void main(String[] args) throws IOException {
		System.out.println("test");
		Matrice matrice = new Matrice("TSP005");
		matrice.afficheMatrice();
		matrice.reduxtion();
		matrice.afficheMatrice();
		Matrice matrice_red = matrice.retire(2, 2);
		matrice_red.afficheMatrice();
	}

}
