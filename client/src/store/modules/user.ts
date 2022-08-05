import autoApi, { UserInfoResp } from '@/api/auth';
import { defineStore } from 'pinia';
import { ref } from 'vue';

export const useUserStore = defineStore('userStore', () => {
  const userInfo = ref<UserInfoResp | null>(null);

  const getUserInfo = async (): Promise<UserInfoResp> => {
    if (userInfo.value !== null) return userInfo.value;
    return await autoApi.info().then(res => {
      return userInfo.value = res.data;
    });
  }

  return { userInfo, getUserInfo };
});
