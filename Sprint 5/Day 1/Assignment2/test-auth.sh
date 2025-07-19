# Quick Test Script for Authentication
# Run these commands after the application starts successfully

echo "Testing DAO-Based Authentication..."

echo "1. Testing public endpoint (no auth):"
curl -w "\n" http://localhost:8086/api/db/public/info

echo "2. Registering a new user:"
curl -X POST http://localhost:8086/api/db/public/register \
  -H "Content-Type: application/json" \
  -d '{"username":"testuser","password":"password123","role":"USER"}' \
  -w "\n"

echo "3. Testing user authentication:"
curl -u admin:admin123 http://localhost:8086/api/db/user/profile -w "\n"

echo "4. Testing admin access:"
curl -u admin:admin123 http://localhost:8086/api/db/admin/users -w "\n"

echo ""
echo "Testing LDAP Authentication..."

echo "5. Testing LDAP public endpoint:"
curl -w "\n" http://localhost:8086/api/ldap/public/info

echo "6. Testing LDAP user authentication:"
curl -u ben:benspassword http://localhost:8086/api/ldap/user/profile -w "\n"

echo "7. Testing LDAP admin access:"
curl -u joe:joespassword http://localhost:8086/api/ldap/admin/info -w "\n"

echo ""
echo "Testing System Endpoints..."

echo "8. Testing health endpoint:"
curl -w "\n" http://localhost:8086/actuator/health
