<script setup lang="ts">
import { setToken } from '@/utils/auth';
import { ElForm, ElMessage } from 'element-plus';
import authApi, { LoginReq } from '@/api/auth';
import { reactive, ref } from 'vue';
import router from '@/router';

const formRef = ref<InstanceType<typeof ElForm>>();

const form = reactive<LoginReq>({
  username: 'admin',
  password: '123456'
});
const isLoading = ref<boolean>(false);

const rules = reactive({
  username: [
    {
      required: true,
      message: '请输入账号',
      trigger: 'blur',
    },
    {
      min: 4,
      max: 16,
      message: '长度必须在4到16之间',
      trigger: 'blur',
    },
  ],
  password: [
    {
      required: true,
      message: '请输入密码',
      trigger: 'blur',
    },
    {
      min: 6,
      max: 16,
      message: '长度必须在6到16之间',
      trigger: 'blur',
    },
  ]
});

const onSubmit = (): void => {
  if (!formRef.value) return;
  formRef.value.validate((valid: boolean | undefined) => {
    if (valid) {
      isLoading.value = true;
      authApi.login(form).then(res => {
        ElMessage.success('登录成功');
        setToken(res.data);
        router.push('/dashboard');
        isLoading.value = false;
      }).catch(err => {
        isLoading.value = false;
      });
    }
  })
};
</script>

<template>
  <el-container class="login-container">
    <el-header>Header</el-header>
    <el-main class="login-main">
      <div class="login-form">
        <h3>登录</h3>
        <el-form ref="formRef" :model="form" :rules="rules">
          <el-form-item prop="username">
            <el-input v-model="form.username" placeholder="账号" size="large"></el-input>
          </el-form-item>
          <el-form-item prop="password">
            <el-input v-model="form.password" placeholder="密码" size="large" show-password></el-input>
          </el-form-item>
          <el-form-item>
            <el-button style="width: 100%" type="primary" :loading="isLoading" @click="onSubmit" size="large">
              登录
            </el-button>
          </el-form-item>
        </el-form>
        <el-divider><span style="color: #878787;">其他登录方式</span></el-divider>
      </div>
    </el-main>
  </el-container>
</template>

<style scoped>
.login-container {
  height: 100%;
  background-color: #222832;
}

.login-main {
  padding: 120px;
}

.login-form {
  width: 400px;
  height: 260px;
  margin: auto;
  padding: 16px;
  text-align: center;
  background-color: #fff;
  border-radius: 8px;
}
</style>
