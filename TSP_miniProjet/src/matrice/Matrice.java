package matrice;
import java.util.*;
import java.io.*;
/**
 * Cette classe represente les matrices utilisees afin de resoudre le probleme du STP
 */
public class Matrice{
	
	private int taille;
	private Chemin[][] chemins;
	
	private int distance_min = 0;
	private int infini;
	
/**
 * Constructeur de la classe Matrice.
 * @param nom_fichier : nom du fichier ou sont stockees les distances sous le format defini
 */
	public Matrice(String nom_fichier) throws java.io.IOException {
		File fichier = new File(nom_fichier + ".txt");
		Scanner lecteur = new Scanner(fichier);
		
		this.taille = lecteur.nextInt();
		this.chemins = new Chemin[taille][taille];
		
		this.infini = 0;
		
		for(int i = 0; i < this.taille; i++) {
			for(int j = 0; j < this.taille; j++) {
				int current = lecteur.nextInt();
				this.chemins[i][j] = new Chemin(i,j,current);
				if(current > this.infini) {
					this.infini = current;
				}
			}
		}
		return;
	}
	
	public Matrice(Chemin[][] tab, int taille, int infini, int distance_min){
		this.chemins = tab;
		this.taille = taille;
		this.infini = infini;
		this.distance_min = distance_min;
		return;
	}
	
	/**
	 * Cette methode permet d'afficher la matrice
	 */
	public void afficheMatrice() {
		System.out.println("Nombre de villes: " + this.taille);
		for(int i = 0; i < this.taille; i++) {
			for(int j = 0; j < this.taille; j++) {
				System.out.print(this.chemins[i][j].getDistance() + "\t ");
			}
			System.out.println();
		}
		System.out.println();
		return;
	}
	
	/**
	 * Methode renvoyant le plus grand element de la colonne i de la matrice (sans considerer "l'infini".
	 * @param i colonne
	 * @return plus grand entier de la colonne i;
	 */
	public int recherche_max_colonne(int i) {
		int max_col = 0;
		int current = 0;
		for(int j = 0; j < this.taille; j++) {
			current = this.chemins[j][i].getDistance();
			if((current > max_col)&&(current != this.infini)) {
				max_col = current;
			}
		}
		return max_col;
	}
	
	/**
	 * Methode renvoyant le plus grand element de la ligne j de la matrice (sans considerer "l'infini".
	 * @param j ligne
	 * @return plus grand entier de la ligne j;
	 */
	public int recherche_max_ligne(int j) {
		int max_lig = 0;
		int current = 0;
		for(int i = 0; i < this.taille; i++) {
			current = this.chemins[j][i].getDistance();
			if((current > max_lig)&&(current != this.infini)) {
				max_lig = current;
			}
		}
		return max_lig;
	}

	public int recherche_min_colonne(int i) {
		int min_col = this.chemins[0][i].getDistance();
		int current = 0;
		for(int j = 1; j < this.taille; j++) {
			current = this.chemins[j][i].getDistance();
			if(current < min_col) {
				min_col = current;
			}
		}
		return min_col;
	}
	
	public int recherche_min_ligne(int j) {
		int min_lig = this.chemins[j][0].getDistance();
		int current = 0;
		for(int i = 1; i < this.taille; i++) {
			current = this.chemins[j][i].getDistance();
			if(current < min_lig) {
				min_lig = current;
			}
		}
		return min_lig;
	}
	
	public void affichage_min_max() {
		for(int i = 0;i < this.taille; i++) {
			int a = this.recherche_max_colonne(i);
			int b = this.recherche_min_colonne(i);
			System.out.println("Colonne :" + i + " | Max : " + a +" / Min : " + b);
		}
		for(int i = 0;i < this.taille; i++) {
			int a = this.recherche_max_ligne(i);
			int b = this.recherche_min_ligne(i);
			System.out.println("Ligne :" + i + " | Max : " + a +" / Min : " + b);
		}
		return;
	}
	
	public void reduction() {
		
		// réduction par colonne
		for(int i = 0; i < this.taille; i++) {
			int min_col = this.recherche_min_colonne(i); 
			this.distance_min = this.distance_min + min_col;
			
			for(int j = 0; j < this.taille; j++) {
				if(this.chemins[j][i].getDistance() != this.infini) {
					this.chemins[j][i].setDistance(this.chemins[j][i].getDistance() - min_col);
				}
			}
		}
		
		// réduction par ligne
		for(int j = 0; j < this.taille; j++) {
			int min_lin = this.recherche_min_ligne(j);
			this.distance_min = this.distance_min + min_lin;
			
			for(int i = 0; i < this.taille; i++) {
				if(this.chemins[j][i].getDistance() != this.infini) {
					this.chemins[j][i].setDistance(this.chemins[j][i].getDistance() - min_lin);
				}
			}
		}
		
		return;
	}
	
	public Matrice retire(int i, int j) {
		if(this.taille <= 1) {
			return null;
		}
		Chemin[][] tab = new Chemin[this.taille - 1][this.taille - 1];
		int index_i = 0;
		int index_j = 0;
		for(int a = 0; a < this.taille; a++) {
			if(a != i) {
				for(int b = 0; b < this.taille; b++) {
					if(b != j) {
						//System.out.println(tab[index_i][index_j]);
						//System.out.println(this.chemins[i][j]);
						tab[index_i][index_j] = this.chemins[a][b];
						index_j++;
					}
				}
				index_i++;
				index_j = 0;
			}
		}
		Matrice reduite = new Matrice(tab, this.taille - 1, this.infini, this.distance_min);
		return reduite;
	}

	
}
