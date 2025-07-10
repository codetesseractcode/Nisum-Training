#!/bin/bash

# Assignment 5 Test Script
echo "🚀 Starting Assignment 5: Admin Access Control HOC Test"
echo "==========================================================="

# Navigate to project directory
cd "C:\Users\linar\OneDrive\Desktop\Nisum Training\Nisum-Training\Sprint 4\Day 3\Assignment 5\admin-access-app"

echo "📦 Installing dependencies..."
npm install

echo "🔧 Building the project..."
npm run build

if [ $? -eq 0 ]; then
    echo "✅ Build successful!"
    echo "🌐 Starting development server..."
    echo "   Open http://localhost:5173 in your browser"
    echo "   Press Ctrl+C to stop the server"
    npm run dev
else
    echo "❌ Build failed! Please check the errors above."
fi
