<script setup lang="ts">
import Header from './components/Header.vue';
import { computed } from 'vue';
import { useAppStore } from '@/store';
import { RouteRecordNormalized, useRouter } from 'vue-router';

const router = useRouter();
const rootRoute: RouteRecordNormalized = router.getRoutes().find((el) => el.name === 'root') as RouteRecordNormalized;

const appStore = useAppStore();
const isCollapse = computed(() => appStore.isCollapse);
const menuWidth = computed(() => {
  return appStore.isCollapse ? 64 : 200;
});
</script>

<template>
  <el-container class="container">
    <el-aside class="aside" :width="menuWidth + 'px'">
      <div class="logo">
        <img src="/favicon.ico" />
        <span v-if="!isCollapse" style="flex-shrink: 0; margin-left: 8px;">Admin</span>
      </div>
      <div class="line" />
      <el-menu
        background-color="#222832"
        text-color="#fff"
        :collapse="isCollapse"
        :router="true"
        :collapse-transition="false"
      >
        <template v-for="subMenu in rootRoute.children">
          <el-sub-menu v-if="subMenu.children" :index="`/${subMenu.path}`">
            <template #title>
              <el-icon v-if="subMenu.meta?.icon"><svg viewBox="0 0 1024 1024" >
                <path v-for="(iconSvg, index) in subMenu.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
              </svg></el-icon>
              <span>{{ subMenu.meta?.locale }}</span>
            </template>
            <el-menu-item v-for="menuItem in subMenu.children" :key="menuItem.path" :index="`/${subMenu.path}/${menuItem.path}`">
              <el-icon v-if="menuItem.meta?.icon"><svg viewBox="0 0 1024 1024" >
                <path v-for="(iconSvg, index) in menuItem.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
              </svg></el-icon>
              <span>{{ menuItem.meta?.locale }}</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="`/${subMenu.path}`">
            <el-icon v-if="subMenu.meta?.icon"><svg viewBox="0 0 1024 1024" >
              <path v-for="(iconSvg, index) in subMenu.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
            </svg></el-icon>
            <span>{{ subMenu.meta?.locale }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <el-container class="content">
      <el-header class="header"><Header /></el-header>
      <el-main><router-view /></el-main>
    </el-container>
  </el-container>
</template>

<style>
.el-menu {
  border-right: none !important;
}
.el-sub-menu {
  border-top: 1px solid hsla(0, 0%, 100%, .05);
  border-bottom: 1px solid rgba(0, 0, 0, .2);
}
</style>

<style scoped>
.logo {
  display: flex;
  align-items: center;
  justify-content: center;
  height: 60px;
  font-size: 20px;
  color: #fff;
}

.container {
  height: 100vh;
}

.aside {
  background-color: #222832;
  overflow: hidden;
  overflow-y: auto;
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
  transition: width 0.5s;
}

.header {
  padding: 0 4px;
  border-bottom: 1px solid #eee;
}

.line {
  border-top: 1px solid hsla(0,0%,100%,.05);
  border-bottom: 1px solid rgba(0,0,0,.2);
}
</style>
