# BomberBot

BomberBot é um remake do clássico jogo Bomberman, desenvolvido em Java. O projeto recria a experiência do original com jogabilidade dinâmica e desafiadora.

---

## ✨ Diferenciais

- 🎨 *Pixel art original:* Todos os elementos gráficos foram criados exclusivamente pelos desenvolvedores.
- 🔊 *Efeitos sonoros próprios:* Os efeitos de áudio do jogo são autoria do grupo.

---

## 🚀 Principais Funcionalidades

- Jogador e bots com diferentes níveis de dificuldade (fácil, médio, difícil)
- Sistema de colisão e fases progressivas
- Gerador de mapas, ranking e gerenciamento de score
- Interface gráfica e manipulação de sons
- Objetos interativos (bombas, portas, vidas)

---

## 📁 Estrutura de Pastas/Principais Pacotes

```bash
src/
├── entidade/            # Jogador, Personagem, e classes relacionadas
├── fase/                # Lógica e configuração das fases
├── inimigo/             # Inteligências dos bots (BotFacil, BotMedio, BotDificil)
├── objeto/              # Objetos interativos do jogo (bombas, portas, vidas, etc.)
├── principal/           # Main, GamePanel, UI, utilitários e entrada principal
├── recursos/            # Gerador de mapa, ranking, gerenciador de ranking
├── tile/                # Tiles base do mapa
├── tile_Interativo/     # Blocos interativos e variantes
```

---

## 🛠️ Tecnologias Utilizadas

- *Java* (versão recomendada: 8 ou superior)
- *Eclipse IDE*
- *AWT/Swing* para interface gráfica
- Ferramentas de edição de pixel art e áudio (todas as artes e sons são autorais)

---

## 💻 Ambiente de Desenvolvimento

- Desenvolvido no Eclipse IDE
- Sistema operacional: Windows 10

---

## 🏁 Como executar

```bash
git clone https://github.com/LittleNeto/BomberBot.git
```
1. Importe o projeto no Eclipse IDE.
2. Compile e execute o arquivo principal conforme indicado na documentação do projeto.

---

## 📋 Requisitos

- Java (versão recomendada: 8 ou superior)
- Eclipse IDE ou compatível

---

## 🕹️ Controles do Jogo

| Tecla     | Ação                              |
|-----------|-----------------------------------|
| *W*     | Mover para cima                   |
| *A*     | Mover para a esquerda             |
| *S*     | Mover para baixo                  |
| *D*     | Mover para a direita              |
| *X*     | Colocar bomba                     |
| *P*     | Pausar/despausar o jogo           |
| *Enter* | Confirmar no menu e no ranking    |
| *Z*     | Retornar do ranking ao menu       |

---

## 👥 Colaboradores

- [José Araújo Agra Neto](https://github.com/LittleNeto)
- [Júlio Pedro da Silva](https://github.com/julioP-dev)
- [Mateus Érik Nóbrega de Araújo](https://github.com/mateuserikNA)

---

## 📝 Notas

Projeto feito como trabalho final das disciplinas Linguagem de Programação 2 (lp2) e Laboratório de Programação 2 (labp2), ministradas pelo professor Janderson Aguiar no período de 2025.1
