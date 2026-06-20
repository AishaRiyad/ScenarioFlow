import { useEffect, useState } from "react";
import api from "../api/api";
import "./ScenarioBuilderPage.css";

export default function ScenarioBuilderPage() {
  const [scenarios, setScenarios] = useState([]);
  const [nodes, setNodes] = useState([]);
  const [selectedScenarioId, setSelectedScenarioId] = useState("");

  const [nodeForm, setNodeForm] = useState({
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

  useEffect(() => {
    fetchScenarios();
  }, []);

  useEffect(() => {
    if (selectedScenarioId) {
      fetchNodes(selectedScenarioId);
    }
  }, [selectedScenarioId]);

  async function fetchScenarios() {
    const res = await api.get("/scenarios");
    setScenarios(res.data);
  }

  async function fetchNodes(scenarioId) {
    const res = await api.get(`/nodes/scenario/${scenarioId}`);
    setNodes(res.data);
  }

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
      await api.post("/nodes", {
        ...nodeForm,
        scenarioId: Number(selectedScenarioId),
        scoreValue: Number(nodeForm.scoreValue),
      });

      setMessage("Node created successfully 🌱");
      setNodeForm({
        title: "",
        content: "",
        nodeType: "DECISION",
        feedbackText: "",
        scoreValue: 0,
      });

      fetchNodes(selectedScenarioId);
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not create node");
    }
  }

  async function createChoice(e) {
    e.preventDefault();
    setMessage("");

    try {
      await api.post("/choices", {
        nodeId: Number(choiceForm.nodeId),
        choiceText: choiceForm.choiceText,
        nextNodeId: Number(choiceForm.nextNodeId),
        scoreImpact: Number(choiceForm.scoreImpact),
      });

      setMessage("Choice linked successfully 🔗");
      setChoiceForm({
        nodeId: "",
        choiceText: "",
        nextNodeId: "",
        scoreImpact: 0,
      });

      fetchNodes(selectedScenarioId);
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not create choice");
    }
  }

  return (
    <main className="page builder-page">
      <header className="builder-header">
        <h1>Scenario Builder 🧩</h1>
        <p>Select a scenario, create nodes, and connect them with choices.</p>
      </header>

      <section className="card builder-selector">
        <h2>Select Scenario</h2>

        <select
          className="input"
          value={selectedScenarioId}
          onChange={(e) => setSelectedScenarioId(e.target.value)}
        >
          <option value="">Choose scenario</option>
          {scenarios.map((scenario) => (
            <option key={scenario.id} value={scenario.id}>
              #{scenario.id} - {scenario.title}
            </option>
          ))}
        </select>
      </section>

      {message && <div className="info-box builder-message">{message}</div>}

      {selectedScenarioId && (
        <>
          <section className="builder-grid">
            <div className="card builder-card">
              <h2>Create Node</h2>

              <form className="builder-form" onSubmit={createNode}>
                <input
                  className="input"
                  name="title"
                  placeholder="Node title"
                  value={nodeForm.title}
                  onChange={handleNodeChange}
                />

                <textarea
                  className="input textarea"
                  name="content"
                  placeholder="Node content"
                  value={nodeForm.content}
                  onChange={handleNodeChange}
                />

                <select
                  className="input"
                  name="nodeType"
                  value={nodeForm.nodeType}
                  onChange={handleNodeChange}
                >
                  <option value="START">START</option>
                  <option value="DECISION">DECISION</option>
                  <option value="END">END</option>
                </select>

                <textarea
                  className="input textarea"
                  name="feedbackText"
                  placeholder="Feedback text"
                  value={nodeForm.feedbackText}
                  onChange={handleNodeChange}
                />

                <input
                  className="input"
                  name="scoreValue"
                  type="number"
                  placeholder="Score value"
                  value={nodeForm.scoreValue}
                  onChange={handleNodeChange}
                />

                <button className="btn btn-primary" type="submit">
                  Create Node
                </button>
              </form>
            </div>

            <div className="card builder-card">
              <h2>Create Choice</h2>

              <form className="builder-form" onSubmit={createChoice}>
                <select
                  className="input"
                  name="nodeId"
                  value={choiceForm.nodeId}
                  onChange={handleChoiceChange}
                >
                  <option value="">Current node</option>
                  {nodes.map((node) => (
                    <option key={node.id} value={node.id}>
                      #{node.id} - {node.title} ({node.nodeType})
                    </option>
                  ))}
                </select>

                <input
                  className="input"
                  name="choiceText"
                  placeholder="Choice text"
                  value={choiceForm.choiceText}
                  onChange={handleChoiceChange}
                />

                <select
                  className="input"
                  name="nextNodeId"
                  value={choiceForm.nextNodeId}
                  onChange={handleChoiceChange}
                >
                  <option value="">Next node</option>
                  {nodes.map((node) => (
                    <option key={node.id} value={node.id}>
                      #{node.id} - {node.title} ({node.nodeType})
                    </option>
                  ))}
                </select>

                <input
                  className="input"
                  name="scoreImpact"
                  type="number"
                  placeholder="Score impact"
                  value={choiceForm.scoreImpact}
                  onChange={handleChoiceChange}
                />

                <button className="btn btn-primary" type="submit">
                  Link Choice
                </button>
              </form>
            </div>
          </section>

          <section className="card nodes-preview">
            <h2>Scenario Nodes</h2>

            {nodes.length === 0 ? (
              <p>No nodes yet.</p>
            ) : (
              <div className="nodes-list">
                {nodes.map((node) => (
                  <div className="node-preview" key={node.id}>
                    <h3>
                      #{node.id} - {node.title}
                    </h3>
                    <span>{node.nodeType}</span>
                    <p>{node.content}</p>

                    {node.choices?.length > 0 && (
                      <ul>
                        {node.choices.map((choice) => (
                          <li key={choice.id}>
                            {choice.choiceText} → Node #{choice.nextNodeId}
                          </li>
                        ))}
                      </ul>
                    )}
                  </div>
                ))}
              </div>
            )}
          </section>
        </>
      )}
    </main>
  );
}