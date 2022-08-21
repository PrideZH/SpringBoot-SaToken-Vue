import i18n from '@/locale';
import { defineStore } from 'pinia';
import { ref } from 'vue';

import zhCn from 'element-plus/lib/locale/lang/zh-cn';
import en from 'element-plus/lib/locale/lang/en';

const MOBILE_WIDTH = 768;

export const useAppStore = defineStore('useApp', () => {
  const isMobile = ref<boolean>(true);

  const isCollapse = ref<boolean>(false); // 侧边栏是否收缩

  const toggleCollapse = () => {
    isCollapse.value = !isCollapse.value;
  };

  const locale = ref(zhCn);

  const setLang = (lang: 'zhCn' | 'enUs') => {
    i18n.global.locale = lang;

    switch (lang) {
      case 'zhCn': locale.value = zhCn; break;
      case 'enUs': locale.value = en; break;
    }

    localStorage.setItem('localeLang', i18n.global.locale);
  };

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

  const getWindowResize = () => {
    isMobile.value = window.innerWidth < MOBILE_WIDTH;
    if (isMobile.value && !isCollapse.value) {
      isCollapse.value = true;
    }
  };
  getWindowResize();
  window.addEventListener('resize', getWindowResize);
  
  return { isMobile, isCollapse, locale, toggleCollapse, setLang };
});
