export const APP_NAME = 'EcoBlog';

export const STORAGE_KEYS = {
  user: 'ecoblog_user',
  theme: 'ecoblog_theme',
  recentlyViewed: 'ecoblog_recently_viewed'
};

export const POST_SORT_OPTIONS = [
  { label: 'Newest', value: 'createdAt,desc' },
  { label: 'Most liked', value: 'likeCount,desc' },
  { label: 'Oldest', value: 'createdAt,asc' }
];

export const PAGE_SIZE = 9;
