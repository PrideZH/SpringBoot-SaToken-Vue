<script setup lang="ts">
defineProps({
  content: {
    type: String,
    default: ''
  }
});
</script>

<template>
  <span class="icon-btn">
    <el-tooltip :disabled="content === ''" effect="dark" :content="content" placement="bottom">
      <slot />
    </el-tooltip>
  </span>
</template>

<style scoped>
.icon-btn {
  display: flex;
  align-items: center;
  justify-content: center;
  padding: 8px;
  cursor: pointer;
}

.icon-btn:hover {
  background-color: #f6f6f6;
}
</style>
