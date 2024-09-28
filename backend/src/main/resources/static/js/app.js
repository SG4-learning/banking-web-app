document.addEventListener('DOMContentLoaded', function() {
    fetch('/api/users')
        .then(response => response.json())
        .then(users => {
            const userList = document.getElementById('user-list');
            users.forEach(user => {
                const userItem = document.createElement('div');
                userItem.textContent = `Name: ${user.name}, Email: ${user.email}`;
                userList.appendChild(userItem);
            });
        })
        .catch(error => console.error('Error fetching users:', error));
});