import { useEffect, useState } from "react";
import api from "../api/api";
import "./ProfilePage.css";

export default function ProfilePage() {
  const [profile, setProfile] = useState(null);
  const [achievements, setAchievements] = useState([]);

  const [editForm, setEditForm] = useState({
    fullName: "",
    email: "",
    currentPassword: "",
    newPassword: "",
  });

  const [message, setMessage] = useState("");

  useEffect(() => {
    async function fetchProfile() {
      const res = await api.get("/users/me");
      setProfile(res.data);
      setEditForm({
        fullName: res.data.fullName,
        email: res.data.email,
        currentPassword: "",
        newPassword: "",
      });

      const achievementsRes = await api.get("/users/me/achievements");
      setAchievements(achievementsRes.data);
    }

    fetchProfile();
  }, []);

  function handleChange(e) {
    setEditForm({ ...editForm, [e.target.name]: e.target.value });
  }

  async function updateProfile(e) {
    e.preventDefault();
    setMessage("");

    try {
      const res = await api.put("/users/me", editForm);
      setProfile(res.data);
      localStorage.setItem("email", res.data.email);
      setMessage("Profile updated successfully ✨");
      setEditForm({
        ...editForm,
        currentPassword: "",
        newPassword: "",
      });
    } catch (err) {
      setMessage(err.response?.data?.message || "Could not update profile");
    }
  }

  if (!profile) {
    return (
      <main className="page profile-page">
        <section className="card profile-card">
          <p>Loading profile...</p>
        </section>
      </main>
    );
  }

  return (
    <main className="page profile-page">
      <section className="card profile-card">
        <div className="profile-avatar">🌸</div>
        <h1>{profile.fullName}</h1>
        <p>{profile.email}</p>
        <span className="role-badge">{profile.role}</span>

        <div className="profile-stats">
          <div>
            <h3>{profile.totalAttempts}</h3>
            <p>Total Attempts</p>
          </div>

          <div>
            <h3>{profile.completedAttempts}</h3>
            <p>Completed</p>
          </div>

          <div>
            <h3>{profile.bestScore}</h3>
            <p>Best Score</p>
          </div>

          <div>
            <h3>{profile.averageScore.toFixed(1)}</h3>
            <p>Average Score</p>
          </div>
        </div>

        <form className="profile-form" onSubmit={updateProfile}>
          <h2>Edit Profile</h2>

          {message && <div className="info-box">{message}</div>}

          <input
            className="input"
            name="fullName"
            value={editForm.fullName}
            onChange={handleChange}
            placeholder="Full name"
          />

          <input
            className="input"
            name="email"
            type="email"
            value={editForm.email}
            onChange={handleChange}
            placeholder="Email"
          />

          <input
            className="input"
            name="currentPassword"
            type="password"
            value={editForm.currentPassword}
            onChange={handleChange}
            placeholder="Current password"
          />

          <input
            className="input"
            name="newPassword"
            type="password"
            value={editForm.newPassword}
            onChange={handleChange}
            placeholder="New password"
          />

          <button className="btn btn-primary" type="submit">
            Save Changes
          </button>
        </form>

        <section className="achievements-section">
          <h2>Achievements 🏅</h2>

          <div className="achievements-grid">
            {achievements.map((achievement) => (
              <div
                className={`achievement-card ${
                  achievement.unlocked ? "unlocked" : "locked"
                }`}
                key={achievement.title}
              >
                <div className="achievement-icon">{achievement.icon}</div>
                <h3>{achievement.title}</h3>
                <p>{achievement.description}</p>
                <span>{achievement.unlocked ? "Unlocked" : "Locked"}</span>
              </div>
            ))}
          </div>
        </section>
      </section>
    </main>
  );
}