import { BaseResp, Page } from '@/types';
import axios from 'axios';
import { PermissionResp } from './permission';

export interface RoleReq {
  name: string;
  permissionIds: string[];
}

export interface RoleResp extends BaseResp {
  name: string;
  code: string;
  permissions: PermissionResp[];
}

const roleApi = {

  create (data: RoleReq) {
    return axios.post('/api/sys-role', data);
  },

  page (page: number, size: number) {
    return axios.get<Page<RoleResp>>('/api/sys-role/page', { params: { page, size } });
  },

  list () {
    return axios.get<RoleResp[]>('/api/sys-role');
  },

  update (id: string, data: RoleReq) {
    return axios.put(`/api/sys-role/${id}`, data);
  },

  del (id: string) {
    return axios.delete(`/api/sys-role/${id}`);
  }

}

export default roleApi;
