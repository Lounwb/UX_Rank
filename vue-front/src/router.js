import { createRouter, createWebHistory } from 'vue-router'
import Main from './components/Main.vue'
import Process from './components/Process.vue'
import Report from './components/Report.vue'
 
const routes = [
  {
    path: '/',
    component: Main
  },
  {
    path: '/process',
    name: 'Process',
    component: Process
  },
  {
    path: '/report',
    name: 'Report',
    component: Report
  }
]
 
const router = createRouter({
  history: createWebHistory(),
  routes
})
 
export default router