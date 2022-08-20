<script setup lang="ts">
import roleApi, { RoleReq, RoleResp } from '@/api/role';
import permissionApi, { PermissionResp } from '@/api/permission';
import { onMounted, ref } from 'vue';
import { Page } from '@/types';
import { hasPerm } from '@/utils/auth';
import { ElMessage } from 'element-plus';

const roles = ref<Page<RoleResp>>();
const permissions = ref<PermissionResp[]>([]);
const currentPage = ref<number>(1);
const pageSize = ref<number>(10);

const dialogStatus = ref<'create' | 'update'>('create');
const dialogVisible = ref<boolean>(false);
const tempId = ref<string>('');
const tempRole = ref<RoleReq>({
  name: '',
  permissionIds: []
});

const getRoles = () => {
  roleApi.page(currentPage.value, pageSize.value).then(res => {
    roles.value = res.data;
  });
}

const showCreate = () => {
  tempRole.value = {
    name: '',
    permissionIds: []
  };
  dialogStatus.value = 'create';
  dialogVisible.value = true;
}

const showUpdate = (role: RoleResp) => {
  tempId.value = role.id;
  tempRole.value = {
    name: role.name,
    permissionIds: role.permissions.map(permission => permission.id)
  };
  dialogStatus.value = 'update';
  dialogVisible.value = true;
}

const createRole = () => {
  roleApi.create(tempRole.value).then(res => {
    ElMessage.success('添加成功');
    getRoles();
    dialogVisible.value = false;
  });
}

const updateRole = () => {
  roleApi.update(tempId.value, tempRole.value).then(res => {
    ElMessage.success('修改成功');
    getRoles();
    dialogVisible.value = false;
  });
}

const deleteRole = (id: string) => {
  roleApi.del(id).then(res => {
    ElMessage.success('删除成功');
    getRoles();
  });
}

onMounted(() => {
  getRoles();
  if (hasPerm('sys:permission:get')) {
    permissionApi.list().then(res => permissions.value = res.data);
  }
});
</script>

<template>
  <!-- <el-card class="table-query">
    <el-form :inline="true">
      <el-form-item label="名称">
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
      <el-button v-if="hasPerm('sys:role:add')" type="success" @click="showCreate">{{ $t('common.add') }}</el-button>
    </div>
    <el-table :data="roles?.records" size="small">
      <el-table-column prop="name" label="名称" align="center" />
      <el-table-column prop="permission" label="权限" align="center">
        <template #default="scope">
          <el-space wrap>
            <el-tag v-for="permission in scope.row.permissions">{{ permission.name }}</el-tag>
          </el-space>
        </template>
      </el-table-column>
      <el-table-column prop="createTime" label="创建时间" align="center" width="140"/>
      <el-table-column prop="updateTime" label="修改时间" align="center" width="140"/>
      <el-table-column fixed="right" label="操作" align="center" width="128">
        <template #default="scope">
          <el-button v-if="hasPerm('sys:role:put')" type="primary" size="small" @click="showUpdate(scope.row)">{{ $t('common.edit') }}</el-button>
          <el-popconfirm title="是否确定删除?" @confirm="deleteRole(scope.row.id)">
            <template #reference>
              <el-button v-if="hasPerm('sys:role:del')" type="danger" size="small" >{{ $t('common.delete') }}</el-button>
            </template>
          </el-popconfirm>
        </template>
      </el-table-column>
    </el-table>

    <el-pagination
      layout="total, sizes, prev, pager, next, jumper"
      small="small"
      v-model:current-page="currentPage"
      v-model:page-size="pageSize"
      :page-sizes="[10, 20, 30, 40]"
      :total="roles?.total || 0"
    />
  </el-card>

  <el-dialog v-model="dialogVisible" :title="dialogStatus === 'create' ? '创建角色' : '编辑'">
    <el-form :model="tempRole" label-position="left" label-width="80px">
      <el-form-item label="名称" required>
        <el-input type="text" v-model="tempRole.name" />
      </el-form-item>
      <el-form-item label="权限" required>
          <template v-for="permission in permissions">
            <el-checkbox v-model="tempRole.permissionIds" :label="permission.id">{{ permission.name }}</el-checkbox>
          </template>
        </el-form-item>
    </el-form>
    <template #footer>
      <el-button v-if="dialogStatus==='create'" type="success" @click="createRole">创 建</el-button>
      <el-button type="primary" v-else @click="updateRole">修 改</el-button>
    </template>
  </el-dialog>
</template>

<style scoped>
.el-table {
  margin-bottom: 16px;
}
</style>
