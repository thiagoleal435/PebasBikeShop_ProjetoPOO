package br.com.bikeshop.dao;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class GerenciadorDados {

    // Método genérico que salva qualquer tipo de lista (Clientes, Produtos, Vendas)
    // "U" é o tipo de dado que você vai passar
    public static <U> void salvar(List<U> lista, String nomeArquivo) {
        // Tenta escrever o arquivo. O "try-with-resources" fecha o arquivo automaticamente
        try (ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(nomeArquivo))) {
            oos.writeObject(lista);
            System.out.println("Dados salvos com sucesso em: " + nomeArquivo);
        } catch (IOException e) {
            System.err.println("Erro ao salvar arquivo: " + e.getMessage());
        }
    }

    // Método genérico que lê o arquivo e retorna uma lista
    @SuppressWarnings("unchecked")
    public static <U> List<U> carregar(String nomeArquivo) {
        List<U> lista = new ArrayList<>();
        
        File arquivo = new File(nomeArquivo);
        if (!arquivo.exists()) {
            return lista; // Se o arquivo não existe, retorna lista vazia
        }

        try (ObjectInputStream ois = new ObjectInputStream(new FileInputStream(nomeArquivo))) {
            lista = (List<U>) ois.readObject();
            System.out.println("Dados carregados de: " + nomeArquivo);
        } catch (IOException | ClassNotFoundException e) {
            System.err.println("Erro ao ler arquivo: " + e.getMessage());
        }
        
        return lista;
    }
}
