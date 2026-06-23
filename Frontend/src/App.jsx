import { BrowserRouter, Routes, Route } from "react-router-dom";

import Navbar from "./components/Navbar.jsx";

import LandingPage from "./pages/landing/LandingPage";

import LoginPage from "./pages/auth/LoginPage";
import RegisterPage from "./pages/auth/RegisterPage";

import AdminDashboard from "./pages/admin/AdminDashboard";
import ScenarioBuilderPage from "./pages/admin/ScenarioBuilderPage";
import VisualBuilderPage from "./pages/admin/VisualBuilderPage";

import ScenarioListPage from "./pages/user/ScenarioListPage";
import ScenarioPlayPage from "./pages/user/ScenarioPlayPage";
import ResultPage from "./pages/user/ResultPage";
import MyAttemptsPage from "./pages/user/MyAttemptsPage";
import ProfilePage from "./pages/user/ProfilePage";
import ScenarioDetailsPage from "./pages/user/ScenarioDetailsPage";
import LeaderboardPage from "./pages/user/LeaderboardPage";

function App() {
  return (
    <BrowserRouter>
      <Navbar />

      <Routes>
        <Route path="/" element={<LandingPage />} />

        <Route path="/login" element={<LoginPage />} />
        <Route path="/register" element={<RegisterPage />} />

        <Route path="/admin" element={<AdminDashboard />} />
        <Route path="/admin/builder" element={<ScenarioBuilderPage />} />
        <Route path="/admin/visual-builder" element={<VisualBuilderPage />} />

        <Route path="/scenarios" element={<ScenarioListPage />} />
        <Route path="/scenarios/:scenarioId" element={<ScenarioDetailsPage />} />
        <Route path="/scenarios/:scenarioId/play" element={<ScenarioPlayPage />} />
        <Route path="/attempts/:attemptId/result" element={<ResultPage />} />
        <Route path="/my-attempts" element={<MyAttemptsPage />} />
        <Route path="/profile" element={<ProfilePage />} />
        <Route path="/leaderboard" element={<LeaderboardPage />} />
      </Routes>
    </BrowserRouter>
  );
}

export default App;