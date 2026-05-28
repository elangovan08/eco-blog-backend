import { useEffect, useState } from 'react';
import { useNavigate, useParams } from 'react-router-dom';
import ErrorAlert from '../components/ErrorAlert.jsx';
import FormInput from '../components/FormInput.jsx';
import LoadingState from '../components/LoadingState.jsx';
import { useAuth } from '../context/AuthContext.jsx';
import { postService } from '../services/postService';
import { getApiError, minLength, required } from '../utils/validators';

export default function PostFormPage() {
  const { id } = useParams();
  const navigate = useNavigate();
  const { user } = useAuth();
  const isEdit = Boolean(id);
  const [form, setForm] = useState({ title: '', content: '' });
  const [errors, setErrors] = useState({});
  const [apiError, setApiError] = useState('');
  const [loading, setLoading] = useState(isEdit);
  const [submitting, setSubmitting] = useState(false);

  useEffect(() => {
    if (!isEdit) return;
    postService.getById(id)
      .then(post => setForm({ title: post.title, content: post.content }))
      .catch(err => setApiError(getApiError(err, 'Unable to load post.')))
      .finally(() => setLoading(false));
  }, [id, isEdit]);

  function update(field, value) {
    setForm(current => ({ ...current, [field]: value }));
  }

  function validate() {
    const next = {};
    if (!required(form.title)) next.title = 'Title is required.';
    if (!minLength(form.content, 10)) next.content = 'Content must be at least 10 characters.';
    setErrors(next);
    return Object.keys(next).length === 0;
  }

  async function handleSubmit(event) {
    event.preventDefault();
    if (!validate()) return;
    setSubmitting(true);
    setApiError('');
    try {
      const payload = { ...form, authorId: user.id };
      const saved = isEdit ? await postService.update(id, payload) : await postService.create(payload);
      navigate(`/posts/${saved.id}`);
    } catch (err) {
      setApiError(getApiError(err, 'Unable to save post.'));
    } finally {
      setSubmitting(false);
    }
  }

  if (loading) return <LoadingState label="Loading form..." />;

  return (
    <div className="container py-5">
      <div className="row justify-content-center">
        <div className="col-lg-9">
          <h1 className="page-title mb-3">{isEdit ? 'Edit Post' : 'Create Post'}</h1>
          <div className="section-panel p-4">
            <ErrorAlert message={apiError} />
            <form onSubmit={handleSubmit}>
              <FormInput id="title" label="Title" value={form.title} error={errors.title} onChange={e => update('title', e.target.value)} />
              <FormInput id="content" label="Content" as="textarea" rows="10" value={form.content} error={errors.content} onChange={e => update('content', e.target.value)} />
              <button className="btn btn-eco" type="submit" disabled={submitting}>
                {submitting ? 'Saving...' : 'Save Post'}
              </button>
            </form>
          </div>
        </div>
      </div>
    </div>
  );
}
