import App from './App.vue';
import ElementPlus from 'element-plus';
import router from './router/index';
import store from './store';
import { createApp } from 'vue';
import '@/api/interceptor';

import 'element-plus/theme-chalk/el-message.css';
import 'element-plus/theme-chalk/el-form.css';

const app = createApp(App);

app.use(ElementPlus);
app.use(router);
app.use(store);

app.mount('#app');
