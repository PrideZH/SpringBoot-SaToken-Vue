<script setup lang="ts">
import { ref } from 'vue';
import { ElMessage } from 'element-plus'

const props = defineProps({
  avatar: {
    type: String,
    require: true,
  }
});

// upload: 点击保存修改按钮后回调展示头像的二进制格式
const emit = defineEmits(['upload']);

const file = ref();

const dialogFileVisible = ref(false);
const isChoice = ref<boolean>(false);

// 展示头像
const showAvatar = ref(props.avatar);

// 更新展示头像
const setAvatar = (str: string | undefined) => {
    if (str && str.substring(0, 23) === 'data:image/jpeg;base64,') {
        showAvatar.value = str;
    } else {
        showAvatar.value = 'data:image/jpeg;base64,' + str;
    }
}

let fileValue: any = null;
const handleFileChange = (e: any) => {
  const input = e.target;
  const files = e.target.files;
  let file: any = null;
  if(files && files[0]) {
    file = files[0];
    if (['image/jpeg', 'image/png'].indexOf(file.type) === -1) {
      ElMessage.error('图片格式错误');
      return false;
    }
    if (file.size > 1024 * 500) {
      ElMessage.error('图片大小不能超过500KB!');
      input.value = '';
      return false;
    }
    var reader = new FileReader();
    reader.readAsDataURL(file);
    // 文件读取完成时触发
    reader.onload = function () {
      isChoice.value = true;
      fileValue = file;
      setAvatar(reader.result as string);
    }
  } else {
    ElMessage.error('图片读取失败');
  }
}

const handleSave = () => {
    emit('upload', fileValue);
    dialogFileVisible.value = false;
}
</script>

<template>
  <div class="avatar-upload">
    <el-image class="avatar" :src="avatar" fit="cover" />
    <input @change="handleFileChange" class="file-input" ref="file" type="file" accept="image/jpeg" />
    <div class="cover" @click="dialogFileVisible = true">更换头像</div>
  </div>

  <el-dialog title="头像编辑" width="300px" v-model="dialogFileVisible">
    <el-image
    class="avatar-large"
    :src="showAvatar"
    fit="cover" />
    <template #footer>
    <span class="dialog-footer">
        <el-button @click="handleSave" :disabled="!isChoice">保存修改</el-button>
        <el-button type="primary" @click="file.click()">选择图片</el-button>
    </span>
    </template>
  </el-dialog>
</template>

<style scoped>
.avatar-upload {
  position: relative;
  display: block;
  width: 128px;
  height: 128px;
}

.cover {
  position: absolute;
  width: 100%;
  height: 100%;
  top: 0;
  left: 0;
  display: none;
  background-color: rgba(0, 0, 0, 0.5);
  text-align: center;
  line-height: 128px;
  color: #fff;
  cursor: pointer;
}

.avatar-upload:hover .cover {
  display: block;
}

.file-input {
  position: relative;
  display: none;
}

.avatar {
  width: 100%;
  height: 100%;
}

.avatar-large {
  width: 256px;
  height: 256px;
}
</style>