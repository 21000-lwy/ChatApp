# 智能聊天助手 - ChatApp

基于 MVI 架构 + Jetpack Compose 开发的 Android 智能聊天应用，集成本地 Ollama 大模型（gemma3:1b），支持聊天记录的持久化存储与管理。

## 📱 项目简介

本项目是一款完全运行在 Android 端的智能聊天助手。用户可以与 AI 进行自然对话，所有聊天记录都会自动保存到本地数据库，支持历史记录查看、单条删除、一键清空等功能。AI 响应通过调用本地部署的 Ollama 服务（gemma3:1b 模型）实现，无需云端 API，保护用户隐私。

## ✨ 功能特性

- 🤖 **智能对话**：用户发送消息，AI 自动回复（基于 gemma3:1b 本地模型）
- 💾 **聊天记录持久化**：所有消息自动保存到 Room 数据库，支持应用重启后加载
- 📜 **历史记录管理**：查看所有历史消息，支持长按删除单条消息
- 🗑️ **一键清空**：清空全部聊天记录（带确认对话框）
- 🎨 **现代化 UI**：Material 3 设计，支持深色/浅色主题（跟随系统）
- 🔄 **流畅交互**：消息自动滚动到底部，发送时加载动画，网络错误友好提示
- 📱 **多屏幕适配**：聊天、历史、关于三个页面，支持底部导航栏和侧边栏抽屉菜单

## 🛠️ 技术栈

| 分类 | 技术 |
|------|------|
| 语言 | Kotlin |
| UI 框架 | Jetpack Compose + Material 3 |
| 架构模式 | MVI (Model-View-Intent) |
| 状态管理 | StateFlow + Sealed Class |
| 异步处理 | Coroutines + RxJava3（网络请求） |
| 本地存储 | Room |
| 网络请求 | Retrofit + Gson + RxJava3CallAdapterFactory |
| 依赖注入 | 手动构造（无 DI 框架） |
| AI 模型 | Ollama + gemma3:1b（本地部署） |
| 最低 SDK | API 24 (Android 7.0) |
| 编译 SDK | API 34 |

## 🚀 快速开始

### 1. 前置条件

- Android Studio Hedgehog | 2023.1.1 或更高版本
- JDK 17
- Android SDK API 34
- 一台已安装 [Ollama](https://ollama.com/) 并运行 `gemma3:1b` 模型的本地或局域网机器

### 2. 克隆项目

```bash
git clone https://github.com/21000-lwy/ChatApp.git
```
### 3. 配置 Ollama 服务地址

打开 `chatapp/src/main/java/cn/edu/ncu/lwy/chatapp/webservice/ServiceCreator.kt`，修改 `BASE_URL` 为你的 Ollama 服务地址：

```kotlin
private const val BASEURL = "http://你的Ollama服务器IP:11434/"
```

> 如果 Ollama 运行在 Android 模拟器所在的电脑上，可以使用 `http://10.0.2.2:11434/`（模拟器专用地址）。  
> 如果运行在真机上，需要确保真机与服务器在同一局域网，并使用服务器的实际 IP。

### 4. 启动 Ollama 模型

在 Ollama 服务器上执行：

```bash
ollama pull gemma3:1b   # 如果尚未下载
ollama serve            # 确保服务已启动
```

### 5. 在 Android Studio 中运行

- 打开项目，等待 Gradle 同步完成
    
- 连接 Android 设备或启动模拟器
    
- 点击运行按钮（▶️）
    

## 📁 项目结构

```text
ChatApp/
├── chatapp/                     # 主模块
│   ├── src/main/java/.../
│   │   ├── model/               # 数据模型（ChatMessage, ChatState, ChatIntent）
│   │   ├── repository/          # Room DAO 和数据库
│   │   ├── viewmodel/           # ViewModel + MVI 逻辑
│   │   ├── webservice/          # Retrofit 网络服务
│   │   ├── ui/
│   │   │   ├── screen/          # Compose 页面（Chat, History, About, Main）
│   │   │   ├── component/       # 可复用组件（消息项、底部栏、抽屉等）
│   │   │   └── theme/           # 主题颜色、字体
│   │   └── MainActivity.kt
│   └── build.gradle.kts
├── gradle/                      # Gradle Wrapper
├── build.gradle.kts             # 项目级构建脚本
├── settings.gradle.kts
└── README.md
```
