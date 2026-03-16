#!/bin/bash

echo "========================================"
echo "  EaseChat 快速启动脚本"
echo "========================================"
echo ""

# 检查 MySQL 是否运行
echo "[1/4] 检查 MySQL 服务..."
if ! pgrep -x "mysqld" > /dev/null; then
    echo "[错误] MySQL 服务未运行！"
    echo "请先启动 MySQL 服务："
    echo "  sudo systemctl start mysql   # Linux"
    echo "  或"
    echo "  brew services start mysql    # macOS"
    echo ""
    exit 1
else
    echo "[成功] MySQL 服务正在运行"
fi
echo ""

# 检查数据库是否存在
echo "[2/4] 检查数据库..."
if ! mysql -u root -p123456 -e "USE easechat;" 2>/dev/null; then
    echo "[警告] 数据库 'easechat' 不存在或密码错误"
    echo "请手动执行以下命令创建数据库："
    echo "  mysql -u root -p < easechat/src/main/resources/db.sql"
    echo ""
    read -p "是否继续启动？(y/n): " confirm
    if [ "$confirm" != "y" ]; then
        exit 1
    fi
else
    echo "[成功] 数据库连接正常"
fi
echo ""

# 启动后端
echo "[3/4] 启动后端服务..."
cd easechat
echo "正在启动 Spring Boot 应用..."
echo "请等待，这可能需要几分钟..."
mvn spring-boot:run > ../backend.log 2>&1 &
BACKEND_PID=$!
echo "[成功] 后端启动中... (PID: $BACKEND_PID)"
cd ..
sleep 15
echo ""

# 启动前端
echo "[4/4] 启动前端开发服务器..."
cd frontend

# 检查依赖
if [ ! -d "node_modules" ]; then
    echo "[警告] 未找到 node_modules，正在安装依赖..."
    npm install
fi

echo "正在启动 Vite 开发服务器..."
npm run dev > ../frontend.log 2>&1 &
FRONTEND_PID=$!
echo "[成功] 前端启动中... (PID: $FRONTEND_PID)"
cd ..
echo ""

echo "========================================"
echo "  启动完成！"
echo "========================================"
echo ""
echo "后端地址：http://localhost:8080"
echo "前端地址：http://localhost:5173"
echo ""
echo "后端 PID: $BACKEND_PID"
echo "前端 PID: $FRONTEND_PID"
echo ""
echo "查看日志："
echo "  后端: tail -f backend.log"
echo "  前端: tail -f frontend.log"
echo ""
echo "停止服务："
echo "  kill $BACKEND_PID $FRONTEND_PID"
echo ""
