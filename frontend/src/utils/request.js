import axios from 'axios'
import {ElMessage} from 'element-plus'

const service = axios.create({
  baseURL: '/api',
  timeout: 5000,
  withCredentials: true
})

service.interceptors.response.use(
  response => response,
  error => {
    ElMessage.error(error.message)
    return Promise.reject(error)
  }
)

export default service
