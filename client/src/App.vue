<script setup lang="ts">
import { ElConfigProvider } from 'element-plus';
import { useAppStore } from './store';

const appStore = useAppStore();
</script>

<template>
  <el-config-provider :locale="appStore.locale">
    <router-view />
  </el-config-provider>
</template>

<style>
* {
  padding: 0;
  margin: 0;
  box-sizing: border-box;
}

html, body, #app {
  height: 100%;
}
</style>
