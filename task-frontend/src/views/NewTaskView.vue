<template>
	<div class="login-container">
    <h1>Nueva Tarea</h1>
    <form @submit.prevent="loginUser">
      <div class="form-group">
        <label for="title">Título de la tarea:</label>
        <input
          type="text"
          id="title"
          v-model="task.title"
          required
          placeholder="Ingrese un título a la tarea"
        />
      </div>
      <div class="form-group">
        <label for="description">Descripción de la tarea:</label>
        <input
          type="text"
          id="description"
          v-model="task.description"
          required
          placeholder="Ingrese una descripción de la tarea"
        />
      </div>
			<div class="form-group">
        <label for="experation_date">Fecha de expiración de la tarea:</label>
        <input
          type="date"
          id="experation_date"
          v-model="task.expiration_date"
          required
          placeholder="Ingrese una fecha de expiración de la tarea"
        />
      </div>
      <button type="submit" @click="saveTask()">Crear tarea</button>
    </form>
    <p v-if="message" :class="{'error-message': !success, 'success-message': success}">
      {{ message }}
    </p>
  </div>
</template>

<script>
import axios from 'axios';

export default{
	data(){
		return{
			task: {
				user_id: '',
				title: '',
				description: '', 
				expiration_date: '',
				state: 'pendiente'
			}
		}
	},
	methods:{
		async saveTask(){
			try{
				const date = new Date();
				const formattedDate = date.toISOString().split('T')[0];
				if(this.task.expiration_date < formattedDate){
					window.alert("No se puede crear una tarea para antes de hoy")
				}else{
					this.task.user_id = sessionStorage.getItem('userId');
					const token = sessionStorage.getItem('user');
					const response = await axios.post(`http://localhost:8080/api/task`, this.task, 
					{ params:{
						token: token}});
					console.log(response.data);
					window.alert("Tarea creada exitosamente");
					this.$router.push("/tasks");
				}
			} catch(error){
				console.log(error)
			}
			
		}
	}
}
</script>

<style scoped>
.login-container {
  max-width: 400px;
  margin: 0 auto;
  padding: 20px;
  border: 1px solid #ccc;
  border-radius: 10px;
  background-color: #f9f9f9;
}
h1 {
  text-align: center;
  color: #333;
  margin-bottom: 20px;
}
.form-group {
  margin-bottom: 15px;
}
label {
  display: block;
  margin-bottom: 5px;
  font-weight: bold;
}
input {
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
}
button {
  width: 100%;
  padding: 10px;
  background-color: #007bff;
  color: white;
  border: none;
  border-radius: 5px;
  cursor: pointer;
}
button:hover {
  background-color: #0056b3;
}
.error-message {
  color: red;
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
}
.success-message {
  color: green;
  font-weight: bold;
  text-align: center;
  margin-top: 10px;
}
</style>
