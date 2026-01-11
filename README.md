Memory Helper 🧠

Um jogo de memória para Android desenvolvido com Jetpack Compose

Projetado para exercitar a memória através de diferentes categorias e níveis de dificuldade.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
✨ Características

    Três categorias de ícones: 👶 Crianças, 👨‍💼 Adultos, 👵 Idosos

    Três níveis de dificuldade: Fácil (6 cartas), Médio (14 cartas), Difícil (20 cartas)

    Sistema de pontuação: Baseado em movimentos e dificuldade

    Interface moderna: Desenvolvida com Material Design 3 e Jetpack Compose

    Totalmente offline: Jogue sem necessidade de conexão com internet
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
📸 Screenshots

https://github.com/SilencioPz/MemoryHelper/issues/1#issue-3799353350

https://github.com/SilencioPz/MemoryHelper/issues/2#issue-3799354078

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
🚀 Como Rodar o Projeto

Pré-requisitos

    Android Studio (versão mais recente recomendada)

    JDK 11 ou superior

    Android SDK (API 24+)

Para Windows

    Clone o repositório:
    bash

git clone https://github.com/seu-usuario/MemoryHelper.git
cd MemoryHelper

    Abra no Android Studio:

        Inicie o Android Studio

        Selecione "Open" e navegue até a pasta do projeto

        Aguarde a sincronização do Gradle

    Execute o app:

        Conecte um dispositivo Android ou configure um emulador

        Clique no botão ▶️ "Run" ou pressione Shift + F10
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Para Linux

    Clone o repositório:
    bash

git clone https://github.com/seu-usuario/MemoryHelper.git
cd MemoryHelper

Dê permissão de execução:
bash

chmod +x gradlew

Abra no Android Studio ou build via terminal:
bash

# Para sincronizar dependências
./gradlew sync

# Para buildar o projeto
./gradlew build

# Para instalar no dispositivo conectado
./gradlew installDebug

Executar via Android Studio:
bash

studio.sh .  # Ou abra via interface gráfica
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
📁 Estrutura do Projeto

MemoryHelper/

├── app/

│   ├── src/main/java/com/example/memoryhelper/

│   │   ├── screens/          # Telas do jogo

│   │   │   ├── MenuScreen.kt

│   │   │   ├── DifficultyScreen.kt

│   │   │   ├── GameScreen.kt

│   │   ├── ui/               # Componentes UI

│   │   │   ├── MemoryApp.kt

│   │   ├── model/            # Modelos de dados

│   │   │   ├── MemoryCard.kt

│   │   │   ├── GameConfig.kt

│   │   │   ├── Difficulty.kt

│   │   ├── game/             # Lógica do jogo

│   │   │   └── GameManager.kt

│   ├── src/main/res/         # Recursos (imagens, cores, strings)

│   └── build.gradle.kts      # Configuração do módulo app

├── gradle/

│   └── wrapper/              # Gradle Wrapper

├── build.gradle.kts          # Configuração do projeto

├── settings.gradle.kts       # Configuração de módulos

└── gradle.properties         # Propriedades do Gradle

----------------------------------------------------------------------------------------------------------------------------------------------------------------------
🛠️ Dependências (Gradle)

O projeto utiliza as seguintes principais dependências:
kotlin

// Core Android
implementation(libs.androidx.core.ktx)
implementation(libs.androidx.lifecycle.runtime.ktx)
implementation(libs.androidx.activity.compose)

// Jetpack Compose
implementation(platform(libs.androidx.compose.bom))
implementation(libs.androidx.compose.ui)
implementation(libs.androidx.compose.ui.graphics)
implementation(libs.androidx.compose.ui.tooling.preview)
implementation(libs.androidx.compose.material3)

// Coroutines
implementation("org.jetbrains.kotlinx:kotlinx-coroutines-android:1.7.3")

// Utilitários
implementation("androidx.browser:browser:1.5.0")  // Para abrir links
implementation("androidx.compose.ui:ui-text:1.5.0")  // Suporte a texto
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
Versões mínimas:

    Min SDK: 24 (Android 7.0)

    Compile SDK: 36 (Android 14)

    Kotlin: 1.9+

    Jetpack Compose: BOM 2024.01.00
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
🎮 Como Jogar

    Inicie o jogo e escolha uma categoria (Crianças, Adultos ou Idosos)

    Selecione a dificuldade (Fácil, Médio ou Difícil)

    Memorize as cartas que aparecem viradas

    Encontre os pares de cartas com o mesmo ícone

    Complete o jogo com o menor número de movimentos possível

    Tente bater seu recorde de pontuação!
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
📊 Sistema de Pontuação

    Base: 1000 pontos

    Penalidade por movimento: -10 pontos por movimento

    Multiplicador de dificuldade:

        Fácil: ×1

        Médio: ×2

        Difícil: ×3
Fórmula: Pontuação = max(0, (1000 - (movimentos × 10)) × multiplicador)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
🔧 Build e Desenvolvimento

Comandos úteis do Gradle:

# Limpar build
./gradlew clean

# Buildar APK de debug
./gradlew assembleDebug

# Buildar APK de release
./gradlew assembleRelease

# Executar testes
./gradlew test

# Verificar dependências
./gradlew dependencies

Configuração para desenvolvedores:

    Importar no Android Studio: O projeto utiliza Gradle Kotlin DSL

    Sincronização automática: Habilite no Android Studio

    Emulador recomendado: API 30+ com Google Play Services
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
📄 Licença

Este projeto está licenciado sob a GNU General Public License v3.0.

Memory Helper - Um jogo para exercitar a memória
Copyright (C) 2024 SilencioPZ

Este programa é software livre: você pode redistribuí-lo e/ou modificar
sob os termos da GNU General Public License conforme publicada pela
Free Software Foundation, seja a versão 3 da Licença, ou
(a seu critério) qualquer versão posterior.

Este programa é distribuído na esperança de que seja útil,
mas SEM QUALQUER GARANTIA; sem mesmo a garantia implícita de
COMERCIALIZAÇÃO ou ADEQUAÇÃO A UM DETERMINADO PROPÓSITO. Veja a
GNU General Public License para mais detalhes.

Você deve ter recebido uma cópia da GNU General Public License
junto com este programa. Se não, veja <https://www.gnu.org/licenses/>.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
👤 Autor

SilencioPZ
🌐 [🌐 silenciopz.neocities.org](http://silenciopz.neocities.org)
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
✅ Créditos

Músicas gratuitas: <https://musopen.org/>.

Músicas utilizadas: Beethoven - Adieu au Piano, Beethoven - Symphony Number 9 in D minor, Chopin - Waltz in A minor e Tchaikovsky - The Nutcracker Suite Act 1.

Ícones: <https://www.flaticon.com/>

Todos os ícones utilizados no jogo foram baixados neste site gratuito.

IA Utilizada: <https://chat.deepseek.com/>

Deepseek me ajudou com o código bruto, polindo e refinando conforme testes realizados.
----------------------------------------------------------------------------------------------------------------------------------------------------------------------
🙏 Agradecimentos

    Ícones de FlatIcon (com adaptações)

    Comunidade Android e Jetpack Compose

    Todos os testadores e colaboradores

Divirta-se exercitando sua memória! 🧠✨

"A memória é o diário que todos carregamos conosco." - Oscar Wilde
