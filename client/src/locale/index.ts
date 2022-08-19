import { createI18n } from 'vue-i18n';

import zhCn from './lang/zh-cn';
import enUs from './lang/en-us';

const i18n = createI18n({
  globalInjection: true, // 全局模式，可以直接使用 $t
  locale: localStorage.getItem('localeLang') || 'zhCn',
  messages: {
    zhCn,
    enUs
  }
});

export default i18n;