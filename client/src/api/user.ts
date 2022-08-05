import axios from 'axios';
import { Page, BaseResp } from '@/types';
import { RoleResp } from './role';

export interface UserReq {
  username: string;
  password: string;
  nickname: string;
  roleIds: string[];
}

export interface UserResp extends BaseResp {
  username: string;
  nickname: string;
  superAdmin: string;
  roles: RoleResp[];
}
const userApi = {

  create (data: UserReq) {
    return axios.post('/api/sys-user', data);
  },

  page (page: number, size: number) {
    return axios.get<Page<UserResp>>('/api/sys-user/page', { params: { page, size } });
  },

  update (id: string, data: UserReq) {
    return axios.put(`/api/sys-user/${id}`, data);
  },

  del (id: string) {
    return axios.delete(`/api/sys-user/${id}`);
  }

}

export default userApi;
