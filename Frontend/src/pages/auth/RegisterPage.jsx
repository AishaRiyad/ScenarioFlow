import { useState } from "react";
import { Link, useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./Auth.css";

export default function RegisterPage() {
  const navigate = useNavigate();
  const [form, setForm] = useState({
    fullName: "",
    email: "",
    password: "",
  });
  const [error, setError] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setError("");

    try {
      const res = await api.post("/auth/register", form);

      localStorage.setItem("token", res.data.token);
      localStorage.setItem("email", res.data.email);
      localStorage.setItem("role", res.data.role);

      navigate("/scenarios");
    } catch (err) {
      setError(err.response?.data?.message || "Register failed");
    }
  }

  return (
    <main className="page auth-page">
      <section className="card auth-card">
        <h1>Join ScenarioFlow ✨</h1>
        <p>Create your account and start exploring interactive scenarios.</p>

        {error && <div className="error-box">{error}</div>}

        <form className="auth-form" onSubmit={handleSubmit}>
          <input className="input" name="fullName" type="text" placeholder="Full name" onChange={handleChange} />
          <input className="input" name="email" type="email" placeholder="Email" onChange={handleChange} />
          <input className="input" name="password" type="password" placeholder="Password" onChange={handleChange} />
          <button className="btn btn-primary" type="submit">Create Account</button>
        </form>

        <div className="auth-link">
          Already have an account? <Link to="/login">Login</Link>
        </div>
      </section>
    </main>
  );
}