CREATE TABLE users (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    full_name VARCHAR(100) NOT NULL,
    email VARCHAR(100) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    role VARCHAR(20) NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

CREATE TABLE scenarios (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    title VARCHAR(150) NOT NULL,
    description TEXT,
    category VARCHAR(100),
    difficulty VARCHAR(50),
    status VARCHAR(20) NOT NULL,
    created_by BIGINT NOT NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    CONSTRAINT fk_scenarios_created_by FOREIGN KEY (created_by) REFERENCES users(id)
);

CREATE TABLE scenario_nodes (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    scenario_id BIGINT NOT NULL,
    title VARCHAR(150) NOT NULL,
    content TEXT NOT NULL,
    node_type VARCHAR(20) NOT NULL,
    feedback_text TEXT,
    score_value INT DEFAULT 0,
    CONSTRAINT fk_nodes_scenario FOREIGN KEY (scenario_id) REFERENCES scenarios(id) ON DELETE CASCADE
);

CREATE TABLE choices (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    node_id BIGINT NOT NULL,
    choice_text VARCHAR(255) NOT NULL,
    next_node_id BIGINT NOT NULL,
    score_impact INT DEFAULT 0,
    CONSTRAINT fk_choices_node FOREIGN KEY (node_id) REFERENCES scenario_nodes(id) ON DELETE CASCADE,
    CONSTRAINT fk_choices_next_node FOREIGN KEY (next_node_id) REFERENCES scenario_nodes(id)
);

CREATE TABLE attempts (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    user_id BIGINT NOT NULL,
    scenario_id BIGINT NOT NULL,
    started_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    ended_at TIMESTAMP NULL,
    final_score INT DEFAULT 0,
    completed BOOLEAN DEFAULT FALSE,
    CONSTRAINT fk_attempts_user FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE,
    CONSTRAINT fk_attempts_scenario FOREIGN KEY (scenario_id) REFERENCES scenarios(id) ON DELETE CASCADE
);

CREATE TABLE attempt_steps (
    id BIGINT PRIMARY KEY AUTO_INCREMENT,
    attempt_id BIGINT NOT NULL,
    node_id BIGINT NOT NULL,
    choice_id BIGINT NOT NULL,
    step_order INT NOT NULL,
    CONSTRAINT fk_attempt_steps_attempt FOREIGN KEY (attempt_id) REFERENCES attempts(id) ON DELETE CASCADE,
    CONSTRAINT fk_attempt_steps_node FOREIGN KEY (node_id) REFERENCES scenario_nodes(id),
    CONSTRAINT fk_attempt_steps_choice FOREIGN KEY (choice_id) REFERENCES choices(id)
);