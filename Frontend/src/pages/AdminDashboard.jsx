import { useState } from "react";
import api from "../api/api";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    difficulty: "Easy",
  });

  const [message, setMessage] = useState("");

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.post("/scenarios", form);
      setMessage("Scenario created successfully ✨");
      setForm({ title: "", description: "", category: "", difficulty: "Easy" });
    } catch {
      setMessage("Could not create scenario");
    }
  }

  return (
    <main className="page admin-page">
      <section className="admin-header">
        <div>
          <h1>Admin Studio 🎨</h1>
          <p>Create interactive scenarios and build decision paths.</p>
        </div>

        <a className="btn btn-secondary" href="/admin/builder">
          Open Scenario Builder
        </a>
      </section>

      <section className="card admin-card">
        <h2>Create New Scenario</h2>

        {message && <div className="info-box">{message}</div>}

        <form className="admin-form" onSubmit={handleSubmit}>
          <input
            className="input"
            name="title"
            placeholder="Scenario title"
            value={form.title}
            onChange={handleChange}
          />

          <textarea
            className="input textarea"
            name="description"
            placeholder="Description"
            value={form.description}
            onChange={handleChange}
          />

          <input
            className="input"
            name="category"
            placeholder="Category"
            value={form.category}
            onChange={handleChange}
          />

          <select
            className="input"
            name="difficulty"
            value={form.difficulty}
            onChange={handleChange}
          >
            <option>Easy</option>
            <option>Medium</option>
            <option>Hard</option>
          </select>

          <button className="btn btn-primary" type="submit">
            Create Scenario
          </button>
        </form>
      </section>
    </main>
  );
}