<template>
  <div class="login-wrapper">
    <div class="login-content">
      <div class="title">EaseChat - Login</div>
      <div style="width: 80%; margin: 0 auto;">
        <el-input placeholder="账号" v-model="account">
          <template #prefix>
            <el-icon>
              <User />
            </el-icon>
          </template>
        </el-input>
      </div>
      <div style="width: 80%; margin: 0 auto; margin-top: 20px;">
        <el-input placeholder="密码" type="password" v-model="password">
          <template #prefix>
            <el-icon>
              <Lock />
            </el-icon>
          </template>
        </el-input>
      </div>

      <div style="width: 80%; margin: 0 auto; margin-top: 20px;">
        <el-button @click="handlerLogin" style="width: 100%; font-size: 20px;" type="primary">登录</el-button>
      </div>

      <div style="width: 80%; margin: 0 auto; margin-top: 20px;">
        <el-button @click="handlerRegister" style="width: 100%; font-size: 20px;">去注册</el-button>
      </div>

      <div class="line-left-top top-line"></div>
      <div class="line-left-top left-line"></div>
      <div class="line-right-top top-line"></div>
      <div class="line-right-top right-line"></div>
      <div class="line-left-bottom top-line"></div>
      <div class="line-left-bottom left-line"></div>
      <div class="line-right-bottom top-line"></div>
      <div class="line-right-bottom right-line"></div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const account = ref("")
const password = ref("")

const handlerRegister = () => {
  router.push('/register')
}

const handlerLogin = () => {
  if (!account.value) {
    ElMessage.warning("请输入账号")
    return
  }
  if (!password.value) {
    ElMessage.warning("请输入密码")
    return
  }

  const params = { account: account.value, password: password.value }

  request.post("/user/login", params).then((res) => {
    const data = res.data
    if (data.code !== 200) {
      ElMessage.warning(data.msg)
      return
    }
    ElMessage.success(data.msg)
    // 简单处理，通常需要保存 token
    setTimeout(() => {
      router.push('/')
    }, 800)
  })
}
</script>

<style scoped>
.login-wrapper {
  display: flex;
  justify-content: end;
  background-image: url("@/assets/img/img.png");
  background-size: 100% 100%;
  height: 100vh;
  align-items: center;
}

.login-content {
  width: 400px;
  height: 420px;
  background: rgba(0, 0, 0, 0.3);
  margin-right: 300px;
  position: relative;
}

.title {
  height: 100px;
  line-height: 100px;
  text-align: center;
  color: white;
  font-size: 30px;
  font-family: 楷体;
}

/* 线条样式 */
.top-line {
  background: #409eff;
  width: 30px;
  height: 3px;
}

.left-line {
  background: #409eff;
  width: 3px;
  height: 30px;
}

.right-line {
  background: #409eff;
  width: 3px;
  height: 30px;
}

.line-left-top {
  position: absolute;
  top: 0;
  left: 0;
}

.line-right-top {
  position: absolute;
  top: 0;
  right: 0;
}

.line-left-bottom {
  position: absolute;
  left: 0;
  bottom: 0;
}

.line-right-bottom {
  position: absolute;
  right: 0;
  bottom: 0;
}
</style>