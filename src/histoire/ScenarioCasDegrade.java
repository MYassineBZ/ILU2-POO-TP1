package histoire;

import personnages.Gaulois;
import villagegaulois.Etal;

public class ScenarioCasDegrade {

	public static void main(String[] args) {
		Gaulois Adam = new Gaulois("Adam", 6);
		Etal etal = new Etal();
		etal.libererEtal();
		System.out.println("Fin du test\n");
		etal.acheterProduit(3, Adam);

	}

}
