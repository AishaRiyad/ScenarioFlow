import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../../api/api";
import "./ScenarioListPage.css";

export default function ScenarioListPage() {
  const [scenarios, setScenarios] = useState([]);
  const [error, setError] = useState("");
  const [keyword, setKeyword] = useState("");
  const [category, setCategory] = useState("");

  const navigate = useNavigate();

  async function fetchScenarios() {
    try {
      const res = await api.get("/scenarios/published", {
        params: {
          keyword,
          category,
        },
      });

      setScenarios(res.data);
    } catch {
      setError("Could not load scenarios");
    }
  }

  useEffect(() => {
    fetchScenarios();
  }, []);

  return (
    <main className="page scenarios-page">
      <header className="scenarios-header">
        <div>
          <h1>Choose your path 🌷</h1>
          <p>Pick a scenario and see how your decisions shape the ending.</p>
        </div>
      </header>

      {error && <div className="error-box">{error}</div>}

      <section className="filters-section">
        <input
          className="input"
          placeholder="Search scenario..."
          value={keyword}
          onChange={(e) => setKeyword(e.target.value)}
        />

        <select
          className="input"
          value={category}
          onChange={(e) => setCategory(e.target.value)}
        >
          <option value="">All Categories</option>
          <option value="Career">Career</option>
          <option value="Education">Education</option>
          <option value="Leadership">Leadership</option>
          <option value="Communication">Communication</option>
          <option value="Ethics">Ethics</option>
        </select>

        <button className="btn btn-primary" onClick={fetchScenarios}>
          Search
        </button>
      </section>

      <section className="scenario-grid">
        {scenarios.map((scenario) => (
          <article className="card scenario-card" key={scenario.id}>
            <span className="scenario-category">
              {scenario.category || "General"}
            </span>

            <h2>{scenario.title}</h2>

            <p>{scenario.description}</p>

            <div className="scenario-footer">
              <span>{scenario.difficulty || "Easy"}</span>

              <button
                className="btn btn-primary"
                onClick={() => navigate(`/scenarios/${scenario.id}`)}
              >
                View Details
              </button>
            </div>
          </article>
        ))}
      </section>
    </main>
  );
}