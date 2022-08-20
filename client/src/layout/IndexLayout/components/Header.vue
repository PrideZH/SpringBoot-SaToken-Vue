<script setup lang="ts">
import authApi, { UserInfoResp, UserResp } from '@/api/auth';
import IconBtn from '@/components/IconBtn.vue';
import router from '@/router';
import { useAppStore, useUserStore } from '@/store';
import { CaretBottom, Expand, Fold, Location, SwitchButton, User } from '@element-plus/icons-vue';
import { onMounted, ref } from 'vue';

const appStore = useAppStore();
const userStore = useUserStore();

const userInfo = ref<UserInfoResp | null>(null);

const toggleCollapse = (): void => {
  appStore.isCollapse = !appStore.isCollapse;
};

const logout = () => {
  authApi.logout().then(res => {
    localStorage.removeItem('token');
    userStore.userInfo = null; // 清空用户信息
    router.push('/login');
  })
}

onMounted(async () => {
  userInfo.value = await userStore.getUserInfo();
});
</script>

<template>
  <div class="header">
    <div class="left">
      <IconBtn style="height: 40px;" @click="toggleCollapse">
        <el-icon :size="24">
          <Expand v-if="appStore.isCollapse" />
          <Fold v-else />
        </el-icon>
      </IconBtn>
      <span>{{ $t(router.currentRoute.value.meta.locale as string || 'default') }}</span>
    </div>
    <div class="right">
      <el-dropdown trigger="click">
        <IconBtn style="height: 40px;"><el-icon><Location /></el-icon></IconBtn>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item @click="appStore.setLang('zhCn')">简体中文</el-dropdown-item>
            <el-dropdown-item @click="appStore.setLang('enUs')">English</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-popover trigger="click">
        <template #reference>
          <div class="author">
            <div class="author-img"><el-avatar :src="userInfo?.avatarUrl" :size="32"/></div>
            <el-icon class="author-down"><CaretBottom /></el-icon>
          </div>
        </template>
        <div class="nickname">{{ userInfo?.nickname || '' }}</div>
        <div class="btn-item" @click="router.push('/account')"><el-icon><User /></el-icon>个人中心</div>
        <div class="btn-item" @click="logout"><el-icon><SwitchButton /></el-icon>退出</div>
      </el-popover>
    </div>
  </div>
</template>

<style scoped>
.header {
  height: 100%;
  display: flex;
  justify-content: space-between;
  align-items: center;
}

.left, .right {
  display: flex;
  align-items: center;
}

.author {
  display: flex;
  align-items: baseline;
  margin: 0 32px 0 10px;
  cursor: pointer;
}

.author-img {
  padding: 4px;
}

.author:hover .author-img {
  background-color: #f5f5f5;
}

.author-down {
  margin-left: 8px;
  font-size: 12px;
  color: #888;
}

.nickname {
  padding: 4px;
  text-align: center;
}

.btn-item {
  display: flex;
  align-items: center;
  width: 100%;
  padding: 8px;
  box-sizing: border-box;
  cursor: pointer;
}

.btn-item >>> .el-icon {
  margin-right: 4px;
}

.btn-item:hover {
  background-color: #f5f5f5;
}
</style>
