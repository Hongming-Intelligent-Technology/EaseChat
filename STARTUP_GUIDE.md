# EaseChat 启动指南

## 📋 前置要求

### 必需软件
- **Java 21** - 运行后端
- **Node.js 20.19+** 或 **22.12+** - 运行前端
- **MySQL 8.x** - 数据库
- **Maven 3.6+** - 构建后端（通常随IDE自带）

## 🚀 快速启动步骤

### 第一步：配置数据库

1. **启动 MySQL 服务**

2. **创建数据库并导入表结构**
   ```bash
   # 登录 MySQL
   mysql -u root -p
   
   # 在 MySQL 命令行中执行
   source D:/Project/Easechat/easechat/src/main/resources/db.sql
   
   # 或者直接在命令行执行
   mysql -u root -p < D:/Project/Easechat/easechat/src/main/resources/db.sql
   ```

3. **修改数据库配置（如果需要）**
   
   编辑 `easechat/src/main/resources/application.properties`：
   ```properties
   spring.datasource.url=jdbc:mysql://localhost:3306/easechat?useSSL=false&serverTimezone=Asia/Shanghai&characterEncoding=utf-8
   spring.datasource.username=root
   spring.datasource.password=123456  # 修改为你的MySQL密码
   ```

### 第二步：启动后端

**方式一：使用 IDE（推荐）**
1. 用 IntelliJ IDEA 或 Eclipse 打开 `easechat` 文件夹
2. 等待 Maven 依赖下载完成
3. 找到 `EasechatApplication.java` 文件
4. 右键 → Run 'EasechatApplication'
5. 看到 "Started EasechatApplication" 表示启动成功

**方式二：使用命令行**
```bash
# 进入后端目录
cd D:/Project/Easechat/easechat

# 编译并运行
mvn spring-boot:run

# 或者先打包再运行
mvn clean package -DskipTests
java -jar target/easechat-0.0.1-SNAPSHOT.jar
```

**启动成功标志：**
- 控制台看到：`Tomcat started on port 8080`
- 浏览器访问：http://localhost:8080（应该返回 404 或 Whitelabel Error Page，这是正常的）

### 第三步：启动前端

1. **安装依赖**
   ```bash
   # 进入前端目录
   cd D:/Project/Easechat/frontend
   
   # 安装依赖（首次运行需要）
   npm install
   ```

2. **启动开发服务器**
   ```bash
   npm run dev
   ```

3. **打开浏览器**
   
   访问：http://localhost:5173

**启动成功标志：**
- 控制台显示：`Local: http://localhost:5173/`
- 浏览器自动打开并显示登录页面

## 🎯 使用系统

### 首次使用

1. **注册账号**
   - 访问 http://localhost:5173
   - 点击"注册"按钮
   - 填写账号和密码（必填）
   - 点击"注册"完成

2. **登录系统**
   - 输入刚才注册的账号密码
   - 点击"登录"

3. **功能入口**
   - **首页**：查看和发布帖子
   - **Chat 按钮**：进入聊天室
   - **New Post 按钮**：创建新帖子

### 主要功能

#### 📝 帖子功能
- 发布帖子（标题、作者、封面图）
- 浏览所有帖子
- 预览帖子详情

#### 💬 聊天功能
- 创建聊天室
- 加入聊天室
- 发送文字消息
- 发送图片
- 发送语音消息

#### 👤 个人中心
- 编辑个人信息
- 上传头像
- 修改密码
- 查看个人资料

## 🔧 常见问题

### 1. 后端启动失败

**问题：数据库连接失败**
```
java.sql.SQLException: Access denied for user 'root'@'localhost'
```
**解决：**
- 检查 MySQL 是否启动
- 检查 `application.properties` 中的用户名密码是否正确

**问题：端口被占用**
```
Port 8080 was already in use
```
**解决：**
- 方式一：修改 `application.properties` 中的 `server.port=8080` 改为其他端口
- 方式二：关闭占用 8080 端口的程序

### 2. 前端启动失败

**问题：依赖安装失败**
```
npm ERR! code ECONNREFUSED
```
**解决：**
```bash
# 使用国内镜像
npm config set registry https://registry.npmmirror.com
npm install
```

**问题：端口被占用**
```
Port 5173 is in use
```
**解决：**
- Vite 会自动尝试使用 5174, 5175 等端口
- 或手动指定端口：`npm run dev -- --port 3000`

### 3. 跨域问题

**问题：前端无法访问后端 API**
```
Access to XMLHttpRequest has been blocked by CORS policy
```
**解决：**
- 检查 `application.properties` 中的 `cors.allowed-origins` 是否包含前端地址
- 默认配置：`cors.allowed-origins=http://localhost:5173`
- 如果前端使用其他端口，需要修改此配置

### 4. 文件上传问题

**问题：上传的图片不显示**

**解决：**
- 确保 `uploads/` 文件夹已创建
- 检查 `application.properties` 中的配置：
  ```properties
  spring.web.resources.static-locations=classpath:/static/,file:uploads/
  ```

## 📂 项目结构

```
Easechat/
├── easechat/               # 后端 Spring Boot 项目
│   ├── src/main/java/
│   │   └── com/easechat/easechat/
│   │       ├── controller/   # 控制器
│   │       ├── entity/       # 实体类
│   │       ├── mapper/       # MyBatis Mapper
│   │       ├── config/       # 配置类
│   │       └── common/       # 通用工具
│   ├── src/main/resources/
│   │   ├── application.properties  # 配置文件
│   │   └── db.sql                  # 数据库脚本
│   └── pom.xml             # Maven 配置
│
└── frontend/               # 前端 Vue 3 项目
    ├── src/
    │   ├── views/         # 页面组件
    │   ├── components/    # 可复用组件
    │   ├── router/        # 路由配置
    │   └── utils/         # 工具函数
    └── package.json       # npm 配置
```

## 🔑 默认配置

| 项目 | 地址 | 说明 |
|------|------|------|
| 前端开发服务器 | http://localhost:5173 | Vue开发服务器 |
| 后端API服务器 | http://localhost:8080 | Spring Boot |
| 数据库 | localhost:3306 | MySQL |
| 数据库名 | easechat | 自动创建 |
| 文件上传目录 | uploads/ | 项目根目录下 |

## 🎨 核心API接口

### 用户相关
- `POST /user/register` - 注册
- `POST /user/login` - 登录
- `GET /user/loginOut` - 登出
- `GET /user/current` - 获取当前用户
- `POST /user/update` - 更新用户信息

### 博客相关
- `POST /blog/save` - 创建/更新博客
- `GET /blog/list` - 获取所有博客
- `GET /blog/my` - 获取我的博客
- `POST /blog/delete` - 删除博客

### 聊天室相关
- `POST /room/save` - 创建聊天室
- `GET /room/list` - 获取所有聊天室
- `GET /room/self/list` - 获取我的聊天室
- `GET /room/delete?id=xx` - 删除聊天室

### 消息相关
- `POST /comment/save` - 发送消息
- `GET /comment/list?roomId=xx` - 获取聊天记录

### 文件上传
- `POST /file/upload` - 上传文件（图片、音频）

## 🛡️ 安全特性

- ✅ BCrypt 密码加密
- ✅ Session 会话管理
- ✅ CORS 跨域配置
- ✅ 文件上传安全检查
- ✅ SQL 注入防护（MyBatis-Plus）
- ✅ XSS 防护（Vue 自动转义）

## 📝 开发建议

### 生产环境部署

1. **修改配置**
   ```properties
   # application.properties
   spring.datasource.password=强密码
   cors.allowed-origins=https://yourdomain.com
   ```

2. **打包前端**
   ```bash
   cd frontend
   npm run build
   # 将 dist/ 目录部署到静态服务器
   ```

3. **打包后端**
   ```bash
   cd easechat
   mvn clean package -DskipTests
   # 运行 target/easechat-0.0.1-SNAPSHOT.jar
   ```

## 💡 技术栈

**后端：**
- Spring Boot 3.4.2
- MyBatis-Plus 3.5.8
- MySQL 8.x
- Spring Security Crypto (BCrypt)

**前端：**
- Vue 3.5
- Vue Router 4.6
- Element Plus 2.13
- Axios 1.13
- Vite 7.3

---

✨ 开始使用 EaseChat，享受简洁的聊天体验！
