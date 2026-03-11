# 📱 SensorMonitor

Aplicativo Android para monitoramento de sensores em tempo real, desenvolvido como **Mini Projeto** da disciplina de Desenvolvimento Mobile.

---

## 📋 Sobre o Projeto

O **SensorMonitor** utiliza sensores reais do dispositivo Android (acelerômetro e sensor de luz) para capturar, exibir e armazenar dados sensoriais. O app segue a arquitetura **MVVM + Clean Architecture** com **Jetpack Compose** para UI, **Hilt** para injeção de dependência e persistência com **SQLite**.

---

## ✅ Requisitos Atendidos

| Requisito | Implementação |
|---|---|
| Sensor real | Acelerômetro (`TYPE_ACCELEROMETER`) e Sensor de Luz (`TYPE_LIGHT`) |
| Interface interativa | 4 telas Compose com interação do usuário |
| UI declarativa | Jetpack Compose com Material3 e tema dark personalizado |
| Navegação | Compose Navigation (`NavHost`) — Single-Activity |
| Exibição em tempo real | `SensorEventListener` com atualização contínua via `StateFlow` |
| Banco de dados SQLite | `SQLiteOpenHelper` com operações de inserção, consulta e exclusão |
| Arquitetura | MVVM + Clean Architecture com camadas `data → domain → presentation` |
| Injeção de dependência | Hilt (`@HiltViewModel`, `@Inject`, `@Module`) |

---

## 🖥️ Telas do Aplicativo

### 1. Tela Principal (`MainScreen`)
- Menu com 3 cards clicáveis para navegar entre funcionalidades
- Cards coloridos para Acelerômetro, Sensor de Luz e Histórico
- Hero card com status do sistema e badges

### 2. Acelerômetro (`AccelerometerScreen`)
- Exibição em tempo real dos eixos **X**, **Y** e **Z** (m/s²)
- Barras de progresso coloridas para cada eixo
- Cálculo da **magnitude** do vetor aceleração
- **Detecção de agitação (shake)** — vibra o dispositivo ao detectar movimento intenso
- Botão para **salvar leitura** no banco de dados
- Estado gerenciado via `AccelerometerViewModel` + `StateFlow`

### 3. Sensor de Luz (`LightSensorScreen`)
- Exibição do valor de luminosidade em **lux**
- **Cor de fundo dinâmica** — muda conforme o nível de luz ambiente:
  - 🌙 Muito Escuro → 🌑 Escuro → 🏠 Interno → ☁ Moderado → 🌤 Claro → ☀ Muito Claro → 🔆 Luz Solar
- Barra de progresso proporcional
- Botão para **salvar leitura** no banco de dados
- Estado gerenciado via `LightSensorViewModel` + `StateFlow`

### 4. Histórico (`HistoryScreen`)
- Lista todas as leituras salvas usando `LazyColumn`
- Indicador colorido por tipo de sensor
- Exibe valores e data/hora de cada registro
- Botão para **limpar todo o histórico**
- Estado gerenciado via `HistoryViewModel` + `StateFlow`

---

## 🏗️ Arquitetura — MVVM + Clean Architecture

```
com.example.miniprojeto/
├── SensorMonitorApp.kt              ← @HiltAndroidApp
├── data/                             ← CAMADA DE DADOS
│   ├── database/SensorDatabaseHelper.kt    (SQLite)
│   ├── model/SensorReading.kt              (Data class)
│   └── repository/SensorRepository.kt      (Implementação)
├── di/                               ← INJEÇÃO DE DEPENDÊNCIA
│   └── AppModule.kt                        (Hilt Module)
├── domain/                           ← CAMADA DE DOMÍNIO
│   ├── repository/SensorRepositoryInterface.kt  (Interface)
│   └── usecase/                             (Casos de Uso)
│       ├── SaveAccelerometerReadingUseCase.kt
│       ├── SaveLightReadingUseCase.kt
│       ├── GetAllReadingsUseCase.kt
│       └── ClearAllReadingsUseCase.kt
├── presentation/                     ← CAMADA DE APRESENTAÇÃO
│   ├── components/CommonComponents.kt      (Composables reutilizáveis)
│   ├── navigation/
│   │   ├── Screen.kt                       (Rotas)
│   │   └── AppNavGraph.kt                  (NavHost)
│   └── screen/
│       ├── main/MainScreen.kt              (Tela principal)
│       ├── accelerometer/
│       │   ├── AccelerometerScreen.kt       (Composable)
│       │   ├── AccelerometerViewModel.kt    (ViewModel + StateFlow)
│       │   └── AccelerometerUiState.kt      (Estado)
│       ├── light/
│       │   ├── LightSensorScreen.kt
│       │   ├── LightSensorViewModel.kt
│       │   └── LightSensorUiState.kt
│       └── history/
│           ├── HistoryScreen.kt
│           ├── HistoryViewModel.kt
│           └── HistoryUiState.kt
├── sensor/                           ← HANDLERS (mantidos)
│   ├── BaseSensorHandler.kt                (Interface)
│   ├── AccelerometerHandler.kt             (Processa dados)
│   ├── LightSensorHandler.kt              (Processa dados)
│   └── ShakeDetector.kt                   (Detecção de agitação)
├── ui/
│   ├── main/MainActivity.kt               (Single-Activity @AndroidEntryPoint)
│   └── theme/ (Color.kt, Theme.kt, Type.kt)
└── util/                             ← UTILITÁRIOS (mantidos)
    ├── Constants.kt
    ├── DateFormatter.kt
    ├── Extensions.kt
    └── VibrationHelper.kt
```

### Fluxo de Dados

```
[Sensor Hardware]
      ↓
[SensorEventListener] (DisposableEffect no Composable)
      ↓
[ViewModel] ← processa via Handler → atualiza StateFlow
      ↓
[UiState] ← observado pelo Composable via collectAsState()
      ↓
[Screen Composable] → renderiza a UI

[Salvar] → ViewModel → UseCase → Repository → SQLiteHelper
```

---

## 🔧 Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **SDK Mínimo:** API 26 (Android 8.0)
- **SDK Alvo:** API 36
- **UI:** Jetpack Compose + Material3
- **Arquitetura:** MVVM + Clean Architecture
- **Navegação:** Compose Navigation (`NavHost`)
- **Estado:** `StateFlow` + `UiState` data classes
- **DI:** Hilt (Dagger)
- **Banco de Dados:** SQLite (via `SQLiteOpenHelper`)
- **Sensores:** `SensorManager`, `SensorEventListener`
- **Vibração:** `Vibrator` + `VibrationEffect`
- **Build:** Gradle (Kotlin DSL) + AGP 9.x + KSP

---

## 🚀 Como Executar

1. Abra o projeto no **Android Studio**
2. Conecte um dispositivo físico (recomendado para sensores reais)
3. Execute o app com `Run > Run 'app'`
4. Ou gere o APK via terminal:
   ```bash
   ./gradlew assembleDebug
   ```
   O APK será gerado em `app/build/outputs/apk/debug/app-debug.apk`

---

## 📝 Logs

Todos os eventos são registrados no **Logcat** com a tag `SensorMonitor`:
- Inicialização e registro de sensores
- Leituras em tempo real
- Detecção de agitação
- Operações no banco de dados (inserção, consulta, limpeza)

Para filtrar no Logcat:
```
tag:SensorMonitor
```

---

## 👤 Autor

**Emanuel Borges** — 2026
