import { useEffect, useState } from "react";
import { useNavigate } from "react-router-dom";
import { calculateTotal } from "../services/productService";

export const CartView = ({ items, handlerDelete }) => {

    const [total, setTotal] = useState(0);
    const navigate = useNavigate();

    useEffect(()=>{
        setTotal(calculateTotal(items));
    },[items]);

    const onDeleteProduct = (id) =>{
        handlerDelete(id);
    } 
    const onCatalog = () => {
        navigate("/catalog");
    }
    return (
        <>
            <h3>Carro de Compras</h3>
            <table className={items.length > 0 ? "table table-hover table-striped":"table"}>
              
            
                <thead>
                    <tr>
                        <th>Producto</th>
                        <th>Precio</th>
                        <th>Cantidad</th>
                        <th>Total</th>
                        <th>Eliminar</th>
                    </tr>
                </thead>
                <tbody>

                    {items.length > 0 ?
                        items.map(
                            item => (
                                <tr key={item.product.id}>
                                    <td>{item.product.name}</td>
                                    <td>$ {item.product.price}</td>
                                    <td>{item.quantity}</td>
                                    <td>{item.product.price * item.quantity}</td>
                                    <td>
                                        <button 
                                            className="btn btn-outline-secondary"
                                            onClick={ ()=> onDeleteProduct(item.product.id)}   
                                        >
                                            <i className="bi bi-trash"></i>
                                        </button>
                                    </td>
                                </tr>

                            )
                        )
                        : (<tr>
                            <td colSpan={5} className="fw-bold">
                                <p className="alert alert-warning">
                                    <i className="bi bi-exclamation-triangle"/>
                                    &nbsp;&nbsp;
                                    Agregue productos al carro de compras
                                </p>
                            </td>
                        </tr>
                        )
                    }

                </tbody>
                <tfoot>
                    <tr>
                        <td colSpan={"3"} className="text-end fw-bold">Total</td>
                        <td colSpan={"2"} className="text-start fw-bold">{total}</td>
                    </tr>
                </tfoot>
            </table>
            <button 
               className="btn btn-success"
               onClick={onCatalog}
            >Seguir comprando</button>

        </>
    )
};
