<template>
  <div class="auth-page">
    <div class="auth-card ease-card">
      <div class="auth-badge">EaseChat</div>
      <h1 class="auth-title ease-title">Welcome Back</h1>
      <p class="auth-sub">Sign in to continue the conversation.</p>
      <div class="auth-form">
        <el-input placeholder="Account" v-model="account">
          <template #prefix>
            <el-icon>
              <User/>
            </el-icon>
          </template>
        </el-input>
        <el-input placeholder="Password" type="password" v-model="password">
          <template #prefix>
            <el-icon>
              <Lock/>
            </el-icon>
          </template>
        </el-input>
        <el-button @click="handlerLogin" type="primary" class="auth-primary">Login</el-button>
        <el-button @click="handlerRegister" class="auth-secondary">Go to Register</el-button>
      </div>
    </div>
    <div class="auth-glow"></div>
  </div>
</template>

<script setup>
import {ref} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const account = ref("")
const password = ref("")

const handlerRegister = () => {
  router.push('/register')
}

const handlerLogin = () => {
  if (!account.value) {
    ElMessage.warning("Please enter your account")
    return
  }
  if (!password.value) {
    ElMessage.warning("Please enter your password")
    return
  }

  const params = {account: account.value, password: password.value}

  request.post("/user/login", params).then((res) => {
    const data = res.data
    if (data.code !== 200) {
      ElMessage.warning(data.msg)
      return
    }
    ElMessage.success(data.msg)
    // Simply handle, usually need to save token
    setTimeout(() => {
      router.push('/')
    }, 800)
  })
}
</script>

<style scoped>
.auth-page {
  min-height: 100vh;
  display: grid;
  place-items: center;
  position: relative;
  overflow: hidden;
}

.auth-card {
  width: min(420px, 90vw);
  padding: 36px 32px;
  position: relative;
  z-index: 2;
}

.auth-badge {
  text-transform: uppercase;
  letter-spacing: 0.3em;
  color: var(--ease-accent);
  font-size: 12px;
}

.auth-title {
  margin: 10px 0 6px;
  font-size: 30px;
}

.auth-sub {
  color: var(--ease-muted);
  margin-bottom: 24px;
}

.auth-form {
  display: grid;
  gap: 16px;
}

.auth-primary,
.auth-secondary {
  width: 100%;
}

.auth-glow {
  position: absolute;
  width: 420px;
  height: 420px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(217, 119, 6, 0.25), rgba(255, 255, 255, 0));
  top: 10%;
  right: 10%;
  filter: blur(0px);
  z-index: 1;
}
</style>
