import React from "react";

function Profile() {

  const user = JSON.parse(localStorage.getItem("user"));

  return (
    <div>
      <h2>User Profile</h2>
      <p><b>Username:</b> {user.username}</p>
      <p><b>Email:</b> {user.email}</p>
      <p><b>Phone:</b> {user.phone}</p>
      <p><b>About:</b> {user.about}</p>

      <h3>Important Links</h3>
      <a href="https://www.google.com">Google</a><br/>
      <a href="https://github.com">GitHub</a><br/>
      <a href="https://linkedin.com">LinkedIn</a>
    </div>
  );
}

export default Profile;