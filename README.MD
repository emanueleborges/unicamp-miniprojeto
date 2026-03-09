# 📱 SensorMonitor

Aplicativo Android para monitoramento de sensores em tempo real, desenvolvido como **Mini Projeto** da disciplina de Desenvolvimento Mobile.

---

## 📋 Sobre o Projeto

O **SensorMonitor** utiliza sensores reais do dispositivo Android (acelerômetro e sensor de luz) para capturar, exibir e armazenar dados sensoriais. O app conta com interface personalizada em XML, navegação entre telas via `Intent` e persistência de dados com **SQLite**.

---

## ✅ Requisitos Atendidos

| Requisito | Implementação |
|---|---|
| Sensor real | Acelerômetro (`TYPE_ACCELEROMETER`) e Sensor de Luz (`TYPE_LIGHT`) |
| Interface interativa | 4 Activities com interação do usuário |
| Layout XML personalizado | 5 layouts XML + drawable customizado |
| Navegação com Intent | `startActivity(Intent(...))` entre todas as telas |
| Exibição em tempo real | `SensorEventListener` com atualização contínua na UI |
| Banco de dados SQLite | `SQLiteOpenHelper` com operações de inserção, consulta e exclusão |

---

## 🖥️ Telas do Aplicativo

### 1. Tela Principal (`MainActivity`)
- Menu com 3 cards clicáveis para navegar entre funcionalidades
- Cards coloridos para Acelerômetro, Sensor de Luz e Histórico

### 2. Acelerômetro (`AccelerometerActivity`)
- Exibição em tempo real dos eixos **X**, **Y** e **Z** (m/s²)
- Barras de progresso coloridas para cada eixo
- Cálculo da **magnitude** do vetor aceleração
- **Detecção de agitação (shake)** — vibra o dispositivo ao detectar movimento intenso
- Botão para **salvar leitura** no banco de dados

### 3. Sensor de Luz (`LightSensorActivity`)
- Exibição do valor de luminosidade em **lux**
- **Cor de fundo dinâmica** — muda conforme o nível de luz ambiente:
  - 🌙 Muito Escuro → 🌑 Escuro → 🏠 Interno → ☁ Moderado → 🌤 Claro → ☀ Muito Claro → 🔆 Luz Solar
- Barra de progresso proporcional
- Botão para **salvar leitura** no banco de dados

### 4. Histórico (`HistoryActivity`)
- Lista todas as leituras salvas usando `RecyclerView`
- Indicador colorido por tipo de sensor
- Exibe valores e data/hora de cada registro
- Botão para **limpar todo o histórico**

---

## 🗂️ Estrutura dos Arquivos

```
app/src/main/
├── AndroidManifest.xml
├── java/com/example/miniprojeto/
│   ├── MainActivity.kt              # Tela principal com menu
│   ├── AccelerometerActivity.kt     # Sensor acelerômetro
│   ├── LightSensorActivity.kt      # Sensor de luz
│   ├── HistoryActivity.kt          # Histórico de leituras
│   ├── SensorReading.kt            # Modelo de dados
│   ├── SensorDatabaseHelper.kt     # Helper SQLite
│   └── SensorReadingAdapter.kt     # Adapter do RecyclerView
└── res/
    ├── layout/
    │   ├── activity_main.xml
    │   ├── activity_accelerometer.xml
    │   ├── activity_light.xml
    │   ├── activity_history.xml
    │   └── item_sensor_reading.xml
    ├── drawable/
    │   └── card_background.xml
    └── values/
        ├── colors.xml
        ├── strings.xml
        └── themes.xml
```

---

## 🔧 Tecnologias Utilizadas

- **Linguagem:** Kotlin
- **SDK Mínimo:** API 26 (Android 8.0)
- **SDK Alvo:** API 36
- **Banco de Dados:** SQLite (via `SQLiteOpenHelper`)
- **UI:** Layouts XML com `LinearLayout`, `ScrollView`, `RecyclerView`
- **Sensores:** `SensorManager`, `SensorEventListener`
- **Vibração:** `Vibrator` + `VibrationEffect`
- **Build:** Gradle (Kotlin DSL)

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
