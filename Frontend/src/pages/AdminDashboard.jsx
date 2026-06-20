import { useEffect, useState } from "react";
import api from "../api/api";
import "./AdminDashboard.css";

export default function AdminDashboard() {
  const [stats, setStats] = useState(null);
  const [scenarios, setScenarios] = useState([]);
  const [templateType, setTemplateType] = useState("JOB_INTERVIEW");

  const [form, setForm] = useState({
    title: "",
    description: "",
    category: "",
    difficulty: "Easy",
  });

  const [message, setMessage] = useState("");

  async function fetchScenarios() {
    try {
      const res = await api.get("/scenarios");
      setScenarios(res.data);
    } catch (err) {
      console.log(err);
    }
  }

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
    fetchScenarios();
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

      fetchScenarios();
    } catch {
      setMessage("Could not create scenario");
    }
  }

  async function createTemplateScenario() {
    try {
      await api.post("/scenarios/template", {
        templateType,
      });

      setMessage("Template scenario created 🎉");
      fetchScenarios();
    } catch {
      setMessage("Could not create template");
    }
  }

  async function publishScenario(id) {
    setMessage("");

    try {
      await api.patch(`/scenarios/${id}/publish`);

      setMessage("Scenario published successfully 🌟");
      fetchScenarios();
    } catch {
      setMessage("Could not publish scenario");
    }
  }

  async function deleteScenario(id) {
    const confirmed = window.confirm(
      "Are you sure you want to delete this scenario?"
    );

    if (!confirmed) return;

    try {
      await api.delete(`/scenarios/${id}`);

      setMessage("Scenario deleted successfully 🗑️");
      fetchScenarios();
    } catch {
      setMessage("Could not delete scenario");
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

      {message && <div className="info-box">{message}</div>}

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

      <section className="card template-card">
        <h2>Create From Template</h2>

        <select
          className="input"
          value={templateType}
          onChange={(e) => setTemplateType(e.target.value)}
        >
          <option value="JOB_INTERVIEW">Job Interview</option>
          <option value="CUSTOMER_SERVICE">Customer Service</option>
          <option value="LEADERSHIP">Leadership</option>
          <option value="ETHICS">Ethics</option>
          <option value="CONFLICT_RESOLUTION">Conflict Resolution</option>
        </select>

        <button
          className="btn btn-primary"
          onClick={createTemplateScenario}
        >
          Create Template
        </button>
      </section>

      <section className="card scenario-table-card">
        <h2>Manage Scenarios</h2>

        <table className="scenario-table">
          <thead>
            <tr>
              <th>ID</th>
              <th>Title</th>
              <th>Category</th>
              <th>Difficulty</th>
              <th>Status</th>
              <th>Action</th>
            </tr>
          </thead>

          <tbody>
            {scenarios.map((scenario) => (
              <tr key={scenario.id}>
                <td>{scenario.id}</td>
                <td>{scenario.title}</td>
                <td>{scenario.category}</td>
                <td>{scenario.difficulty}</td>

                <td>
                  <span
                    className={
                      scenario.status === "PUBLISHED"
                        ? "published"
                        : "draft"
                    }
                  >
                    {scenario.status}
                  </span>
                </td>

                <td>
                  {scenario.status !== "PUBLISHED" ? (
                    <button
                      className="btn btn-primary small-btn"
                      onClick={() => publishScenario(scenario.id)}
                    >
                      Publish
                    </button>
                  ) : (
                    <span>Published</span>
                  )}

                  <button
                    className="btn btn-danger small-btn"
                    onClick={() => deleteScenario(scenario.id)}
                  >
                    Delete
                  </button>
                </td>
              </tr>
            ))}
          </tbody>
        </table>
      </section>

      <section className="card admin-card">
        <h2>Create New Scenario</h2>

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