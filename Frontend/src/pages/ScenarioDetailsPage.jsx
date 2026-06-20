import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../api/api";
import "./ScenarioDetailsPage.css";

export default function ScenarioDetailsPage() {
  const { scenarioId } = useParams();
  const navigate = useNavigate();

  const [scenario, setScenario] = useState(null);
  const [rating, setRating] = useState(null);

  useEffect(() => {
    async function fetchData() {
      const scenarioRes = await api.get(`/scenarios/${scenarioId}`);
      setScenario(scenarioRes.data);

      const ratingRes = await api.get(`/ratings/scenario/${scenarioId}`);
      setRating(ratingRes.data);
    }

    fetchData();
  }, [scenarioId]);

  if (!scenario) {
    return (
      <main className="page details-page">
        <section className="card details-card">
          <p>Loading scenario...</p>
        </section>
      </main>
    );
  }

  return (
    <main className="page details-page">
      <section className="card details-card">
        <span className="details-category">{scenario.category || "General"}</span>

        <h1>{scenario.title}</h1>
        <p>{scenario.description}</p>

        <div className="details-meta">
          <div>
            <strong>Difficulty</strong>
            <span>{scenario.difficulty || "Easy"}</span>
          </div>

          <div>
            <strong>Rating</strong>
            <span>
              {rating ? `${rating.averageRating.toFixed(1)} ⭐ (${rating.totalRatings})` : "No rating"}
            </span>
          </div>

          <div>
            <strong>Status</strong>
            <span>{scenario.status}</span>
          </div>
        </div>

        <button
          className="btn btn-primary"
          onClick={() => navigate(`/scenarios/${scenarioId}/play`)}
        >
          Start Scenario 🎮
        </button>
      </section>
    </main>
  );
}