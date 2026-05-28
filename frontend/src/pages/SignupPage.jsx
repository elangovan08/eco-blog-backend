import { useState } from 'react';
import { Link, useNavigate } from 'react-router-dom';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { getApiError, isEmail, minLength, required } from '../utils/validators';

export default function SignupPage() {
  const { signup } = useAuth();
  const navigate = useNavigate();
  const [form, setForm] = useState({ username: '', email: '', password: '', role: 'POSTER' });
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [submitting, setSubmitting] = useState(false);

  function validate() {
    const next = {};
    if (!required(form.username)) next.username = 'Username is required.';
    if (!isEmail(form.email)) next.email = 'Valid email is required.';
    if (!minLength(form.password, 6)) next.password = 'Password must be at least 6 characters.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    try {
      await signup(form);
      navigate('/posts');
    } catch (err) {
      setApiError(getApiError(err, 'Signup failed.'));
    } finally {
      setSubmitting(false);
    }
  }

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-md-8 col-lg-6">
          <div className="section-panel p-4">
            <h1 className="h3 fw-bold mb-3">Create Account</h1>
            <ErrorAlert message={apiError} />
            <form onSubmit={handleSubmit}>
              <FormInput id="username" label="Username" value={form.username} error={errors.username} onChange={e => setForm({ ...form, username: e.target.value })} />
              <FormInput id="email" label="Email" value={form.email} error={errors.email} onChange={e => setForm({ ...form, email: e.target.value })} />
              <FormInput id="password" label="Password" type="password" value={form.password} error={errors.password} onChange={e => setForm({ ...form, password: e.target.value })} />
              <div className="mb-3">
                <label className="form-label fw-semibold" htmlFor="role">Role</label>
                <select id="role" className="form-select" value={form.role} onChange={e => setForm({ ...form, role: e.target.value })}>
                  <option value="POSTER">Poster</option>
                  <option value="VIEWER">Viewer</option>
                  <option value="ADMIN">Admin</option>
                </select>
              </div>
              <button className="btn btn-eco w-100" type="submit" disabled={submitting}>
                {submitting ? 'Creating...' : 'Signup'}
              </button>
            </form>
            <p className="text-muted-eco mt-3 mb-0">Already have an account? <Link to="/login">Login</Link></p>
          </div>
        </div>
      </div>
    </div>
  );
}
