# PUC-Frotas-de-Escolar
Trabalho da disciplina Laboratório de Desenvolvimento para Dispositivos Móveis, cujo objetivo é criar um MVP de aplicativo que interligue frotas de transporte escolar, inicialmente universitário, aos alunos interessados.

# Como contribuir

Antes de tudo, é necessário que possua o git instalado em sua máquina. 
Isso pode ser feito acessando este link: https://git-scm.com/download/win

Depois, insira suas credenciais de acesso via git bash ou seu prompt de comando.

### 0. Configurando o git

Primeiro insira seu username por meio do seguinte comando - substitua **"user name"** pelo seu nome de usuário.

```
git config --global user.name "user name"
```

E depois seu email de acesso.

```
git config --global user.email "myemail@email.com"
```

Depois de configurado o git, é necessário que este repositório remoto seja clonado para sua máquina, criando assim um repositório local no qual você pode realizar alterações que podem ser submetidas para uma fusão (merge) com o repositório remoto. Para clonar o repositório, abra seu prompt de comando ou git e insira o comando abaixo, subtituindo a tag **<HTTPS>** pela cópia link URL para clone HTTPS do repositório em questão - na página inicial do repositório na opção **Clone or Download**.

### 1. Clonando um repositório

```
git clone <HTTPS>
```

Depois de clonado o repositório, crie uma branch para a edição do mesmo em sua máquina. Uma branch é uma ramificação do projeto original, na qual você pode rastrear modificações no arquivo por meio de diff e submetê-lo para um merge com o repositório remoto.

### 2. Criando uma branch

```
git branch <Branch Name>
```

Substitua **<Branch Name>** pelo nome da sua branch. Agora você já pode adicionar, remover e atualizar dados do repositório clonado e depois submetê-lo para merge! Após a conclusão das alterações sobre os dados, deve-se fazer o add do repositório para o INDEX do git, preparando-o para a realização do commit, que é a confirmação das alterações realizadas localmente - caso vá submeter as alterações via Android Studio, esta etapa pode ser desconsiderada. Depois de criada a branch, altere para ela para que ela receba as alterações ao invés da branch master.

```
git checkout <Branch Name>
```

### 3. Fazendo add

```
git add 
```

### 4. Fazendo commit

```
git commit -m "comment"
```

Substitua **"comment"** por um comentário para commit. Por fim, envie sua branch para o repositório remoto através do comando push, que enviará todos os arquivos de volta para o GitHub, onde pederá realizar um pedido para merge com a branch master.

### 5. Realizando push

```
git push -u origin "branch-name"
```

Substitua **"branch-name"** pelo nome da branch criada no passo 2. Para finalizar, acesse sua branch através do repositório remoto, já no GitHub após a submissão, e seleciona a opção de **Pull request** para que a requisição de merge sejá feita. Assim que o administrador aprovar sua requisição, as alterações realizadas serão acrescidas à branch master.

# Caso as alterações tenham sido feitas via Android Studio

Caso esteja utilizando o Android Studio, a plataforma deixa as coisas mais simples. 

### 0. Caso ainda não tenha inicializado o git

Pela página inicial do Android Studio, acesse a opção **Configure** -> **Settings** -> **Version Control** -> **GitHub**, adicione uma nova conta do GitHub e selecione **Log In**. Depois, siga os passos abaixo para inicializar o repositório.  

### 1. Depois de inicializado o git

Caso já tenha inicializado o git no Android, na tela inicial do Android Studio, selecione a opção **Check out project from Version Control** e a IDE já retornará uma lista com todos os seus repositórios existentes no GitHub. Para clonar um repositório, selecione uma das opções ou insira a URL do repositório, escolha a pasta na qual o repostiório será clonado e pressione a opção **Clone**. Quando for perguntado **Would you like to create an Android Studio project for the sources you have checked out to C:\"Seu local de armazenamento"?** pressione **Yes** e finalize a clonagem do repositório. 

### 2. Para enviar a branch para merge

Uma vez dentro do projeto, no menu superior, surgirá uma nova aba de ferramentas para o Git, que incluem **Update** (pull), **Commit**, **Show History** e **Revert**, que permitem o rastreamento do código. Depois de finalizadas as alterações, selecione a opção de **Commit** e depois **Commit and Push** para enviar as alterações para o repositório remoto.

