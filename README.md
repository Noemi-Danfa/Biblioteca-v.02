# Atividade prática — Princípios de projeto

Ponto de partida: versão **v0.1**, que distribui as responsabilidades entre `Main`, `Menu`, `Biblioteca`, `Livro`, `Usuario` (com as subclasses `Aluno`, `Professor` e `Comunidade`) e `Emprestimo`.

**Regra geral:** a cada exercício, compile, rode a verificação, faça um commit e troque o driver.

**Entrega:** o link do repositório com o código final.

## 04/11/2026

## Exercício 0 — Preparação e caracterização

1. Crie um novo repositório no GitHub para esta atividade e clone-o na máquina.
2. Copie a pasta `biblioteca-v0.1` deste repositório para o seu  repositório.
3. Certifique-se que existe o arquivo `entrada.txt` na pasta `biblioteca-v0.1`. Ele contém um roteiro fixo de 22 operações: listagens, empréstimos, devoluções, erros de ID e o relatório de multas.
4. Abra um prompt de comando do PowerShell na pasta `biblioteca-v0.1`, compile e rode o roteiro, guardando a saída:

   ```powershell
   $arquivosJava = @((Get-ChildItem src -Filter *.java).FullName)
   javac -encoding UTF-8 -d out $arquivosJava
   Get-Content entrada.txt | java -cp out Main > saida-antes.txt
   ```

O arquivo `saida-antes.txt` será usado como referência para verificar se cada exercício mantém o comportamento do programa.

5. Abra `saida-antes.txt` e confira se ela contém `Devolucao realizada. Multa: R$ 3.0`, `Devolucao realizada. Multa: R$ -4.0` e `Total de multas arrecadadas: R$ -1.0`.
6. Faça o primeiro commit (sem a pasta `out/`, coloque-a no arquivo `.gitignore`):
   
   ```powershell
   git add src entrada.txt saida-antes.txt
   git commit -m "Ponto de partida v0.1"
   ```
   
**Como verificar cada exercício:** depois de cada mudança, compile e rode de novo, gerando `saida-depois.txt`, e compare:

```powershell
$arquivosJava = @((Get-ChildItem src -Filter *.java).FullName)
javac -encoding UTF-8 -d out $arquivosJava
Get-Content entrada.txt | java -cp out Main > saida-depois.txt
Compare-Object (Get-Content saida-antes.txt) (Get-Content saida-depois.txt)
```

Se `Compare-Object` não imprimir nada, as saídas são idênticas.

## Exercício 1 — Demeter

**Problema na v0.1:** em `Biblioteca.devolver`, a linha `emprestimo.getLivro().devolver();` faz `Biblioteca` navegar por dentro de `Emprestimo` até chegar ao `Livro`. A Lei de Demeter recomenda conversar apenas com objetos "vizinhos" e não encadear chamadas pela estrutura interna de outro objeto.

**Passos:**

1. Em `Emprestimo`, crie o método `public double encerrar(int diasAtraso)`. Ele deve:
   - chamar `livro.devolver()`;
   - retornar `calcularMulta(diasAtraso)`.
2. Em `Biblioteca.devolver`, troque as duas linhas que calculam a multa e devolvem o livro por uma única chamada: `double multa = emprestimo.encerrar(diasAtraso);`.
3. Confirme que `Biblioteca` não contém mais o trecho `getLivro().` (use a busca do editor).

**Verificação:** compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.

**Commit:** `Ex1: Demeter em devolver`. Troque o driver.

## 06/11/2026

## Exercício 2 — Responsabilidade Única e composição

**Problema na v0.1:** `Biblioteca` guarda livros, usuários e empréstimos **e** controla o dinheiro arrecadado (`totalMultasArrecadadas`). Ela muda por motivos demais. Comece separando a responsabilidade mais simples: o caixa de multas.

**Passos:**

1. Crie `src/Caixa.java` com:
   - o atributo `private double total = 0.0;`;
   - o método `public void registrar(double multa)`, que soma a multa ao total;
   - o método `public double getTotal()`.
2. Em `Biblioteca`:
   - remova o atributo `totalMultasArrecadadas`;
   - crie o atributo `private final Caixa caixa = new Caixa();`;
   - em `devolver`, troque `totalMultasArrecadadas += multa;` por `caixa.registrar(multa);`;
   - em `getTotalMultasArrecadadas()`, retorne `caixa.getTotal()`.

**Verificação:** compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.

**Commit:** `Ex2: extrai Caixa`. Troque o driver.

## Exercício 3 — Aberto/Fechado

**Problema na v0.1:** `Biblioteca.cadastrarUsuario(String nome, String tipo)` usa um `switch` para decidir qual subclasse criar. Para criar um tipo de usuário novo, é preciso **modificar** `Biblioteca`. O princípio Aberto/Fechado pede que o sistema seja estendido com código novo, sem alterar classes que já funcionam.

**Passos:**

1. Em `Biblioteca`, substitua o método `cadastrarUsuario(String nome, String tipo)` inteiro por:
   - `public void adicionarUsuario(Usuario usuario)`, que apenas adiciona o usuário à lista.
2. Em `Main.seed`, troque as três chamadas a `cadastrarUsuario` por chamadas a `adicionarUsuario`, criando os objetos diretamente: `biblioteca.adicionarUsuario(new Aluno("Ana"));`, e o mesmo para `Professor("Prof. Bruno")` e `Comunidade("Carlos")`.
3. Compile, rode o roteiro e compare. Resultado esperado: nenhuma diferença.
4. Agora estenda o sistema. Crie `src/Servidor.java`, seguindo o modelo de `Aluno.java`, com tipo `"servidor"`, limite de 5 empréstimos e multa de R$ 0,75 por dia.
5. Em `Main.seed`, depois de Carlos, adicione `biblioteca.adicionarUsuario(new Servidor("Dora"));`.
6. Confira com `git status` quais arquivos mudaram nos passos 4 e 5. `Biblioteca`, `Emprestimo` e `Menu` **não** podem aparecer.

**Verificação:**

- Compare as saídas. Resultado esperado: exatamente uma linha nova, `[ID 4] - Dora [servidor]`.
- Rode o sistema manualmente: opção 3, usuário 4, livro 2 (empréstimo realizado). Depois a opção 4, usuário 4, livro 2, 2 dias. Resultado esperado: `Devolucao realizada. Multa: R$ 1.5`.
- Atualize a referência: `Get-Content entrada.txt | java -cp out Main > saida-antes.txt`. A partir daqui, a linha de Dora faz parte da saída esperada.

**Commit:** `Ex3: Aberto/Fechado no cadastro de usuarios`. Troque o driver.