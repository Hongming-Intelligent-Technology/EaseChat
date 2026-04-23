# EaseChat

![License](https://img.shields.io/badge/license-MIT-green)
![Java](https://img.shields.io/badge/Java-21-orange)
![Vue](https://img.shields.io/badge/Vue-3-brightgreen)
![Spring Boot](https://img.shields.io/badge/Spring%20Boot-3.4.2-brightgreen)

[English](README_EN.md) | [中文](README.md)

---

## 📖 快速开始

**🚀 一键启动（Windows）：**
```bash
# 双击运行
start.bat

# 或命令行运行
.\start.bat
```

**🚀 一键启动（Linux/Mac）：**
```bash
chmod +x start.sh
./start.sh
```

**📚 详细文档：** 查看 [完整启动指南](STARTUP_GUIDE.md)

---

## EaseChat - 实时聊天与博客应用

一个现代化的全栈应用，支持实时聊天、博客发布、用户管理等功能。

## 📋 项目特性

### 💬 聊天功能
- ✨ 创建和加入聊天室
- 📤 支持文本、图片和语音消息
- 👥 实时消息推送
- 🎯 聊天室管理

### 📝 博客功能  
- ✍️ 发布图文博客
- 🖼️ 封面图片上传
- 📱 响应式卡片布局
- 👁️ 帖子预览

### 👤 用户系统
- 🔐 安全的注册登录（BCrypt加密）
- 🎨 个人资料编辑
- 📷 头像上传
- 🔒 会话管理

## 🛠 技术栈

### 前端
- **框架**: Vue 3.5 (Composition API)
- **构建工具**: Vite 7.3
- **UI 组件库**: Element Plus 2.13
- **HTTP 客户端**: Axios 1.13
- **路由**: Vue Router 4.6
- **设计**: 统一设计系统（CSS Variables）
- **开发端口**: 5173

### 后端
- **框架**: Spring Boot 3.4.2
- **Java 版本**: 21
- **数据库**: MySQL 8.x
- **ORM**: MyBatis-Plus 3.5.8
- **安全**: Spring Security Crypto (BCrypt)
- **会话**: Session-based Authentication
- **服务端口**: 8081

## 📦 环境要求

### 前置条件

**后端要求:**
- Java 21 或更高版本
- MySQL 8.x
- Maven 3.6+

**前端要求:**
- Node.js 20.19.0 或更高版本（或 >= 22.12.0）
- npm 或 yarn

## 🚀 快速开始

### 1. 数据库配置

```sql
-- 创建数据库
CREATE DATABASE easechat CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

-- 导入数据库架构
SOURCE easechat/src/main/resources/db.sql;
```

### 2. 后端设置

```bash
# 进入后端目录
cd easechat

# 更新数据库配置
# 编辑 src/main/resources/application.properties
# 修改: spring.datasource.username, spring.datasource.password, spring.datasource.url

# 构建项目
mvn clean package

# 运行后端服务
mvn spring-boot:run
```

后端服务将在 `http://localhost:8081` 启动

### 3. 前端设置

```bash
# 进入前端目录
cd frontend

# 安装依赖
npm install

# 启动开发服务器
npm run dev
```

前端应用将在 `http://localhost:5173` 访问

### 4. 访问应用

打开浏览器访问 `http://localhost:5173` 即可使用应用

## 📁 项目结构

```
Easechat/
├── easechat/                    # 后端（Spring Boot）
│   ├── src/
│   │   ├── main/
│   │   │   ├── java/           # Java 源代码
│   │   │   └── resources/       # 配置文件和数据库脚本
│   │   └── test/                # 测试代码
│   ├── pom.xml                 # Maven 配置文件
│   └── mvnw                    # Maven 包装器
│
├── frontend/                    # 前端（Vue 3）
│   ├── src/
│   │   ├── components/         # Vue 组件
│   │   ├── views/              # 页面视图
│   │   ├── router/             # 路由配置
│   │   ├── utils/              # 工具函数
│   │   ├── assets/             # 静态资源
│   │   └── main.js             # 入口文件
│   ├── package.json            # npm 配置文件
│   └── vite.config.js          # Vite 配置文件
│
├── README.md                    # 项目说明（中文）
├── README_EN.md                 # Project README (English)
├── SETUP.md                     # 详细设置指南
└── HELP.md                      # 帮助文档
```

## 🔧 主要模块

### 后端模块
- **Controller**: 处理 HTTP 请求的控制器
- **Entity**: 数据库实体模型
- **Mapper**: MyBatis 数据访问层
- **Config**: 应用配置（CORS、数据库等）
- **Common**: 通用工具和异常处理

### 前端模块
- **Home**: 主页
- **Login**: 登录页面
- **Register**: 注册页面
- **路由**: 应用路由配置

## 📝 API 端点

### 用户相关
- `POST /api/user/register` - 用户注册
- `POST /api/user/login` - 用户登录
- `GET /api/user/current` - 获取当前用户信息
- `POST /api/user/update` - 更新用户资料

### 聊天室相关
- `GET /api/room/list` - 获取聊天室列表
- `GET /api/room/self/list` - 获取我创建的聊天室
- `POST /api/room/save` - 创建新聊天室
- `POST /api/room/delete` - 删除聊天室

### 博客相关
- `GET /api/blog/list` - 获取博客列表
- `POST /api/blog/save` - 创建博客
- `POST /api/blog/delete` - 删除博客

## 🐛 故障排除

### 后端无法启动
- 检查 Java 版本是否为 21+
- 确认 MySQL 数据库已启动
- 验证 application.properties 中的数据库配置

### 前端无法连接后端
- 检查 request.js 中的后端 API 地址配置
- 确保后端服务器正在运行
- 检查浏览器控制台的 CORS 错误

### 数据库连接失败
- 验证 MySQL 用户名和密码
- 确认数据库 easechat 已创建
- 检查 db.sql 脚本是否已执行

更多帮助，请查看 [SETUP.md](SETUP.md) 和 [HELP.md](easechat/HELP.md)

## 📄 许可证

MIT License - 详见 LICENSE 文件

## 👨‍💻 开发者

开发和维护者信息

---

**最后更新**: 2026 年 2 月

**最后更新**: 2026 年 2 月
