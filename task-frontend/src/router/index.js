import { createRouter, createWebHistory } from 'vue-router'
import HomeView from '../views/HomeView.vue'
import RegisterView from '../views/RegisterView.vue'
import LoginView from '../views/LoginView.vue'
import TasksView from '@/views/TasksView.vue'
import NewTaskView from '@/views/NewTaskView.vue'
import EditTaskView from '@/views/EditTaskView.vue'

const routes = [
  {
    path: "/register",
    name: "Register",
    component: RegisterView,
  },
  {
    path: '/',
    name: 'home',
    component: HomeView
  },
  {
    path: '/login',
    name: 'login',
    component: LoginView
  },
  {
    path: '/newTask',
    name: 'newTask',
    component: NewTaskView
  },
  {
    path: '/editTask/:taskId',
    name: 'editTask',
    component: EditTaskView
  },
  {
    path: '/tasks',
    name: 'tasks',
    component: TasksView
  }
]

const router = createRouter({
  history: createWebHistory(process.env.BASE_URL),
  routes
})

export default router
