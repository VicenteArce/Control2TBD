<template>
  <nav>
    <router-link to="/">Home</router-link> 
    <router-link to="/tasks" v-if="isLoggedIn">Mis Tareas</router-link> 
    
    <!-- Mostrar Login y Register solo si el usuario no está logueado -->
    <router-link to="/login" v-if="!isLoggedIn">Login</router-link> 
    <router-link to="/register" v-if="!isLoggedIn">Register</router-link> 

    <!-- Mostrar Logout solo si el usuario está logueado -->
    <router-link to="/" @click="logout" v-if="isLoggedIn">Logout</router-link> 
  </nav>
  <router-view/>
</template>

<script>
import { auth } from "@/services/authService";

export default {
  data() {
    return {
      user: sessionStorage.getItem('user') || null,  // Establecer el valor inicial del usuario
      logged: sessionStorage.getItem('logged') == 1,  // Establecer estado de login
    };
  },
  computed: {
    // Computed property que depende de `logged` para actualizar la UI
    isLoggedIn() {
      return this.logged;
    }
  },
  methods: {
    getUser() {
      this.user = sessionStorage.getItem('user');  // Recupera el usuario de sessionStorage
    },
    logout() {
      auth.logout();  // Llama al método logout del servicio
      this.logged = false;  // Actualiza el estado de login
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
