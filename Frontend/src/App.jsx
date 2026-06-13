import { BrowserRouter, Routes, Route } from "react-router-dom";

import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import AdminDashboard from "./pages/AdminDashboard";
import ScenarioListPage from "./pages/ScenarioListPage";
import ScenarioBuilderPage from "./pages/ScenarioBuilderPage";
import ScenarioPlayPage from "./pages/ScenarioPlayPage";
import ResultPage from "./pages/ResultPage";

function App() {
  return (
    <BrowserRouter>
      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/scenarios" element={<ScenarioListPage />} />
        <Route path="/admin/builder" element={<ScenarioBuilderPage />} />
        <Route path="/scenarios/:scenarioId/play" element={<ScenarioPlayPage />} />
        <Route path="/attempts/:attemptId/result" element={<ResultPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;