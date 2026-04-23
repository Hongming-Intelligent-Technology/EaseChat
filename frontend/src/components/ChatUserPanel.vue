<template>
  <div class="chat-user-panel">
    <div class="user-avatar-section">
      <img v-if="user.icon" :src="user.icon" class="avatar icon">
      <el-avatar v-else class="avatar-fallback">{{ (user.account || 'U').slice(0, 1).toUpperCase() }}</el-avatar>
    </div>
    <div class="user-info">
      <div class="user-account">Account: {{ user.account }}</div>
      <div class="user-actions">
        <el-button @click="$emit('show-detail')" size="small" type="warning">Details</el-button>
        <el-button @click="handleLogout" size="small" type="danger">Logout</el-button>
      </div>
    </div>
  </div>
</template>

<script setup>
import {defineProps, defineEmits} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import request from '../utils/request'

const props = defineProps({
  user: {
    type: Object,
    required: true
  }
})

const emit = defineEmits(['show-detail', 'logout'])
const router = useRouter()

const handleLogout = () => {
  request.get('/user/loginOut').then(() => {
    ElMessage.success('Logout successful')
    emit('logout')
    setTimeout(() => router.push('/login'), 800)
  })
}
</script>

<style scoped>
.chat-user-panel {
  display: flex;
  flex-direction: column;
  align-items: center;
}

.user-avatar-section {
  display: flex;
  justify-content: center;
  margin-top: 30px;
  min-height: 100px;
}

.user-info {
  text-align: center;
  margin-top: 20px;
  display: flex;
  flex-direction: column;
  gap: 12px;
  padding: 0 10px;
}

.user-account {
  font-size: 14px;
  color: var(--ease-ink);
}

.user-actions {
  display: flex;
  gap: 8px;
  justify-content: center;
  flex-wrap: wrap;
}

.icon {
  height: 100px;
  width: 100px;
  border-radius: 50%;
  border: 1px solid var(--ease-line);
  object-fit: cover;
}

.avatar-fallback {
  height: 100px;
  width: 100px;
  font-size: 32px;
}
</style>
