import { TokenResp, UserInfoResp } from "@/api/auth";
import { useUserStore } from "@/store";

const isLogin = (): boolean => {
  return !!localStorage.getItem(localStorage.getItem('token-header') || 'token');
};

const getToken = (): TokenResp | null => {
  const tokenHeader: string = localStorage.getItem('token-header') || 'token';
  const token: string | null = localStorage.getItem(tokenHeader);
  if (token === null) return null;
  return { name: tokenHeader, value: token };
};

const setToken = (token: TokenResp) => {
  localStorage.setItem('token-header', token.name);
  localStorage.setItem(token.name, token.value);
};

const clearToken = () => {
  localStorage.removeItem(localStorage.getItem('token-header') || 'token');
};

/**
 * 判断当前用户是否拥有指定权限
 */
const hasPerm = (permission: string): boolean => {
  const userStore = useUserStore();
  if (userStore.userInfo === null) return false;
  return (<UserInfoResp>userStore.userInfo).permissions.indexOf(permission) > -1;
}

export { isLogin, getToken, setToken, clearToken, hasPerm };
