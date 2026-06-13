import { useEffect, useState } from "react";
import { Link, useParams } from "react-router-dom";
import api from "../api/api";
import "./ResultPage.css";

export default function ResultPage() {
  const { attemptId } = useParams();
  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  useEffect(() => {
    async function fetchResult() {
      try {
        const res = await api.get(`/attempts/${attemptId}/result`);
        setResult(res.data);
      } catch (err) {
        setError(err.response?.data?.message || "Could not load result");
      }
    }

    fetchResult();
  }, [attemptId]);

  return (
    <main className="page result-page">
      <section className="card result-card">
        <div className="result-icon">🏆</div>

        {error && <div className="error-box">{error}</div>}

        {result && (
          <>
            <h1>{result.resultMessage}</h1>
            <div className="score-box">{result.finalScore}</div>
            <p>{result.feedback}</p>

            <div className="result-actions">
              <Link className="btn btn-primary" to="/scenarios">
                Try Another Scenario
              </Link>
              <Link className="btn btn-secondary" to="/">
                Back Home
              </Link>
            </div>
          </>
        )}
      </section>
    </main>
  );
}