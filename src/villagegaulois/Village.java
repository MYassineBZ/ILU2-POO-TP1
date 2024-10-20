package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;
	private Marche marche;

	public Village(String nom, int nbVillageoisMaximum, int nbEtal) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
		this.marche = new Marche(nbEtal);

	}

	private static class Marche {
		private Etal[] etals;

		private Marche(int nbEtal) {
			this.etals = new Etal[nbEtal];
			for (int i = 0; i < nbEtal; i++) {
				this.etals[i] = new Etal();
			}
		}

		private void utiliserEtal(int indiceEtal, Gaulois Vendeur, String produit, int nbProduit) {
			etals[indiceEtal].occuperEtal(Vendeur, produit, nbProduit);
		}

		private int trouverEtalLibre() {
			int longueur = this.etals.length;
			for (int cpt = 0; cpt != longueur - 1; cpt++) {
				if (!this.etals[cpt].isEtalOccupe()) {
					return cpt;
				}

			}
			return -1;

		}

		private Etal[] trouverEtals(String produit) {
			int nbproduit = 0;
			for (int i = 0; i < etals.length; i++) {
				if (this.etals[i].contientProduit(produit)) {
					nbproduit++;
				}

			}
			Etal[] VendProduit = new Etal[nbproduit];

			for (int j = 0, k = 0; j < etals.length; j++) {
				if (etals[j].contientProduit(produit)) {
					VendProduit[k] = etals[j];
					k++;
				}

			}
			return VendProduit;
		}

		private Etal trouverVendeur(Gaulois gaulois) {
			for (int i = 0; i < etals.length; i++) {
				if (etals[i].getVendeur().equals(gaulois)) {
					return etals[i];
				}
			}
			return null;
		}

		private void afficherMarche() {
			int nbEtal = 0;
			for (int i = 0; i < etals.length; i++) {
				if (!this.etals[i].isEtalOccupe()) {
					nbEtal++;
				}
			}

			if (nbEtal == 0) {
				System.out.println("Il ne reste aucun étals libre \n");
			}
			System.out.println("Il reste" + nbEtal + "étals non utilisés dans le marché\n");
		}
	}

	public String getNom() {
		return nom;
	}

	public void setChef(Chef chef) {
		this.chef = chef;
	}

	public void ajouterHabitant(Gaulois gaulois) {
		if (nbVillageois < villageois.length) {
			villageois[nbVillageois] = gaulois;
			nbVillageois++;
		}
	}

	public Gaulois trouverHabitant(String nomGaulois) {
		if (nomGaulois.equals(chef.getNom())) {
			return chef;
		}
		for (int i = 0; i < nbVillageois; i++) {
			Gaulois gaulois = villageois[i];
			if (gaulois.getNom().equals(nomGaulois)) {
				return gaulois;
			}
		}
		return null;
	}

	public String afficherVillageois() {
		StringBuilder chaine = new StringBuilder();
		if (nbVillageois < 1) {
			chaine.append("Il n'y a encore aucun habitant au village du chef " + chef.getNom() + ".\n");
		} else {
			chaine.append("Au village du chef " + chef.getNom() + " vivent les légendaires gaulois :\n");
			for (int i = 0; i < nbVillageois; i++) {
				chaine.append("- " + villageois[i].getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public String installerVendeur(Gaulois vendeur, String produit, int nb_Produit) {
		int premierEtalVide = marche.trouverEtalLibre();
		StringBuilder chaine = new StringBuilder();
		String nomVendeur = vendeur.getNom();
		chaine.append(nomVendeur + " cherche un endroit pour vendre " + nb_Produit + " " + produit + ".\nLe vendeur "
				+ nomVendeur + " ");
		if (premierEtalVide != -1) {
			marche.utiliserEtal(premierEtalVide, vendeur, produit, nb_Produit);
			premierEtalVide++;
			chaine.append("vend des " + produit + " a l'etal n°" + premierEtalVide + "\n");

		} else {
			chaine.append("n'a pas trouve dd'etals libre afin de vendre ces " + produit + "\n");
		}
		return chaine.toString();

	}

	public String rechercherVendeursProduit(String Produit) {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Les vendeurs qui proposent des " + Produit + " sont : \n");
		Etal[] etalProduit = marche.trouverEtals(Produit);
		if (etalProduit.length == 0) {
			StringBuilder pasTrouver = new StringBuilder();
			pasTrouver.append("Il n'y a pas de vendeur qui propose des " + Produit + " au marché\n");
			return pasTrouver.toString();
		}
		if (etalProduit.length == 1) {
			StringBuilder Seul = new StringBuilder();
			Seul.append("Seul le vendeur " + etalProduit[0].getVendeur().getNom() + " propose des " + Produit
					+ " au marche\n");
			return Seul.toString();
		}
		for (int i = 0; i < etalProduit.length; i++) {
			Etal etalActuel = etalProduit[i];
			if (etalActuel.contientProduit(Produit)) {
				chaine.append("-" + etalActuel.getVendeur().getNom() + "\n");
			}
		}
		return chaine.toString();
	}

	public Etal rechercherEtal(Gaulois vendeur) {
		Etal[] listeEtal = marche.etals;
		for (int i = 0; i < listeEtal.length; i++) {
			if (listeEtal[i].getVendeur() == vendeur) {
				return listeEtal[i];
			}
		}
		return null;
	}

	public String partirVendeur(Gaulois vendeur) {
		Etal etalActuel = rechercherEtal(vendeur);
		return etalActuel.libererEtal();
	}

	public String afficherMarche() {
		StringBuilder chaine = new StringBuilder();
		chaine.append("Le marche du village \"" + nom + "\" possede plusieurs etals: \n");
		Etal[] listeEtals = marche.etals;
		int nbEtals = 0;
		for (int i = 0; i < listeEtals.length; i++) {
			Etal etalActuel = listeEtals[i];
			if (etalActuel != null && etalActuel.getQuantite() != 0) {
				Gaulois vendeur = etalActuel.getVendeur();
				chaine.append(
						vendeur.getNom() + " vend " + etalActuel.getQuantite() + " " + etalActuel.getProduit() + "\n");
				nbEtals++;

			}
		}
		chaine.append("Il reste " + (marche.etals.length - nbEtals) + " étals non utilisés dans le marché.\n");
		return chaine.toString();
	}
}