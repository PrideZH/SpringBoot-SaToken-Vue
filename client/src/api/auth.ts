import { BaseResp } from '@/types';
import axios from 'axios';

export interface LoginReq {
  username: string,
  password: string
}

export interface TokenResp {
  name: string;
  value: string;
}

export interface UserResp extends BaseResp {
  username: string;
  nickname: string;
  superAdmin: string;
}

export interface UserInfoResp extends BaseResp {
  username: string;
  nickname: string;
  avatarUrl: string;
  superAdmin: string;
  permissions: string[];
}

const authApi = {

  login(data: LoginReq) {
    return axios.post<TokenResp>('/api/sys-auth/login', data);
  },

  // export function refreshToken() {
  //   return axios.post('/api/admin/refreshToken');
  // }

  logout() {
    return axios.post('/api/sys-auth/logout');
  },

  /**
   * 获取当前用户信息
   */
  info() {
    return axios.get<UserInfoResp>('/api/sys-auth/info');
  },

}

export default authApi;
