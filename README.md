# Guia de Execução do Programa Locadora

Este guia ajudará você a executar o programa da Locadora na sua máquina. Siga os passos abaixo para iniciar e utilizar o programa corretamente.

## Pré-Requisitos

Antes de começar, certifique-se de ter instalado:
- Java Runtime Environment (JRE) versão 8 ou superior.
- JavaFX SDK compatível com a sua versão do JRE.

## Passo a Passo

### 1. Descompactar os Arquivos

Você receberá um arquivo compactado (zipado). Salve-o em um local de fácil acesso em sua máquina.
- Clique com o botão direito do mouse sobre o arquivo compactado e escolha a opção `Extrair aqui` ou `Extract Here`.

### 2. Verificar Arquivos Descompactados

Após a extração, você deve ver uma pasta contendo, entre outros, os seguintes arquivos importantes:
- `App.jar` - O arquivo executável do programa.
- `run.bat` - Um script batch para iniciar o programa facilmente.
- Uma pasta chamada `javafx-sdk` contendo os arquivos necessários do JavaFX.

### 3. Executar o Programa

Dê um duplo clique sobre o arquivo `run.bat` para iniciar o programa.
- Se tudo estiver configurado corretamente, uma tela de login aparecerá.

### 4. Login no Sistema

Na tela de login, utilize um dos seguintes conjuntos de credenciais para acessar o sistema:

**Para o gerente:**
- **Usuário:** GERENTE
- **Senha:** GERENTE123

**Para o funcionário:**
- **Usuário:** FUNCIONARIO
- **Senha:** FUNCIONARIO123

### 5. Uso do Programa

- **Gerente:** Após o login, você será redirecionado para a tela de gerenciamento, onde poderá acessar e gerenciar as configurações, veículos e relatórios.
- **Funcionário:** Após o login, você terá acesso à tela de funcionários, onde poderá gerenciar reservas e outras tarefas relacionadas.

### 6. Navegação

Use as abas e menus disponíveis na interface gráfica para navegar entre as diferentes funcionalidades do sistema. Atente-se para a existência de menus que aceitam duplo-clique, sendo perceptíveis por sempre serem apresentados como tabelas.

### 7. Encerramento

Para encerrar o programa, feche a janela principal ou utilize a opção de saída disponível dentro do programa.

## Solução de Problemas

- Se o `run.bat` não iniciar o programa, verifique se a versão do Java instalada em sua máquina é compatível e se o JavaFX SDK está corretamente referenciado no script `run.bat`.
- Em caso de mensagens de erro relacionadas ao JavaFX, assegure-se de que o SDK do JavaFX está corretamente instalado e configurado conforme mencionado nos pré-requisitos.
- Se você encontrar erros de `ClassNotFoundException` ou `NoClassDefFoundError`, pode ser necessário configurar o classpath para incluir as bibliotecas do JavaFX.