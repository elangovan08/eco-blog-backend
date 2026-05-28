import apiClient from './apiClient';

export const adminService = {
  getDashboard() {
    return apiClient.get('/admin/dashboard').then(res => res.data);
  }
};
