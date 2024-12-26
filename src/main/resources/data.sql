-- Create the 'users' table
CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    mail VARCHAR(255),
    enabled BOOLEAN NOT NULL DEFAULT true  -- Added 'enabled' column with default value true
);

-- Create the 'income' table
CREATE TABLE IF NOT EXISTS income (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    amount DECIMAL(10, 2) NOT NULL,  -- Updated to DECIMAL
    description VARCHAR(255),
    user_id BIGINT,  -- Consistent column naming
    date DATE
);

-- Create the 'expense' table
CREATE TABLE IF NOT EXISTS expense (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category VARCHAR(255),
    amount DECIMAL(10, 2) NOT NULL,  -- Updated to DECIMAL
    user_id BIGINT NOT NULL,  -- Consistent column naming
    date DATE NOT NULL
);

-- Create the 'balances' table
CREATE TABLE IF NOT EXISTS balances (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,  -- Consistent column naming
    total_income DECIMAL(10, 2) NOT NULL,  -- Updated to DECIMAL
    total_expense DECIMAL(10, 2) NOT NULL,  -- Updated to DECIMAL
    balance DECIMAL(10, 2) NOT NULL  -- Updated to DECIMAL
);

-- Insert data into 'users' table
INSERT INTO users (username, password, mail, enabled)
VALUES
    ('elcin', '12345', 'elcin@example.com', true),
    ('narmin', '12345', 'narmin@example.com', true),
    ('kamran', '12345', 'kamran@example.com', true);

-- Insert data into 'income' table
INSERT INTO income (amount, description, user_id, date) 
VALUES
    (1000.00, 'Salary', 1, '2024-01-01'),
    (250.00, 'Freelance work', 2, '2024-01-10'),
    (600.00, 'Investment income', 3, '2024-01-15');

-- Insert data into 'expense' table
INSERT INTO expense (category, amount, user_id, date) 
VALUES 
    ('Food', 150.00, 1, '2024-01-02'),
    ('Rent', 500.00, 2, '2024-01-05'),
    ('Entertainment', 80.00, 3, '2024-01-12');

-- Insert data into 'balances' table
INSERT INTO balances (user_id, total_income, total_expense, balance) 
VALUES
    (1, 1000.00, 150.00, 850.00),
    (2, 250.00, 500.00, -250.00),
    (3, 600.00, 80.00, 520.00);
