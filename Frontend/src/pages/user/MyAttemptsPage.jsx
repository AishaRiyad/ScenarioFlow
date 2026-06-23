import { useEffect, useState } from "react";
import api from "../../api/api";
import "./MyAttemptsPage.css";

export default function MyAttemptsPage() {
  const [attempts, setAttempts] = useState([]);

  useEffect(() => {
    async function fetchAttempts() {
      const res = await api.get("/attempts/my");
      setAttempts(res.data);
    }

    fetchAttempts();
  }, []);

  return (
    <main className="page attempts-page">
      <header className="attempts-header">
        <h1>My Attempts 🌟</h1>
        <p>Track your scenario journeys and scores.</p>
      </header>

      <section className="attempts-grid">
        {attempts.map((attempt) => (
          <article className="card attempt-card" key={attempt.id}>
            <h2>Attempt #{attempt.id}</h2>
            <p>Score: {attempt.finalScore}</p>
            <span className={attempt.completed ? "done" : "pending"}>
              {attempt.completed ? "Completed" : "In Progress"}
            </span>
          </article>
        ))}
      </section>
    </main>
  );
}