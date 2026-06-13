import { Link } from "react-router-dom";
import "./Auth.css";

export default function RegisterPage() {
  return (
    <main className="page auth-page">
      <section className="card auth-card">
        <h1>Join ScenarioFlow ✨</h1>
        <p>Create your account and start exploring interactive scenarios.</p>

        <form className="auth-form">
          <input className="input" type="text" placeholder="Full name" />
          <input className="input" type="email" placeholder="Email" />
          <input className="input" type="password" placeholder="Password" />
          <button className="btn btn-primary" type="submit">Create Account</button>
        </form>

        <div className="auth-link">
          Already have an account? <Link to="/login">Login</Link>
        </div>
      </section>
    </main>
  );
}