<template>
  <div class="main-box chat-page">
    <div class="main-item-box chat-shell">
      <div class="chat-sidebar">
        <ChatUserPanel
          :user="user"
          @show-detail="toDetail"
          @logout="loginOut"
        />
        <div style="padding: 10px; box-sizing: border-box; flex: 1; overflow: hidden;">
          <el-tabs type="card" class="sidebar-tabs">
            <el-tab-pane label="Chat Rooms">
              <div class="userList">
                <el-card @click="showRoom(item)" shadow="hover"
                         style="margin-bottom: 10px; cursor: pointer;"
                         v-for="item in roomData" :key="item.id">
                  <div style="display: flex;">{{ item.name }}</div>
                </el-card>
                <el-empty v-if="roomData.length === 0" description="No chat rooms available"/>
              </div>
            </el-tab-pane>
            <el-tab-pane label="My Chat Rooms">
              <div class="userList">
                <el-button @click="handlerShowEditRoom" size="small" type="primary"
                           style="width: 100%; margin-bottom: 10px;">
                  New
                </el-button>
                <el-card @click="showRoom(item)" shadow="hover"
                         style="margin-bottom: 10px; cursor: pointer;"
                         v-for="item in selfRoom" :key="item.id">
                  <div style="display: flex; justify-content: space-between; align-items: center;">
                    <div>{{ item.name }}</div>
                    <el-button @click.stop="handlerDelete(item)" size="small" type="warning">
                      Delete
                    </el-button>
                  </div>
                </el-card>
                <el-empty v-if="selfRoom.length === 0" description="No created chat rooms"/>
              </div>
            </el-tab-pane>
          </el-tabs>
        </div>
      </div>

      <div class="chat-main">

        <div v-if="showDetail" style="flex: 1; padding: 20px; overflow-y: auto;">
          <div v-if="showEdit" style="width: 500px; margin: 0 auto;">
            <div
              style="display: flex; justify-content: center; margin: 30px auto; position:relative;">
              <div style="position: absolute; top: 0px; right: 140px; cursor: pointer"
                   @click="removeIcon">
                <el-icon color="#F56C6C" size="20">
                  <CircleCloseFilled/>
                </el-icon>
              </div>
              <el-upload class="avatar-uploader" action="/api/file/upload" :show-file-list="false"
                         :with-credentials="true" :on-success="handleUploadIcon">
                <img v-if="updateUser.icon" :src="updateUser.icon" class="avatar icon">
                <el-icon v-else class="avatar-uploader-icon">
                  <Plus/>
                </el-icon>
              </el-upload>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Account" disabled v-model="updateUser.account"></el-input>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Password" type="password"
                        v-model="updateUser.password"></el-input>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Phone" v-model="updateUser.phone"></el-input>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Email" type="email" v-model="updateUser.email"></el-input>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Address" v-model="updateUser.address"></el-input>
            </div>
            <div style="margin-top: 20px;">
              <el-select v-model="updateUser.gender" placeholder="Select Gender">
                <el-option label="Unknown" value="UNKNOWN"></el-option>
                <el-option label="Male" value="MALE"></el-option>
                <el-option label="Female" value="FEMALE"></el-option>
              </el-select>
            </div>
            <div style="margin-top: 20px;">
              <el-input placeholder="Personal Introduction" type="textarea" :rows="3" resize="none"
                        v-model="updateUser.remark"></el-input>
            </div>
          </div>
          <div v-else>
            <div style="display: flex; justify-content: center; margin-top: 100px;">
              <img v-if="user.icon" :src="user.icon" class="avatar icon">
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div>Account: {{ user.account }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div v-if="user.phone">Phone: {{ user.phone }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div v-if="user.email">Email: {{ user.email }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div v-if="user.address">Address: {{ user.address }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div v-if="user.gender">Gender: {{ user.gender }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div>Role: {{ user.role || 'NORMAL_USER' }}</div>
            </div>
            <div style="text-align: center; margin-top: 20px;">
              <div>Personal Introduction: {{ user.remark || 'No personal introduction' }}</div>
            </div>
          </div>
          <div style="display: flex; justify-content: center; margin-top: 50px;">
            <el-button @click="handlerEdit" type="primary">{{
                showEdit ? 'Save' : 'Edit'
              }}
            </el-button>
            <el-button @click="toHandlerClose" type="danger">{{
                showEdit ? 'Cancel' : 'Close'
              }}
            </el-button>
          </div>
        </div>

        <div v-else style="flex: 1; display: flex; flex-direction: column; height: 100%;">
          <div v-if="!selectRoom.name"
               style="height: 100%; display: flex; justify-content: center; align-items: center;">
            <el-empty description="Please select a chat room"/>
          </div>

          <div v-if="selectRoom.name" style="height: 100%; display: flex; flex-direction: column;">
            <el-card shadow="never" :body-style="{ padding: '15px' }">
              <div style="font-size: 20px;">Chat Room: {{ selectRoom.name }}</div>
            </el-card>

            <div class="chat-content" ref="chatContainer">
              <div v-if="commentData.length === 0"
                   style="display: flex; justify-content: center; height: 100%; align-items: center;">
                <el-empty description="No messages"></el-empty>
              </div>
              <div v-for="(item, index) in commentData" :key="index"
                   style="display: flex; margin-top: 10px;">
                <div v-if="user.account === item.account"
                     style="display: flex; justify-content: end; width: 100%">
                  <div
                    style="display: flex; justify-content: end; background: #FFFFFF; padding: 10px; border-radius: 8px;">
                    <div style="margin-right: 15px; text-align: right;">
                      <div style="color: #c0c0c0; font-size: 12px;">{{ item.createTime }}</div>
                      <div v-if="item.type === 'txt'" style="margin-top: 5px;">{{
                          item.content
                        }}
                      </div>
                      <div v-if="item.type === 'img'" style="margin-top: 5px;">
                        <el-image :src="item.content" style="height: 100px"
                                  :preview-src-list="[item.content]"/>
                      </div>
                      <div v-if="item.type === 'music'" style="margin-top: 5px;">
                        <audio controls preload="auto">
                          <source :src="item.content" type="audio/mpeg">
                        </audio>
                      </div>
                    </div>
                    <div>
                      <img v-if="item.icon" class="friendUser" :src="item.icon"/>
                      <el-avatar v-else>{{ item.account }}</el-avatar>
                    </div>
                  </div>
                </div>
                <div v-else style="display: flex; justify-content: start; width: 100%;">
                  <div
                    style="display: flex; justify-content: start; background: #FFFFFF; padding: 10px; border-radius: 8px;">
                    <div>
                      <img v-if="item.icon" class="friendUser" :src="item.icon"/>
                      <el-avatar v-else>{{ item.account }}</el-avatar>
                    </div>
                    <div style="margin-left: 15px;">
                      <div style="color: #c0c0c0; font-size: 12px;">{{ item.createTime }}</div>
                      <div v-if="item.type === 'txt'">{{ item.content }}</div>
                      <div v-if="item.type === 'img'">
                        <el-image :src="item.content" style="height: 100px"
                                  :preview-src-list="[item.content]"/>
                      </div>
                      <div v-if="item.type === 'music'" style="margin-top: 10px;">
                        <audio controls preload="auto">
                          <source :src="item.content" type="audio/mpeg">
                        </audio>
                      </div>
                    </div>
                  </div>
                </div>
              </div>
            </div>

            <div style="padding: 10px; background: #fff; border-top: 1px solid #eee;">
              <div style="display: flex; gap: 10px;">
                <el-input placeholder="Enter message..." v-model="content"
                          @keyup.enter="sendHandler"></el-input>
                <el-button @click="sendHandler" type="primary" :icon="Pointer">Send</el-button>
                <el-upload action="/api/file/upload" :show-file-list="false"
                           :with-credentials="true"
                           :on-success="handleUploadChat">
                  <el-button type="success" :icon="Picture" circle></el-button>
                </el-upload>
                <el-button @click="sendMusicHandler" type="warning" :icon="Headset"
                           circle></el-button>
              </div>
            </div>
          </div>
        </div>
      </div>
    </div>

    <el-dialog title="Recording" v-model="sendMusic" width="400px" :before-close="cancelMusic">
      <div style="text-align: center; font-size: 20px; padding: 20px;">
        <div class="recording-animation">Recording...</div>
      </div>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="cancelMusic">Cancel</el-button>
          <el-button type="primary" @click="handlerSubmitMusic">Send</el-button>
        </span>
      </template>
    </el-dialog>

    <el-dialog title="Add Chat Room" v-model="showRoomFlag" width="400px">
      <el-form :model="roomForm" :rules="rules" ref="roomFormRef" label-width="100px">
        <el-form-item label="Name" prop="name">
          <el-input v-model="roomForm.name"/>
        </el-form-item>
      </el-form>
      <template #footer>
        <span class="dialog-footer">
          <el-button @click="showRoomFlag = false">Cancel</el-button>
          <el-button type="primary" @click="handlerSubmitRoom">Confirm</el-button>
        </span>
      </template>
    </el-dialog>
  </div>
</template>

<script setup>
import {ref, reactive, onMounted, onUnmounted} from 'vue'
import {useRouter} from 'vue-router'
import {ElMessage, ElMessageBox} from 'element-plus'
import {Pointer, Picture, Headset, Plus, CircleCloseFilled} from '@element-plus/icons-vue'
import request from '../utils/request'
import ChatUserPanel from '../components/ChatUserPanel.vue'

const router = useRouter()

// State variables
const user = ref({})
const updateUser = ref({})
const showEdit = ref(false)
const showDetail = ref(false)
const sendMusic = ref(false)
const content = ref("")
const roomData = ref([])
const selfRoom = ref([])
const commentData = ref([])
const selectRoom = ref({})
const showRoomFlag = ref(false)
const roomForm = ref({})
const roomFormRef = ref(null)
const chatContainer = ref(null)

// Recording related
let mediaRecorder = null
let recordedBlobs = []

const rules = {
  name: [{required: true, message: 'Please enter the chat room name', trigger: 'blur'}]
}

// Polling timer
let timer = null
const removeIcon = () => {
  updateUser.value.icon = null
}

const handlerEdit = () => {
  if (!showEdit.value) {
    showEdit.value = true
    return
  }
  if (!updateUser.value.password) {
    ElMessage.warning("Please enter password")
    return
  }
  request.post("/user/update", updateUser.value).then(res => {
    if (res.data.code !== 200) {
      ElMessage.warning(res.data.msg)
      return
    }
    ElMessage.success("Update successful")
    onloadAuth()
    showEdit.value = false
  })
}

const handleUploadIcon = (res) => {
  if (res.code !== 200) {
    ElMessage.warning("File upload failed")
    return
  }
  updateUser.value.icon = res.data
}

const toHandlerClose = () => {
  if (showEdit.value) {
    showEdit.value = false
    return
  }
  showDetail.value = false
}

const toDetail = () => {
  showEdit.value = false
  showDetail.value = true
}

const sendMusicHandler = () => {
  navigator.mediaDevices.getUserMedia({audio: true}).then(stream => {
    mediaRecorder = new MediaRecorder(stream)
    recordedBlobs = []
    mediaRecorder.ondataavailable = event => {
      if (event.data && event.data.size > 0) {
        recordedBlobs.push(event.data)
      }
    }
    mediaRecorder.start()
    sendMusic.value = true
  }).catch(() => {
    ElMessage.warning("No recording permission or device does not support")
  })
}

const handlerSubmitMusic = () => {
  if (!mediaRecorder) return
  mediaRecorder.stop()
  setTimeout(() => {
    let superBuffer = new Blob(recordedBlobs, {type: 'audio/wav'})
    const formData = new FormData()
    formData.append("file", superBuffer, new Date().getTime() + '.wav')

    request.post('/file/upload', formData, {headers: {"Content-Type": "multipart/form-data"}}).then(res => {
      if (res.data.code !== 200) {
        ElMessage.warning("File upload failed")
        return
      }
      sendComment(res.data.data, "music")
      sendMusic.value = false
    })
  }, 300)
}

const cancelMusic = () => {
  if (mediaRecorder && mediaRecorder.state !== 'inactive') {
    mediaRecorder.stop()
  }
  sendMusic.value = false
}

const handlerDelete = (room) => {
  ElMessageBox.confirm(`Confirm to delete this chat room?`, 'Tip', {
    confirmButtonText: 'Confirm',
    cancelButtonText: 'Cancel',
    type: "warning",
  }).then(() => {
    request.get(`/room/delete`, {params: {id: room.id}}).then(() => {
      if (selectRoom.value.id === room.id) selectRoom.value = {}
      ElMessage.success("Deleted successfully")
      onloadRoom()
    })
  })
}

const handlerSubmitRoom = () => {
  roomFormRef.value.validate((valid) => {
    if (!valid) return
    request.post("/room/save", roomForm.value).then(res => {
      if (res.data.code === 400) {
        ElMessage.warning(res.data.msg)
        return
      }
      ElMessage.success("Added successfully")
      showRoomFlag.value = false
      onloadRoom()
    })
  })
}

const handlerShowEditRoom = () => {
  roomForm.value = {}
  showRoomFlag.value = true
}

const handleUploadChat = (res) => {
  if (res.code !== 200) {
    ElMessage.warning("File upload failed")
    return
  }
  sendComment(res.data, "img")
}

const sendHandler = () => {
  if (!content.value) {
    ElMessage.warning("Please enter message")
    return
  }
  sendComment(content.value, "txt")
  content.value = ""
}

const sendComment = (content, type) => {
  const comment = {
    content: content,
    roomId: selectRoom.value.id,
    type: type,
  }
  request.post("/comment/save", comment).then(() => {
    ElMessage.success("Sent successfully")
    fetchComments()
  })
}

const loginOut = () => {
  request.get(`/user/loginOut`).then(() => {
    ElMessage.success('Logout successful')
    setTimeout(() => {
      router.push("/login")
    }, 800)
  })
}

const showRoom = (room) => {
  showDetail.value = false
  selectRoom.value = room
  commentData.value = []
  fetchComments()
}

const onloadAuth = () => {
  request.get("/user/current").then(res => {
    if (res.data.code !== 200) {
      ElMessage.warning("User not logged in")
      setTimeout(() => {
        router.push("/login")
      }, 800)
      return
    }
    user.value = res.data.data
    updateUser.value = JSON.parse(JSON.stringify(user.value))
  })
}

const onloadRoom = () => {
  request.get("/room/self/list").then(res => selfRoom.value = res.data.data)
  request.get("/room/list").then(res => roomData.value = res.data.data)
}

const fetchComments = () => {
  if (!selectRoom.value.id) return
  request.get("/comment/list", {params: {roomId: selectRoom.value.id}}).then(res => {
    commentData.value = res.data.data
  })
}

onMounted(() => {
  onloadAuth()
  onloadRoom()
  timer = setInterval(fetchComments, 1000)
})

onUnmounted(() => {
  if (timer) clearInterval(timer)
})
</script>

<style scoped>
.chat-page {
  min-height: 100vh;
  padding: 40px 20px 60px;
}

.main-box {
  width: min(1200px, 92vw);
  margin: 0 auto;
  height: calc(100vh - 140px);
  display: flex;
}

.main-item-box {
  width: 100%;
  display: flex;
}

.chat-shell {
  background: var(--ease-panel);
  border-radius: var(--ease-radius);
  box-shadow: var(--ease-shadow);
  overflow: hidden;
  border: 1px solid var(--ease-line);
}

.chat-sidebar {
  width: 300px;
  border-right: 1px solid var(--ease-line);
  display: flex;
  flex-flow: column;
  background: rgba(248, 250, 252, 0.7);
}

.chat-main {
  flex: 1;
  display: flex;
  flex-direction: column;
  overflow: hidden;
  background: var(--ease-surface);
}

.friendUser,
.icon,
.avatar-uploader-icon {
  border-radius: 50%;
  border: 1px solid var(--ease-line);
}

.friendUser {
  height: 40px;
  width: 40px;
}

.icon,
.avatar-uploader-icon {
  height: 100px;
  width: 100px;
  line-height: 100px;
  text-align: center;
  font-size: 30px;
  color: var(--ease-muted);
}

.userList {
  height: calc(100vh - 250px);
  overflow-y: auto;
}

.chat-content {
  flex: 1;
  overflow-y: auto;
  padding: 10px;
  background-color: var(--ease-surface);
}

.sidebar-tabs :deep(.el-tabs__content) {
  height: calc(100% - 40px);
  overflow: hidden;
}

@media (max-width: 1024px) {
  .chat-shell {
    flex-direction: column;
  }

  .chat-sidebar {
    width: 100%;
    border-right: none;
    border-bottom: 1px solid var(--ease-line);
  }
}
</style>
