<template>
  <div class="login-wrapper">
    <div class="login-content" style="margin-top: 100px;">
      <div class="title-hint" style="font-weight: 700; font-size: 30px; text-align: center;">多人聊天程序</div>
      <div style="display: flex; justify-content: center; margin-top: 30px;">
        <el-upload class="avatar-uploader" action="/api/file/upload" :show-file-list="false" :on-success="handleUpload">
          <img v-if="icon" :src="icon" class="avatar icon">
          <el-icon v-else class="avatar-uploader-icon">
            <Plus />
          </el-icon>
        </el-upload>
      </div>
      <div style="margin-top: 20px;">
        <el-input placeholder="Account" v-model="account"></el-input>
      </div>
      <div style="margin-top: 20px;">
        <el-input placeholder="Password" type="password" v-model="password"></el-input>
      </div>
      <div style="margin-top: 20px;">
        <el-input placeholder="Introduction" type="textarea" :rows="4" resize="none" v-model="remark"></el-input>
      </div>
      <div style="margin-top: 30px;">
        <el-button @click="registerHandler" style="width: 100%;" type="primary">注册</el-button>
      </div>
      <div style="margin-top: 10px;">
        <el-button @click="loginHandler" style="width: 100%;">去登录</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import { ref } from 'vue'
import { useRouter } from 'vue-router'
import { ElMessage } from 'element-plus'
import request from '../utils/request'

const router = useRouter()
const icon = ref("")
const account = ref("")
const password = ref("")
const remark = ref("")

const handleUpload = (res) => {
  if (res.code !== 200) {
    ElMessage.warning("文件上传失败")
    return
  }
  icon.value = res.data
}

const loginHandler = () => {
  router.push('/login')
}

const registerHandler = () => {
  if (!account.value) {
    ElMessage.warning("请输入账号")
    return
  }
  if (!password.value) {
    ElMessage.warning("请输入密码")
    return
  }

  const params = {
    account: account.value,
    password: password.value,
    icon: icon.value,
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
.login-wrapper {
  display: flex;
  justify-content: center;
  background-image: url("@/assets/img/img_1.png");
  background-size: 100% 100%;
  height: 100vh;
  align-items: center;
}

.login-content {
  width: 350px;
  background: rgba(255, 255, 255, 0.9);
  padding: 20px;
  border-radius: 10px;
}

.icon,
.avatar-uploader-icon {
  height: 80px;
  width: 80px;
  line-height: 80px;
  border-radius: 50%;
  border: 1px solid #c0c0c0;
  text-align: center;
  font-size: 28px;
  color: #8c939d;
}
</style>