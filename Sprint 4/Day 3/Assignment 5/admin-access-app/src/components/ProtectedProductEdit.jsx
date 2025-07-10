import ProductEdit from './ProductEdit';
import withAdminAccess from '../hoc/withAdminAccess';

const ProtectedProductEdit = withAdminAccess(ProductEdit);

export default ProtectedProductEdit;
