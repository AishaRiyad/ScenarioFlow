import { useCallback, useEffect, useState } from "react";
import {
  ReactFlow,
  Background,
  Controls,
  MiniMap,
  applyNodeChanges,
} from "@xyflow/react";
import "@xyflow/react/dist/style.css";

import api from "../api/api";
import "./VisualBuilderPage.css";

export default function VisualBuilderPage() {
  const [scenarios, setScenarios] = useState([]);
  const [selectedScenarioId, setSelectedScenarioId] = useState("");
  const [flowNodes, setFlowNodes] = useState([]);
  const [flowEdges, setFlowEdges] = useState([]);

  useEffect(() => {
    async function fetchScenarios() {
      const res = await api.get("/scenarios");
      setScenarios(res.data);
    }

    fetchScenarios();
  }, []);

  useEffect(() => {
    if (selectedScenarioId) {
      fetchFlowData(selectedScenarioId);
    }
  }, [selectedScenarioId]);

  const onNodesChange = useCallback((changes) => {
    setFlowNodes((nds) => applyNodeChanges(changes, nds));
  }, []);

  async function fetchFlowData(scenarioId) {
    const res = await api.get(`/nodes/scenario/${scenarioId}`);
    const nodes = res.data;

    const mappedNodes = nodes.map((node, index) => ({
      id: String(node.id),
      position: {
        x: node.positionX ?? 120 + (index % 3) * 280,
        y: node.positionY ?? 100 + Math.floor(index / 3) * 180,
      },
      data: {
        label: `${node.nodeType}: ${node.title}`,
      },
      style: {
        borderRadius: 18,
        padding: 12,
        border: "2px solid #e5ddff",
        background: "#ffffff",
        color: "#2b2540",
        fontWeight: 700,
      },
    }));

    const mappedEdges = nodes.flatMap((node) =>
      (node.choices || []).map((choice) => ({
        id: `e-${node.id}-${choice.nextNodeId}-${choice.id}`,
        source: String(node.id),
        target: String(choice.nextNodeId),
        label: choice.choiceText,
        animated: true,
      }))
    );

    setFlowNodes(mappedNodes);
    setFlowEdges(mappedEdges);
  }

  async function savePositions() {
    try {
      await Promise.all(
        flowNodes.map((node) =>
          api.patch(`/nodes/${node.id}/position`, {
            positionX: node.position.x,
            positionY: node.position.y,
          })
        )
      );

      alert("Positions saved successfully ✨");
    } catch {
      alert("Could not save positions");
    }
  }

  return (
    <main className="page visual-builder-page">
      <header className="visual-header">
        <h1>Visual Decision Tree 🌳</h1>
        <p>See your scenario flow as a connected decision map.</p>
      </header>

      <section className="card visual-selector">
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

      <section className="card flow-card">
        {selectedScenarioId ? (
          <>
            <button
              className="btn btn-primary save-flow-btn"
              onClick={savePositions}
            >
              Save Layout
            </button>

            <ReactFlow
              nodes={flowNodes}
              edges={flowEdges}
              onNodesChange={onNodesChange}
              fitView
            >
              <Background />
              <Controls />
              <MiniMap />
            </ReactFlow>
          </>
        ) : (
          <div className="empty-flow">Choose a scenario to view its tree ✨</div>
        )}
      </section>
    </main>
  );
}