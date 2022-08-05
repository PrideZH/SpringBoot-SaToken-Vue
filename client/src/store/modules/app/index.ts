import { defineStore } from 'pinia';
import { AppState } from './types';

export const useAppStore = defineStore('app', {
  state: (): AppState => ({
    isCollapse: false
  }),

  getters: {
    appCurrentSetting(state: AppState): AppState {
      return { ...state };
    },
  },

  actions: {
    updateSettings(partial: Partial<AppState>) {
      // @ts-ignore-next-line
      this.$patch(partial);
    },

    // 改变主题颜色
    // toggleTheme(dark: boolean) {
    //   if (dark) {
    //     this.theme = 'dark';
    //     document.body.setAttribute('arco-theme', 'dark');
    //   } else {
    //     this.theme = 'light';
    //     document.body.removeAttribute('arco-theme');
    //   }
    // },
  },
});
