import { useNavigate } from "react-router-dom";

export const ProductCardView = ({ handler, id, name, description, price }) => {
    
    const navigate = useNavigate();

    const ProductHandler = (product) => {
        // console.log(product);
        handler(product);
        navigate('/cart');
    }
    
    return (
        <>
            <div className="card h-100">
                <div className="card-body">
                    {/* <img className="card-img-top" src={prod.image} alt="" width='100%' /> */}
                    <div className="card-body p-4" style={{height: '100%'}}>
                        <h5 className="card-title mb-2">
                            {name}
                        </h5>
                        <p className="card-text mb-2 " style={{height: '100px'}}>{description}</p>
                        <p className="card-text fw-bold text-end">$ {price} </p>
                        {/* <button className="btn btn-primary">Agregar</button> */}
                        <div className="card-footer pt-0 border-top-0 bg-transparent">
                            <div className="text-center">
                                <button 
                                    className="btn btn-outline-dark mt-auto"
                                    onClick={()=>ProductHandler({id,name,description,price})}
                                >
                                    <i className="bi bi-cart-plus">   Agregar</i>
                                    
                                </button>
                            </div>
                        </div>
                    </div>
                </div>
            </div>
        </>
    )
};
