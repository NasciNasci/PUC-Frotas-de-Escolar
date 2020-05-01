# PUC-Frotas-de-Escolar
Trabalho da disciplina Laboratório de Desenvolvimento para Dispositivos Móveis, cujo objetivo é criar um MVP de aplicativo que interligue frotas de transporte escolar, inicialmente universitário, aos alunos interessados.

# Como contribuir

Primeiro é necessário que este repositório remoto seja clonado para sua máquina, criando assim um repositório local no qual você pode realizar alterações que podem ser submetidas para uma fusão (merge) com o repositório remoto. Para clonar o repositório, abra seu prompt de comando ou git e insira o comando abaixo, subtituindo a tag **<HTTPS>** pela cópia link URL para clone HTTPS do repositório em questão - na página inicial do repositório na opção **Clone or Download**.

### 1. Clonando um repositório

```
git clone <HTTPS>
```

Depois de clonado o repositório, crie uma branch para a edição do mesmo em sua máquina. Uma branch é uma ramificação do projeto original, na qual você pode rastrear modificações no arquivo por meio de diff e submetê-lo para um merge com o repositório remoto.

### 2. Criando uma branch

```
git branch <Branch Name>
```

Substitua **<Branch Name>** pelo nome da sua branch. Agora você já pode adicionar, remover e atualizar dados do repositório clonado e depois submetê-lo para merge! Após a conclusão das alterações sobre os dados, deve-se fazer o add do repositório para o INDEX do git, preparando-o para a realização do commit, que é a confirmação das alterações realizadas localmente - caso vá submeter as alterações via Android Studio, esta etapa pode ser desconsiderada.

### 3. Fazendo add

```
git add 
```

### 4. Fazendo commit

```
git commit -m "comment"
```

Substitua **"comment"** por um comentário para commit. Por fim, envie sua branch para o repositório remoto através do comando push, que enviará todos os arquivos de volta para o github, onde pederá realizar um pedido para merge com a branch master.

### 5. Realizando push

```
git push -u origin "branch-name"
```

Substitua **"branch-name"** pelo nome da branch criada no passo 2. Para finalizar, acesse sua branch através do repositório remoto, já no github após a submissão, e seleciona a opção de **Pull request** para que a requisição de merge sejá feita. Assim que o administrador aprovar sua requisição, as alterações realizadas serão acrescidas à branch master.

# Caso as alterações tenham sido feitas via Android Studio



