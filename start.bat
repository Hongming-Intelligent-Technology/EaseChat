@echo off
echo ========================================
echo   EaseChat 快速启动脚本
echo ========================================
echo.

REM 检查 MySQL 是否运行
echo [1/4] 检查 MySQL 服务...
sc query mysql 2>nul | find "RUNNING" >nul
if errorlevel 1 (
    echo [错误] MySQL 服务未运行！
    echo 请先启动 MySQL 服务：
    echo   1. 打开"服务"管理器 ^(Win+R, 输入 services.msc^)
    echo   2. 找到 MySQL 服务并启动
    echo.
    pause
    exit /b 1
) else (
    echo [成功] MySQL 服务正在运行
)
echo.

REM 检查数据库是否存在
echo [2/4] 检查数据库...
mysql -u root -p123456 -e "USE easechat;" 2>nul
if errorlevel 1 (
    echo [警告] 数据库 'easechat' 不存在或密码错误
    echo 请手动执行以下命令创建数据库：
    echo   mysql -u root -p ^< easechat\src\main\resources\db.sql
    echo.
    set /p confirm="是否继续启动？(y/n): "
    if /i not "%confirm%"=="y" exit /b 1
) else (
    echo [成功] 数据库连接正常
)
echo.

REM 启动后端
echo [3/4] 启动后端服务...
cd easechat
echo 正在启动 Spring Boot 应用...
echo 请等待，这可能需要几分钟...
start "EaseChat Backend" cmd /k "mvn spring-boot:run"
cd ..
echo [成功] 后端启动中...
timeout /t 15 /nobreak >nul
echo.

REM 启动前端
echo [4/4] 启动前端开发服务器...
cd frontend

REM 检查依赖
if not exist "node_modules" (
    echo [警告] 未找到 node_modules，正在安装依赖...
    npm install
)

echo 正在启动 Vite 开发服务器...
start "EaseChat Frontend" cmd /k "npm run dev"
cd ..
echo [成功] 前端启动中...
echo.

echo ========================================
echo   启动完成！
echo ========================================
echo.
echo 后端地址：http://localhost:8080
echo 前端地址：http://localhost:5173
echo.
echo 请等待约 30 秒后访问前端地址
echo 按任意键关闭此窗口...
pause >nul
