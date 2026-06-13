import { BrowserRouter, Routes, Route } from "react-router-dom";

import LandingPage from "./pages/LandingPage";
import LoginPage from "./pages/LoginPage";
import RegisterPage from "./pages/RegisterPage";
import AdminDashboard from "./pages/AdminDashboard";
import ScenarioListPage from "./pages/ScenarioListPage";
import ScenarioBuilderPage from "./pages/ScenarioBuilderPage";

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
      </Routes>
    </BrowserRouter>
  );
}

export default App;