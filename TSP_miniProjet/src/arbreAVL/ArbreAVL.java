package arbreAVL;
import java.util.ArrayList;
import java.util.List;

public class ArbreAVL {
	
	//attributs
	protected NoeudAVL racine;
	protected int taille;
	
	//constructeur par défaut
	public ArbreAVL() {
		this.racine = null;
		this.taille = 0;
	}

	/**
	 * Insertion d'un noeud avec un nom s dans l'arbre
	 * <br>Phase 1: Localisation de la position dans laquelle insérer le nouveau nœud Y avec l'identification du nœud père PP ;
	 * <br>Phase 2: Insertion du noeud Y ;
	 * <br>Phase 3: Mise à jour des facteurs d'équilibrage entre le nœud A et le nœud inséré Y ;
	 * <br>Phase 4: Vérification de l'équilibrage du nœud A ;
	 * <br>Phase 5: Trouvez et effectuez la rotation appropriée, procéder à la mise à jour des facteurs d'équilibre
	 * 
	 * @param s nom du noeud à insérer
	 */
	public void ajoutAVL (String s) {
		
		NoeudAVL A; // noeud le plus bas avec facteur d'équilibrage -1 ou 1
		NoeudAVL AA; // noeud père de A
		NoeudAVL P;  // noeud - itérateur se déplaça le long de l'arbre
		NoeudAVL PP; // noeud père de P
		NoeudAVL Y = new NoeudAVL(s); // noed du noeud à insérer
		NoeudAVL r = racine;  
		
		if (racine == null) { // arbre tree
			racine = Y; // insertion du premier noeud comme racine d'arbre
		} else {
			A = racine; 
			AA = null;
			P = racine; 
			PP = null;
			
			// Phase 1: Localisation de la position dans laquelle insérer le nouveau nœud Y 
			// 			avec l'identification du nœud père PP
			while (P != null) { 
				if (P.deseq != 0) { 
					// Phase 1a. : stockage du nœud le plus bas (A), et de son père (AA), 
					//             avec un facteur d'équilibrage de -1 ou 1
					A = P;  
					r = A;
					AA = PP;
				}
				PP = P; 
				if (Y.compareTo(P) <= 0) { 
					P = P.filsg; // mise à jour de l'itérateur
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
			

			// Phase 3: Mise à jour des facteurs d'équilibrage entre le noeud A et le nœud inséré Y
			while (P != Y) {
				if (Y.compareTo(P) <= 0) {
					P.deseq = P.deseq+1;
					P = P.filsg;
				} else {
					P.deseq = P.deseq-1;
					P = P.filsd;
				}
			}
			

			// Le seul noeud qui pourrait être déséquilibré (facteur -2 ou 2) est le nœud A, 
			// qui doit être équilibré avec des rotations
			
			// Phase 4: Vérification de l'équilibrage du nœud A
			if(A.deseq == -2) {
				
				// Phase 5a: Trouvez et effectuez la rotation appropriée, 
				// 			procéder à la mise à jour des facteurs d'équilibre
				
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
				
				// Phase 5b: Trouvez et effectuez la rotation appropriée, 
				// 			procéder à la mise à jour des facteurs d'équilibre
				
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
	 * Visite de l'arbre avec une racine indiquée lors un parcour infixe. 
	 * Une fois visités, tous les nœuds sont insérés dans une liste.
	 * @param racine point de départ
	 * @return Liste ordonnée des nœuds parcourus
	 */
	public List<NoeudAVL> parcoursAVL (NoeudAVL racine){
		ArrayList <NoeudAVL> list = new ArrayList <NoeudAVL> ();
		if (racine != null) {
			// algorithme récursif
			list.addAll(this.parcoursAVL(racine.filsg));
			list.add(racine);
			list.addAll(this.parcoursAVL(racine.filsd));
		}
		return list;
	}
	
	
	/**
	 *Recherche la chaîne donnée en paramètre et retourne le référent sur la chaine recherchée 
	 *dans l'arbre AVL si l'élément existe, et retourne null sinon. 
	 * @param s nom du noeud à rechercher
	 * @return noeud 
	 */
	public NoeudAVL rechercheAVL (String s) {
		return this.recherche (s, this.racine);
	}

	/**
	 * Recherche le noeud avec le nom s dans le sous-arbre avec racine noued
	 * @param s nom du noeud à rechercher
	 * @param noeud racine du sous-arbre
	 * @return noued trouvé
	 */
	private NoeudAVL recherche(String s, NoeudAVL noeud) {
		if (noeud == null)
			return null;
		// algorithme récursif
		// position du noeud à rechercher par rapport à la racine du sous-arbre
		if (s.compareTo(noeud.nom)<0)
			// Le noeud à rechercher est situé à guache de la racine du sous-arbre
			return recherche (s, noeud.filsg);
		else {
			if (s.compareTo(noeud.nom)>0)
				// Le noeud à rechercher est situé à droite de la racine du sous-arbre
				return recherche (s, noeud.filsd);
			else
				// noued trouvé
				return noeud;
		}
	}
		

	/**
	 * Recherche la chaîne donnée en paramètre et la supprime de l'arbre AVL, 
	 * et retourne le référent sur la chaine supprimée; 
	 * et retourne null si l'élément à supprimer n'existe pas. 
	 * 
	 * <br>Phase 1: Suppression du noeud de manière standard comme dans un arbre de recherche binaire
	 * <br>Phase 2: Mise à jour des facteurs d'équilibrage du nouveau sous-arbre + rotations + mise à jour des nœuds
	 * <br>Phase 3: Vérification des facteurs d'équilibrage de la racine au nouveau nœud ajouté avec toutes les rotations
	 * 
	 * @param s nom du noeud à supprimer
	 * @return noeud supprimé
	 */
	public NoeudAVL suppAVL (String s) {
		// Identification du nœud à supprimer
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
				// met à jour le facteur d'équilibre du sous-arbre après la suppression
				suppNoeud.deseq = this.hauter(suppNoeud.filsg) - this.hauter (suppNoeud.filsd);
			
			pereSupp.deseq = this.hauter(pereSupp.filsg) - this.hauter (pereSupp.filsd);	
			
			// Phase 3
			this.equilibrage(suppNoeud, pereSupp);
			
			taille = taille - 1;
		}
		
		return supp;
	}
	
	
	/**
	 * Effectuer les opérations de suppression d'un nœud (3 cas)
	 * @param supp noeud à supprimer
	 * @return racine de sous-arborescence après l'opération de suppression
	 */
	private NoeudAVL suppNoeud(NoeudAVL supp) {
		
		if (supp != null) {
			
			// Cas 1: le nœud à supprimer n'a pas enfants (c'est une feuille)
			if (supp.filsg == null && supp.filsd == null)
				// remplacement du noeud par null
				return this.remplacer(supp, null);
			
			
			// Cas 2: le nœud à supprimer n'a qu'un seul fils:
			
			else if (supp.filsg != null && supp.filsd == null)
				// remplacement du noeud par son fils gauche
				return this.remplacer(supp, supp.filsg);
			else if (supp.filsg == null && supp.filsd != null)
				// remplacement du noeud par son fils droit
				return this.remplacer(supp, supp.filsd);
			
			
			// Cas 3: le nœud à supprimer a deux enfants: 
			
			else if (supp.filsg != null && supp.filsd != null) {
				
				// trouver le successeur qui n'a plus d'enfant, détacher le successeur
				NoeudAVL succ = this.successeur(supp.filsd); 
				
				NoeudAVL pereSuccessore = this.pere(succ);
				
				if (pereSuccessore != supp) {
					// le fils gauche (le successeur lui-même d'abord) du père du successeur 
					// devient le fils droit du successeur
					pereSuccessore.filsg = succ.filsd;
					
					// le successeur prendra la place du nœud à supprimer 
					// pour que son fils droit devienne le fils droit du nœud à supprimer
					succ.filsd = supp.filsd;
				}
				
				// le fils gauche du successeur devient le fils gauche du nœud à supprimer
				succ.filsg = supp.filsg;
				
				return this.remplacer(supp, succ);
				
			}
			
		}
		return null;
		
		
	}
	
	
	/**
	 * Remplace un nœud par un autre nœud 
	 * @param supp noeud à remplacer 
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
			// Le nœud à supprimer était la racine de l'arbre
			this.racine = n;
		
		return n;

	}

	/**
	 * Effectuer les opérations de mise à jour des facteurs d'équilibrage et démarrer les rotations
	 * @param suppNoeud noeud
	 * @param pereSupp père noeud
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
	 * Il gère les rotations et la mise à jour des facteurs d'équilibrage après les rotations
	 * @param A noeud avant rotation
	 * @return nœud après rotation
	 */
	private NoeudAVL checkEquilibrage(NoeudAVL A) {
		// Phase 4: Vérification de l'équilibrage du nœud A
		NoeudAVL r = new NoeudAVL ();
		
		if(A.deseq == -2) {
			
			// Phase 5a: Trouvez et effectuez la rotation appropriée, 
			// 			procéder à la mise à jour des facteurs d'équilibre
			
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
			
			// Phase 5b: Trouvez et effectuez la rotation appropriée, 
			// 			procéder à la mise à jour des facteurs d'équilibre
			
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
	 * Trouver le successeur du nœud passé en paramètre: 
	 * c'est le nœud le plus bas qui n'a pas de fils à gauche
	 * @param n noeud
	 * @return successeur
	 */
	public NoeudAVL successeur(NoeudAVL n) {
		while (n.filsg != null)
			n = n.filsg;
		
		return n;
	}

	
	/**
	 * Étant donné un nœud, il trouve son nœud parent
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
	 * Calculer la hauteur d'un nœud passé en paramètre
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


