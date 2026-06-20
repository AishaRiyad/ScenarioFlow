import { useEffect, useState } from "react";
import { Link, useParams, useSearchParams } from "react-router-dom";
import jsPDF from "jspdf";
import api from "../api/api";
import "./ResultPage.css";

export default function ResultPage() {
  const { attemptId } = useParams();
  const [searchParams] = useSearchParams();
  const scenarioId = searchParams.get("scenarioId");

  const [result, setResult] = useState(null);
  const [error, setError] = useState("");

  const [rating, setRating] = useState(5);
  const [comment, setComment] = useState("");
  const [ratingMessage, setRatingMessage] = useState("");

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

  function downloadReport() {
    if (!result) return;

    const doc = new jsPDF();

    const feedback = result.feedback || "No feedback available";
    const resultMessage = result.resultMessage || "No result message";

    doc.setFontSize(20);
    doc.text("ScenarioFlow Result Report", 20, 20);

    doc.setFontSize(12);
    doc.text(`Attempt ID: ${attemptId}`, 20, 40);
    doc.text(`Final Score: ${String(result.finalScore ?? 0)}`, 20, 50);
    doc.text(`Result: ${resultMessage}`, 20, 60);
    doc.text(`Decisions Count: ${result.decisionsCount}`, 20, 70);

    doc.text("Feedback:", 20, 85);
    doc.text(feedback, 20, 95, { maxWidth: 170 });

    doc.text("Strengths:", 20, 120);
    doc.text(result.strengths || "", 20, 130, { maxWidth: 170 });

    doc.text("Improvement Areas:", 20, 155);
    doc.text(result.improvementAreas || "", 20, 165, { maxWidth: 170 });

    doc.text(
      `Generated at: ${new Date().toLocaleString()}`,
      20,
      190
    );

    doc.save(`scenarioflow-result-${attemptId}.pdf`);
  }

  async function submitRating(e) {
    e.preventDefault();
    setRatingMessage("");

    try {
      await api.post("/ratings", {
        scenarioId: Number(scenarioId),
        rating: Number(rating),
        comment,
      });

      setRatingMessage("Thank you for your rating ⭐");
    } catch (err) {
      setRatingMessage(
        err.response?.data?.message || "Could not submit rating"
      );
    }
  }

  return (
    <main className="page result-page">
      <section className="card result-card">
        <div className="result-icon">🏆</div>

        {error && <div className="error-box">{error}</div>}

        {result && (
          <>
            <h1>{result.resultMessage}</h1>

            <div className="score-box">
              {result.finalScore}
            </div>

            <p>{result.feedback}</p>

            <div className="insights-grid">
              <div className="insight-card">
                <h3>Decisions</h3>
                <p>{result.decisionsCount}</p>
              </div>

              <div className="insight-card">
                <h3>Strengths</h3>
                <p>{result.strengths}</p>
              </div>

              <div className="insight-card">
                <h3>Improvement Areas</h3>
                <p>{result.improvementAreas}</p>
              </div>
            </div>

            {scenarioId && (
              <form className="rating-form" onSubmit={submitRating}>
                <h2>Rate this scenario ⭐</h2>

                <select
                  className="input"
                  value={rating}
                  onChange={(e) => setRating(e.target.value)}
                >
                  <option value="5">5 - Excellent</option>
                  <option value="4">4 - Good</option>
                  <option value="3">3 - Okay</option>
                  <option value="2">2 - Needs work</option>
                  <option value="1">1 - Poor</option>
                </select>

                <textarea
                  className="input textarea"
                  placeholder="Optional comment"
                  value={comment}
                  onChange={(e) => setComment(e.target.value)}
                />

                <button className="btn btn-primary" type="submit">
                  Submit Rating
                </button>

                {ratingMessage && (
                  <p className="rating-message">
                    {ratingMessage}
                  </p>
                )}
              </form>
            )}

            <div className="result-actions">
              <button
                className="btn btn-secondary"
                onClick={downloadReport}
              >
                Download PDF Report
              </button>

              <Link
                className="btn btn-primary"
                to="/scenarios"
              >
                Try Another Scenario
              </Link>

              <Link
                className="btn btn-secondary"
                to="/"
              >
                Back Home
              </Link>
            </div>
          </>
        )}
      </section>
    </main>
  );
}