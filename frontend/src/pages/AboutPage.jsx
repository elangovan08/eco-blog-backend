export default function AboutPage() {
  return (
    <div className="container py-5">
      <div className="row align-items-center g-4">
        <div className="col-lg-6">
          <h1 className="page-title mb-3">About EcoBlog</h1>
          <p className="lead text-muted-eco">
            EcoBlog is a community publishing platform for people who care about technology,
            pollution, public awareness, and sustainable choices.
          </p>
          <p>
            The old JSP pages are now represented as React routes, while the Spring Boot backend
            provides clean JSON endpoints for posts, comments, likes, bookmarks, search, and admin data.
          </p>
        </div>
        <div className="col-lg-6">
          <div className="section-panel p-4">
            <h2 className="h5 fw-bold">What this frontend does</h2>
            <ul className="mb-0">
              <li>Calls REST APIs through Axios services.</li>
              <li>Uses React Router for navigation.</li>
              <li>Stores the current user with Context API.</li>
              <li>Provides reusable components for posts, comments, forms, and loading states.</li>
            </ul>
          </div>
        </div>
      </div>
    </div>
  );
}
