import Vue from 'vue'
import App from './App.vue'
import router from './router'
import store from './store'
import VueResource from 'vue-resource'
import ElementUI from 'element-ui'
import 'element-ui/lib/theme-chalk/index.css'
import axios from 'axios'
import 'animate.css'
Vue.config.productionTip = false
Vue.use(ElementUI);
Vue.use(VueResource);
Vue.prototype.$axios = axios

axios.defaults.baseURL = 'http://localhost:9001'


new Vue({
    router,
    store,
    render: h => h(App)
}).$mount('#app')
