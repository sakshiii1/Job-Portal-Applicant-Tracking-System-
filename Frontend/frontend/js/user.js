var UserApi = {

  

  // 🔥 Admin: Get all users
  listAll: function () {
    return api.request("/api/admin/users", {
      method: "GET"
    });
  }

};

window.UserApi = UserApi;