<script setup lang="ts">
import { onMounted, ref } from 'vue';
import { Page } from '@/types';
import userApi, { UserResp, UserReq } from '@/api/user';
import { hasPerm } from '@/utils/auth';
import { ElMessage } from 'element-plus';
import roleApi, { RoleResp } from '@/api/role';

const users = ref<Page<UserResp>>();
const roles = ref<RoleResp[]>();
const currentPage = ref<number>(1);
const pageSize = ref<number>(10);

const dialogStatus = ref<'create' | 'update'>('create');
const dialogVisible = ref<boolean>(false);
const tempId = ref<string>('');
const tempUser = ref<UserReq>({
  username: '',
  password: '',
  nickname: '',
  roleIds: []
});

const getUsers = () => {
  userApi.page(currentPage.value, pageSize.value).then(res => {
    users.value = res.data;
  });
}

const showCreate = () => {
  tempUser.value = {
    username: '',
    password: '',
    nickname: '',
    roleIds: []
  };
  dialogStatus.value = 'create';
  dialogVisible.value = true;
}

const showUpdate = (user: UserResp) => {
  tempId.value = user.id;
  tempUser.value = {
    username: user.username,
    password: '',
    nickname: user.nickname,
    roleIds: user.roles.map(role => role.id)
  };
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}

const createUser = () => {
  userApi.create(tempUser.value).then(res => {
    ElMessage.success('添加成功');
    getUsers();
    dialogVisible.value = false;
  });
}

const updateUser = () => {
  userApi.update(tempId.value, tempUser.value).then(res => {
    ElMessage.success('修改成功');
    getUsers();
    dialogVisible.value = false;
  });
}

const deleteUser = (id: string) => {
  userApi.del(id).then(res => {
    ElMessage.success('删除成功');
    getUsers();
  });
}

onMounted(() => {
  getUsers();
  roleApi.list().then(res => roles.value = res.data);
});
</script>

<template>
  <!-- <el-card class="table-query">
    <el-form :inline="true">
      <el-form-item label="用户名">
        <el-input placeholder="请输入"></el-input>
      </el-form-item>
      <el-form-item label="昵称">
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
      <el-button v-if="hasPerm('sys:user:add')" type="success" @click="showCreate">{{ $t('common.add') }}</el-button>
    </div>
    <el-table :data="users?.records" size="small">
      <el-table-column prop="nickname" label="昵称" align="center" />
      <el-table-column prop="username" label="用户名" align="center" />
      <el-table-column label="角色" align="center">
        <template #default="scope">
          <el-space wrap>
            <el-tag v-if="scope.row.superAdmin" type="warning">超级管理员</el-tag>
            <template v-else>
              <el-tag v-for="role in scope.row.roles">{{ role.name }}</el-tag>
            </template>
          </el-space>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="140"/>
      <el-table-column prop="updateTime" label="修改时间" align="center" width="140"/>
      <el-table-column fixed="right" label="操作" align="center" width="128">
        <template #default="scope">
          <el-button v-if="!scope.row.superAdmin && hasPerm('sys:user:put')" type="primary" size="small" @click="showUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
          <el-popconfirm v-if="!scope.row.superAdmin && hasPerm('sys:user:del')" title="是否确定删除?" @confirm="deleteUser(scope.row.id)">
            <template #reference>
              <el-button type="danger" size="small" >{{ $t('common.delete') }}</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>
    <el-pagination
      v-model:current-page="currentPage"
      :page-size="pageSize"
      :total="users?.total || 0"
    />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="dialogStatus === 'create' ? '创建用户' : '编辑'">
    <el-form :model="tempUser" label-position="left" label-width="80px">
      <el-form-item label="用户名" required v-if="dialogStatus==='create'">
        <el-input type="text" v-model="tempUser.username" />
      </el-form-item>
      <el-form-item label="密码" v-if="dialogStatus==='create'" required>
        <el-input type="password" v-model="tempUser.password" />
      </el-form-item>
      <el-form-item label="新密码" v-else>
        <el-input type="password" v-model="tempUser.password" placeholder="不填则表示不修改" />
      </el-form-item>
      <el-form-item label="角色" required>
        <el-select v-model="tempUser.roleIds" multiple placeholder="支持多角色">
          <el-option v-for="role in roles" :label="role.name" :value="role.id" />
        </el-select>
      </el-form-item>
      <el-form-item label="昵称" required>
        <el-input type="text" v-model="tempUser.nickname" />
      </el-form-item>
    </el-form>
    <template #footer>
      <el-button v-if="dialogStatus==='create'" type="success" @click="createUser">创 建</el-button>
      <el-button type="primary" v-else @click="updateUser">修 改</el-button>
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
