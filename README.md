# 🏥 Sistema de Fila de Atendimento com Prioridades

Sistema de gerenciamento de filas de atendimento desenvolvido em Java, que implementa priorização para idosos seguindo as regras de atendimento preferencial estabelecidas por lei.

## 📋 Descrição do Projeto

Este sistema simula o funcionamento de filas de atendimento em locais como bancos, cartórios e repartições públicas, onde é necessário priorizar o atendimento de pessoas idosas, respeitando também o atendimento das demais pessoas para evitar o starvation (espera indefinida).

## 🎯 Funcionalidades

- ✅ Cadastro de pessoas com nome e idade
- ✅ Classificação automática em filas (comum, 65+ e 80+)
- ✅ Sistema de cotas para atendimento prioritário
- ✅ Prevenção de starvation para pessoas comuns
- ✅ Gerenciamento de múltiplos guichês (comuns e preferenciais)
- ✅ Simulação de ordem de atendimento
- ✅ Cálculo de tempo médio de espera
- ✅ Visualização do estado das filas em tempo real

## 🏗️ Estrutura do Projeto

```
EstruturaDados.TrabalhoPratico/
│
├── Pessoa.java                    # Representa cada pessoa na fila
├── No.java                        # Nó da lista encadeada
├── FilaAtendimento.java           # Implementação da estrutura de fila
├── Guiche.java                    # Representa cada guichê de atendimento
├── GerenciadorDeAtendimento.java  # Lógica de priorização e escalonamento
└── AppFila.java                   # Aplicação principal (interface do usuário)
```

## 🔧 Tecnologias Utilizadas

- **Linguagem:** Java
- **Estruturas de Dados:** Fila (Queue) com Lista Encadeada
- **Paradigma:** Programação Orientada a Objetos (POO)
- **Bibliotecas:** 
  - `java.util.Scanner` (entrada de dados)
  - `java.time.Instant` e `java.time.Duration` (cálculo de tempo)

## 📊 Sistema de Prioridades

### Tipos de Filas

| Fila | Idade | Prioridade |
|------|-------|------------|
| **Comum** | < 65 anos | Baixa |
| **Prioritária 65+** | 65-79 anos | Média |
| **Prioritária 80+** | ≥ 80 anos | Alta |

### Sistema de Cotas

O sistema implementa um **ciclo de atendimento** nos guichês preferenciais:

```
[80+] → [80+] → [65+] → [Comum*] → [Repete...]
  ↓       ↓       ↓         ↓
Cota 1  Cota 2  Cota 3  Starvation
```

- **2 pessoas de 80+** são atendidas
- **1 pessoa de 65+** é atendida
- **1 pessoa comum** é atendida (prevenção de starvation)
- Ciclo se repete

### Tipos de Guichês

#### Guichês Preferenciais
- Priorizam idosos (80+ > 65+)
- Seguem o sistema de cotas
- Atendem pessoas comuns em caso de:
  - Starvation (após completar ciclo)
  - Ociosidade (filas prioritárias vazias)

#### Guichês Comuns
- Priorizam pessoas comuns
- Atendem idosos apenas em caso de ociosidade
- Ordem de ociosidade: Comum → 65+ → 80+

## 🚀 Como Executar

### Pré-requisitos
- JDK 8 ou superior instalado
- IDE Java (Eclipse, IntelliJ, NetBeans) ou compilador javac

### Compilação e Execução

**Via Terminal:**
```bash
# Compilar
javac EstruturaDados/TrabalhoPratico/*.java

# Executar
java EstruturaDados.TrabalhoPratico.AppFila
```

**Via IDE:**
1. Importe o projeto
2. Execute a classe `AppFila.java`

## 📖 Como Usar

### Menu Principal

```
--- MENU ---
1 - Incluir pessoa
2 - Liberar atendimento
3 - Exibir estado da fila
4 - Exibir guichês
5 - Exibe ordem de atendimento
6 - Tempo médio das pessoas em geral
7 - Tempo médio das pessoas comuns
8 - Tempo médio das pessoas prioritárias
9 - Sair
```

### Fluxo de Uso Básico

1. **Iniciar o sistema:** Informe o número de guichês desejado
2. **Adicionar pessoas:** Opção 1 - cadastre nome e idade
3. **Liberar atendimento:** Opção 2 - processa um ciclo de atendimento
4. **Visualizar filas:** Opção 3 - veja quem está esperando
5. **Ver estatísticas:** Opções 6-8 - analise tempos médios de espera

## 💡 Conceitos de Estrutura de Dados

### Fila (Queue)
- **Tipo:** FIFO (First In, First Out)
- **Implementação:** Lista encadeada simples
- **Operações:**
  - `enfileirar()`: O(1) - adiciona no fim
  - `desenfileirar()`: O(1) - remove do início

### Lista Encadeada
```
[Pessoa1] → [Pessoa2] → [Pessoa3] → null
   ↑                                  ↑
 início                              fim
```

### Algoritmo de Escalonamento
- **Prioridade por idade:** 80+ > 65+ > comum
- **Sistema de cotas:** Limita atendimentos consecutivos
- **Prevenção de starvation:** Garante atendimento eventual de todos
- **Ociosidade:** Aproveita recursos livres

## 📈 Exemplo de Execução

```
Digite o número de guichês: 4
(2 guichês comuns + 2 guichês preferenciais)

--- MENU ---
Opção: 1
Nome: João
Idade: 85
Pessoa adicionada à fila 80+

Opção: 1
Nome: Maria
Idade: 70
Pessoa adicionada à fila 65+

Opção: 1
Nome: Pedro
Idade: 30
Pessoa adicionada à fila comum

Opção: 2
Guichê Preferencial: Atendeu João (85 anos)
Guichê Preferencial: Livre (Filas Vazias).
Guichê Comum: Atendeu Pedro (30 anos)
Guichê Comum: Livre (Filas Vazias).

Opção: 6
O tempo médio de todos os atendidos foi: 1523 milissegundos
```

## 🐛 Problemas Conhecidos

- [ ] Opções do menu estão numeradas incorretamente (5-9 deveriam ser 5-8)
- [ ] Variável `numPessoa` em `FilaAtendimento` nunca é incrementada
- [ ] `contadorGlobalCiclo` é compartilhado entre simulação e atendimento real
- [ ] Falta tratamento de exceções para entradas inválidas

## 🔮 Melhorias Futuras

- [ ] Interface gráfica (GUI)
- [ ] Persistência de dados (banco de dados ou arquivo)
- [ ] Relatórios em PDF
- [ ] Sistema de senhas
- [ ] Notificações sonoras
- [ ] Múltiplas categorias de prioridade
- [ ] Configuração dinâmica de cotas
- [ ] Testes unitários

## 👥 Autores

Desenvolvido como Trabalho Prático da disciplina de Estrutura de Dados.

## 📄 Licença

Este projeto é de uso educacional.

## 📚 Referências

- Estruturas de Dados e Algoritmos em Java
- Lei Federal nº 10.048/2000 (Atendimento Prioritário)
- Decreto nº 5.296/2004 (Regulamenta atendimento prioritário)

---

⭐ **Dica:** Para entender melhor o funcionamento, execute o sistema com poucos guichês (2-3) e vá adicionando pessoas de diferentes idades para observar a ordem de atendimento.

📧 Para dúvidas ou sugestões, abra uma issue neste repositório.
