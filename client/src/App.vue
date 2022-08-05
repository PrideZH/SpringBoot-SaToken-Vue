<script setup lang="ts">
import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import { ElConfigProvider } from 'element-plus';
import { ref } from 'vue';

const locale = ref(zhCn);
</script>

<template>
  <el-config-provider :locale="locale">
    <router-view />
  </el-config-provider>
</template>

<style>
body {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

body, #app {
  height: 100vh;
}
</style>
