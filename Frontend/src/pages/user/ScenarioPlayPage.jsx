import { useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../../api/api";
import "./ScenarioPlayPage.css";

export default function ScenarioPlayPage() {
  const { scenarioId } = useParams();
  const navigate = useNavigate();

  const [attemptId, setAttemptId] = useState(null);
  const [node, setNode] = useState(null);
  const [message, setMessage] = useState("");

  async function startScenario() {
    setMessage("");

    try {
      const res = await api.post("/attempts/start", {
        scenarioId: Number(scenarioId),
      });

      setNode(res.data);
      setAttemptId(res.data.attemptId);
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not start scenario");
    }
  }

  async function choose(choiceId) {
    setMessage("");

    try {
      const res = await api.post("/attempts/choice", {
        attemptId,
        choiceId,
      });

      setNode(res.data);

      if (res.data.nodeType === "END") {
        navigate(
          `/attempts/${res.data.attemptId}/result?scenarioId=${scenarioId}`
        );
      }
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not submit choice");
    }
  }

  return (
    <main className="page play-page">
      <section className="card play-card">
        {!node ? (
          <>
            <h1>Ready to begin? 🎮</h1>
            <p>Your choices will shape the story and final result.</p>

            {message && <div className="error-box">{message}</div>}

            <button className="btn btn-primary" onClick={startScenario}>
              Start Scenario
            </button>
          </>
        ) : (
          <>
            <span className="node-type">{node.nodeType}</span>

            <h1>{node.title}</h1>

            <p>{node.content}</p>

            <div className="choices-list">
              {(node.choices || []).map((choice) => (
                <button
                  className="choice-button"
                  key={choice.id}
                  onClick={() => choose(choice.id)}
                >
                  {choice.choiceText}
                </button>
              ))}
            </div>
          </>
        )}
      </section>
    </main>
  );
}