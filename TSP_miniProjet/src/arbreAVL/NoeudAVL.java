package arbreAVL;
/**
 * Chaque noeud est caract�ris� par un facteur d'�quilibrage appel� deseq qui peut �tre -2, -1, 0, 1, 2
 * Lorsque deseq = -2 ou deseq = 2, le noeud n'est pas �quilibr�
 * 
 * @param nom repr�sente une description du noeud, qui est la racine du sous-arbre
 * @param filsg repr�sentent le sous-arbre gauche du noeud lui-m�me
 * @param filsd repr�sentent le sous-arbre droite du noeud lui-m�meui-m�me
 * @author Salvatore
 */

public class NoeudAVL implements Cloneable {

	protected int deseq;
	protected String nom;

	protected NoeudAVL filsg;
	protected NoeudAVL filsd;
	
	public NoeudAVL(String s) {
		this.nom = s;
		this.deseq = 0;
		this.filsg = null;
		this.filsd = null;
	}
	
	
	public NoeudAVL() {
	}


	/**
	 * Comparaison entre 2 noeuds, pour d�cider de la position du n�ud par rapport � sa racine
	 * @param p noeud � comparer
	 * @return diff�rence 
	 */
	public int compareTo(NoeudAVL p) {
		return this.nom.compareTo(p.nom);
	}
	
	
	/**
	 * Tourner le sous-arbre du noeud lui-m�me vers la GAUCHE signifie placer 
	 * 1. le sous-arbre droit comme racine du nouveau sous-arbre, 
	 * 2. le sous-arbre gauche de la nouvelle racine est positionn� � droite de l'ancienne racine
	 * 3. l'ancienne racine devient le sous-arbre gauche de la nouvelle racine 	 
	 * @return racine du nouveau sous-arbre (apr�s rotation)
	 */
	public NoeudAVL rotationgauche() {
		// 1
		NoeudAVL racine = this.filsd; 
		// 2
		this.filsd = racine.filsg;
		// 3
		racine.filsg = this;
		return racine;
	}


	/**
	 * La double rotation DROITE-GAUCHE est divis�e en 2 phases:
	 * 1. rotation � droite sur le sous-arbre droit qui est positionn� � droite du n�ud consid�r�
	 * 2. rotation � gauche du n�ud lui-m�me
	 * @return racine du nouveau sous-arbre (apr�s rotation)
	 */
	public NoeudAVL rotationdroitegauche() {
		// 1
		NoeudAVL rot = this.filsd;
		NoeudAVL racineDroite = rot.rotationdroite();
		this.filsd = racineDroite;
		// 2
		NoeudAVL racine = this.rotationgauche();
		return racine;
	}

	
	/**
	 * Tourner le sous-arbre du noeud lui-m�me vers la DROITE signifie placer 
	 * 1. le sous-arbre guache comme racine du nouveau sous-arbre, 
	 * 2. le sous-arbre droit de la nouvelle racine est positionn� � guache de l'ancienne racine
	 * 3. l'ancienne racine devient le sous-arbre droit de la nouvelle racine 	 
	 * @return racine du nouveau sous-arbre (apr�s rotations)
	 */
	public NoeudAVL rotationdroite() {
		// 1
		NoeudAVL racine = this.filsg;
		// 2
		this.filsg = racine.filsd;
		// 3
		racine.filsd = this;
		return racine;
	}

	/**
	 * La double rotation GAUCHE-DROITE est divis�e en 2 phases:
	 * 1. rotation � gauche sur le sous-arbre guache qui est positionn� � gauche du n�ud consid�r�
	 * 2. rotation � droite du n�ud lui-m�me
	 * @return racine du nouveau sous-arbre (apr�s rotations)
	 */
	public NoeudAVL rotationgauchedroite() {
		// 1
		NoeudAVL rot = this.filsg;
		NoeudAVL racineGauche = rot.rotationgauche();
		this.filsg = racineGauche;
		// 2
		NoeudAVL racine = this.rotationdroite();
		return racine;
	}
	
	
	@Override
	public String toString() {
		String result = deseq + " " + nom; 
		if (this.filsg != null)
			result += "\nGauche: " + this.filsg.toString();
		if (this.filsd != null)
			result += "\nDroite: " + this.filsd.toString();
		return result;
	}
	
	@Override
    protected Object clone() throws CloneNotSupportedException {
        return super.clone();
    }
	

	
}
