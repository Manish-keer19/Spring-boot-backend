import { useState } from 'react';
import LoginButton from './LoginButton';
import React from 'react';

const LoginPage = () => {
  const [email, setEmail] = useState('');
  const [username, setUsername] = useState('');
  const [profilePic, setProfilePic] = useState('');

  const handleLogin = () => {
    console.log('Logged in with:', { username, email, profilePic });
  };

  return (
    <div className="flex justify-center items-center h-screen bg-[#212121]">
      <div className="bg-[#333] p-8 rounded-lg w-96 shadow-lg">
        <h2 className="text-center text-2xl font-semibold text-white mb-6">Login</h2>
        <input
          type="text"
          placeholder="Username"
          value={username}
          onChange={(e) => setUsername(e.target.value)}
          className="mb-4 p-3 bg-[#424242] text-white border border-[#424242] rounded-md w-full focus:ring-2 focus:ring-white"
        />
        <input
          type="email"
          placeholder="Email"
          value={email}
          onChange={(e) => setEmail(e.target.value)}
          className="mb-4 p-3 bg-[#424242] text-white border border-[#424242] rounded-md w-full focus:ring-2 focus:ring-white"
        />
        <input
          type="text"
          placeholder="Profile Picture URL"
          value={profilePic}
          onChange={(e) => setProfilePic(e.target.value)}
          className="mb-6 p-3 bg-[#424242] text-white border border-[#424242] rounded-md w-full focus:ring-2 focus:ring-white"
        />
        <button
          onClick={handleLogin}
          className="w-full p-3 bg-blue-500 text-white rounded-md hover:bg-blue-600 focus:ring-2 focus:ring-blue-300 mb-4"
        >
          Login
        </button>

        <div className="flex justify-between">
          {/* Reuse LoginButton for Google */}
          <LoginButton
            providerName="Google"
            providerUrl="http://localhost:8080/oauth2/authorization/google"
            bgColor="bg-[#db4437]"
            hoverColor="bg-[#c1351d]"
          />

          {/* Reuse LoginButton for GitHub */}
          <LoginButton
            providerName="GitHub"
            providerUrl="http://localhost:8080/oauth2/authorization/github"
            bgColor="bg-[#333]"
            hoverColor="bg-[#222]"
          />
        </div>
      </div>
    </div>
  );
};

export default LoginPage;
