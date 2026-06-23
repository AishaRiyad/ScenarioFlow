import { useEffect, useState } from "react";
import { useNavigate, useParams } from "react-router-dom";
import api from "../../api/api";
import "./ScenarioDetailsPage.css";

export default function ScenarioDetailsPage() {
  const { scenarioId } = useParams();
  const navigate = useNavigate();

  const [scenario, setScenario] = useState(null);
  const [rating, setRating] = useState(null);
  const [recommendations, setRecommendations] = useState([]);

  const [comments, setComments] = useState([]);
  const [commentText, setCommentText] = useState("");
  const [commentMessage, setCommentMessage] = useState("");

  useEffect(() => {
    async function fetchData() {
      const scenarioRes = await api.get(`/scenarios/${scenarioId}`);
      setScenario(scenarioRes.data);

      const ratingRes = await api.get(`/ratings/scenario/${scenarioId}`);
      setRating(ratingRes.data);

      const commentsRes = await api.get(`/comments/scenario/${scenarioId}`);
      setComments(commentsRes.data);

      const recRes = await api.get(`/scenarios/${scenarioId}/recommendations`);
      setRecommendations(recRes.data);
    }

    fetchData();
  }, [scenarioId]);

  async function submitComment(e) {
    e.preventDefault();
    setCommentMessage("");

    try {
      const res = await api.post("/comments", {
        scenarioId: Number(scenarioId),
        content: commentText,
      });

      setComments([res.data, ...comments]);
      setCommentText("");
      setCommentMessage("Comment added successfully 🌸");
    } catch (err) {
      setCommentMessage(err.response?.data?.message || "Could not add comment");
    }
  }

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
        <span className="details-category">
          {scenario.category || "General"}
        </span>

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
              {rating
                ? `${rating.averageRating.toFixed(1)} ⭐ (${rating.totalRatings})`
                : "No rating"}
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

        {recommendations.length > 0 && (
          <section className="recommendations-section">
            <h2>You may also like ✨</h2>

            <div className="recommendations-grid">
              {recommendations.map((item) => (
                <div className="card recommendation-card" key={item.id}>
                  <h3>{item.title}</h3>
                  <p>{item.category}</p>

                  <button
                    className="btn btn-secondary"
                    onClick={() => navigate(`/scenarios/${item.id}`)}
                  >
                    View
                  </button>
                </div>
              ))}
            </div>
          </section>
        )}

        <section className="comments-section">
          <h2>Community Comments 💬</h2>

          <form className="comment-form" onSubmit={submitComment}>
            <textarea
              className="input textarea"
              placeholder="Share your thoughts about this scenario..."
              value={commentText}
              onChange={(e) => setCommentText(e.target.value)}
            />

            <button className="btn btn-secondary" type="submit">
              Add Comment
            </button>

            {commentMessage && (
              <p className="comment-message">{commentMessage}</p>
            )}
          </form>

          <div className="comments-list">
            {comments.length === 0 ? (
              <p>No comments yet.</p>
            ) : (
              comments.map((comment) => (
                <div className="comment-card" key={comment.id}>
                  <strong>{comment.userFullName}</strong>
                  <p>{comment.content}</p>
                  <span>{new Date(comment.createdAt).toLocaleString()}</span>
                </div>
              ))
            )}
          </div>
        </section>
      </section>
    </main>
  );
}