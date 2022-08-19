export default {
  path: 'dashboard',
  name: 'dashboard',
  component: () => import('@/views/dashboard/index.vue'),
  meta: {
    locale: 'menu.dashboard',
    requireAuth: true,
    icon: [
      'M512 896a384 384 0 100-768 384 384 0 000 768zm0 64a448 448 0 110-896 448 448 0 010 896z',
      'M192 512a320 320 0 11640 0 32 32 0 11-64 0 256 256 0 10-512 0 32 32 0 01-64 0z',
      'M570.432 627.84A96 96 0 11509.568 608l60.992-187.776A32 32 0 11631.424 440l-60.992 187.776zM502.08 734.464a32 32 0 1019.84-60.928 32 32 0 00-19.84 60.928z'
    ]
  }
};
