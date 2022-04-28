import axiosClient from "../apiClient";

export async function registerUser(data) {
  return await axiosClient.post("/auth/register", JSON.stringify(data));
}

export async function loginUser(data) {
  return await axiosClient.post("/auth/login", JSON.stringify(data));
}

export async function getUser() {
  return await axiosClient.get(`/user/me`);
}

export async function getProfilePicture() {
  return await axiosClient.get(`/user/me/profile-picture`);
}

export async function getAllUsers() {
  return await axiosClient.get(`/user/users`);
}

export async function updateUser(data) {
  return await axiosClient.post(`/user/update`, JSON.stringify(data));
}

export async function uploadFile(data) {
  return await axiosClient.post(`/file/upload`, data);
}

export async function deleteUser(id) {
  return await axiosClient.delete(`/user/deleteUser/${id}`);
}
