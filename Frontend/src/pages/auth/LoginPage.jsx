import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./Auth.css";

export default function LoginPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({ email: "", password: "" });
  const [error, setError] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    try {
      const res = await api.post("/auth/login", form);

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("email", res.data.email);
      localStorage.setItem("role", res.data.role);

      navigate(res.data.role === "ADMIN" ? "/admin" : "/scenarios");
    } catch (err) {
      setError(err.response?.data?.message || "Login failed");
    }
  }

  return (
    <main className="page auth-page">
      <section className="card auth-card">
        <h1>Welcome back 🌸</h1>
        <p>Login and continue your decision journey.</p>

        {error && <div className="error-box">{error}</div>}

        <form className="auth-form" onSubmit={handleSubmit}>
          <input className="input" name="email" type="email" placeholder="Email" onChange={handleChange} />
          <input className="input" name="password" type="password" placeholder="Password" onChange={handleChange} />
          <button className="btn btn-primary" type="submit">Login</button>
        </form>

        <div className="auth-link">
          New here? <Link to="/register">Create account</Link>
        </div>
      </section>
    </main>
  );
}