<template>
  <div class="login-container">
    <h1>Iniciar Sesión</h1>
    <form @submit.prevent="loginUser">
      <div class="form-group">
        <label for="username">Nombre de Usuario:</label>
        <input
          type="text"
          id="username"
          v-model="user.name"
          required
          placeholder="Ingrese su nombre de usuario"
        />
      </div>
      <div class="form-group">
        <label for="password">Contraseña:</label>
        <input
          type="password"
          id="password"
          v-model="user.password"
          required
          placeholder="Ingrese su contraseña"
        />
      </div>
      <button type="submit">Iniciar Sesión</button>
    </form>
    <p v-if="message" :class="{'error-message': !success, 'success-message': success}">
      {{ message }}
    </p>
  </div>
</template>

<script>
import axios from "axios";

export default {
  name: "LoginView",
  data() {
    return {
      user: {
        name: "",
        password: "",
      },
      message: "",
      success: false,
    };
  },
  methods: {
    async loginUser() {
      try {
        const response = await axios.post("http://localhost:8080/api/user/login", this.user);
        if (response.status === 200) {
          this.message = "Inicio de sesión exitoso. ¡Bienvenido!";
          this.success = true;
          this.$router.push("/");
        } else {
          this.message = "Nombre de usuario o contraseña incorrectos.";
          this.success = false;
        }
      } catch (error) {
        console.error(error);
        this.message = "Ocurrió un error al iniciar sesión. Intente de nuevo más tarde.";
        this.success = false;
      }
    },
  },
};
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
