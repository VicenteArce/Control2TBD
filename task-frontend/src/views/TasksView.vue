<template>
	<h2>Mis Tareas</h2>
	<div v-for="task in tasks " :key="task.id">
		<div class="task-item">
		<h3>Título: {{task.title}}</h3>
		<p>Descripción: {{ task.description }}</p>
		<p>Fecha de expiración: {{ task.expiration_date }}</p>
		<p>Estado: {{ task.state }}</p>
		<button type="submit" @click="goToEdit(task.task_id)">Editar tarea</button>
	</div>
	</div>
	<br>

	<button type="submit" @click="goToCreate()">Agregar tarea</button>
</template>

<script>
import axios from 'axios';

export default{
	data(){
		return{
			tasks: []
		}
	},
	created(){
		this.getTasks()
	},
	methods:{
		async getTasks(){
			try{
				const userId = sessionStorage.getItem('userId');
				const token = sessionStorage.getItem('user')
				const response = await axios.get(`http://localhost:8080/api/tasks/user/${userId}`, 
				{ params:{
					token: token}});
				this.tasks = response.data
				console.log(response.data)
			} catch(error){
				console.log(error)
			}
		},
		async goToCreate(){
			this.$router.push("/newTask");
		},
		async goToEdit(taskId){
			this.$router.push(`/editTask/${taskId}`);
		}
	}
}
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