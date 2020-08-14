package br.am.rbalves.core.factories;
import com.github.javafaker.Faker;

public class RestricaoFactory {
	
	private static String[] cpfsComRestricao = { "97093236014", "60094146012", "84809766080", "62648716050", "26276298085",
            "01317496094", "55856777050", "19626829001", "24094592008", "58063164083" };

    public static String buscarCpfComRestricao() {
        Faker faker = new Faker();
        int indice = faker.number().numberBetween(0, 11);
        return cpfsComRestricao[indice];
    }

    public static Boolean verificarSeCpfExiste(String cpf) {
        for (String item : cpfsComRestricao) {
            if (item.equals(cpf)) {
                return true;
            }
        }
        return false;
    }

    public static String buscarCpfSemRestricao() {

        Faker faker = new Faker();
        String cpf = Long.toString(faker.number().randomNumber(11, true));

        while (verificarSeCpfExiste(cpf)) {
            cpf = Long.toString(faker.number().randomNumber(11, true));
        }

        return cpf;
    }

}
