Biblioteca Escolar com conceitos de POO
---------------------------------------------------

Projeto da disciplina Programação Orientada a Objetos do curso de ADS. 

Criadores: 
[Hélder Christian](https://github.com/Heilch).
[Hellen Millena](https://github.com/millyhxs).

🎯 Objetivo
---------------------------------------------------

Este projeto tem como finalidade aplicar os conceitos de Programação Orientada a Objetos (POO) por meio do desenvolvimento de um sistema completo para gerenciamento de empréstimos de obras literárias em uma biblioteca escolar.

⚙️ Funcionalidades
---------------------------------------------------

📘 Cadastro de Obras Literárias
---------------------------------------------------
Suporte aos tipos: Livro, Revista e Artigo.

👥 Cadastro de Usuários (Leitores)
---------------------------------------------------
Tipos: Aluno, Professor e Servidor.
Funcionalidades: cadastro, edição e exclusão.

📚 Empréstimo de Obras
---------------------------------------------------
Associação de uma obra a um usuário elegível.
Cálculo automático da data prevista para devolução.

Registro da data do empréstimo e atualização do status da obra.

🔁 Devolução de Obras
---------------------------------------------------
Verificação de atraso na devolução.

Aplicação de multa, se necessário.

Registro do pagamento da multa.

🔍 Listagem e Pesquisa de Obras
---------------------------------------------------
Visualização por status: disponíveis, emprestadas ou em atraso.

Busca por título, autor ou tipo de obra.

💰 Registro de Pagamentos de Multas
---------------------------------------------------
Dados persistidos em arquivos JSON.

📄 Relatórios Gerenciais (PDF)
---------------------------------------------------
Empréstimos realizados no mês.
Obras mais emprestadas.
Usuários com mais atrasos.

🔐 Login de Usuários e Perfis de Acesso
---------------------------------------------------
Administrador: gerencia usuários, obras e outros administradores.
Bibliotecário: registra empréstimos, devoluções e gera relatórios.
Estagiário: registra apenas devoluções.

🧱 Requisitos Técnicos e Conceitos de POO Aplicados
---------------------------------------------------
Encapsulamento com modificadores.

Herança entre classes.

Polimorfismo para tratar obras de forma genérica.

Classes e métodos abstratos.

Interfaces.

Sobrecarga e sobrescrita de métodos.

Tratamento de exceções personalizadas.

---------------------------------------------------
💻 Tecnologias Utilizadas
---------------------------------------------------
Eclipse IDE

Java SE 17+

Gson – persistência de dados em JSON

Java Swing – interface gráfica

iText – geração de relatórios em PDF

Maven - Projeto Maven
