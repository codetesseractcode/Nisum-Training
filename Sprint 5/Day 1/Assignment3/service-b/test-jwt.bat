@echo off
echo Testing JWT Authentication Microservice
echo ========================================

echo.
echo 1. Testing Health Endpoint...
curl -s http://localhost:8085/test/health
echo.

echo.
echo 2. Generating JWT Token with ROLE_SERVICE...
for /f "tokens=*" %%i in ('curl -s "http://localhost:8085/test/generate-token?username=testuser&role=ROLE_SERVICE"') do set TOKEN_RESPONSE=%%i
echo %TOKEN_RESPONSE%
echo.

echo.
echo 3. Testing Protected Endpoint WITHOUT Token (Should return 403)...
curl -s -w "Status: %%{http_code}" http://localhost:8085/api/protected-data
echo.

echo.
echo 4. Copy the token from step 2 and test manually:
echo curl -H "Authorization: Bearer YOUR_TOKEN" http://localhost:8085/api/protected-data
echo.

echo Testing complete!
pause
