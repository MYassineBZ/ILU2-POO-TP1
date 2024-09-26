package villagegaulois;

import personnages.Chef;
import personnages.Gaulois;

public class Village {
	private String nom;
	private Chef chef;
	private Gaulois[] villageois;
	private int nbVillageois = 0;

	public Village(String nom, int nbVillageoisMaximum) {
		this.nom = nom;
		villageois = new Gaulois[nbVillageoisMaximum];
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

		private void afficheMarche() {
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

}