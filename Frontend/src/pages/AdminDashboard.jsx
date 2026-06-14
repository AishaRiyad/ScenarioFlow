import { useEffect, useState } from "react";
import api from "../api/api";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);

  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    difficulty: "Easy",
  });

  const [message, setMessage] = useState("");
  const [publishId, setPublishId] = useState("");

  useEffect(() => {
    async function fetchStats() {
      try {
        const res = await api.get("/dashboard/stats");
        setStats(res.data);
      } catch (err) {
        console.log(err);
      }
    }

    fetchStats();
  }, []);

  function handleChange(e) {
    setForm({ ...form, [e.target.name]: e.target.value });
  }

  async function handleSubmit(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.post("/scenarios", form);
      setMessage("Scenario created successfully ✨");
      setForm({
        title: "",
        description: "",
        category: "",
        difficulty: "Easy",
      });
    } catch {
      setMessage("Could not create scenario");
    }
  }

  async function publishScenario(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.patch(`/scenarios/${publishId}/publish`);
      setMessage("Scenario published successfully 🌟");
      setPublishId("");
    } catch {
      setMessage("Could not publish scenario");
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

      {stats && (
        <section className="stats-grid">
          <div className="card stat-card">
            <h3>{stats.totalUsers}</h3>
            <p>Users</p>
          </div>

          <div className="card stat-card">
            <h3>{stats.totalScenarios}</h3>
            <p>Scenarios</p>
          </div>

          <div className="card stat-card">
            <h3>{stats.totalAttempts}</h3>
            <p>Attempts</p>
          </div>

          <div className="card stat-card">
            <h3>{stats.completedAttempts}</h3>
            <p>Completed</p>
          </div>

          <div className="card stat-card">
            <h3>{stats.averageScore.toFixed(1)}</h3>
            <p>Average Score</p>
          </div>
        </section>
      )}

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

        <form className="admin-form publish-form" onSubmit={publishScenario}>
          <h2>Publish Scenario</h2>

          <input
            className="input"
            placeholder="Scenario ID"
            value={publishId}
            onChange={(e) => setPublishId(e.target.value)}
          />

          <button className="btn btn-secondary" type="submit">
            Publish Scenario
          </button>
        </form>
      </section>
    </main>
  );
}