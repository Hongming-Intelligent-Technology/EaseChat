<template>
  <div class="feed-page">
    <div class="feed-backdrop"></div>
    <AppHeader
      title="Community Posts"
      :user="user"
      @logout="loadUser"
    >
      <template #actions>
        <el-button type="primary" @click="openCompose">New Post</el-button>
        <el-button type="primary" @click="goChat">Chat</el-button>
      </template>
    </AppHeader>

    <main class="feed-content">
      <section class="feed">
        <div class="feed-title">Latest Posts</div>
        <div v-if="loading" class="feed-loading">Loading...</div>
        <div v-else>
          <div v-if="posts.length === 0" class="feed-empty">
            <el-empty description="No posts yet"/>
          </div>
          <div v-else class="feed-grid">
            <article v-for="item in posts" :key="item.id" class="post-card">
              <div class="post-cover" v-if="item.picture">
                <img :src="item.picture" alt="cover"/>
              </div>
              <div v-else class="post-cover-empty">
                <div class="cover-pattern"></div>
              </div>
              <div class="post-body">
                <div class="post-title">{{ item.title || 'Untitled' }}</div>
                <div class="post-meta">
                  <span>{{ item.author || item.account || 'Anonymous' }}</span>
                  <span class="dot"></span>
                  <span>{{ item.createTime }}</span>
                </div>
                <div class="post-actions">
                  <el-button text type="primary" @click="previewPost(item)">Preview</el-button>
                </div>
              </div>
            </article>
          </div>
        </div>
      </section>
    </main>

    <el-dialog v-model="previewVisible" title="Post Preview" width="600px">
      <div class="preview-body">
        <img v-if="preview.picture" :src="preview.picture" class="preview-image"/>
        <div class="preview-title">{{ preview.title || 'Untitled' }}</div>
        <div class="preview-meta">
          {{ preview.author || preview.account || 'Anonymous' }} | {{ preview.createTime || '' }}
        </div>
      </div>
    </el-dialog>

    <el-dialog v-model="composeVisible" title="Create a Post" width="520px">
      <div class="compose-card">
        <div class="panel-sub">Share something worth reading today.</div>
        <el-form label-position="top" class="compose-form">
          <el-form-item label="Title">
            <el-input v-model="postForm.title" maxlength="80" show-word-limit
                      placeholder="A clear, bold title"/>
          </el-form-item>
          <el-form-item label="Author">
            <el-input v-model="postForm.author" placeholder="Display name"/>
          </el-form-item>
          <el-form-item label="Cover Image">
            <div class="cover-row">
              <el-upload action="/api/file/upload" :show-file-list="false" :with-credentials="true"
                         :on-success="handleUploadCover">
                <el-button size="small" type="primary" plain>Upload</el-button>
              </el-upload>
              <img v-if="postForm.picture" :src="postForm.picture" alt="Cover" class="cover-thumb"/>
            </div>
          </el-form-item>
        </el-form>
        <div class="compose-actions">
          <el-button @click="composeVisible = false">Cancel</el-button>
          <el-button type="primary" @click="submitPost" :loading="saving">Publish</el-button>
        </div>
      </div>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage} from 'element-plus'
import AppHeader from '../components/AppHeader.vue'
import request from '../utils/request'

const router = useRouter()

const user = ref({})
const posts = ref([])
const loading = ref(false)
const saving = ref(false)
const previewVisible = ref(false)
const preview = ref({})
const composeVisible = ref(false)

const postForm = reactive({
  title: '',
  author: '',
  picture: ''
})

const handleUploadCover = (res) => {
  if (res.code !== 200) {
    ElMessage.warning('File upload failed')
    return
  }
  postForm.picture = res.data
}

const openCompose = () => {
  composeVisible.value = true
}

const loadUser = () => {
  request.get('/user/current').then(res => {
    if (res.data.code !== 200) {
      ElMessage.warning('User not logged in')
      setTimeout(() => router.push('/login'), 800)
      return
    }
    user.value = res.data.data
    if (!postForm.author) {
      postForm.author = user.value.account || ''
    }
  })
}

const loadPosts = () => {
  loading.value = true
  request.get('/blog/list').then(res => {
    posts.value = res.data.data || []
  }).finally(() => {
    loading.value = false
  })
}

const submitPost = () => {
  if (!postForm.title) {
    ElMessage.warning('Please enter a title')
    return
  }
  const payload = {
    title: postForm.title,
    author: postForm.author || user.value.account || '',
    picture: postForm.picture
  }
  saving.value = true
  request.post('/blog/save', payload).then(res => {
    if (res.data.code !== 200) {
      ElMessage.warning(res.data.msg)
      return
    }
    ElMessage.success('Post published')
    postForm.title = ''
    postForm.picture = ''
    composeVisible.value = false
    loadPosts()
  }).finally(() => {
    saving.value = false
  })
}

const refreshPosts = () => {
  loadPosts()
}

const previewPost = (item) => {
  preview.value = item
  previewVisible.value = true
}

const goChat = () => {
  router.push('/chat')
}

onMounted(() => {
  loadUser()
  loadPosts()
})
</script>

<style scoped>
.feed-page {
  min-height: 100vh;
  position: relative;
  overflow: hidden;
  color: var(--ease-ink);
}

.feed-backdrop {
  position: absolute;
  inset: -20% -10% auto auto;
  width: 420px;
  height: 420px;
  background: radial-gradient(circle, rgba(20, 184, 166, 0.25), rgba(255, 255, 255, 0));
  filter: blur(0px);
  pointer-events: none;
}

.feed-content {
  display: flex;
  flex-direction: column;
  gap: 32px;
  padding: 40px 60px 80px;
}

.compose-card {
  background: var(--ease-panel);
  border-radius: var(--ease-radius);
  padding: 28px;
  box-shadow: var(--ease-shadow);
  animation: floatIn 0.6s ease-out;
}

.panel-title {
  font-size: 22px;
  font-weight: 600;
  color: var(--ease-ink);
  font-family: "DM Serif Display", "Space Grotesk", serif;
}

.panel-sub {
  margin-top: 6px;
  color: var(--ease-muted);
}

.compose-form {
  margin-top: 20px;
}

.cover-row {
  display: flex;
  align-items: center;
  gap: 12px;
}

.cover-thumb {
  width: 56px;
  height: 56px;
  border-radius: 12px;
  object-fit: cover;
  border: 1px solid var(--ease-line);
}

.compose-actions {
  display: flex;
  justify-content: flex-end;
  gap: 12px;
  margin-top: 10px;
}

.feed {
  display: flex;
  flex-direction: column;
  gap: 16px;
}

.feed-title {
  font-size: 20px;
  font-weight: 600;
  color: var(--ease-ink);
}

.feed-grid {
  display: grid;
  grid-template-columns: repeat(auto-fit, minmax(280px, 1fr));
  gap: 24px;
}

.post-card {
  background: var(--ease-panel);
  border-radius: 16px;
  overflow: hidden;
  box-shadow: 0 4px 12px rgba(15, 23, 42, 0.06),
  0 2px 4px rgba(15, 23, 42, 0.04);
  display: flex;
  flex-direction: column;
  min-height: 240px;
  transition: all 0.3s cubic-bezier(0.4, 0, 0.2, 1);
  cursor: pointer;
  position: relative;
  border: 1px solid rgba(226, 232, 240, 0.8);
}

.post-card::before {
  content: '';
  position: absolute;
  top: 0;
  left: 0;
  right: 0;
  height: 3px;
  background: linear-gradient(90deg, var(--ease-accent) 0%, var(--ease-brand) 100%);
  opacity: 0;
  transition: opacity 0.3s ease;
}

.post-card:hover {
  transform: translateY(-4px);
  box-shadow: 0 12px 28px rgba(15, 23, 42, 0.12),
  0 4px 8px rgba(15, 23, 42, 0.08);
  border-color: rgba(20, 184, 166, 0.3);
}

.post-card:hover::before {
  opacity: 1;
}

.post-cover {
  position: relative;
  overflow: hidden;
  background: linear-gradient(135deg, #f8fafc 0%, #e2e8f0 100%);
}

.post-cover img {
  width: 100%;
  height: 180px;
  object-fit: cover;
  transition: transform 0.4s cubic-bezier(0.4, 0, 0.2, 1);
}

.post-card:hover .post-cover img {
  transform: scale(1.05);
}

.post-cover-empty {
  height: 120px;
  background: linear-gradient(135deg, rgba(20, 184, 166, 0.1) 0%, rgba(59, 130, 246, 0.1) 100%);
  position: relative;
  overflow: hidden;
}

.cover-pattern {
  position: absolute;
  inset: 0;
  background-image: radial-gradient(circle at 20% 30%, rgba(20, 184, 166, 0.15) 0%, transparent 50%),
  radial-gradient(circle at 80% 70%, rgba(59, 130, 246, 0.15) 0%, transparent 50%);
}

.post-body {
  padding: 20px;
  display: flex;
  flex-direction: column;
  gap: 10px;
  flex: 1;
  background: linear-gradient(to bottom, var(--ease-panel) 0%, rgba(248, 250, 252, 0.5) 100%);
}

.post-title {
  font-size: 18px;
  color: var(--ease-ink);
  font-weight: 600;
  line-height: 1.4;
  font-family: "DM Serif Display", "Space Grotesk", serif;
  display: -webkit-box;
  -webkit-line-clamp: 2;
  line-clamp: 2;
  -webkit-box-orient: vertical;
  overflow: hidden;
  transition: color 0.2s ease;
}

.post-card:hover .post-title {
  color: var(--ease-accent);
}

.post-meta {
  display: flex;
  align-items: center;
  gap: 8px;
  color: var(--ease-muted);
  font-size: 13px;
  padding-top: 4px;
}

.post-meta span:first-child {
  font-weight: 500;
  color: var(--ease-ink);
  opacity: 0.7;
}

.dot {
  width: 3px;
  height: 3px;
  border-radius: 50%;
  background: var(--ease-accent);
}

.post-actions {
  margin-top: auto;
  padding-top: 12px;
  border-top: 1px solid rgba(226, 232, 240, 0.6);
}

.preview-body {
  display: flex;
  flex-direction: column;
  gap: 12px;
}

.preview-image {
  width: 100%;
  border-radius: 12px;
  max-height: 260px;
  object-fit: cover;
}

.preview-title {
  font-size: 24px;
  font-weight: 600;
  color: var(--ease-ink);
  font-family: "DM Serif Display", "Space Grotesk", serif;
}

.preview-meta {
  color: var(--ease-muted);
}

.feed-grid {
  animation: riseIn 0.7s ease-out;
}

.post-card {
  animation: cardFadeIn 0.5s ease-out backwards;
}

.post-card:nth-child(1) {
  animation-delay: 0.05s;
}

.post-card:nth-child(2) {
  animation-delay: 0.1s;
}

.post-card:nth-child(3) {
  animation-delay: 0.15s;
}

.post-card:nth-child(4) {
  animation-delay: 0.2s;
}

.post-card:nth-child(5) {
  animation-delay: 0.25s;
}

.post-card:nth-child(6) {
  animation-delay: 0.3s;
}

@keyframes cardFadeIn {
  from {
    opacity: 0;
    transform: translateY(20px) scale(0.95);
  }
  to {
    opacity: 1;
    transform: translateY(0) scale(1);
  }
}

@keyframes floatIn {
  from {
    transform: translateY(12px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@keyframes riseIn {
  from {
    transform: translateY(16px);
    opacity: 0;
  }
  to {
    transform: translateY(0);
    opacity: 1;
  }
}

@media (max-width: 640px) {
  .feed-content {
    padding: 20px;
  }
}
</style>
