import { Link } from "react-router-dom";
import "./Auth.css";

export default function LoginPage() {
  return (
    <main className="page auth-page">
      <section className="card auth-card">
        <h1>Welcome back 🌸</h1>
        <p>Login and continue your decision journey.</p>

        <form className="auth-form">
          <input className="input" type="email" placeholder="Email" />
          <input className="input" type="password" placeholder="Password" />
          <button className="btn btn-primary" type="submit">Login</button>
        </form>

        <div className="auth-link">
          New here? <Link to="/register">Create account</Link>
        </div>
      </section>
    </main>
  );
}