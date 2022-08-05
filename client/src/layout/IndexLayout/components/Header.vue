<script setup lang="ts">
import authApi, { UserInfoResp, UserResp } from '@/api/auth';
import IconBtn from '@/components/IconBtn.vue';
import router from '@/router';
import { useAppStore, useUserStore } from '@/store';
import { Expand, Fold, Location, SwitchButton } from '@element-plus/icons-vue';
import { computed, onMounted, ref } from 'vue';

const userStore = useUserStore();

const userInfo = ref<UserInfoResp | null>(null);

const name = ref<string>('Dashboard');

const appStore = useAppStore();
const isCollapse = computed(() => appStore.isCollapse);

const toggleCollapse = (): void => {
  appStore.updateSettings({isCollapse: !isCollapse.value});
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
          <Expand v-if="isCollapse" />
          <Fold v-else />
        </el-icon>
      </IconBtn>
      <span>{{ name }}</span>
    </div>
    <div class="right">
      <el-dropdown trigger="click">
        <IconBtn style="height: 40px;"><el-icon><Location /></el-icon></IconBtn>
        <template #dropdown>
          <el-dropdown-menu>
            <el-dropdown-item>简体中文</el-dropdown-item>
            <el-dropdown-item>English</el-dropdown-item>
          </el-dropdown-menu>
        </template>
      </el-dropdown>
      <el-popover trigger="click">
        <template #reference>
          <div class="author">{{ userInfo?.username || '' }}</div>
        </template>
        <div class="nickname">
          <p>{{ userInfo?.nickname || '' }}</p>
          <div class="btn-item" @click="logout"><el-icon><SwitchButton /></el-icon>退出</div>
        </div>
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
  margin: 0 32px 0 10px;
  cursor: pointer;
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
