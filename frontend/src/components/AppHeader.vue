<template>
  <header class="app-header">
    <div class="header-brand">
      <div class="brand-eyebrow">{{ eyebrow }}</div>
      <h1 class="ease-title">{{ title }}</h1>
    </div>
    <div class="header-actions">
      <div class="user-chip ease-chip" v-if="user">
        <el-avatar v-if="user.icon" :src="user.icon" size="small"/>
        <span class="user-name">{{ user.account || 'Guest' }}</span>
      </div>
      <slot name="actions"></slot>
      <el-button @click="handleLogout" type="danger" v-if="showLogout">Logout</el-button>
    </div>
  </header>
</template>

<script setup>
import {defineProps, defineEmits} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import request from '../utils/request'

const props = defineProps({
  eyebrow: {
    type: String,
    default: 'EaseChat'
  },
  title: {
    type: String,
    default: 'Community'
  },
  user: {
    type: Object,
    default: null
  },
  showLogout: {
    type: Boolean,
    default: true
  }
})

const emit = defineEmits(['logout'])
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
.app-header {
  position: sticky;
  top: 0;
  z-index: 10;
  display: flex;
  justify-content: space-between;
  align-items: center;
  padding: 24px 60px;
  background: rgba(255, 255, 255, 0.9);
  backdrop-filter: blur(10px);
  border-bottom: 1px solid var(--ease-line);
}

.brand-eyebrow {
  font-size: 12px;
  letter-spacing: 0.3em;
  text-transform: uppercase;
  color: var(--ease-accent);
}

.header-brand h1 {
  margin: 6px 0 0;
  font-size: 32px;
  color: var(--ease-ink);
}

.header-actions {
  display: flex;
  align-items: center;
  gap: 12px;
}

.user-chip {
  font-size: 13px;
}

@media (max-width: 1024px) {
  .app-header {
    flex-direction: column;
    align-items: flex-start;
    gap: 16px;
    padding: 20px;
  }
}

@media (max-width: 640px) {
  .header-actions {
    flex-wrap: wrap;
  }
}
</style>
