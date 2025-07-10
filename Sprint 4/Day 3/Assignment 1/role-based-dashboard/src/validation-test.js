// Quick validation script to check all imports
// This file can be deleted - it's just for validation

// Test all imports
import { UserProvider, useUser, USER_ROLES } from './contexts/UserContext';
import { PermissionGuard, AuthGuard } from './components/guards/PermissionGuard';
import { Login } from './components/Login';
import { Header } from './components/Header';
import { Dashboard } from './components/Dashboard';

console.log('✅ All imports are working correctly!');
console.log('✅ Available USER_ROLES:', USER_ROLES);
console.log('✅ Components loaded successfully');

// You can delete this file after confirming everything works
