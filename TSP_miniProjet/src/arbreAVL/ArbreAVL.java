package arbreAVL;
import java.util.ArrayList;
import java.util.List;

public class ArbreAVL {
	
	//attributs
	protected NoeudAVL racine;
	protected int taille;
	
	//constructeur par d�faut
	public ArbreAVL() {
		this.racine = null;
		this.taille = 0;
	}

	/**
	 * Insertion d'un noeud avec un nom s dans l'arbre
	 * <br>Phase 1: Localisation de la position dans laquelle ins�rer le nouveau n�ud Y avec l'identification du n�ud p�re PP ;
	 * <br>Phase 2: Insertion du noeud Y ;
	 * <br>Phase 3: Mise � jour des facteurs d'�quilibrage entre le n�ud A et le n�ud ins�r� Y ;
	 * <br>Phase 4: V�rification de l'�quilibrage du n�ud A ;
	 * <br>Phase 5: Trouvez et effectuez la rotation appropri�e, proc�der � la mise � jour des facteurs d'�quilibre
	 * 
	 * @param s nom du noeud � ins�rer
	 */
	public void ajoutAVL (String s) {
		
		NoeudAVL A; // noeud le plus bas avec facteur d'�quilibrage -1 ou 1
		NoeudAVL AA; // noeud p�re de A
		NoeudAVL P;  // noeud - it�rateur se d�pla�a le long de l'arbre
		NoeudAVL PP; // noeud p�re de P
		NoeudAVL Y = new NoeudAVL(s); // noed du noeud � ins�rer
		NoeudAVL r = racine;  
		
		if (racine == null) { // arbre tree
			racine = Y; // insertion du premier noeud comme racine d'arbre
		} else {
			A = racine; 
			AA = null;
			P = racine; 
			PP = null;
			
			// Phase 1: Localisation de la position dans laquelle ins�rer le nouveau n�ud Y 
			// 			avec l'identification du n�ud p�re PP
			while (P != null) { 
				if (P.deseq != 0) { 
					// Phase 1a. : stockage du n�ud le plus bas (A), et de son p�re (AA), 
					//             avec un facteur d'�quilibrage de -1 ou 1
					A = P;  
					r = A;
					AA = PP;
				}
				PP = P; 
				if (Y.compareTo(P) <= 0) { 
					P = P.filsg; // mise � jour de l'it�rateur
				} else {
					P = P.filsd;
				}
			}

			// Phase 2: Insertion du noeud Y
			if (Y.compareTo(PP) <= 0) { 
				PP.filsg = Y;
			} else {
				PP.filsd = Y;
			}
			P = A; 
			

			// Phase 3: Mise � jour des facteurs d'�quilibrage entre le noeud A et le n�ud ins�r� Y
			while (P != Y) {
				if (Y.compareTo(P) <= 0) {
					P.deseq = P.deseq+1;
					P = P.filsg;
				} else {
					P.deseq = P.deseq-1;
					P = P.filsd;
				}
			}
			

			// Le seul noeud qui pourrait �tre d�s�quilibr� (facteur -2 ou 2) est le n�ud A, 
			// qui doit �tre �quilibr� avec des rotations
			
			// Phase 4: V�rification de l'�quilibrage du n�ud A
			if(A.deseq == -2) {
				
				// Phase 5a: Trouvez et effectuez la rotation appropri�e, 
				// 			proc�der � la mise � jour des facteurs d'�quilibre
				
				if(A.filsd.deseq == -1) {
					// noeud et fils droit avec signe concordant -> rotation simple
					r = A.rotationgauche();
					r.deseq = 0;
					r.filsg.deseq = 0;
				} else {
					// noeud et fils droit avec signe disconcordant -> double rotation
					if(A.filsd.deseq == 1) {
						r = A.rotationdroitegauche();

						if (r.deseq == 1)	{
							r.filsg.deseq = 0;
							r.filsd.deseq = -1;
						} else {
							if (r.deseq == -1) {
								r.filsg.deseq = 1;
								r.filsd.deseq = 0;
							} else {
								if (r.deseq == 0)	{
									r.filsg.deseq = 0;
									r.filsd.deseq = 0;
								}
							}
						}
						r.deseq = 0;
					}
				}	
			}

			if(A.deseq == 2)	{
				
				// Phase 5b: Trouvez et effectuez la rotation appropri�e, 
				// 			proc�der � la mise � jour des facteurs d'�quilibre
				
				if(A.filsg.deseq == 1) {
					// noeud et fils droit avec signe concordant -> rotation simple
					r = A.rotationdroite();
					r.deseq = 0;
					r.filsd.deseq = 0;
				} else {
					// noeud et fils droit avec signe disconcordant -> double rotation
					if(A.filsg.deseq == -1) {	
						r = A.rotationgauchedroite();
						if (r.deseq == 1)	{
							r.filsg.deseq = 1;
							r.filsd.deseq = 0;
						} else {
							if (r.deseq == -1) {
								r.filsg.deseq = 0;
								r.filsd.deseq = -1;
							} else {
								if (r.deseq == 0)	{
									r.filsg.deseq = 0;
									r.filsd.deseq = 0;
								}
							}
						}
						r.deseq = 0;
					}
				}	
			}
			
			if(AA == null) {
				racine = r;
			} else {
				if(AA.compareTo(r) >= 0) {
					AA.filsg = r;
				}
				else {
					AA.filsd = r;
				}
			}
		}
		taille = taille + 1;
	}
	
	/**
	 * Visite de l'arbre avec une racine indiqu�e lors un parcour infixe. 
	 * Une fois visit�s, tous les n�uds sont ins�r�s dans une liste.
	 * @param racine point de d�part
	 * @return Liste ordonn�e des n�uds parcourus
	 */
	public List<NoeudAVL> parcoursAVL (NoeudAVL racine){
		ArrayList <NoeudAVL> list = new ArrayList <NoeudAVL> ();
		if (racine != null) {
			// algorithme r�cursif
			list.addAll(this.parcoursAVL(racine.filsg));
			list.add(racine);
			list.addAll(this.parcoursAVL(racine.filsd));
		}
		return list;
	}
	
	
	/**
	 *Recherche la cha�ne donn�e en param�tre et retourne le r�f�rent sur la chaine recherch�e 
	 *dans l'arbre AVL si l'�l�ment existe, et retourne null sinon. 
	 * @param s nom du noeud � rechercher
	 * @return noeud 
	 */
	public NoeudAVL rechercheAVL (String s) {
		return this.recherche (s, this.racine);
	}

	/**
	 * Recherche le noeud avec le nom s dans le sous-arbre avec racine noued
	 * @param s nom du noeud � rechercher
	 * @param noeud racine du sous-arbre
	 * @return noued trouv�
	 */
	private NoeudAVL recherche(String s, NoeudAVL noeud) {
		if (noeud == null)
			return null;
		// algorithme r�cursif
		// position du noeud � rechercher par rapport � la racine du sous-arbre
		if (s.compareTo(noeud.nom)<0)
			// Le noeud � rechercher est situ� � guache de la racine du sous-arbre
			return recherche (s, noeud.filsg);
		else {
			if (s.compareTo(noeud.nom)>0)
				// Le noeud � rechercher est situ� � droite de la racine du sous-arbre
				return recherche (s, noeud.filsd);
			else
				// noued trouv�
				return noeud;
		}
	}
		

	/**
	 * Recherche la cha�ne donn�e en param�tre et la supprime de l'arbre AVL, 
	 * et retourne le r�f�rent sur la chaine supprim�e; 
	 * et retourne null si l'�l�ment � supprimer n'existe pas. 
	 * 
	 * <br>Phase 1: Suppression du noeud de mani�re standard comme dans un arbre de recherche binaire
	 * <br>Phase 2: Mise � jour des facteurs d'�quilibrage du nouveau sous-arbre + rotations + mise � jour des n�uds
	 * <br>Phase 3: V�rification des facteurs d'�quilibrage de la racine au nouveau n�ud ajout� avec toutes les rotations
	 * 
	 * @param s nom du noeud � supprimer
	 * @return noeud supprim�
	 */
	public NoeudAVL suppAVL (String s) {
		// Identification du n�ud � supprimer
		NoeudAVL supp = this.rechercheAVL(s);
		
		if (supp != null) {
				
			NoeudAVL pereSupp = this.pere(supp);
			
			// Phase 1
			NoeudAVL suppNoeud = this.suppNoeud(supp);
			
			
			// Phase 2
			if(suppNoeud == null) {
				// c'est une feuille
				pereSupp.deseq = this.hauter(pereSupp.filsg) - this.hauter (pereSupp.filsd);
				this.equilibrage(pereSupp, this.pere(pereSupp));
				return supp;
			}	
			
			else 
				// met � jour le facteur d'�quilibre du sous-arbre apr�s la suppression
				suppNoeud.deseq = this.hauter(suppNoeud.filsg) - this.hauter (suppNoeud.filsd);
			
			pereSupp.deseq = this.hauter(pereSupp.filsg) - this.hauter (pereSupp.filsd);	
			
			// Phase 3
			this.equilibrage(suppNoeud, pereSupp);
			
			taille = taille - 1;
		}
		
		return supp;
	}
	
	
	/**
	 * Effectuer les op�rations de suppression d'un n�ud (3 cas)
	 * @param supp noeud � supprimer
	 * @return racine de sous-arborescence apr�s l'op�ration de suppression
	 */
	private NoeudAVL suppNoeud(NoeudAVL supp) {
		
		if (supp != null) {
			
			// Cas 1: le n�ud � supprimer n'a pas enfants (c'est une feuille)
			if (supp.filsg == null && supp.filsd == null)
				// remplacement du noeud par null
				return this.remplacer(supp, null);
			
			
			// Cas 2: le n�ud � supprimer n'a qu'un seul fils:
			
			else if (supp.filsg != null && supp.filsd == null)
				// remplacement du noeud par son fils gauche
				return this.remplacer(supp, supp.filsg);
			else if (supp.filsg == null && supp.filsd != null)
				// remplacement du noeud par son fils droit
				return this.remplacer(supp, supp.filsd);
			
			
			// Cas 3: le n�ud � supprimer a deux enfants: 
			
			else if (supp.filsg != null && supp.filsd != null) {
				
				// trouver le successeur qui n'a plus d'enfant, d�tacher le successeur
				NoeudAVL succ = this.successeur(supp.filsd); 
				
				NoeudAVL pereSuccessore = this.pere(succ);
				
				if (pereSuccessore != supp) {
					// le fils gauche (le successeur lui-m�me d'abord) du p�re du successeur 
					// devient le fils droit du successeur
					pereSuccessore.filsg = succ.filsd;
					
					// le successeur prendra la place du n�ud � supprimer 
					// pour que son fils droit devienne le fils droit du n�ud � supprimer
					succ.filsd = supp.filsd;
				}
				
				// le fils gauche du successeur devient le fils gauche du n�ud � supprimer
				succ.filsg = supp.filsg;
				
				return this.remplacer(supp, succ);
				
			}
			
		}
		return null;
		
		
	}
	
	
	/**
	 * Remplace un n�ud par un autre n�ud 
	 * @param supp noeud � remplacer 
	 * @param n noeud 
	 * @return n
	 */
	private NoeudAVL remplacer(NoeudAVL supp, NoeudAVL n) {
		NoeudAVL pereSupp = this.pere(supp);
		
		if (pereSupp != null) {
			if (pereSupp.filsg == supp)
				pereSupp.filsg = n;
			else
				pereSupp.filsd = n;
		} else 
			// Le n�ud � supprimer �tait la racine de l'arbre
			this.racine = n;
		
		return n;

	}

	/**
	 * Effectuer les op�rations de mise � jour des facteurs d'�quilibrage et d�marrer les rotations
	 * @param suppNoeud noeud
	 * @param pereSupp p�re noeud
	 */
	private void equilibrage(NoeudAVL suppNoeud, NoeudAVL pereSupp) {
		// Phase 3
		if (suppNoeud != this.racine) {
			
			if (suppNoeud.deseq == -2 || suppNoeud.deseq == 2) {
				NoeudAVL p = this.checkEquilibrage (suppNoeud);
				if (pereSupp == null)
					racine = p;
				else  
					if (p.compareTo(pereSupp) > 0)
						pereSupp.filsd = p;
					else
						pereSupp.filsg = p;	
				} 
			
			pereSupp.deseq = this.hauter(pereSupp.filsg) - this.hauter (pereSupp.filsd);	
			
			this.equilibrage(pereSupp, this.pere(pereSupp));
		}
			
	}

	
	
	/**
	 * Il g�re les rotations et la mise � jour des facteurs d'�quilibrage apr�s les rotations
	 * @param A noeud avant rotation
	 * @return n�ud apr�s rotation
	 */
	private NoeudAVL checkEquilibrage(NoeudAVL A) {
		// Phase 4: V�rification de l'�quilibrage du n�ud A
		NoeudAVL r = new NoeudAVL ();
		
		if(A.deseq == -2) {
			
			// Phase 5a: Trouvez et effectuez la rotation appropri�e, 
			// 			proc�der � la mise � jour des facteurs d'�quilibre
			
			if(A.filsd.deseq == -1) {
				// noeud et fils droit avec signe concordant -> rotation simple
				r = A.rotationgauche();
				r.deseq = 0;
				r.filsg.deseq = 0;
			} else {
				// noeud et fils droit avec signe disconcordant -> double rotation
				if(A.filsd.deseq == 1) {
					r = A.rotationdroitegauche();

					if (r.deseq == 1)	{
						r.filsg.deseq = 0;
						r.filsd.deseq = -1;
					} else {
						if (r.deseq == -1) {
							r.filsg.deseq = 1;
							r.filsd.deseq = 0;
						} else {
							if (r.deseq == 0)	{
								r.filsg.deseq = 0;
								r.filsd.deseq = 0;
							}
						}
					}
					r.deseq = 0;
				}
			}	
		}

		if(A.deseq == 2)	{
			
			// Phase 5b: Trouvez et effectuez la rotation appropri�e, 
			// 			proc�der � la mise � jour des facteurs d'�quilibre
			
			if(A.filsg.deseq == 1) {
				// noeud et fils droit avec signe concordant -> rotation simple
				r = A.rotationdroite();
				r.deseq = 0;
				r.filsd.deseq = 0;
			} else {
				// noeud et fils droit avec signe disconcordant -> double rotation
				if(A.filsg.deseq == -1) {	
					r = A.rotationgauchedroite();
					if (r.deseq == 1)	{
						r.filsg.deseq = 1;
						r.filsd.deseq = 0;
					} else {
						if (r.deseq == -1) {
							r.filsg.deseq = 0;
							r.filsd.deseq = -1;
						} else {
							if (r.deseq == 0)	{
								r.filsg.deseq = 0;
								r.filsd.deseq = 0;
							}
						}
					}
					r.deseq = 0;
				}
			}
		}
		return r;
	}

	
	/**
	 * Trouver le successeur du n�ud pass� en param�tre: 
	 * c'est le n�ud le plus bas qui n'a pas de fils � gauche
	 * @param n noeud
	 * @return successeur
	 */
	public NoeudAVL successeur(NoeudAVL n) {
		while (n.filsg != null)
			n = n.filsg;
		
		return n;
	}

	
	/**
	 * �tant donn� un n�ud, il trouve son n�ud parent
	 * @param u noeud
	 * @return pere noeud
	 */
	public NoeudAVL pere(NoeudAVL u) {
		NoeudAVL racine = this.racine;
		NoeudAVL pere = null;
		while (racine != u) {
			pere = racine; 
			if (u.compareTo(racine)<=0)
				racine = racine.filsg;
			else
				racine = racine.filsd;
		}
		return pere;
	}

	
	/**
	 * Calculer la hauteur d'un n�ud pass� en param�tre
	 * @param n noeud
	 * @return hauter
	 */
	public int hauter(NoeudAVL n) {
		if (n == null)
			return -1;
		else {
			int hauterG = this.hauter(n.filsg);
			int hauterD = this.hauter(n.filsd);
			return Math.max(hauterG, hauterD) + 1;
		}
	}


	

}


