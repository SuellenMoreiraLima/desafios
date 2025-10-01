@echo off
echo Testando aplicacao com perfil de PRODUCAO...
echo.

REM Definir variaveis de ambiente para producao
set SPRING_PROFILES_ACTIVE=prod
set DB_HOST=localhost
set DB_PORT=3306
set DB_NAME=cep_database
set DB_USERNAME=root
set DB_PASSWORD=root123
set VIACEP_API_URL=https://viacep.com.br/ws

echo Variaveis de ambiente configuradas:
echo SPRING_PROFILES_ACTIVE=%SPRING_PROFILES_ACTIVE%
echo DB_HOST=%DB_HOST%
echo DB_PORT=%DB_PORT%
echo DB_NAME=%DB_NAME%
echo DB_USERNAME=%DB_USERNAME%
echo VIACEP_API_URL=%VIACEP_API_URL%
echo.

echo IMPORTANTE: Certifique-se de que o MySQL esteja rodando na porta 3306
echo.

REM Executar aplicacao
mvn spring-boot:run

pause