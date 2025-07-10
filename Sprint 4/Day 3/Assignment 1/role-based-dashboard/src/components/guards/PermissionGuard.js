import { useUser } from '../../contexts/UserContext';

// Higher-Order Component for permission-based rendering
// Following Open/Closed Principle - open for extension, closed for modification
export function PermissionGuard({ 
  permission, 
  role, 
  fallback = null, 
  children 
}) {
  const { hasPermission, hasRole } = useUser();

  // Check permission if provided
  if (permission && !hasPermission(permission)) {
    return fallback;
  }

  // Check role if provided
  if (role && !hasRole(role)) {
    return fallback;
  }

  return children;
}

// Higher-Order Component for authenticated users only
export function AuthGuard({ fallback = null, children }) {
  const { isLoggedIn } = useUser();

  if (!isLoggedIn) {
    return fallback;
  }

  return children;
}
