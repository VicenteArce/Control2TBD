<template>
  <nav>
    <router-link to="/">Home</router-link> 
    <router-link to="/tasks" v-if="auth.isLogged()">Mis Tareas</router-link> 
    
    <!-- Mostrar Login y Register solo si el usuario no está logueado -->
    <router-link to="/login" v-if="!auth.isLogged()">Login</router-link> 
    <router-link to="/register" v-if="!auth.isLogged()">Register</router-link> 

    <!-- Mostrar Logout solo si el usuario está logueado -->
    <router-link to="/" @click="logout" v-if="auth.isLogged()">Logout</router-link> 
  </nav>
  <router-view/>
</template>

<script>
import { auth } from "@/services/authService";

export default {
	name: "App",
  setup() {
    return {
      auth
    }
  },

  methods: {
    getUser() {
      this.user = sessionStorage.getItem('user');  // Recupera el usuario de sessionStorage
    },
    logout() {
      auth.logout();  // Llama al método logout del servicio
      this.$router.push('/login');  // Redirige al login después de hacer logout
    }
  }
};
</script>

<style>
#app {
  font-family: Avenir, Helvetica, Arial, sans-serif;
  -webkit-font-smoothing: antialiased;
  -moz-osx-font-smoothing: grayscale;
  text-align: center;
  color: #2c3e50;
}

nav {
  padding: 30px;
}

nav a {
  font-weight: bold;
  color: #2c3e50;
  margin-right: 15px; /* Espacio entre los enlaces */
}

nav a.router-link-exact-active {
  color: #42b983;
}
</style>
