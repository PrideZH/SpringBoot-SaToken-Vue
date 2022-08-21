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
  return appStore.isCollapse ? (appStore.isMobile ? 0 : 64) : 200;
});

const clickMenuItem = (route: string) => {
  router.push(route);
  if (appStore.isMobile) {
    appStore.isCollapse = true;
  }
}
</script>

<template>
  <div class="container">
    <div class="masking" v-if="appStore.isMobile" v-show="!appStore.isCollapse" @click="appStore.isCollapse = true"/>
    <el-aside :class="[ 'aside', { 'aside-mobile': appStore.isMobile } ]" :width="menuWidth + 'px'">
      <div class="logo">
        <img src="/favicon.ico" />
        <span v-if="!isCollapse" style="flex-shrink: 0; margin-left: 8px;">Admin</span>
      </div>
      <div class="line" />
      <el-menu
        background-color="#222832"
        text-color="#fff"
        :collapse="isCollapse"
        :collapse-transition="false"
      >
        <template v-for="subMenu in rootRoute.children">
          <el-sub-menu v-if="subMenu.children" :index="`/${subMenu.path}`">
            <template #title>
              <el-icon v-if="subMenu.meta?.icon"><svg viewBox="0 0 1024 1024" >
                <path v-for="(iconSvg, index) in subMenu.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
              </svg></el-icon>
              <span>{{ $t(subMenu.meta?.locale as string) }}</span>
            </template>
            <el-menu-item v-for="menuItem in subMenu.children" :key="menuItem.path" :index="`/${subMenu.path}/${menuItem.path}`" @click="clickMenuItem(`/${subMenu.path}/${menuItem.path}`)">
              <el-icon v-if="menuItem.meta?.icon"><svg viewBox="0 0 1024 1024" >
                <path v-for="(iconSvg, index) in menuItem.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
              </svg></el-icon>
              <span>{{ $t(menuItem.meta?.locale as string) }}</span>
            </el-menu-item>
          </el-sub-menu>
          <el-menu-item v-else :index="`/${subMenu.path}`" @click="clickMenuItem(`/${subMenu.path}`)">
            <el-icon v-if="subMenu.meta?.icon"><svg viewBox="0 0 1024 1024" >
              <path v-for="(iconSvg, index) in subMenu.meta?.icon" :key="index" fill="currentColor" :d="iconSvg"></path>
            </svg></el-icon>
            <span>{{ $t(subMenu.meta?.locale as string) }}</span>
          </el-menu-item>
        </template>
      </el-menu>
    </el-aside>
    <div class="content" :style="{ marginLeft: appStore.isMobile ? 0 : menuWidth + 'px' }">
      <el-header class="header"><Header /></el-header>
      <el-main><router-view /></el-main>
    </div>
  </div>
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
  height: 100%;
}

.masking {
  width: 100%;
  height: 100%;
  background: #000;
  opacity: .3;
  top: 0;
  position: absolute;
  z-index: 64;
}

.aside {
  position: fixed;
  height: 100%;
  background-color: #222832;
  overflow: hidden;
  overflow-y: auto;
  -ms-overflow-style: none;
  overflow: -moz-scrollbars-none;
  transition: width 0.5s;
  z-index: 128;
}

.aside-mobile {
  position: absolute;
}

.content {
  min-width: 512px;
  transition: margin 0.5s;
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
