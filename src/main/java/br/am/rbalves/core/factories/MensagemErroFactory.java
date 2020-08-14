package br.am.rbalves.core.factories;

public class MensagemErroFactory {
	
	public static String mensagemCpf(String cpf) {
        return cpf == null
                ? "CPF não pode ser vazio"
                : "CPF já existente";
    }

    public static String mensagemValor(float valor) {
        return (valor < 1000)
            ? "Valor deve ser igual ou maior a R$ 1.000"
            : "Valor deve ser menor ou igual a R$ 40.000";
    }

    public static String mensagemParcelas(int parcelas) {
        return (parcelas < 2)
                ? "Parcelas deve ser igual ou maior que 2"
                : "Parcelas deve ser menor ou igual a 48";
    }

}
