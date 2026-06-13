import { useState } from "react";
import api from "../api/api";
import "./ScenarioBuilderPage.css";

export default function ScenarioBuilderPage() {
  const [nodeForm, setNodeForm] = useState({
    scenarioId: "",
    title: "",
    content: "",
    nodeType: "DECISION",
    feedbackText: "",
    scoreValue: 0,
  });

  const [choiceForm, setChoiceForm] = useState({
    nodeId: "",
    choiceText: "",
    nextNodeId: "",
    scoreImpact: 0,
  });

  const [message, setMessage] = useState("");

  function handleNodeChange(e) {
    setNodeForm({ ...nodeForm, [e.target.name]: e.target.value });
  }

  function handleChoiceChange(e) {
    setChoiceForm({ ...choiceForm, [e.target.name]: e.target.value });
  }

  async function createNode(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.post("/nodes", nodeForm);
      setMessage("Node created successfully 🌱");
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not create node");
    }
  }

  async function createChoice(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.post("/choices", choiceForm);
      setMessage("Choice linked successfully 🔗");
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not create choice");
    }
  }

  return (
    <main className="page builder-page">
      <header className="builder-header">
        <h1>Scenario Builder 🧩</h1>
        <p>Create nodes, connect choices, and build your decision tree.</p>
      </header>

      {message && <div className="info-box builder-message">{message}</div>}

      <section className="builder-grid">
        <div className="card builder-card">
          <h2>Create Node</h2>

          <form className="builder-form" onSubmit={createNode}>
            <input className="input" name="scenarioId" placeholder="Scenario ID" value={nodeForm.scenarioId} onChange={handleNodeChange} />

            <input className="input" name="title" placeholder="Node title" value={nodeForm.title} onChange={handleNodeChange} />

            <textarea className="input textarea" name="content" placeholder="Node content" value={nodeForm.content} onChange={handleNodeChange} />

            <select className="input" name="nodeType" value={nodeForm.nodeType} onChange={handleNodeChange}>
              <option value="START">START</option>
              <option value="DECISION">DECISION</option>
              <option value="END">END</option>
            </select>

            <textarea className="input textarea" name="feedbackText" placeholder="Feedback text" value={nodeForm.feedbackText} onChange={handleNodeChange} />

            <input className="input" name="scoreValue" type="number" placeholder="Score value" value={nodeForm.scoreValue} onChange={handleNodeChange} />

            <button className="btn btn-primary" type="submit">Create Node</button>
          </form>
        </div>

        <div className="card builder-card">
          <h2>Create Choice</h2>

          <form className="builder-form" onSubmit={createChoice}>
            <input className="input" name="nodeId" placeholder="Current Node ID" value={choiceForm.nodeId} onChange={handleChoiceChange} />

            <input className="input" name="choiceText" placeholder="Choice text" value={choiceForm.choiceText} onChange={handleChoiceChange} />

            <input className="input" name="nextNodeId" placeholder="Next Node ID" value={choiceForm.nextNodeId} onChange={handleChoiceChange} />

            <input className="input" name="scoreImpact" type="number" placeholder="Score impact" value={choiceForm.scoreImpact} onChange={handleChoiceChange} />

            <button className="btn btn-primary" type="submit">Link Choice</button>
          </form>
        </div>
      </section>
    </main>
  );
}