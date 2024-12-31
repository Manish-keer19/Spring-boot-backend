import React from "react";
import { useEffect, useState } from "react";

const Dashboard = () => {
  const [userData, setUserData] = useState(null);
  const [loading, setLoading] = useState(true);
  const [error, setError] = useState(null);

  useEffect(() => {
    const fetchUserData = async () => {
      try {
        // const response = await fetch("http://localhost:8080/api/v1/success", {
        const response = await fetch("http://localhost:8080/success", {
          method: "GET",
          headers: {
            "Content-Type": "application/json",
            // Include authorization if needed (e.g., JWT token)
            // 'Authorization': `Bearer ${token}`
          },
          credentials: "include", // Ensure cookies are included in the request
        });
        if (!response.ok) {
          throw new Error("Failed to fetch data");
        }
        const data = await response.json();
        console.log("data is ", data);
        setUserData(data);
      } catch (error) {
        setError(error.message);
      } finally {
        setLoading(false);
      }
    };

    fetchUserData();
  }, []);

  if (loading)
    return <div className="text-white text-center mt-8">Loading...</div>;
  if (error)
    return (
      <div className="text-white text-center mt-8">{`Error: ${error}`}</div>
    );

  return (
    <div className="flex justify-center items-center bg-[#212121] min-h-screen text-white">
      {userData ? (
        <div className="bg-[#333] p-8 rounded-lg w-96 shadow-lg">
          <div className="flex flex-col items-center mb-6">
            <img
              src={userData.avatar_url}
              alt={userData.login}
              className="w-32 h-32 rounded-full mb-4 border-4 border-[#00A1D6]"
            />
            <h2 className="text-2xl font-semibold">
              {userData.name || userData.login}
            </h2>
            <p className="text-sm text-gray-400">{userData.bio}</p>
            <a
              href={userData.html_url}
              target="_blank"
              rel="noopener noreferrer"
              className="text-blue-400 mt-2 hover:underline"
            >
              GitHub Profile
            </a>
          </div>
          <div className="text-center mb-4">
            <p>
              <strong>Location:</strong> {userData.location || "N/A"}
            </p>
            <p>
              <strong>Public Repositories:</strong> {userData.public_repos}
            </p>
            <p>
              <strong>Followers:</strong> {userData.followers}
            </p>
            <p>
              <strong>Following:</strong> {userData.following}
            </p>
          </div>
          <div className="text-center mt-4">
            <p>
              <strong>Account Created On:</strong>{" "}
              {new Date(userData.created_at).toLocaleDateString()}
            </p>
          </div>
        </div>
      ) : (
        <div className="text-white text-center mt-8 ">
          <h1>wait user data is loading...</h1>
        </div>
      )}
    </div>
  );
};

export default Dashboard;
