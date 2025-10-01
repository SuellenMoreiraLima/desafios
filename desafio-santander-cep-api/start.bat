@echo off
echo 🚀 Iniciando Desafio Santander CEP com Docker...
echo.

docker compose up --build -d

if %errorlevel% equ 0 (
    echo.
    echo ✅ SUCESSO! Serviços iniciados!
    echo.
    echo 🔗 Aplicação: http://localhost:8080/api/cep/01310100
    echo 🗄️ MySQL Web: http://localhost:8082
    echo.
    echo 📝 Para parar: docker compose down
) else (
    echo.
    echo ❌ Erro ao iniciar. Verifique se Docker Desktop está rodando.
)

pause