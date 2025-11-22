📘 Sistema de Gestão para Loja de Bicicletas
Controle de produtos, clientes, vendas e estoque – com interface gráfica em Java Swing
🛒 Sobre o Projeto

Este repositório contém o desenvolvimento de um Sistema de Gestão para uma Loja de Bicicletas, criado para simular o funcionamento de um comércio especializado na venda de bicicletas e acessórios.
O sistema foi desenvolvido utilizando Java, com:

Arquitetura em camadas

Persistência de dados via arquivos (serialização)

Interface gráfica feita em Java Swing

Operações completas de CRUD

Módulo de vendas com emissão de fatura

Controle de estoque com geração de alertas

🚴 Contexto da Loja

A loja fictícia comercializa diversos tipos de bicicletas:

Mountain Bike

Bicicleta Urbana

Bicicleta Elétrica

Bicicleta Infantil

Speed/Road bikes

Além de acessórios e componentes.
Cada produto possui características específicas do setor, como tamanho de aro, tipo de quadro e categoria.

O sistema permite registrar produtos, clientes, vendas, emitir faturas, controlar estoque e gerar relatórios de desempenho da loja.

🧩 Funcionalidades
🔧 Gestão de Produtos (CRUD)

Cadastrar novos produtos

Editar informações

Excluir produtos

Consultar e listar produtos

Definir estoque mínimo individual

Alerta automático quando o estoque fica abaixo do mínimo

👥 Gestão de Clientes (CRUD)

Cadastrar clientes

Alterar dados

Excluir clientes

Consultar e listar

🛒 Gestão de Vendas

Registrar venda associando cliente + itens de compra

Atualizar estoque automaticamente

Emitir fatura da venda

Registrar data e valor total

📊 Relatórios e Estatísticas

Total de vendas no mês

Produto mais vendido

Produto menos vendido

Melhor cliente

Dia com maior número de vendas

💾 Persistência de Dados

Utilização de arquivos com serialização Java

Sem uso de banco de dados

🎨 Interface Gráfica (Java Swing)

Layout simples e funcional

Telas para produtos, clientes e vendas

Menus e navegação intuitiva

🏗 Arquitetura em Camadas

Model – entidades e lógica básica

DAO/Repository – persistência em arquivos

Service – regras de negócio

Controller – integração entre view e lógica

View – interface Swing
