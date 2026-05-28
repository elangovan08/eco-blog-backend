import { useEffect, useState } from 'react';
import ErrorAlert from '../components/ErrorAlert.jsx';
import LoadingState from '../components/LoadingState.jsx';
import { adminService } from '../services/adminService';
import { getApiError } from '../utils/validators';

export default function AdminDashboardPage() {
  const [dashboard, setDashboard] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState('');

  useEffect(() => {
    adminService.getDashboard()
      .then(setDashboard)
      .catch(err => setError(getApiError(err, 'Unable to load admin dashboard.')))
      .finally(() => setLoading(false));
  }, []);

  if (loading) return <LoadingState label="Loading dashboard..." />;

  return (
    <div className="container py-5">
      <h1 className="page-title mb-4">Admin Dashboard</h1>
      <ErrorAlert message={error} />
      {dashboard && (
        <>
          <div className="row g-4 mb-4">
            <StatCard label="Users" value={dashboard.userCount} />
            <StatCard label="Posts" value={dashboard.postCount} />
            <StatCard label="Comments" value={dashboard.commentCount} />
            <StatCard label="Contact Messages" value={dashboard.contactCount} />
          </div>
          <div className="section-panel p-4">
            <h2 className="h5 fw-bold mb-3">Recent Users</h2>
            <div className="table-responsive">
              <table className="table align-middle">
                <thead>
                  <tr>
                    <th>Username</th>
                    <th>Email</th>
                    <th>Role</th>
                  </tr>
                </thead>
                <tbody>
                  {dashboard.recentUsers.map(user => (
                    <tr key={user.id}>
                      <td>{user.username}</td>
                      <td>{user.email}</td>
                      <td><span className="badge text-bg-success">{user.role}</span></td>
                    </tr>
                  ))}
                </tbody>
              </table>
            </div>
          </div>
        </>
      )}
    </div>
  );
}

function StatCard({ label, value }) {
  return (
    <div className="col-md-4">
      <div className="section-panel p-4">
        <div className="text-muted-eco">{label}</div>
        <div className="display-6 fw-bold">{value}</div>
      </div>
    </div>
  );
}
