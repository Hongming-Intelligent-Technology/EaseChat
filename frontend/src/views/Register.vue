<template>
  <div class="auth-page">
    <div class="auth-card ease-card">
      <div class="auth-badge">EaseChat</div>
      <h1 class="auth-title ease-title">Create Your Profile</h1>
      <p class="auth-sub">Join the community and start posting today.</p>
      <div class="avatar-row">
        <el-upload class="avatar-uploader" action="/api/file/upload" :show-file-list="false"
                   :with-credentials="true" :on-success="handleUpload">
          <img v-if="icon" :src="icon" class="avatar icon">
          <el-icon v-else class="avatar-uploader-icon">
            <Plus/>
          </el-icon>
        </el-upload>
      </div>
      <div class="auth-form">
        <el-input placeholder="Account" v-model="account"></el-input>
        <el-input placeholder="Password" type="password" v-model="password"></el-input>
        <el-input placeholder="Phone" v-model="phone"></el-input>
        <el-input placeholder="Email" type="email" v-model="email"></el-input>
        <el-input placeholder="Address (optional)" v-model="address"></el-input>
        <el-select v-model="gender" placeholder="Select Gender (optional)">
          <el-option label="Unknown" value="UNKNOWN"></el-option>
          <el-option label="Male" value="MALE"></el-option>
          <el-option label="Female" value="FEMALE"></el-option>
        </el-select>
        <el-input placeholder="Introduction (optional)" type="textarea" :rows="4" resize="none"
                  v-model="remark"></el-input>
        <el-button @click="registerHandler" type="primary" class="auth-primary">Register</el-button>
        <el-button @click="loginHandler" class="auth-secondary">Go to Login</el-button>
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
const icon = ref("")
const account = ref("")
const password = ref("")
const phone = ref("")
const email = ref("")
const address = ref("")
const gender = ref("")
const remark = ref("")

const handleUpload = (res) => {
  if (res.code !== 200) {
    ElMessage.warning("File upload failed")
    return
  }
  icon.value = res.data
}

const loginHandler = () => {
  router.push('/login')
}

const registerHandler = () => {
  if (!account.value) {
    ElMessage.warning("Please enter your account")
    return
  }
  if (!password.value) {
    ElMessage.warning("Please enter your password")
    return
  }

  const params = {
    account: account.value,
    password: password.value,
    icon: icon.value,
    phone: phone.value,
    email: email.value,
    address: address.value,
    gender: gender.value || "UNKNOWN",
    remark: remark.value
  }

  request.post("/user/register", params).then((res) => {
    const data = res.data
    if (data.code !== 200) {
      ElMessage.warning(data.msg)
      return
    }
    ElMessage.success(data.msg)
    setTimeout(() => {
      router.push('/login')
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
  padding: 40px 20px;
}

.auth-card {
  width: min(520px, 92vw);
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
  margin-bottom: 16px;
}

.avatar-row {
  display: flex;
  justify-content: center;
  margin: 16px 0 20px;
}

.auth-form {
  display: grid;
  gap: 14px;
}

.auth-primary,
.auth-secondary {
  width: 100%;
}

.icon,
.avatar-uploader-icon {
  height: 80px;
  width: 80px;
  line-height: 80px;
  border-radius: 50%;
  border: 1px solid var(--ease-line);
  text-align: center;
  font-size: 28px;
  color: var(--ease-muted);
}

.auth-glow {
  position: absolute;
  width: 420px;
  height: 420px;
  border-radius: 50%;
  background: radial-gradient(circle, rgba(20, 184, 166, 0.2), rgba(255, 255, 255, 0));
  bottom: 5%;
  left: 8%;
  z-index: 1;
}
</style>
