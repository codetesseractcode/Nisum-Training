import React from 'react';
import { PermissionGuard } from './guards/PermissionGuard';
import { useUser, USER_ROLES } from '../contexts/UserContext';
import './Dashboard.css';

// Following Single Responsibility Principle - separate components for different features
function AddProductSection() {
  return (
    <div className="dashboard-card">
      <h3>Add Product</h3>
      <p>Admin-only feature to add new products to the inventory.</p>
      <button className="action-button primary">
        Add New Product
      </button>
    </div>
  );
}

function ViewAnalyticsSection() {
  return (
    <div className="dashboard-card">
      <h3>Analytics</h3>
      <p>View detailed analytics and reports.</p>
      <div className="analytics-grid">
        <div className="metric">
          <h4>Total Sales</h4>
          <span className="metric-value">$12,345</span>
        </div>
        <div className="metric">
          <h4>Active Users</h4>
          <span className="metric-value">1,234</span>
        </div>
      </div>
    </div>
  );
}

function ManageUsersSection() {
  return (
    <div className="dashboard-card">
      <h3>Manage Users</h3>
      <p>Admin-only feature to manage system users.</p>
      <button className="action-button secondary">
        View All Users
      </button>
    </div>
  );
}

function ProductListSection() {
  const products = [
    { id: 1, name: 'Laptop', price: '$999' },
    { id: 2, name: 'Mouse', price: '$25' },
    { id: 3, name: 'Keyboard', price: '$75' }
  ];

  return (
    <div className="dashboard-card">
      <h3>Products</h3>
      <p>View available products in the system.</p>
      <div className="product-list">
        {products.map(product => (
          <div key={product.id} className="product-item">
            <span>{product.name}</span>
            <span className="price">{product.price}</span>
          </div>
        ))}
      </div>
    </div>
  );
}

function ProfileSection() {
  const { user } = useUser();
  
  return (
    <div className="dashboard-card">
      <h3>My Profile</h3>
      <div className="profile-info">
        <p><strong>Name:</strong> {user.name}</p>
        <p><strong>Email:</strong> {user.email}</p>
        <p><strong>Role:</strong> {user.role}</p>
      </div>
      <button className="action-button secondary">
        Edit Profile
      </button>
    </div>
  );
}

export function Dashboard() {
  const { user } = useUser();

  return (
    <div className="dashboard-container">
      <div className="dashboard-content">
        <div className="welcome-section">
          <h2>Welcome to your Dashboard, {user.name}!</h2>
          <p className="role-description">
            You are logged in as: <strong>{user.role.toUpperCase()}</strong>
          </p>
        </div>

          <div className="dashboard-grid">
            {/* Everyone can see products */}
            <ProductListSection />

            {/* Users and Admins can edit profile */}
            <PermissionGuard permission="edit_profile">
              <ProfileSection />
            </PermissionGuard>

            {/* Only Admins can add products */}
            <PermissionGuard 
              permission="add_product"
              fallback={
                <div className="dashboard-card restricted">
                  <h3>Add Product</h3>
                  <p>⚠️ Admin access required</p>
                </div>
              }
            >
              <AddProductSection />
            </PermissionGuard>

            {/* Only Admins can view analytics */}
            <PermissionGuard permission="view_analytics">
              <ViewAnalyticsSection />
            </PermissionGuard>

            {/* Only Admins can manage users */}
            <PermissionGuard permission="manage_users">
              <ManageUsersSection />
            </PermissionGuard>
          </div>

          <div className="permissions-info">
            <h4>Your Permissions:</h4>
            <div className="permissions-list">
              {user && user.role && (
                <>
                  {user.role === USER_ROLES.ADMIN && (
                    <span className="permission admin">All Permissions</span>
                  )}
                  {user.role === USER_ROLES.USER && (
                    <>
                      <span className="permission user">View Products</span>
                      <span className="permission user">Edit Profile</span>
                    </>
                  )}
                  {user.role === USER_ROLES.GUEST && (
                    <span className="permission guest">View Products Only</span>
                  )}
                </>
              )}
            </div>
          </div>
        </div>
      </div>
  );
}
