# 智能聊天助手 - ChatApp

基于 MVI 架构 + Jetpack Compose 开发的 Android 智能聊天应用，通过硅基流动 API 调用大语言模型，支持聊天记录的持久化存储与管理。

## 📱 项目简介

本项目是一款完全运行在 Android 端的智能聊天助手。用户可以与 AI 进行自然对话，所有聊天记录都会自动保存到本地数据库，支持历史记录查看、单条删除、一键清空等功能。AI 响应通过调用**硅基流动 API**（默认使用 Qwen3.5-4B 模型）实现，支持灵活更换模型。

## ✨ 功能特性

- 🤖 **智能对话**：用户发送消息，AI 自动回复（基于硅基流动大模型）
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
| AI 模型 API | 硅基流动 (SiliconFlow) + Qwen3.5-4B（可配置） |
| 最低 SDK | API 24 (Android 7.0) |
| 编译 SDK | API 34 |

## 🚀 快速开始

### 1. 前置条件

- Android Studio Hedgehog | 2023.1.1 或更高版本
- JDK 17
- Android SDK API 34
- 一个有效的 [硅基流动](https://cloud.siliconflow.cn/) 账户及 API Key（以 `sk-` 开头）

### 2. 克隆项目

```bash
git clone https://github.com/21000-lwy/ChatApp.git
```

### 3. 配置 API 信息

编辑项目根目录下的 `local.properties` 文件（如果没有则新建），添加以下内容：

```properties

SILICONFLOW_BASE_URL=https://api.siliconflow.cn/v1/
SILICONFLOW_API_KEY=sk-你的真实API密钥
SILICONFLOW_MODEL=deepseek-ai/DeepSeek-V3
```

> 你可以在 [硅基流动模型广场](https://cloud.siliconflow.cn/me/models) 选择其他模型，替换 `SILICONFLOW_MODEL` 的值。

### 4. 同步 Gradle 并运行

- 在 Android Studio 中点击 **File → Sync Project with Gradle Files**
    
- 启动模拟器
    
- 点击运行按钮（▶️）


## 📁 项目结构

```text

ChatApp/
├── chatapp/                     # 主模块
│   ├── src/main/java/.../
│   │   ├── model/               # 数据模型（ChatMessage, ChatState, ChatIntent）
│   │   ├── repository/          # Room DAO 和数据库
│   │   ├── viewmodel/           # ViewModel + MVI 逻辑
│   │   ├── webservice/          # Retrofit 网络服务（硅基流动 API）
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

## 📄 开源协议

本项目仅供学习交流使用，无特殊开源协议声明。
