[![Review Assignment Due Date](https://classroom.github.com/assets/deadline-readme-button-22041afd0340ce965d47ae6ef1cefeee28c7c493a6346c4f15d667ab976d596c.svg)](https://classroom.github.com/a/AR7CADm8)
[![Open in Codespaces](https://classroom.github.com/assets/launch-codespace-2972f46106e565e64193e422d61a12cf1da4916b45550586e14ef0a7c637dd04.svg)](https://classroom.github.com/open-in-codespaces?assignment_repo_id=21514522)
# Proposta de aplicativo

## Equipe
* **Nome do Aluno(a) 1:** Antônio Kauã Silva Barros


---

## Título do Projeto
RecipeApp - Aplicativo de receitas

## Descrição do Projeto
Este projeto é um aplicativo mobile de receitas que facilita a organização e o acesso a diferentes pratos culinários. A proposta é oferecer uma experiência simples e eficiente para pessoas que gostam de cozinhar ou que buscam ideias rápidas para suas refeições. Com ele, o usuário pode se cadastrar, fazer login, criar sua própria receita e visualizar receitas trazidas de uma API externa, garantindo uma base inicial de conteúdo logo ao acessar o app.

Além disso, o aplicativo permite que cada usuário gerencie seu próprio espaço culinário: é possível salvar receitas favoritas, listar apenas aquelas que o próprio usuário criou e até personalizar o perfil com uma foto. Para tornar a busca mais prática, o app oferece recursos de pesquisa por nome e filtros por categoria (doce, salgada ou agridoce), ajudando o usuário a encontrar rapidamente o que deseja. Dessa forma, o projeto resolve o problema da falta de organização e da dificuldade de encontrar receitas de forma ágil e personalizada.

O público-alvo inclui desde pessoas que cozinham ocasionalmente até entusiastas que gostam de registrar e compartilhar suas criações culinárias. Com funcionalidades intuitivas e foco na personalização, o app se torna uma ferramenta prática para quem deseja armazenar, consultar e descobrir novas receitas no dia a dia.

---

## Funcionalidades Principais
[Liste as principais funcionalidades do projeto. Use caixas de seleção para que a equipe possa marcar as concluídas nas próximas etapas.]

- [x] Realizar Cadastro: Permite que o usuário crie uma conta no aplicativo utilizando e-mail e senha ou pelo google. O processo é feito através do Firebase Authentication.
- [x] Realizar Login: Autentica o usuário já cadastrado, garantindo acesso às suas receitas, favoritos e configurações do perfil. Também utiliza o Firebase Authentication.
- [x] Recuperar Senha: O usuário poderá recuperar sua senha, colocando seu email e recebendo um e-mail de recuperação. Processo também realizado pelo firebase Authentication.
- [ ] Validação de email: Usuário terá a conta ativa apenas após validar seu email.
- [x] Listar receitas: Exibe uma lista de receitas obtidas da API pública de receitas, permitindo que o usuário navegue e visualize detalhes de cada prato.
- [ ] Pesquisar receita por nome: Possibilita ao usuário buscar receitas pelo nome, filtrando os resultados exibidos na tela.
- [ ] Filtrar receita por doce, salgada ou agridoce: Permite refinar a visualização das receitas exibidas aplicando filtros de categoria conforme o tipo da receita.
- [x] Adicionar receita própria: O usuário pode criar e cadastrar uma nova receita, preenchendo informações como nome, ingredientes e preparo. As receitas são salvas no aplicativo.
- [x] Listar receitas próprias: Exibe somente as receitas criadas pelo usuário, permitindo acesso rápido ao conteúdo armazenado.
- [x] Adicionar e remover favoritos: O usuário pode favoritar ou desfavoritar receitas. Os favoritos são salvos na Firestore, associados ao usuário autenticado.
- [x] Listar favoritos: Mostra apenas as receitas que foram marcadas como favoritas, carregando os dados diretamente da Firestore.
- [x] Adicionar foto de perfil: Permite ao usuário escolher uma imagem para seu perfil e armazená-la, exibindo-a na tela de conta.
---

> [!WARNING]
> Daqui em diante o README.md só deve ser preenchido no momento da entrega final.

##  Tecnologias: 
Liste aqui as tecnologias e bibliotecas que foram utilizadas no projeto.

---

## Instruções para Execução
[Inclua instruções claras sobre como rodar o projeto localmente. Isso é crucial para que você possa testá-lo nas próximas entregas. **Somente caso haja alguma coisa diferente do usual**

```bash
# Clone o repositório
git clone [https://docs.github.com/pt/repositories/creating-and-managing-repositories/about-repositories](https://docs.github.com/pt/repositories/creating-and-managing-repositories/about-repositories)

# Navegue para o diretório
cd [nome-do-repositorio]

# Siga as instruções específicas para a sua tecnologia...
