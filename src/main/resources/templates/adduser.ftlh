[#ftl]
<!DOCTYPE html>
<html lang="cs">
<head>
    <title>Přidání uživatele</title>
</head>
<body>
<form method="post" action="/adduser">
    <div class="col-md-3">
        <label for="username" class="form-label text-white">Username</label>
        <input type="text" id="username" name="username" class="form-control" required>
    </div>
    <div class="col-md-3">
        <label for="password" class="form-label text-white">Password</label>
        <input type="text" id="password" name="password" class="form-control">
    </div>
    <div class="col-md-3">
        <label for="userType" class="form-label text-white">User Type</label>
        <input type="text" id="userType" name="userType" class="form-control">
    </div>
    <div class="col-md-1 align-self-end">
        <button type="submit" class="btn btn-primary">Create User</button>
    </div>
</form>

</body>
</html>