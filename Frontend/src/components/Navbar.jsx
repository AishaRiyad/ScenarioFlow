import { Link, useNavigate } from "react-router-dom";
import "./Navbar.css";

export default function Navbar() {
  const navigate = useNavigate();
  const role = localStorage.getItem("role");
  const token = localStorage.getItem("token");

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
        {token && <Link to="/scenarios">Scenarios</Link>}
        {token && <Link to="/my-attempts">My Attempts</Link>}
        {role === "ADMIN" && <Link to="/admin">Admin</Link>}
        {role === "ADMIN" && <Link to="/admin/builder">Builder</Link>}

        {token ? (
          <button className="btn btn-secondary" onClick={logout}>Logout</button>
        ) : (
          <Link className="btn btn-primary" to="/login">Login</Link>
        )}
      </div>
    </nav>
  );
}