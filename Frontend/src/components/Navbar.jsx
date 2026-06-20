import { Link, useLocation, useNavigate } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
  const navigate = useNavigate();
  const location = useLocation();

  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

  if (location.pathname === "/" || location.pathname === "/login" || location.pathname === "/register") {
    return null;
  }

  function logout() {
    localStorage.removeItem("token");
    localStorage.removeItem("email");
    localStorage.removeItem("role");
    navigate("/login");
  }

  return (
    <nav className="navbar card">
      <Link to="/" className="nav-logo">ScenarioFlow 🌷</Link>

      <div className="nav-links">
        {token && <Link to="/profile">Profile</Link>}
        {token && <Link to="/scenarios">Scenarios</Link>}
        {token && <Link to="/my-attempts">My Attempts</Link>}
        {role === "ADMIN" && <Link to="/admin">Admin</Link>}
        {role === "ADMIN" && <Link to="/admin/builder">Builder</Link>}
        {role === "ADMIN" && <Link to="/admin/visual-builder">Visual Builder</Link>}

        <button className="btn btn-secondary" onClick={logout}>
          Logout
        </button>
      </div>
    </nav>
  );
}