import { useEffect, useState } from "react";
import api from "../../api/api";
import "./LeaderboardPage.css";

export default function LeaderboardPage() {

  const [players, setPlayers] = useState([]);

  useEffect(() => {
    async function fetchLeaderboard() {
      const res = await api.get("/attempts/leaderboard");
      setPlayers(res.data);
    }

    fetchLeaderboard();
  }, []);

  return (
    <main className="page leaderboard-page">

      <header className="leaderboard-header">
        <h1>Leaderboard 🏆</h1>
        <p>Top performers across all scenarios.</p>
      </header>

      <section className="card leaderboard-card">

        <table className="leaderboard-table">

          <thead>
            <tr>
              <th>Rank</th>
              <th>User</th>
              <th>Best Score</th>
              <th>Completed</th>
            </tr>
          </thead>

          <tbody>

            {players.map((player, index) => (
              <tr key={index}>

                <td>
                  {index === 0
                    ? "🥇"
                    : index === 1
                    ? "🥈"
                    : index === 2
                    ? "🥉"
                    : `#${index + 1}`}
                </td>

                <td>{player.fullName}</td>

                <td>{player.bestScore}</td>

                <td>{player.completedAttempts}</td>

              </tr>
            ))}

          </tbody>

        </table>

      </section>

    </main>
  );
}