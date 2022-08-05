import { BaseResp } from '@/types';
import axios from 'axios';

export interface PermissionResp extends BaseResp {
  name: string;
  code: string;
}

const permissionApi = {

  list () {
    return axios.get<PermissionResp[]>('/api/sys-permission');
  }

}

export default permissionApi;
