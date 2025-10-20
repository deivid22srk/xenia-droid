# Changelog - Xenia Android Port

## [1.0.1-alpha] - 2025-10-20

### ✨ Novas Funcionalidades

#### 🎮 Sistema de Biblioteca de Jogos
- **Seletor de Pasta de Jogos**: Implementado sistema completo de seleção de diretórios usando Storage Access Framework (SAF)
- **Scanner Automático de Jogos**: Busca recursiva em pastas por arquivos de jogos Xbox 360
- **Formatos Suportados**:
  - ISO (`.iso`) - Imagens de disco
  - XEX (`.xex`) - Executáveis Xbox 360
  - XBLA (`.xbla`) - Xbox Live Arcade
  - GOD (`.god`) - Games on Demand

#### 🖼️ Interface de Biblioteca
- **Grid Responsivo**: Layout adaptativo de cards de jogos
- **Cards de Jogos**: Design Material 3 com informações detalhadas
  - Gradiente de fundo quando não há capa
  - Ícone de controle como placeholder
  - Nome do jogo, tipo e tamanho do arquivo
  - Overlay com gradiente para melhor legibilidade

- **Exibição de Capas**: Sistema preparado para exibir capas dos jogos
  - Integração com Coil 3 para carregamento de imagens
  - Cache automático
  - Fallback para placeholder

#### 🎨 Melhorias de UI
- **Diálogo de Detalhes do Jogo**: 
  - Informações completas do jogo
  - Opção de iniciar emulação
  - Opção de remover da biblioteca
  
- **Tela Vazia Melhorada**:
  - Ícone grande e amigável
  - Instruções claras
  - Botão de ação direto

- **Loading States**: Indicadores de carregamento durante scan

### 🔧 Correções Técnicas

#### Build System
- ✅ **Corrigido erro de compilação**: Arquivo `xenia/cpu/mmio_handler.h` faltando
  - Copiado do repositório oficial do Xenia
  - Adicionado ao CMakeLists.txt
  
- ✅ **Corrigido erro no cvar.cc**: 
  - Adicionado include `<iostream>` para std::cout
  - Atualizado para usar `cxxopts::exceptions::exception` ao invés de `cxxopts::OptionException`

- ✅ **CMake atualizado**:
  - Adicionado suporte para módulo CPU
  - Configurado compilação de mmio_handler.cc

#### Dependências
- ✅ Adicionado **Coil 3** para carregamento de imagens:
  - `coil-compose:3.0.0`
  - `coil-network-okhttp:3.0.0`

### 📁 Nova Estrutura de Arquivos

```
app/src/main/java/com/xenia/emulator/
├── data/
│   ├── model/
│   │   └── Game.kt              # Modelo de dados de jogo
│   └── repository/
│       └── GameRepository.kt     # Repositório de jogos
├── viewmodel/
│   └── GamesViewModel.kt         # ViewModel da biblioteca
└── ui/
    ├── components/
    │   └── GameCard.kt           # Componente de card de jogo
    └── screens/
        └── HomeScreenNew.kt      # Nova tela principal
```

### 🎯 Arquitetura

#### MVVM Pattern
- **Model**: `Game`, `GameFileType`
- **Repository**: `GameRepository` para acesso a dados
- **ViewModel**: `GamesViewModel` com StateFlow
- **View**: Jetpack Compose com Material 3

#### Features Implementadas
- Scan de diretórios com SAF
- Scan recursivo de subpastas
- Detecção automática de tipo de arquivo
- Gerenciamento de estado com Flow
- Carregamento assíncrono com Coroutines

### 📱 Permissões

Permissões configuradas no AndroidManifest:
- `READ_EXTERNAL_STORAGE` (API ≤ 32)
- `WRITE_EXTERNAL_STORAGE` (API ≤ 29)
- `MANAGE_EXTERNAL_STORAGE` (API 30+)
- `INTERNET` (para futuras funcionalidades online)
- `WAKE_LOCK` (manter tela ligada durante emulação)

### 🚀 Como Usar

1. **Abrir App**: Inicie o Xenia
2. **Adicionar Pasta**: Toque no botão + ou ícone de pasta
3. **Selecionar Diretório**: Escolha a pasta com seus jogos
4. **Visualizar Jogos**: Os jogos aparecerão automaticamente em grid
5. **Iniciar Jogo**: Toque no card e selecione "Launch"

### 🔜 Próximos Passos

#### Planejado para v1.0.2
- [ ] Integração com IGDB API para capas automáticas
- [ ] Download de metadados dos jogos
- [ ] Sistema de favoritos
- [ ] Histórico de últimos jogados
- [ ] Filtros e ordenação

#### Planejado para v1.1.0
- [ ] Save states (estados salvos)
- [ ] Suporte para cheats
- [ ] Screenshots
- [ ] Controles on-screen

### 📊 Estatísticas do Projeto

- **Arquivos Kotlin**: 15
- **Linhas de Código**: ~1200 (Kotlin) + ~2500 (C++)
- **Tamanho do Projeto**: ~3.2 MB (compactado)

### 🐛 Problemas Conhecidos

- [ ] Core de emulação ainda não totalmente integrado
- [ ] Renderização Vulkan em progresso
- [ ] Audio system pendente de implementação
- [ ] Performance não otimizada

### 🙏 Agradecimentos

- **Xenia Project** pelo emulador original
- **Material Design 3** pela interface moderna
- **Coil** pela biblioteca de imagens eficiente
- **Jetpack Compose** pelo framework de UI declarativa

---

**Status:** 🟢 Build funcional - Pronto para testes!

**Versão:** 1.0.1-alpha  
**Data:** 20 de Outubro de 2025  
**Autor:** Desenvolvido para continuação do porte Android
