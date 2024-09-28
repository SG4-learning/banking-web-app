-- Create tables
CREATE TABLE IF NOT EXISTS user (
    id INT PRIMARY KEY AUTO_INCREMENT,
    name VARCHAR(100) NOT NULL,
    password VARCHAR(100) NOT NULL,
    phone VARCHAR(15),
    email VARCHAR(255)
);

CREATE TABLE IF NOT EXISTS account (
    id INT PRIMARY KEY AUTO_INCREMENT,
    user_id INT,
    account_number VARCHAR(20) UNIQUE NOT NULL,
    account_type VARCHAR(50) NOT NULL,
    balance DECIMAL(15, 2) NOT NULL,
    FOREIGN KEY (user_id) REFERENCES user(id)
);

CREATE TABLE IF NOT EXISTS transactions (
    id INT PRIMARY KEY AUTO_INCREMENT,
    account_id INT,
    transaction_type VARCHAR(50) NOT NULL,
    amount DECIMAL(15, 2) NOT NULL,
    transaction_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    FOREIGN KEY (account_id) REFERENCES account(id)
);

-- Insert initial data
INSERT INTO user (name, email, phone, password) VALUES
('John Doe', 'john.doe@example.com', '123-456-7890', '123 Elm Street'),
('Jane Smith', 'jane.smith@example.com', '987-654-3210', '456 Oak Avenue');

INSERT INTO account (user_id, account_number, account_type, balance) VALUES
(1, '1234567890', 'Checking', 1000.00),
(1, '0987654321', 'Savings', 5000.00),
(2, '1122334455', 'Checking', 2000.00);

INSERT INTO transactions (account_id, transaction_type, amount) VALUES
(1, 'Deposit', 1000.00),
(2, 'Deposit', 5000.00),
(3, 'Deposit', 2000.00),
(1, 'Withdrawal', 200.00),
(2, 'Withdrawal', 1000.00);