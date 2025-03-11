-- Rollback para excluir as tabelas e a sequência
DROP TABLE IF EXISTS tb_task CASCADE;
DROP TABLE IF EXISTS tb_project CASCADE;
DROP TABLE IF EXISTS tb_client CASCADE;
DROP TABLE IF EXISTS tb_users CASCADE;

-- Rollback para remover a sequência
DROP SEQUENCE IF EXISTS task_seq;

