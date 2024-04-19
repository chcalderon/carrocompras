import { useEffect, useState } from "react";
import { getProducts } from "../services/productService";
import { ProductCardView } from "./ProductCardView";


export const CatalogView = ({ handler }) => {

    const [products, setProducts] = useState([]);
    const [isLoading, setIsLoading] = useState(true);

    const findAll = async () => {
        const prods = await getProducts();
        setProducts(prods);
        setIsLoading(false);
    }
    useEffect(
        () => {
            findAll();
        }, []);
    return (
        <>
            {
                isLoading &&
                <div className="alert alert-info">Cargando..</div>
            }
            <div className="row gx-4 gx-lg-5 row-cols-2 row-cols-md-3 row-cols-xl-4 justify-content-center">
                {products.map(prod => (

                    <div className="col-4 mb-5" key={prod.id}>
                        <ProductCardView
                            handler={handler}
                            id={prod.id}
                            name={prod.name}
                            description={prod.description} price={prod.price} />
                    </div>

                ))}

            </div>
        </>
    );
}