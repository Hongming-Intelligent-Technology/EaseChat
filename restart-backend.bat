@echo off
echo ========================================
echo   清理并重启 EaseChat 后端
echo ========================================
echo.

REM 查找占用8080端口的进程
echo [1/3] 检查端口8080...
for /f "tokens=5" %%a in ('netstat -ano ^| findstr :8080') do (
    set pid=%%a
    goto :found
)
echo [提示] 端口8080未被占用
goto :start

:found
echo [发现] 进程 %pid% 占用了端口8080
echo [操作] 正在关闭进程...
taskkill /F /PID %pid% >nul 2>&1
echo [成功] 进程已关闭
echo.

:start
echo [2/3] 清理编译缓存...
cd easechat
call mvn clean >nul 2>&1
echo [成功] 缓存已清理
echo.

echo [3/3] 启动后端服务...
echo.
echo ========================================
echo   后端正在启动...
echo   访问地址: http://localhost:8080
echo   按 Ctrl+C 可停止服务
echo ========================================
echo.
call mvn spring-boot:run
