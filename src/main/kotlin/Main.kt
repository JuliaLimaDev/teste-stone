import java.lang.Exception

fun main() {
    val nomes = arrayOf("João", "Maria", "Lara", "luna", "Otavio")

    val despesas = arrayOf(
        Despesa("Passeio de barco", 5, 53),
        Despesa("Rodizio de churrasco", 4, 45),
        Despesa("Passeio de quadriciclo", 2, 93),
        Despesa("Hospedagem", 3, 101)
    )

    val resultado = calculaPrecoPorPessoa(nomes, despesas)
    println(resultado)
}

fun calculaPrecoPorPessoa(nomes: Array<String>, despesas: Array<Despesa>): MutableMap<String, Int> {
    // Retorna erro caso alguma das listas esteja vazia.
    if (despesas.isEmpty() || nomes.isEmpty()) {
        throw Exception("Lista de despesas ou lista de nomes vazia.")
    }

    var gastoTotal = 0
    var debitoIndividual = 0

    // Iterando a lista de despesas para calcular o gasto total.
    for (despesa in despesas) {
        despesa.valorTotal = despesa.quantidade * despesa.valorUnitario

        gastoTotal += despesa.valorTotal
    }

    /*
    Calculando o débito individual com o resultado
    da divisão do gasto total com o número de participantes.
    */
    debitoIndividual = gastoTotal / nomes.size

    val resultado = mutableMapOf<String, Int>()

    /*
    Iterando a lista de nomes para criar um mapa onde as
    chaves são os nomes dos participantes e os valores são
    o que cada participante deve pagar.
    */
    for (nome in nomes) {
        resultado[nome] = debitoIndividual
    }

    /*
    Calculando o resto da divisão entre o gasto
    total e a quantidade de participantes.
    */
    val resto = gastoTotal % nomes.size

    /*
    Se o resto for igual a 0, todos os participantes irão pagar o mesmo valor.

    Caso haja resto, itera a lista de nomes e aumenta em 1 o valor a pagar de
    cada participante cujo índice é menor que o resto. Ex: Restando 3 centavos
    para uma lista de 4 participantes, os 3 primeiros receberão 1 a mais no
    valor a pagar, distribuindo o resto.
    */
    if (resto == 0) {
        return resultado
    } else {
        for ((i, nome) in nomes.withIndex()) {
            if (i < resto) {
                resultado[nome] = debitoIndividual + 1
            }

            // Se o valor a pagar dessa pessoa ainda for 0, remove do mapa.
            if (resultado[nome] == 0) {
                resultado.remove(nome)
            }
        }

        return resultado
    }
}