import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar";

import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import AdminDashboard from "./pages/AdminDashboard";
import ScenarioListPage from "./pages/ScenarioListPage";
import ScenarioBuilderPage from "./pages/ScenarioBuilderPage";
import ScenarioPlayPage from "./pages/ScenarioPlayPage";
import ResultPage from "./pages/ResultPage";
import MyAttemptsPage from "./pages/MyAttemptsPage";
import ProfilePage from "./pages/ProfilePage";
import ScenarioDetailsPage from "./pages/ScenarioDetailsPage";
import VisualBuilderPage from "./pages/VisualBuilderPage";
import LeaderboardPage from "./pages/LeaderboardPage";


function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<LandingPage />} />
        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />
        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/scenarios" element={<ScenarioListPage />} />
        <Route path="/admin/builder" element={<ScenarioBuilderPage />} />
        <Route path="/scenarios/:scenarioId/play" element={<ScenarioPlayPage />} />
        <Route path="/attempts/:attemptId/result" element={<ResultPage />} />
        <Route path="/my-attempts" element={<MyAttemptsPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/scenarios/:scenarioId" element={<ScenarioDetailsPage />} />
        <Route path="/admin/visual-builder" element={<VisualBuilderPage />} />
        <Route path="/leaderboard" element={<LeaderboardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;