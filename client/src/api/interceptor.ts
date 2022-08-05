import axios, { AxiosRequestConfig, AxiosResponse } from 'axios';
import { clearToken, getToken } from '@/utils/auth';
import { ElMessage } from 'element-plus';
import { TokenResp } from './auth';
import router from '@/router';

export const TIMEOUT: number = 30 * 1000;

export interface HttpResponse<T = unknown> {
  code: number;
  data: T;
  message: string;
}

axios.defaults.baseURL = import.meta.env.VITE_APP_BASE_URL as string;
axios.defaults.timeout = TIMEOUT;

axios.interceptors.request.use(
  (config: AxiosRequestConfig) => {
    const token: TokenResp | null = getToken();
    if (config.headers) {
      if (token) config.headers[token.name] = token.value;
    }
    return config;
  },
  (error) => {
    return Promise.reject(error);
  }
);

axios.interceptors.response.use(
  (response: AxiosResponse<HttpResponse>) => {
    const res: HttpResponse<unknown> = response.data;
    if (res.code !== 200) {
      ElMessage.error(`[${res.code}] ${res.message}`);
      // 显示消息错误信息
      if (res.data) {
        (<string[]>res.data).forEach(item => ElMessage.error(item));
      }
      // 401: token 无效; 50012: Other clients logged in; 50014: Token expired;
      if ([401].includes(res.code)) {
        clearToken();
        router.push('/login');
      }
      return Promise.reject(new Error(res.message || 'Error'));
    }
    return res;
  },
  (error) => {
    const { response, message } = error;
    if (response && response.data) {
      const { status, data } = response
      ElMessage.error(`[${status}] ${data.msg || message}`);
    } else {
      let tips = null;
      if (message === 'Network Error') {
        tips = '后端接口连接异常';
      }
      ElMessage.error(tips || `后端接口未知异常`);
    }
    return Promise.reject(error)
  }
);
