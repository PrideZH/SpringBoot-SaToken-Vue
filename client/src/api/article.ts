import { BaseResp, Page } from '@/types';
import axios from 'axios';

export interface ArticleReq {
  content: string;
}

export interface ArticleResp extends BaseResp {
  content: string;
}

const articleApi = {

  create (data: ArticleReq) {
    return axios.post('/api/article', data);
  },

  page (page: number, size: number) {
    return axios.get<Page<ArticleResp>>('/api/article/page', { params: { page, size } });
  },

  update (id: string, data: ArticleReq) {
    return axios.put(`/api/article/${id}`, data);
  },

  del (id: string) {
    return axios.delete(`/api/article/${id}`);
  }

}

export default articleApi;
