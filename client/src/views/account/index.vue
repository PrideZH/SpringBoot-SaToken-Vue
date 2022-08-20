<script setup lang="ts">
import userApi from '@/api/user';
import { useUserStore } from '@/store';
import { ElMessage } from 'element-plus';
import AvatarUpload from './components/AvatarUpload.vue';

const userStore = useUserStore();

const uploadAvatar = (file: any) => {
  let formData = new FormData()
  formData.append('file', file)
  
  userApi.updateAvatar(formData).then(res => {
    if (userStore.userInfo) {
      userStore.userInfo.avatarUrl = res.data;
    }
    ElMessage.success('修改成功');
  });
};
</script>

<template>
  <el-row :gutter="16">
    <el-col class="user-info-box" :span="6" :xs="{span: 24}">
      <el-card>
        <template #header>个人信息</template>
        <AvatarUpload :avatar="userStore.userInfo?.avatarUrl" @upload="uploadAvatar"/>
        <ul class="user-info">
          <li>
            <span class="user-info-key">登录账号</span>
            <span class="user-info-value">{{ userStore.userInfo?.username }}</span>
          </li>
          <li>
            <span class="user-info-key">用户昵称</span>
            <span class="user-info-value">{{ userStore.userInfo?.nickname }}</span>
          </li>
        </ul>
      </el-card>
    </el-col>
    <el-col :span="18" :xs="{span: 24}">
      <el-card>
        <template #header>修改信息</template>
      </el-card>
    </el-col>
  </el-row>
  
</template>

<style scoped>
.user-info-box {
  margin-bottom: 16px;
}

.user-info {
  padding-left: 0;
  list-style: none;
}

.user-info li {
  border-bottom: 1px solid #f0f3f4;
  padding: 12px 0;
  font-size: 12px;
}

.user-info-value {
  float: right;
}
</style>