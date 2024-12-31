import React from 'react';

const LoginButton = ({ providerName, providerUrl, bgColor, hoverColor }) => {
  const handleLoginRedirect = () => {
    window.location.href = providerUrl; // Redirect to the OAuth2 provider URL
  };

  return (
    <button
      onClick={handleLoginRedirect}
      className={`w-1/2 p-3 ${bgColor} text-white rounded-md hover:${hoverColor} focus:ring-2 focus:ring-gray-600 text-center block`}
    >
      Login with {providerName}
    </button>
  );
};

export default LoginButton;
