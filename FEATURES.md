# Xenia Android - New Features

## ✨ Funcionalidades Implementadas

### 📂 Sistema de Biblioteca de Jogos

#### Seleção de Pasta de Jogos
- **Seletor de Diretórios**: Interface moderna usando Storage Access Framework (SAF) do Android
- **Múltiplos Diretórios**: Suporte para adicionar múltiplas pastas de jogos
- **Permissões Persistentes**: Acesso mantido mesmo após reiniciar o app
- **Diretórios Padrão**: Busca automática em pastas comuns:
  - `/storage/emulated/0/Download/Xbox360`
  - `/storage/emulated/0/Games/Xbox360`
  - `/sdcard/Download/Xbox360`
  - `/sdcard/Games/Xbox360`

#### Scanner de Jogos
- **Formatos Suportados**:
  - `.iso` - Imagens ISO de discos Xbox 360
  - `.xex` - Executáveis Xbox 360
  - `.xbla` - Jogos Xbox Live Arcade
  - `.god` - Games on Demand
  
- **Scanner Recursivo**: Busca automática em subpastas
- **Detecção Automática**: Identifica automaticamente o tipo de arquivo
- **Informações Detalhadas**:
  - Nome do jogo (extraído do nome do arquivo)
  - Tipo de arquivo
  - Tamanho do arquivo formatado
  - Caminho completo

#### Interface da Biblioteca
- **Grid Responsivo**: Layout adaptativo que se ajusta ao tamanho da tela
- **Cards de Jogos Modernos**:
  - Design Material 3
  - Gradiente personalizado quando não há capa
  - Ícone de controle como placeholder
  - Informações sobrepostas com fundo semi-transparente
  - Tamanho do arquivo formatado (KB, MB, GB)
  - Tipo de arquivo em badge

- **Funcionalidades Interativas**:
  - Toque para ver detalhes do jogo
  - Diálogo de ações (Iniciar, Remover, Cancelar)
  - Botão de atualização para re-escanear
  - Botão FAB para adicionar nova pasta

#### Exibição de Capas
- **Sistema de Imagens**:
  - Integração com Coil 3 para carregamento eficiente
  - Suporte para URLs de capas
  - Cache automático de imagens
  - Loading assíncrono
  - Placeholder com gradiente enquanto carrega

- **Preparado para API de Capas**:
  - Estrutura pronta para integrar com IGDB API
  - Suporte para capas personalizadas
  - Fallback para imagem padrão

### 🎨 Interface do Usuário

#### Design Material 3
- **Tema Xenia Verde**: Cores personalizadas com identidade visual
- **Modo Escuro/Claro**: Suporte completo com cores dinâmicas
- **Animações Suaves**: Transições e efeitos modernos
- **Feedback Visual**: Estados de loading e placeholders informativos

#### Navegação
- **Bottom Navigation Bar**: Navegação entre Home, Configurações e Sobre
- **Top App Bar**: Barra superior com ações rápidas
- **FAB (Floating Action Button)**: Ação principal sempre visível

### 🔧 Arquitetura

#### Clean Architecture
```
app/
├── data/
│   ├── model/          # Game, GameFileType
│   └── repository/     # GameRepository
├── viewmodel/          # GamesViewModel
└── ui/
    ├── components/     # GameCard
    ├── screens/        # HomeScreen, Settings, About
    └── theme/          # Material 3 theming
```

#### Tecnologias
- **Kotlin**: Linguagem moderna e concisa
- **Jetpack Compose**: UI declarativa e reativa
- **Coroutines & Flow**: Programação assíncrona
- **ViewModel**: Gerenciamento de estado
- **Coil 3**: Carregamento de imagens
- **Material 3**: Design system moderno

### 🛠️ Correções Técnicas

#### Build System
- ✅ Corrigido `mmio_handler.h` faltando - copiado do Xenia oficial
- ✅ Adicionado `<iostream>` no `cvar.cc`
- ✅ Atualizado exceção do cxxopts: `OptionException` → `exceptions::exception`
- ✅ Adicionado `xenia/cpu/` ao CMakeLists.txt
- ✅ Configurado Coil 3 para imagens

#### Permissões
- ✅ `READ_EXTERNAL_STORAGE` (API ≤ 32)
- ✅ `WRITE_EXTERNAL_STORAGE` (API ≤ 29)
- ✅ `MANAGE_EXTERNAL_STORAGE` (API 30+)
- ✅ `INTERNET` (para baixar capas no futuro)

### 📱 Como Usar

#### Adicionar Jogos
1. Abra o app Xenia
2. Toque no botão **+** (FAB) ou no ícone de pasta
3. Selecione a pasta onde seus jogos estão
4. Os jogos serão automaticamente escaneados e exibidos

#### Iniciar um Jogo
1. Toque no card do jogo desejado
2. No diálogo, toque em "Launch"
3. O jogo será carregado no emulador

#### Remover da Biblioteca
1. Toque no card do jogo
2. No diálogo, toque em "Remove"
3. O jogo será removido da biblioteca (não será deletado do dispositivo)

#### Atualizar Biblioteca
- Toque no ícone de refresh (⟳) no canto superior direito
- A biblioteca será re-escaneada

### 🎮 Tipos de Arquivo Suportados

| Tipo | Extensão | Descrição |
|------|----------|-----------|
| ISO | `.iso` | Imagem de disco completa |
| XEX | `.xex` | Executável Xbox 360 |
| XBLA | `.xbla` | Xbox Live Arcade |
| GOD | `.god` | Games on Demand |

### 🔮 Próximos Passos

#### Funcionalidades Planejadas
- [ ] Integração com IGDB API para capas automáticas
- [ ] Busca de metadados dos jogos
- [ ] Categorização e filtros
- [ ] Favoritos e últimos jogados
- [ ] Estados salvos (save states)
- [ ] Histórico de tempo de jogo
- [ ] Suporte para cheats
- [ ] Screenshots e capturas de vídeo

#### Melhorias de UX
- [ ] Arrastar para atualizar (pull-to-refresh)
- [ ] Ordenação customizável (nome, data, tamanho)
- [ ] Visualização em lista ou grade
- [ ] Pesquisa de jogos
- [ ] Edição de informações manualmente

### 📊 Progresso do Projeto

**Concluído:**
- ✅ Sistema de build corrigido
- ✅ Scanner de jogos funcional
- ✅ Interface de biblioteca completa
- ✅ Sistema de capas preparado
- ✅ Arquitetura MVVM implementada

**Status:** 🟢 **FUNCIONAL** - Pronto para testes!

---

## 🤝 Contribuindo

Este é um projeto experimental de porte do Xenia para Android. Contribuições são bem-vindas!

## 📄 Licença

Este projeto segue a licença do Xenia original (BSD).

---

**Desenvolvido com ❤️ para a comunidade de emulação**
