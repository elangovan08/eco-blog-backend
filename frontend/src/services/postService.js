import apiClient from './apiClient';

export const postService = {
  getAll() {
    return apiClient.get('/posts').then(res => res.data);
  },
  getById(id) {
    return apiClient.get(`/posts/${id}`).then(res => res.data);
  },
  create(payload) {
    return apiClient.post('/posts', payload).then(res => res.data);
  },
  update(id, payload) {
    return apiClient.put(`/posts/${id}`, payload).then(res => res.data);
  },
  remove(id) {
    return apiClient.delete(`/posts/${id}`);
  },
  search(keyword) {
    return apiClient.get('/posts/search', { params: { keyword } }).then(res => res.data);
  },
  like(postId, userId) {
    return apiClient.post(`/posts/${postId}/likes`, null, { params: { userId } }).then(res => res.data);
  },
  bookmark(postId, userId) {
    return apiClient.post(`/posts/${postId}/bookmarks`, null, { params: { userId } }).then(res => res.data);
  }
};
