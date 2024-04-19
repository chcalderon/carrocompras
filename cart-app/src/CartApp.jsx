import 'bootstrap/dist/css/bootstrap.css';
import 'bootstrap-icons/font/bootstrap-icons.css';
import { NavBar } from './components/NavBar';
import { CartRoutes } from './routes/CartRoutes';
import { useItemsCart } from "./hooks/useItemsCart";
import "./style.css";

export const CartApp = () => {

    const { cartItems, handlerAddProductCart, handlerDeleteProductCart} = useItemsCart();
    return (
        <>
            <NavBar/>
            <div className="container my-4">
                <h3 className="my-4">Cart App</h3>
                <CartRoutes
                    cartItems={cartItems}
                    handlerAddProductCart={handlerAddProductCart}
                    handlerDeleteProductCart={handlerDeleteProductCart}
                />
            </div>
        </>
    )
}