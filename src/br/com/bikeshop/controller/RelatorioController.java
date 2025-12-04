package br.com.bikeshop.controller;

import br.com.bikeshop.model.Cliente;
import br.com.bikeshop.model.Produto;
import br.com.bikeshop.model.Venda;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

public class RelatorioController {

    private List<Venda> vendas;

    public RelatorioController() {
        // Aproveita o carregamento que já existe no VendaController
        this.vendas = new VendaController().listarVendas();
    }

    // 1. Total de Vendas do Mês Atual
    public String getMetricaMesAtual() {
        if (vendas.isEmpty()) return "Sem dados.";

        LocalDate hoje = LocalDate.now();
        String mesAnoAtual = String.format("/%02d/%d", hoje.getMonthValue(), hoje.getYear());
        
        int qtdVendas = 0;
        double valorTotal = 0.0;

        for (Venda v : vendas) {
            if (v.getData().endsWith(mesAnoAtual)) { // Verifica se a data termina com /MM/AAAA
                qtdVendas++;
                valorTotal += v.getValorTotal();
            }
        }

        return String.format("Qtd: %d vendas | Total: R$ %.2f", qtdVendas, valorTotal);
    }

    // 2. Produto Mais Vendido (Retorna um objeto Map com os dados)
    public Map<String, String> getProdutoMaisVendido() {
        if (vendas.isEmpty()) return null;

        Map<String, Integer> contagemQtd = new HashMap<>();
        Map<String, Double> contagemValor = new HashMap<>();
        Map<String, String> nomes = new HashMap<>();

        for (Venda v : vendas) {
            String codigo = v.getProduto().getCodigo();
            contagemQtd.put(codigo, contagemQtd.getOrDefault(codigo, 0) + v.getQuantidade());
            contagemValor.put(codigo, contagemValor.getOrDefault(codigo, 0.0) + v.getValorTotal());
            nomes.put(codigo, v.getProduto().getDescricao());
        }

        // Acha o maior
        String melhorCodigo = Collections.max(contagemQtd.entrySet(), Map.Entry.comparingByValue()).getKey();

        Map<String, String> resultado = new HashMap<>();
        resultado.put("descricao", nomes.get(melhorCodigo));
        resultado.put("qtd", String.valueOf(contagemQtd.get(melhorCodigo)));
        resultado.put("total", String.format("R$ %.2f", contagemValor.get(melhorCodigo)));
        
        return resultado;
    }

    // 4. Produto Menos Vendido (Lógica inversa do mais vendido)
    public Map<String, String> getProdutoMenosVendido() {
        if (vendas.isEmpty()) return null;

        Map<String, Integer> contagemQtd = new HashMap<>();
        Map<String, Double> contagemValor = new HashMap<>();
        Map<String, String> nomes = new HashMap<>();

        for (Venda v : vendas) {
            String codigo = v.getProduto().getCodigo();
            contagemQtd.put(codigo, contagemQtd.getOrDefault(codigo, 0) + v.getQuantidade());
            contagemValor.put(codigo, contagemValor.getOrDefault(codigo, 0.0) + v.getValorTotal());
            nomes.put(codigo, v.getProduto().getDescricao());
        }

        String piorCodigo = Collections.min(contagemQtd.entrySet(), Map.Entry.comparingByValue()).getKey();

        Map<String, String> resultado = new HashMap<>();
        resultado.put("descricao", nomes.get(piorCodigo));
        resultado.put("qtd", String.valueOf(contagemQtd.get(piorCodigo)));
        resultado.put("total", String.format("R$ %.2f", contagemValor.get(piorCodigo)));

        return resultado;
    }

    // 3. Melhor Cliente (Retorna dados complexos incluindo lista de faturas)
    public Map<String, Object> getMelhorCliente() {
        if (vendas.isEmpty()) return null;

        Map<String, Double> valorPorCpf = new HashMap<>();
        Map<String, Integer> itensPorCpf = new HashMap<>();
        Map<String, Cliente> clienteMap = new HashMap<>();
        Map<String, List<String>> faturasPorCpf = new HashMap<>();

        for (Venda v : vendas) {
            String cpf = v.getCliente().getCpf();
            valorPorCpf.put(cpf, valorPorCpf.getOrDefault(cpf, 0.0) + v.getValorTotal());
            itensPorCpf.put(cpf, itensPorCpf.getOrDefault(cpf, 0) + v.getQuantidade());
            clienteMap.putIfAbsent(cpf, v.getCliente());
            
            faturasPorCpf.computeIfAbsent(cpf, k -> new ArrayList<>()).add(v.getCodigoUnico() + " (" + v.getData() + ")");
        }

        // Cliente que gastou mais dinheiro
        String melhorCpf = Collections.max(valorPorCpf.entrySet(), Map.Entry.comparingByValue()).getKey();
        Cliente c = clienteMap.get(melhorCpf);

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("nome", c.getNome());
        resultado.put("cpf", c.getCpf());
        resultado.put("qtdItens", itensPorCpf.get(melhorCpf));
        resultado.put("faturas", faturasPorCpf.get(melhorCpf).toArray(new String[0])); // Para o ComboBox
        resultado.put("totalGasto", String.format("R$ %.2f", valorPorCpf.get(melhorCpf)));

        return resultado;
    }

    // 5. Dia com Mais Vendas
    public Map<String, Object> getMelhorDia() {
        if (vendas.isEmpty()) return null;

        Map<String, Double> valorPorDia = new HashMap<>();
        Map<String, List<Venda>> vendasPorDia = new HashMap<>();

        for (Venda v : vendas) {
            String dia = v.getData();
            valorPorDia.put(dia, valorPorDia.getOrDefault(dia, 0.0) + v.getValorTotal());
            vendasPorDia.computeIfAbsent(dia, k -> new ArrayList<>()).add(v);
        }

        String melhorDia = Collections.max(valorPorDia.entrySet(), Map.Entry.comparingByValue()).getKey();
        List<Venda> vendasDoDia = vendasPorDia.get(melhorDia);

        // Descobrir item mais vendido deste dia
        Map<String, Integer> itensDia = new HashMap<>();
        for (Venda v : vendasDoDia) {
            itensDia.put(v.getProduto().getDescricao(), itensDia.getOrDefault(v.getProduto().getDescricao(), 0) + v.getQuantidade());
        }
        String itemMaisVendido = Collections.max(itensDia.entrySet(), Map.Entry.comparingByValue()).getKey();
        
        // Lista de faturas do dia
        List<String> faturas = vendasDoDia.stream().map(v -> v.getCodigoUnico() + " - " + v.getCliente().getNome()).collect(Collectors.toList());

        Map<String, Object> resultado = new HashMap<>();
        resultado.put("data", melhorDia);
        resultado.put("faturas", faturas.toArray(new String[0]));
        resultado.put("total", String.format("R$ %.2f", valorPorDia.get(melhorDia)));
        resultado.put("itemTop", itemMaisVendido);

        return resultado;
    }
}