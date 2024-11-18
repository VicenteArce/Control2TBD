
<template>
  <h2>Mis Tareas</h2>

  <div>
    <label for="stateFilter">Filtrar por estado:</label>
    <select v-model="stateFilter">
      <option value="pendiente">Pendiente</option>
      <option value="completada">Completada</option>
    </select>
    <button type="button" @click="filterTasksByState">Filtrar por estado</button>
  </div>

  <div>
    <label for="searchWord">Buscar por palabra clave:</label>
    <input type="text" v-model="searchWord" placeholder="Escribe una palabra clave">
    <button type="button" @click="searchTasksByWord">Buscar por palabra clave</button>
  </div>

  <div>
    <button type="button" @click="clearFilters">Eliminar filtros</button>
  </div>

  <div v-if="tasks.length === 0">
    <p>No hay tareas disponibles según los filtros aplicados.</p>
  </div>

  <div v-for="task in tasks" :key="task.id">
    <div class="task-item">
      <h3>Título: {{ task.title }}</h3>
      <p>Descripción: {{ task.description }}</p>
      <p>Fecha de expiración: {{ task.expiration_date }}</p>
      <p>Estado: {{ task.state }}</p>
      <button type="submit" @click="goToEdit(task.task_id)">Editar tarea</button>
      <button type="submit" @click="deleteTask(task.task_id)">Eliminar tarea</button>
      <div v-if="task.state !== 'completada'">
        <button type="submit" @click="completeTask(task.task_id)">Marcar como completada</button>
      </div>
    </div>
  </div>
  <br>
  <button type="submit" @click="goToCreate">Agregar tarea</button>

</template>

<script>
import axios from 'axios';

export default {
  data() {
    return {
      tasks: [],
      stateFilter: 'pendiente',
      searchWord: ''
    };
  },
  created() {
    this.getTasks();
	this.checkTasksExpiringSoon();
  },
  methods: {
    async filterTasksByState() {
      try {
        const token = sessionStorage.getItem('user');
        const response = await axios.get(
          `http://localhost:8080/api/tasks/${this.stateFilter}`,
          { params: { token: token } }
        );

        if (response.data.length === 0) {
          alert('No hay tareas disponibles para este estado');
        } else {
          this.tasks = response.data;
        }
        console.log(response.data);
      } catch (error) {
        console.log(error);
      }
    },

    async searchTasksByWord() {
      try {
        const token = sessionStorage.getItem('user');
        const response = await axios.get(
          `http://localhost:8080/api/tasks/word/${this.searchWord}`,
          { params: { token: token } }
        );

        if (response.data.length === 0) {
          alert('No hay tareas disponibles para esta palabra clave');
        } else {
          this.tasks = response.data;
        }
        console.log(response.data);
      } catch (error) {
        console.log(error);
      }
    },

    async getTasks() {
      try {
        const userId = sessionStorage.getItem('userId');
        const token = sessionStorage.getItem('user');
        const response = await axios.get(
          `http://localhost:8080/api/tasks/user/${userId}`,
          { params: { token: token } }
        );
        this.tasks = response.data;
        console.log(response.data);
      } catch (error) {
        console.log(error);
      }
    },

    async goToCreate() {
      this.$router.push('/newTask');
    },

    async goToEdit(taskId) {
      this.$router.push(`/editTask/${taskId}`);
    },

    async completeTask(id) {
      try {
        const token = sessionStorage.getItem('user');
        const response = await axios.put(
          `http://localhost:8080/api/task/complete/${id}`,
          null,
          { params: { token: token } }
        );
        console.log(response.data);
        window.location.reload();
      } catch (error) {
        console.log(error);
      }
    },

    async deleteTask(id) {
      try {
        const token = sessionStorage.getItem('user');
        await axios.delete(`http://localhost:8080/api/task/${id}`, {
          params: { token: token }
        });
        window.alert('Tarea eliminada exitosamente');
        window.location.reload();
      } catch (error) {
        console.log(error);
      }
    },

    clearFilters() {
      this.stateFilter = 'pendiente';
      this.searchWord = '';
      this.getTasks();  // Obtener todas las tareas nuevamente
    },

	async checkTasksExpiringSoon() {
   await this.getTasks();  // Asegúrate de que las tareas están cargadas

   if (!Array.isArray(this.tasks) || this.tasks.length === 0) {
     console.error("Las tareas no están disponibles o no son un array válido");
     return;
   }

   const today = new Date();
   const twoDaysFromNow = new Date(today);
   twoDaysFromNow.setDate(today.getDate() + 1);

   const expiringTasks = this.tasks.filter(task => {
     if (!task.expiration_date) return false;
     const taskDate = new Date(task.expiration_date);
     return taskDate >= today && taskDate <= twoDaysFromNow;
   });

   if (expiringTasks.length > 0) {
     expiringTasks.forEach(task => {
       alert(`La tarea "${task.title}" expirará pronto el ${task.expiration_date}`);
     });
   } else {
     alert("No hay tareas que expiren en los próximos 2 días.");
   }
}
  }
};
</script>

<style scoped>
.task-item {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}
</style>
