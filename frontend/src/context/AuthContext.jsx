import { createContext, useContext, useMemo, useState } from 'react';
import { authService } from '../services/authService';
import { clearStoredUser, getStoredUser, storeUser } from '../utils/storage';

const AuthContext = createContext(null);

export function AuthProvider({ children }) {
  const [user, setUser] = useState(() => getStoredUser());

  async function login(credentials) {
    const response = await authService.login(credentials);
    setUser(response.user);
    storeUser(response.user);
    return response;
  }

  async function signup(payload) {
    const response = await authService.signup(payload);
    setUser(response.user);
    storeUser(response.user);
    return response;
  }

  function logout() {
    setUser(null);
    clearStoredUser();
  }

  const value = useMemo(() => ({
    user,
    isAuthenticated: Boolean(user),
    isAdmin: user?.role === 'ADMIN',
    login,
    signup,
    logout
  }), [user]);

  return <AuthContext.Provider value={value}>{children}</AuthContext.Provider>;
}

export function useAuth() {
  const context = useContext(AuthContext);
  if (!context) {
    throw new Error('useAuth must be used inside AuthProvider');
  }
  return context;
}
