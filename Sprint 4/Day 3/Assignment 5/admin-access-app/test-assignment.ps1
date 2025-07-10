# Assignment 5 Test Script for Windows
Write-Host "🚀 Starting Assignment 5: Admin Access Control HOC Test" -ForegroundColor Green
Write-Host "===========================================================" -ForegroundColor Green

# Navigate to project directory
Set-Location "C:\Users\linar\OneDrive\Desktop\Nisum Training\Nisum-Training\Sprint 4\Day 3\Assignment 5\admin-access-app"

Write-Host "📦 Installing dependencies..." -ForegroundColor Yellow
npm install

Write-Host "🔧 Testing the project build..." -ForegroundColor Yellow
npm run build

if ($LASTEXITCODE -eq 0) {
    Write-Host "✅ Build successful!" -ForegroundColor Green
    Write-Host "🌐 Starting development server..." -ForegroundColor Cyan
    Write-Host "   Open http://localhost:5173 in your browser" -ForegroundColor White
    Write-Host "   Press Ctrl+C to stop the server" -ForegroundColor White
    npm run dev
} else {
    Write-Host "❌ Build failed! Please check the errors above." -ForegroundColor Red
}
