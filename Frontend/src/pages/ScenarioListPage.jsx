import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import api from "../api/api";
import "./ScenarioListPage.css";

export default function ScenarioListPage() {
  const [scenarios, setScenarios] = useState([]);
  const [error, setError] = useState("");

  const navigate = useNavigate();

  useEffect(() => {
    async function fetchScenarios() {
      try {
        const res = await api.get("/scenarios/published");
        setScenarios(res.data);
      } catch {
        setError("Could not load scenarios");
      }
    }

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
                onClick={() => navigate(`/scenarios/${scenario.id}/play`)}
              >
                Start
              </button>
            </div>
          </article>
        ))}
      </section>
    </main>
  );
}