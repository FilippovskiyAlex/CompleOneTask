-- Удаляем таблицы в правильном порядке (с учетом внешних ключей)
DROP TABLE IF EXISTS comments CASCADE;
DROP TABLE IF EXISTS task_users CASCADE;
DROP TABLE IF EXISTS tasks CASCADE;
DROP TABLE IF EXISTS projects CASCADE;
DROP TABLE IF EXISTS tags CASCADE;
DROP TABLE IF EXISTS users CASCADE;

-- Таблица пользователей
CREATE TABLE users (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    email VARCHAR(255) NOT NULL UNIQUE
);
-- Таблица тегов
CREATE TABLE tags (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT
);
-- Таблица проектов
CREATE TABLE projects (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status VARCHAR(255) NOT NULL
);
-- Таблица задач
CREATE TABLE tasks (
    id UUID PRIMARY KEY,
    name VARCHAR(255) NOT NULL UNIQUE,
    description TEXT,
    status VARCHAR(255) NOT NULL,
    start_date DATE NOT NULL,
    end_date DATE,
    project_id UUID,
    tag_id UUID,
    FOREIGN KEY (project_id) REFERENCES projects(id),
    FOREIGN KEY (tag_id) REFERENCES tags(id)
);
-- Таблица связей задач и пользователей
CREATE TABLE task_users (
    task_id UUID NOT NULL,
    user_id UUID NOT NULL,
    PRIMARY KEY (task_id, user_id),
    FOREIGN KEY (task_id) REFERENCES tasks(id),
    FOREIGN KEY (user_id) REFERENCES users(id)
);
-- Таблица комментариев
CREATE TABLE comments (
    id UUID PRIMARY KEY,
    description VARCHAR(255) NOT NULL,
    created_at DATE NOT NULL UNIQUE,
    user_id UUID NOT NULL,
    task_id UUID NOT NULL,
    FOREIGN KEY (user_id) REFERENCES users(id),
    FOREIGN KEY (task_id) REFERENCES tasks(id)
);