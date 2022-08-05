<script setup lang="ts">
import articleApi, { ArticleReq, ArticleResp } from '@/api/article';
import permissionApi, { PermissionResp } from '@/api/permission';
import { onMounted, ref } from 'vue';
import { Page } from '@/types';
import { hasPerm } from '@/utils/auth';
import { ElMessage } from 'element-plus';

const articles = ref<Page<ArticleResp>>();
const currentPage = ref<number>(1);
const pageSize = ref<number>(10);

const dialogStatus = ref<'create' | 'update'>('create');
const dialogVisible = ref<boolean>(false);
const tempId = ref<string>('');
const tempArticle = ref<ArticleReq>({
  content: ''
});

const getArticles = () => {
  articleApi.page(currentPage.value, pageSize.value).then(res => {
    articles.value = res.data;
  });
}

const showCreate = () => {
  tempArticle.value = {
    content: '',
  };
  dialogStatus.value = 'create';
  dialogVisible.value = true;
}

const showUpdate = (article: ArticleResp) => {
  tempId.value = article.id;
  tempArticle.value = {
    content: article.content,
  };
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}

const createArticle = () => {
  articleApi.create(tempArticle.value).then(res => {
    ElMessage.success('添加成功');
    getArticles();
    dialogVisible.value = false;
  });
}

const updateArticle = () => {
  articleApi.update(tempId.value, tempArticle.value).then(res => {
    ElMessage.success('修改成功');
    getArticles();
    dialogVisible.value = false;
  });
}

const deleteArticle = (id: string) => {
  articleApi.del(id).then(res => {
    ElMessage.success('删除成功');
    getArticles();
  });
}

onMounted(() => {
  getArticles();
});
</script>

<template>
  <!-- <el-card class="table-query">
    <el-form :inline="true">
      <el-form-item label="搜索">
        <el-input placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item>
        <el-button>重置</el-button>
        <el-button type="primary">查询</el-button>
      </el-form-item>
    </el-form>
  </el-card> -->
  <el-card>
    <div class="table-operate">
      <el-button v-if="hasPerm('article:add')" type="success" @click="showCreate">添加</el-button>
    </div>
    <el-table :data="articles?.records" size="small">
      <el-table-column prop="content" label="文章" align="center" />
      <el-table-column prop="createTime" label="创建时间" align="center" />
      <el-table-column prop="updateTime" label="修改时间" align="center" />
      <el-table-column fixed="right" label="操作" align="center">
        <template #default="scope">
          <el-button v-if="hasPerm('article:put')" type="primary" size="small" @click="showUpdate(scope.row)">修改</el-button>
          <el-popconfirm title="是否确定删除?" @confirm="deleteArticle(scope.row.id)">
            <template #reference>
              <el-button v-if="hasPerm('article:del')" type="danger" size="small" >删除</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:currentPage="currentPage"
      :page-size="pageSize"
      :total="articles?.total"
    >
    </el-pagination>
  </el-card>

  <el-dialog v-model="dialogVisible" :title="dialogStatus === 'create' ? '创建文章' : '编辑'">
    <el-form :model="tempArticle" label-position="left" label-width="80px">
      <el-form-item label="文章" required>
        <el-input v-model="tempArticle.content" type="textarea" show-word-limit maxlength="200" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button v-if="dialogStatus==='create'" type="success" @click="createArticle">创 建</el-button>
      <el-button type="primary" v-else @click="updateArticle">修 改</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.table-query {
  margin-bottom: 16px;
}

.table-query >>> .el-card__body {
  padding-bottom: 0;
}
</style>
