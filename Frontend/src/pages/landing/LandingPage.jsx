import { Link } from "react-router-dom";
import "./LandingPage.css";

export default function LandingPage() {
  return (
    <main className="page landing-page">
      <nav className="landing-nav">
        <h2>ScenarioFlow</h2>
        <div>
          <Link to="/login" className="btn btn-secondary">Login</Link>
          <Link to="/register" className="btn btn-primary">Get Started</Link>
        </div>
      </nav>

      <section className="hero card">
        <div className="hero-content">
          <span className="badge">Interactive Decision Simulator</span>
          <h1>Build stories where every choice changes the path ✨</h1>
          <p>
            Create realistic scenarios, guide users through decisions, track their journey,
            and reveal personalized results.
          </p>

          <div className="hero-actions">
            <Link to="/register" className="btn btn-primary">Start Exploring</Link>
            <Link to="/scenarios" className="btn btn-secondary">View Scenarios</Link>
          </div>
        </div>

        <div className="hero-illustration">
          <div className="bubble big">🎯</div>
          <div className="bubble medium">💭</div>
          <div className="bubble small">🌸</div>
          <div className="path-card">Choose → Learn → Improve</div>
        </div>
      </section>
    </main>
  );
}